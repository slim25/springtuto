package springtutorial.test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.aop.aspectj.annotation.AspectJProxyFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import springtutorial.aspect.CounterAspect;
import springtutorial.dao.impl.EventDaoImpl;
import springtutorial.exception.EventNotFound;
import springtutorial.main.App;
import springtutorial.model.Event;
import springtutorial.model.Ticket;
import springtutorial.model.User;
import springtutorial.service.BookingService;
import springtutorial.service.EventService;
import springtutorial.service.impl.EventServiceImpl;

public class CounterAspectTest {
	private static ApplicationContext ctx;
	private static App app;
	private static EventService proxyEventService;
	private static BookingService proxyBookingService;
	
	@BeforeClass
	public static void beforeClass(){
		ctx = new ClassPathXmlApplicationContext("spring/spring-context.xml");
		app = ctx.getBean(springtutorial.main.App.class);
		EventService targetEvent = app.eventService;
		BookingService bookingTarget = app.bookingService;
		
		AspectJProxyFactory factory = new AspectJProxyFactory(targetEvent);
		CounterAspect aspect = new CounterAspect();
		factory.addAspect(aspect);
		proxyEventService = factory.getProxy();
		
		AspectJProxyFactory factory2 = new AspectJProxyFactory(bookingTarget);
		factory2.addAspect(aspect);
		proxyBookingService = factory2.getProxy();
	}
	
	@Test
	public void testAspectAcessByName() throws EventNotFound{
		proxyEventService.getByName("Glasgou");
		proxyEventService.getByName("Glasgou");
		proxyEventService.getByName("Walk around");
		assertTrue("Aspect access by name count is not correct",CounterAspect.eventAccessedTimes.size() == 2);
		assertTrue("Counter is not increased, when accessing by name few times",CounterAspect.eventAccessedTimes.get("Glasgou") == 2);
	}
	
	@Test
	public void testCountPriceQueried() throws EventNotFound{
		try{
			proxyBookingService.getTicketPrice(new Event(11, "CustomEvent", 1f,null,null,null,null), null, null, null);
		}catch(NullPointerException e ){
			//expected NullPointerException
		}
		try{
			proxyBookingService.getTicketPrice(new Event(12, "Film evening", 1f,null,null,null,null), null, null, null);
		}catch(NullPointerException e){
			//expected NullPointerException
		}
		try{
			proxyBookingService.getTicketPrice(new Event(12, "Film evening", 1f,null,null,null,null), null, null, null);
		}catch(NullPointerException e){
			//expected NullPointerException
		}
		assertTrue("Aspect access by name count is not correct",CounterAspect.eventCountPriceQueried.size() == 2);
		assertTrue("Counter is not increased, when accessing by name few times",CounterAspect.eventCountPriceQueried.get("Film evening") == 2);
		
	}
	
	@Test
	public void testCountHowManyTicketsWereBooked() throws EventNotFound{
//		app.bookingService.bookTicket(User user, Ticket ticket);
//		app.bookingService.bookTicket(User user, Ticket ticket);
//		app.bookingService.bookTicket(User user, Ticket ticket);
		
//		assertTrue("Aspect access by name count is not correct",CounterAspect.eventCountPriceQueried.size() == 2);
//		assertTrue("Counter is not increased, when accessing by name few times",CounterAspect.eventCountPriceQueried.get("Film evening") == 2);
		
	}
	
}
