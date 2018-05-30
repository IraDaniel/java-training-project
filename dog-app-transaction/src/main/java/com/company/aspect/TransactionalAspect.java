package com.company.aspect;

import com.company.exception.DogSqlException;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

@Aspect
@EnableAspectJAutoProxy(proxyTargetClass=true)
public class TransactionalAspect {

    private final Logger logger = LoggerFactory.getLogger(getClass());
    private final PlatformTransactionManager transactionManager;

    public TransactionalAspect(PlatformTransactionManager transactionManager) {
        this.transactionManager = transactionManager;
    }

    @Pointcut("@annotation(TransactionalMarker)")
    public void executeWithTransaction() {
    }

    @Around("executeWithTransaction()")
    public Object invoke(ProceedingJoinPoint jp) throws Throwable {
        logger.info("Enter to aspect");
        DefaultTransactionDefinition definition = new DefaultTransactionDefinition(TransactionDefinition.PROPAGATION_REQUIRED);
        TransactionStatus defaultStatus = transactionManager.getTransaction(definition);
        Object result;
        try {
            result = jp.proceed(jp.getArgs());
            transactionManager.commit(defaultStatus);
        } catch (DogSqlException e) {
            transactionManager.rollback(defaultStatus);
            throw e;
        }

        return result;
    }
}
