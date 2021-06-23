

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.imageio.ImageIO;

import java.awt.image.BufferedImage;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;


public class App {
    
    /** 
     * Just a test function I should delete
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yy-MM-dd");
        String date = simpleDateFormat.format(new Date());
        String name = "elec";
        int number = 0;
        //System.out.println("Hello, World!" +date );
        
        Robot robot = new Robot();
        Rectangle rect =new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
        //Rectangle rect =new Rectangle(0, 0, 1920, 1080);
        for (GraphicsDevice gd : GraphicsEnvironment.getLocalGraphicsEnvironment().getScreenDevices()) {
            rect = rect.union(gd.getDefaultConfiguration().getBounds());
        }
        BufferedImage img = robot.createScreenCapture(rect);
        ImageIO.write(img, "JPG", new File("Output/"+date +"_"+ name +"_"+ number + ".jpg"));
        number++;
    }
}
