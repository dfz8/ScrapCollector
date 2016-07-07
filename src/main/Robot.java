package main;

public class Robot implements Comparable<Robot> {
	private int energy;
	private DNA dna;
	private int pos_x;
	private int pos_y;
	private int score;
	private int distanceTraveled;

	public Robot(int x, int y){
		this.energy = 100;
		this.dna = new DNA();
		this.pos_x = x;
		this.pos_y = y;
		this.score = 0;
		this.distanceTraveled = 0;
	}

	public Robot(int x, int y, DNA dna){
		this(x, y);
		this.dna = dna; 
	}

	public void update(ScrapWorld.OBJECTS[][] map){
		//surroundings = stuff in { up, right, down, left, center } tile
		int index = 256*map[pos_y+1][pos_x].value() +
				 	 64*map[pos_y][pos_x+1].value() +
					 16*map[pos_y-1][pos_x].value() +
					  4*map[pos_y][pos_x-1].value() + 
					    map[pos_y][pos_x].value();
		switch(dna.getAction(index)){ 
			case UP:
				if(map[pos_y+1][pos_x] != ScrapWorld.OBJECTS.WALL){
					this.pos_y++;
					this.distanceTraveled++;
				}
				break;
			case RIGHT:
				if(map[pos_y][pos_x+1] != ScrapWorld.OBJECTS.WALL){
					this.pos_x++;
					this.distanceTraveled++;
				}
				break;
			case DOWN:
				if(map[pos_y-1][pos_x] != ScrapWorld.OBJECTS.WALL){
					this.pos_y--;
					this.distanceTraveled++;
				}		
				break;		
			case LEFT:
				if(map[pos_y][pos_x-1] != ScrapWorld.OBJECTS.WALL){
					this.pos_x--;
					this.distanceTraveled++;
				}	
				break;			
			case PICKUP:
				if(map[pos_y][pos_x] == ScrapWorld.OBJECTS.OIL){
					this.energy += 10;
					this.score += 20;
					map[pos_y][pos_x] = ScrapWorld.OBJECTS.EMPTY;
				} else if(map[pos_y][4] == ScrapWorld.OBJECTS.SCRAP) {
					this.score += 100;
					map[pos_y][pos_x] = ScrapWorld.OBJECTS.EMPTY;
				}
				break;
			default: //case STAY: save energy
				this.energy++;
		}
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
		if(this.distanceTraveled > robot.distanceTraveled)
			return 1;
		if(this.distanceTraveled < robot.distanceTraveled)
			return -1;
		return 0;
	}
	
}
