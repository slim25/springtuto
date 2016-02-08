package springtutorial.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import springtutorial.dao.UserDao;
import springtutorial.exception.UserNotFound;
import springtutorial.model.User;
@Repository("userDao")
public class UserDaoImpl implements UserDao{
	public static List<User> users;
	
	public void addUser(User user) {
		users.add(user);
	}

	public boolean removeUser(User user) {
		return users.remove(user);
	}

	public User getById(Integer id) throws UserNotFound {
		for(User user : users){
			if(user.getId() == id){
				return user;
			}
		}
		throw new UserNotFound();
	}

	public User getByEmail(String email) throws UserNotFound {
		for(User user : users){
			if(user.getEmail().equalsIgnoreCase(email)){
				return user;
			}
		}
		throw new UserNotFound();
	}

	public User getUsersByName(String name) throws UserNotFound {
		for(User user : users){
			if(user.getName().equalsIgnoreCase(name)){
				return user;
			}
		}
		throw new UserNotFound();
	}

	public static List<User> getUsers() {
		return users;
	}

	public static void setUsers(List<User> users) {
		UserDaoImpl.users = users;
	}


}
