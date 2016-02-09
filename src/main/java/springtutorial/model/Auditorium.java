package springtutorial.model;

import java.util.ArrayList;
import java.util.List;

public class Auditorium {
	private Integer id;
	private String name;
	private Integer numberOfSeats;
	private List<Integer> vipSeats;
	
	public Auditorium(Integer id, String name, Integer numberOfSeats, List<Integer> vipSeats) {
		super();
		this.id = id;
		this.name = name;
		this.numberOfSeats = numberOfSeats;
		this.vipSeats = vipSeats;
	}

	public Auditorium() {
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getNumberOfSeats() {
		return numberOfSeats;
	}

	public void setNumberOfSeats(Integer numberOfSeats) {
		this.numberOfSeats = numberOfSeats;
	}

	public List<Integer> getVipSeats() {
		return vipSeats;
	}

	public void setVipSeats(String vipSeats) {
		String [] vipSeatsNumbers = vipSeats.split(",");
		if(vipSeatsNumbers.length > 0){
			this.vipSeats = new ArrayList<>();
		}else{
			return;
		}
		for(String vipSeatNumber : vipSeatsNumbers){
			this.vipSeats.add(Integer.valueOf(vipSeatNumber));
		}
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "Auditorium [id=" + id + ", name=" + name + ", numberOfSeats=" + numberOfSeats + ", vipSeats=" + vipSeats
				+ "]";
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((numberOfSeats == null) ? 0 : numberOfSeats.hashCode());
		result = prime * result + ((vipSeats == null) ? 0 : vipSeats.hashCode());
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
		Auditorium other = (Auditorium) obj;
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
		if (numberOfSeats == null) {
			if (other.numberOfSeats != null)
				return false;
		} else if (!numberOfSeats.equals(other.numberOfSeats))
			return false;
		if (vipSeats == null) {
			if (other.vipSeats != null)
				return false;
		} else if (!vipSeats.equals(other.vipSeats))
			return false;
		return true;
	}

	
}
