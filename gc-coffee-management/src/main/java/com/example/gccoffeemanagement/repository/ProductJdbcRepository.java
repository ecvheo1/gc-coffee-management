package com.example.gccoffeemanagement.repository;

import com.example.gccoffeemanagement.domain.Category;
import com.example.gccoffeemanagement.domain.Product;
import com.example.gccoffeemanagement.exception.ErrorCode;
import com.example.gccoffeemanagement.exception.NotExecuteException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.*;

@Repository
public class ProductJdbcRepository implements ProductRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final String FIND_ALL_SQL = "SELECT * FROM product";
    private final String FIND_BY_NAME_CATEGORY_PRICE_SQL = "SELECT * FROM product WHERE name = :name and category = :category and price = :price";
    private final String SAVE_SQL = "INSERT INTO product(name, category, price, description) VALUES(:name, :category, :price, :description)";

    public ProductJdbcRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Product> findAll() {
        return jdbcTemplate.query(FIND_ALL_SQL, productRowMapper);
    }

    @Override
    public Optional<Product> findByNameCategoryPrice(String name, Category category, int price) {
        List<Product> foundProducts = jdbcTemplate.query(
                FIND_BY_NAME_CATEGORY_PRICE_SQL,
                Map.of("name", name, "category", category.toString(), "price", price),
                productRowMapper);
        return foundProducts.isEmpty() ? Optional.empty() : Optional.of(foundProducts.get(0));
    }

    @Override
    public void save(Product product) {
        if (product == null) {
            throw new IllegalStateException(ErrorCode.NULL_INPUT_OBJECT.getMessage());
        }

        int insertNum = jdbcTemplate.update(SAVE_SQL, toParamMap(product));
        if (insertNum != 1) {
            throw new NotExecuteException();
        }
    }

    private static final RowMapper<Product> productRowMapper = (resultSet, i) -> {
        long productId = resultSet.getLong("id");
        String productName = resultSet.getString("name");
        Category category = Category.valueOf(resultSet.getString("category"));
        int price = resultSet.getInt("price");
        String description = resultSet.getString("description");
        LocalDateTime createdAt = resultSet.getTimestamp("created_at").toLocalDateTime();
        LocalDateTime updatedAt = resultSet.getTimestamp("updated_at").toLocalDateTime();
        return Product.entityOf(productId, productName, category, price, description, createdAt, updatedAt);
    };

    private Map<String, Object> toParamMap(Product product) {
        HashMap<String, Object> paramMap = new HashMap<>();
        paramMap.put("name", product.getName());
        paramMap.put("category", product.getCategory().toString());
        paramMap.put("price", product.getPrice());
        paramMap.put("description", product.getDescription());
        return paramMap;
    }
}
