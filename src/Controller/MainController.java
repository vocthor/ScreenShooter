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
     * Stage for the Selected Area feature
     */
    private Stage transpStage;

    /**
     * Timer for the loop (cf MainController.startCapture())
     */
    private Timer time;

    /**
     * Values for the selected area
     */
    private double selectAreaOriginX;   //x coordinate top-left corner
    private double selectAreaOriginY;   //y coordinate top-left corner
    private double selectAreaWidth;     //width
    private double selectAreaHeight;    //height

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
        lockParameters(true);                                                  //Locks parameters
        if (transpStage!=null) transpStage.close();                            //and automatically close the Select Area window
        displayTextArea.appendText("[START] \n");
        //System.out.println("[START]");
        time = new Timer();
        time.schedule (new TimerTask(){
            public void run () {
                sc.capture();
                displayTextArea.appendText("[RUNNING] " + sc.getDate()+"_"+sc.getName()+"_"+(sc.getNum()-1) + ".jpg  stored in \"" + sc.getPath() + "\" \n");
                //System.out.println("[RUNNING] " + sc.getDate()+"_"+sc.getName()+"_"+(sc.getNum()-1) + ".jpg  stored in \"" + sc.getPath() + "\"" );
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
        if(pathFile.isDirectory() && pathFile.canWrite()){      //Is it better to use try-catch ?
            sc.setPath(pathTextField.getText());
            displayTextArea.appendText("    Path set to \""+sc.getPath()+"\" \n");
            //System.out.println("Path set to \""+sc.getPath()+"\"");
            startCaptureButton.setDisable(false);
        }else {
            displayTextArea.appendText("The directory is either inaccessible either unwritable ! \n");
            //System.out.println("The directory is either inaccessible either unwritable !");
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
        //System.out.println("Name set to \""+sc.getName() +"\"");
    }

    /**
     * Configure the ScreenShooter object to take captures on a given regular time basis
     * @param event
     */
    @FXML
    void capturePeriod (ActionEvent event){
        sc.setPeriod(capturePeriodSpinner.getValue());
        displayTextArea.appendText("    Capture period set to " + sc.getPeriod() + " millisecond(s) \n");
        //System.out.println("Capture period set to " + sc.getPeriod() + " millisecond(s)");
    }

    /**
     * Save the changes made to the name, path, or period.
     * Updates the {@link ScreenShooter.rect} var depending on the choice Primary monitor, All monitors, or Selected Area
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
        //System.out.println("[PAUSE]");
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
    public void stopCapture (ActionEvent event){
        displayTextArea.appendText("[STOP] The parameters are now reset \n");
        //System.out.println("[STOP]");
        try{        //in case you close the window without starting the capture even once
            time.cancel();
        } catch (Exception e){
            //System.out.println(e.getMessage());
        }
        pauseCaptureButton.setDisable(true);
        stopCaptureButton.setDisable(true);
        lockParameters(false);
        pathTextField.clear();
        nameTextField.clear();
        primaryMonitorRMI.setSelected(true);
        sc = new ScreenShooter(null, null);
        saveChanges(event);
    }

    /**
     * Opens an other identical window, allowing you to make 2 simultaneous capture session.
     * @param event
     */
    @FXML
    void newWindow (ActionEvent event){
        displayTextArea.appendText("New Window feature is not implemented yet (and will probably never be)\n");
        //System.out.println("New Window feature is not implemented yet");
        /*try{
            Parent root = FXMLLoader.load(getClass().getResource("vue/UI.fxml"));
            
            Stage newStage = new Stage();
            newStage.setScene(new Scene(root));
            newStage.setTitle("ScreenShooter");
            newStage.setResizable(true);
            newStage.centerOnScreen();
            newStage.setOnCloseRequest(new EventHandler<WindowEvent>() {	//capture stops when closing main window
            @Override
            public void handle(WindowEvent t) {
                newController.stopCapture(new ActionEvent());    //Is this line really useful ?
                Platform.exit();
                System.exit(0);
            }
        });  
            newStage.show();
        }catch(IOException e){
            displayTextArea.appendText("Pb when opening new Window");
        }*/
    }

    /**
     * Allows you to take captures of only the selected part of your screen. 
     * A new window with low opacity will be opened for this.
     * @param event
     */
    @FXML
    void selectCaptureZone (ActionEvent event){
        try {
            try{             //In case of a transparent is already opened
                transpStage.close();
            }catch(Exception e){}
            FXMLLoader transpLoader = new FXMLLoader(getClass().getResource("../View/TransparentWindow.fxml"));
            Parent root1 = (Parent) transpLoader.load();
            TransparentController transpController = transpLoader.getController();
            transpController.setParentController(this);
            transpStage = new Stage();
            transpStage.setScene(new Scene(root1));
            transpStage.initModality(Modality.NONE);
            transpStage.setTitle("Select Area");  
            transpStage.setResizable(true);       
            transpStage.setMaximized(true);
            transpStage.setOpacity(0.5);
            transpStage.show();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            displayTextArea.appendText("An IOException has occured !");
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
     * Setter of the selectAreaOriginX var (for the selected area Rectangle)
     * @param x (double)
     */
    public void setSelectAreaOriginX( double x){
        selectAreaOriginX = x;
    }

    /**
     * Setter of the selectAreaOriginY var (for the selected area Rectangle)
     * @param y (double)
     */
    public void setSelectAreaOriginY( double y){
        selectAreaOriginY = y;
    }

    /**
     * Setter of the selectAreaWidth var (for the selected area Rectangle)
     * @param w (double)
     */
    public void setSelectAreaWidth( double w){
        selectAreaWidth = w;
    }

    /**
     * Setter of the selectAreaHeight var (for the selected area Rectangle)
     * @param h (double)
     */
    public void setSelectAreaHeight( double h){
        selectAreaHeight = h;
    }

    /**
     * Getter of the selectAreaOriginX var (for the selected area Rectangle)
     * @return (double)
     */
    public double getSelectAreaOriginX (){
        return selectAreaOriginX;
    }

    /**
     * Getter of the selectAreaOriginY var (for the selected area Rectangle)
     * @return (double)
     */
    public double getSelectAreaOriginY (){
        return selectAreaOriginY;
    }

    /**
     * Getter of the selectAreaWidth var (for the selected area Rectangle)
     * @return (double)
     */
    public double getSelectAreaWidth (){
        return selectAreaWidth;
    }

    /**
     * Getter of the selectAreaHeight var (for the selected area Rectangle)
     * @return (double)
     */
    public double getSelectAreaHeight (){
        return selectAreaHeight;
    }

    /**
     * Getter of the FXML TextArea displayTextArea
     * @return (TextArea) displayTextArea
     */
    public TextArea getDisplayTextArea (){
        return displayTextArea;
    }

    /**
     * Getter of the ScreenShooter object atttribute
     * @return (ScreenShooter) sc
     */
    public ScreenShooter getScreenShooter (){
        return sc;
    }

}
