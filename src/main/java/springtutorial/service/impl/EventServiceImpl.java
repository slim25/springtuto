package springtutorial.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import springtutorial.dao.EventDao;
import springtutorial.exception.EventNotFound;
import springtutorial.model.Auditorium;
import springtutorial.model.Event;
import springtutorial.service.EventService;
@Service
public class EventServiceImpl implements EventService {
	@Autowired
	@Qualifier("EventDAO")
	EventDao eventDao;

	public void create(Event event) {
		eventDao.create(event);

	}

	public boolean remove(Event event) {
		return eventDao.remove(event);
	}

	public Event getByName(String name) throws EventNotFound {
		return eventDao.getByName(name);
	}

	public List<Event> getAll() {
		return eventDao.getAll();
	}

	public List<Event> getForDateRange(Date from, Date to) {
		return eventDao.getForDateRange(from, to);
	}

	public List<Event> getNextEvents(Date to) {
		return eventDao.getNextEvents(to);
	}

	public void assignAuditorium(Event event, Auditorium auditorium, Date date) throws EventNotFound {
		 eventDao.assignAuditorium(event, auditorium, date);
	}

}
