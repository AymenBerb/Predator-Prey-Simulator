import java.awt.*;
import javax.swing.*;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * A graphical view of the simulation grid.
 * The view displays a colored rectangle for each location
 * representing its contents. It uses a default background color.
 * Colors for each type of species can be defined using the
 * setColor method.
 *
 * @author Aymen Berbache and Aleks
 * @version 2022.02
 */
public class SimulatorView extends JFrame
{
    // A variable that holds reference to the simulator.
    private final Simulator simulator;
    // Colors used for empty locations.
    private static final Color EMPTY_COLOR = Color.WHITE;
    // Color used for objects that have no defined color.
    private static final Color UNKNOWN_COLOR = Color.BLUE;

    private final String STEP_PREFIX = "Step: ";
    private final String POPULATION_PREFIX = "Population: ";
    private final String TIME_PREFIX = "Time: ";
    private final String WEATHER_PREFIX = "Weather: ";
    private final String DISEASE_PREFIX = "Disease Deaths: ";

    private final JLabel stepLabel, population, timeLabel, weatherLabel, diseaseLabel;

    private final FieldView fieldView;

    // A map for storing colors for participants in the simulation
    private final Map<Class, Color> colors;
    // A statistics object computing and storing simulation information
    private final FieldStats stats;

    /**
     * Create a view of the given width and height.
     * @param height The simulation's height.
     * @param width  The simulation's width.
     */
    public SimulatorView(Simulator simulator, int height, int width)
    {
        // Reference to simulator is being stored so that it can be accessed later on.
        this.simulator  = simulator;

        stats = new FieldStats();
        colors = new LinkedHashMap<>();

        setTitle("Savanna Simulation");

        // Labels
        stepLabel = new JLabel(STEP_PREFIX,JLabel.CENTER);
        stepLabel.setPreferredSize(new Dimension(150,20));
        population = new JLabel(POPULATION_PREFIX, JLabel.CENTER);
        population.setPreferredSize(new Dimension(600,20));
        timeLabel = new JLabel(TIME_PREFIX, JLabel.CENTER);
        timeLabel.setPreferredSize(new Dimension(100,20));
        weatherLabel = new JLabel(WEATHER_PREFIX, JLabel.CENTER);
        weatherLabel.setPreferredSize(new Dimension(200,20));
        diseaseLabel = new JLabel(DISEASE_PREFIX, JLabel.CENTER);
        diseaseLabel.setPreferredSize(new Dimension(200,20));

        // Buttons
        JButton runLongButton = new JButton("Run/Stop");
        runLongButton.addActionListener(e -> runStopEvent());
        JButton oneStepButton = new JButton("One Step");
        oneStepButton.addActionListener(e -> oneStepEvent());
        JButton resetButton = new JButton("Reset");
        resetButton.addActionListener(e -> resetEvent());

        setLocation(100, 50);

        fieldView = new FieldView(height, width);

        // Information panel at the top of the stage
        JPanel topPanel = new JPanel();
        topPanel.setBackground(Color.LIGHT_GRAY);
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.LINE_AXIS));
        topPanel.setBorder(BorderFactory.createTitledBorder("INFORMATION"));
        topPanel.add(timeLabel);
        topPanel.add(Box.createGlue()); // Create space between labels.
        topPanel.add(stepLabel);
        topPanel.add(Box.createGlue());
        topPanel.add(weatherLabel);

        // Statistics panel at the bottom of the stage
        JPanel bottomPanel = new JPanel();
        bottomPanel.setBackground(Color.LIGHT_GRAY);
        bottomPanel.setLayout(new BoxLayout(bottomPanel, BoxLayout.LINE_AXIS));
        bottomPanel.setBorder(BorderFactory.createTitledBorder("STATISTICS"));
        bottomPanel.add(population);
        bottomPanel.add(Box.createGlue());
        bottomPanel.add(diseaseLabel);

        // Buttons panel at the right of the stage
        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setBackground(Color.LIGHT_GRAY);
        buttonsPanel.setLayout(new GridLayout(3,1));
        buttonsPanel.setBorder(BorderFactory.createTitledBorder("CONTROLS"));
        buttonsPanel.add(runLongButton);
        buttonsPanel.add(oneStepButton);
        buttonsPanel.add(resetButton);


        // Manage the layout of the stage
        Container contents = getContentPane();
        contents.setLayout(new BorderLayout());
        contents.add(topPanel, BorderLayout.NORTH);
        contents.add(fieldView, BorderLayout.CENTER);
        contents.add(bottomPanel, BorderLayout.SOUTH);
        contents.add(buttonsPanel, BorderLayout.WEST);

        pack();
        setVisible(true);
    }

    /**
     * Implements the event that occurs when button "Run/Stop" is clicked.
     */
    private void runStopEvent(){
        if(java.lang.Thread.activeCount() == 3){
            // If the simulation isn't running, make it start running in a new Thread.
            simulator.isRunning = true;
            // Start new Thread
            new Thread(simulator::runLongSimulation).start();
        } else{
            // Terminate thread.
            simulator.isRunning = false;
        }
    }

    /**
     * Implements the event that occurs when button "One Step" is clicked.
     */
    private void oneStepEvent(){
        // Terminate Thread if simulation is currently running
        if(java.lang.Thread.activeCount() == 4){
            simulator.isRunning = false;
        }else{
            // Method call from simulator class via simulator instance.
            simulator.simulateOneStep();
        }
    }

    /**
     * Implements the event that occurs when button "Reset" is clicked.
     */
    private void resetEvent(){
        simulator.isRunning  =  false;
        simulator.reset();
    }

    /**
     * Define a color to be used for a given class of animal.
     * @param animalClass The animal's Class object.
     * @param color The color to be used for the given class.
     */
    public void setColor(Class animalClass, Color color)
    {
        colors.put(animalClass, color);
    }


    /**
     * @return The color to be used for a given class of animal.
     */
    private Color getColor(Class animalClass)
    {
        Color col = colors.get(animalClass);
        if(col == null) {
            // no color defined for this class
            return UNKNOWN_COLOR;
        }
        else {
            return col;
        }
    }

    /**
     * Show the current status of the field.
     * @param step Which iteration step it is.
     * @param field The field whose status is to be displayed.
     */
    public void showStatus(int step, Field field, String time, Weather weather, Disease disease)
    {
        if(!isVisible()) {
            setVisible(true);
        }

        stepLabel.setText(STEP_PREFIX + step);
        timeLabel.setText(TIME_PREFIX + time);
        weatherLabel.setText(WEATHER_PREFIX + weather.getWeatherStatus());
        diseaseLabel.setText(DISEASE_PREFIX + disease.getDeaths());
        stats.reset();


        fieldView.preparePaint();

        for(int row = 0; row < field.getDepth(); row++) {
            for(int col = 0; col < field.getWidth(); col++) {
                Object animal = field.getObjectAt(row, col);
                if(animal != null) {
                    stats.incrementCount(animal.getClass());
                    fieldView.drawMark(col, row, getColor(animal.getClass()));
                }
                else {
                    fieldView.drawMark(col, row, EMPTY_COLOR);
                }
            }
        }
        stats.countFinished();
        population.setText(POPULATION_PREFIX + stats.getPopulationDetails(field).toString());
        fieldView.repaint();
    }

    /**
     * Determine whether the simulation should continue to run.
     * @return true If there is more than one species alive.
     */
    public boolean isViable(Field field)
    {
        return stats.isViable(field);
    }

    /**
     * Provide a graphical view of a rectangular field. This is
     * a nested class (a class defined inside a class) which
     * defines a custom component for the user interface. This
     * component displays the field.
     * This is rather advanced GUI stuff - you can ignore this
     * for your project if you like.
     */
    private class FieldView extends JPanel
    {
        private final int GRID_VIEW_SCALING_FACTOR = 6;

        private final int gridWidth;
        private final int gridHeight;
        private int xScale, yScale;
        Dimension size;
        private Graphics g;
        private Image fieldImage;

        /**
         * Create a new FieldView component.
         */
        public FieldView(int height, int width)
        {
            gridHeight = height;
            gridWidth = width;
            size = new Dimension(0, 0);
        }

        /**
         * Tell the GUI manager how big we would like to be.
         */
        public Dimension getPreferredSize()
        {
            return new Dimension(gridWidth * GRID_VIEW_SCALING_FACTOR,
                                 gridHeight * GRID_VIEW_SCALING_FACTOR);
        }

        /**
         * Prepare for a new round of painting. Since the component
         * may be resized, compute the scaling factor again.
         */
        public void preparePaint()
        {
            if(! size.equals(getSize())) {  // if the size has changed...
                size = getSize();
                fieldImage = fieldView.createImage(size.width, size.height);
                g = fieldImage.getGraphics();

                xScale = size.width / gridWidth;
                if(xScale < 1) {
                    xScale = GRID_VIEW_SCALING_FACTOR;
                }
                yScale = size.height / gridHeight;
                if(yScale < 1) {
                    yScale = GRID_VIEW_SCALING_FACTOR;
                }
            }
        }
        
        /**
         * Paint on grid location on this field in a given color.
         */
        public void drawMark(int x, int y, Color color)
        {
            g.setColor(color);
            g.fillRect(x * xScale, y * yScale, xScale-1, yScale-1);
        }

        /**
         * The field view component needs to be redisplayed. Copy the
         * internal image to screen.
         */
        public void paintComponent(Graphics g)
        {
            if(fieldImage != null) {
                Dimension currentSize = getSize();
                if(size.equals(currentSize)) {
                    g.drawImage(fieldImage, 0, 0, null);
                }
                else {
                    // Rescale the previous image.
                    g.drawImage(fieldImage, 0, 0, currentSize.width, currentSize.height, null);
                }
            }
        }
    }
}