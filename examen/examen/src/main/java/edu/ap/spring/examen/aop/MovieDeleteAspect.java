package edu.ap.spring.examen.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

/*
 * 6/ Print de naam van elke film die verwijderd wordt (zie punt 4) uit op de console via een aop Aspect.  
 * Je kiest zelf welk type van Aspect (4 punten).
 */
@Component
@Aspect
public class MovieDeleteAspect {
    @Around("@annotation(edu.ap.spring.examen.aop.Interceptable) && execution(public String delete(..)) ")
    public void printDeleted(ProceedingJoinPoint joinPoint) throws Throwable {

        Object[] args = joinPoint.getArgs();
        var value = (float) args[1];

        System.out.println(value);
        joinPoint.proceed();
    }
}