package springtutorial.model;

import java.util.Date;
import java.util.List;

public class User {
	private Integer id;
	private String email;
	private String name;
	private List<Ticket> bookedTickets;
	private Date birthday;
	private boolean registered;
	
	public User(Integer id, String email, String name,
			List<Ticket> bookedTickets, Date birthday) {
		super();
		this.id = id;
		this.email = email;
		this.name = name;
		this.bookedTickets = bookedTickets;
		this.birthday = birthday;
	}

	public User() {
		// TODO Auto-generated constructor stub
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Ticket> getBookedTickets() {
		return bookedTickets;
	}

	public void setBookedTickets(List<Ticket> bookedTickets) {
		this.bookedTickets = bookedTickets;
	}
	
	public void addBookedTickets(Ticket bookedTicket) {
		if(this.bookedTickets != null){
			this.bookedTickets.add(bookedTicket);
		}
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public boolean isRegistered() {
		return registered;
	}

	public void setRegistered(boolean isRegistered) {
		this.registered = isRegistered;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", email=" + email + ", name=" + name
				+ ", bookedTickets=" + bookedTickets + "]";
	}
	
}
