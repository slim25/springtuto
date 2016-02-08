package springtutorial.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import springtutorial.dao.AuditoriumDao;
import springtutorial.exception.NotExistSuchAuditorium;
import springtutorial.model.Auditorium;
import springtutorial.service.AuditoriumService;
@Service
public class AuditoriumServiceImpl implements AuditoriumService {

	@Autowired
	@Qualifier("AuditoriumDAO")
	AuditoriumDao auditoriumDao;

	public List<Auditorium> getAuditoriums() {
		return auditoriumDao.getAuditoriums();

	}

	public Integer getSeatsNumber(Integer auditoriumId) throws NotExistSuchAuditorium {
		return auditoriumDao.getSeatsNumber(auditoriumId);
	}

	public List<Integer> getVipSeats(Integer auditoriumId) throws NotExistSuchAuditorium {
		return auditoriumDao.getVipSeats(auditoriumId);
	}

}
