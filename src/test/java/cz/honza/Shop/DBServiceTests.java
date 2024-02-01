package cz.honza.Shop;

/**
 * Testy v spring kontexte
 */

import cz.honza.Shop.db.service.api.CustomerService;
import cz.honza.Shop.db.service.api.MerchantService;
import cz.honza.Shop.domain.Customer;
import cz.honza.Shop.domain.Merchant;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DBServiceTests {
    @Autowired  //v testech @Autowired, ale v produkcnich tridach constructor injection
    private CustomerService customerService;
    @Autowired
    private MerchantService merchantService;

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
        Merchant merchant = new Merchant("CZC.cz", "obchod@czc.cz", "Hvezdova" );
        Integer id = merchantService.add(merchant);
        assert id != null;  //if id == null Merchant creation failed
        merchant.setId(id); // bez tohoto radku bude objekt jiny a testy selzou

        Merchant fromDB = merchantService.get(id); //po pridani do db nacteni z DB
        Assert.assertEquals(merchant, fromDB);  //vytvoreny merchant je shodny s ulozenym v DB

        List<Merchant> merchants = merchantService.getMerchants();
        Assert.assertEquals(1, merchants.size());   //velikost listu by mela byt 1
        Assert.assertEquals(merchant, merchants.get(0));   // pozor merchants != merchant
    }
}
