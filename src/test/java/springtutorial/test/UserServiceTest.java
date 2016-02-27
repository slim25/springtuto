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
import springtutorial.exception.EventNotFound;
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
	public void AtestRegisterUser() throws ParseException, UserNotFound, EventNotFound {
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
		eventService.create(testEvent);
		
		User userBobko = new User(testUserId,testUserEmail, testUserName, userBookedTicket, dateformat.parse("1992-02-08"));
		userBobko.getBookedTickets().get(0).setUser(userBobko);
		userService.register(userBobko);
		boolean containsUser = false;
		for(User user : userService.getAllUsers()){
			if(user.getId().equals(userBobko.getId()) && user.getName().equals(userBobko.getName()) 
					&& user.getBirthday().compareTo(userBobko.getBirthday()) == 0){
				containsUser = true;
			}
		}
		assertTrue("User is not saved",containsUser);
		testUser = userBobko;

	}
	
	@Test
	public void BtestGetById() throws ParseException, UserNotFound, EventNotFound {
		User userBobkoReceivedById = userService.getById(testUserId);
		boolean userEquals = false;
		if(testUser.getId().equals(userBobkoReceivedById.getId()) && testUser.getName().equals(userBobkoReceivedById.getName()) 
				&& testUser.getBirthday().compareTo(userBobkoReceivedById.getBirthday()) == 0){
			userEquals = true;
		}
		assertTrue("Received User by id is not equal to test User", userEquals);
	}
	
	@Test
	public void CtestGetUserByEmail() throws ParseException, UserNotFound, EventNotFound {
		User userBobkoReceivedByEmail = userService.getUserByEmail(testUserEmail);
		boolean userEquals = false;
		if(testUser.getId().equals(userBobkoReceivedByEmail.getId()) && testUser.getName().equals(userBobkoReceivedByEmail.getName()) 
				&& testUser.getBirthday().compareTo(userBobkoReceivedByEmail.getBirthday()) == 0){
			userEquals = true;
		}
		assertTrue("Received User by email is not equal to test User", userEquals);
	}
	@Test
	public void DtestGetUserByName() throws ParseException, UserNotFound, EventNotFound {
		User userBobkoReceivedByName = userService.getUserByName(testUserName);
		boolean userEquals = false;
		if(testUser.getId().equals(userBobkoReceivedByName.getId()) && testUser.getName().equals(userBobkoReceivedByName.getName()) 
				&& testUser.getBirthday().compareTo(userBobkoReceivedByName.getBirthday()) == 0){
			userEquals = true;
		}
		assertTrue("Received User by name is not equal to test User", userEquals);
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
