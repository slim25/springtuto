package springtutorial.service;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;

import springtutorial.exception.EventNotFound;
import springtutorial.exception.UserNotFound;
import springtutorial.model.Auditorium;
import springtutorial.model.Event;

public interface EventService {

	void create(Event event);

	boolean remove(Event event);

	Event getByName(String name) throws EventNotFound;

	List<Event> getAll() throws UserNotFound, EventNotFound;
	
	List<Event> getForDateRange(Date from, Date to);

	List<Event> getNextEvents(Date to);
	
	void assignAuditorium(Event event,Auditorium auditorium,Date date) throws EventNotFound; 

}
