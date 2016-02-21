package springtutorial.aspect;

import java.util.HashMap;
import java.util.Map;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import springtutorial.discountStrategy.DiscountStrategy;
import springtutorial.model.Ticket;
import springtutorial.model.User;

@Aspect
@Component
public class DiscountAspect extends AbstractAspect{
	public static Map<String, Integer> discountGivenTimes = new HashMap<>();
	public static Map<String, Map<String,Integer>> discountGivenTimesForUser = new HashMap<>();
	
	
	 @Around("execution(* springtutorial.service.impl.DiscountServiceImpl.getDiscountPercentage"
			+ "(..)) && args(user,..)")
	public Object countHowManyTimesDiscountWasGivenForUser(ProceedingJoinPoint pjp, User user) throws Throwable{
		
		
			Object[] args = pjp.getArgs();
			Object retVal = pjp.proceed(args);
			
			@SuppressWarnings("unchecked")
			Map.Entry<String,Float> returnValue = (DiscountStrategy.DiscountWithPercentage)retVal;
			String userName = user.getName();
			
			Map<String, Integer> dicountGivenCountForUser = discountGivenTimesForUser.get(userName);
			if(dicountGivenCountForUser == null) {
				dicountGivenCountForUser = new HashMap();
				dicountGivenCountForUser.put(returnValue.getKey(), 1);
				discountGivenTimesForUser.put(userName, dicountGivenCountForUser);
			}else{
				Integer dicountGivenCount = dicountGivenCountForUser.get(returnValue.getKey());
				dicountGivenCount = checkForFirstTimeCall(dicountGivenCount);
				dicountGivenCountForUser.put(returnValue.getKey(), dicountGivenCount);
			}
			
			Integer dicountGivenCount = discountGivenTimes.get(returnValue.getKey());
			dicountGivenCount = checkForFirstTimeCall(dicountGivenCount);
			
			discountGivenTimes.put(returnValue.getKey(), dicountGivenCount);
			
			return retVal;
		 
	}
	
}
