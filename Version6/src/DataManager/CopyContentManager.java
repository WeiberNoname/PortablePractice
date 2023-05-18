package DataManager;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.ArrayList;

public class CopyContentManager extends ExtendsInteraction1{
	private int startPointX = 0,startPointY = 0;
	private int deviationX = 0,deviationY = 0;
	private int X = 0,Y = 0,maxX = 0,maxY = 0;
	private int iDeviation = 0, size;
	
public CopyContentManager() {
	
}


public void paintBufferFileManager(Graphics g,int mouse,int mouseY,String type) {
	int width = paintPanel.getWidth();
	int height = paintPanel.getHeight();
	//end point
	deviationX = (width-200);
	deviationY = (height-200);
	
	X = mouseX; 
	Y = mouseY;
	startPointX = 100;
	startPointY = 100;
	
	maxX = startPointX + deviationX;
	maxY = startPointY + deviationY;
	
	g.setFont(new Font("one",Font.CENTER_BASELINE,16));
	g.setColor(new Color(200,200,200));
	g.drawRect(startPointX, startPointY, deviationX, deviationY);
	
	g.setColor(new Color(155,155,155));
	g.fillRect(startPointX+3,startPointY+3,deviationX-6,deviationY-6);
	
	g.setColor(new Color(155,155,155));
	g.fillRect(0,0,width,20);
	g.fillRect(0,height-20,width,20);
	
	paintList(g,type);
}
public void paintList(Graphics g,String type) {
	
	
	
	
	if(type.indexOf("File")>-1) {
		g.setColor(new Color(10,120,10));
		size = fileList.size();
		
		iDeviationCalculation();
		for(int i =0 ; i < size; i++) {
			int sY = startPointY+20+i*19+iDeviation;
			if(sY < maxY&&sY>startPointY+19) {
			if(Y<sY&&Y>startPointY+20+(i-1)*19+iDeviation) {
				g.setColor(new Color(25,170,25));
			}else {
				g.setColor(new Color(10,120,10));
			}
			g.drawString(fileList.get(i).toString(),startPointX+10,sY);
			}
		}
	}else if(type.indexOf("directory")>-1){
		g.setColor(new Color(150,90,10));
		size = directoryList.size();

		iDeviationCalculation();
		for(int i =0 ; i < size; i++) {
			int sY = startPointY+20+i*19+iDeviation;
			if(sY < maxY&&sY>startPointY+19) {
			if(Y<sY&&Y>startPointY+20+(i-1)*19+iDeviation) {
				g.setColor(new Color(190,130,30));
			}else {
				g.setColor(new Color(150,90,10));
			}
			g.drawString(directoryList.get(i).toString(),startPointX+10,sY);
			}
		}
	}
	
}
private void iDeviationCalculation() {
	int limitPY = size * 25,limitNY = (-1)*(size * 25);
	if(Y < startPointY-80&&iDeviation > limitNY) {
		iDeviation -= 2;
	}else if(Y >  startPointY+deviationY+80&&iDeviation < limitPY) {
		iDeviation += 2;
	}
}

}
