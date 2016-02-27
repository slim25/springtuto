package springtutorial.service;

import java.util.Collection;
import java.util.List;

import org.springframework.stereotype.Service;

import springtutorial.exception.EventNotFound;
import springtutorial.exception.UserNotFound;
import springtutorial.model.Ticket;
import springtutorial.model.User;

public interface UserService {
	
	void register(User user);
	
	boolean remove(User user);
	
	User getById(Integer id) throws UserNotFound, EventNotFound;
	
	User getUserByEmail(String email) throws UserNotFound, EventNotFound;
	
	User getUserByName(String name) throws UserNotFound, EventNotFound;
	
	List<Ticket> getBookedTickets(User user);

	List<User> getAllUsers() throws EventNotFound;
	
}
