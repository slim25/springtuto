package springtutorial.model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import org.springframework.jdbc.core.RowMapper;

public class Ticket implements RowMapper<Ticket>{
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((date == null) ? 0 : date.hashCode());
		result = prime * result + ((event == null) ? 0 : event.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((seatNumber == null) ? 0 : seatNumber.hashCode());
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
		Ticket other = (Ticket) obj;
		if (date == null) {
			if (other.date != null)
				return false;
		} else if (!(date.compareTo(other.date) == 0))
			return false;
		if (event == null) {
			if (other.event != null)
				return false;
		} else if (!event.getId().equals(other.event.getId()))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (seatNumber == null) {
			if (other.seatNumber != null)
				return false;
		} else if (!seatNumber.equals(other.seatNumber))
			return false;
		
		return true;
	}

	@Override
	public Ticket mapRow(ResultSet resultSet, int arg1) throws SQLException {
		Ticket ticket = new Ticket();
		ticket.setId(resultSet.getInt("id"));
		ticket.setDate(resultSet.getDate("event_date"));
		ticket.setSeatNumber(resultSet.getInt("seatNumber"));
		return ticket;
	}
	
}
