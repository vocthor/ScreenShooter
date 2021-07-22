
import Controller.MainController;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.application.Platform;


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
        FXMLLoader mainLoader = new FXMLLoader(getClass().getResource("/View/MainWindow.fxml"));		//bunch of Javafx stuff
        Parent root = (Parent) mainLoader.load();
        MainController mainController = mainLoader.getController();
        Scene scene = new Scene(root);
		primaryStage.setScene(scene);
		primaryStage.setTitle("ScreenShooter");
        primaryStage.setResizable(true);
        primaryStage.centerOnScreen();
        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {	//capture stops when closing main window
            @Override
            public void handle(WindowEvent t) {
                mainController.stopCapture(new ActionEvent());    //Is this line really useful ?
                Platform.exit();
                System.exit(0);
            }
        });
		primaryStage.show();
    }
    
}
