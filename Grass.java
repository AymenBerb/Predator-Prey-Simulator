/**
 * A simple model of grass.
 * Grass age, spread, and die.
 *
 * @author Aymen berbache and Aleks
 * @version 2022.02
 */
public class Grass extends Plant {

    //The maximum age of grass.
    private static final int MAX_AGE = 14;
    //The food value of grass.
    private static final int FOOD_VALUE = 8;
    //The probability ratio at which grass spread
    private static final double SPREADING_PROBABILITY = 0.07;

    /**
     * Create a new grass patch and place it on the field
     * @param field The field currently occupied.
     * @param location The location within the field.
     */
    public Grass(Field field, Location location)
    {
        super(field, location);
    }

    /**
     * @param field The simulation field.
     * @param location Location to create the new grass.
     * @return The new grass object
     */
    protected Plant newPlantObject(Field field, Location location) {
        return new Grass(field, location);
    }

    /**
     * @return The grass' food value
     */
    protected int getFOOD_VALUE() {
        return FOOD_VALUE;
    }

    /**
     * @return The grass maximum age
     */
    protected int getMAX_AGE() {
        return MAX_AGE;
    }

    /**
     * @return The grass spreading probability
     */
    protected double getSPREADING_PROBABILITY() {
        return SPREADING_PROBABILITY;
    }
}