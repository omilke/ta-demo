package de.omilke.ta.demo;

import de.omilke.ta.demo.exceptions.MyCheckedApplicationExceptionWithRollback;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.Schedule;
import javax.ejb.SessionContext;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.ejb.Timeout;
import javax.ejb.TimerConfig;
import javax.ejb.TimerService;
import javax.inject.Inject;

/**
 *
 * @author Oliver Milke
 */
@Singleton
@Startup
public class StarterBean {

    @Inject
    private BatService batService;

    @Inject
    private CdiBean cdiBean;

    @Inject
    private MyEntityService myEntityService;

    @Resource
    private TimerService timerService;

    @Resource
    private SessionContext context;

    @PostConstruct
    public void init() {

        System.out.println("> init");

        /*
         * TODO uncomment one of the following to demonstate the behaviour 
         */
        //checkNoRollbackCheckedExceptionInternally();
        //checkNoRollbackCheckedException();
        //checkNoRollbackUncheckedException();
        //checkRollbackCheckedException();
        //checkRollbackRuntimeException();
        //checkNoRollbackCheckedExceptionCdi();

        checkNoRollbackCheckExceptionInNewTransaction();
    }

    private void checkNoRollbackCheckedException() throws IllegalStateException {

        try {
            this.batService.throwsCheckedExceptionWithoutRollback();
        } catch (Exception ex) {
            System.out.println(">> caught:" + ex);
        }

        //no rollback, since the thrown exception is a checked exception without @ApplicationException(rollback = true)
        printRollback();
    }

    private void checkNoRollbackUncheckedException() {
        try {
            this.batService.throwsUncheckedExceptionWithoutRollback();
        } catch (Exception ex) {
            System.out.println(">> caught:" + ex);
        }

        //no rollback, since the thrown exception is an unchecked exception with @ApplicationException(rollback = false)
        printRollback();
    }

    private void checkRollbackCheckedException() throws IllegalStateException {

        try {
            this.batService.throwsCheckedExceptionWithRollback();
        } catch (Exception ex) {
            System.out.println(">> caught:" + ex);
        }

        //rollback, since the thrown exception is a checked exception with @ApplicationException(rollback = true)
        printRollback();
    }

    private void checkRollbackRuntimeException() throws IllegalStateException {

        try {
            this.batService.throwsRuntimeException();
        } catch (Exception ex) {
            System.out.println(">> caught:" + ex);
        }

        //rollback, since the thrown exception is an unchecked exception (which implies @ApplicationException(rollback = true))
        printRollback();
    }

    private void checkNoRollbackCheckedExceptionCdi() throws IllegalStateException {

        try {
            this.batService.doIt();
            this.cdiBean.throwsCheckedExceptionWithRollback();
        } catch (Exception ex) {
            System.out.println(">> caught:" + ex);
        }

        //no rollback, since the exception thrown is not crossing @EJB proxy
        printRollback();
    }

    private void checkNoRollbackCheckedExceptionInternally() throws IllegalStateException {

        try {
            this.throwsCheckedExceptionWithRollbackInternally();
        } catch (Exception ex) {
            System.out.println(">> caught:" + ex);
        }

        //no rollback, since the exception thrown is not crossing @EJB proxy
        printRollback();
    }

    private void checkNoRollbackCheckExceptionInNewTransaction() {

        this.myEntityService.countEntities();

        try {

            this.myEntityService.insertOneWithNewTransactionAndException();
        } catch (Exception ex) {
            System.out.println(">> caught:" + ex);
        }

        this.myEntityService.countEntities();

        //no rollback, since the exception thrown is not crossing @EJB proxy
        printRollback();

    }

    private void printRollback() throws IllegalStateException {

        System.out.println("rollback? " + context.getRollbackOnly());
    }

    private void throwsCheckedExceptionWithRollbackInternally() throws MyCheckedApplicationExceptionWithRollback {

        throw new MyCheckedApplicationExceptionWithRollback("internally thrown");
    }

    //TODO uncomment for demonstration of behaviour
    @Schedule(hour = "*", minute = "*", second = "*/15")
    public void scheduled() throws Exception {

        printTime("scheduled @ ");

        this.myEntityService.countEntities();
        this.myEntityService.insertOne();
        this.myEntityService.countEntities();

        this.timerService.createSingleActionTimer(1, new TimerConfig());

        //TODO unccomment one of the following throw statements to demonstrate the behaviour
        //no rollback, timer expunges however (retried after 5 sec.)
        //timedOut() will therefore be executed
        throw new Exception("my bad :(");
        
        //rollback with timer being expunged
        //timedOut() will therefore not be executed
        //throw new MyApplicationException("my bad :(");
        //throw new RuntimeException("my bad :(");
    }

    @Timeout
    public void timeOut() {

        printTime("timeOut @");
    }

    private void printTime(String identifier) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy MMM dd HH:mm:ss");
        Calendar calendar = Calendar.getInstance();

        System.out.println(identifier + sdf.format(calendar.getTime()));
    }

}
