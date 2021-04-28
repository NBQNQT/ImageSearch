import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import javax.imageio.ImageIO;

    public class ImageProcess{
        public static final String path = System.getProperty("user.dir");
        public static BufferedImage thumb(String filename, BufferedImage source, int width,
                                          int height, boolean b){
            int type = source.getType();
            BufferedImage target = null;
            double sx = (double) width/ source.getWidth();
            double sy = (double) height/ source.getHeight();

            if(b){
                if (sx>sy) {
                    sx = sy;
                    width = (int) (sx * source.getWidth());
                }else {
                    sy = sx;
                    height = (int) (sy * source.getHeight());
                }
            }
            if (type == BufferedImage.TYPE_CUSTOM) {
                ColorModel cm = source.getColorModel();
                WritableRaster raster = cm.createCompatibleWritableRaster(width, height);
                boolean alphaPremultiplied = cm.isAlphaPremultiplied();
                target = new BufferedImage(cm, raster, alphaPremultiplied, null);
            } else
                target = new BufferedImage(width, height,type);
            Graphics2D g = target.createGraphics();
            g.setRenderingHint(RenderingHints.KEY_RENDERING,RenderingHints.VALUE_RENDER_QUALITY);
            g.drawRenderedImage(source, AffineTransform.getScaleInstance(sx,sy));
            g.dispose();

            return target;
        }

        public static BufferedImage readPNGImage(String filename){
            try {
                InputStream imageIn = new FileInputStream(new File(filename));
                BufferedImage sourceImage = ImageIO.read(imageIn);

                return sourceImage;
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
        public static BufferedImage readJPEGImage(String filename) {
            try {
                InputStream imageIn = new FileInputStream(new File(filename));
                BufferedImage sourceImage = ImageIO.read(imageIn);

                return sourceImage;
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
        public static int average(int[] pixels) {
            float m =0;
            for (int i=0; i< pixels.length; ++i) {
                m += pixels[i];
            }
            m =m /pixels.length;
            return (int) m;
        }
        public static int rgbToGray(int pixels){
            int _red =(pixels >> 16) & 0xFF;
            int _green =(pixels >> 8) & 0xFF;
            int _blue =(pixels >>0) & 0xFF;
            return (int) (0.3 * _red+0.59 * _green + 0.11 * _blue);
        }
    }
