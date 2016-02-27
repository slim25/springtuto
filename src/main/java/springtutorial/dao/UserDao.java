package springtutorial.dao;

import java.util.List;

import springtutorial.exception.EventNotFound;
import springtutorial.exception.UserNotFound;
import springtutorial.model.User;

public interface UserDao {

	void addUser(User user);

	boolean removeUser(User user);

	User getById(Integer id) throws UserNotFound, EventNotFound;

	User getByEmail(String email) throws UserNotFound, EventNotFound;

	User getUsersByName(String name) throws UserNotFound, EventNotFound;

	List<User> getUsers() throws EventNotFound;
}
