package Test;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.*;
import javax.swing.*;

public class DisplayText extends JPanel {
    private static final long serialVersionUID = 1L;
    private static String text = "",bufferString = "",character = "{}[]:;1234567890*+-/@#$%^&*()_=!~`x.?><.,\"\"\'\'\\|\\/";
    private static JFrame frame = new JFrame("Display Text");
    private static int basicFontSize = 15 ;
    private static int positionLineX = 1,positionLineY = 1,maxPosition = 1, maxLine = 1,lineNumberY = 1,lineNumberX = 1;
    
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        g.setColor(new Color(0,0,0));
        g.fillRect(0,0,frame.getWidth(),frame.getHeight());
        
        Font font = new Font("Arial", Font.CENTER_BASELINE, basicFontSize);
        FontMetrics metrics = g.getFontMetrics(font);
        int metricsNum = metrics.getHeight(),frameWidth = frame.getWidth(),frameHeight = frame.getHeight();
        g.setFont(font);
        
        g.setColor(new Color(10,50,10));
        g.fillRect(0,(lineNumberY-1)*metricsNum,frameWidth,metricsNum);
        int x = 0, y = basicFontSize;
        for (String line : text.split("!")) {
        	x = 10;
            for (String word : line.split(" ")) {
            	int width = metrics.stringWidth(word + " ");
                
                if(word.indexOf("public")>-1) {
                	g.setColor(new Color(80,80,220));
                }else if(word.indexOf("pub")>-1){
                	g.setColor(new Color(100,200,160));
                }else {
                	g.setColor(new Color(200,200,160));
                }
                g.drawString(word + " ", x, y);
                x += width;
            }
           
            y += metricsNum;
        }
        g.setColor(Color.green);
        g.drawString("Line Number : "+lineNumberY+" "+"Line Position : "+lineNumberX,10,frameHeight-40);
        calculator(g);
        repaint();
    }

    private static void calculator(Graphics g) {
        Font font = new Font("Arial", Font.CENTER_BASELINE, basicFontSize);
        FontMetrics metrics = g.getFontMetrics(font);
        int metricsNum = metrics.getHeight(),metricsWidth = metrics.getHeight()/2-1;
        String content = "",front = "",back = "";
        maxLine = 0;
        int currentLineY = 0,currentLineX = 0,totalSize = 0;
        for (String line : text.split("!")) {
        		currentLineY +=1;
            	content = line;
            	int contentLength = content.length();
            	if(currentLineY == lineNumberY) {
            		if(lineNumberX > contentLength) {
            			lineNumberX = contentLength;
            		}
            		maxPosition = contentLength;
            		bufferString = content;
            		g.fillRect((lineNumberX+1) * metricsWidth, (lineNumberY-1) * metricsNum+1, 2, 15);
            	}
            	totalSize += line.length();
            	maxLine += 1;
        }
    }
    
    public static void main(String[] args) {
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setLocationRelativeTo(null);
        frame.add(new DisplayText());
        frame.setVisible(true);
        
        frame.getContentPane().setBackground(new Color(0,0,0));
        frame.addMouseListener(new MouseAdapter() {
        	public void mouseClicked(MouseEvent e) {
        		frame.requestFocusInWindow();
        	}
        });
        frame.addKeyListener(new KeyAdapter() {
        	public void keyPressed(KeyEvent e) {
        		int keyCode = e.getKeyCode();
        		char c = e.getKeyChar();
        		actionCommand(keyCode,c);
        	}
        });
    }
    
    private static void actionCommand( int type,char c) {
    	try {
    	switch(type) {
    	case KeyEvent.VK_UP:
    		if(lineNumberY > 1) {
    			lineNumberY -= 1;
    		}
    		break;
    	case KeyEvent.VK_DOWN:
    		if(lineNumberY < maxLine) {
    			lineNumberY += 1;
    		}
    		break;
    	case KeyEvent.VK_LEFT:
    		if(lineNumberX > 1) {
    			lineNumberX -= 1;
    		}
    		break;
    	case KeyEvent.VK_RIGHT:
    		if(lineNumberX < maxPosition) {
    			lineNumberX += 1;
    		}
    		break;
    	case KeyEvent.VK_ENTER:
    		text = divideAddFunction(lineNumberX,"!",text);
    		lineNumberY +=1;
    		break;
    	case KeyEvent.VK_BACK_SPACE:
    		break;
    	case KeyEvent.VK_ALPHANUMERIC:
			lineNumberX+=1;
    		break;
    	case KeyEvent.VK_SPACE:
    		text = divideAddFunction(lineNumberX," ",text);
			lineNumberX+=1;
    		break;
    	case KeyEvent.VK_UNDEFINED:break;
    		default:
    			if(character.indexOf(c)>-1||Character.isLetterOrDigit(c)) {
    				text = divideAddFunction(lineNumberX,String.valueOf(c),text);
        			lineNumberX+=1;
    	    	}
    			break;
    	}
    	
}catch(StringIndexOutOfBoundsException e) {
	text += c;
	lineNumberX+=1;
    	}
    }
    //star|t0-4+ ? + 4
    private static String divideAddFunction(int position,String c,String result) {
    	int length = result.length();
    	if(position == length) {
    		result += c ;
    	}else {
    		result = result.substring(0,position)+c+result.substring(position);
    	}
    return result;
    }
}
