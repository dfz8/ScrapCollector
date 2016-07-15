package main;

public class Robot implements Comparable<Robot> {
	
	private int energy;
	private DNA dna;
	private int pos_x;
	private int pos_y;
	private int score;
	private int distanceTraveled;
	private int start_x;
	private int start_y;
	static final int OIL_WORTH = 100;
	static final int SCRAP_WORTH = 1000;
	

	public Robot(int x, int y){
		this.energy = 100;
		this.dna = new DNA();
		this.pos_x = x;
		this.pos_y = y;
		this.start_x = x;
		this.start_y = y;
		this.score = 0;
		this.distanceTraveled = 0;
	}

	public Robot(int x, int y, DNA dna){
		this(x, y);
		this.dna = dna; 
	}

	public void update(ScrapWorld.OBJECTS[][] map){		
		//always pick up things in the current tile
		if(map[this.pos_y][this.pos_x] == ScrapWorld.OBJECTS.OIL){
			this.energy += 10;
			this.score += OIL_WORTH;
			map[this.pos_y][this.pos_x] = ScrapWorld.OBJECTS.EMPTY;
		} else if(map[this.pos_y][this.pos_x] == ScrapWorld.OBJECTS.SCRAP) {
			this.score += SCRAP_WORTH;
			map[this.pos_y][this.pos_x] = ScrapWorld.OBJECTS.EMPTY;
		}
		
		//find the index of the current action
		int index =  64*map[this.pos_y+1][this.pos_x].value() +
				 	 16*map[this.pos_y][this.pos_x+1].value() +
					  4*map[this.pos_y-1][this.pos_x].value() +
					    map[this.pos_y][this.pos_x-1].value();		
			
		switch(dna.getAction(index)){ 
			case UP:
				if(map[this.pos_y+1][this.pos_x] != ScrapWorld.OBJECTS.WALL){
					this.pos_y++;
					int dist = Math.abs(this.pos_x - this.start_x) 
							 + Math.abs(this.pos_y - this.start_y);
					if(dist > this.distanceTraveled)
						this.distanceTraveled = dist;
				}
				break;
			case RIGHT:
				if(map[this.pos_y][this.pos_x+1] != ScrapWorld.OBJECTS.WALL){
					this.pos_x++;
					int dist = Math.abs(this.pos_x - this.start_x) 
							 + Math.abs(this.pos_y - this.start_y);
					if(dist > this.distanceTraveled)
						this.distanceTraveled = dist;
				}
				break;
			case DOWN:
				if(map[this.pos_y-1][this.pos_x] != ScrapWorld.OBJECTS.WALL){
					this.pos_y--;
					int dist = Math.abs(this.pos_x - this.start_x) 
							 + Math.abs(this.pos_y - this.start_y);
					if(dist > this.distanceTraveled)
						this.distanceTraveled = dist;
				}		
				break;		
			case LEFT:
				if(map[this.pos_y][this.pos_x-1] != ScrapWorld.OBJECTS.WALL){
					this.pos_x--;
					int dist = Math.abs(this.pos_x - this.start_x) 
							 + Math.abs(this.pos_y - this.start_y);
					if(dist > this.distanceTraveled)
						this.distanceTraveled = dist;
				}	
				break;
			case STAY:
			default:
				this.energy++;
		}
		
		//each update uses up energy
		this.energy--;
	}

	public int getX() { return this.pos_x; }
	public int getY() { return this.pos_y; }
	public int getEnergy() { return this.energy; }
	public int getScore() { return this.score; }
	public DNA getDNA() { return this.dna; }
	public int getDistanceTraveled() { return this.distanceTraveled; }

	@Override
	public int compareTo(Robot robot) {
		if(this.score > robot.score) 
			return 1;
		if(this.score < robot.score)
			return -1;
		if(this.getDistanceTraveled() > robot.getDistanceTraveled())
			return 1;
		if(this.getDistanceTraveled() < robot.getDistanceTraveled())
			return -1;
		return 0;
	}
	
}
