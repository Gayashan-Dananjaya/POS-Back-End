package lk.ijse.dep10.pos.dao.custom.impl;

import lk.ijse.dep10.pos.dao.CrudDAOImpl;
import lk.ijse.dep10.pos.dao.custom.OrderDetailDAO;
import lk.ijse.dep10.pos.entity.OrderDetail;
import lk.ijse.dep10.pos.entity.OrderDetailPK;
import org.springframework.stereotype.Repository;

@Repository
public class OrderDetailDAOImpl extends CrudDAOImpl<OrderDetail, OrderDetailPK> implements OrderDetailDAO {

    @Override
    public boolean existsOrderDetailByItemCode(String itemCode) throws Exception {
        return !entityManager.createQuery("FROM OrderDetail od WHERE od.item.code = :code", OrderDetail.class).setParameter("code", itemCode).getResultList().isEmpty();
    }
}
