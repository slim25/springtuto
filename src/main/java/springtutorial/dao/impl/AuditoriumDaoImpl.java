package springtutorial.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import springtutorial.dao.AuditoriumDao;
import springtutorial.exception.NotExistSuchAuditorium;
import springtutorial.model.Auditorium;
@Repository
public class AuditoriumDaoImpl implements AuditoriumDao{
	public static List<Auditorium> auditoriums;
	
	public AuditoriumDaoImpl(List<Auditorium> auditoriums){
		this.auditoriums = auditoriums;
	}
	public AuditoriumDaoImpl(){
		
	}

	public Integer getSeatsNumber(Integer auditoriumId) throws NotExistSuchAuditorium {
		for(Auditorium auditorium : auditoriums){
			if(auditorium.getId() == auditoriumId){
				return auditorium.getNumberOfSeats();
			}
		}
		throw new NotExistSuchAuditorium();
	}

	public List<Integer> getVipSeats(Integer auditoriumId) throws NotExistSuchAuditorium {
		
		for(Auditorium auditorium : auditoriums){
			if(auditorium.getId() == auditoriumId){
				return auditorium.getVipSeats();
			}
		}
		throw new NotExistSuchAuditorium();
	}

	public List<Auditorium> getAuditoriums() {
		return auditoriums;
	}

	
	
	
	
}
