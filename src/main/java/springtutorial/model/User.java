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
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((birthday == null) ? 0 : birthday.hashCode());
		result = prime * result + ((bookedTickets == null) ? 0 : bookedTickets.hashCode());
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + (registered ? 1231 : 1237);
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
		User other = (User) obj;
		if (birthday == null) {
			if (other.birthday != null)
				return false;
		} else if (!birthday.equals(other.birthday))
			return false;
		if (bookedTickets == null) {
			if (other.bookedTickets != null)
				return false;
		} else if (!bookedTickets.equals(other.bookedTickets))
			return false;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
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
		if (registered != other.registered)
			return false;
		return true;
	}
}
