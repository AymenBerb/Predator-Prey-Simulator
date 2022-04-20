
import java.util.Random;

/**
 * A model of elephants.
 * Elephants age, move, breed, eat grass, and die.
 *
 * @author Aaymen Berbache and Aleks
 * @version 2022.02
 */
public class Elephant extends Prey
{
    // Characteristics shared by all elephants (class variables).

    // The age at which an elephant can start to breed.
    private static final int BREEDING_AGE = 15;
    // The age to which an elephant can live.
    private static final int MAX_AGE = 70;
    // The likelihood of an elephant breeding.
    private static final double BREEDING_PROBABILITY = 0.5;
    // The maximum number of births.
    private static final int MAX_LITTER_SIZE = 1;
    // The value that is worth an elephant when it is eaten by another animal
    private static final int FOOD_VALUE = 10;
    //The maximum food level of the elephant.
    private static final int MAX_FOOD_LEVEL = 30;
    // The elephant eat Grass and kill humans and lions by defending itself.
    private static final String[] preys = {"Grass"};
    // A shared random number generator to control breeding.
    private static final Random rand = Randomizer.getRandom();
    // The infection probability of the animal.
    private static final double DISEASE_INFECTION_RATE = 0.001;


    /**
     * Create an elephant. An elephant be created as a newborn (age zero
     * and not hungry) or with a random age and food level.
     *
     * @param randomAge If true, the elephant will have random age and hunger level.
     * @param field The field currently occupied.
     * @param location The location within the field.
     */
    public Elephant(boolean randomAge, Field field, Location location)
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
     * @param field The simulation field.
     * @param location Location to create the new elephant.
     * @return A new elephant object with age 0.
     */
    protected Animal newAnimalObject(Field field, Location location) {
        return new Elephant(false, field, location);
    }

    /**
     * @return The maximum age
     */
    protected int getMAX_AGE()
    {
        return MAX_AGE;
    }

    /**
     * @return the food value of the animal.
     */
    protected int getFOOD_VALUE() {
        return FOOD_VALUE;
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
     * @return The list of species that the elephant can eat.
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
