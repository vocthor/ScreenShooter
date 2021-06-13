
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class UIControleur implements Initializable {
    @FXML private Button startCaptureButton;
    @FXML private Button saveChangesButton;
    @FXML private Button pauseCaptureButton;
    @FXML private Button stopCaptureButton;
    @FXML private Button newWinButton;
    @FXML private TextField pathTextField;
    @FXML private TextField nameTextField;
    @FXML private Spinner<Integer> capturePeriodSpinner;
    @FXML private TextArea displayTextArea;

    private ScreenShooter sc;

    private Timer time;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        pauseCaptureButton.setDisable(true);
        stopCaptureButton.setDisable(true);
        sc = new ScreenShooter(null, null);
    }

    @FXML
    void startCapture (ActionEvent event){
        pauseCaptureButton.setDisable(false);
        stopCaptureButton.setDisable(false);
        startCaptureButton.setDisable(true);
        lockParameters(true);
        displayTextArea.appendText("[START] \n");
        System.out.println("[START]");
        time = new Timer();
        time.schedule (new TimerTask(){
            public void run () {
                //displayTextArea.appendText("[RUNNING] " + sc.getFileName() + ".jpg  stored in \"" + sc.getPath() + "\" \n");
                sc.capture();
                System.out.println("[RUNNING] " + sc.getFileName() + ".jpg  stored in \"" + sc.getPath() + "\"" );
            }
        },0,sc.getPeriod());
    }

    @FXML 
    void chosenPath (ActionEvent event){
        sc.setPath(pathTextField.getText());
        displayTextArea.appendText("Path set to \""+sc.getPath()+"\" \n");
        System.out.println("Path set to \""+sc.getPath()+"\"");
    }

    @FXML 
    void chosenName (ActionEvent event){
        sc.setName(nameTextField.getText());
        displayTextArea.appendText("Name set to \""+sc.getName() +"\" \n");
        System.out.println("Name set to \""+sc.getName() +"\"");
    }

    @FXML
    void capturePeriod (ActionEvent event){
        sc.setPeriod(capturePeriodSpinner.getValue());
        displayTextArea.appendText("Capture period set to " + sc.getPeriod() + " millisecond(s) \n");
        System.out.println("Capture period set to " + sc.getPeriod() + " millisecond(s)");
    }

    @FXML
    void saveChanges (ActionEvent event){
        chosenPath(event);
        chosenName(event);
        capturePeriod(event);
    } 

    @FXML
    void pauseCapture (ActionEvent event){
        displayTextArea.appendText("[PAUSE] \n");
        System.out.println("[PAUSE]");
        time.cancel();
        pauseCaptureButton.setDisable(true);
        startCaptureButton.setDisable(false);
    }

    @FXML
    void stopCapture (ActionEvent event){
        displayTextArea.appendText("[STOP] The parameters are now reset \n");
        System.out.println("[STOP]");
        time.cancel();
        pauseCaptureButton.setDisable(true);
        stopCaptureButton.setDisable(true);
        startCaptureButton.setDisable(false);
        lockParameters(false);
        pathTextField.clear();
        nameTextField.clear();
        sc = new ScreenShooter(null, null);
        saveChanges(event);
    }

    @FXML
    void newWindow (ActionEvent event){
        System.out.println("New Window");
    }

    private void lockParameters (boolean b){
        nameTextField.setDisable(b);
        pathTextField.setDisable(b);
        saveChangesButton.setDisable(b);
        capturePeriodSpinner.setDisable(b);
    }
}
