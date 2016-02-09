package springtutorial.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import springtutorial.discountStrategy.DiscountStrategy;
import springtutorial.model.User;
import springtutorial.service.DiscountService;
@Service
public class DiscountServiceImpl extends DiscountService{

	public DiscountServiceImpl(List<DiscountStrategy> strategies) {
		super(strategies);
		System.out.println(" Init DiscountServiceImpl");
	}
	public DiscountServiceImpl() {
		this(null);
	}

	@Override
	public Map.Entry<String,Float> getDiscountPercentage(User user, Date date) {
		Map.Entry<String,Float> result = null;
		for(DiscountStrategy discStrategy : this.getDiscountStrategies()){
			if(result == null || (result != null && result.getValue()
					.compareTo(discStrategy.getDiscountPercentage(user, date).getValue())<0))
			result = discStrategy.getDiscountPercentage(user, date);
		}
		return result;
	}

}
