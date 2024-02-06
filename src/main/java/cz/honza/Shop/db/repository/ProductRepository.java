package cz.honza.Shop.db.repository;

import cz.honza.Shop.db.service.api.request.UpdateProductRequest;
import cz.honza.Shop.domain.Product;
import cz.honza.Shop.db.mapper.ProductRawMapper;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;

@Component
public class ProductRepository {
    private final JdbcTemplate jdbcTemplate;
    private final ProductRawMapper productRawMapper = new ProductRawMapper();

    public ProductRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Product get(int id) {
        final String sql = "SELECT * FROM product WHERE product.id = " + id;
        try {
            //one Object we need only 1 where id = int
            return jdbcTemplate.queryForObject(sql, productRawMapper);
        } catch (EmptyResultDataAccessException e) {    // if id does not return
            return null;
        }
    }

    public Integer add(Product product) {
        final String sql = "INSERT INTO product(merchant_id, name, description, price, created_at, available) VALUES (?,?,?,?,?,?)";

        KeyHolder keyHolder = new GeneratedKeyHolder(); // for RETURNING Generated key from DB thx to update
        jdbcTemplate.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                // if Customer is set return generated id
                PreparedStatement ps = con.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
                ps.setInt(1, product.getMerchantId());
                ps.setString(2, product.getName());
                ps.setString(3, product.getDescription());
                ps.setDouble(4, product.getPrice());
                //Bez IF vrati null, kdyz nepujdeme pres konstruktor, pri pouziti REST POST volani.
                // @NonNull => @Nullable v Product
                if (product.getCreatedAt() == null) {
                    product.setCreatedAt(Timestamp.from(Instant.now()));
                }
                ps.setTimestamp(5, product.getCreatedAt());
                ps.setInt(6, product.getAvailable());
                return ps;
            }
        }, keyHolder);

        //zpusob ziskani id po vygenerovani radku tabulky
        if (keyHolder.getKey() != null) {
            return keyHolder.getKey().intValue();
        } else {
            return null;
        }
    }

    public List<Product> getAll() {
        final String sql = "SELECT * FROM product";
        return jdbcTemplate.query(sql, productRawMapper);
    }

    public void update(int id, UpdateProductRequest request) {
        final String sql = "UPDATE product SET name=?, description=?, price=?, available=? WHERE id = ?"; /* id = "+id;*/
        jdbcTemplate.update(sql, request.getName(), request.getDescription(), request.getPrice(), request.getAvailable(), id);
    }

    public void delete(int id){
        final String sql = "DELETE FROM product WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }

    public void updateAvailable(int id, int newAvailable) {
        final String sql =  "UPDATE product SET available = ? WHERE id = ?";
        jdbcTemplate.update(sql, newAvailable, id);
    }
}
