package springtutorial.aspect;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import springtutorial.discountStrategy.DiscountStrategy;
import springtutorial.model.Ticket;
import springtutorial.model.User;
@Component
@Aspect
public class LuckyWinnerAspect {
	public static Map<String, Integer> eventAccessedTimes = new HashMap<>();
	public static boolean luckyWinner = false;
	
	@Around("execution(* springtutorial.service.impl.BookingServiceImpl.bookTicket"
			+ "(..)) && args(user,ticket))")
	public Object checkLuckyWinner(ProceedingJoinPoint pjp, User user, Ticket ticket) throws Throwable{
		if(user != null || ticket != null) return pjp.proceed(pjp.getArgs());
		Random randomGenerator = new Random();
		int randomInt = randomGenerator.nextInt(4);
		if(randomInt == 1){
			luckyWinner = true;
			return true;
		}else{
			luckyWinner = false;
			return false;
		}
	}
	
}
