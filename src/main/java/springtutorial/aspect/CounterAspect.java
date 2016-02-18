package springtutorial.aspect;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import springtutorial.model.Event;
import springtutorial.model.Ticket;
@Component
@Aspect
public class CounterAspect extends AbstractAspect{
	
	public static Map<String, Integer> eventAccessedTimes = new HashMap<>();
	public static Map<String, Integer> eventCountPriceQueried = new HashMap<>();
	public static Map<String, Integer> eventCountBookedTicket = new HashMap<>();
	
	@Before("execution(* springtutorial.service.impl.EventServiceImpl.getByName(String)) && args(eventName)")
	public void countAccessByName(String eventName){
		System.out.println("Event name = " + eventName);
		Integer eventCount = eventAccessedTimes.get(eventName);
		eventCount = checkForFirstTimeCall(eventCount);
		
		eventAccessedTimes.put(eventName, eventCount);
		
	}
	
	@Before("execution(* springtutorial.service.impl.BookingServiceImpl.getTicketPrice"
			+ "(..)) && args(event,..)")
	public void countPriceQueried(Event event){
		String eventName = event.getName();
		System.out.println("2.Event name = " + eventName);
		Integer eventCount = eventCountPriceQueried.get(eventName);
		eventCount = checkForFirstTimeCall(eventCount);
		
		eventCountPriceQueried.put(eventName, eventCount);
		System.out.println("RESULT = " + CounterAspect.eventCountPriceQueried);
	}
	
	@Before("execution(* springtutorial.service.impl.BookingServiceImpl.bookTicket"
			+ "(..)) && args(..,ticket)")
	public void countHowManyTicketsWereBooked(Ticket ticket){
		//ticket null - when need to call LuckyWinnerAspect
		if(ticket == null) return;
		String eventName = ticket.getEvent().getName();
		System.out.println("3.Event name = " + eventName);
		Integer eventCount = eventCountBookedTicket.get(eventName);
		eventCount = checkForFirstTimeCall(eventCount);
		
		eventCountBookedTicket.put(eventName, eventCount);
		System.out.println("RESULT Booked Ticket = " + CounterAspect.eventCountBookedTicket);
	}
	
}
