package lk.ijse.dep10.pos.dao.custom.impl;

import lk.ijse.dep10.pos.dao.CrudDAOImpl;
import lk.ijse.dep10.pos.dao.custom.ItemDAO;
import lk.ijse.dep10.pos.entity.Item;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ItemDAOImpl extends CrudDAOImpl<Item, String> implements ItemDAO {

    @Override
    public List<Item> findItems(String query) throws Exception {
        query = "%" + query + "%";
        return entityManager.createQuery("SELECT i FROM Item i WHERE i.code LIKE ?1 OR i.description LIKE ?2", Item.class).setParameter(1, query).setParameter(2, query).getResultList();
    }
}
