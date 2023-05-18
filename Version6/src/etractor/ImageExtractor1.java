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

public class ImageExtractor1 extends JPanel{

	  public BufferedImage canvas;
	  
	    public Graphics2D g2d,g2d1;
	    public Graphics2D g2,g21;
	    public Graphics g1;
	    
	    public Color color = Color.WHITE,colorSeparate = Color.green,rgb = Color.green;
	    
	    private int mouseX = 0, mouseY;
	    
	    
	    private static int percentForBinary = 0;
	    //Image process illustration
	    protected String typeOfProcess = "Default(null)";
	    
	    public static String stringBuffer = "ImageModel",clickedContent = "",stringCode = "";
	    
	    public boolean drawLineTF = false,bufferFile = false,drawGridTF = true,focusKernel = true,coordinate = false;
	    
	    private Color colorProgress = Color.red;
		private Color endProgress = Color.cyan;
	    
	    private CloseCommand CC = new CloseCommand();
	    private Key1 k1 = new Key1();
	    private KernelSet1 KS = new KernelSet1();
	    private CopyContentManager CCM = new CopyContentManager();
	    private ImageDataExtractor IDE = new ImageDataExtractor();
	    private ExtendsInteraction1 EI1 = new ExtendsInteraction1();
	    private DataInteraction1 DI1 = new DataInteraction1();
	    
	    public int deviationX = 0, deviationY = 0;
	    
	    public ImageExtractor1() {
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
  			if(bufferFile) {
  				bufferSet(false);
  			}else {
  				CC.removeComponent("image");
  			}
  		}else if(KeyCode == KeyEvent.VK_ENTER) {
  			division(stringBuffer);
  			stringBuffer = "";
  		}
  		if(KeyCode == KeyEvent.VK_CLOSE_BRACKET) {
  			stringBuffer += KeyChar;
  		}else if(KeyCode == KeyEvent.VK_OPEN_BRACKET) {
  			stringBuffer += KeyChar;
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
	        publicDrawer(g);
	        publicWriter(g);
	        RGBSet(g);
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
	    
	    	//This is used to calculate percentage that based on the DataInteraction1
		   public void setPercentForBinary() {
			   percentForBinary = DI1.percentageForBinary;
		   }
		   //This is a contentWriter and used to draw some of the information on the panel
		   public void publicWriter(Graphics g) {
			   g.setColor(new Color(30,30,30));
			   String adjustString = "";
			   int wordLength = typeOfProcess.length()*11;
			   int scale = getWidth()-100;
			   if(wordLength>scale) {
				   scale = scale / 11;
				   adjustString = typeOfProcess.substring(0, scale);
			   }else {
				   adjustString = typeOfProcess;
			   }
			   g.drawString(adjustString+" "+percentForBinary+"%", 70, 100);
		   }
		   //This is a showing part that contain tha progress of the process
		   public void publicDrawer(Graphics g) {
			 drawProgress1(g);
		   }
		   //Graphics illustration
		   
		   //progress (Top)
		   private void drawProgress1(Graphics g) {
			   int width = getWidth()-100;
			   g.setColor(new Color(10,100,10));
			   g.drawRect(50, 70, width,70);
			   g.setColor(new Color(10,120,10));
			   g.fillRect(51,71,width-2,68);
			   g.setColor(Color.black);
			   g.fillRect(0, 700, width+100,70);
			   g.drawRect(60, 120, width-20,10);
			   g.setColor(new Color(30,30,30));
			   g.fillRect(61, 121, width-22,8);

			   int progressLength = width-22;
			   int totalLength = progressLength;
			   int one = totalLength/100;
			   int finalPercent = percentForBinary*one;
			   if(percentForBinary == 100) {
				   finalPercent = progressLength;
				   completeInformation();
			   }else if(percentForBinary == 1) {
				   finalPercent = 0;
			   }else if(percentForBinary > 100){
				   finalPercent = progressLength;
			   } 
			   if(percentForBinary < 100) {
				   colorProgress = Color.red;
			   }
			   g.setColor(colorProgress);
			   setPercentForBinary();
			   g.fillRect(61, 121,finalPercent, 8);
		   }
		   //End information
		  
		   private void completeInformation() {
			   typeOfProcess = "Complete program process!";
			   colorProgress = endProgress;
		   }
		   public static String[] rgbFormula = new String[3];
		   private void RGBSet(Graphics g) {
			   int width = getWidth()-100;
			   g.setColor(new Color(10,100,10));
			   g.drawRect(50, 150, width,70);
			   g.setColor(new Color(10,120,10));
			   g.fillRect(51,151,width-2,68);
			   g.setColor(Color.black);
			   g.drawString("Red Model Set : "+rgbFormula[0], 70, 170);
			   g.drawString("Green Model Set : "+rgbFormula[1], 70, 190);
			   g.drawString("Blue Model Set : "+rgbFormula[2], 70, 210);
		   }
		   
		//command division : used to check command list   
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
		    				typeOfProcess = "Extract image data transform into binary file";
		    			}else {
		    				DI1.errorInfoAndSolution("connection");
		    			}
		    		}catch(IOException e) {
		    		}
		    	}else if(command.indexOf("decode")>-1) {
		    		try {
		    			if(DI1.connectionTest()) {
		    				IDE.imageDecoderBinary(EI1.getFileList());
		    				typeOfProcess = "Decode binary file into image file(png)";
		    			}else {
		    				DI1.errorInfoAndSolution("connection");
		    			}
		    		}catch(IOException e) {
		    		}
		    	}else if(command.indexOf("r set")>-1) {
		    		if(command.indexOf("[")>-1&&command.indexOf("]")>-1) {
		    			rgbFormula[0] = command.substring(command.indexOf("[")+1,command.indexOf("]"));
		    		}
		    	}else if(command.indexOf("g set")>-1) {
		    		if(command.indexOf("[")>-1&&command.indexOf("]")>-1) {
		    			rgbFormula[1] = command.substring(command.indexOf("[")+1,command.indexOf("]"));
		    		}
		    	}else if(command.indexOf("b set")>-1) {
		    		if(command.indexOf("[")>-1&&command.indexOf("]")>-1) {
		    			rgbFormula[2] = command.substring(command.indexOf("[")+1,command.indexOf("]"));
		    		}
		    	}
		    	extractCommand();
		    }
		   public void extractCommand() {
			   if(rgbFormula != null) {
			   for(int i = 0 ; i < 3; i++) {
				   if(rgbFormula[i] != null) {
					   rgbCommand(rgbFormula[i]);
				   }
			   }
			   }
		   }
		   
		   
		   //This method used to analyze and divided each different part of the method
		   private void rgbCommand(String command) {
			 
			   
			   
			   if(command.length()>0) {
				   String[] tokens = command.split(" ");
				   String token = "";
				   int position = 0;
				   int size = tokens.length;
				   
			   do {
				   token = tokens[position];
				 
				   if(token.indexOf("r")>-1) {
					   
				   }else if(token.indexOf("g")>-1) {
					   
				   }else if(token.indexOf("b")>-1) {
					   
				   }else if(token.indexOf("+")>-1) {
					   
				   }else if(token.indexOf("-")>-1) {
					   
				   }else if(token.indexOf("*")>-1) {
					   
				   }else if(token.indexOf("/")>-1) {
					   
				   }else {

				   }
				 position +=1;
			   }while(position<size);
			   }
		   }
		   //Based on the above formula to detect what will happen after compression or even Color transformation>> using a default example
		   //create a movable image histogram
		   public void histogramPaint(Graphics g) {
			   
		   }
		   
	 
	   protected void bufferSet(boolean TF) {
			   bufferFile = TF;
	   }
	    
  
}




