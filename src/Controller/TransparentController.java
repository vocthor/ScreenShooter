package Controller;

import java.awt.MouseInfo;

import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;

/**
 * Controller class for the TransparentWindow.fxml file
 * This window can only be created passing by the {@code Selected Area} menu
 * @author vocthor
 */
public class TransparentController{

    /**
     * Main window from which this window was created
     */
    MainController mainController;

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
        //displayTextArea.appendText("Selected Area set up : \n x = " + selectAreaOriginX +"\n y = " + selectAreaOriginY + "\n ");
        System.out.println(MouseInfo.getPointerInfo().getLocation().getX() + " and " +  MouseInfo.getPointerInfo().getLocation().getY());
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
        //displayTextArea.appendText("Width = " + selectAreaWidth + "\n Height = " + selectAreaHeight + "\n");
        System.out.println(MouseInfo.getPointerInfo().getLocation().getX() + " and " +  MouseInfo.getPointerInfo().getLocation().getY());
    }
}
