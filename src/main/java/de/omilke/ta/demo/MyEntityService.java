package de.omilke.ta.demo;

import de.omilke.ta.demo.entity.MyEntity;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author Oliver Milke
 */
@Stateless
public class MyEntityService {

    @PersistenceContext
    EntityManager em;

    public void clearInsertAndCount() {

        clear();

        insertOne();

        countEntities();

    }

    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public void insertOneWithNewTransactionAndException() throws Exception {

        this.insertOne();

        throw new Exception("my bad :(");
    }

    public void insertOne() {

        MyEntity newEntity = new MyEntity();
        this.em.persist(newEntity);
    }

    public void clear() {
        Query q = this.em.createQuery("DELETE FROM MyEntity");
        q.executeUpdate();
    }

    public void countEntities() {

        Query q2 = this.em.createQuery("SELECT COUNT(e) FROM MyEntity e");
        Object singleResult = q2.getSingleResult();

        System.out.println("Found Entities: " + singleResult);
    }
}
