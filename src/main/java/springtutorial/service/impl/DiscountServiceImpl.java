package springtutorial.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import springtutorial.discountStrategy.DiscountStrategy;
import springtutorial.model.User;
import springtutorial.service.DiscountService;
@Service
public class DiscountServiceImpl implements DiscountService{
	private List<DiscountStrategy> discountStrategies;

	public DiscountServiceImpl(List<DiscountStrategy> strategies) {
		this.discountStrategies = strategies;
		System.out.println(" Init DiscountServiceImpl");
	}
	public DiscountServiceImpl(){
	
	}
	
	public List<DiscountStrategy> getDiscountStrategies() {
		return discountStrategies;
	}

	@Override
	public DiscountStrategy.DiscountWithPercentage getDiscountPercentage(User user, Date date) {
		DiscountStrategy.DiscountWithPercentage result = null;
		for(DiscountStrategy discStrategy : this.getDiscountStrategies()){
			if(result == null || (result != null && result.getValue()
					.compareTo(discStrategy.getDiscountPercentage(user, date).getValue())<0))
			result = discStrategy.getDiscountPercentage(user, date);
		}
		return result;
	}

}
