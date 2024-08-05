package br.com.alyson.apimanagertask.aspect;

import br.com.alyson.apimanagertask.domain.exception.MyCustomTimeoutException;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.hibernate.QueryTimeoutException;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class TransactionTimeoutAspect {

    @AfterThrowing(pointcut = "execution(* br.com.alyson.apimanagertask..*(..)) && @annotation(org.springframework.transaction.annotation.Transactional)", throwing = "ex")
    public void handleQueryTimeoutException(QueryTimeoutException ex) throws Throwable {
        throw new MyCustomTimeoutException("The operation timed out due to a lock on the table");
    }
}
