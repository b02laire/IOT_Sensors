import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;

import java.net.SocketException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import network.Server;
/**
*Class App
*Displays an interactive chart populated by data recovered via UDP
*Inspired by "JavaFX Realtime Demo" by Kasun Vithanage
*@see: https://levelup.gitconnected.com/realtime-charts-with-javafx-ed33c46b9c8d

*/
public class App extends Application {
	final int WINDOW_SIZE = 20;
	private ScheduledExecutorService scheduledExecutorService;


    public static void main(String[] args) throws SocketException {
        launch(args);
    }
		/**
		*Start method
		Used to initialize the JavaFX Chart as well as the server
		*/
    public void start(Stage primaryStage) throws Exception {
    	Server server = new Server();
        primaryStage.setTitle("JavaFX Realtime Chart Demo");

        // show the stage
        primaryStage.show();
        //defining the axes
        final CategoryAxis xAxis = new CategoryAxis(); // we are gonna plot against time
        final NumberAxis yAxis = new NumberAxis();
        xAxis.setLabel("Time/s");
        xAxis.setAnimated(false); // axis animations are removed
        yAxis.setLabel("Value");
        yAxis.setAnimated(false); // axis animations are removed

        //creating the line chart with two axis created above
        final LineChart<String, Number> lineChart = new LineChart<>(xAxis, yAxis);
        lineChart.setTitle("Realtime JavaFX Charts");
        lineChart.setAnimated(false); // disable animations

        //defining a series to display data
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Data Series");

        // add series to chart
        lineChart.getData().add(series);

        // setup scene
        Scene scene = new Scene(lineChart, 800, 600);
        primaryStage.setScene(scene);

     		// this is used to display time in HH:mm:ss format
        final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss");

        // setup a scheduled executor to periodically put data into the chart
        scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();

        // All the code inside the scheduleAtFixedRate method will be executed every second
        scheduledExecutorService.scheduleAtFixedRate(() -> {

        	server.run(); // retrieiving data from the Sensor
            // Update the chart
            Platform.runLater(() -> {
                // get current time
                Date now = new Date();
                // put data with current time
                series.getData().add(new XYChart.Data<>(simpleDateFormat.format(now), server.PackettoInt()));
								// Deleting values when reaching WINDOW_SIZE to avoid a crowded chart
								if (series.getData().size() > WINDOW_SIZE)
                    series.getData().remove(0);
            });
        }, 0, 1, TimeUnit.SECONDS);


    }
		/**
		*Stop method
		Called when the GUI is closed to stop the execution
		*/
    @Override
    public void stop() throws Exception {
        super.stop();
        scheduledExecutorService.shutdownNow();
    }

}
