package edu.ap.spring.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.*;

import edu.ap.spring.service.Wallet;
import edu.ap.spring.transaction.Transaction;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Component
@Aspect
public class BlockChainAspect {

    @Around("@annotation(edu.ap.spring.aop.Interceptable) && execution(public * sendFunds(..)) ")
    public Transaction checkBalance(ProceedingJoinPoint joinPoint) throws Throwable {

        Transaction result = null;
        Wallet wallet = (Wallet) joinPoint.getTarget();
        Object[] args = joinPoint.getArgs();
        float value = (float) args[1];
        float balance = wallet.getBalance();

        if(balance < value) {
			System.out.println("# Not Enough funds to send transaction. Transaction Discarded.");
		}
        else {
            result = (Transaction) joinPoint.proceed();
        }

        return result;
    }

    @Around("@annotation(edu.ap.spring.aop.Interceptable) && execution(public String getTransactionForm(..)) ")
    public String checkLogin(ProceedingJoinPoint joinPoint) throws Throwable {

        String template = "login";
        //boolean loggedIn = false;

        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
                .currentRequestAttributes())
                .getRequest();

        Cookie[] cookies = request.getCookies();
        if(cookies != null) {
            for (Cookie cookie : cookies) {
                if ("Authorisation".equals(cookie.getName())) {
                    if(cookie.getValue().equalsIgnoreCase("admin")) {
                        template = "transaction";
                    }
                }
            }
        }

        joinPoint.proceed();
        return template;
    }
}

