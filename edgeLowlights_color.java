import ij.IJ;
import ij.ImagePlus;
import ij.plugin.filter.Convolver;
import ij.plugin.filter.PlugInFilter;
import ij.process.ColorProcessor;
import ij.process.ImageProcessor;

public class edgeLowlights_color implements PlugInFilter
{
	ImagePlus im = IJ.getImage();
	
	public int setup(String arg, ImagePlus im)
	{
		return DOES_RGB; //accepts 24-bit RGB Images
	}
	
	public void run(ImageProcessor ip)
	{
		
		ColorProcessor cp = ip.convertToColorProcessor();
		
		int h = ip.getHeight();
		int w = ip.getWidth();
		
		
		Convolver c = new Convolver();
		float kern[] ={-1,0,1,
				       -1,0,1,
				       -1,0,1};//going with the Prewit Gradient for this application
		
		ImagePlus Gradient = IJ.createImage("Gradient", w, h, 1, 8);
		
		ImageProcessor Grad_ip = Gradient.getProcessor();
		
		for(int u=0;u<w;u++)
		{
			for(int v=0;v<h;v++)
			{
				int rgb_array[] = {0,0,0};
				
				cp.getPixel(u, v, rgb_array);
				
				int p = (rgb_array[0]+rgb_array[1]+rgb_array[2])/3;
				
				Grad_ip.set(u,v, p);
				
			}
		}
		
		c.convolve(Grad_ip, kern, 3, 3);
		
		for(int u = 0;u<w;u++)
		{
			for(int v = 0;v<h;v++)
			{
				int rgb_array[] = {0,0,0};
				cp.getPixel(u, v, rgb_array);
				
				double p = .7*(255-Grad_ip.getPixel(u, v));
				
				
				rgb_array[0]= (rgb_array[0]+(int)p)/2;
				if(rgb_array[0]>255) rgb_array[0]=0;
				
				rgb_array[1]= (rgb_array[1]+(int)p)/2;
				if(rgb_array[1]>255) rgb_array[1]=0;
				
				rgb_array[2]= (rgb_array[2]+(int)p)/2;
				if(rgb_array[2]>255) rgb_array[2]=0;
				
				ip.putPixel(u, v, rgb_array);
				
			}
		}
		
		im.updateAndDraw();
		
	}
}