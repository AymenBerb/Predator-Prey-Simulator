import java.util.Random;

/**
 * A simple model of a zebra.
 * Zebras age, move, breed, eat, and die.
 *
 * @author Colin Billhardt and Thom Treebus
 * @version 2022.02
 */
public class Zebra extends Prey
{
    // Characteristics shared by all zebras (class variables).

    // The age at which a zebra can start to breed.
    private static final int BREEDING_AGE = 15;
    // The likelihood of a zebra breeding.
    private static final double BREEDING_PROBABILITY = 0.5;
    // The age to which a zebra can live.
    private static final int MAX_AGE = 70;
    // The maximum number of births.
    private static final int MAX_LITTER_SIZE = 1;
    // The value that is worth a zebra when it is eaten by another animal
    private static final int FOOD_VALUE = 25;
    //The maximum food level of the zebra.
    private static final int MAX_FOOD_LEVEL = 30;
    //A list of species that are part of the zebra's diet.
    private static final String[] preys = {"Grass"};
    // A shared random number generator to control breeding.
    private static final Random rand = Randomizer.getRandom();
    // The infection probability of the animal.
    private static final double DISEASE_INFECTION_RATE = 0.001;

    /**
     * Create a new zebra. A zebra may be created with age
     * zero (a newborn) or with a random age.
     *
     * @param randomAge If true, the zebra will have a random age.
     * @param field The field currently occupied.
     * @param location The location within the field.
     */
    public Zebra(boolean randomAge, Field field, Location location)
    {
        super(field, location);
        if(randomAge) {
            // We assume the simulation starts with a relatively young population
            age = rand.nextInt(MAX_AGE/2);
            // food level initialized by Animal constructor
        }else{
            // age =  0;  Implied by Entity constructor
            foodLevel = 15;  //Initial food level of a newborn
        }
    }

    /**
     * Creates a new zebra
     * @param field The simulation field.
     * @param location Location to create the new zebra.
     * @return The new zebra
     */
    protected Animal newAnimalObject(Field field, Location location) {
        return new Zebra(false, field, location);
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
     *
     * @return The max food value
     */
    protected int getMAX_FOOD_LEVEL()
    {
        return MAX_FOOD_LEVEL;
    }

    /**
     * @return The zebra's food value when eaten
     */
    protected int getFOOD_VALUE()
    {
        return FOOD_VALUE;
    }

    /**
     * @return The list of species that the zebra can eat.
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
