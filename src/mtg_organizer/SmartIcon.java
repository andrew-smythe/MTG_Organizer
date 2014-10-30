package mtg_organizer;

import java.awt.*;
import java.awt.image.BufferedImage;

public class SmartIcon implements SimpleShape{
	BufferedImage image;
	private int posX, posY;
	private int width, height;
	private double rotation;
	
	public SmartIcon(BufferedImage img)
	{		
	    this.image = img;
	}
	
	public int getX()
	{
		return this.posX;
	}
	public int getY()
	{
		return this.posY;
	}
	
	public int getWidth()
	{
		return this.width;
	}
	public int getHeight()
	{
		return this.height;
	}
	
	public void setLocation (int posX, int posY)
	{
		this.posX=posX;
		this.posY=posY;
	}
	
	public void setSize (int width, int height)
	{
		this.width=width;
		this.height=height;
	}
		
	public void changeLocation (int stepX, int stepY)
	{
		this.posX+=stepX;
		this.posY+=stepY;
	}
	
		
	public void paint(java.awt.Graphics2D brush)
	{
		if(this.rotation!=0)
		{
			brush.rotate(this.rotation, this.getCenterX(), this.getCenterY());
		}
		brush.drawImage(this.image,null,
                posX, posY);
		Toolkit.getDefaultToolkit().sync();
		if(this.rotation!=0)
		{
			brush.rotate(-this.rotation, this.getCenterX(), this.getCenterY());
		}
	}
	
	public int getCenterX()
	{
		return this.posX+this.width/2;
	}
	
	public int getCenterY()
	{
		return this.posY+this.height/2;
	}
	public void setRotation(double rotation)
	{
		this.rotation=rotation;
	}
	
	public double getRotation()
	{
		return this.rotation;
	}
}
