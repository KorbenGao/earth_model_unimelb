
public class Rabbit {

    // specifies daisy's age
    private int age = 0;
    private int food = 5;


    public Rabbit() {
        this.age = 0;
        this.food = (int) (5 * Math.random());
    }

    /**
     * increase the daisy's age by 1
     */
    public void age() {
        //System.out.println(this.age);
        food = food - 1;
        if (food > 0) {
            age = age + 1;
        } else {
            age = age + 6;
        }

    }

    /**
     * @param foodNum
     */
    public void eat(int foodNum) {
        food = food + foodNum;
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

    public void init() {
        this.food = 5;
        this.age = 0;
    }

    public void feed(int food) {
        this.food = this.food + food;
    }

}
