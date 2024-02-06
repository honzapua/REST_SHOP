package cz.honza.Shop.db.repository;

import com.fasterxml.jackson.datatype.jsr310.deser.InstantDeserializer;
import cz.honza.Shop.db.mapper.BoughtProductRowMapper;
import cz.honza.Shop.domain.BoughtProduct;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
public class BoughtProductRepository {
    private final JdbcTemplate jdbcTemplate;
    private  final BoughtProductRowMapper boughtProductRowMapper = new BoughtProductRowMapper();

    public BoughtProductRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void add(BoughtProduct boughtProduct) {  //be aware" product_id in DB not productId etc.
        final String sql = "INSERT INTO bought_product (product_id, customer_id, quantity, bought_at) VALUES (?, ?, ?, ?)";
        jdbcTemplate.update(sql, boughtProduct.getProductId(),       // Object... args[] can be as many argument we need for INSERT!
                                boughtProduct.getCustomerId(),
                                boughtProduct.getQuantity(),
                                boughtProduct.getBoughtAt());

    }

    public List<BoughtProduct> getAllByCustomerId(int customerId) {
        final String sql = "SELECT * FROM bought_product WHERE customer_id = " + customerId;
        return jdbcTemplate.query(sql,boughtProductRowMapper);
    }
}
