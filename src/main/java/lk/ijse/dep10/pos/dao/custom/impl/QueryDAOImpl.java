package lk.ijse.dep10.pos.dao.custom.impl;

import lk.ijse.dep10.pos.dao.custom.QueryDAO;
import lk.ijse.dep10.pos.dto.OrderDTO2;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Tuple;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class QueryDAOImpl implements QueryDAO {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<OrderDTO2> findOrdersByQuery(String query) throws Exception {
        String sql = "SELECT CONCAT('OD', LPAD(o.id, 3, 0))          as orderId, " +
                "       o.datetime as orderDate, " +
                "       CONCAT('C', LPAD(oc.customer_id, 3, 0)) as customerId, " +
                "       c.name as customerName, " +
                "       SUM(od.qty * od.unit_price)             as orderTotal " +
                "FROM `order` o " +
                "         LEFT OUTER JOIN order_customer oc ON o.id = oc.order_id " +
                "         LEFT OUTER JOIN customer c ON oc.customer_id = c.id " +
                "         INNER JOIN order_detail od ON o.id = od.order_id " +
                "WHERE CONCAT('OD', LPAD(o.id, 3, 0)) LIKE ?1 " +
                "   OR o.datetime LIKE ?2 " +
                "   OR CONCAT('C', LPAD(oc.customer_id, 3, 0)) LIKE ?3 " +
                "   OR c.name LIKE ?4 " +
                "GROUP BY o.id;";
        query = "%" + query + "%";
        List<Tuple> orderList = entityManager.createNativeQuery(sql, Tuple.class)
                .setParameter(1, query).setParameter(2, query).setParameter(3, query).setParameter(4, query)
                .getResultList();
        return orderList.stream().map(tuple -> new OrderDTO2(
                tuple.get("orderId", String.class),
                tuple.get("orderDate", Timestamp.class).toLocalDateTime(),
                tuple.get("customerId", String.class),
                tuple.get("customerName", String.class),
                tuple.get("orderTotal", BigDecimal.class)
        )).peek(o -> {
            if (o.getCustomerName() == null) o.setCustomerId(null);
        }).collect(Collectors.toList());
    }
}
