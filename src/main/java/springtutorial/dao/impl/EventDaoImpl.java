package springtutorial.dao.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import springtutorial.dao.EventDao;
import springtutorial.exception.EventNotFound;
import springtutorial.model.Auditorium;
import springtutorial.model.Event;
@Repository
public class EventDaoImpl implements EventDao{
	public static List<Event> events;
	
	
	public static List<Event> getEvents() {
		return events;
	}

	public static void setEvents(List<Event> events) {
		EventDaoImpl.events = events;
	}

	public void create(Event event) {
		events.add(event);
	}

	public boolean remove(Event event) {
		return events.remove(event);
	}

	public Event getByName(String name) throws EventNotFound {
		for(Event event : events){
			if(event.getName().equalsIgnoreCase(name)){
				return event;
			}
		}
		throw new EventNotFound();
	}

	public List<Event> getAll() {
		return events;
	}

	public List<Event> getForDateRange(Date from, Date to) {
		List<Event> result = new ArrayList<>();
		for(Event event : events){
			for(Map.Entry<Date, Auditorium> auditoriumAndDate : event.getAuditoriumAndDate().entrySet()){
				if(auditoriumAndDate.getKey().compareTo(from) >= 0 && auditoriumAndDate.getKey().compareTo(from) <=0){
					result.add(event);
					break;
				}
			}
		}
		return result;
	}

	public List<Event> getNextEvents(Date to) {
		List<Event> result = new ArrayList<>();
		for(Event event : events){
			for(Map.Entry<Date, Auditorium> auditoriumAndDate : event.getAuditoriumAndDate().entrySet()){
			if(auditoriumAndDate.getKey().compareTo(new Date()) >= 0 && auditoriumAndDate.getKey().compareTo(to) <=0){
				result.add(event);
				break;
			}
		}
		}
		return result;
	}

	public void assignAuditorium(Event event, Auditorium auditorium, Date date) throws EventNotFound {
		for(Event ev : events){
			if(ev.getId() == event.getId()){
				ev.assignAuditorium(auditorium, date);
				return;
			}
		}
		throw new EventNotFound();
	}
	
}
