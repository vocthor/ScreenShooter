import java.io.File;
import java.io.IOException;
import java.awt.image.BufferedImage;
import java.awt.AWTException;
import java.awt.Rectangle;
import java.awt.Robot;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.imageio.ImageIO;

/**
 * Object for only one capture session.
 * @author vocthor
 */
public class ScreenShooter {
    private String path;        //where to store the captures
    private String name;        //generic name of the current capture session
    private int number;         //id for distinguishing the captures of a same capture session
    private int period;
    
    /**
     * Bunch of object used to create the capture and store it
     */
    private Robot robot;
    private Rectangle rect;
    private SimpleDateFormat simpleDateFormat;
    private String date;

    /**
     * Constructor of the ScreenShooter
     * @param p (String) path
     * @param n (String) name
     */
    public ScreenShooter(String p, String n){
        path = p;
        name = n;
        number =0;
        try {
            robot = new Robot();
        } catch (AWTException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        simpleDateFormat = new SimpleDateFormat("yy-MM-dd");
        date = simpleDateFormat.format(new Date());
    }

    /**
     * Getter of the number
     * @return (int) number
     */
    public int getNum(){
        return number;
    }

    /**
     * Getter of the path
     * @return (String) path
     */
    public String getPath(){
        return path;
    }

    /**
     * Getter of the name
     * @return (String) name
     */
    public String getName(){
        return name;
    }

    /**
     * Getter of the period
     * @return (int) period
     */
    public int getPeriod(){
        return period;
    }

    /**
     * Getter of the definitive file's name.
     * The file's name is based on the given name
     * @return (String) fileName
     */
    public String getFileName(){
        return date +"_"+ name +"_"+ number;
    }

    /**
     * Getter of the rectangle used for the screen capture
     * @return (Rectangle) rect
     */
    public Rectangle getRect(){
        return rect;
    }
    /**
     * Setter of the path
     * @param p (String) path
     */
    public void setPath (String p){
        path=p;
    }

    /**
     * Setter of the name
     * @param n (String) name
     */
    public void setName(String n){
        name=n;
    }

    /**
     * Setter of the period
     * @param n (int) period
     */
    public void setPeriod (int n){
        period=n;
    }

    /**
     * Setter of the rect
     * @param rectangle (Rectangle) rect
     */
    public void setRect (Rectangle rectangle){
        rect = rectangle;
    }

    /**
     * Use the Rectangle and Robot objects created in the constructor to take a screen capture of the primary monitor, 
     * and it stores it into the given path
     */
    public void capture() {
        BufferedImage img = robot.createScreenCapture(rect);
        try {
            ImageIO.write(img, "JPG", new File(path+ date +"_"+ name +"_"+ number + ".jpg"));
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        number++;
    }
}
