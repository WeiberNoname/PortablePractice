package etractor;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.MouseInfo;
import java.awt.Point;
import java.io.File;
import java.util.ArrayList;


public class KernelSet1{
	
	private int width = 25;
	private ArrayList<Point> pointLocation = new ArrayList<Point>();
	
	public KernelSet1() {
		
	}
	
	public int setKernelBasic(String command) {
		int i = 0;
		try {
			i = Integer.parseInt(command);
		}catch(NumberFormatException e) {
			
		}
		return i;
	}
	
	public void drawLine(Graphics g,int size,int deviationX,int deviationY) {
		g.setColor(new Color(20,100,20));
		//x start from 50 and 100
		int o = width*(size);
		for(int i = 0 ;i<size+1;i++) {
			g.drawLine(50+deviationX,100+i*width+deviationY,50+o+deviationX,100+i*width+deviationY);
			}
		for(int i = 0 ;i<size+1;i++) {
			g.drawLine(50+i*width+deviationX,100+deviationY,50+i*width+deviationX,100+o+deviationY);
		}
	}
	
	public Point drawSquare(Graphics g,int mouseX,int mouseY,int size,int deviationX,int deviationY) {
		
		g.setColor(new Color(20,100,20));
		//x start from 50 and 100
		int locationX = 0,locationY = 0;
		for(int i = 0 ; i < size;i++) {
			for(int o = 0 ; o < size;o++) {
			int x = 50 + o*width+deviationX;
			int x1 = 50 + o*width+width+deviationX;
			int y = 100 + i*width+deviationY;
			int y1 = 100 + i*width+width+deviationY;
			if(mouseX > x&&mouseX < x1&&mouseY>y&&mouseY < y1) {
				g.fillRect(x,y,width,width);
				locationX = o+1; 
				locationY = i+1;
			}
		}
		}
		return new Point(locationX,locationY);
	}
	
	//add kernel value location
	public void addPointLocation(Point p) {
		if(!pointLocation.contains(p)) {
			pointLocation.add(p);
		}
	}
	
	public void drawPointLocation(Graphics g,int deviationX,int deviationY) {
		int size=pointLocation.size();
		g.setColor(new Color(100,10,10));
		for(int i = 0 ; i <size ;i++) {
			Point p = pointLocation.get(i);
			int x = 50 +(p.x-1) *width+deviationX;
			int y = 100 + (p.y-1)*width+deviationY;
			g.fillRect(x,y,width,width);
		}
	}
	
	public void cleanPointLocation() {
		pointLocation.clear();
	}
	
	
	
}
