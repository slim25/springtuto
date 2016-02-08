package springtutorial.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import springtutorial.discountStrategy.DiscountStrategy;
import springtutorial.model.Event;
import springtutorial.model.User;


public abstract class DiscountService {
	private List<DiscountStrategy> discountStrategies;


	public DiscountService(List<DiscountStrategy> strategies) {
		this.discountStrategies = strategies;
	}

	public List<DiscountStrategy> getDiscountStrategies() {
		return discountStrategies;
	}
	public abstract Map.Entry<String,Float> getDiscountPercentage(User user, 
			Date date);
}
