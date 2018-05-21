
public class Rabbit {

    // specifies daisy's age
    private int age = 0;
    private int food = 5;

    /**
     * constructor
     */
    public Rabbit() {
        this.age = 0;
        this.food = (int) (5 * Math.random());
    }

    /**
     * decrease the rabbit's food by 1
     * <p>
     * If the rabbit's food is more than zero, its age increases by 1;
     * If the rabbit's food is no more than zero, its age increases by 6;
     */
    public void age() {
        food = food - 1;
        if (food > 0) {
            age = age + 1;
        } else {
            age = age + 6;
        }

    }

    /**
     * feed the rabbit by a number of food
     *
     * @param food
     */
    public void feed(int food) {
        this.food = this.food + food;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getFood() {
        return food;
    }

    public void setFood(int food) {
        this.food = food;
    }

}
