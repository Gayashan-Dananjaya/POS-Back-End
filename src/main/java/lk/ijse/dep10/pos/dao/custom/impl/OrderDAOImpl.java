package lk.ijse.dep10.pos.dao.custom.impl;

import lk.ijse.dep10.pos.dao.CrudDAOImpl;
import lk.ijse.dep10.pos.dao.custom.OrderDAO;
import lk.ijse.dep10.pos.entity.Order;
import org.springframework.stereotype.Repository;

@Repository
public class OrderDAOImpl extends CrudDAOImpl<Order, Integer> implements OrderDAO {

}
