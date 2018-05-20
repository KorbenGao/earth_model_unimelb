/**
 * A daisy has one of three colors : white, black or orange. Each color has its
 * own albedo
 */

public class Daisy {
	// specifies daisy's albedo
	private double albedo;

	// specifies daisy's age
	private int age;

	// specifies daisy's color
	private String color;

	/**
	 * create a new daisy
	 */
	public Daisy() {
		albedo = 0;
		age = 0;
		color = "";
	}

	/**
	 * create a new daisy and set up its attributes
	 */
	public Daisy(double albedo, int age, String color) {
		this.albedo = albedo;
		this.age = age;
		this.color = color;
	}

	/**
	 * @return albedo
	 */
	public double getAlbedo() {
		return albedo;
	}

	/**
	 * set albedo
	 */
	public void setAlbedo(double albedo) {
		this.albedo = albedo;
	}

	/**
	 * @return color
	 */
	public String getColor() {
		return color;
	}

	/**
	 * set color
	 */
	public void setColor(String color) {
		this.color = color;
	}

	/**
	 * @return age
	 */
	public int getAge() {
		return age;
	}

	/**
	 * increase the daisy's age by 1
	 */
	public void age() {
		age = age + 1;
	}
}
