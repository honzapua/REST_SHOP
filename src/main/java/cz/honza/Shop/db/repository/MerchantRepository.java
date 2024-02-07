package cz.honza.Shop.db.repository;

import cz.honza.Shop.db.mapper.MerchantRowMapper;
import cz.honza.Shop.domain.Merchant;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

@Component
public class MerchantRepository {
    private final JdbcTemplate jdbcTemplate;
    private final MerchantRowMapper merchantRawMapper = new MerchantRowMapper();

    public MerchantRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Merchant get(int id) {
        final String sql = "SELECT * FROM merchant WHERE merchant.id = " + id;
        try {
            //one Object we need only 1 where id = int
            return jdbcTemplate.queryForObject(sql, merchantRawMapper);
        } catch (EmptyResultDataAccessException e) {    // if id does not return
            return null;
        }
    }

    public Integer add(Merchant merchant) {
        final String sql = "INSERT INTO merchant(name, email, address) VALUES (?, ?, ?)";

        KeyHolder keyHolder = new GeneratedKeyHolder(); // for RETURNING Generated key from DB thx to update
        jdbcTemplate.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                // if Customer is set return generated id
                PreparedStatement ps = con.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
                ps.setString(1, merchant.getName());
                ps.setString(2, merchant.getEmail());
                ps.setString(3, merchant.getAddress());
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

    public List<Merchant> getAll() {
        final String sql = "SELECT * FROM merchant";
        return jdbcTemplate.query(sql, merchantRawMapper);
    }


}

/* TODO
     public void update(int id, UpdateMerchantRequest request) {
         final String sql = "UPDATE merchant SET name=?, email=?, address=? WHERE id = ?";  //id = "+id;
         jdbcTemplate.update(sql, request.getName(), request.getEmail(), request.getAddress(), id);
     }

     public void delete(int id) {
         final String sql = "DELETE FROM merchant WHERE id = ?";
         jdbcTemplate.update(sql, id);
     }
*/
