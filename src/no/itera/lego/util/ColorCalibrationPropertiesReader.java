package no.itera.lego.util;


import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ColorCalibrationPropertiesReader {

    public static float[] readColorCalibrationProperties() {
        Properties prop = new Properties();

        float[] values = new float[]{1, 1, 1};

        try {
            //load color calibration values
            prop.load(new FileInputStream("ColorCalibration.properties"));

            String red = prop.getProperty("red");
            String green = prop.getProperty("green");
            String blue = prop.getProperty("blue");

            float r = Float.parseFloat(red);
            float g = Float.parseFloat(green);
            float b = Float.parseFloat(blue);

            values = new float[]{r, g, b};
        } catch (IOException ex) {
            System.out.println("No color calibration file found.");
        } catch (NumberFormatException e) {
            System.out.println("Could not parse all color values");
            e.printStackTrace();
        }

        return values;
    }
}
