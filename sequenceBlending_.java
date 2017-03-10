import ij.IJ;
import ij.ImagePlus;
import ij.plugin.filter.PlugInFilter;
import ij.process.ImageProcessor;


public class sequenceBlending_ implements PlugInFilter
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
		
		int imageNumber = (int) Math.round(IJ.getNumber("How many images would you like to blend? accepts integers", 0));
		
		double alpha = IJ.getNumber("alpha value (0...1)", 1);
	
			for(int i=0;i<imageNumber;i++)
			{
				ImagePlus blender = IJ.openImage();
				ImageProcessor blender_ip = blender.getProcessor();
				
				
				for(int u = 0;u<w;u++)
				{
					for(int v = 0;v<h;v++)
					{
						
						int p = (int)(ip.getPixel(u, v)*alpha + (1-alpha)*blender_ip.getPixel(u, v));
						ip.set(u, v, p);
						
					}
				}
			}
			 new ImagePlus("new image",ip).show();
			

	}
	
}