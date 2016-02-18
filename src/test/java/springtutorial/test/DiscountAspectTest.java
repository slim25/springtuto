package springtutorial.test;

import static org.junit.Assert.assertTrue;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import springtutorial.aspect.CounterAspect;
import springtutorial.aspect.DiscountAspect;
import springtutorial.dao.impl.EventDaoImpl;
import springtutorial.dao.impl.UserDaoImpl;
import springtutorial.exception.EventNotFound;
import springtutorial.model.Event;
import springtutorial.model.Ticket;
import springtutorial.model.User;
import springtutorial.service.DiscountService;

public class DiscountAspectTest extends BaseServiceTest{
	
	@Autowired
	DiscountService discountService;
	@Before
	public void clearDiscountGivenTimes(){
		DiscountAspect.discountGivenTimes.clear();
	}
	
	@Test
	public void testCountHowManyTimesDiscountWasGiven() throws ParseException{
		User userVitalik = UserDaoImpl.users.get(0);
		User userAdmin = UserDaoImpl.users.get(1);
		Date eventDate = dateformat.parse("2016-02-10");
		Date eventDateBirthdayAdmin = dateformat.parse("1980-01-08");
		List<Ticket> tenBuyedTicket = getTickets(userVitalik, eventDate);
		userVitalik.setBookedTickets(tenBuyedTicket);
		
		discountService.getDiscountPercentage(userVitalik, eventDate);
		discountService.getDiscountPercentage(userAdmin, eventDateBirthdayAdmin);
		
		assertTrue("Aspect to count how many times discount was given is not correct, must be size 2",
				DiscountAspect.discountGivenTimes.size() == 2);
		assertTrue("Counter for EveryTenTicketDiscountStrategy is not correct",DiscountAspect.discountGivenTimes.get("EveryTenTicketDiscountStrategy") == 1);
		assertTrue("Counter for BirthdayDiscountStrategy is not correct",DiscountAspect.discountGivenTimes.get("BirthdayDiscountStrategy") == 1);
	}
	
	private List<Ticket> getTickets(User user, Date date){
		List<Ticket> result = new ArrayList<Ticket>(10);
		Event testEvent = EventDaoImpl.events.get(0);
		for (int i = 1; i <= 10; i++) {
			result.add(new Ticket(i, testEvent, i, user, date));
		}
		return result;
	}
	
	@Before
	public void clearDiscountGivenTimesForUser(){
		DiscountAspect.discountGivenTimesForUser.clear();
	}
	@Test
	public void testCountHowManyTimesDiscountWasGivenForUser() throws ParseException{
		User userVitalik = UserDaoImpl.users.get(0);
		User userAdmin = UserDaoImpl.users.get(1);
		Date eventDate = dateformat.parse("2016-02-10");
		Date eventDateBirthdayAdmin = dateformat.parse("1980-01-08");
		Date eventDateBirthdayVitalik = dateformat.parse("1990-01-08");
		List<Ticket> tenBuyedTicket = getTickets(userVitalik, eventDate);
		userVitalik.setBookedTickets(tenBuyedTicket);
		
		discountService.getDiscountPercentage(userVitalik, eventDate);
		userVitalik.setBookedTickets(new ArrayList<Ticket>());
		discountService.getDiscountPercentage(userVitalik, eventDateBirthdayVitalik);
		discountService.getDiscountPercentage(userAdmin, eventDateBirthdayAdmin);
		
		assertTrue("Aspect to count how many times discount was given is not correct, must be size 2",
				DiscountAspect.discountGivenTimes.size() == 2);
		assertTrue("Counter for EveryTenTicketDiscountStrategy is not correct",DiscountAspect.discountGivenTimesForUser.get("Vitalik").get("EveryTenTicketDiscountStrategy") == 1);
		assertTrue("Counter for EveryTenTicketDiscountStrategy is not correct",DiscountAspect.discountGivenTimesForUser.get("Vitalik").get("BirthdayDiscountStrategy") == 1);
		assertTrue("Counter for BirthdayDiscountStrategy is not correct",DiscountAspect.discountGivenTimesForUser.get("Admin").get("BirthdayDiscountStrategy") == 1);
	}
	
}
