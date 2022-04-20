import java.util.*;
/**
 * A class that adds weather to the simulation.
 * There are 3 types of weather conditions: rain, fog and
 *
 * @author Aymen Berbache and Aleks
 * @version 2022.02
 */
public class Weather {

    private boolean wind;
    private boolean rain;
    private boolean fog;
    private static final Random rand = Randomizer.getRandom();

    public Weather() {
        weatherUpdate();
    }

    /**
     * Choose randomly the weather state.
     */
    public void weatherUpdate() {
        rain =  rand.nextBoolean();
        fog  = rand.nextBoolean();
        wind =  rand.nextBoolean();
    }

    public String getWeatherStatus() {

        String status = " ";
        if(isRainy()){
            status += "rainy; ";
        }
        if(isFoggy()){
            status += "foggy; ";
        }
        if(isWindy()){
            status += "windy.";
        }
        if(!isRainy() && !isFoggy() && !isWindy()){
            status += "sunny.";
        }
        return status;
    }

    /**
     * @return true if there is wind
     */
    public boolean isWindy(){
        return wind;
    }

    /**
     * @return true if there is rain
     */
    public boolean isRainy(){
        return rain;
    }

    /**
     * @return true if there is fog
     */
    public boolean isFoggy(){
        return fog;
    }


}
