package springtutorial.test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import springtutorial.dao.impl.AuditoriumDaoImpl;
import springtutorial.dao.impl.EventDaoImpl;
import springtutorial.dao.impl.UserDaoImpl;
import springtutorial.model.Auditorium;
import springtutorial.model.Event;
import springtutorial.model.Ticket;
import springtutorial.model.User;
import springtutorial.persistance.Rating;
import springtutorial.service.AuditoriumService;
import springtutorial.service.BookingService;
import springtutorial.service.EventService;
import springtutorial.service.UserService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring-test/spring-context.xml"})
public abstract class BaseServiceTest {
	protected static StringBuilder builder = new StringBuilder();
	protected static SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd");
	
	@Autowired
	protected UserService userService;
	@Autowired
	protected AuditoriumService auditoriumService;
	@Autowired
	protected BookingService bookingService;
	@Autowired
	protected EventService eventService;
	@Autowired
	protected ApplicationContext context;
	
	protected User createUser(Integer userId, String userEmail, String userName, Date birthday) throws ParseException {
		Map<Date, Auditorium> dateAndAudetorium = new HashMap<>();
		Auditorium testAuditorium = auditoriumService.getAuditoriums().get(0);
		dateAndAudetorium.put(dateformat.parse("2016-02-10"), testAuditorium);
		Date eventDate = dateformat.parse("2016-02-10");
		Set<Ticket> purchasedTicket = new HashSet<>();
		purchasedTicket.add(new Ticket(33, null, 5, null, eventDate));

		String eventName = "Party";
		Event testEvent = new Event(33, eventName, 122.0f, Rating.HIGH, 2, dateAndAudetorium, purchasedTicket);
		testEvent.getPurchasedTickets().iterator().next().setEvent(testEvent);

		List<Ticket> userBookedTicket = new ArrayList<>();
		userBookedTicket.add(new Ticket(30, testEvent, 10, null, dateformat.parse("2016-02-10")));

		User userBobko = new User(userId, userEmail, userName, userBookedTicket, birthday);
		userBobko.getBookedTickets().get(0).setUser(userBobko);
		userBobko.getBookedTickets().get(0).getEvent().getPurchasedTickets().iterator().next().setUser(userBobko);
		return userBobko;

	}
	
	
}
