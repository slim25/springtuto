package springtutorial.model;

import java.util.Date;

public class Ticket {
	private Integer id;
	private Event event;
	private Integer seatNumber;
	private User user;
	private Date date;
	
	@Override
	public String toString() {
		return "Ticket [id=" + id + ", event=" + event.getName() + ", seatNumber=" + seatNumber + ", user=" + user.getName() + ", date="
				+ date + "]";
	}

	public Ticket() {
		// TODO Auto-generated constructor stub
	}

	public Ticket(Integer id, Event event, Integer seatNumber, User user, Date date) {
		super();
		this.id = id;
		this.event = event;
		this.seatNumber = seatNumber;
		this.user = user;
		this.date = date;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Event getEvent() {
		return event;
	}

	public void setEvent(Event event) {
		this.event = event;
	}

	public Integer getSeatNumber() {
		return seatNumber;
	}

	public void setSeatNumber(Integer seatNumber) {
		this.seatNumber = seatNumber;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
	
}
