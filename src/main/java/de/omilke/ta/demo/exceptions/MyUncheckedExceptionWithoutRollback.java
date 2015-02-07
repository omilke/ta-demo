package de.omilke.ta.demo.exceptions;

import javax.ejb.ApplicationException;

/**
 *
 * @author Oliver Milke
 */
@ApplicationException(rollback = false)
public class MyUncheckedExceptionWithoutRollback extends RuntimeException {

    public MyUncheckedExceptionWithoutRollback(String message) {
        super(message);
    }  
    
}
