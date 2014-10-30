package mtg_organizer;

import java.util.*;
import javax.swing.*;

public class SimpleShapeContainer extends JPanel {
	
	private  List <SimpleShape> shapes=new LinkedList<SimpleShape>();
	
	public SimpleShapeContainer ()
	{
		super();		
	}
	
	public void addShape(SimpleShape shape)
	{
		shapes.add(shape);
	}
	
	public void paintComponent (java.awt.Graphics aBrush )
	{
		super.paintComponent(aBrush);
		java.awt.Graphics2D aBetterBrush=(java.awt.Graphics2D)aBrush;
		for(SimpleShape s:shapes)
		{
			s.paint(aBetterBrush);
		}
	}
}
	