/**
 * a grid can be empty ground or covered by a daisy every grid has its local
 * temperature and some neighbours
 */

public class Grid {
    private double localTemperature;
    private boolean isCovered;
    private Daisy currentDaisy;
    private Rabbit currentRabbit;

    /**
     * create a new grid
     */
    public Grid() {
        isCovered = false;
        currentDaisy = null;
        currentRabbit = null;
        localTemperature = 0;
    }

    /**
     * create a new grid and set up its attributes
     *
     * @param isCovered    true if covered by a daisy
     * @param currentDaisy daisy on the grid
     */
    public Grid(boolean isCovered, Daisy currentDaisy) {
        this.isCovered = isCovered;
        this.currentDaisy = currentDaisy;
        this.currentRabbit = null;
        localTemperature = 0;
    }

    public Grid(boolean isCovered, Rabbit currentRabbit) {
        this.isCovered = isCovered;
        this.currentRabbit = currentRabbit;
        this.currentDaisy = null;
        localTemperature = 0;
    }

    /**
     * @return local temperature
     */
    public double getLocalTemperature() {
        return localTemperature;
    }

    /**
     * set local temperature
     */
    public void setLocalTemperature(double localTemperature) {
        this.localTemperature = localTemperature;
    }

    /**
     * @return true if covered by a daisy
     */
    public boolean isCovered() {
        return isCovered;
    }

    /**
     * set true if the grid is coverd by a daisy
     */
    public void setCovered(boolean isCovered) {
        this.isCovered = isCovered;
        if (isCovered == false) {
            this.currentRabbit = null;
            this.currentDaisy = null;
        }
    }

    /**
     * @return daisy on the grid
     */
    public Daisy getCurrentDaisy() {
        return currentDaisy;
    }

    /**
     * put @param currentDaisy on the grid
     */
    public void setCurrentDaisy(Daisy currentDaisy) {
        isCovered = true;
        this.currentDaisy = currentDaisy;
        this.currentRabbit = null;
    }

    /**
     * calculate the local temperature depending on whether the grid is covered by a
     * daisy
     */
    public void calcLocalTemp() {
        double absorbedLuminosity = 0;
        double localHeating = 0;
        if (isCovered && null != getCurrentDaisy()) {
            absorbedLuminosity = (1 - currentDaisy.getAlbedo()) * Parameter.getLuminosity();
        } else {
            absorbedLuminosity = (1 - Parameter.getAlbedoSurface()) * Parameter.getLuminosity();
        }
        if (absorbedLuminosity > 0)
            localHeating = (double) 72 * Math.log(absorbedLuminosity) + 80;
        else
            localHeating = 80;
        localTemperature = (localTemperature + localHeating) / 2;
    }

    /**
     * eliminate current daisy if it dies
     */
    public void daisyDie() {
        isCovered = false;
        currentDaisy = null;
    }

    public Rabbit getCurrentRabbit() {
        return currentRabbit;
    }

    public void setCurrentRabbit(Rabbit currentRabbit) {
        this.currentRabbit = currentRabbit;
        this.currentDaisy = null;
        this.isCovered = true;
    }

    public void rabbitDie() {
        isCovered = false;
        currentRabbit = null;
    }

}
