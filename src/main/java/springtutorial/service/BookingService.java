package springtutorial.service;

import java.util.Date;
import java.util.Set;

import org.springframework.stereotype.Service;

import springtutorial.model.Event;
import springtutorial.model.Ticket;
import springtutorial.model.User;


public interface BookingService {
	Float getTicketPrice(Event event,Date date,Integer seatNumber,User user);
	
	boolean bookTicket(User user,Ticket ticket); 
	
	Set<Ticket> getPurchasedTicketsForEvent(Event event,Date date); 
}
