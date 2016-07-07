package main;

public class DNA {
	public static enum actions {
		UP, RIGHT, DOWN, LEFT, PICKUP, STAY;
	};
	private int[] genes;

	public DNA(){
		this.genes = new int[1024];
		for(int  i = 0; i < this.genes.length; i++) {
			this.genes[i] = (int)(Math.random()*6);
		}
	}

	public DNA(int[] genes){
		this.genes = genes;
	}

	public int[] getGenes(){
		return this.genes;
	}

	public actions getAction(int num){
		switch(genes[num]){
			case 0:
				return actions.UP;
			case 1:
				return actions.RIGHT;
			case 2:
				return actions.DOWN;
			case 3:
				return actions.LEFT;
			case 4:
				return actions.PICKUP;
			case 5:
			default:
				return actions.STAY;
		}
	}
}
