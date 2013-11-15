package an.dpr.routeanalyzer.util;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import javax.imageio.ImageIO;
import javax.imageio.stream.FileImageInputStream;
import javax.imageio.stream.FileImageOutputStream;
import javax.swing.ImageIcon;

/**
 * Historial CVS de $Id: ImageTest.java $
 * $Log: ImageTest.java,v $
 *
 */
/**
 * ImageTest
 *
 * @author ricardo.saez, Ultima modificacion:  $Author:  $
 * @version $Revision:  $ $Date: $
 */
public class ImageTest {

    public static void main(String[] args) {
        try {
            
        } catch (Exception ex) {
            System.out.println("Exception: " + ex);
            ex.printStackTrace();
        }
    }

    private static byte[] ConvertImageToByteArray(Image imageToConvert) throws IOException {
        byte[] data;
        BufferedImage img = ImageIO.read(ImageTest.class.getResource("/es/masterd/gaed/cliente/resources/images/euro.png"));
        ByteArrayOutputStream bas = new ByteArrayOutputStream();
        ImageIO.write(img, "pnm", bas);
        data = bas.toByteArray();

        return data;
    }

}
