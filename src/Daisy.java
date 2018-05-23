/**
 * A daisy has one of two colors : white or black.
 * Each color has its own albedo.
 */
public class Daisy {
    // daisy's albedo
    private double albedo;

    // daisy's age
    private int age;

    //  daisy's color
    private String color;


    /**
     * constructor of a daisy
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
