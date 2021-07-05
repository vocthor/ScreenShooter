package Controller;

import java.awt.MouseInfo;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * Controller class for the TransparentWindow.fxml file
 * This window can only be created passing by the {@code Selected Area} menu
 * @author vocthor
 */
public class TransparentController{

    @FXML private BorderPane transparentBorderPane;
    @FXML private Label transpLabel;

    /**
     * Main window from which this window was created
     */
    MainController mainController;

    /**
     * Rectangle of the selected area
     */
    Rectangle selectedRectangle;

    /**
     * Setter of the parent controller (parent window)
     * @param mainController (MainController) parent controller
     */
    public void setParentController(MainController mainController){
        this.mainController = mainController;
    }

    /**
     * Sets the selectAreaOriginX and selectAreaOriginY values of the mainController
     * @param event
     * @see {@link Controller.MainController}
     */
    @FXML 
    void mousePressed (MouseEvent event){
        mainController.setSelectAreaOriginX(MouseInfo.getPointerInfo().getLocation().getX());
        mainController.setSelectAreaOriginY(MouseInfo.getPointerInfo().getLocation().getY());
        mainController.getDisplayTextArea().appendText("Selected Area set up : \n x = " + mainController.getSelectAreaOriginX() +"\n y = " + mainController.getSelectAreaOriginY() + "\n ");
        //System.out.println(MouseInfo.getPointerInfo().getLocation().getX() + " and " +  MouseInfo.getPointerInfo().getLocation().getY());
        selectedRectangle = new Rectangle();
        selectedRectangle.setX(mainController.getSelectAreaOriginX());
        selectedRectangle.setY(mainController.getSelectAreaOriginY());
        selectedRectangle.setFill(Color.RED);
    }

    /**
     * Sets the selectAreaWidth and selectAreaHeight values of the mainController
     * @param event
     * @see {@link Controller.MainController}
     */
    @FXML
    void mouseReleased (MouseEvent event){
        mainController.setSelectAreaWidth(MouseInfo.getPointerInfo().getLocation().getX()-mainController.getSelectAreaOriginX());
        mainController.setSelectAreaHeight(MouseInfo.getPointerInfo().getLocation().getY()-mainController.getSelectAreaOriginY());
        mainController.getDisplayTextArea().appendText("Width = " + mainController.getSelectAreaWidth() + "\n Height = " + mainController.getSelectAreaHeight() + "\n \n");
        //System.out.println(MouseInfo.getPointerInfo().getLocation().getX() + " and " +  MouseInfo.getPointerInfo().getLocation().getY());
        selectedRectangle.setWidth(mainController.getSelectAreaWidth());
        selectedRectangle.setHeight(mainController.getSelectAreaHeight());
        transparentBorderPane.setCenter(selectedRectangle);
    }
}
