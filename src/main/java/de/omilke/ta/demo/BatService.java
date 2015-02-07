package de.omilke.ta.demo;

import de.omilke.ta.demo.exceptions.MyUncheckedExceptionWithoutRollback;
import de.omilke.ta.demo.exceptions.MyCheckedApplicationExceptionWithRollback;
import de.omilke.ta.demo.entity.MyEntity;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Oliver Milke
 */
@Stateless
public class BatService {

    @PersistenceContext
    EntityManager em;

    public void throwsRuntimeException() {

        this.doIt();

        throw new RuntimeException("RuntimeException from the service for bats.");
    }

    public void throwsCheckedExceptionWithoutRollback() throws Exception {

        this.doIt();

        throw new Exception("Standard Exception from the service for bats.");
    }

    public void throwsCheckedExceptionWithRollback() throws Exception {

        this.doIt();

        throw new MyCheckedApplicationExceptionWithRollback("ApplicationException with Rollback from the service for bats.");
    }

    public void throwsUncheckedExceptionWithoutRollback() {

        this.doIt();

        throw new MyUncheckedExceptionWithoutRollback("Unchecked ApplicationException without Rollback from the service for bats.");
    }

    public void doIt() {

        //force DB access, in order to generate a transaction
        this.em.find(MyEntity.class, 1);

    }

}
