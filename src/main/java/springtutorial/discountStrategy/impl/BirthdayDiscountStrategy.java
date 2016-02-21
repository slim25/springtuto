package springtutorial.discountStrategy.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.Map;

import springtutorial.discountStrategy.DiscountStrategy;
import springtutorial.model.User;

public class BirthdayDiscountStrategy implements DiscountStrategy {

	public DiscountWithPercentage getDiscountPercentage(User user, Date eventDate) {
		Date userBirthday = user.getBirthday();
		Calendar userCalendar = Calendar.getInstance();
		Calendar eventCalendar = Calendar.getInstance();
		userCalendar.setTime(userBirthday);
		eventCalendar.setTime(eventDate);
		
		return (userCalendar.get(Calendar.MONTH) == eventCalendar.get(Calendar.MONTH))
				&& (userCalendar.get(Calendar.DAY_OF_MONTH) == eventCalendar.get(Calendar.DAY_OF_MONTH)) ? 
						new DiscountWithPercentage("BirthdayDiscountStrategy",5f) : new DiscountWithPercentage("BirthdayDiscountStrategy",0f);

	}

}
