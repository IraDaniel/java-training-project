package com.company.aspect;

import com.company.dao.JdbcConnectionHolder;
import com.company.exception.DogSqlException;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@Aspect
@EnableAspectJAutoProxy(proxyTargetClass=true)
public class TransactionalAspect {

    private final Logger logger = LoggerFactory.getLogger(getClass());
    private final JdbcConnectionHolder connectionHolder;

    public TransactionalAspect(JdbcConnectionHolder connectionHolder) {
        this.connectionHolder = connectionHolder;
    }

    @Pointcut("@annotation(TransactionalMarker)")
    public void executeWithTransaction() {
    }

    @Around("executeWithTransaction()")
    public Object invoke(ProceedingJoinPoint jp) throws Throwable {
        logger.info("Enter to aspect");
        Object result;
        try {
            result = jp.proceed(jp.getArgs());
            connectionHolder.commit();
        } catch (DogSqlException e) {
            connectionHolder.rollBack();
            throw e;
        } finally {
            connectionHolder.closeConnection();
        }
        return result;
    }
}
