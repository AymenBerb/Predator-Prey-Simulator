import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

/**
 * A Class defining all common characteristics of animals in the simulation
 *
 * @author Aymen Berbache and Aleks
 * @version 2022.02
 */
public abstract class Animal extends Entity
{
    //The foodLevel of the animal.
    protected int foodLevel;
    //The animal's gender 0 if female, 1 if male.
    private final int sex;
    // A boolean that stores the infection state of the animal.
    private boolean isInfected;
    //A random Number shared amongst all animals.
    private static final Random rand = Randomizer.getRandom();

    /**
     * Create a new animal at location in field.
     * 
     * @param field The field currently occupied.
     * @param location The location within the field.
     */
    public Animal(Field field, Location location)
    {
        super(field,location);
        foodLevel = 30;
        sex = rand.nextInt(2);
        randomlyInfect();
    }

    /**
     * This is what an animal doe most of the time: it looks for food.
     * In the process, it might breed, die of hunger, die of old age or
     * randomly contract a disease and die from it.
     * @param newAnimals A list to return newly born animals.
     */
    public void act(List<Entity> newAnimals) {
        incrementAge();
        spreadDisease();
        incrementHunger();
        // Stores the next location of the animal.
        Location newLocation = null;
        // the animal can find food and give birth only if it is alive and awake.
        if (isAlive() && animalAwake(getIS_NOCTURNAL())) {
            // Only the females may give birth
            if (sex == 0) {
                giveBirth(newAnimals);
            }
            // Predators don't act when the weather is foggy.
            if (!(Simulator.weather.isFoggy() && getVISIBILITY_REQUIRED())) {
                // The animal can hunt only when it is hungry
                if(foodLevel < getMAX_FOOD_LEVEL()){
                    // Move towards a source of food if found.
                    newLocation = findFood();
                }
                if (newLocation == null) {
                    // No food found - try to move to a free location.
                    newLocation = getField().freeAdjacentLocation(getLocation());
                }
                // See if it was possible to move.
                if (newLocation != null) {
                    setLocation(newLocation);
                } else {
                    // Overcrowding.
                    setDead();
                }
            }
        }
    }

    /**
     * Check whether or not this animal is to give birth at this step.
     * New births will be made into free adjacent locations.
     * @param newAnimals A list to return newly born animals.
     */
    protected void giveBirth(List<Entity> newAnimals)
    {
        Field field = getField();
        List<Location> free = field.getFreeAdjacentLocations(getLocation());
        //Get the number of new animals to add to the field.
        int births = breed();
        for(int b = 0; b < births && free.size() > 0; b++) {
            Location location = free.remove(0);
            Animal newBorn = newAnimalObject(field, location);
            newAnimals.add(newBorn);
        }
    }

    /**
     * Generate a number representing the number of births,
     * if the animal can breed.
     * @return The number of births (may be zero).
     */
    protected int breed()
    {
        int births = 0;
        if(canBreed() && rand.nextDouble() <= getBREEDING_PROBABILITY()) {
            births = rand.nextInt(getMAX_LITTER_SIZE()) + 1;
        }
        return births;
    }

    /**
     * An animal can breed if it has reached the breeding age and found a partner.
     * @return true if the human can breed, false otherwise.
     */
    private boolean canBreed()
    {
        return checkForAdjacentMales(getLocation()) && (age >= getBREEDING_AGE());
    }

     /**
     * Check if there are any adjacent males of the same species.
     * @param location of the animal
     * @return true if there are males around, false otherwise.
     */
    private boolean checkForAdjacentMales(Location location) {

        Field field = getField();
        // Get a list of adjacent locations.
        List<Location> adjacentLocations = field.adjacentAnimals(location);
        // Get the animal at the current location.
        Entity female = (Entity) field.getObjectAt(location);

            // Get a list of adjacent male animals of the same specie.
            List<Animal> adjacentMaleAnimals =

                    adjacentLocations.stream()
                            // Filters only the animals of the same specie
                            .filter(locations -> field.getObjectAt(locations).getClass().equals(female.getClass()))
                            // Maps, respectively, to a new list of Animal's objects
                            .map(object -> (Animal) field.getObjectAt(object))
                            // Filters the males
                            .filter(animal -> animal.getSex() == 1).collect(Collectors.toList());

            return !adjacentMaleAnimals.isEmpty();
    }

    /**
     * A method that checks whether or not an animal is asleep.
     * @param isNocturnal The lifestyle of the animal.
     * @return true if the animal is awake.
     */
    private boolean animalAwake(boolean isNocturnal) {
        return (isNocturnal && !Simulator.time.isDay()) || (!isNocturnal && Simulator.time.isDay());
    }

    /**
     * Look for food adjacent to the current location.
     * @return Where food was found, or null if it wasn't.
     */
    private Location findFood()
    {
        Field field = getField();
        // Create a list of adjacent locations.
        List<Location> adjacent = field.adjacentLocations(getLocation());
         for (Location location : adjacent) {
            Object prey = field.getObjectAt(location);
            if(prey != null){
                String targetBreed = prey.getClass().getName();
                // Check if it is in the diet of the animal.
                if(Arrays.stream(getPreys()).toList().contains(targetBreed)){
                    kill((Entity) prey);
                    return location;
                }
            }
        }
        return null;
    }

    /**
     * Increment food level of predator and remove the prey that has been killed from the view.
     * @param prey The entity that will be eaten.
     */
    protected void kill(Entity prey)
    {
        if(this instanceof Predator && prey instanceof Grass){
            // Do nothing. Predators are allowed to step over grass and thus kill it.
            // However, they don't eat plants so their food level isn't updated.
        }else{
            //Add the food value of the species that has just been eaten to the animal's foodLevel
            foodLevel += prey.getFOOD_VALUE();
        }
        prey.setDead();
    }

    /**
     * @return The animal's gender
     */
    private int getSex()
    {
        return sex;
    }

    /**
     * Increment hunger of the animal by one.
     */
    private void incrementHunger()
    {
        foodLevel--;
        if(foodLevel <= 0) {
            setDead();
        }
    }

    /**
     * Randomly set animals infected, and leave them with a small number of steps
     * to live depending on the virulence of the disease.
     */
    private void randomlyInfect() {
        if(rand.nextDouble() <= getDISEASE_INFECTION_RATE()){
            isInfected = true;
            // Leave the animal with a certain amount of steps left to live, depending on the virulence
            // of the disease.
            this.age = this.getMAX_AGE() - Simulator.disease.getVirulence();
        }
    }

    /**
     * An animal can randomly spread disease randomly to adjacent animals of the same species.
     */
    private void spreadDisease(){
        // Animal can only spread infection when it is infected and alive.
        if(isAlive() && isInfected){
            Field field = getField();
            List<Location> adjacentLocations  = field.adjacentAnimals(getLocation());
            adjacentLocations.stream()
                    // Get a stream of the adjacent animals objects
                    .map(location -> (Animal) field.getObjectAt(location))
                    // An animal can spread the virus only with other animals of the same specie.
                    .filter(animal -> animal.getClass().equals(this.getClass()))
                    // Infect adjacent animals
                    .forEach(animal -> {
                        // Depending on the virality of the virus, its contraction by other animals is not systematic
                        if(rand.nextDouble() <= Simulator.disease.getVirality()){
                            animal.isInfected = true;
                            // Leave the animal with a certain amount of steps left to live, depending on the virulence
                            // of the disease.
                            if(animal.getMAX_AGE() - Simulator.disease.getVirulence() > animal.age){
                                animal.age = animal.getMAX_AGE() - Simulator.disease.getVirulence();
                            }else{
                                //If the animal is already very old, he dies because of the weakness of his immune system.
                                animal.setDead();
                            }
                            Simulator.disease.incrementDeath();
                        }
                    });
        }
    }


    //Abstract methods
    /**
     * Creates a new animal object
     * @param field The simulation field.
     * @param location Location to create the new animal.
     * @return A new animal object
     */
    protected abstract Animal newAnimalObject(Field field, Location location);

    /**
     * @return The age when the animal can start breeding.
     */
    protected abstract int getBREEDING_AGE();

    /**
     * @return The likelihood of the animal breeding.
     */
    protected abstract double getBREEDING_PROBABILITY();

    /**
     * @return The max number of births the animal can have at once.
     */
    protected abstract int getMAX_LITTER_SIZE();

    /**
     * @return The max food value, at this value, the animal can no longer eat.
     */
    protected abstract int getMAX_FOOD_LEVEL();

    /**
     * @return A list of species that are part of the animal's diet.
     */
    protected abstract String[] getPreys();

    /**
     * @return true if the animal lives by night.
     */
    protected abstract boolean getIS_NOCTURNAL();

    /**
     * @return the animal's chance to get infected randomly.
     */
    protected abstract double getDISEASE_INFECTION_RATE();

    /**
     * @return the animal visibility requirement to hunt.
     */
    protected abstract boolean getVISIBILITY_REQUIRED();

}