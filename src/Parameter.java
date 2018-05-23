/**
 * this calls records all the parameters
 */

public class Parameter {
	// percentage of black daisies of all grids
	private static double percentageOfBlack;

	// percentage of white daisies of all grids
	private static double percentageOfWhite;
	
	// percentage of rabit
	private static double percentageOfRabbit;

	// albedo of white daisy
	private static double albedoWhite;

	// albedo of black daisy
	private static double albedoBlack;

	// albedo of empty grid
	private static double albedoSurface;

	// the solar luminosity
	private static double luminosity;

	// specifies the number of scenario which is used
	private static int scenario;
	/**
	 * set the percentage of black daisies
	 */
	public static void setPercentageOfBlack(double percentageOfBlack) {
		Parameter.percentageOfBlack = percentageOfBlack;
	}

	/**
	 * @return the percentage of black daisies
	 */
	public static double getPercentageOfBlack() {
		return percentageOfBlack;
	}
	
	

	public static double getPercentageOfRabbit() {
		return percentageOfRabbit;
	}

	public static void setPercentageOfRabbit(double percentageOfRabbit) {
		Parameter.percentageOfRabbit = percentageOfRabbit;
	}

	/**
	 * set the percentage of white daisies
	 */
	public static void setPercentageOfWhite(double percentageOfWhite) {
		Parameter.percentageOfWhite = percentageOfWhite;
	}

	/**
	 * @return the percentage of white daisies
	 */
	public static double getPercentageOfWhite() {
		return percentageOfWhite;
	}

	/**
	 * @return albedo of white daisy
	 */
	public static double getAlbedoWhite() {
		return albedoWhite;
	}

	/**
	 * set the albedo of white daisy
	 */
	public static void setAlbedoWhite(double albedoWhite) {
		Parameter.albedoWhite = albedoWhite;
	}

	/**
	 * @return albedo of black daisy
	 */
	public static double getAlbedoBlack() {
		return albedoBlack;
	}

	/**
	 * set albedo of black daisy
	 */
	public static void setAlbedoBlack(double albedoBlack) {
		Parameter.albedoBlack = albedoBlack;
	}

	/**
	 * @return albedo of empty grid
	 */
	public static double getAlbedoSurface() {
		return albedoSurface;
	}

	/**
	 * set albedo of empty grid
	 */
	public static void setAlbedoSurface(double albedoSurface) {
		Parameter.albedoSurface = albedoSurface;
	}

	/**
	 * @return solar luminosity
	 */
	public static double getLuminosity() {
		return luminosity;
	}

	/**
	 * set solar luminosity
	 */
	public static void setLuminosity(double luminosity) {
		Parameter.luminosity = luminosity;
	}

	/**
	 * @return the number of scenario which is used
	 */
	public static int getScenario() {
		return scenario;
	}

	/**
	 * set the number of scenario to be used
	 */
	public static void setScenario(int scenario) {
		Parameter.scenario = scenario;
	}

}
