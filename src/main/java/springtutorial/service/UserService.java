package springtutorial.service;

import java.util.List;

import org.springframework.stereotype.Service;

import springtutorial.exception.UserNotFound;
import springtutorial.model.Ticket;
import springtutorial.model.User;

public interface UserService {
	
	void register(User user);
	
	boolean remove(User user);
	
	User getById(Integer id) throws UserNotFound;
	
	User getUserByEmail(String email) throws UserNotFound;
	
	User getUsersByName(String name) throws UserNotFound;
	
	List<Ticket> getBookedTickets(User user);
	
}
