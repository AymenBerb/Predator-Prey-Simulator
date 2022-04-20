/**
 * A class that defines common characteristics to all preys in the simulation.
 * A prey is awake during the day and doesn't need visibility to eat.
 * @author Aymen Berbache and Aleks
 * @version 2022.02
 */
public abstract class Prey extends Animal {

    // A variable that holds whether the animal needs visibility to hunt.
    private static final boolean VISIBILITY_REQUIRED = false;
    //All preys live by day and sleeps at night.
    private static final boolean IS_NOCTURNAL = false;

    /**
     * Create a new animal at location in field.
     *
     * @param field    The field currently occupied.
     * @param location The location within the field.
     */
    public Prey(Field field, Location location) {
        super(field, location);
    }

    /**
     * @return the animal visibility requirement to hunt.
     */
    @Override
    protected boolean getVISIBILITY_REQUIRED() {
        return VISIBILITY_REQUIRED;
    }

    /**
     * @return true if the animal lives by night.
     */
    @Override
    protected boolean getIS_NOCTURNAL(){
        return IS_NOCTURNAL;
    }

}