import java.util.List;

/**
 *A class that defines the common characteristics of all
 *living entities in the simulation.
 *
 * @author Aymen Berbache and Aleks
 * @version 2022.02
 */
public abstract class Entity {
    //Characteristics shared by all entity
    // The animal's field.
    private Field field;
    // The animal's position in the field.
    private Location location;


    // Whether the entity is alive or not.
    private boolean alive;
    //The entity' age
    protected int age;

    /**
     * Create a new entity
     * @param field The current field
     * @param location The location on the field.
     */
    public Entity(Field field, Location location) {
        this.field = field;
        setLocation(location);
        alive = true;
        age = 0;
    }

    /**
     * Increases the entity age.
     */
    protected void incrementAge()
    {
        age++;
        if(age>getMAX_AGE()) {
            setDead();
        }
    }
    /**
     * @return true if entity is alive
     */
    protected boolean isAlive()
    {
        return alive;
    }

    /**
     * Indicate that the animal/plant is no longer alive.
     * It is removed from the field.
     */
    protected void setDead()
    {
        alive = false;
        if(location != null) {
            field.clear(location);
            location = null;
            field = null;
        }
    }

    /**
     * Place the entity at the new location in the given field.
     * @param newLocation The entity new location.
     */
    protected void setLocation(Location newLocation)
    {
        if(location != null) {
            field.clear(location);
        }
        location = newLocation;
        field.place(this, newLocation);
    }
    /**
     * Return the entity location.
     * @return The entity location.
     */
    protected Location getLocation()
    {
        return location;
    }

    /**
     * Return the entity field.
     * @return The entity field.
     */
    protected Field getField()
    {
        return field;
    }


    //Abstract methods
    /**
     * Make this entity act - animals hunt, breed, move, age
     *                       - plants grow, spread
     * @param newEntity A list to receive newly born animals.
     */
    abstract public void act(List<Entity> newEntity);

    /**
     *  @return the entity max age.
     */
    protected abstract int getMAX_AGE();

    /**
     * @return the entity food value.
     */
    protected abstract int getFOOD_VALUE();
}