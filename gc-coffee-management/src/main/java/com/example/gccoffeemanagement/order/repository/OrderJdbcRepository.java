package com.example.gccoffeemanagement.order.repository;

import com.example.gccoffeemanagement.order.domain.Order;
import com.example.gccoffeemanagement.order.domain.OrderProduct;
import com.example.gccoffeemanagement.order.domain.OrderStatus;
import com.example.gccoffeemanagement.order.entity.OrderEntity;
import com.example.gccoffeemanagement.order.entity.OrderProductEntity;
import com.example.gccoffeemanagement.common.exception.ErrorCode;
import com.example.gccoffeemanagement.common.exception.NotExecuteException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Repository
public class OrderJdbcRepository implements OrderRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final String SAVE_ORDER_SQL = "INSERT INTO orders(email, address, postcode, order_status) VALUES (:email, :address, :postcode, :orderStatus);";
    private final String SAVE_ORDER_PRODUCT_SQL = "INSERT INTO order_product(order_id, product_id, quantity) VALUES (:orderId, :productId, :quantity);";
    private final String SELECT_LAST_INSERT_ORDER_SQL = "SELECT * FROM orders ORDER BY id DESC LIMIT 1";

    public OrderJdbcRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void save(Order order) {
        if (order == null) {
            throw new IllegalStateException(ErrorCode.NULL_INPUT_OBJECT.getMessage());
        }

        int insertOrderNum = jdbcTemplate.update(SAVE_ORDER_SQL, toOrderParamMap(OrderEntity.from(order)));
        if (insertOrderNum != 1) {
            throw new NotExecuteException();
        }

        OrderEntity orderEntity = jdbcTemplate.queryForObject(SELECT_LAST_INSERT_ORDER_SQL, Collections.emptyMap(), orderEntityRowMapper);
        for (OrderProduct orderProduct : order.getOrderProducts()) {
            int insertOrderProductNum = jdbcTemplate.update(SAVE_ORDER_PRODUCT_SQL, toOrderProductParamMap(orderEntity.getId(), orderEntity.getCreatedAt(), orderEntity.getUpdatedAt(), OrderProductEntity.from(orderProduct)));
            if (insertOrderProductNum != 1) {
                throw new NotExecuteException();
            }
        }
    }

    private static final RowMapper<OrderEntity> orderEntityRowMapper = (resultSet, i) -> {
        long id = resultSet.getLong("id");
        String email = resultSet.getString("email");
        String address = resultSet.getString("address");
        String postcode = resultSet.getString("postcode");
        OrderStatus orderStatus = OrderStatus.valueOf(resultSet.getString("order_status"));
        LocalDateTime createdAt = resultSet.getTimestamp("created_at").toLocalDateTime();
        LocalDateTime updatedAt = resultSet.getTimestamp("updated_at").toLocalDateTime();
        return OrderEntity.of(id, email, address, postcode, orderStatus, createdAt, updatedAt);
    };

    private Map<String, Object> toOrderParamMap(OrderEntity orderEntity) {
        HashMap<String, Object> paramMap = new HashMap<>();
        paramMap.put("email", orderEntity.getEmail());
        paramMap.put("address", orderEntity.getAddress());
        paramMap.put("postcode", orderEntity.getPostcode());
        paramMap.put("orderStatus", orderEntity.getOrderStatus().toString());
        return paramMap;
    }

    private Map<String, Object> toOrderProductParamMap(long orderId, LocalDateTime createdAt, LocalDateTime updatedAt, OrderProductEntity orderProductEntity) {
        HashMap<String, Object> paramMap = new HashMap<>();
        paramMap.put("orderId", orderId);
        paramMap.put("productId", orderProductEntity.getProductId());
        paramMap.put("quantity", orderProductEntity.getQuantity());
        paramMap.put("createdAt", createdAt);
        paramMap.put("updatedAt", updatedAt);
        return paramMap;
    }
}
