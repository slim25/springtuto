package springtutorial.discountStrategy.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import springtutorial.discountStrategy.DiscountStrategy;
import springtutorial.model.Ticket;
import springtutorial.model.User;

public class EveryTenTicketDiscountStrategy implements DiscountStrategy{

	public DiscountWithPercentage getDiscountPercentage(User user, Date date) {
		List<Ticket> bookedTickets = user.getBookedTickets();
		return (bookedTickets == null || bookedTickets.size() == 0 || bookedTickets.size()%10 != 0) ?
				new DiscountWithPercentage("EveryTenTicketDiscountStrategy",0f) : new DiscountWithPercentage("EveryTenTicketDiscountStrategy",50f);
	}

}
