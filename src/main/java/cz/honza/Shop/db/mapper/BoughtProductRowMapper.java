package cz.honza.Shop.db.mapper;

import cz.honza.Shop.domain.BoughtProduct;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class BoughtProductRowMapper implements RowMapper<BoughtProduct> {   // parametrized Rowmapper

    @Override
    public BoughtProduct mapRow(ResultSet rs, int rowNum) throws SQLException { // rs "touch" columns of DB items because RowMapper<BoughtProduct>
        BoughtProduct boughtProduct = new BoughtProduct(); // do not use parameters in constructor but setters on next line
        boughtProduct.setProductId(rs.getInt("product_id"));    // name of column
        boughtProduct.setCustomerId(rs.getInt("customer_id"));
        boughtProduct.setQuantity(rs.getInt("quantity"));
        boughtProduct.setBoughtAt(rs.getTimestamp("bought_at"));
        return boughtProduct;
    }
}
