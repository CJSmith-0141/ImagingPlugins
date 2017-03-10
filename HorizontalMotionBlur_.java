import ij.IJ;
import ij.ImagePlus;
import ij.plugin.filter.Convolver;
import ij.plugin.filter.PlugInFilter;
import ij.process.ImageProcessor;


public class HorizontalMotionBlur_ implements PlugInFilter
{
	ImagePlus im = IJ.getImage();
	
	public int setup(String arg, ImagePlus im)
	{
		return DOES_ALL; //accepts 8-bits gray scale
	}
	
	public void run(ImageProcessor ip)
	{
		
		

		float kern[] = {1,30,30,50,100,155,200};
		
		Convolver c = new Convolver();
		
		c.convolve(ip, kern, 7, 1);
		
		new ImagePlus("horizontal motion blured image",ip).show();
		
		

	}
	
}