import java.util.List;
import java.util.Random;

/**
 * A class defining the behavior of plants.
 * Plants grow, spread and die of age.
 *
 * @author Aymen Berbache and Aleks
 * @version 2022.02
 */
public abstract class Plant extends Entity {

    //A shared random number generator to control the plant spreading
    private static final Random rand = Randomizer.getRandom();

    /**
     * Create a new plant at a location in the field
     * @param field The field currently occupied
     * @param location The location within the field
     */
    public Plant(Field field, Location location)
    {
        super(field, location);
    }

    /**
     * @param newPlants A list that holds the newly created plants.
     */
    public void act(List<Entity> newPlants) {
        incrementAge();
        // Plants grow two times more when it's rainy.
        if (Simulator.weather.isRainy()) {
            incrementAge();
        }
        // Plants spread randomly
        if (isAlive() && rand.nextDouble() <= getWindySpreadingProbability()) {
            createPlants(newPlants);
        }
    }

    /**
     * A plant has a probability of spreading to an adjacent location in the simulation
     * @param newPlants A list of new plants
     */
    private void createPlants(List<Entity> newPlants){

        Field field = getField();
        List<Location> free = field.getFreeAdjacentLocations(getLocation());
        for(int newPlant = 0; newPlant < free.size(); newPlant++) {
            Location loc = free.remove(0);
            Plant newP = newPlantObject(field, loc);
            newPlants.add(newP);
        }

    }

    /**
     * @return The spreading probability of the plant considering the state of the weather.
     * If there is wind, plants are more likely to spread.
     */
    private double getWindySpreadingProbability(){
        if(Simulator.weather.isWindy()){
            return getSPREADING_PROBABILITY()*2;
        }else{
            return getSPREADING_PROBABILITY();
        }
    }

    //abstract methods

    /**
     * Creates a new animal object
     * @param field The simulation field.
     * @param location Location to create the new plant.
     * @return A new plant object
     */
    protected abstract Plant newPlantObject(Field field, Location location);

    /**
     * @return The plant's max age
     */
    protected abstract int getMAX_AGE();

    /**
     * @return The plant's food value
     */
    protected abstract int getFOOD_VALUE();

    /**
     * @return The probability that the plant will spread to a neighbouring cell
     */
    protected abstract double getSPREADING_PROBABILITY();

}