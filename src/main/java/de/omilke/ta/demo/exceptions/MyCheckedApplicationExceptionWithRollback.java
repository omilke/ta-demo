package de.omilke.ta.demo.exceptions;

import javax.ejb.ApplicationException;

/**
 *
 * @author Oliver Milke
 */
@ApplicationException(rollback = true)
public class MyCheckedApplicationExceptionWithRollback extends Exception {

    public MyCheckedApplicationExceptionWithRollback(String message) {
        super(message);
    }  
    
}
