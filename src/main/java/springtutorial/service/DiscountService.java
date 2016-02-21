package springtutorial.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import springtutorial.discountStrategy.DiscountStrategy;
import springtutorial.model.Event;
import springtutorial.model.User;


public interface DiscountService {
	
	DiscountStrategy.DiscountWithPercentage getDiscountPercentage(User user, 
			Date date);
}
