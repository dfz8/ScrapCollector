package main;

public class Robot {
	private int energy;
	private DNA dna;

	public Robot(){
		this.energy = 100;
		this.dna = new DNA();
	}

	public Robot(DNA dna){
		this.dna = dna;
	}

	public void update(int[] surroundings){
		int index = 16*surroundings[0] +
			8*surroundings[1] +
			4*surroundings[2] +
			2*surroundings[3] + 
			surroundings[4];
		switch(dna.getAction(index)){
			case UP:
			case LEFT:
			case DOWN:
			case RIGHT:
			case PICKUP:
			default:
		}
		this.energy--;
	}
}
