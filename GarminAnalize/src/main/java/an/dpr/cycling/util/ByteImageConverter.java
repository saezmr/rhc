
package an.dpr.cycling.util;

import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriter;
import javax.imageio.stream.FileImageInputStream;
import javax.imageio.stream.ImageInputStream;
import javax.imageio.stream.ImageInputStreamImpl;
import javax.swing.JFileChooser;

/**
 * Historial CVS de $Id: ByteImageConverter.java $
 * $Log: ByteImageConverter.java,v $
 *
 */
/**
 * ByteImageConverter
 *
 * @author ricardo.saez, Ultima modificacion:  $Author:  $
 * @version $Revision:  $ $Date: $
 */
public class ByteImageConverter {


    /**
     * Muestra dialogo para elegir imagen y devuelve la informacion en byte[]
     * @return
     */
    public static byte[] imageFromFileToByte(){
        byte[] data = null;

        JFileChooser jfc = new JFileChooser();
        int returnVal = jfc.showDialog(null, "Attach");
        if (returnVal == JFileChooser.APPROVE_OPTION  && jfc.getSelectedFile() != null){
            try {
                File file = jfc.getSelectedFile();
                FileImageInputStream input = new FileImageInputStream(file);
                ByteArrayOutputStream output = new ByteArrayOutputStream();
                byte[] buf = new byte[1024];
                int numBytesRead = 0;
                while ((numBytesRead = input.read(buf)) != -1) {
                    output.write(buf, 0, numBytesRead);
                }
                data = output.toByteArray();
                output.close();
                input.close();
            } catch (FileNotFoundException ex) {
                Logger.getLogger(ByteImageConverter.class.getName()).log(Level.SEVERE, "No se encontro el fichero", ex);
            } catch (IOException ex) {
                Logger.getLogger(ByteImageConverter.class.getName()).log(Level.SEVERE, "Error Entrada/Salida", ex);
            }
        }

        return data;
    }


    public static byte[] imageToByte(Image img) throws IOException{
        byte[] data = null;
        BufferedImage bu = new BufferedImage(img.getWidth(null),img.getHeight(null),BufferedImage.TYPE_INT_RGB);
        ByteArrayOutputStream bas = new ByteArrayOutputStream();
        ImageIO.write(bu, "pnm", bas);
        data = bas.toByteArray();
        return data;
    }

    /**
     * Devuelve la imagen contenida en el byte[] pasado como parametro
     * @param data
     * @return
     */
    public static Image byteToImage(byte[] data){
        return Toolkit.getDefaultToolkit().createImage(data);
    }

}
