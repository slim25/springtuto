package springtutorial.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import springtutorial.dao.UserDao;
import springtutorial.exception.EventNotFound;
import springtutorial.exception.UserNotFound;
import springtutorial.model.Ticket;
import springtutorial.model.User;
import springtutorial.service.UserService;
@Service
public class UserServiceImpl implements UserService{
	@Autowired
	private UserDao userDao;
	
	public UserServiceImpl(){
		System.out.println(" Init UserServiceImpl");
	}
	public void register(User user) {
		userDao.addUser(user);
	}

	public boolean remove(User user) {
		return userDao.removeUser(user);
	}

	public User getById(Integer id) throws UserNotFound, EventNotFound {
		return userDao.getById(id);
	}

	public User getUserByEmail(String email) throws UserNotFound, EventNotFound {
		return userDao.getByEmail(email);
	}

	public User getUserByName(String name) throws UserNotFound, EventNotFound {
		return userDao.getUsersByName(name);
	}
	
	public List<Ticket> getBookedTickets(User user) {
		return user.getBookedTickets();
	}
	@Override
	public List<User> getAllUsers() throws EventNotFound{
		return userDao.getUsers();
	}

}
