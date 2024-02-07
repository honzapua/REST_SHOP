package cz.honza.Shop;

import cz.honza.Shop.db.service.api.CustomerService;
import cz.honza.Shop.db.service.api.MerchantService;
import cz.honza.Shop.db.service.api.ProductService;
import cz.honza.Shop.db.service.api.request.UpdateProductRequest;
import cz.honza.Shop.domain.Customer;
import cz.honza.Shop.domain.Product;
import cz.honza.Shop.domain.Merchant;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

//Tests in Spring context
@RunWith(SpringRunner.class)
@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_CLASS)
public class DBServiceTests {
    @Autowired  //v testech @Autowired, ale v produkcnich tridach constructor injection
    private CustomerService customerService;
    @Autowired
    private MerchantService merchantService;
    @Autowired
    private ProductService productService;

    private Merchant merchant; //pomocny deklarace field kvuli createMerchant()

    @Before //pomocna metoda se bude spoustet pred kazdym testem, potrebujeme merchant field/property kvuli product() a merchant()
    public void createMerchant() {
        if (merchant == null) {
//            Merchant merchant = new Merchant("CZC.cz", "obchod@czc.cz", "Hvezdova");  // Nechceme tvorit Novou instanci Merchant! Byl by null pointer!
            merchant = new Merchant("CZC.cz", "obchod@czc.cz", "Hvezdova");
            Integer id = merchantService.add(merchant);
            assert id != null;  //if id == null Merchant creation failed
            merchant.setId(id); // bez tohoto radku bude objekt jiny a testy selzou
        }
    }

    @Test   //pom.xml pro <scope>test</scope> h2 DB
    public void customer() {
        Customer customer = new Customer("Pepa", "Zlamal", "gusep@seznam.cz", "Sarajevska", 40, "7771234567");
        Integer id = customerService.add(customer); // 1 row added
        assert id != null;  //if id == null Customer creation failed
        customer.setId(id); // bez tohoto radku bude objekt jiny a testy selzou

        Customer fromDB = customerService.get(id);  //po pridani do db nacteni z DB
        Assert.assertEquals(customer, fromDB);  //vytvoreny customer je shodny s ulozenym v DB

        List<Customer> customers = customerService.getCustomers();
        Assert.assertEquals(1, customers.size());   //velikost listu by mela byt 1
        Assert.assertEquals(customer, customers.get(0));
    }

    @Test
    public void merchant() {
//        duplicity from this code was moved to method  @Before createMerchant();
//        Merchant merchant = new Merchant("CZC.cz", "obchod@czc.cz", "Hvezdova");
//        Integer id = merchantService.add(merchant);
//        assert id != null;  //if id == null Merchant creation failed
//        merchant.setId(id); // bez tohoto radku bude objekt jiny a testy selzou*/

        Merchant fromDB = merchantService.get(merchant.getId()); //po pridani do db nacteni z DB
        Assert.assertEquals(merchant, fromDB);  //vytvoreny merchant je shodny s ulozenym v DB

        List<Merchant> merchants = merchantService.getMerchants();
        Assert.assertEquals(1, merchants.size());   //velikost listu by mela byt 1
        Assert.assertEquals(merchant, merchants.get(0));   // pozor merchants != merchant
    }

    @Test
    // problem s merchant_id, nevime jestli probehl predchozi test a jestli uz je v DB merchant_id. => @Before createMerchant()
    public void product() {
        Product product = new Product(merchant.getId(), "ProductName", "Product Description", 5, 1);
        Integer id = productService.add(product);
        assert id != null;
        product.setId(id);

        Product fromDB = productService.get(id);
        Assert.assertEquals(product, fromDB);

        List<Product> products = productService.getProducts();
        Assert.assertEquals(1, products.size());   //velikost listu by mela byt 1
        Assert.assertEquals(product, products.get(0));   // pozor products != product

        product.setAvailable(10);
        UpdateProductRequest productRequest = new UpdateProductRequest(product.getName(), product.getDescription(), product.getPrice(), product.getAvailable());

        productService.update(id, productRequest);  // this has available = 10
        Product fromDBAfterUpdate = productService.get(id);
        Assert.assertEquals(product, fromDBAfterUpdate);
        Assert.assertNotEquals(fromDB, fromDBAfterUpdate);// enriched by setAvailable(10);

        productService.delete(id);
        Assert.assertEquals(0, productService.getProducts().size());
    }
}
