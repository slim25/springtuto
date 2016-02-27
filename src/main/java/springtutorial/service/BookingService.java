package springtutorial.service;

import java.util.Date;
import java.util.Set;

import org.springframework.stereotype.Service;

import springtutorial.exception.EventNotFound;
import springtutorial.exception.UserNotFound;
import springtutorial.model.Event;
import springtutorial.model.Ticket;
import springtutorial.model.User;


public interface BookingService {
	Float getTicketPrice(Event event,Date date,Integer seatNumber,User user);
	
	boolean bookTicket(User user,Ticket ticket) throws EventNotFound; 
	
	Set<Ticket> getPurchasedTicketsForEvent(Event event,Date date) throws UserNotFound, EventNotFound; 
}
