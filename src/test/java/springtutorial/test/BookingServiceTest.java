package springtutorial.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import springtutorial.dao.EventDao;
import springtutorial.dao.UserDao;
import springtutorial.dao.impl.EventDaoImpl;
import springtutorial.dao.impl.UserDaoImpl;
import springtutorial.exception.EventNotFound;
import springtutorial.exception.NotExistSuchAuditorium;
import springtutorial.exception.UserNotFound;
import springtutorial.model.Auditorium;
import springtutorial.model.Event;
import springtutorial.model.Ticket;
import springtutorial.model.User;
import springtutorial.persistance.Rating;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class BookingServiceTest extends BaseServiceTest{
	private Integer testUserId = 25;
	private String testUserEmail = "pablo@ee.ee";
	private String testUserName = "Pablo";
	private String eventName = "Disco";
	private static Event testEvent;
	private static User testUser;
	private static Date eventDate;
	
	
	@Test
	public void AtestGetTicketPrice() throws NotExistSuchAuditorium, ParseException{
		//create user
		User user = createUser(testUserId, testUserEmail, testUserName, dateformat.parse("1992-02-08"));
		BookingServiceTest.testUser = user;
			//create event
		Map<Date, Auditorium> dateAndAudetorium = new HashMap<>();
		List<Integer> vipSeats = new ArrayList<>();
		vipSeats.add(19);
		vipSeats.add(6);
		vipSeats.add(7);
		Auditorium testAuditorium = new Auditorium(40, "Green", 120, vipSeats);
		
		dateAndAudetorium.put(dateformat.parse("2016-02-08"), testAuditorium);
		Set<Ticket> purchasedTicket = new HashSet<>();
		eventDate = dateformat.parse("2016-02-08");
		purchasedTicket.add(new Ticket(33, null, 5, user, eventDate));
		Event testEvent = new Event(33, eventName, 122.0f, Rating.HIGH, 2, dateAndAudetorium, purchasedTicket);
		testEvent.getPurchasedTickets().iterator().next().setEvent(testEvent);
		BookingServiceTest.testEvent = testEvent;
		
		Float templatePrice = (122.0f * 1.2f * 2f)*0.95f;
		templatePrice = new BigDecimal(templatePrice).setScale(4, RoundingMode.UP).floatValue();
		
		Float ticketPrice = bookingService.getTicketPrice(testEvent, eventDate, 7, user);
		ticketPrice = new BigDecimal(ticketPrice).setScale(4, RoundingMode.UP).floatValue();
		System.out.println("get ticket price = " + ticketPrice +"; template price is = " + templatePrice);
		assertTrue("Received ticket price is not equals to template", ticketPrice.equals(templatePrice));

	}
	

	@Test
	public void BtestBookTicket() throws NotExistSuchAuditorium, ParseException, EventNotFound, UserNotFound{
		User userVitalik = userService.getAllUsers().get(0);
		Event testEvent = eventService.getAll().get(0);
		Date eventDate = testEvent.getAuditoriumAndDate().keySet().iterator().next();
		Ticket ticket = new Ticket(55, testEvent, 4, userVitalik, eventDate);
		boolean isBooked = bookingService.bookTicket(userVitalik, ticket);
		
		if(isBooked == false) fail("Booking service book ticket response = false;");
		boolean userContains = false;
		for(User user : userService.getAllUsers()){
			if(user.getId() == userVitalik.getId() && user.getBookedTickets().contains(ticket)){
				userContains = true;
			}
		}
		if(userContains == false) fail("Booked ticket not writed to user as booked ticket");
		
		boolean eventContains = false;
		for(Event event : eventService.getAll()){
			boolean ticketContains =false;
			for(Ticket purchasedTicket : event.getPurchasedTickets()){
				if(purchasedTicket.getDate().compareTo(ticket.getDate()) == 0
						&& purchasedTicket.getId().equals(ticket.getId())){
					ticketContains = true;
				}
			}
			if(event.getId() == testEvent.getId() && ticketContains){
				eventContains = true;
			}
		}
		if(eventContains == false) fail("Booked ticket not writed to event as booked ticket");
	}
	
	@Test
	public void CtestGetPurchasedTicketForEvent() throws NotExistSuchAuditorium, ParseException, UserNotFound, EventNotFound{
		Event testEvent = eventService.getAll().get(0);
		Date eventDate = dateformat.parse("2016-02-09");
		Set<Ticket> purchasedTickets = bookingService.getPurchasedTicketsForEvent(testEvent, eventDate);
		
		assertTrue("Size of purchased tickets for some date is not equals with template size", purchasedTickets.size() == 1);
		
	}
}
