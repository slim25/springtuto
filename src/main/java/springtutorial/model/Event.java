package springtutorial.model;

import java.util.Collection;
import java.util.Date;
import java.util.Map;
import java.util.Set;

import springtutorial.persistance.Rating;

public class Event {
	private Integer id;
	private String name;
	private Float price;
	private Rating rating;
//	private Date eventDate;
	private Integer timesPerDay;
//	private Auditorium auditorium;
	private Map<Date, Auditorium> auditoriumAndDate;
	private Set<Ticket> purchasedTickets;


	public Event() {
	}
	
	public Event(Integer id, String name, Float price, Rating rating, Integer timesPerDay,
			Map<Date, Auditorium> auditoriumAndDate, Set<Ticket> purchasedTickets) {
		super();
		this.id = id;
		this.name = name;
		this.price = price;
		this.rating = rating;
		this.timesPerDay = timesPerDay;
		this.auditoriumAndDate = auditoriumAndDate;
		this.purchasedTickets = purchasedTickets;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Float getPrice() {
		return price;
	}

	public void setPrice(Float price) {
		this.price = price;
	}

	public Rating getRating() {
		return rating;
	}

	public void setRating(String rating) {
		this.rating = Rating.valueOf(rating.toUpperCase());
	}


	public Integer getTimesPerDay() {
		return timesPerDay;
	}

	public void setTimesPerDay(Integer timesPerDay) {
		this.timesPerDay = timesPerDay;
	}
	

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Collection<Ticket> getPurchasedTickets() {
		return purchasedTickets;
	}

	public void setPurchasedTickets(Set<Ticket> purchasedTickets) {
		this.purchasedTickets = purchasedTickets;
	}
	public void addPurchasedTicket(Ticket purchasedTicket) {
		if(this.purchasedTickets != null){
			this.purchasedTickets.add(purchasedTicket);
		}
	}

	public Map<Date, Auditorium> getAuditoriumAndDate() {
		return auditoriumAndDate;
	}

	public void setAuditoriumAndDate(Map<Date, Auditorium> auditoriumAndDate) {
		this.auditoriumAndDate = auditoriumAndDate;
	}
	public void assignAuditorium(Auditorium auditorium, Date date){
		this.auditoriumAndDate.put(date, auditorium);
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		for(Ticket ticket: purchasedTickets){
			builder.append("[ " +ticket +" ] ");
		}
		return "Event [id=" + id + ", name=" + name + ", price=" + price + ", rating=" + rating + ", timesPerDay="
				+ timesPerDay + ", auditoriumAndDate=" + auditoriumAndDate + ", purchasedTickets=" + purchasedTickets
				+ "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((price == null) ? 0 : price.hashCode());
		result = prime * result + ((rating == null) ? 0 : rating.hashCode());
		result = prime * result + ((timesPerDay == null) ? 0 : timesPerDay.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Event other = (Event) obj;
		
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (price == null) {
			if (other.price != null)
				return false;
		} else if (!price.equals(other.price))
			return false;
		
		if (rating != other.rating)
			return false;
		if (timesPerDay == null) {
			if (other.timesPerDay != null)
				return false;
		} else if (!timesPerDay.equals(other.timesPerDay))
			return false;
		return true;
	}
	
}
