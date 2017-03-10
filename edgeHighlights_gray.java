import ij.IJ;
import ij.ImagePlus;
import ij.plugin.filter.PlugInFilter;
import ij.process.ImageProcessor;
import ij.plugin.filter.Convolver;



public class edgeHighlights_gray implements PlugInFilter
{
	ImagePlus im = IJ.getImage();
	
	public int setup(String arg, ImagePlus im)
	{
		return DOES_8G; //accepts 8-bits gray scale
	}
	
	public void run(ImageProcessor ip)
	{
		
		int h = ip.getHeight();
		int w = ip.getWidth();
		
		
		Convolver c = new Convolver();
		float kern[] ={-1,0,1,
				       -1,0,1,
				       -1,0,1};//going with the Prewit Gradient for this application
		
		ImagePlus Gradient = IJ.createImage("Gradient", w, h, 1, 8);
		
		ImageProcessor Grad_ip = Gradient.getProcessor();
		
		Grad_ip = ip.duplicate();
		
		c.convolve(Grad_ip, kern, 3, 3);
		
		for(int u = 0;u<w;u++)
		{
			for(int v = 0;v<h;v++)
			{
				int p = ip.getPixel(u, v);
				int newP = (p+(3*Grad_ip.getPixel(u, v)))/2;
				if(newP>255) newP = 255;
				
				ip.set(u,v,newP);
				
			}
		}
		
		im.updateAndDraw();
		
	}
}