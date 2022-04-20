
import java.util.Random;

/**
 * A simple model of a Lion.
 * Lions age, move, breed, eat, and die.
 *
 * @author Aymen Berbache and Aleks
 * @version 2022.02
 */
public class Lion extends Predator
{
    // Characteristics shared by all lions (class variables).

    // The age at which a Lion can start to breed.
    private static final int BREEDING_AGE = 10;
    // The likelihood of a Lion breeding;
    private static final double BREEDING_PROBABILITY = 0.6;
    // The age to which a Lion can live.
    private static final int MAX_AGE = 100;
    // The maximum number of births.
    private static final int MAX_LITTER_SIZE = 4;
    //The maximum food level of the Lion.
    private static final int MAX_FOOD_LEVEL = 100;
    //A list of species that are part of the lion's diet. Lions don't eat grass but step on it and "kill" it by doing so.
    private static final String[] preys = {"Zebra","Elephant","Buffalo","Grass"};
    //If the animal is nocturnal, it hunts by night and sleeps during the day.
    private static final boolean IS_NOCTURNAL = true;
    // The infection probability of the animal.
    private static final double DISEASE_INFECTION_RATE = 0.001;
    // A shared random number generator to control breeding.
    private static final Random rand = Randomizer.getRandom();

    /**
     * Create a Lion. A Lion be created as a newborn (age zero
     * and not hungry) or with a random age and food level.
     * 
     * @param randomAge If true, the Lion will have random age and hunger level.
     * @param field The field currently occupied.
     * @param location The location within the field.
     */
    public Lion(boolean randomAge, Field field, Location location)
    {
        super(field, location);
        if(randomAge) {
            // We assume the simulation starts with a relatively young population.
            age = rand.nextInt(MAX_AGE/2);
            // food level initialized by Animal constructor
        }else{
            // age =  0;  Implied by Entity constructor
            foodLevel = 20;  //Initial food level of a newborn
        }
    }

    /**
     * Creates a new Lion object
     * @param field The simulation field.
     * @param location Location to create the new Lion.
     * @return A new animal object
     */
    protected Animal newAnimalObject(Field field, Location location) {
        return new Lion(false, field, location);
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
     * @return The list of species that the lion can eat.
     */
     protected String[] getPreys() {
        return preys;
    }

    /**
     * @return true if the animal lives by night.
     */
    protected boolean getIS_NOCTURNAL(){
        return IS_NOCTURNAL;
    }

    /**
     * @return the animal's chance to get infected randomly.
     */
    protected double getDISEASE_INFECTION_RATE(){
        return DISEASE_INFECTION_RATE;
    }

}