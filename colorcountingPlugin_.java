import ij.IJ;
import ij.ImagePlus;
import ij.plugin.filter.PlugInFilter;
import ij.process.ColorProcessor;
import ij.process.ImageProcessor;
public class colorcountingPlugin_ implements PlugInFilter
{
    public int setup(String arg, ImagePlus im)
    {
        return DOES_RGB; //accepts 8-bits gray scale
    }
    public void run(ImageProcessor ip)
    {
    
        ColorProcessor cp = ip.convertToColorProcessor();
        int w = ip.getWidth();
        int h = ip.getHeight();
        double r_counter = 0;
        double g_counter = 0;
        double b_counter = 0;
        double w_counter = 0;
        double k_counter = 0;
             //iterate
             for(int u = 0;u<w;u++)
             {
                 for(int v = 0;v<h;v++)
                 {
                     int[] rgb_array = {0,0,0};

                     cp.getPixel(u, v, rgb_array);

                     if(rgb_array[0] == 255 && rgb_array[1] ==   0 && rgb_array[2] == 0) r_counter++;

                     if(rgb_array[0] ==   0 && rgb_array[1] == 255 && rgb_array[2] == 0) g_counter++;

                     if(rgb_array[0] ==   0 && rgb_array[1] ==   0 && rgb_array[2] == 255) b_counter++;

                     if(rgb_array[0] ==   0 && rgb_array[1] ==   0 && rgb_array[2] == 0) k_counter++;

                     if(rgb_array[0] == 255 && rgb_array[1] == 255 && rgb_array[2] == 255) w_counter++;

                 }
             }
         IJ.log("RED: ");
         IJ.log(Double.toString(r_counter));
         IJ.log(" GREEN: ");
         IJ.log(Double.toString(g_counter));
         IJ.log(" BLUE: ");
         IJ.log(Double.toString(b_counter));
         IJ.log("BLACK: ");
         IJ.log(Double.toString(k_counter));
         IJ.log("WHITE: ");
         IJ.log(Double.toString(w_counter));
    }
}
