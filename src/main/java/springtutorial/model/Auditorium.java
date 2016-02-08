package springtutorial.model;

import java.util.ArrayList;
import java.util.List;

public class Auditorium {
	private Integer id;
	private String name;
	private Integer numberOfSeats;
	private List<Integer> vipSeats;
	
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
		System.out.println("Inside setVip seats");
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
	
	
}
