package main;
import java.io.*;
import java.util.Arrays;
import java.util.Objects;
import java.util.StringTokenizer;

public class ScrapWorld {

	public static enum OBJECTS {
		WALL(0, "x"), SCRAP(1,"s"), OIL(2,"o"), EMPTY(3,".");
		
		private final int value;
		private String str;
		private OBJECTS(int v, String str){
			this.value = v;
			this.str = str;
		}
		public int value() { return this.value; }
		public String str() { return this.str; }
	};
	
	public static int width;
	public static int height;
	public static final int MAX_STEPS = 125;
	public static final int GENERATIONS = 2;
	
	public static void main(String[] args) throws IOException, InterruptedException {
		//instantiate map + robots
		OBJECTS[][] map = readFile();
		Robot[] allRobots = new Robot[10];
		for(int i = 0; i < allRobots.length; i++){
			allRobots[i] = new Robot(1,1);
		}
		
		Robot[] orderedRobots;
		for(int gen = 0; gen < GENERATIONS; gen++){
			orderedRobots = simulateRound(map, allRobots);
			allRobots = createNewGeneration(orderedRobots);			
		}
		
	}
	
	private static Robot[] createNewGeneration(Robot[] oldGen){
		Robot[] newGen = new Robot[oldGen.length];
		//...
		return newGen;
	}

	private static Robot[] simulateRound(OBJECTS[][] map, Robot[] allRobots) throws InterruptedException {
		Robot curRobot = allRobots[0];
		OBJECTS[][] curMap = copyMap(map);
		
		//print out path for first robot of each generation
		print(curMap, curRobot);		
		int stepCount = 0;
		while( curRobot.getEnergy() > 0 && stepCount < MAX_STEPS){
			System.out.println("Step: " + stepCount + ", score: " + curRobot.getScore() + ", energy: " + curRobot.getEnergy());
			curRobot.update(curMap);
			print(curMap, curRobot);
			stepCount++;
			Thread.sleep(300);
		}	
		System.out.println("Robot 1 scored " + curRobot.getScore() + ", distance traversed: " + curRobot.getDistanceTraveled());
		for(int i = 1; i < allRobots.length; i++){
			curRobot = allRobots[i];
			curMap = copyMap(map);
			stepCount = 0;
			while( curRobot.getEnergy() > 0 && stepCount < MAX_STEPS){
				curRobot.update(curMap);
				stepCount++;
			}
			System.out.println("Robot " + (i+1) + " scored " + curRobot.getScore() + ", distance traversed: " + curRobot.getDistanceTraveled());
		}
		
		Arrays.sort(allRobots);
		return allRobots;
	}
	
	public static OBJECTS[][] copyMap(OBJECTS[][] map){
		OBJECTS[][] newMap = new OBJECTS[height][width];
		for(int r = 0; r < height; r++){
			for(int c = 0; c < width; c++){
				newMap[r][c] = map[r][c];
			}
		}
		return newMap;
	}
	
	public static void print(OBJECTS[][] map, Robot robot){
		String[][] printMap = new String[height][width];
		for(int r = 0; r < height; r++){
			for(int c = 0; c < width; c++){
				printMap[r][c] = map[r][c].str();
			}
		}
		if(printMap[robot.getX()][robot.getY()].equals(".")){
			printMap[robot.getX()][robot.getY()] = "r";
		} else {
			printMap[robot.getX()][robot.getY()] = "R";
		}
		
		for(int r = 0; r < height; r++){
			for(int c = 0; c < width; c++){
				System.out.print(printMap[r][c] + " ");
			}
			System.out.println();
		}
	}
	
	public static OBJECTS[][] readFile() throws IOException {
		BufferedReader in = new BufferedReader(new FileReader(new File("resources/map.txt")));
		String[] dim = in.readLine().split(" ");

		height = Integer.parseInt(dim[0]);	
		width = Integer.parseInt(dim[1]);	
		OBJECTS[][] map = new OBJECTS[height][width];
		
		StringTokenizer st;
		for(int row = 0; row < height; row++){
			st = new StringTokenizer(in.readLine());
			for(int col = 0; col < width; col++){
				switch( st.nextToken() ){
				case ".":
					map[row][col] = OBJECTS.EMPTY;
					break;
				case "o":
					map[row][col] = OBJECTS.OIL;
					break;
				case "s":
					map[row][col] = OBJECTS.SCRAP;
					break;
				default:
				case "x":
					map[row][col] = OBJECTS.WALL;
					break;
				}
			}
		}
		in.close();
		
		return map;		
	}
}
