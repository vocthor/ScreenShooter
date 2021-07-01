
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


/**
 * Class Visualization (starting point of the program) 
 * @author vocthor
 */
public class Visualiseur extends Application {

    
    /** 
     * Main function
     * @param args
     */
    public static void main(String[] args) {
        launch(args);
    }

    /**
     * Method that set up the scene
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("MainWindow.fxml"));
        Scene scene = new Scene(root);

		primaryStage.setScene(scene);
		primaryStage.setTitle("ScreenShooter");
        primaryStage.setResizable(true);
        primaryStage.centerOnScreen();
		primaryStage.show();
		
    }
    
}
