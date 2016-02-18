package springtutorial.test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;


import org.junit.Test;

import springtutorial.aspect.LuckyWinnerAspect;

public class LuckyWinnerAspectTest extends BaseServiceTest{
	
	@Test
	public void testCheckLuckyWinner() {
		for (int i = 0; i < 10; i++) {
			boolean result1 = bookingService.bookTicket(null, null);
			if(LuckyWinnerAspect.luckyWinner == true){
				assertTrue(result1);
			}else{
				assertFalse(result1);
			}
		}
		
	}
}
