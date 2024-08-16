package edu.ap.spring.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.*;

@Component
@Aspect
public class MovieAspect {

    @Before("execution(public String delete(..)) ")
    public void printDelete(JoinPoint joinPoint) throws Throwable {

        Object[] args = joinPoint.getArgs();
        String name = (String) args[0];

        System.out.println();
        System.out.println("DELETED : " + name);
        System.out.println();
    }
}

