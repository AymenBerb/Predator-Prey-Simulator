import java.util.Random;
import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;
import java.awt.Color;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Simulation of a savanna environment
 *
 * @author Aymen Berbache and Aleks
 * @version 2021.03.03
 */
public class Simulator
{
    // Constants representing configuration information for the simulation.
    // The default width for the grid.
    private static final int DEFAULT_WIDTH = 180;
    // The default depth of the grid.
    private static final int DEFAULT_DEPTH = 115;
    // The probability that a Lion will be created in any given grid position.
    private static final double LION_CREATION_PROBABILITY = 0.025;
    // The probability that a Human will be created in any given grid position.
    private static final double HUMAN_CREATION_PROBABILITY = 0.025;
    // The probability that an Elephant will be created in any given grid position.
    private static final double ELEPHANT_CREATION_PROBABILITY = 0.02;
    // The probability that a Buffalo will be created in any given grid position.
    private static final double BUFFALO_CREATION_PROBABILITY = 0.02;
    // The probability that a zebra will be created in any given grid position.
    private static final double ZEBRA_CREATION_PROBABILITY = 0.03;
    // The probability that grass will be created in any given grid position.
    private static final double GRASS_CREATION_PROBABILITY = 0.10;

    // List of animals in the field.
    private final List<Entity> entities;
    // The current state of the field.
    private final Field field;
    // The current step of the simulation.
    private static int step;
    // A graphical view of the simulation.
    private final SimulatorView view;
    // Boolean to control the running state.
    public boolean isRunning = true;
    // An object that tracks day/night cycles.
    public static Time time = new Time();
    // An object that tracks the weather.
    public static Weather weather = new Weather();
    // Add disease to simulation
    public static Disease disease = new Disease(0.05, 10);


    /**
     * Construct a simulation field with default size.
     */
    public Simulator()
    {
        this(DEFAULT_DEPTH, DEFAULT_WIDTH);
    }

    /**
     * Create a simulation field with the given size.
     * @param depth Depth of the field. Must be greater than zero.
     * @param width Width of the field. Must be greater than zero.
     */
    public Simulator(int depth, int width)
    {
        if(width <= 0 || depth <= 0) {
            System.out.println("The dimensions must be greater than zero.");
            System.out.println("Using default values.");
            depth = DEFAULT_DEPTH;
            width = DEFAULT_WIDTH;
        }

        entities = new ArrayList<>();
        field = new Field(depth, width);

        // Create a view of the state of each location in the field.
        view = new SimulatorView(this, depth, width);
        view.setColor(Lion.class, Color.ORANGE);
        view.setColor(Human.class, Color.RED);
        view.setColor(Elephant.class, Color.LIGHT_GRAY);
        view.setColor(Zebra.class, Color.MAGENTA);
        view.setColor(Buffalo.class, Color.DARK_GRAY);
        view.setColor(Grass.class, Color.GREEN);

        // Setup a valid starting point.
        reset();
    }

    /**
     * Run the simulation from its current state for a reasonably long period,
     * (100 steps).
     */
    public void runLongSimulation()
    {
        simulate(1000);
    }

    /**
     * Run the simulation from its current state for the given number of steps.
     * Stop before the given number of steps if it ceases to be viable.
     * @param numSteps The number of steps to run for.
     */
    public void simulate(int numSteps)
    {
        for(int step = 1; step <= numSteps && view.isViable(field) && isRunning; step++) {
            simulateOneStep();
            delay(10);
        }
    }

    /**
     * Run the simulation from its current state for a single step.
     * Iterate over the whole field updating the state of each entity.
     */
    public void simulateOneStep()
    {
        step++;

        // Alternate time each 10 steps.
        alternateTime();
        // Change weather state randomly.
        changeWeather();

        // Provide space for newborn entities.
        List<Entity> newEntities = new ArrayList<>();

        // Let all entities act.
        for(Iterator<Entity> it = entities.iterator(); it.hasNext(); ) {
            Entity entity = it.next();
            if(!entity.isAlive()) {
                it.remove();
            }else {
                entity.act(newEntities);
            }
        }

        // Add the newly born entities to the main lists.
        entities.addAll(newEntities);

        view.showStatus(step, field, time.getIsDay(), weather,disease);
    }

    /**
     * Change the state of the time each 5 steps.
     */
    public void alternateTime(){
        if(step % 10 == 0){
            time.switchTime();
        }
    }

    /**
     * Change the weather state at a random time interval
     * comprised between each 10 to 20 (exclusive) steps.
     */
    public void changeWeather(){
        //Get a random number between 10 and 20 exclusive.
        int randomNum = ThreadLocalRandom.current().nextInt(5,10);
        // Change weather state if condition met.
        if(step % randomNum == 0){
            weather.weatherUpdate();
        }
    }

    /**
     * Reset the simulation to a starting position.
     */
    public void reset()
    {
        step = 0;
        entities.clear();
        populate();

        // Show the starting state in the view.
        view.showStatus(step, field, time.getIsDay(), weather,disease);
    }

    /**
     * Randomly populate the field with predators and prey.
     */
    private void populate()
    {
        Random rand = Randomizer.getRandom();
        field.clear();
        for(int row = 0; row < field.getDepth(); row++) {
            for(int col = 0; col < field.getWidth(); col++) {
                if(rand.nextDouble() <= ELEPHANT_CREATION_PROBABILITY) {
                    Location location = new Location(row, col);
                    Elephant elephant = new Elephant(true, field, location);
                    entities.add(elephant);
                }
                else if(rand.nextDouble() <= ZEBRA_CREATION_PROBABILITY) {
                    Location location = new Location(row, col);
                    Zebra zebra = new Zebra(true, field, location);
                    entities.add(zebra);
                }
                else if(rand.nextDouble() <= BUFFALO_CREATION_PROBABILITY) {
                    Location location = new Location(row, col);
                    Buffalo buffalo = new Buffalo(true, field, location);
                    entities.add(buffalo);
                }
                else if(rand.nextDouble() <= GRASS_CREATION_PROBABILITY) {
                    Location location = new Location(row, col);
                    Grass grass = new Grass(field, location);
                    entities.add(grass);
                }
                else if(rand.nextDouble() <= LION_CREATION_PROBABILITY) {
                    Location location = new Location(row, col);
                    Lion lion = new Lion(true, field, location);
                    entities.add(lion);
                }
                else if(rand.nextDouble() <= HUMAN_CREATION_PROBABILITY) {
                    Location location = new Location(row, col);
                    Human human = new Human(true, field, location);
                    entities.add(human);
                }

                // else leave the location empty.
            }
        }
    }

    /**
     * Pause for a given time.
     * @param millisec  The time to pause for, in milliseconds
     */
    private void delay(int millisec)
    {
        try {
            Thread.sleep(millisec);
        }
        catch (InterruptedException ie) {
            // wake up
        }
    }

    public static void main(String[] args) {
        Simulator simulator =  new Simulator();
    }

}