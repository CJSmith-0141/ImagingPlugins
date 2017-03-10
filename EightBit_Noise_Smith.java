import ij.IJ;
import ij.ImagePlus;
import ij.plugin.filter.PlugInFilter;
import ij.process.ImageProcessor;
import java.lang.Math;
import java.util.Random;
public class EightBit_Noise_Smith implements PlugInFilter
{
    ImagePlus im = IJ.getImage();
        public int setup(String arg, ImagePlus im)
        {
            return DOES_8G; //accepts 8-bits gray scale
        }
        public void run(ImageProcessor ip)
        {
            int w = ip.getWidth();
            int h = ip.getHeight();
            float p =0;
            Random rand_p = new Random();
                //iterate
                for(int u = 0;u<w;u++)
                {
                    for(int v = 0;v<h;v++)
                    {
                        p =    (float)ip.getPixel(u, v);
                        //This value changes to change the standard deviation
                        p = p + (float)(rand_p.nextGaussian()*25);
                        ip.set(u, v, Math.round(p));
                    }
                }
            im.updateAndDraw();
        }
}
