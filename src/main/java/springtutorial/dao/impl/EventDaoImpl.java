package springtutorial.dao.impl;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import springtutorial.dao.EventDao;
import springtutorial.exception.EventNotFound;
import springtutorial.model.Auditorium;
import springtutorial.model.Event;
import springtutorial.persistance.Rating;
@Repository
public class EventDaoImpl implements EventDao{
	public static List<Event> events;
	
	private final String SQL_CREATE_EVENT = "INSERT INTO event (name, price, rating, timesPerDay) VALUES (?, ?, ?, ?)";
	private final String SQL_REMOVE_EVENT = "DELETE FROM event WHERE id = ?";
	private final String SQL_GET_EVENT_BY_NAME = "SELECT * FROM event WHERE name=?";
	private final String SQL_GET_ALL_EVENTS = "SELECT * FROM event";
	private final String SQL_GET_EVENT_BY_DATE_RANGE = "SELECT * FROM event WHERE id IN (SELECT id FROM event_auditorium_date WHERE event_date > ? AND event_date < ?)";
	private final String SQL_GET_EVENT_TO_DATE = "SELECT * FROM event WHERE id IN (SELECT id FROM event_auditorium_date WHERE event_date < ?)";
	private final String SQL_EVENT_ASSIGN_AUDITORIUM = "UPDATE event_auditorium_date SET auditorium=?, event_date=? WHERE auditorium=?";
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	public static List<Event> getEvents() {
		return events;
	}

	public static void setEvents(List<Event> events) {
		EventDaoImpl.events = events;
	}

	public void create(Event event) {
		jdbcTemplate.update(SQL_CREATE_EVENT, new Object[] { event.getName(),
				event.getPrice(),event.getRating(), event.getTimesPerDay()  
			});
	}

	public boolean remove(Event event) {
		int removedRows = jdbcTemplate.update(SQL_REMOVE_EVENT, new Object[] { event.getId()
			});
		
		return removedRows > 0;
	}

	public Event getByName(String name) throws EventNotFound {
		List<Map<String, Object>> eventRow = jdbcTemplate.queryForList(SQL_GET_EVENT_BY_NAME,name);
		if(eventRow.size()==0) throw new EventNotFound();
		Event event = new Event();
		for (Map<?, ?> eventData : eventRow) {
			event.setId((Integer) eventData.get("id"));
			event.setName((String) eventData.get("name"));
			event.setPrice((Float) eventData.get("price"));
			event.setRating((String) eventData.get("price"));
			event.setTimesPerDay((Integer) eventData.get("timesPerDay"));
		}
		return event;
	}

	public List<Event> getAll() {
		List<Event> events = new ArrayList<>();
		
		List<Map<String, Object>> eventRows = jdbcTemplate.queryForList(SQL_GET_ALL_EVENTS);
		for (Map<?, ?> eventData : eventRows) {
			Event event = new Event();
			event.setId((Integer) eventData.get("id"));
			event.setName((String) eventData.get("name"));
			event.setPrice((Float) eventData.get("price"));
			event.setRating((String) eventData.get("price"));
			event.setTimesPerDay((Integer) eventData.get("timesPerDay"));
			events.add(event);
		}
		return events;
		
	}

	public List<Event> getForDateRange(Date from, Date to) {
		List<Event> events = new ArrayList<>();
		
		List<Map<String, Object>> eventRows = jdbcTemplate.queryForList(SQL_GET_EVENT_BY_DATE_RANGE, from, to);
		for (Map<?, ?> eventData : eventRows) {
			Event event = new Event();
			event.setId((Integer) eventData.get("id"));
			event.setName((String) eventData.get("name"));
			event.setPrice((Float) eventData.get("price"));
			event.setRating((String) eventData.get("price"));
			event.setTimesPerDay((Integer) eventData.get("timesPerDay"));
			events.add(event);
		}
		return events;
	}

	public List<Event> getNextEvents(Date to) {
		List<Event> events = new ArrayList<>();
		
		List<Map<String, Object>> eventRows = jdbcTemplate.queryForList(SQL_GET_EVENT_TO_DATE, to);
		for (Map<?, ?> eventData : eventRows) {
			Event event = new Event();
			event.setId((Integer) eventData.get("id"));
			event.setName((String) eventData.get("name"));
			event.setPrice((Float) eventData.get("price"));
			event.setRating((String) eventData.get("price"));
			event.setTimesPerDay((Integer) eventData.get("timesPerDay"));
			events.add(event);
		}
		return events;
	}

	public void assignAuditorium(Event event, Auditorium auditorium, Date date) throws EventNotFound {
		int updatedRows = jdbcTemplate.update(SQL_EVENT_ASSIGN_AUDITORIUM, new Object[] { auditorium.getId(),
				date,event.getId()
			});
		if(updatedRows == 0) throw new EventNotFound();
	}
	
}
