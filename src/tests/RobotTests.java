package tests;

import static org.junit.Assert.*;

import org.junit.Test;
import main.*;

public class RobotTests {

	@Test
	public void testRobotConstructors() {
		Robot rob = new Robot(0,10);
		assertEquals(rob.getX(), 0);
		assertEquals(rob.getY(), 10);
		assertEquals(rob.getScore(), 0);
		assertEquals(rob.getEnergy(), 100);
		
		Robot robo = new Robot(11, 12, new DNA(new int[1024]));
		assertEquals(11, robo.getX());
		assertEquals(12, robo.getY());
		int sum = 0;
		DNA dna = robo.getDNA();
		for (int c : dna.getGenes()){
			sum += c;
		}
		assertEquals(sum, 0);
	}

}
