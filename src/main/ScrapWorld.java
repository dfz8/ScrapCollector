package main;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
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
	public static final int GENERATIONS = 100;
	public static final int SLEEP_TIME = 200;
	public static final double MUTATION_RATE = 0.01;
	public static final int ROBOTS_PER_GENERATION = 40; //must be even
	public static boolean displayOn = false;
	public static PrintWriter out;
	
	public static void main(String[] args) throws IOException, InterruptedException {
		//instantiate map + robots
		out = new PrintWriter(new FileWriter(new File("results")));
		OBJECTS[][] map = readFile();
		
		//create first generation of robots
		Robot[] allRobots = new Robot[ROBOTS_PER_GENERATION];
			for(int i = 0; i < allRobots.length; i++){
				allRobots[i] = new Robot(1,1);		
		}
		Robot[] orderedRobots;
		
		//simulate multiple generations
		int gen = 0;
		for(; gen < GENERATIONS; gen++){
			orderedRobots = simulateRound(map, allRobots, gen);
			allRobots = createNewGeneration(orderedRobots);
		}
		allRobots = simulateRound(map, allRobots, gen);
		out.close();		
		
		Arrays.sort(allRobots);
		saveRobot(allRobots[allRobots.length-1]);
	}

	private static Robot[] simulateRound(OBJECTS[][] map, Robot[] allRobots, int generation) throws InterruptedException {
		Robot curRobot = allRobots[0];
		OBJECTS[][] curMap = copyMap(map);	
		int stepCount = 0;
		
		out.println("==============================================================");
		out.println("Results for Generation: " + generation);
		out.println("==============================================================");
		for(int i = 0; i < allRobots.length - 1; i++){
			curRobot = allRobots[i];
			curMap = copyMap(map);
			stepCount = 0;
			while( curRobot.getEnergy() > 0 && stepCount < MAX_STEPS){
				curRobot.update(curMap);
				stepCount++;
			}
			out.println("Robot " + (i+1) + " scored " + curRobot.getScore() + ", distance traversed: " + curRobot.getDistanceTraveled());
		}
		curRobot = allRobots[allRobots.length-1];
		curMap = copyMap(map);
		stepCount = 0;
		while( curRobot.getEnergy() > 0 && stepCount < MAX_STEPS){
			if(displayOn){
				System.out.println("Step: " + stepCount + ", score: " + curRobot.getScore());
				print(curMap, curRobot);
				Thread.sleep(SLEEP_TIME);
			}
			curRobot.update(curMap);
			stepCount++;
		}
		out.println("Robot " + allRobots.length + " scored " + curRobot.getScore() + ", distance traversed: " + curRobot.getDistanceTraveled());
		
		return allRobots;
	}

	private static Robot[] createNewGeneration(Robot[] oldGen){
		Arrays.sort(oldGen);
		Robot[] newGen = new Robot[oldGen.length];
		int splicePoint;
		int[] genes1, genes2, newGenes1, newGenes2;
		for(int i = 0; i < oldGen.length/2; i++){
			genes1 = oldGen[oldGen.length - 2*i - 1].getDNA().getGenes();
			genes2 = oldGen[oldGen.length - 2*i - 2].getDNA().getGenes();

			newGenes1 = new int[genes1.length];
			newGenes2 = new int[genes2.length];
			
			splicePoint = (int)(Math.random()*newGenes1.length);
			int j = 0;
			for(; j < splicePoint; j++){
				if(Math.random() < MUTATION_RATE)
					newGenes1[j] = (int)(Math.random()*DNA.actions.values().length);
				else
					newGenes1[j] = genes1[j];
				if(Math.random() < MUTATION_RATE)
					newGenes2[j] = (int)(Math.random()*DNA.actions.values().length);
				else
					newGenes2[j] = genes2[j];
			}
			for(; j < newGenes1.length; j++){
				if(Math.random() < MUTATION_RATE)
					newGenes1[j] = (int)(Math.random()*DNA.actions.values().length);
				else
					newGenes1[j] = genes2[j];
				if(Math.random() < MUTATION_RATE)
					newGenes2[j] = (int)(Math.random()*DNA.actions.values().length);
				else
					newGenes2[j] = genes1[j];
			}
			newGen[2*i] = new Robot(1,1, new DNA(newGenes1));
			newGen[2*i+1] = new Robot(1,1, new DNA(newGenes2));
		}
		return newGen;
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

	public static void saveRobot(Robot robo) throws IOException {
		PrintWriter saver = new PrintWriter(new FileWriter("high-scores.txt", true));
		saver.println("SCORE:" + robo.getScore());
		saver.println("Distance:" + robo.getDistanceTraveled());
		saver.println("Generation: " + GENERATIONS);
		saver.println("Gene: " + robo.getDNA().toString());
		saver.println("=================================");
		saver.close();
	}
}
