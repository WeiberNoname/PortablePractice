package etractor;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.swing.JPanel;

import paint.CloseCommand;
import DataManager.*;

public class ImageLaboratory1 extends JPanel{

	  public BufferedImage canvas;
	  
	    public Graphics2D g2d,g2d1;
	    public Graphics2D g2,g21;
	    public Graphics g1;
	    
	    public Color color = Color.WHITE,colorSeparate = Color.green,rgb = Color.green;
	    
	    private int mouseX = 0, mouseY;
	    
	    public static String stringBuffer = "ImageModel",clickedContent = "",stringCode = "";
	    
	    public boolean drawLineTF = false,bufferFile = false,drawGridTF = true,focusKernel = true,coordinate = false;
	    
	    private CloseCommand CC = new CloseCommand();
	    private Key1 k1 = new Key1();
	    private KernelSet1 KS = new KernelSet1();
	    private CopyContentManager CCM = new CopyContentManager();
	    private ImageDataExtractor IDE = new ImageDataExtractor();
	    private ExtendsInteraction1 EI1 = new ExtendsInteraction1();
	    private DataInteraction1 DI1 = new DataInteraction1();
	    
	    public int deviationX = 0, deviationY = 0;
	    
	    public ImageLaboratory1() {
	    	setBackground(Color.WHITE);
	        addMouseListener(new MouseAdapter() {
				public void mousePressed(MouseEvent e) {
	            	requestFocusInWindow();
	            }
				public void mouseClicked(MouseEvent e) {
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
	        			deviationY += 80;
	        		}else if(keyCode== KeyEvent.VK_UP) {
	        			deviationY -= 80;
	        		}else if(keyCode == KeyEvent.VK_RIGHT) {
	        			deviationX += 80;
	        		}else if(keyCode== KeyEvent.VK_LEFT) {
	        			deviationX -= 80;
	        		}
				}
				public void keyReleased(KeyEvent e) {
						keyListenerTerminal(e);
				}
	        });
	    }
	    
	    
	    private void keyListenerTerminal(KeyEvent e) {
	    //	stringBuffer  = KC.keyCheck(e, stringBuffer);
  		int KeyCode = e.getKeyCode();
  		char KeyChar = e.getKeyChar();
  		if(KeyCode == KeyEvent.VK_BACK_SPACE&&stringBuffer.length()>0) {
  			stringBuffer = stringBuffer.substring(0,stringBuffer.length()-1);
  		}
  		stringBuffer= k1.keyListener_Char(e, stringBuffer);
  		if(e.isControlDown()&& KeyCode == KeyEvent.VK_N) {
  			cleanDrawLine();
  		}else if(e.isControlDown()&& KeyCode == KeyEvent.VK_Q) {
  			CC.removeComponent("image");
  		}else if(KeyCode == KeyEvent.VK_ENTER) {
  			division(stringBuffer);
  			stringBuffer = "";
  		}
  		
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
	    
	    public void cleanDrawLine() {
	    	canvas = null;
			paintStart(getGraphics());
			stringBuffer = "";
			stringCode = "";
			clickedContent = "";
			deviationX = 0;
			deviationY = 0;
			KS.cleanPointLocation();
	    }

		public void paintComponent(Graphics g) {
	        super.paintComponent(g);
	        g.setColor(new Color(0,0,0));
	        g.fillRect(0,0,getWidth(),getHeight());
	        paintStart(g);
	        fileManagerBuffer(g);
	        repaint();
	    }
		private void fileManagerBuffer(Graphics g) {
			if(bufferFile) {
	        	CCM.paintBufferFileManager(g,mouseX,mouseY,"File");
			}
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
	        g.setFont(new Font("one",Font.CENTER_BASELINE,15));
	        g.drawString(stringBuffer,50,50);
	        g.drawLine(50,60,getWidth()-50,60);
	        //Code
	   
	        repaint();
	    }
	    
	    private void division(String command) {
	    	
	    	if(command.indexOf("open ")>-1&&command.length()>5) {
	    		command = command.substring(5);
	    		if(command.indexOf("buffer")>-1) {
	    			bufferSet(true);	
	    		}
	    	}else if(command.indexOf("off ")>-1&&command.length()>4) {
	    		command = command.substring(4);
	    		if(command.indexOf("buffer")>-1) {
	    			bufferSet(false);
	    		}
	    	}else if(command.indexOf("extract all")>-1) {
	    		try {
	    			if(DI1.connectionTest()) {
	    				IDE.imageExtractorBinary(EI1.getFileList());		
	    			}else {
	    				DI1.errorInfoAndSolution("connection");
	    			}
	    		}catch(IOException e) {
	    		}
	    	}else if(command.indexOf("decode")>-1) {
	    		try {
	    			if(DI1.connectionTest()) {
	    				IDE.imageDecoderBinary(EI1.getFileList());		
	    			}else {
	    				DI1.errorInfoAndSolution("connection");
	    			}
	    		}catch(IOException e) {
	    		}
	    	}
	    }
	   private void bufferSet(boolean TF) {
			   bufferFile = TF;
	   }
	    
  
}
