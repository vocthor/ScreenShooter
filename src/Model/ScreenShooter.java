package Model;
import java.io.File;
import java.io.IOException;
import java.awt.image.BufferedImage;
import java.awt.AWTException;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.imageio.ImageIO;

/**
 * Object for only one capture session.
 * @author vocthor
 */
public class ScreenShooter {

    /**
     * Where to store the captures
     */
    private String path; 
    /**
     * generic name of the current capture session
     */
    private String name;  
    /**
     *  id for distinguishing the captures of a same capture session
     */      
    private int number;         
    /**
     * time in millisecond between each capture
     */
    private int period;         
    
    
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
        rect =new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
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
     * Getter of the date in format YY_MM_DD
     * @return (String) date
     */
    public String getDate(){
        return date;
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
     * Sets up the rectangle for capturing only the primary monitor
     */
    public void setRectPrimary (){
        rect = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());  
    }

    /**
     * Sets up the rectangle for capturing all the monitors
     */
    public void setRectAll(){
        for (GraphicsDevice gd : GraphicsEnvironment.getLocalGraphicsEnvironment().getScreenDevices()) {
            rect = rect.union(gd.getDefaultConfiguration().getBounds());
        }
    }

    /**
     * Sets up the rectangle for capturing only the selected area
     * @param x x coordinate of the top left corner
     * @param y y coordinate of the top left corner
     * @param width width of the rectangle
     * @param height height of the rectangle
     */
    public void setRectSelectedArea(double x, double y, double width, double height){
        rect = new Rectangle((int) x, (int) y, (int) width, (int) height);
    }

    /**
     * Use the Rectangle and Robot objects created in the constructor to take a screen capture, 
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
