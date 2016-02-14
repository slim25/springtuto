package springtutorial.test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

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
import org.junit.internal.runners.statements.Fail;
import org.junit.runners.MethodSorters;

import springtutorial.aspect.CounterAspect;
import springtutorial.dao.impl.EventDaoImpl;
import springtutorial.exception.EventNotFound;
import springtutorial.exception.UserNotFound;
import springtutorial.model.Auditorium;
import springtutorial.model.Event;
import springtutorial.model.Ticket;
import springtutorial.model.User;
import springtutorial.persistance.Rating;
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class EventServiceTest extends BaseServiceTest {
	private Integer testUserId = 5;
	private String testUserEmail = "bobko@ee.ee";
	private String testUserName = "Bobko";
	private String eventName = "Party";
	private static Event testEvent;

	@Test
	public void AtestCreateEvent() throws ParseException, UserNotFound {
		Map<Date, Auditorium> dateAndAudetorium = new HashMap<>();
		List<Integer> vipSeats = new ArrayList<>();
		vipSeats.add(19);
		vipSeats.add(6);
		vipSeats.add(7);
		Auditorium testAuditorium = new Auditorium(10, "Blue", 100, vipSeats);
		dateAndAudetorium.put(dateformat.parse("2016-02-11"), testAuditorium);
		Set<Ticket> purchasedTicket = new HashSet<>();
		Date eventDate = dateformat.parse("2016-02-10");
		purchasedTicket.add(new Ticket(33, null, 5, createUser(testUserId,testUserEmail,testUserName, dateformat.parse("1992-02-08")), eventDate));
		Event testEvent = new Event(33, eventName, 122.0f, Rating.HIGH, 2, dateAndAudetorium, purchasedTicket);
		testEvent.getPurchasedTickets().iterator().next().setEvent(testEvent);
		EventServiceTest.testEvent = testEvent;
		
		eventService.create(testEvent);

		assertTrue("Event does not created",EventDaoImpl.events.contains(testEvent));

	}
	
	@Test
	public void BtestGetByName() throws EventNotFound{
		Event receivedEventByName = eventService.getByName(eventName);
		assertTrue("Recieved event by name is not equals to template event",receivedEventByName.equals(testEvent));
	}
	
	@Test
	public void CtestGetAll(){
		List<Event> allEvents = eventService.getAll();
		assertTrue("Recieved event list size is not equal to template list size",allEvents.size() == 3);
	}
	
	@Test
	public void DtestAssignAuditorium() throws ParseException, EventNotFound{
		List<Integer> vipSeats = new ArrayList<>();
		vipSeats.add(19);
		vipSeats.add(6);
		vipSeats.add(7);
		Auditorium testAuditorium = new Auditorium(10, "Brown", 100, vipSeats);
		Date testDate = dateformat.parse("2016-02-15");
		eventService.assignAuditorium(testEvent, testAuditorium, testDate);
		Event receiverEventFromDB = null;
		for(Event event : EventDaoImpl.events){
			if(event.getId() == testEvent.getId()){
				receiverEventFromDB = event;
			}
		}
		for(Map.Entry<Date,Auditorium> entry : receiverEventFromDB.getAuditoriumAndDate().entrySet()){
			if(entry.getKey().equals(testDate) && entry.getValue().equals(testAuditorium)){
				return;
			}
		}
		 fail("There is no present template date with auditory");
	}
	
	@Test
	public void EtestRemoveEvent(){
		eventService.remove(testEvent);
		assertFalse("Event does not deleted",EventDaoImpl.events.contains(testEvent));
	}
	
	@Test
	public void Ftest() throws EventNotFound{
		eventService.getByName("Glasgou");
		System.out.println("RESULT = " + CounterAspect.eventAccessedTimes);
	}
	
}
