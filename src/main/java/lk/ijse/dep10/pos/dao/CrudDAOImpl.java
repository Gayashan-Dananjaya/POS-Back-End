package lk.ijse.dep10.pos.dao;

import lk.ijse.dep10.pos.entity.SuperEntity;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceUnit;
import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;
import java.util.Optional;

public abstract class CrudDAOImpl<T extends SuperEntity, ID extends Serializable> implements CrudDAO<T, ID>{

//    @PersistenceUnit
//    private EntityManagerFactory entityManagerFactory;

    @PersistenceContext
    protected EntityManager entityManager;
    private final Class<T> entityClassObj;

    public CrudDAOImpl() {
        entityClassObj = (Class<T>) (((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0]);
    }

    @Override
    public long count() throws Exception {
        return entityManager.createQuery("SELECT count(e) FROM "+ entityClassObj.getName() + " e", Long.class).getSingleResult();
    }

    @Override
    public T save(T entity) throws Exception {
        entityManager.persist(entity);
        return entity;
    }

    @Override
    public void update(T entity) throws Exception {
        entityManager.merge(entity);
    }

    @Override
    public void deleteById(ID pk) throws Exception {
        entityManager.remove(entityManager.find(entityClassObj, pk));
    }

    @Override
    public Optional<T> findById(ID pk) throws Exception {
        return Optional.ofNullable(entityManager.find(entityClassObj, pk));
    }

    @Override
    public List<T> findAll() throws Exception {
        return entityManager.createQuery("SELECT e FROM " + entityClassObj.getName() + " e", entityClassObj).getResultList();
    }

    @Override
    public boolean existsById(ID pk) throws Exception {
        return findById(pk).isPresent();
    }
}
