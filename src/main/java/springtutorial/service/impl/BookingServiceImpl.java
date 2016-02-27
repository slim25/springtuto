package springtutorial.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import springtutorial.dao.EventDao;
import springtutorial.dao.UserDao;
import springtutorial.dao.impl.EventDaoImpl;
import springtutorial.dao.impl.UserDaoImpl;
import springtutorial.exception.EventNotFound;
import springtutorial.exception.UserNotFound;
import springtutorial.model.Auditorium;
import springtutorial.model.Event;
import springtutorial.model.Ticket;
import springtutorial.model.User;
import springtutorial.persistance.Rating;
import springtutorial.service.BookingService;
import springtutorial.service.DiscountService;
@Service
public class BookingServiceImpl implements BookingService {
	private final String SQL_INSERT_BOOKED_TICKETS = "INSERT INTO ticket (id,event_id, seatNumber, user_id, event_date) VALUES (?, ?, ?, ?, ?)";
	private final String SQL_GET_PURCHASED_TICKETS_FOR_EVENT = "SELECT ticket_id FROM purchasedTickets WHERE event_id=? AND ticket_id IN "
			+ "(SELECT id FROM ticket WHERE event_date = ?)";
	private final String SQL_GET_PURCHASED_TICKET_BY_ID = "SELECT * FROM ticket WHERE id=?";
	private final String SQL_INSERT_PURCHASED_TICKETS = "INSERT INTO purchasedTickets (event_id, ticket_id) VALUES (?, ?)";
	
	@Autowired
	@Qualifier("discountService")
	DiscountService discountService;
	@Autowired
	@Qualifier("EventDAO")
	EventDao eventDao;
	@Autowired
	@Qualifier("userDao")
	UserDao userDao;
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	public BookingServiceImpl() {
		System.out.println(" Init BookingServiceImpl");
	}
	
	@Override
	public Float getTicketPrice(Event event, Date date, Integer seatNumber,
			User user) {
		Map.Entry<String,Float> discount = discountService.getDiscountPercentage(user, date);
		float eventBasePrice = event.getPrice();
		Auditorium auditorium = event.getAuditoriumAndDate().get(date);
		List<Integer> vipSeats = auditorium.getVipSeats();
			//All prices for high rated movies should be higher (For example, 1.2xBasePrice)
		if(event.getRating().equals(Rating.HIGH))eventBasePrice*=1.2f;
			//Vip seats should cost more than regular seats (For example, 2xBasePrice)
		if(vipSeats.contains(seatNumber)) eventBasePrice*=2f;
			//User is needed to calculate discount 
		if(discount.getValue() != 0f){ 
			eventBasePrice = eventBasePrice - (eventBasePrice*discount.getValue())/100;
			System.out.println("User has discount : " + discount.getKey());
		}
		return eventBasePrice;
	}

	@Override
	public boolean bookTicket(User user, Ticket ticket) throws EventNotFound {
		int resultUpdate = jdbcTemplate.update(SQL_INSERT_BOOKED_TICKETS, new Object[] {ticket.getId(), ticket.getEvent().getId(),
				ticket.getSeatNumber(),user.getId() ,ticket.getDate()
			});
		int resultUpdatePurchasedTickets = jdbcTemplate.update(SQL_INSERT_PURCHASED_TICKETS, new Object[] {ticket.getEvent().getId(),ticket.getId()
			});
		
//		for(Iterator<User> iterator = userDao.getUsers().iterator();iterator.hasNext();){
//			User curUser = iterator.next();
//			if(curUser.isRegistered() && curUser.getId() == user.getId()){
//				curUser.addBookedTickets(ticket);
//				try {
//					eventDao.getByName(ticket.getEvent().getName()).addPurchasedTicket(ticket);
//					return true;
//				} catch (EventNotFound e) {
//					System.out.println("Ticket event not found");
//					e.printStackTrace();
//					return false;
//				}
//			}
//		}
		return resultUpdate > 0 && resultUpdatePurchasedTickets> 0;
	}

	@Override
	public Set<Ticket> getPurchasedTicketsForEvent(Event event, Date date) throws UserNotFound, EventNotFound {
		Set<Ticket> purchasedTickets = new HashSet<>();
		jdbcTemplate.queryForList("SELECT * FROM ticket");
		jdbcTemplate.queryForList("SELECT * FROM purchasedTickets");
		
		List<Map<String, Object>> purchasedTicketsRows = jdbcTemplate.queryForList(SQL_GET_PURCHASED_TICKETS_FOR_EVENT, event.getId(),date);
		for (Map<?, ?> ticketIdRow : purchasedTicketsRows) {
			Integer ticketId = (Integer)ticketIdRow.get("ticket_id");
			Ticket ticket = jdbcTemplate.queryForObject(SQL_GET_PURCHASED_TICKET_BY_ID, new Object[] { ticketId }, new Ticket());
			if(ticket != null){
				purchasedTickets.add(ticket);
			}
		}
		return purchasedTickets;
		
//		boolean eventPresent = false;
//		for(Event ev : eventDao.getAll()){
//			if(ev.getId() == event.getId()){
//				event = ev;
//				eventPresent = true;
//			}
//		}
//		if(eventPresent == false) return null;
//		Set<Ticket> result = new HashSet<>();
//		
//		Calendar purchaseTicketCalendar = Calendar.getInstance();
//		purchaseTicketCalendar.setTime(date);
//		
//		Calendar allPurchasedCalendar = Calendar.getInstance();
//		
//		for(Ticket ticket : event.getPurchasedTickets()){
//			allPurchasedCalendar.setTime(ticket.getDate());
//			if(allPurchasedCalendar.get(Calendar.MONTH) == purchaseTicketCalendar.get(Calendar.MONTH) && 
//					allPurchasedCalendar.get(Calendar.DAY_OF_MONTH) == purchaseTicketCalendar.get(Calendar.DAY_OF_MONTH)){
//				result.add(ticket);
//			}
//		}
//		return result;
	}

}
