package springtutorial.main;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import springtutorial.exception.UserNotFound;
import springtutorial.service.AuditoriumService;
import springtutorial.service.BookingService;
import springtutorial.service.EventService;
import springtutorial.service.UserService;

public class App {
	@Autowired
	AuditoriumService auditoriumService;
	@Autowired
	BookingService bookingService;
	@Autowired
	EventService eventService;
	@Autowired
	UserService userService;
	
	public static void main(String[] args) {
		ApplicationContext ctx = new ClassPathXmlApplicationContext("spring/spring-context.xml");
		App app = ctx.getBean(springtutorial.main.App.class);
		
		try {
			System.out.println(app.userService.getById(1));
		} catch (UserNotFound e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
