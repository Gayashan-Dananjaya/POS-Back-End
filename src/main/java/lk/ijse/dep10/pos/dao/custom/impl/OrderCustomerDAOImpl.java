package lk.ijse.dep10.pos.dao.custom.impl;

import lk.ijse.dep10.pos.dao.CrudDAOImpl;
import lk.ijse.dep10.pos.dao.custom.OrderCustomerDAO;
import lk.ijse.dep10.pos.entity.OrderCustomer;
import org.springframework.stereotype.Repository;

@Repository
public class OrderCustomerDAOImpl extends CrudDAOImpl<OrderCustomer, Integer> implements OrderCustomerDAO {

    @Override
    public boolean existsOrderByCustomerId(int customerId) throws Exception {
        return !entityManager.createQuery("FROM OrderCustomer oc WHERE oc.customer.id = :customerId", OrderCustomer.class).setParameter("customerId", customerId).getResultList().isEmpty();
    }
}
