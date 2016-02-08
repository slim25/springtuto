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
	
	
}
