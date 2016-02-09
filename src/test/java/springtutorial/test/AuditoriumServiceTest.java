package springtutorial.test;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import springtutorial.exception.NotExistSuchAuditorium;
import springtutorial.model.Auditorium;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class AuditoriumServiceTest extends BaseServiceTest{
	private static Auditorium testAuditorium;
	@Test
	public void AtestGetAuditoriums(){
		List<Auditorium> allAuditoriums = new ArrayList<>();
		List<Integer> vipSeats1 = new ArrayList<>();
		vipSeats1.add(15);vipSeats1.add(16);vipSeats1.add(17);
		testAuditorium = new Auditorium(1, "Blue", 100, vipSeats1);
		allAuditoriums.add(testAuditorium);
		List<Integer> vipSeats2 = new ArrayList<>();
		vipSeats2.add(7);vipSeats2.add(8);vipSeats2.add(9);vipSeats2.add(10);
		allAuditoriums.add(new Auditorium(2, "Green", 65, vipSeats2));
		List<Integer> vipSeats3 = new ArrayList<>();
		vipSeats3.add(90);vipSeats3.add(100);vipSeats3.add(150);
		allAuditoriums.add(new Auditorium(3, "Black", 150, vipSeats3));
		List<Auditorium> receivedAuditoriums = auditoriumService.getAuditoriums();
		assertTrue("Received auditoriums are not equal to template list of auditoriums",receivedAuditoriums.equals(allAuditoriums));

	}
	
	@Test
	public void BtestGetSeatsNumber() throws NotExistSuchAuditorium{
		Integer seatsNumber = auditoriumService.getSeatsNumber(testAuditorium.getId());
		assertTrue("Seats number are not equals to template seats number",seatsNumber.equals(testAuditorium.getNumberOfSeats()));

	}
	@Test
	public void CtestGetVipSeats() throws NotExistSuchAuditorium{
		List<Integer> vipSeats = auditoriumService.getVipSeats(testAuditorium.getId());
		assertTrue("Seats number are not equals to template seats number",vipSeats.equals(testAuditorium.getVipSeats()));
	}
}
