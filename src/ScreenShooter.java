import java.io.File;
import java.io.IOException;
import java.awt.image.*;
import java.awt.AWTException;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.imageio.ImageIO;


public class ScreenShooter {
    private String path;
    private String name;
    private int number;
    private int period;
    
    private Robot robot;
    private Rectangle rect;
    private SimpleDateFormat simpleDateFormat;
    private String date;

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

    public int getNum(){
        return number;
    }

    public String getPath(){
        return path;
    }

    public String getName(){
        return name;
    }

    public int getPeriod(){
        return period;
    }

    public String getFileName(){
        return date +"_"+ name +"_"+ number;
    }

    public void setPath (String p){
        path=p;
    }

    public void setName(String n){
        name=n;
    }

    public void setPeriod (int n){
        period=n;
    }

    public void capturePrimaryScreen() {
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
