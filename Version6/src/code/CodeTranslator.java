package code;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

import paint.CloseCommand;

public class CodeTranslator extends JPanel{
	public BufferedImage canvas;
	 
    public Graphics2D g2d,g2d1;
    public Graphics2D g2,g21;
    public Graphics g1;
    
    public Color color = Color.WHITE,colorSeparate = Color.green,rgb = Color.green;
    
    public static String stringCommand = "";
    public static String string = "",dot = "",character = "{}[]:;1234567890*+-/@#$%^&*()_=!~`x.?><.,\"\"\'\'\\|\\/";
    public static int stringListRow = 1,changeRow = 1;
    public static int stringCursor = 1;
    private int commandCursor = 0;
    public static int maxLine = 1;
    private int deviationX = 0,deviationY = 0;
    
    private Font font = new Font("font",Font.CENTER_BASELINE,15);
    
    private int font_1011 = 0;
    private int startFrom = 1 ,endFrom = 1 ;
    //delete
    private boolean deleteTF = false,shiftTF = false;
    //copyFunction
    public static int mouseX ,mouseY;
    public static boolean cursorSetTF = false,editable = false;
    public boolean drawLineTF = false,drawGridTF = true,focusKernel = true,coordinate = false;
    private CloseCommand CC = new CloseCommand();
    
    public CodeTranslator() {
    	setBackground(Color.WHITE);
        addMouseMotionListener(new MouseMotionListener() {
			public void mouseDragged(MouseEvent e) {
			}
			public void mouseMoved(MouseEvent e) {
				mouseX = e.getX();
				mouseY = e.getY();
			}
        });
        addMouseListener(new MouseAdapter() {
        	public void mouseClicked(MouseEvent e) {
        		requestFocusInWindow();
        		stringListRow = changeRow;
        		triggerCursorSet();
        		changeLine(e);
        	}
        	public void mousePressed(MouseEvent e) {
        	}
        	public void mouseReleased(MouseEvent e) {
        	}
        });
        addKeyListener(new KeyAdapter() {
        	public void keyPressed(KeyEvent e) {
        		keyFunction1(e);
			}
        });
    }
    private void keyFunction1(KeyEvent e){
    	int keyCode = e.getKeyCode();
		if(e.isControlDown()) {
		if(keyCode == KeyEvent.VK_DOWN) {
			deviationY -= font_1011;
		}else if(keyCode== KeyEvent.VK_UP) {
			deviationY += font_1011;
		}else if(keyCode== KeyEvent.VK_LEFT) {
			deviationX += font_1011;
		}else if(keyCode== KeyEvent.VK_RIGHT) {
			deviationX -= font_1011;
		}else if(keyCode ==KeyEvent.VK_Q) {
			CC.removeComponent("translate");
		}else if(keyCode == KeyEvent.VK_D&&deleteTF == false) {
    			deleteTF = true;
    	}else if(keyCode == KeyEvent.VK_T) {
    		stringListRow = maxLine;
    	}
		}else {
			char c = e.getKeyChar();
			if(stringListRow != maxLine) {
			actionCommandCode(keyCode,c);
			}else {
			actionCommand(keyCode,c);
			}
		}
    }
    
    private void changeLine(MouseEvent e) {
    	int height = getHeight()-15;
		int width = getWidth();
		int mX = e.getX();
		int mY = e.getY();
		int dotLength = dot.length();
		if(mY>height) {
			int p1 = mX*100 / width;
			if(p1 > 100) {
				p1 =100;
			}
			int cursor = dotLength * p1/100;
			int row = ((dot.substring(0,cursor)).split("\n")).length;
			stringListRow = row-1;
			stringCursor = cursor;
		}
    }
    
    //keyListener repository
    private void actionCommandCode( int type,char c) {
    	switch(type) {
    	case KeyEvent.VK_UP:
    		if(stringListRow > 0) {
    			stringListRow -= 1;
    			triggerCursorSet();
    		}
    		break;
    	case KeyEvent.VK_DOWN:
    		if(stringListRow < maxLine) {
    			stringListRow += 1;
    			triggerCursorSet();
    		}
    		break;
    	case KeyEvent.VK_LEFT:
    		if(stringCursor > 1) {
    			stringCursor -= 1;
    		}
    		break;
    	case KeyEvent.VK_RIGHT:
    		if(stringCursor <= dot.length()) {
    			stringCursor+=1;
    		}
    		break;
    	case KeyEvent.VK_ENTER:
    		if(editable) {
    		maxLine+=1;
    		if(stringCursor > 0&&stringCursor <= dot.length()) {
    		dot = dot.substring(0,stringCursor-1)+"\n"+dot.substring(stringCursor-1);
    		stringListRow +=1;
    		stringCursor+=1;
    		}else {
    		dot +="\n[EndLine]";
    		stringListRow +=1;
    		stringCursor +=1;
    		}
    		}
    		break;
    	case KeyEvent.VK_BACK_SPACE:
    		if(editable) {
    		if(stringCursor >1 && stringCursor <= dot.length()) {
    			dot = dot.substring(0,stringCursor-2)+dot.substring(stringCursor-1);
	    		stringCursor -=1;
    		}else if(dot.length() > 0 ) {
    			dot = dot.substring(0,dot.length()-1);
    			stringCursor -=1;
    		}
    		}
    		break;
    	case KeyEvent.VK_UNDEFINED:break;
    		default:
    			if(editable) {
    			if(character.indexOf(c)>-1||Character.isLetterOrDigit(c)||c == KeyEvent.VK_SPACE) {
    				if(stringCursor > 0&&stringCursor <= dot.length()) {
    		    		dot = dot.substring(0,stringCursor-1)+c+dot.substring(stringCursor-1);
    		    		stringCursor +=1;
    		    		}else {
    		    		dot +=c;
    		    		stringCursor +=1;
    		    		}
    			}
    			}
    	
    			break;
    	}
    }
    private void actionCommand( int type,char c) {
    	switch(type) {
    	case KeyEvent.VK_UP:
    		if(stringListRow > 0) {
    			stringListRow -= 1;
    			triggerCursorSet();
    		}
    		break;
    	case KeyEvent.VK_DOWN:
    		if(stringListRow < maxLine) {
    			stringListRow += 1;
    			triggerCursorSet();
    		}
    		break;
    	case KeyEvent.VK_LEFT:
    		if(commandCursor > 0) {
    			commandCursor -= 1;
    		}
    		break;
    	case KeyEvent.VK_RIGHT:
    		if(commandCursor < stringCommand.length()) {
    			commandCursor += 1; 
    		}
    		break;
    	case KeyEvent.VK_ENTER:
    		commandCenter(stringCommand);
    		break;
    	case KeyEvent.VK_BACK_SPACE:
    		if(stringCommand.length()>0&&commandCursor > 0) {
    			if(commandCursor < stringCommand.length()) {
    	    		stringCommand = stringCommand.substring(0,commandCursor-1)+stringCommand.substring(commandCursor);
    	    		}else {
    	    		stringCommand = stringCommand.substring(0,stringCommand.length()-1);
    	    		}
    			commandCursor -=1;
    		}
    		break;
    	case KeyEvent.VK_SPACE:
    		if(commandCursor < stringCommand.length()) {
    		stringCommand = stringCommand.substring(0,commandCursor)+" "+stringCommand.substring(commandCursor);
    		}else {
    		stringCommand += " ";
    		}
    		commandCursor+=1;
    		break;
    	case KeyEvent.VK_UNDEFINED:break;
    		default:
    			if(character.indexOf(c)>-1||Character.isLetterOrDigit(c)) {
    				if(commandCursor < stringCommand.length()) {
    		    		stringCommand = stringCommand.substring(0,commandCursor)+c+stringCommand.substring(commandCursor);
    		    		}else {
    		    		stringCommand += c;
    		    		}
    				commandCursor+=1;
    			}
    			break;
    	}
    }
    
    //set command into arrayList
    
    
    public void focusKernelSet() {
    	if(focusKernel) {
    		focusKernel = false;
    	}else{
    		focusKernel = true;
    	}
    }
    public void renew(int i) {
    	canvas = null;
    }

	public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(new Color(0,0,0));
        g.fillRect(0,0,getWidth(),getHeight());
        paintStart(g);
        repaint();
    }
	
    //two method that provide for user dragging skill
    private void paintStart(Graphics g) {
        if (canvas == null) {
            canvas = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_ARGB);
            g2d = (Graphics2D) canvas.getGraphics();
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            g2d.setColor(Color.BLACK);
            g2d.fillRect(0,0,getWidth(), getHeight());
            g2d.setColor(Color.gray);
            g2d.drawRect(0,0,getWidth(), getHeight());
            g2d.setColor(colorSeparate);
        }

        g.drawImage(canvas, 0,0, null);

        //scale
        g.setColor(Color.BLACK);
        g.fillRect(0, 0,getWidth(), getHeight());
        g.setColor(new Color(20,20,20));
        g.drawRect(0,0,getWidth(), getHeight());
        //reconstruct first
        string = reconstruct(dot);
        paintCode(g,dot,string,deviationX,deviationY);
        
        repaint();
    }
    
    private void triggerCursorSet() {
    	cursorSetTF = true;
    }
    
    private void paintCode(Graphics g,String str,String str1,int deviationX,int deviationY) {
    	try {
    	
    	g.setFont(font);
    	FontMetrics metrics = g.getFontMetrics(font);
    	int length = 0,fontHeight = metrics.getHeight();
    	int x = 10+deviationX,y = 20+deviationY,i = 0,row = 0,total = 0,lastTotal = 0;
    	int width = getWidth(), over = 0;
    	int startFrom = 0, end = 0,totalSize = str.length();
    	int height1 = getHeight()-fontHeight-5;
    	font_1011 = fontHeight;
    	if(string.length() == 0) {
			dot = dot+"\n[EndLine]";
		}
    	//check
    	editable=false;
    	for(String line: str.split("\n")) {
    		length = line.length();
    		over += length+1;
    		total += length+1;
    		int forStringDraw = y+fontHeight*i;
    		int uY =y+fontHeight*(i-1)+5;
    		int stopY = forStringDraw+fontHeight,stopX =height1+fontHeight-5;
    		
    		if(uY < getHeight()-30 ) {
    			
    		if(stringListRow == row) {
    			editable=true;
    			if(stopX>stopY) {
    			g.setColor(new Color(10,120,10,50));
	    		g.fillRect(0,uY,width, fontHeight);
	    		g.setColor(new Color(10,130,10,55));
	    		g.drawRect(0,uY,width, fontHeight);
    			}
	    		if(cursorSetTF) {
	    			if( stringCursor > total) {
		    			cursorSetTF = false;
		    			stringCursor = lastTotal + 1;
		    		}else if(stringCursor <  lastTotal||stringCursor <  lastTotal+1) {
		    			cursorSetTF = false;
		    			stringCursor = lastTotal + 1;
		    		}
	    			
	    		}
	    		if(str1.length()>0) {
	    		startFrom = lastTotal;
    			end = total*100/totalSize;
    			if(end > 100) {
    				end = 100;
    			}
	    		}
	    		if(stringCursor > total) {
	    			stringListRow += 1; 
	    		}else if(stringCursor < lastTotal+1){
	    			stringListRow -= 1;
	    		}
	    		//cursor
	    			g.setColor(Color.green);
	    			if(stopX > stopY) {
	    			int lengthOfLine = stringCursor - lastTotal-1;
	    			int lineLength = line.length();
	    			if(lineLength >0&&lengthOfLine>=0&&lengthOfLine<=lineLength) {
	    				int wordSize = metrics.stringWidth(line.substring(0,lengthOfLine));
	    				g.fillRect(x+wordSize,uY+1, 3, fontHeight-2);
	    			}else {
	    				g.fillRect(x,uY+1, 3, fontHeight-2);
	    			}
	    			}
	    			if(deleteTF) {
	    				deleteTF = false;
	    				int deleteNum = line.length();
	    				dot = dot.substring(0,lastTotal)+dot.substring(total);
	    			}
    		}
    		//End of a check
    		
    		if(mouseX <= width&&mouseX >= 0 && mouseY >= uY&&mouseY <= (uY+fontHeight)) {
    			g.setColor(new Color(110,110,110,60));
	    		g.fillRect(0,uY,width, fontHeight);
	    		g.drawRect(0,uY,width, fontHeight);
	    		changeRow = row;
    		}
    		
    		if(length >0&&stopY < stopX)  {
    				g.setColor(new Color(190,190,170));
    				
    				
    				g.drawString(str1.substring(0,length),x,forStringDraw);
    				str1 = str1.substring(length);
    		}
    		}
    		lastTotal += length+1;
    		row += 1;
    		i+=1;
    	}
    	
    	//must 1
    	maxLine = i+1;
    	int panelWidth = getWidth();
    	if(stringListRow == maxLine) {
    		g.setColor(new Color(190,190,190,200));
    		g.fillRect(0,height1,width,fontHeight);	
    		g.setColor(new Color(130,130,130,230));
    		g.drawRect(0,height1,width,fontHeight);
    		g.setColor(new Color(10,10,10,200));
    		int commandLength = 0;
    		if(commandCursor == stringCommand.length()) {
    			commandLength = metrics.stringWidth(stringCommand)+3;	
    		}else {
    		String bufferString = stringCommand.substring(0,commandCursor);
    		commandLength = metrics.stringWidth(bufferString)+3;
    		}
    		g.fillRect(commandLength, height1+1 , 3, fontHeight-2);
    		g.setColor(new Color(0,0,0));
    		g.drawString(stringCommand,3,height1+fontHeight-5);
    	}else {
    		g.setColor(new Color(190,190,170));	
    		Font font1 = new Font("font",Font.CENTER_BASELINE,15);
    		g.setFont(font1);
    		g.drawString("Line : "+ stringListRow+" | "+"Code Cursor : "+stringCursor+" | "+"Total from : "+lastTotal+" | "+"Percentage : "+end+"%",3,height1+fontHeight-5);
    		int p = panelWidth*end/100;
    		g.setColor(new Color(10,150,10,200));	
    		g.fillRect(0,height1+fontHeight-2,p,5);
    	 
    	}
    	if(stringCursor < 0) {
    		stringCursor = 0;
    	}
    	if(stringListRow < 0) {
    		stringListRow = 0;
    	}
    	if(commandCursor < 0) {
    		commandCursor = 0;
    	}
    	}catch(StringIndexOutOfBoundsException e) {
    	}
    }
    
    private String reconstruct(String str) {
    	String result = "";
    	for(String line: str.split("\n")) {
    		result += line; 
    	}
    	return result;
    }
    private void commandCenter(String command) {
    	if(command.indexOf("new")>-1) {
    		string += "[EndLine]";
    		dot+= "\n[EndLine]";
    	}else if(command.indexOf("restart")>-1){
    		string = "";
    		dot = "";
    	}else if(command.indexOf("line ")>-1&&command.length()>5) {
    		command = command.substring(5);
    		if(command.length()>0) {
    			try {
    			int line = Integer.parseInt(command);
    			if(line < maxLine-1 && line > -1) {
    				triggerCursorSet();
    				stringListRow = line;
    				
    			}
    			}catch(NumberFormatException e) {
    			}
    		}
    	}
    	stringCommand = "";
    	commandCursor = 0;
    }	    	
}
