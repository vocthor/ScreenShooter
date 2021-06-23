
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.RadioMenuItem;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;


/**
 * Controller class for the UI.fxml file
 * @author vocthor
 */
public class UIControleur implements Initializable {

    /**
     * All the javafx objects.
     * cf UI.fxml
     */
    @FXML private Button startCaptureButton;
    @FXML private Button saveChangesButton;
    @FXML private Button pauseCaptureButton;
    @FXML private Button stopCaptureButton;
    @FXML private Button newWinButton;
    @FXML private Button browseButton;
    @FXML private TextField pathTextField;
    @FXML private TextField nameTextField;
    @FXML private Spinner<Integer> capturePeriodSpinner;
    @FXML private TextArea displayTextArea;
    @FXML private Menu monitorMenu;
    @FXML private RadioMenuItem primaryMonitorRMI;
    @FXML private RadioMenuItem allMonitorsRMI;
    @FXML private RadioMenuItem selectedAreaRMI;

    /**
     * Object taking and saving the current capture session. 
     * cf ScreenShooter.java
     */
    private ScreenShooter sc;

    private Timer time;
    

    /**
     * By default values when launching the program
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        startCaptureButton.setDisable(true);
        pauseCaptureButton.setDisable(true);
        stopCaptureButton.setDisable(true);
        sc = new ScreenShooter(null, null);
        displayTextArea.appendText("You should read the README.md file or click on the Help menu for how to use this program and more ! \n \n");
    }

    /**
     * Starts taking captures until "Pause Capture" or "Stop Capture" are pressed.
     * @param event
     */
    @FXML
    void startCapture (ActionEvent event){
        pauseCaptureButton.setDisable(false);
        stopCaptureButton.setDisable(false);
        startCaptureButton.setDisable(true);
        lockParameters(true);
        displayTextArea.appendText("[START] \n");
        System.out.println("[START]");
        time = new Timer();
        if (primaryMonitorRMI.isSelected()){
            time.schedule (new TimerTask(){
                public void run () {
                    //displayTextArea.appendText("[RUNNING] " + sc.getFileName() + ".jpg  stored in \"" + sc.getPath() + "\" \n");
                    sc.capturePrimaryScreen();
                    System.out.println("[RUNNING] " + sc.getFileName() + ".jpg  stored in \"" + sc.getPath() + "\"" );
                }
            },0,sc.getPeriod());
        } else if (allMonitorsRMI.isSelected()){
            time.schedule (new TimerTask(){
                public void run () {
                    //displayTextArea.appendText("[RUNNING] " + sc.getFileName() + ".jpg  stored in \"" + sc.getPath() + "\" \n");
                    sc.captureAllScreens();
                    System.out.println("[RUNNING] " + sc.getFileName() + ".jpg  stored in \"" + sc.getPath() + "\"" );
                }
            },0,sc.getPeriod());
        }else if (selectedAreaRMI.isSelected()){

        }
    }

    /**
     * Configure the ScreenShooter object to store the captures into the given directory's path.
     * @param event
     */
    @FXML 
    void chosenPath (ActionEvent event){
        File pathFile = new File(pathTextField.getText());
        if(pathFile.isDirectory() && pathFile.canWrite()){
            sc.setPath(pathTextField.getText());
            displayTextArea.appendText("    Path set to \""+sc.getPath()+"\" \n");
            System.out.println("Path set to \""+sc.getPath()+"\"");
            startCaptureButton.setDisable(false);
        }else {
            displayTextArea.appendText("The directory is either inaccessible either unwritable ! \n");
            System.out.println("The directory is either inaccessible either unwritable !");
        }
    }

    /**
     * Same as chosenPath(ActionEvent) but in a other way
     * @param event
     */
    @FXML
    void selectDirectory (ActionEvent event){
        displayTextArea.appendText("Select where to store the captures \n");
        DirectoryChooser dc = new DirectoryChooser();
        dc.setTitle("Select where to store the captures");
        File sd = dc.showDialog(new Stage());
        pathTextField.appendText(sd.getAbsolutePath()+"\\");
    }

    /**
     * Configure the ScreenShooter object to save the captures with a name based on the given name.
     * @param event
     */
    @FXML 
    void chosenName (ActionEvent event){
        sc.setName(nameTextField.getText());
        displayTextArea.appendText("    Name set to \""+sc.getName() +"\" \n");
        System.out.println("Name set to \""+sc.getName() +"\"");
    }

    /**
     * Configure the ScreenShooter object to take captures on a given regular time basis
     * @param event
     */
    @FXML
    void capturePeriod (ActionEvent event){
        sc.setPeriod(capturePeriodSpinner.getValue());
        displayTextArea.appendText("    Capture period set to " + sc.getPeriod() + " millisecond(s) \n");
        System.out.println("Capture period set to " + sc.getPeriod() + " millisecond(s)");
    }

    /**
     * Save the changes made to the name, path, or period.
     * @param event
     */
    @FXML
    void saveChanges (ActionEvent event){
        chosenPath(event);
        chosenName(event);
        capturePeriod(event);
    } 

    /**
     * Can only be activated when a capture session is going on.
     * Pauses the current capture session.
     * Parameters are still locked.
     * @param event
     */
    @FXML
    void pauseCapture (ActionEvent event){
        displayTextArea.appendText("[PAUSE] \n");
        System.out.println("[PAUSE]");
        time.cancel();
        pauseCaptureButton.setDisable(true);
        startCaptureButton.setDisable(false);
    }

    /**
     * Can only be activated when a capture session is going on.
     * Stops the current capture session.
     * Parameters are reset and unlocked.
     * @param event
     */
    @FXML
    void stopCapture (ActionEvent event){
        displayTextArea.appendText("[STOP] The parameters are now reset \n");
        System.out.println("[STOP]");
        time.cancel();
        pauseCaptureButton.setDisable(true);
        stopCaptureButton.setDisable(true);
        lockParameters(false);
        pathTextField.clear();
        nameTextField.clear();
        sc = new ScreenShooter(null, null);
        saveChanges(event);
    }

    /**
     * Opens an other identical window, allowing you to make 2 simultaneous capture session.
     * @param event
     */
    @FXML
    void newWindow (ActionEvent event){
        displayTextArea.appendText("New Window feature is not implemented yet \n");
        System.out.println("New Window feature is not implemented yet");
    }

    /**
     * Allows you to take captures of your primary monitor
     * @param event
     */
    @FXML
    void primaryMonitorCapture (ActionEvent event){
        displayTextArea.appendText("[MONITOR] Primary monitor \n");
        System.out.println("[MONITOR] Primary monitor");
    }

    /**
     * Allows you to take captures of all your monitors
     * @param event
     */
    @FXML
    void allMonitorsCapture (ActionEvent event){
        displayTextArea.appendText("[MONITOR] All monitors \n");
        System.out.println("[MONITOR] All monitors");
    }

    /**
     * Allows you to take captures of only the selected part of your screen.
     * @param event
     */
    @FXML
    void selectCaptureZone (ActionEvent event){
        displayTextArea.appendText("[MONITOR] Selected area \n");
        System.out.println("[MONITOR] Selected area");
    }

    /**
     * Display README.md text in the "Console" text area
     * @param event
     */
    @FXML
    void helpDisplay (MouseEvent event){
        displayTextArea.appendText("[HELP]\n");
        try(BufferedReader br = new BufferedReader(new FileReader("README.md"))){
            while(br.ready()){
                displayTextArea.appendText(br.readLine()+"\n");
            }
        }catch(FileNotFoundException e){
            displayTextArea.appendText(e.getMessage());
        }catch(IOException e){
            displayTextArea.appendText(e.getMessage());
        }
    }

    /**
     * Just clear the console
     * Keeps only the first the 1° message "You should read ..."
     * @param event
     */
    @FXML
    void clearConsole (ActionEvent event){
        displayTextArea.clear();
        displayTextArea.appendText("You should read the README.md file or click on the Help menu for how to use this program and more ! \n");
    }

    /**
     * 
     * @param b boolean for locking (true) / unlocking (false) the parameters
     */
    private void lockParameters (boolean b){
        nameTextField.setDisable(b);
        pathTextField.setDisable(b);
        saveChangesButton.setDisable(b);
        capturePeriodSpinner.setDisable(b);
        browseButton.setDisable(b);
        monitorMenu.setDisable(b);
    }
}
