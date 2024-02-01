package cz.honza.Shop;

import cz.honza.Shop.domain.Customer;
import cz.honza.Shop.domain.Merchant;
import cz.honza.Shop.domain.Product;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.Instant;

/**
 * testy v tehle tride se budou spoustet pod spring kontextem,
 * tj. muzeme pouzivat vsechny beans, ktere aplikace realne vyprodukovala
 */
@RunWith(SpringRunner.class)    //
@SpringBootTest
public class DBInsertTests {

    private final String insertCustomer = "INSERT INTO customer(name, surname, email, address, age, phone_number) values (?,?,?,?,?,?)";
    private final String insertMerchant = "INSERT INTO merchant(name, email, address) values (?,?,?)";
    private final String insertProduct = "INSERT INTO product(merchant_id, name, description, price, created_at, available) values (?,?,?,?,?,?)";
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Test
    public void createProduct() {
        Product product = new Product();

        product.setMerchantId(1); // merchant uz byl vytvoren, neni to buletproof, ted to staci na test
        product.setName("Klavesnice");
        product.setDescription("Velmi dobra Razer klavesnice");
        product.setPrice(15.5);
        product.setCreatedAt(Timestamp.from(Instant.now()));    // Timestamp
        product.setAvailable(10);

        jdbcTemplate.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                PreparedStatement ps = con.prepareStatement(insertProduct);    //SQL command
                // v parametrech objekt vytvoreny o par radku vyse
                ps.setInt(1, product.getMerchantId());
                ps.setString(2, product.getName());
                ps.setString(3, product.getDescription());
                ps.setDouble(4, product.getPrice());
                ps.setTimestamp(5, product.getCreatedAt());
                ps.setInt(6, product.getAvailable());
                return ps;
            }
        });
    }

    @Test
    public void createCustomer() {
        Customer customer = new Customer(); // jen pro zacatek ;)

        customer.setName("Ferko");
        customer.setSurname("Mrkvicka");
        customer.setEmail("xxx@email.cz");
        customer.setAddress("xxx");
        customer.setAge(17);
        customer.setPhone_number("xxx");

        jdbcTemplate.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                PreparedStatement ps = con.prepareStatement(insertCustomer);    //SQL command
                // v parametrech objekt vytvoreny o par radku vyse
                ps.setString(1, customer.getName());
                ps.setString(2, customer.getSurname());
                ps.setString(3, customer.getEmail());
                ps.setString(4, customer.getAddress());
                ps.setInt(5, customer.getAge());
                ps.setString(6, customer.getPhone_number());
                return ps;
            }
        });
    }

    @Test
    public void createMerchant() {
        Merchant merchant = new Merchant();

        merchant.setName("merchant Mironet");
        merchant.setEmail("mironet@seznam.cz");
        merchant.setAddress("Praha 4, Chodov");

        jdbcTemplate.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                PreparedStatement ps = con.prepareStatement(insertMerchant);    //SQL command
                // v parametrech objekt vytvoreny o par radku vyse
                ps.setString(1, merchant.getName());
                ps.setString(2, merchant.getEmail());
                ps.setString(3, merchant.getAddress());
                return ps;
            }
        });
    }
}


