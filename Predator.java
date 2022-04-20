public abstract class Predator extends Animal {

    // A variable that holds whether the animal needs visibility to hunt.
    private static final boolean VISIBILITY_REQUIRED = true;

    /**
     * Create a new predator at location in field.
     *
     * @param field  The field currently occupied.
     * @param location The location within the field.
     */
    public Predator(Field field, Location location) {
        super(field, location);
    }

    /**
     * @return the animal visibility requirement to hunt.
     */
    @Override
    protected boolean getVISIBILITY_REQUIRED(){
        return VISIBILITY_REQUIRED;
    }

    /**
     * @return Predators have a null food value because they can't be eaten.
     */
    @Override
    protected int getFOOD_VALUE() {
        return 0; //  Predators can't be eaten
    }
}
