
import java.util.Random;

/**
 * A simple model of a buffalo.
 * Buffalo age, move, breed, eat, and die.
 *
 * @author Aymen Berbache and Aleks
 * @version 2022.02
 */
public class Buffalo extends Prey
{
    // Characteristics shared by all buffalo (class variables).

    // The age at which a buffalo can start to breed.
    private static final int BREEDING_AGE = 15;
    // The age to which a buffalo can live.
    private static final int MAX_AGE = 70;
    // The likelihood of a buffalo breeding.
    private static final double BREEDING_PROBABILITY = 0.45;
    // The maximum number of births.
    private static final int MAX_LITTER_SIZE = 2;
    // The value that is worth a buffalo when it is eaten by another animal
    private static final int FOOD_VALUE = 25;
    //The maximum food level of the buffalo.
    private static final int MAX_FOOD_LEVEL = 30;
    //A list of entities that are part of the buffalo's diet.
    private static final String[] preys = {"Grass"};
    // A shared random number generator to control breeding.
    private static final Random rand = Randomizer.getRandom();
    // The infection probability of the animal.
    private static final double DISEASE_INFECTION_RATE = 0.01;


    /**
     * Create a new buffalo. A buffalo may be created with age
     * zero (a newborn) or with a random age.
     *
     * @param randomAge If true, the buffalo will have a random age.
     * @param field The field currently occupied.
     * @param location The location within the field.
     */
    public Buffalo(boolean randomAge, Field field, Location location)
    {
        super(field, location);
        if(randomAge) {
            // We assume the simulation starts with a relatively young population
            age = rand.nextInt(MAX_AGE/2);
            // food level initialized by Animal constructor
        }else{
            // age =  0;  Implied by Entity constructor
            foodLevel = 14;  //Initial food level of a newborn
        }
    }

    /**
     * Creates a new buffalo
     * @param field The simulation field.
     * @param location Location to create the new buffalo.
     * @return The new buffalo
     */
    protected Animal newAnimalObject(Field field, Location location) {
        return new Buffalo(false, field, location);
    }

    /**
     * @return The maximum age
     */
    protected int getMAX_AGE()
    {
        return MAX_AGE;
    }

    /**
     * @return The breeding age
     */
    protected int getBREEDING_AGE()
    {
        return BREEDING_AGE;
    }

    /**
     * @return The breeding probability
     */
    protected double getBREEDING_PROBABILITY()
    {
        return BREEDING_PROBABILITY;
    }

    /**
     * @return The maximum litter size
     */
    protected int getMAX_LITTER_SIZE()
    {
        return MAX_LITTER_SIZE;
    }

    /**
     * @return The max food value
     */
    protected int getMAX_FOOD_LEVEL()
    {
        return MAX_FOOD_LEVEL;
    }

    /**
     * @return The buffalo's food value when eaten
     */
    protected int getFOOD_VALUE()
    {
        return FOOD_VALUE;
    }

    /**
     * @return The list of species that the buffalo can eat.
     */
     protected String[] getPreys() {
        return preys;
    }

    /**
     * @return the animal's chance to get infected randomly.
     */
    protected double getDISEASE_INFECTION_RATE(){
        return DISEASE_INFECTION_RATE;
    }

}
