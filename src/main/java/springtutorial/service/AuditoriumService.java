package springtutorial.service;

import java.util.List;

import org.springframework.stereotype.Service;

import springtutorial.exception.NotExistSuchAuditorium;
import springtutorial.model.Auditorium;

public interface AuditoriumService {
	List<Auditorium> getAuditoriums();

	Integer getSeatsNumber(Integer auditoriumId) throws NotExistSuchAuditorium;

	List<Integer> getVipSeats(Integer auditoriumId) throws NotExistSuchAuditorium;

}
