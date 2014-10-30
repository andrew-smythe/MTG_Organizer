package mtg_organizer;

public interface SimpleShape {
	public void setLocation (int posX, int posY);

	public void setSize (int width, int height);
		
	public void changeLocation (int stepX, int stepY);	
		
	//method to paint the shape
	public void paint(java.awt.Graphics2D brush);	
	
	public int getX();
	public int getY();
	
	public int getWidth();
	public int getHeight();
	
	public int getCenterX();
	public int getCenterY();
	
	public void setRotation(double rotation);
	public double getRotation();

}
