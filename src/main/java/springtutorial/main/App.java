package springtutorial.main;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

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
import springtutorial.service.AuditoriumService;
import springtutorial.service.BookingService;
import springtutorial.service.EventService;
import springtutorial.service.UserService;
import springtutorial.service.impl.EventServiceImpl;

public class App {
	@Autowired
	AuditoriumService auditoriumService;
	@Autowired
	BookingService bookingService;
	@Autowired
	EventService eventService;
	@Autowired
	UserService userService;
	
	private static StringBuilder builder = new StringBuilder();
	private static SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd");
	
	public static void main(String[] args) {
		ApplicationContext ctx = new ClassPathXmlApplicationContext("spring/spring-context.xml");
		App app = ctx.getBean(springtutorial.main.App.class);
		
		showAuditoryService(app);
//		showBookingService(app);
		try {
			showEventService(app);
			showUserService(app);
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
	
	private static void showAuditoryService(App app){
		System.out.println("Auditorium Service");
		List<Auditorium> auditoriums = app.auditoriumService.getAuditoriums();
		for(Auditorium audit : auditoriums){
			List<Integer> vipSeats = audit.getVipSeats();
			for(Integer seat : vipSeats){
				builder.append("seat : " + seat +"; ");
			}
			System.out.println("Auditory name : " + audit.getName() + "; id : " + audit.getId() 
			+ "; number of seats : " + audit.getNumberOfSeats() + "; Vip seats : " + builder);
			builder.setLength(0);
		}
		Integer auditoryId = 1;
		try {
			Integer numberOfSeats = app.auditoriumService.getSeatsNumber(auditoryId);
			System.out.println("There are " + numberOfSeats + " seats in auditory with id = " + auditoryId);
		
			List<Integer> vipSeats = app.auditoriumService.getVipSeats(auditoryId);
			for(Integer seat : vipSeats){
				builder.append("seat : " + seat +"; ");
			}
			System.out.println("Vip seats for auditory with id = " + auditoryId + " : " + builder);
		} catch (NotExistSuchAuditorium e) {
			System.out.println("Auditory with id : " + auditoryId + " does not exist");
			e.printStackTrace();
		}
	}
	
	private static void showEventService(App app) throws ParseException{
		System.out.println("Event Service");
		Map<Date,Auditorium> dateAndAudetorium = new HashMap<>();
		Auditorium testAuditorium = app.auditoriumService.getAuditoriums().iterator().next();
		dateAndAudetorium.put(dateformat.parse("2016-02-10"), testAuditorium);
		
		Set<Ticket> purchasedTicket = new HashSet<>();
		Integer userID = 1;
		String eventName = "Party";
		try {
			Date eventDate = dateformat.parse("2016-02-10");
			purchasedTicket.add(new Ticket(33, null, 5, app.userService.getById(userID), eventDate));
			Event testEvent = new Event(33, "Party", 122.0f, Rating.HIGH, 2, dateAndAudetorium, purchasedTicket);
			testEvent.getPurchasedTickets().iterator().next().setEvent(testEvent);
			
			app.eventService.create(testEvent);
			boolean eventSaved = EventDaoImpl.events.contains(testEvent);
			System.out.println("event saved = " + eventSaved);
			
			Event receivedEventByName;
			try {
				receivedEventByName = app.eventService.getByName("Party");
				System.out.println("Received event by name = " +  receivedEventByName);
			} catch (EventNotFound e) {
				System.out.println("Event with name : " + eventName + " does not exist");
				e.printStackTrace();
			}
			List<Event> allEvents = app.eventService.getAll();
			System.out.println("All events : ");
			for(Event event : allEvents){
				System.out.print("[ " +event + " ] ");
			}
			//events to date
			Date toDate = dateformat.parse("2016-02-9");
			List<Event> eventsToDate = app.eventService.getNextEvents(toDate);
			System.out.println("Events to date " + toDate+ " : ");
			for(Event event : eventsToDate){
				System.out.print("[ " +event + " ] ");
			}
			//events from - to date
			Date fromDate = dateformat.parse("2016-02-1");
			Date toDateRange = dateformat.parse("2016-02-11");
			List<Event> eventsFromToDate = app.eventService.getForDateRange(fromDate, toDateRange);
			System.out.println("Events from " + fromDate +"- to date " + toDateRange+ " : ");
			for(Event event : eventsFromToDate){
				System.out.print("[ " +event + " ] ");
			}
			List<Integer> vipSeats = new ArrayList<>();
			vipSeats.add(3);
			vipSeats.add(5);
			Auditorium auditorium = new Auditorium(33,"Auditrium33",45,vipSeats);
			
			app.eventService.assignAuditorium(testEvent, auditorium, dateformat.parse("2016-02-1"));
			Event receivedEvent = app.eventService.getByName(eventName);
			System.out.println("Auditorium assigned = " + receivedEvent.getAuditoriumAndDate().values().contains(auditorium));
			
			boolean eventRemoved = app.eventService.remove(testEvent);
			System.out.println("Event removed = " + eventRemoved);
		} catch (UserNotFound | EventNotFound e) {
			if(e instanceof UserNotFound){
				System.out.println("User with id : " + userID + " does not exist");
			}else{
				System.out.println("Event with name : " + eventName + " does not exist");
			}
			e.printStackTrace();
		}
	}
	
	private static void showUserService(App app) throws ParseException{
		System.out.println("User Service");
		List<Ticket> userBookedTicket = new ArrayList<>();
		Event event = null;
		String eventName = "Glasgou";
		try {
			event = app.eventService.getByName(eventName);
			
		} catch (EventNotFound e1) {
			System.out.println("Event with name : " + eventName + " does not exist");
			e1.printStackTrace();
		}
		userBookedTicket.add(new Ticket(30,event, 10, null, dateformat.parse("2016-02-10")));
		
		Integer testUserId = 5;
		String testUserEmail = "bobko@ee.ee";
		String testUserName = "Bobko";
		
		User userBobko = new User(testUserId,testUserEmail, "Bobko", userBookedTicket, dateformat.parse("1992-02-08"));
		userBobko.getBookedTickets().get(0).setUser(userBobko);
		app.userService.register(userBobko);
		System.out.println("User is saved = " + UserDaoImpl.users.contains(userBobko));
		try {
			User userBobkoReceivedById = app.userService.getById(testUserId);
			System.out.println("Received User by id : " + testUserId + " = " + userBobkoReceivedById);
			User userBobkoReceivedByEmail = app.userService.getUserByEmail(testUserEmail);
			System.out.println("Received User by email : " + testUserEmail + " = " + userBobkoReceivedByEmail);
			User userBobkoReceivedByName = app.userService.getUserByName(testUserName);
			System.out.println("Received User by name : " + testUserName + " = " + userBobkoReceivedByName);
			List<Ticket> bookedTickets = app.userService.getBookedTickets(userBobko);
			System.out.println("Booked tickets : " + (bookedTickets.isEmpty() ? "empty" : ""));
			for(Ticket ticket : bookedTickets){
				System.out.println("Ticket : " + ticket);
			}
		} catch (UserNotFound e) {
			System.out.println("User with id : " + testUserId + " does not exist");
			e.printStackTrace();
		}
		boolean userDeleted = app.userService.remove(userBobko);
		System.out.println("User deleted = " + userDeleted);
		
	}
	
	private static void showBookingService(App app){
		
//		List<Auditorium> auditoriums = app.bookingService.getPurchasedTicketsForEvent(event, date);
	}
}
