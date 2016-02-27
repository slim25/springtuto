package springtutorial.dao.impl;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import springtutorial.dao.EventDao;
import springtutorial.dao.UserDao;
import springtutorial.exception.EventNotFound;
import springtutorial.exception.UserNotFound;
import springtutorial.model.Auditorium;
import springtutorial.model.Event;
import springtutorial.model.Ticket;
import springtutorial.model.User;
@Repository("userDao")
public class UserDaoImpl implements UserDao{
	public static List<User> users;
	private final String SQL_CREATE_USER = "INSERT INTO users (email, name, birthday, registered) VALUES (?, ?, ?, ?)";
	private final String SQL_INSERT_USER_BOOKED_TICKETS = "INSERT INTO ticket (event_id, seatNumber, user_id, event_date) VALUES (?, ?, ?, ?)";
	private final String SQL_REMOVE_USER = "DELETE FROM users WHERE id = ?";
	private final String SQL_GET_USER_BY_ID = "SELECT * FROM users WHERE id=?";
	private final String SQL_GET_EVENT_BY_EMAIL = "SELECT * FROM users WHERE email=?";
	private final String SQL_GET_BOOKED_TICKETS = "SELECT * FROM ticket WHERE user_id=?";
	private final String SQL_GET_EVENT_BY_NAME = "SELECT * FROM users WHERE name=?";
	private final String SQL_GET_ALL_USERS = "SELECT * FROM users";
	private final String SQL_UPDATE_USER_ID = "UPDATE users SET id=? WHERE name=?";
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	@Autowired
	@Qualifier("EventDAO")
	EventDao eventDao;
	
	public void addUser(User user) {
		jdbcTemplate.update(SQL_CREATE_USER, new Object[] { user.getEmail(),
				user.getName(), user.getBirthday(),user.isRegistered() 
			});
		if(user.getId() != null){
			jdbcTemplate.update(SQL_UPDATE_USER_ID, new Object[] { user.getId(), user.getName() 
				});
		}
		
//		users.add(user);
	}

	public boolean removeUser(User user) {
		int removedRows = jdbcTemplate.update(SQL_REMOVE_USER, new Object[] { user.getId()
		});
	
	return removedRows > 0;
//		return users.remove(user);
	}

	public User getById(Integer id) throws UserNotFound, EventNotFound {
		jdbcTemplate.queryForList("SELECT * FROM users");
		List<Map<String, Object>> userRow = jdbcTemplate.queryForList(SQL_GET_USER_BY_ID,id);
		if(userRow.size()==0) throw new UserNotFound();
		User user = new User();
		for (Map<?, ?> userData : userRow) {
			user.setId((Integer) userData.get("id"));
			user.setName((String) userData.get("name"));
			user.setEmail((String) userData.get("email"));
			user.setBirthday((Date) userData.get("birthday"));
			user.setRegistered(((byte) userData.get("registered")) == 1 ? true : false);
		}
		
		List<Ticket> userTickets = getUserTickets(user);
		user.setBookedTickets(userTickets);
		
		return user;
//		for(User user : users){
//			if(user.getId() == id){
//				return user;
//			}
//		}
//		throw new UserNotFound();
	}
	
	private List<Ticket> getUserTickets(User user) throws EventNotFound{
		List<Ticket> userTickets = new ArrayList<>();
		List<Map<String, Object>> ticketRows = jdbcTemplate.queryForList(SQL_GET_BOOKED_TICKETS, user.getId());
		for (Map<?, ?> ticketData : ticketRows) {
			Ticket ticket = new Ticket();
			ticket.setId((Integer) ticketData.get("id"));
			ticket.setSeatNumber((Integer) ticketData.get("seatNumber"));
			ticket.setDate((Date) ticketData.get("event_date"));
			ticket.setEvent(eventDao.getById(""+(Integer) ticketData.get("event_id")));
			ticket.setUser(user);
			userTickets.add(ticket);
		}
		return userTickets;
	}

	public User getByEmail(String email) throws UserNotFound, EventNotFound {
		List<Map<String, Object>> userRow = jdbcTemplate.queryForList(SQL_GET_EVENT_BY_EMAIL,email);
		if(userRow.size()==0) throw new UserNotFound();
		User user = new User();
		for (Map<?, ?> userData : userRow) {
			user.setId((Integer) userData.get("id"));
			user.setName((String) userData.get("name"));
			user.setEmail((String) userData.get("email"));
			user.setBirthday((Date) userData.get("birthday"));
			user.setRegistered(((byte) userData.get("registered")) == 1 ? true : false);
		}
		List<Ticket> userTickets = getUserTickets(user);
		user.setBookedTickets(userTickets);
		return user;
		
//		for(User user : users){
//			if(user.getEmail().equalsIgnoreCase(email)){
//				return user;
//			}
//		}
//		throw new UserNotFound();
	}

	public User getUsersByName(String name) throws UserNotFound, EventNotFound {
		List<Map<String, Object>> userRow = jdbcTemplate.queryForList(SQL_GET_EVENT_BY_NAME,name);
		if(userRow.size()==0) throw new UserNotFound();
		User user = new User();
		for (Map<?, ?> userData : userRow) {
			user.setId((Integer) userData.get("id"));
			user.setName((String) userData.get("name"));
			user.setEmail((String) userData.get("email"));
			user.setBirthday((Date) userData.get("birthday"));
			user.setRegistered(((byte) userData.get("registered")) == 1 ? true : false);
		}
		List<Ticket> userTickets = getUserTickets(user);
		user.setBookedTickets(userTickets);
		return user;
		
		
//		for(User user : users){
//			if(user.getName().equalsIgnoreCase(name)){
//				return user;
//			}
//		}
//		throw new UserNotFound();
	}
@Override
	public List<User> getUsers() throws EventNotFound {
		List<User> users = new ArrayList<>();
		
		List<Map<String, Object>> userRows = jdbcTemplate.queryForList(SQL_GET_ALL_USERS);
		for (Map<?, ?> userData : userRows) {
			User user = new User();
			user.setId((Integer) userData.get("id"));
			user.setName((String) userData.get("name"));
			user.setEmail((String) userData.get("email"));
			user.setBirthday((Date) userData.get("birthday"));
			user.setRegistered(((byte) userData.get("registered")) == 1 ? true : false);
			List<Ticket> userTickets = getUserTickets(user);
			user.setBookedTickets(userTickets);
			users.add(user);
		}
		return users;
		
	}

	public static void setUsers(List<User> users) {
		UserDaoImpl.users = users;
	}


}
