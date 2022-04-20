/**
 * A simple class that implements time to the simulation.
 *
 * @author Aymen Berbache and Aleks
 * @version 2022.02
 */
public class Time {
    // A boolean  that holds the current state of time.
    private static boolean isDay = false;

    public Time()
    {
    }

    /**
     * Change the current time by switching the state of the boolean.
     */
    public void switchTime(){
        isDay = !isDay;
    }

    /**
     * @return The current state of time.
     */
    public boolean isDay(){
        return isDay;
    }

    /**
     * @return A string containing the current state of time.
     */
    public String getIsDay(){
        String s = "";
        if(isDay()){
            s += "Day";
        }else{
            s+= "Night";
        }
        return s;
    }

}
