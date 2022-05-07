package com.example.gccoffeemanagement.product.repository;

import com.example.gccoffeemanagement.product.domain.Category;
import com.example.gccoffeemanagement.product.domain.Product;
import com.example.gccoffeemanagement.product.entity.ProductEntity;
import com.example.gccoffeemanagement.common.exception.ErrorCode;
import com.example.gccoffeemanagement.common.exception.NotExecuteException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

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
        return jdbcTemplate.query(FIND_ALL_SQL, productEntityRowMapper)
                .stream()
                .map(ProductEntity::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Product> findByNameCategoryPrice(String name, Category category, int price) {
        List<Product> foundProducts = jdbcTemplate.query(
                        FIND_BY_NAME_CATEGORY_PRICE_SQL,
                        Map.of("name", name, "category", category.toString(), "price", price),
                        productEntityRowMapper)
                .stream()
                .map(ProductEntity::toDomain)
                .collect(Collectors.toList());
        return foundProducts.isEmpty() ? Optional.empty() : Optional.of(foundProducts.get(0));
    }

    @Override
    public void save(Product product) {
        if (product == null) {
            throw new IllegalStateException(ErrorCode.NULL_INPUT_OBJECT.getMessage());
        }

        int insertNum = jdbcTemplate.update(SAVE_SQL, toParamMap(ProductEntity.from(product)));
        if (insertNum != 1) {
            throw new NotExecuteException();
        }
    }

    private static final RowMapper<ProductEntity> productEntityRowMapper = (resultSet, i) -> {
        long id = resultSet.getLong("id");
        String name = resultSet.getString("name");
        Category category = Category.valueOf(resultSet.getString("category"));
        int price = resultSet.getInt("price");
        String description = resultSet.getString("description");
        LocalDateTime createdAt = resultSet.getTimestamp("created_at").toLocalDateTime();
        LocalDateTime updatedAt = resultSet.getTimestamp("updated_at").toLocalDateTime();
        return ProductEntity.of(id, name, category, price, description, createdAt, updatedAt);
    };

    private Map<String, Object> toParamMap(ProductEntity productEntity) {
        HashMap<String, Object> paramMap = new HashMap<>();
        paramMap.put("name", productEntity.getName());
        paramMap.put("category", productEntity.getCategory().toString());
        paramMap.put("price", productEntity.getPrice());
        paramMap.put("description", productEntity.getDescription());
        return paramMap;
    }
}
