package springtutorial.dao;

import springtutorial.exception.UserNotFound;
import springtutorial.model.User;

public interface UserDao {

	void addUser(User user);

	boolean removeUser(User user);

	User getById(Integer id) throws UserNotFound;

	User getByEmail(String email) throws UserNotFound;

	User getUsersByName(String name) throws UserNotFound;
}
