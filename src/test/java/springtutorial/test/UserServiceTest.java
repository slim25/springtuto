package springtutorial.test;

import static org.junit.Assert.*;

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
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import springtutorial.dao.impl.UserDaoImpl;
import springtutorial.exception.UserNotFound;
import springtutorial.model.Auditorium;
import springtutorial.model.Event;
import springtutorial.model.Ticket;
import springtutorial.model.User;
import springtutorial.persistance.Rating;
import org.junit.runners.MethodSorters;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class UserServiceTest extends BaseServiceTest{
	private Integer testUserId = 5;
	private String testUserEmail = "bobko@ee.ee";
	private String testUserName = "Bobko";
	private static User testUser;
	private static List<Ticket> userBookedTicket;
	@Test
	public void AtestRegisterUser() throws ParseException, UserNotFound {
		Map<Date,Auditorium> dateAndAudetorium = new HashMap<>();
		Auditorium testAuditorium = auditoriumService.getAuditoriums().get(0);
		dateAndAudetorium.put(dateformat.parse("2016-02-10"), testAuditorium);
		Date eventDate = dateformat.parse("2016-02-10");
		Integer userID = 1;
		Set<Ticket> purchasedTicket = new HashSet<>();
		purchasedTicket.add(new Ticket(33, null, 5, userService.getById(userID), eventDate));
		
		String eventName = "Party";
		Event testEvent = new Event(33, eventName, 122.0f, Rating.HIGH, 2, dateAndAudetorium, purchasedTicket);
		testEvent.getPurchasedTickets().iterator().next().setEvent(testEvent);
		
		userBookedTicket = new ArrayList<>();
		userBookedTicket.add(new Ticket(30,testEvent, 10, null, dateformat.parse("2016-02-10")));
		
		User userBobko = new User(testUserId,testUserEmail, testUserName, userBookedTicket, dateformat.parse("1992-02-08"));
		userBobko.getBookedTickets().get(0).setUser(userBobko);
		userService.register(userBobko);
		assertTrue("User is not saved",UserDaoImpl.users.contains(userBobko));
		testUser = userBobko;

	}
	
	@Test
	public void BtestGetById() throws ParseException, UserNotFound {
		User userBobkoReceivedById = userService.getById(testUserId);
		assertTrue("Received User by id is not equal to test User", testUser.equals(userBobkoReceivedById));
	}
	
	@Test
	public void CtestGetUserByEmail() throws ParseException, UserNotFound {
		User userBobkoReceivedByEmail = userService.getUserByEmail(testUserEmail);
		assertTrue("Received User by email is not equal to test User", testUser.equals(userBobkoReceivedByEmail));
	}
	@Test
	public void DtestGetUserByName() throws ParseException, UserNotFound {
		User userBobkoReceivedByName = userService.getUserByName(testUserName);
		assertTrue("Received User by name is not equal to test User", testUser.equals(userBobkoReceivedByName));
	}
	@Test
	public void EtestGetBookedTickets() throws ParseException, UserNotFound {
		List<Ticket> bookedTickets = userService.getBookedTickets(testUser);
		assertTrue("Received booked tickets is not equal to template booked tickets", bookedTickets.equals(userBookedTicket));
	}
	
	@Test
	public void EtestRemoveUser() throws ParseException, UserNotFound {
		userService.remove(testUser);
		assertFalse("User does not deleted", UserDaoImpl.users.contains(testUser));
	}
	
	
	
}
