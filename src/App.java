

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.imageio.ImageIO;

import java.awt.image.*;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;


public class App {
    public static void main(String[] args) throws Exception {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yy-MM-dd");
        String date = simpleDateFormat.format(new Date());
        String name = "elec";
        int number = 0;
        //System.out.println("Hello, World!" +date );
        
        Robot robot = new Robot();
        Rectangle rect =new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
        //Rectangle rect =new Rectangle(0, 0, 1920, 1080);
        BufferedImage img = robot.createScreenCapture(rect);
        ImageIO.write(img, "JPG", new File("Output/"+date +"_"+ name +"_"+ number + ".jpg"));
        number++;
    }
}
