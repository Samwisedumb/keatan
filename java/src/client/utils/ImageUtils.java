package client.utils;

import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class ImageUtils
{
	
	public static BufferedImage DEFAULT_IMAGE = new BufferedImage(1,
																  1,
																  BufferedImage.TYPE_INT_ARGB);
	
	public static BufferedImage loadImage(String file)
	{
		try
		{
			return ImageIO.read(new File(file));
		}
		catch(IOException e)
		{
			assert false;
		}
		
		return DEFAULT_IMAGE;
	}
	
	public static BufferedImage resizeImage(BufferedImage original, int newWidth, int newHeight) {
		
		BufferedImage resized = new BufferedImage(newWidth, newHeight, original.getType());
	    Graphics2D g = resized.createGraphics();
	    g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
	    g.drawImage(original, 0, 0, newWidth, newHeight, 0, 0, original.getWidth(), original.getHeight(), null);
	    g.dispose();
	    return resized;
	}
	
}

