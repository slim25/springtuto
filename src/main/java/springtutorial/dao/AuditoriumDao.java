package springtutorial.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import springtutorial.exception.NotExistSuchAuditorium;
import springtutorial.model.Auditorium;


public interface AuditoriumDao {
	Integer getSeatsNumber(Integer auditoriumId) throws NotExistSuchAuditorium;

	List<Integer> getVipSeats(Integer auditoriumId) throws NotExistSuchAuditorium;

	List<Auditorium> getAuditoriums();

}
