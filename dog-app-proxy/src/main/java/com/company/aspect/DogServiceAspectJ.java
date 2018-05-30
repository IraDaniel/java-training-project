package com.company.aspect;

import com.company.controller.DogController;
import org.apache.log4j.LogManager;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

@Aspect
public class DogServiceAspectJ {

    private static final org.apache.log4j.Logger LOGGER = LogManager.getLogger(DogController.class);

    @Pointcut("within(@com.company.dao *)")
    public void callAt(){
    }

    @Around("callAt()")
    public void log(ProceedingJoinPoint pjp ){
        LOGGER.info("Call dao method");
        try{
            pjp.proceed();
        }catch (Throwable throwable){
            //skip
        }
        LOGGER.info("The end");
    }


}
