package lk.ijse.dep10.pos.dao.custom.impl;

import lk.ijse.dep10.pos.dao.CrudDAOImpl;
import lk.ijse.dep10.pos.dao.custom.CustomerDAO;
import lk.ijse.dep10.pos.entity.Customer;
import org.springframework.stereotype.Repository;

import javax.persistence.NoResultException;
import java.util.List;
import java.util.Optional;

@Repository
public class CustomerDAOImpl extends CrudDAOImpl<Customer, Integer> implements CustomerDAO {

    @Override
    public List<Customer> findCustomers(String query) throws Exception {
        query = "%" + query + "%";
        return entityManager.createQuery("SELECT c FROM Customer c WHERE str(c.id) LIKE ?1 OR c.name LIKE ?2 OR c.address LIKE ?3 OR c.contact LIKE ?4", Customer.class).setParameter(1, query).setParameter(2, query).setParameter(3, query).setParameter(4, query).getResultList();
    }

    @Override
    public Optional<Customer> findCustomerByIdOrContact(String idOrContact) throws Exception {
        try {
            return Optional.of(entityManager.createQuery("SELECT c FROM Customer c WHERE str(c.id) LIKE ?1 OR c.contact LIKE ?2", Customer.class).setParameter(1, idOrContact).setParameter(2, idOrContact).getSingleResult());
        }catch (NoResultException exp){
            return Optional.empty();
        }
    }

    @Override
    public boolean existsCustomerByContactAndNotId(String contact, Integer id) throws Exception {
        return !entityManager.createQuery("SELECT c FROM Customer c WHERE c.contact = :contact AND c.id != :id", Customer.class).setParameter("contact", contact).setParameter("id", id).getResultList().isEmpty();
    }

    @Override
    public boolean existsCustomerByContact(String contact) throws Exception {
        return !entityManager.createQuery("SELECT c FROM Customer c WHERE c.contact = :contact", Customer.class).setParameter("contact", contact).getResultList().isEmpty();
    }
}
