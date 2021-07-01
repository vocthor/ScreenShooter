package Controller;
import java.net.URL;
import java.util.ResourceBundle;

import java.awt.MouseInfo;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;

public class TransparentController implements Initializable{

    MainController mainController;

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        // TODO Auto-generated method stub
        
    }

    /**
     * 
     * @param mainController
     */
    public void setParentController(MainController mainController){
        this.mainController = mainController;
    }

    /**
     * 
     * @param event
     */
    @FXML 
    void mousePressed (MouseEvent event){
        mainController.setSelectAreaOriginX(MouseInfo.getPointerInfo().getLocation().getX());
        mainController.setSelectAreaOriginY(MouseInfo.getPointerInfo().getLocation().getY());
        //displayTextArea.appendText("Selected Area set up : \n x = " + selectAreaOriginX +"\n y = " + selectAreaOriginY + "\n ");
        System.out.println(MouseInfo.getPointerInfo().getLocation().getX() + " and " +  MouseInfo.getPointerInfo().getLocation().getY());
    }

    /**
     * 
     * @param event
     */
    @FXML
    void mouseReleased (MouseEvent event){
        mainController.setSelectAreaWidth(MouseInfo.getPointerInfo().getLocation().getX()-mainController.getSelectAreaOriginX());
        mainController.setSelectAreaHeight(MouseInfo.getPointerInfo().getLocation().getY()-mainController.getSelectAreaOriginY());
        //displayTextArea.appendText("Width = " + selectAreaWidth + "\n Height = " + selectAreaHeight + "\n");
        System.out.println(MouseInfo.getPointerInfo().getLocation().getX() + " and " +  MouseInfo.getPointerInfo().getLocation().getY());
    }
}
