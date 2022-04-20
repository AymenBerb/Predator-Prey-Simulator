import java.util.Random;
/**
 * A simple model of a tribal population.
 * Humans age, move, breed, eat, and die.
 *
 * @author Aymen Berbache and Aleks
 * @version 2022.02
 */
public class Human extends Predator
{
    // Characteristics shared by all humans (class variables).

    // The age at which a Human can start to breed.
    private static final int BREEDING_AGE = 10;
    // The likelihood of a Human breeding.
    private static final double BREEDING_PROBABILITY = 0.5;
    // The age to which a Human can live.
    private static final int MAX_AGE = 100;
    // The maximum number of births.
    private static final int MAX_LITTER_SIZE = 2;
    //The maximum food level of the Human.
    private static final int MAX_FOOD_LEVEL = 20;
    //A list of species that are part of the human's diet. Humans don't eat grass but step on it and "kill" it by doing so.
    private static final String[] preys = {"Zebra","Buffalo","Grass"};
    //If the animal is nocturnal, it hunts by night and sleeps during the day.
    private static final boolean IS_NOCTURNAL = false;
    // The infection probability of the animal.
    private static final double DISEASE_INFECTION_RATE = 0.095;
    //A random Number to assign the human's age.
    private static final Random rand = Randomizer.getRandom();

    /**
     * Create a new human. A human may be created with age
     * zero (a newborn) or with a random age.
     * 
     * @param randomAge If true, the human will have a random age.
     * @param field The field currently occupied.
     * @param location The location within the field.
     */
    public Human(boolean randomAge, Field field, Location location)
    {
        super(field, location);
        if(randomAge) {
            // We assume the simulation starts with a relatively young population.
            age = rand.nextInt(MAX_AGE/2);
            // food level initialized by Animal constructor
        }else{
            // age =  0;  Implied by Entity constructor
            foodLevel = 21;  //Initial food level of a newborn
        }
    }

    /**
     *
     * @param field The simulation field.
     * @param location Location to create the new human.
     * @return The new human
     */
    protected Animal newAnimalObject(Field field, Location location) {
        return new Human(false, field, location);
    }

    /**
     * @return The maximum age
     */
    protected int getMAX_AGE()
    {
        return MAX_AGE;
    }

    /**
     * @return 0, a human cannot be eaten.
     */
    protected int getFOOD_VALUE() {
        return 0;
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
     * @return The list of species that the human can eat.
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