import ij.IJ;
import ij.ImagePlus;
import ij.gui.HistogramWindow;
import ij.io.OpenDialog;
import ij.plugin.ContrastEnhancer;
import ij.plugin.filter.PlugInFilter;
import ij.process.ImageProcessor;
import inra.ijpb.morphology.Strel;

import java.lang.Math;





public class detectMoving_ implements PlugInFilter
{
	ImagePlus im = IJ.getImage();
	
	OpenDialog G = new OpenDialog("Open Second Image in sequence");
	//OpenDialog g_2 = new OpenDialog("Open Third Image in sequence");
	
	ImagePlus im_2 = IJ.openImage(G.getPath());
	//ImagePlus im_3 = IJ.openImage(g_2.getPath());
	
	ImageProcessor ip = im.getProcessor();
	ImageProcessor ip_2 = im_2.getProcessor();
	//ImageProcessor ip_3 = im_3.getProcessor();
	
	
	public int setup(String arg, ImagePlus im)
	{
		return DOES_8G; //accepts 8-bits gray scale
	}
	
	public void run(ImageProcessor ip)
	{
		im_2.show();
		
		if( (ip.getWidth()!=ip_2.getWidth()) || (ip.getHeight()!=ip_2.getHeight()) )
		{
			IJ.error("THE IMAGES NEED TO BE THE SAME SIZE");
		}
		
		int h = ip.getHeight();
		int w = ip.getWidth();
		
		ImagePlus absDifference = IJ.createImage("Difference", w, h, 1, 8);
		absDifference.show();
		
		ImageProcessor absDiff = absDifference.getProcessor();
		
		for(int u = 0;u<w;u++)
		{
			for(int v = 0;v<h;v++)
			{
				//absDiff.set(u, v, Math.abs(Math.abs(ip.getPixel(u, v)-ip_2.getPixel(u,v))-Math.abs(ip_2.getPixel(u, v)-ip_3.getPixel(u,v))));
				absDiff.set(u, v, Math.abs(ip.getPixel(u, v)-ip_2.getPixel(u,v)));
			}
		}
		
		int DifferenceHistogram[] = absDiff.getHistogram();
		
		absDifference.updateAndDraw();
		
		HistogramWindow Hist = new HistogramWindow(absDifference);
		
		Hist.showHistogram(absDifference, 256);
		
		ContrastEnhancer CE = new ContrastEnhancer();
		
		ImagePlus stretchedAbsDiff = IJ.createImage("stretchedHistImage", w, h, 1, 8);
		
		stretchedAbsDiff.setProcessor(absDiff.duplicate());
		stretchedAbsDiff.updateImage();
		stretchedAbsDiff.show();
		//ImageProcessor stretchedAbsDiff_ip = stretchedAbsDiff.getProcessor(); 
		
		CE.stretchHistogram(stretchedAbsDiff, 1);
		
		stretchedAbsDiff.updateAndDraw();
		
		double percent = IJ.getNumber("Enter percent of pixels to set to white", 0);
		
		percent = 1-(percent/100);
		
		int threshold =0;
		int percentPixels = (int)(w*h*percent);
		int counter =0;
		
		
		
		for(int i =0;i<256;i++)
		{
			counter = counter + DifferenceHistogram[i];
			if(counter<percentPixels)
			{
				threshold = i;
			}
		}
		
		ImagePlus Binary = IJ.createImage("Binary Mask", w, h, 1, 8);
		Binary.show();
		ImageProcessor absDiffThresh = Binary.getProcessor();
		
		absDiffThresh = absDiff.duplicate();
		
		
		for(int u = 0;u<w;u++)
		{
			for(int v = 0;v<h;v++)
			{
				if(absDiff.getPixel(u, v)<=threshold) absDiffThresh.putPixel(u, v, 0);
				else absDiffThresh.putPixel(u, v, 255);
			}
		}
		
		Binary.setProcessor(absDiffThresh);
		Binary.updateAndDraw();
		
		
		Strel strel = Strel.Shape.SQUARE.fromRadius(2);
		ImageProcessor closed_ip = strel.closing(absDiff);
		
		new ImagePlus("Gray-level close",closed_ip).show();
		
		ImageProcessor betterBinary = closed_ip.duplicate();
		
		percent = IJ.getNumber("Enter percent of pixels to set to white for the better mask", 0);
		
		percent = 1-(percent/100);
		
		threshold =0;
		percentPixels = (int)(w*h*percent);
		counter =0;
		
		DifferenceHistogram = betterBinary.getHistogram();
		
		for(int i =0;i<256;i++)
		{
			counter = counter + DifferenceHistogram[i];
			if(counter<percentPixels)
			{
				threshold = i;
			}
		}
		
		for(int u = 0;u<w;u++)
		{
			for(int v = 0;v<h;v++)
			{
				if(closed_ip.getPixel(u, v)<=threshold) betterBinary.putPixel(u, v, 0);
				else betterBinary.putPixel(u, v, 255);
			}
		}
		
		new ImagePlus("Better binary Mask", betterBinary).show();
		
		ImageProcessor movingPixels_ip = betterBinary.duplicate();
		
		
		for(int u = 0;u<w;u++)
		{
			for(int v = 0;v<h;v++)
			{
				if(betterBinary.getPixel(u, v)==255)movingPixels_ip.set(u, v, ip_2.getPixel(u, v));
				else movingPixels_ip.set(u,v,0);
			}
		}
		
		new ImagePlus("moving pixels",movingPixels_ip).show();
		
		
		
		
		
		
		im.updateAndDraw();
		
	}
}