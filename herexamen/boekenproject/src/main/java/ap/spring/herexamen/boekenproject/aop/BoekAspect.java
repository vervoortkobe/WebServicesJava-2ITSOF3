package ap.spring.herexamen.boekenproject.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class BoekAspect {

    @Before("execution(public String createBook(..)) ")
    public void printCreate(JoinPoint joinPoint) throws Throwable {

        Object[] args = joinPoint.getArgs();
        String title = (String) args[0];
        String author = (String) args[1];

        System.out.println();
        System.out.println("NIEUW BOEK AANGEMAAKT: " + title + ", " + author);
        System.out.println();
    }

    @Before("execution(public String deleteBook(..)) ")
    public void printDelete(JoinPoint joinPoint) throws Throwable {

        Object[] args = joinPoint.getArgs();
        String title = (String) args[0];

        System.out.println();
        System.out.println("BOEK VERWIJDERD: " + title);
        System.out.println();
    }
}