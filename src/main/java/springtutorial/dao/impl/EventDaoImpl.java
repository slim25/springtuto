package springtutorial.dao.impl;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import springtutorial.dao.AuditoriumDao;
import springtutorial.dao.EventDao;
import springtutorial.dao.UserDao;
import springtutorial.exception.EventNotFound;
import springtutorial.exception.UserNotFound;
import springtutorial.model.Auditorium;
import springtutorial.model.Event;
import springtutorial.model.Ticket;
import springtutorial.persistance.Rating;
@Repository
public class EventDaoImpl implements EventDao{
	public static List<Event> events;
	
	private final String SQL_CREATE_EVENT = "INSERT INTO event (name, price, rating, timesPerDay) VALUES (?, ?, ?, ?)";
	private final String SQL_REMOVE_EVENT = "DELETE FROM event WHERE id = ?";
	private final String SQL_GET_EVENT_BY_NAME = "SELECT * FROM event WHERE name=?";
	private final String SQL_GET_EVENT_BY_ID = "SELECT * FROM event WHERE id=?";
	private final String SQL_GET_ALL_EVENTS = "SELECT * FROM event";
	private final String SQL_GET_EVENT_BY_DATE_RANGE = "SELECT * FROM event WHERE id IN (SELECT id FROM event_auditorium_date WHERE event_date > ? AND event_date < ?)";
	private final String SQL_GET_EVENT_TO_DATE = "SELECT * FROM event WHERE id IN (SELECT id FROM event_auditorium_date WHERE event_date < ?)";
	private final String SQL_EVENT_ASSIGN_AUDITORIUM = "INSERT INTO event_auditorium_date (auditorium, event_date,event_id) VALUES(?,?,?)";
	private final String SQL_GET_AUDITORIUM_DATE_FOR_EVENT = "SELECT auditorium,event_date FROM event_auditorium_date WHERE event_id=?";
	private final String SQL_GET_PURCHAISED_TICKETS_FOR_EVENT = "SELECT * FROM ticket WHERE event_id=?";
	private final String SQL_UPDATE_EVENT_ID = "UPDATE event SET id=? WHERE name=?";
	private final String SQL_INSERT_AUDITORIUM_DATE = "INSERT INTO event_auditorium_date (event_id, auditorium,event_date) VALUES (?,?,?)";
	private final String SQL_INSERT_PURCHASED_TICKET = "INSERT INTO purchasedTickets (event_id, ticket_id) VALUES (?,?)";
	private final String SQL_INSERT_TICKET = "INSERT INTO ticket (id,event_id, seatNumber, user_id, event_date) VALUES (?,?,?,?,?)";
	private final String SQL_INSERT_AUDITORIUM = "INSERT INTO auditorium (id, name, numberOfSeats) VALUES (?, ?, ?)";
	private final String SQL_SET_AUDITORIUM_VIP_SEAT = "INSERT INTO vip_seats (auditorium_id, vip_seat_number)"
			+ " VALUES (?, ?)";
	
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	@Autowired
	@Qualifier("AuditoriumDAO")
	AuditoriumDao auditoriumDao;
	@Autowired
	private UserDao userDao;
	
	public static List<Event> getEvents() {
		return events;
	}

	public static void setEvents(List<Event> events) {
		EventDaoImpl.events = events;
	}

	public void create(final Event event) {
		jdbcTemplate.update(SQL_CREATE_EVENT, new Object[] { event.getName(),
				event.getPrice(),event.getRating().toString(), event.getTimesPerDay()  
			});
		if(event.getId() != null){
			jdbcTemplate.update(SQL_UPDATE_EVENT_ID, new Object[] { event.getId(), event.getName()
				});
		}
		
		if(event.getAuditoriumAndDate()!= null || !event.getAuditoriumAndDate().isEmpty()){
			for(Map.Entry<Date,Auditorium> mapValue : event.getAuditoriumAndDate().entrySet()){
				jdbcTemplate.update(SQL_INSERT_AUDITORIUM_DATE,event.getId(),mapValue.getValue().getId(),mapValue.getKey());
			}
		}
		if(event.getPurchasedTickets()!= null || !event.getPurchasedTickets().isEmpty()){
			for(Ticket ticket : event.getPurchasedTickets()){
				jdbcTemplate.update(SQL_INSERT_PURCHASED_TICKET,event.getId(), ticket.getId());
				jdbcTemplate.update(SQL_INSERT_TICKET,ticket.getId(),event.getId(),ticket.getSeatNumber(),ticket.getUser().getId(), ticket.getDate());
			}
		}
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
			event.setPrice(((Double) eventData.get("price")).floatValue());
			event.setRating((String) eventData.get("rating"));
			event.setTimesPerDay((Integer) eventData.get("timesPerDay"));
		}
		return event;
	}
	public Event getById(String id) throws EventNotFound {
		List<Map<String, Object>> eventRow = jdbcTemplate.queryForList(SQL_GET_EVENT_BY_ID,id);
		if(eventRow.size()==0) throw new EventNotFound();
		Event event = new Event();
		for (Map<?, ?> eventData : eventRow) {
			event.setId((Integer) eventData.get("id"));
			event.setName((String) eventData.get("name"));
			event.setPrice(((Double) eventData.get("price")).floatValue());
			event.setRating((String) eventData.get("rating"));
			event.setTimesPerDay((Integer) eventData.get("timesPerDay"));
		}
		return event;
	}

	public List<Event> getAll() throws UserNotFound, EventNotFound {
		List<Event> events = new ArrayList<>();
		
		List<Map<String, Object>> eventRows = jdbcTemplate.queryForList(SQL_GET_ALL_EVENTS);
		for (Map<?, ?> eventData : eventRows) {
			Event event = new Event();
			event.setId((Integer) eventData.get("id"));
			event.setName((String) eventData.get("name"));
			event.setPrice(((Double) eventData.get("price")).floatValue());
			event.setRating((String) eventData.get("rating"));
			event.setTimesPerDay((Integer) eventData.get("timesPerDay"));
			Map<Date,Auditorium> map = getMapDateAuditorium(event.getId());
			Set<Ticket> purchasedTickets = getPurchaisedTickets(event);
			event.setPurchasedTickets(purchasedTickets);
			event.setAuditoriumAndDate(map);
			events.add(event);
		}
		return events;
		
	}
	private Map<Date,Auditorium> getMapDateAuditorium(Integer eventId){
		Map<Date,Auditorium> resultMap = new HashMap<>();
		
		List<Map<String, Object>> mapRows = jdbcTemplate.queryForList(SQL_GET_AUDITORIUM_DATE_FOR_EVENT, eventId);
		for (Map<?, ?> data : mapRows) {
			Date date =(Date) data.get("event_date");
			Auditorium auditorium = auditoriumDao.getAuditoriumById((Integer)data.get("auditorium"));
			resultMap.put(date, auditorium);
		}
		return resultMap;
		
	}
	private Set<Ticket> getPurchaisedTickets(Event event) throws UserNotFound, EventNotFound{
		Set<Ticket> resultTickets = new HashSet<>();
		
		List<Map<String, Object>> mapRows = jdbcTemplate.queryForList(SQL_GET_PURCHAISED_TICKETS_FOR_EVENT, event.getId());
		for (Map<?, ?> data : mapRows) {
			Ticket ticket = new Ticket();
			ticket.setDate((Date) data.get("event_date"));
			ticket.setEvent(event);
			ticket.setId((Integer)data.get("id"));
			ticket.setSeatNumber((Integer)data.get("seatNumber"));
			ticket.setUser(userDao.getById((Integer)data.get("user_id")));
			resultTickets.add(ticket);
		}
		return resultTickets;
		
	}
	
	
	public List<Event> getForDateRange(Date from, Date to) {
		List<Event> events = new ArrayList<>();
		
		List<Map<String, Object>> eventRows = jdbcTemplate.queryForList(SQL_GET_EVENT_BY_DATE_RANGE, from, to);
		for (Map<?, ?> eventData : eventRows) {
			Event event = new Event();
			event.setId((Integer) eventData.get("id"));
			event.setName((String) eventData.get("name"));
			event.setPrice(((Double) eventData.get("price")).floatValue());
			event.setRating((String) eventData.get("rating"));
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
			event.setPrice(((Double) eventData.get("price")).floatValue());
			event.setRating((String) eventData.get("rating"));
			event.setTimesPerDay((Integer) eventData.get("timesPerDay"));
			events.add(event);
		}
		return events;
	}

	public void assignAuditorium(Event event, Auditorium auditorium, Date date) throws EventNotFound {
		jdbcTemplate.update(SQL_INSERT_AUDITORIUM, new Object[] { auditorium.getId(), auditorium.getName(), auditorium.getNumberOfSeats()
		});
		initAuditoriumVipSeats(auditorium.getId(),auditorium.getVipSeats());
		int updatedRows = jdbcTemplate.update(SQL_EVENT_ASSIGN_AUDITORIUM, new Object[] { auditorium.getId(),
				date,event.getId()
			});
		if(updatedRows == 0) throw new EventNotFound();
	}
	private void initAuditoriumVipSeats(final Integer auditoriumId, final List<Integer> vipSeats){
		
		jdbcTemplate.batchUpdate(SQL_SET_AUDITORIUM_VIP_SEAT, new BatchPreparedStatementSetter() {
			
			@Override
			public void setValues(PreparedStatement ps, int i) throws SQLException {
				Integer vipSeat = vipSeats.get(i);
				ps.setInt(1, auditoriumId);
				ps.setInt(2, vipSeat);
			}
					
			@Override
			public int getBatchSize() {
				return vipSeats.size();
			}
		  });
		
	
}
	
}
