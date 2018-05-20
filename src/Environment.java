import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.sql.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

/**
 * the environment has GRIDSIZE * GRIDSIZE grids and a global temperature
 * it updates every year, daisy can sprout or die during passing time 
 */

/**
 * the extension of this model is an added third type of daisy called orange
 * daisy the orange daisy is a kind of mutated daisy. there is no orange daisy
 * in the beginning, but it can be grown in a grid whose neighbors containing at
 * least one daisy in a very low probability. and its offspring can only be
 * white or black depending on the global temperature
 * 
 * this orange daisy is added to mediate the environment and prevent either
 * white or black daisy to die out
 */

public class Environment {
	// max age of daisy
	private static final int MAXAGE = 25;

	// specifies the size of the environment which is GRIDSIZE * GRIDSIZE
	private static final int GRIDSIZE = 29;

	// use 2-dimension array to specify every grid and the relationship between them
	private Grid[][] currentGrid;

	// specifies all the living white daisies
	private ArrayList<Daisy> whiteDaisy;

	// specifies all the living black daisies
	private ArrayList<Daisy> blackDaisy;

	// specifies all the living orange daisies
	private ArrayList<Daisy> orangeDaisy;

	private ArrayList<Rabbit> rabbitList;

	// keyboard input
	public static Scanner keyboard = new Scanner(System.in);

	// specifies the global temperature
	private double globalTemperature;

	// when choosing scenario ramp-up-ramp-down, this specifies the trend of current
	// change
	// of solar luminosity
	private boolean luminosityIncrease;

	/**
	 * create a new environment
	 */
	public Environment() {
		whiteDaisy = new ArrayList<Daisy>();
		blackDaisy = new ArrayList<Daisy>();
		orangeDaisy = new ArrayList<Daisy>();
		currentGrid = new Grid[GRIDSIZE][GRIDSIZE];
		for (int i = 0; i < GRIDSIZE; i++) {
			for (int j = 0; j < GRIDSIZE; j++) {
				currentGrid[i][j] = new Grid();
			}
		}
		// newGrid = new Grid[GRIDSIZE][GRIDSIZE];
		globalTemperature = 0;
		luminosityIncrease = true;
	}

	/**
	 * @return the global temperature
	 */
	public double getGlobalTemperature() {
		return globalTemperature;
	}

	/**
	 * set the global temperature
	 */
	public void setGlobalTemperature(double globalTemperature) {
		this.globalTemperature = globalTemperature;
	}

	/**
	 * @return the number of white daisies
	 */
	public int numberOfWhite() {
		return whiteDaisy.size();
	}

	/**
	 * @return the number of black daisies
	 */
	public int numberOfBlack() {
		return blackDaisy.size();
	}

	/**
	 * @return the number of orange daisies
	 */
	public int numberOfOrange() {
		return orangeDaisy.size();
	}

	/**
	 * initialize the environment, put original white and black daisies on grids
	 * just like the set-up in Netlogo
	 */
	public void init() {
		System.out.println("set the albedo of white daisy :");
		Parameter.setAlbedoWhite(keyboard.nextDouble());

		System.out.println("set the albedo of black daisy :");
		Parameter.setAlbedoBlack(keyboard.nextDouble());

		System.out.println("set the percentage of white daisies (%) :");
		Parameter.setPercentageOfWhite(keyboard.nextDouble());

		System.out.println("set the percentage of black daisies (%) :");
		Parameter.setPercentageOfBlack(keyboard.nextDouble());

		System.out.println("set the percentage of rabbit (%) :");
		Parameter.setPercentageOfRabbit(keyboard.nextDouble());

		System.out.println("set the albedo of surface :");
		Parameter.setAlbedoSurface(keyboard.nextDouble());

		System.out.println("set solar luminosity :");
		Parameter.setLuminosity(keyboard.nextDouble());

		int whiteSize = (int) ((GRIDSIZE * GRIDSIZE) * Parameter.getPercentageOfWhite() / 100);
		int blackSize = (int) ((GRIDSIZE * GRIDSIZE) * Parameter.getPercentageOfBlack() / 100);
		int rabbitSize = (int) ((GRIDSIZE * GRIDSIZE) * Parameter.getPercentageOfRabbit() / 100);

		HashSet<Integer> daisiesPosition = new HashSet<Integer>();

		// put white daisies on grids
		while (whiteSize > daisiesPosition.size()) {
			int currentCount = daisiesPosition.size();
			int position = (int) (Math.random() * (GRIDSIZE * GRIDSIZE));
			daisiesPosition.add(position);

			if (currentCount < daisiesPosition.size()) {
				int age = (int) (Math.random() * MAXAGE);
				int row = position / GRIDSIZE;
				int column = position % GRIDSIZE;
				Daisy newDaisy = new Daisy(Parameter.getAlbedoWhite(), age, "white");
				putDaisyOnGrid(row, column, currentGrid, newDaisy);
				whiteDaisy.add(newDaisy);
			}
		}

		// put black daisies on grids
		while (blackSize > (daisiesPosition.size() - whiteSize)) {
			int currentCount = daisiesPosition.size();
			int position = (int) (Math.random() * (GRIDSIZE * GRIDSIZE));
			daisiesPosition.add(position);

			if (currentCount < daisiesPosition.size()) {
				int age = (int) (Math.random() * MAXAGE);
				int row = position / GRIDSIZE;
				int column = position % GRIDSIZE;
				Daisy newDaisy = new Daisy(Parameter.getAlbedoBlack(), age, "black");
				putDaisyOnGrid(row, column, currentGrid, newDaisy);
				blackDaisy.add(newDaisy);
			}
		}

		// put black daisies on grids
		while (blackSize > (daisiesPosition.size() - whiteSize)) {
			int currentCount = daisiesPosition.size();
			int position = (int) (Math.random() * (GRIDSIZE * GRIDSIZE));
			daisiesPosition.add(position);

			if (currentCount < daisiesPosition.size()) {
				int age = (int) (Math.random() * MAXAGE);
				int row = position / GRIDSIZE;
				int column = position % GRIDSIZE;
				Daisy newDaisy = new Daisy(Parameter.getAlbedoBlack(), age, "black");
				putDaisyOnGrid(row, column, currentGrid, newDaisy);
				blackDaisy.add(newDaisy);
			}
		}

		System.out.println("set the scenario (choose one of the five scenarios below) :" + "and just type the number");
		System.out.println("1. maintain current luminosity");
		System.out.println("2. low solar luminosity");
		System.out.println("3. our solar luminosity");
		System.out.println("4. high solar luminosity");
		System.out.println("5. ramp-up-ramp-down");

		Parameter.setScenario(keyboard.nextInt());
		if (Parameter.getScenario() == 2) {
			Parameter.setLuminosity(0.6);
		}
		if (Parameter.getScenario() == 3) {
			Parameter.setLuminosity(1);
		}
		if (Parameter.getScenario() == 4) {
			Parameter.setLuminosity(1.4);
		}
		if (Parameter.getScenario() == 5) {
			Parameter.setLuminosity(0.8);
		}

		calcEachTemperature();
		calcGlobalTemperature();
	}

	/**
	 * put a daisy on a grid
	 */
	public void putDaisyOnGrid(int row, int column, Grid[][] grid, Daisy daisy) {
		grid[row][column].setCurrentDaisy(daisy);
	}

	public void pubRabbitOnGrid(int row, int column, Grid[][] grid, Daisy daisy) {
		grid[row][column].setCurrentDaisy(daisy);
	}

	/**
	 * calculate all the grid's local temperature
	 */
	public void calcEachTemperature() {
		for (int i = 0; i < GRIDSIZE; i++) {
			for (int j = 0; j < GRIDSIZE; j++) {
				currentGrid[i][j].calcLocalTemp();
			}
		}
	}

	/**
	 * calculate the global temperature
	 */
	public void calcGlobalTemperature() {
		double temperature = 0;
		for (int i = 0; i < GRIDSIZE; i++) {
			for (int j = 0; j < GRIDSIZE; j++) {
				temperature = temperature + currentGrid[i][j].getLocalTemperature();
			}
		}
		globalTemperature = temperature / (GRIDSIZE * GRIDSIZE);
	}

	/**
	 * simulates the change of environment when time passes, just like the go in
	 * Netlogo
	 */
	public void go() {
		int year;
		System.out.println("type the number of years you want the system to go :");
		year = keyboard.nextInt();
		int time = 1;

		Object[] head = { "year", "num_white", "num_black", "num_orange", "global_temp", "luminosity" };
		List<Object> headList = Arrays.asList(head);

		File csvFile = null;
		BufferedWriter csvWriter = null;
		try {
			csvFile = new File("result.csv");
			csvWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(csvFile), "GB2312"), 1024);

			writeRow(headList, csvWriter);

			List<Object> rowList = new ArrayList<Object>();
			rowList.add(0);
			rowList.add(numberOfWhite());
			rowList.add(numberOfBlack());
			rowList.add(numberOfOrange());
			rowList.add(convertDouble(globalTemperature));
			rowList.add(convertDouble(Parameter.getLuminosity()));
			writeRow(rowList, csvWriter);

		} catch (Exception e) {
			e.printStackTrace();
		}

		while (year > 0) {
			if (Parameter.getScenario() == 5) {
				rampUpRampDown();
			}

			calcEachTemperature();
			diffuse();
			ageAllDaisy();
			sproutDaisy();
			calcGlobalTemperature();

			List<Object> rowList = new ArrayList<Object>();
			rowList.add(time);
			rowList.add(numberOfWhite());
			rowList.add(numberOfBlack());
			rowList.add(numberOfOrange());
			rowList.add(convertDouble(globalTemperature));
			rowList.add(convertDouble(Parameter.getLuminosity()));
			try {
				writeRow(rowList, csvWriter);
			} catch (IOException e) {
				e.printStackTrace();
			}

			time = time + 1;
			year = year - 1;
		}

		try {
			csvWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * change the solar luminosity when choosing scenario ramp-up-ramp-down
	 */
	public void rampUpRampDown() {
		if (luminosityIncrease) {
			if (Parameter.getLuminosity() < 1.4) {
				Parameter.setLuminosity(Parameter.getLuminosity() + Math.random() * 0.1);
			} else {
				Parameter.setLuminosity(Parameter.getLuminosity() - Math.random() * 0.1);
				luminosityIncrease = false;
			}
		} else {
			if (Parameter.getLuminosity() > 0.6) {
				Parameter.setLuminosity(Parameter.getLuminosity() - Math.random() * 0.1);
			} else {
				Parameter.setLuminosity(Parameter.getLuminosity() + Math.random() * 0.1);
				luminosityIncrease = true;
			}
		}
	}

	/**
	 * @return currentGrid[i][j]'s neighboring grids
	 */
	public ArrayList<Grid> getNeighbours(int i, int j) {
		ArrayList<Grid> neighbourGrids = new ArrayList<Grid>();

		// Top left corner
		if ((i == 0) && (j == 0)) {
			neighbourGrids.add(currentGrid[i][j + 1]);
			neighbourGrids.add(currentGrid[i + 1][j]);
			neighbourGrids.add(currentGrid[i + 1][j + 1]);
			// Bottom left corner
		} else if ((i == GRIDSIZE - 1) && (j == 0)) {
			neighbourGrids.add(currentGrid[i - 1][j]);
			neighbourGrids.add(currentGrid[i - 1][j + 1]);
			neighbourGrids.add(currentGrid[i][j + 1]);
			// Top right corner
		} else if ((i == 0) && (j == GRIDSIZE - 1)) {
			neighbourGrids.add(currentGrid[i][j - 1]);
			neighbourGrids.add(currentGrid[i + 1][j - 1]);
			neighbourGrids.add(currentGrid[i + 1][j]);
			// Bottom right corner
		} else if ((i == GRIDSIZE - 1) && (j == GRIDSIZE - 1)) {
			neighbourGrids.add(currentGrid[i - 1][j]);
			neighbourGrids.add(currentGrid[i - 1][j - 1]);
			neighbourGrids.add(currentGrid[i][j - 1]);
			// Top edge
		} else if (i == 0) {
			neighbourGrids.add(currentGrid[i][j - 1]);
			neighbourGrids.add(currentGrid[i + 1][j - 1]);
			neighbourGrids.add(currentGrid[i + 1][j]);
			neighbourGrids.add(currentGrid[i + 1][j + 1]);
			neighbourGrids.add(currentGrid[i][j + 1]);
			// Left edge
		} else if (j == 0) {
			neighbourGrids.add(currentGrid[i - 1][j]);
			neighbourGrids.add(currentGrid[i - 1][j + 1]);
			neighbourGrids.add(currentGrid[i][j + 1]);
			neighbourGrids.add(currentGrid[i + 1][j + 1]);
			neighbourGrids.add(currentGrid[i + 1][j]);
			// Right edge
		} else if (j == GRIDSIZE - 1) {
			neighbourGrids.add(currentGrid[i - 1][j]);
			neighbourGrids.add(currentGrid[i - 1][j - 1]);
			neighbourGrids.add(currentGrid[i][j - 1]);
			neighbourGrids.add(currentGrid[i + 1][j - 1]);
			neighbourGrids.add(currentGrid[i + 1][j]);
			// Bottom edge
		} else if (i == GRIDSIZE - 1) {
			neighbourGrids.add(currentGrid[i][j - 1]);
			neighbourGrids.add(currentGrid[i - 1][j - 1]);
			neighbourGrids.add(currentGrid[i - 1][j]);
			neighbourGrids.add(currentGrid[i - 1][j + 1]);
			neighbourGrids.add(currentGrid[i][j + 1]);
			// Anywhere else
		} else {
			neighbourGrids.add(currentGrid[i][j - 1]);
			neighbourGrids.add(currentGrid[i - 1][j - 1]);
			neighbourGrids.add(currentGrid[i - 1][j]);
			neighbourGrids.add(currentGrid[i - 1][j + 1]);
			neighbourGrids.add(currentGrid[i][j + 1]);
			neighbourGrids.add(currentGrid[i + 1][j + 1]);
			neighbourGrids.add(currentGrid[i + 1][j]);
			neighbourGrids.add(currentGrid[i + 1][j - 1]);
		}

		return neighbourGrids;
	}

	/**
	 * every grid diffuse its 50% local temperature to its neighbors this method
	 * makes the diffusion and calculates the result all change is made at once
	 */
	public void diffuse() {
		double[][] newLocalTemperature = new double[GRIDSIZE][GRIDSIZE];
		for (int i = 0; i < GRIDSIZE; i++) {
			for (int j = 0; j < GRIDSIZE; j++) {
				ArrayList<Grid> neighbours = getNeighbours(i, j);
				double numberOfNeighbour = (double) neighbours.size();
				double getNeighbourTemperature = 0;
				for (Iterator<Grid> it = neighbours.iterator(); it.hasNext();) {
					Grid grid = it.next();
					getNeighbourTemperature = getNeighbourTemperature + grid.getLocalTemperature() * 0.5 / 8;
				}
				newLocalTemperature[i][j] = currentGrid[i][j].getLocalTemperature()
						- currentGrid[i][j].getLocalTemperature() * 0.5 * (numberOfNeighbour / 8)
						+ getNeighbourTemperature;
			}
		}

		for (int i = 0; i < GRIDSIZE; i++) {
			for (int j = 0; j < GRIDSIZE; j++) {
				currentGrid[i][j].setLocalTemperature(newLocalTemperature[i][j]);
			}
		}
	}

	/**
	 * increase all daisy's age by 1 and check if some daisies die
	 */
	public void ageAllDaisy() {
		for (int i = 0; i < GRIDSIZE; i++) {
			for (int j = 0; j < GRIDSIZE; j++) {
				if (currentGrid[i][j].isCovered()) {
					if (null != currentGrid[i][j].getCurrentDaisy()) {
						currentGrid[i][j].getCurrentDaisy().age();
						if (currentGrid[i][j].getCurrentDaisy().getAge() >= MAXAGE) {
							String color = currentGrid[i][j].getCurrentDaisy().getColor();
							if (color.equals("white")) {
								whiteDaisy.remove(currentGrid[i][j].getCurrentDaisy());
							} else if (color.equals("black")) {
								blackDaisy.remove(currentGrid[i][j].getCurrentDaisy());
							} else if (color.equals("orange")) {
								orangeDaisy.remove(currentGrid[i][j].getCurrentDaisy());
							}
							currentGrid[i][j].daisyDie();
						}
					}
					if (null != currentGrid[i][j].getCurrentRabbit()) {
						currentGrid[i][j].getCurrentRabbit().age();
						if (currentGrid[i][j].getCurrentRabbit().getAge() >= MAXAGE) {
							if (currentGrid[i][j].getCurrentRabbit().getFood() > 0) {
								currentGrid[i][j].getCurrentRabbit().init();
							} else {
								rabbitList.remove(currentGrid[i][j].getCurrentRabbit());
								currentGrid[i][j].rabbitDie();
							}
						}
					}

				}
			}
		}
	}

	/**
	 * sprout a white or black daisy in a grid's neighbor or an orange daisy is born
	 * in an empty grid. both happens in some probability
	 */
	public void sproutDaisy() {
		for (int i = 0; i < GRIDSIZE; i++) {
			for (int j = 0; j < GRIDSIZE; j++) {
				if (currentGrid[i][j].isCovered() && (currentGrid[i][j].getCurrentDaisy().getAge() != 0)) {
					double seedProb = 0.1457 * currentGrid[i][j].getLocalTemperature()
							- 0.0032 * Math.pow(currentGrid[i][j].getLocalTemperature(), 2) - 0.6443;
					if (Math.random() < seedProb) {
						String color = currentGrid[i][j].getCurrentDaisy().getColor();
						if (color.equals("orange")) {
							if (getGlobalTemperature() < 22.5)
								color = "black";
							else
								color = "white";
						}
						Grid neighbour = seekNeighbour(i, j);
						if (neighbour != null) {
							if (color.equals("white")) {
								Daisy newDaisy = new Daisy(Parameter.getAlbedoWhite(), 0, "white");
								neighbour.setCurrentDaisy(newDaisy);
								whiteDaisy.add(newDaisy);
							} else if (color.equals("black")) {
								Daisy newDaisy = new Daisy(Parameter.getAlbedoBlack(), 0, "black");
								neighbour.setCurrentDaisy(newDaisy);
								blackDaisy.add(newDaisy);
							}
						}
					}
				} else if (!currentGrid[i][j].isCovered()) {
					if (notAllNeighbourEmpty(i, j)) {
						if (Math.random() < Parameter.getProbOfOrangeSprout()) {
							Daisy newDaisy = new Daisy(Parameter.getAlbedoOrange(), 0, "orange");
							currentGrid[i][j].setCurrentDaisy(newDaisy);
							orangeDaisy.add(newDaisy);
						}
					}
				}
			}
		}
	}

	/**
	 * check if the grid's neighbors contain at least one daisy
	 */
	public boolean notAllNeighbourEmpty(int row, int column) {
		ArrayList<Grid> neighbours = getNeighbours(row, column);
		boolean returnBool = false;
		for (Iterator<Grid> it = neighbours.iterator(); it.hasNext();) {
			Grid grid = it.next();
			if (grid.isCovered())
				returnBool = true;
		}
		return returnBool;
	}

	/**
	 * find a neighbor which is empty
	 */
	public Grid seekNeighbour(int row, int column) {
		ArrayList<Grid> neighbours = getNeighbours(row, column);
		ArrayList<Grid> emptyNeighbour = new ArrayList<Grid>();
		for (Iterator<Grid> it = neighbours.iterator(); it.hasNext();) {
			Grid grid = it.next();
			if (!grid.isCovered())
				emptyNeighbour.add(grid);
		}

		int number = (int) (emptyNeighbour.size() * Math.random());
		int i = 0;
		Grid returnGrid = null;
		for (Iterator<Grid> it = emptyNeighbour.iterator(); it.hasNext();) {
			Grid grid = it.next();
			if (number == i)
				returnGrid = grid;
			i++;
		}
		return returnGrid;
	}

	/**
	 * used to write a row in csv file
	 */
	private static void writeRow(List<Object> row, BufferedWriter csvWriter) throws IOException {
		for (Object data : row) {
			StringBuffer sb = new StringBuffer();
			String rowStr = sb.append("\"").append(data).append("\",").toString();
			csvWriter.write(rowStr);
		}
		csvWriter.newLine();
		csvWriter.flush();
	}

	/**
	 * modify the format of a double
	 */
	private double convertDouble(double db) {
		db = ((int) (db * 100)) / 100.0;
		return db;
	}

}
