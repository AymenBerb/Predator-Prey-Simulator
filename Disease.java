/**
 * A model of disease implemented in the simulation.
 * Some animals are occasionally infected, and infection can spread
 * to other animals when they meet. Once an animal is infected,
 * it has a limited number of steps before it dies.
 *
 * @author  Aymen Berbache and Aleks
 * @version 2022.02
 */
public class Disease {

    // The virality of the disease is a rate at which the disease is spread.
    private final double virality;
    // The virulence of the disease indicates how many steps an animal
    // that has contracted the virus has left to live.
    private final int virulence;

    private int count;

    public Disease(double virality, int virulence) {
        this.virality  = virality;
        this.virulence = virulence;
    }

    /**
     * @return The virulence of the disease.
     */
    public int getVirulence(){
        return virulence;
    }

    /**
     * @return The virality of the disease.
     */
    public double getVirality(){
        return virality;
    }


    public void incrementDeath(){
        count++;
    }

    public int getDeaths(){
        return count;
    }

}
