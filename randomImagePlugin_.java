import ij.IJ;
import ij.ImagePlus;
import ij.plugin.PlugIn;
import ij.process.ImageProcessor;
import java.lang.Math;
import java.util.Random;
public class randomImagePlugin_ implements PlugIn
{
    public void run(String args)
    {
		 ImagePlus im = IJ.createImage("rando.png", 500, 500, 0, 8);
		 ImagePlus im_1 = IJ.createImage("randoGaussian.png", 500, 500, 0, 8);
		 ImageProcessor ip = im.getProcessor(); //random image
		 ImageProcessor ip_1 = im_1.getProcessor(); //Gaussian distribution
		 int w = 500;
		 int h = 500;
			 //iterate
			 for(int u = 0;u<w;u++)
			 {
				 for(int v = 0;v<h;v++)
				 {
				 float p = 255*(float)Math.random();
				 Random rndm = new Random();
				 float temp = (float)(rndm.nextGaussian()*50)+150;
				 int p_1 = Math.round(p);
				 int p_2 = Math.round(temp);
				 ip.set(u,v,p_1);
				 ip_1.set(u,v,p_2);
				 }
			 }
		 im.updateAndDraw();
		 im_1.updateAndDraw();
		 im.show();
		 im_1.show();
    }
}
