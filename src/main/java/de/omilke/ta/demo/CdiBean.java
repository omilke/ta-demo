package de.omilke.ta.demo;

import de.omilke.ta.demo.exceptions.MyCheckedApplicationExceptionWithRollback;
import javax.enterprise.context.Dependent;

/**
 * Simple Bean for demonstration.
 *
 * @author Oliver Milke
 */
@Dependent
public class CdiBean {

    public void throwsCheckedExceptionWithRollback() throws Exception {

        throw new MyCheckedApplicationExceptionWithRollback("ApplicationException with Rollback from the a CDI bean");
    }

}
