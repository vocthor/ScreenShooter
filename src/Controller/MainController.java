package Controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;

import Model.ScreenShooter;
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
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;


/**
 * Controller class for the MainWindow.fxml file
 * @author vocthor
 */
public class MainController implements Initializable {

    /**
     * All the javafx objects.
     * cf MainWindow.fxml
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

    /**
     * Timer for the loop (cf MainController.startCapture())
     */
    private Timer time;

    /**
     * Values for the selected area
     */
    private double selectAreaOriginX;
    private double selectAreaOriginY;
    private double selectAreaWidth;
    private double selectAreaHeight;

    /**
     * By default values when launching the program
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try{
            startCaptureButton.setDisable(true);
            pauseCaptureButton.setDisable(true);
            stopCaptureButton.setDisable(true);
            sc = new ScreenShooter(null, null);
            displayTextArea.appendText("You should read the README.md file or click on the Help menu for how to use this program and more ! \n \n");
        }catch (Exception e){
            System.out.println("Transparent window init pb");
        }
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
        time.schedule (new TimerTask(){
            public void run () {
                //displayTextArea.appendText("[RUNNING] " + sc.getFileName() + ".jpg  stored in \"" + sc.getPath() + "\" \n");
                System.out.println("[RUNNING] " + sc.getFileName() + ".jpg  stored in \"" + sc.getPath() + "\"" );
                sc.capture();
            }
        },0,sc.getPeriod());
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
        if (primaryMonitorRMI.isSelected()){
            sc.setRectPrimary();
            displayTextArea.appendText("    Primary monitor \n");
        } else if (allMonitorsRMI.isSelected()){
            sc.setRectAll();
            displayTextArea.appendText("    All monitors \n");
        }else if (selectedAreaRMI.isSelected()){
            sc.setRectSelectedArea(selectAreaOriginX, selectAreaOriginY, selectAreaWidth, selectAreaHeight);
            displayTextArea.appendText("    x = " + selectAreaOriginX +"\n    y = " + selectAreaOriginY + 
                                        "\n    Width = " + selectAreaWidth + "\n    Height = " + selectAreaHeight + "\n \n");
        }
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
        try {
            FXMLLoader transpLoader = new FXMLLoader(getClass().getResource("../View/TransparentWindow.fxml"));
            Parent root1 = (Parent) transpLoader.load();
            TransparentController transpController = transpLoader.getController();
            transpController.setParentController(this);
            Stage stage = new Stage();
            stage.setScene(new Scene(root1));
            stage.initModality(Modality.NONE);
            stage.setTitle("Select Area");  
            stage.setResizable(true);       
            stage.setMaximized(true);
            stage.setOpacity(0.5);
            stage.show();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            System.out.println("Zbeub dans la matrice");
        }
            
        
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
     * Lock the different parameters and menus
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

    /**
     * 
     * @param x
     */
    public void setSelectAreaOriginX( double x){
        selectAreaOriginX = x;
    }

    /**
     * 
     * @param y
     */
    public void setSelectAreaOriginY( double y){
        selectAreaOriginY = y;
    }

    /**
     * 
     * @param w
     */
    public void setSelectAreaWidth( double w){
        selectAreaWidth = w;
    }

    /**
     * 
     * @param h
     */
    public void setSelectAreaHeight( double h){
        selectAreaHeight = h;
    }

    /**
     * 
     * @return
     */
    public double getSelectAreaOriginX (){
        return selectAreaOriginX;
    }

    /**
     * 
     * @return
     */
    public double getSelectAreaOriginY (){
        return selectAreaOriginY;
    }

    /**
     * 
     * @return
     */
    public double getSelectAreaWidth (){
        return selectAreaWidth;
    }

    /**
     * 
     * @return
     */
    public double getSelectAreaHeight (){
        return selectAreaHeight;
    }
}
