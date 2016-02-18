package springtutorial.test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Date;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.aop.aspectj.annotation.AspectJProxyFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import springtutorial.aspect.CounterAspect;
import springtutorial.aspect.DiscountAspect;
import springtutorial.dao.impl.EventDaoImpl;
import springtutorial.dao.impl.UserDaoImpl;
import springtutorial.exception.EventNotFound;
import springtutorial.main.App;
import springtutorial.model.Event;
import springtutorial.model.Ticket;
import springtutorial.model.User;
import springtutorial.service.BookingService;
import springtutorial.service.EventService;
import springtutorial.service.impl.EventServiceImpl;

public class CounterAspectTest extends BaseServiceTest{
	@Before
	public void clearEventAccessedTimes(){
		CounterAspect.eventAccessedTimes.clear();
	}

	
	@Test
	public void testAspectAcessByName() throws EventNotFound{
		eventService.getByName("Glasgou");
		eventService.getByName("Glasgou");
		eventService.getByName("Walk around");
		assertTrue("Aspect access by name count is not correct",CounterAspect.eventAccessedTimes.size() == 2);
		assertTrue("Counter is not increased, when accessing by name few times",CounterAspect.eventAccessedTimes.get("Glasgou") == 2);
	}
	
	@Before
	public void clearEventCountPriceQueried(){
		CounterAspect.eventCountPriceQueried.clear();
	}
	@Test
	public void testCountPriceQueried() throws EventNotFound{
		try{
			bookingService.getTicketPrice(new Event(11, "CustomEvent", 1f,null,null,null,null), null, null, null);
		}catch(NullPointerException e ){
			//expected NullPointerException
		}
		try{
			bookingService.getTicketPrice(new Event(12, "Film evening", 1f,null,null,null,null), null, null, null);
		}catch(NullPointerException e){
			//expected NullPointerException
		}
		try{
			bookingService.getTicketPrice(new Event(12, "Film evening", 1f,null,null,null,null), null, null, null);
		}catch(NullPointerException e){
			//expected NullPointerException
		}
		assertTrue("Aspect test count price is not correct",CounterAspect.eventCountPriceQueried.size() == 2);
		assertTrue("Counter is not increased, when accessing by name few times",CounterAspect.eventCountPriceQueried.get("Film evening") == 2);
		
	}
	@Before
	public void clearEventCountBookedTicket(){
		CounterAspect.eventCountBookedTicket.clear();
	}
	
	@Test
	public void testCountHowManyTicketsWereBooked() throws EventNotFound{
		User userVitalik = UserDaoImpl.users.get(0);
		Event testEvent = EventDaoImpl.events.get(0);
		Date eventDate = testEvent.getAuditoriumAndDate().keySet().iterator().next();
		Ticket ticket = new Ticket(55, testEvent, 4, userVitalik, eventDate);
		
		bookingService.bookTicket(userVitalik, ticket);
		bookingService.bookTicket(userVitalik, ticket);
		bookingService.bookTicket(userVitalik, ticket);
		
		assertTrue("Aspect count how many tickets were booked is not correct",CounterAspect.eventCountBookedTicket.size() == 1);
		assertTrue("Counter is not increased, when accessing by name few times",CounterAspect.eventCountBookedTicket.get(testEvent.getName()) == 3);
		
	}
	
}
