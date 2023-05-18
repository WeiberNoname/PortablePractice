package paint;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.swing.JPanel;

public class DescriptionGuide extends JPanel{

	  public BufferedImage canvas;
	  
	  private ArrayList<String> introduction1 = new ArrayList<String>();
	  
	  
	    public Graphics2D g2d,g2d1;
	    public Graphics2D g2,g21;
	    public Graphics g1;
	    
	    public Color color = Color.WHITE,colorSeparate = Color.green,rgb = Color.green;
	    
	    
	    private int mouseX ,mouseY,deviation = 0;
	    public static String stringBuffer = "Search for words",clickedContent = "";
	    
	    public boolean drawLineTF = false,drawGridTF = true,focusKernel = true,coordinate = false;
	    private KeyCommon KC = new KeyCommon();
	    private Dictionary D = new Dictionary();
	    private AddAPI api = new AddAPI();
	    private CloseCommand CC = new CloseCommand();

	    public DescriptionGuide() {
	    	setBackground(Color.WHITE);
	        addMouseListener(new MouseAdapter() {
				public void mousePressed(MouseEvent e) {
						api.addComponent(clickedContent);
	            		requestFocusInWindow();
	            }
	        });
	        addMouseMotionListener(new MouseMotionListener() {
				public void mouseDragged(MouseEvent e) {
				}
				public void mouseMoved(MouseEvent e) {
					mouseX = e.getX();
					mouseY = e.getY();
				}
	        });
	        addKeyListener(new KeyAdapter() {
	        	public void keyPressed(KeyEvent e) {
	        		int keyCode = e.getKeyCode();
	        		if(keyCode == KeyEvent.VK_DOWN) {
	        			deviation += 80;
	        		}else if(keyCode== KeyEvent.VK_UP) {
	        			deviation -= 80;
	        		}
				}
				public void keyReleased(KeyEvent e) {
	        		stringBuffer  = KC.keyCheck(e, stringBuffer);
	        		int KeyCode = e.getKeyCode();
	        		char KeyChar = e.getKeyChar();
	        		if(e.isControlDown()&& KeyCode == KeyEvent.VK_N) {
	        			cleanDrawLine();
	        		}else if(e.isControlDown()&& KeyCode == KeyEvent.VK_Q) {
	        			CC.removeComponent("guide");
	        		}else if(KeyCode == KeyEvent.VK_ENTER) {
	        			stringBuffer = "";
	        		}
	        	}
	        });
	    }
	    public void image() {
	    }
	    
	    public void coordinateSet() {
	    		coordinate = true;
	    }
	    public void focusKernelSet() {
	    	if(focusKernel) {
	    		focusKernel = false;
	    	}else{
	    		focusKernel = true;
	    	}
	    }
	    
	    public void renew(int i) {
	    	canvas = null;
			stringBuffer = "";
	    }
	    public void cleanDrawLine() {
	    	canvas = null;
			paintStart(getGraphics());
			stringBuffer = "";
	    }

	    private void introduction(Graphics g) {
	    	Font font = new Font("one",Font.CENTER_BASELINE,16);
	    	g.setFont(font);
	    	int fontMetrics = 8;
	    	int width = getWidth()-100*2;
	    	int height = getHeight();
	    	introduction1 = D.dictionary( fontMetrics, width);
	    	int size = introduction1.size();
	    	for(int i = 0 ; i < size;i++) {
	    		int startY = 100+20*i+deviation;
	    		if(startY<=height&&startY >= 80) {
	    		if(mouseY <(100+(20*i)+deviation)&&mouseY > (100 + (20*(i-1))+deviation)) {
	    			g.setColor(new Color(0,130,0));
	    			clickedContent = introduction1.get(i);
	    		}else {
	    			g.setColor(new Color(0,200,0));
	    		}
	    		String content = introduction1.get(i);
	    		if(content.indexOf("!")>-1) {
	    			g.setColor(new Color(150,0,150));
	    		}else if(content.indexOf("[")>-1) {
	    			g.setColor(new Color(200,180,10));
	    		}
	    		if(content.indexOf(stringBuffer)>-1&&stringBuffer.length()>0) {
	    			g.setColor(new Color(130,10,10));
	    		}
	    		g.drawString(content,100,100+20*i+deviation);
	    		}else {
	    		}
	    	}
	    introduction1.clear();
	    D.clear();
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
	            g2d.setColor(Color.green);
	            g2d.drawRect(0,0,getWidth(), getHeight());
	            g2d.setColor(colorSeparate);
	        }


	        g.drawImage(canvas, 0,0, null);

	        //scale
	        g.setColor(Color.BLACK);
	        g.fillRect(0, 0,getWidth(), getHeight());
	        g.setColor(Color.green);
	        g.drawRect(0,0,getWidth(), getHeight());
	        //draw String
	        g.setColor(Color.green);
	        
	        g.setFont(new Font("one",Font.CENTER_BASELINE,15));
	        g.drawString(stringBuffer,50,50);
	        g.drawLine(50,60,getWidth()-50,60);
	        
	        introduction(g);
	        
	        repaint();
	    }
	    
    
}
