package tests;

import static org.junit.Assert.*;

import org.junit.Test;
import main.DNA;

public class DNATests {

	@Test
	public void testConstructors() {
		DNA dna = new DNA();
		assertEquals(dna.getGenes().length, DNA.LENGTH);
		
		int[] gene = {1, 2, 3, 4};
		DNA dna2 = new DNA(gene);
		assertEquals(dna2.getGenes(), gene);
	}
	
	@Test
	public void testGetAction(){
		int[] gene = {0, 1, 2, 3, 4, 5, -1};
		DNA dna = new DNA(gene);
		assertEquals(dna.getAction(0), DNA.actions.STAY);
		assertEquals(dna.getAction(1), DNA.actions.UP);
		assertEquals(dna.getAction(2), DNA.actions.RIGHT);
		assertEquals(dna.getAction(3), DNA.actions.DOWN);
		assertEquals(dna.getAction(4), DNA.actions.LEFT);
		assertEquals(dna.getAction(5), DNA.actions.STAY);
		assertEquals(dna.getAction(6), DNA.actions.STAY);
	}

}
