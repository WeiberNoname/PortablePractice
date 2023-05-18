package paint;

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
import java.util.ArrayList;
import java.util.Comparator;

import javax.swing.JPanel;
public class DataAnalyze extends JPanel{

    public BufferedImage canvas,canvas1;
    public Graphics2D g2d,g2d1;
    public Graphics2D g2,g21;
    public Graphics g1;
    private ArrayList<Point> points = new ArrayList<>(),point2 = new ArrayList<>() ;
    public Color color = Color.WHITE,colorSeparate = Color.green;
    public BufferedImage offscreen, offscreen1;
    public String stringBuffer = "";
    public int pixel = 3,pixelNumber = 0;
    public static DrawControl DC = new DrawControl();
    public int mouseX ,mouseY,mouseX1,mouseY1;
    public String name = "";
    public ArrayList<Point> point1 = new ArrayList<>();
    public boolean drawLineTF = false,drawGridTF = true,focusKernel = false;
    public  int deviationX = 0, deviationY = 0;
    public int deviation = -300,moveDeviation = 70,startX,startY;

    private SaveFile s = new SaveFile();
    private String imageLoad = "";
    
    private Get get1 = new Get();
    private CloseCommand CC =  new CloseCommand();
    //Enlarge

    public void detectPositionPoint1(MouseEvent e) {
    	pixelNumber = (600+deviation) / pixel;
    	int biasX = startX;
    	int biasY = startY;
    	int totalPixel = 600 + deviation;
    	int x = (e.getX()-10)-deviationX;
    	int y = (e.getY()-10)-deviationY;
    	try {
			if(x <=totalPixel&&y<=totalPixel) {
				mouseX1 = x/pixelNumber+biasX;
            	mouseY1 = y/pixelNumber+biasY;
			}else if(x >=totalPixel){
				mouseX1 = (totalPixel)/pixelNumber+biasX;
			}else if(y >=totalPixel){
				mouseY1 = (totalPixel)/pixelNumber+biasY;
			}else if(x <=totalPixel) {
				mouseX1 = 0+biasX;
			}else if(y <= totalPixel) {
				mouseY1 = 0+biasY;
			}
		}catch(ArithmeticException e1) {
		}
    }

    public void detectPositionPoints(MouseEvent e) {
    	if(drawLineTF) {
    	points.add(new Point(e.getX()-deviationX,e.getY()-deviationY));
    	}
    	}
    public void positionOfScreen(KeyEvent e) {
    	int KeyCode = e.getKeyCode();
    	if(KeyCode == KeyEvent.VK_DOWN) {
			deviationY -= moveDeviation;
			paintStart(getGraphics());
		}else if(KeyCode == KeyEvent.VK_UP) {
			deviationY += moveDeviation;
			paintStart(getGraphics());
		}else if(KeyCode == KeyEvent.VK_LEFT){
			deviationX -= moveDeviation;
			paintStart(getGraphics());
		}else if(KeyCode == KeyEvent.VK_RIGHT){
			deviationX += moveDeviation;
			paintStart(getGraphics());
		}
    }

    //g.setColor(Color.BLACK);
 //   g.fillRect(0, 0, getWidth(), getHeight());
    public DataAnalyze() {

    	setBackground(Color.WHITE);
        addMouseListener(new MouseAdapter() {
            @Override
			public void mousePressed(MouseEvent e) {
            		detectPositionPoints(e);
            		requestFocusInWindow();

            }
            @Override
			public void mouseReleased(MouseEvent e) {
            		points.add(null);
            }
        });
        addMouseMotionListener(new MouseMotionListener() {
			@Override
			public void mouseDragged(MouseEvent e) {
				pixelNumber = (600+deviation) / pixel;
			}
			@Override
			public void mouseMoved(MouseEvent e) {
				pixelNumber = (600+deviation)/ pixel;
				if(focusKernel) {
					detectPositionPoint1(e);
				}
			}
        });

        addMouseMotionListener(new MouseAdapter() {
            @Override
			public void mouseDragged(MouseEvent e) {
            	  	detectPositionPoints(e);
            	//  	detectPositionPoint1(e);
            	  	drawLine();
            }
        });


        addKeyListener(new KeyAdapter() {
        	@Override
			public void keyReleased(KeyEvent e) {
        		int KeyCode = e.getKeyCode();
        		char KeyChar = e.getKeyChar();

        		positionOfScreen(e);

        		if(stringBuffer.length()<= 100) {
        		if(KeyCode == KeyEvent.VK_BACK_SPACE&&stringBuffer.length()>0) {
        			stringBuffer = stringBuffer.substring(0,stringBuffer.length()-1);
        		}else if(KeyCode<= KeyEvent.VK_Z&&KeyCode>= KeyEvent.VK_A){
        			stringBuffer += (e.getKeyChar());
        		}else if(KeyCode == KeyEvent.VK_SPACE) {
        			stringBuffer += (e.getKeyChar());
        		}else if(KeyCode == KeyEvent.VK_ENTER) {
        			DC.commandLine(stringBuffer);
        			stringBuffer = "";
        			System.out.println(deviation);
        		}else if(KeyChar <= '9'&&KeyChar >= '0') {
        			stringBuffer += (e.getKeyChar());
        		}else if(KeyCode != KeyEvent.VK_CONTROL&&KeyCode != KeyEvent.VK_SHIFT&&KeyCode != KeyEvent.VK_LEFT&&KeyCode != KeyEvent.VK_RIGHT&&KeyCode != KeyEvent.VK_UP&&KeyCode != KeyEvent.VK_DOWN){
        			stringBuffer += (e.getKeyChar());
        		}



        		}
        		if(e.isControlDown()&& KeyCode == KeyEvent.VK_N) {
        			pixel = DC.getPixel();
        			renew(pixel);
        		}else if(e.isControlDown()&& KeyCode == KeyEvent.VK_D) {
        			drawLineTF = true;
        			drawLine();
        		}else if(e.isControlDown()&& KeyCode == KeyEvent.VK_P) {
        			drawLineTF = false;
        			cleanDrawLine();
        		}else if(e.isControlDown()&& KeyCode == KeyEvent.VK_R) {
        			points.clear();
        			cleanDrawLine();
        		}
        		else if(e.isControlDown()&& KeyCode == KeyEvent.VK_L) {
        			drawGridTF = false;
        			cleanDrawLine();
        		}
        		else if(e.isControlDown()&& KeyCode == KeyEvent.VK_S) {
        			drawGridTF = true;
        			cleanDrawLine();
        		}else if(e.isControlDown()&&KeyCode == KeyEvent.VK_Q) {
        			CC.removeComponent("DA");
        		}else if(e.isControlDown()&&KeyCode == KeyEvent.VK_F) {
        			focusKernelSet();
        		}else if(e.isControlDown()&&KeyCode == KeyEvent.VK_I) {
        			image();
        		}

        		if(e.getKeyCode() == KeyEvent.VK_BACK_SPACE&&stringBuffer.length()>0) {
        			stringBuffer = stringBuffer.substring(0,stringBuffer.length());
        		}
        	}
        });
    }
    
    public void image() {
    	boolean T1;
    	
    	 BufferedImage image1_1 = canvas;
         BufferedImage image2_2 = offscreen;
         
         // draw something on the first image
         Graphics2D graphics1 = image1_1.createGraphics();
         graphics1.dispose();

         // draw the first image onto the second image
         graphics1 = image2_2.createGraphics();
         graphics1.drawImage(image1_1, 10, 10, null);
         graphics1.dispose();
         T1 = s.saveImage(name+"Canvas",offscreen);
    	if(T1) {
    		imageLoad = "Successfully loading!";
    	}else {
    		imageLoad = "Error!";
    	}
    }
    
    public void focusKernelSet() {
    	if(focusKernel) {
			focusKernel = false;
		}else {
			focusKernel = true;
		}
    }
    //g2d
    public void focusIn() {
    	if(focusKernel) {
    		 g2d.setColor(Color.BLACK);
	         g2d.fillRect(0,0,603+deviation,603+deviation);
	         g2d.setColor(Color.green);
	         g2d.drawRect(0,0,601+deviation,601+deviation);
	         g2d.setColor(colorSeparate);
	         if(drawGridTF) {
	         drawSeparate(pixel);
	         }
	    	int d = (pixel - 1)/2;

	    	if(d < 2) {
	    		d  = 2;
	    	}	
		
		int size = point2.size();
    	for(int i = 0 ; i< size;i++) {
    		Point p1 = point2.get(i);
    			g2d.setColor(new Color(200,50,50));
    			g2d.fillRect(p1.x, p1.y, pixelNumber, pixelNumber);
    	}
    
    	}else {
    		point2.clear();
	    	 g2d.setColor(Color.BLACK);
	         g2d.fillRect(0,0,603+deviation,603+deviation);
	         g2d.setColor(Color.green);
	         g2d.drawRect(0,0,601+deviation,601+deviation);
	         g2d.setColor(colorSeparate);
	         if(drawGridTF) {
	         drawSeparate(pixel);
	         }
	    	int numX1 ,numX2 , numY1,numY2;
	    	int d = (pixel - 1)/2;

	    	if(d < 1) {
	    		d  = 1;
	    	}	
	    	numX1 = mouseX - d;
			numX2 = mouseX + d;
			numY1 = mouseY - d;
			numY2 = mouseY + d;
			startX = mouseX-d;
			startY = mouseY-d;
		int size = point1.size();
    	for(int i = 0 ; i< size;i++) {
    		Point p1 = point1.get(i);
    		if(p1.x==mouseX-1&&p1.y==mouseY-1) {
    			g2d.setColor(new Color(200,50,50));
    			g2d.fillRect((pixelNumber*(p1.x-mouseX+d+1))+1, (pixelNumber*(p1.y-mouseY+d+1))+1, pixelNumber, pixelNumber);
    			point2.add(new Point((pixelNumber*(p1.x-mouseX+d+1))+1, (pixelNumber*(p1.y-mouseY+d+1))+1));
    		}else if(p1.x >= numX1-1 && p1.x<= numX2-1&& p1.y >= numY1-1&&p1.y <= numY2-1) {
    			point2.add(new Point((pixelNumber*(p1.x-mouseX+d+1))+1, (pixelNumber*(p1.y-mouseY+d+1))+1));
    			g2d.setColor(Color.green);
    			g2d.fillRect((pixelNumber*(p1.x-mouseX+d+1))+1, (pixelNumber*(p1.y-mouseY+d+1))+1, pixelNumber, pixelNumber);
    		}else if(p1.x+1 >= numX1 && p1.x-1<= numX2&& p1.y -1>= numY1&&p1.y <= numY2+1) {
    			point2.add(new Point((pixelNumber*(p1.x-mouseX+d+1))+1, (pixelNumber*(p1.y-mouseY+d+1))+1));
    			g2d.setColor(Color.magenta);
    			g2d.fillRect((pixelNumber*(p1.x-mouseX+d+1))+1, (pixelNumber*(p1.y-mouseY+d+1))+1, pixelNumber, pixelNumber);
    		}
    	}
    
		}
    	}

    public void updateInfo() {
    //	pixel = get1.dataGetInt("pixel0");
    //	pixelNumber = get1.dataGetInt("pixelNumber0");
    //	deviation = get1.dataGetInt("pixelSize0");
    	mouseX = get1.dataGetInt("mouseX");
    	mouseY = get1.dataGetInt("mouseY");
    	point1 = get1.pointUpdate("point10");
    	focusKernel = get1.booleanGet("focusKernel");
    }

    public void renew(int i) {
    	canvas = null;
		offscreen = null;
		points.clear();
		stringBuffer = "";
		deviation = DC.getDeviation();
		if(i <= 0&& i > (600+deviation)) {
			pixel = 1;
		}else {
			pixel = i;
		}
		imageLoad = "";
    }
    public void cleanDrawLine() {
    	canvas = null;
		offscreen = null;
		paintStart(getGraphics());
		stringBuffer = "";
		imageLoad = "";
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public void drawSeparate(int i1) {
    	pixel = i1;
    	if(pixel <= 0) {
    		pixel = 1;
    	}
    	int totalPixel = 600+deviation;
    	if(drawGridTF) {
    	g2d.setColor(new Color(10,130,10));
    	 int line = (totalPixel) /pixel;
         for(int i = 0 ; i <= pixel;i++) {
         	g2d.drawLine(line*i,0,line*i,totalPixel);
         }
         for(int i = 0 ; i <= pixel;i++) {
         	g2d.drawLine(0,line*i,totalPixel,line*i);
         }
    	}
    }


    @Override
	public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.black);
        g.fillRect(0, 0, getWidth(), getHeight());
        paintStart(g);
        focusIn();
        repaint();
    }




    //two method that provide for user dragging skill
    private void paintStart(Graphics g) {
    	   // Create the off-screen image if it hasn't been created yet
        if (offscreen == null) {
            offscreen = new BufferedImage(625+deviation,850+deviation, BufferedImage.TYPE_INT_ARGB);
        }
   	 	updateInfo();
        // Draw to the off-screen image
        g2 = offscreen.createGraphics();
        // Perform your painting operations here
        g2.setColor(Color.BLACK);
        g2.fillRect(0, 0, 625+deviation,620+deviation);
        g2.setColor(Color.GREEN);
        g2.setFont(new Font("one",Font.CENTER_BASELINE,10));
        g2.drawString("Pixel : "+ pixel,10,9);
        name = DC.getName();
        g2.drawString("Name : "+name,100,9);
        // Draw the off-screen image to the screen
        g.drawImage(offscreen, 0+deviationX, 0+deviationY, null);

        //End Line


        if (canvas == null) {
            canvas = new BufferedImage(603+deviation, 603+deviation, BufferedImage.TYPE_INT_ARGB);
            g2d = (Graphics2D) canvas.getGraphics();
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            g2d.setColor(Color.BLACK);
            g2d.fillRect(0,0,603+deviation,603+deviation);
            g2d.setColor(Color.green);
            g2d.drawRect(0,0,601+deviation,601+deviation);
            g2d.setColor(colorSeparate);
            drawSeparate(pixel);
        }


        g.drawImage(canvas, 10+deviationX, 10+deviationY, null);
        //scale
        g.setColor(Color.BLACK);
        g.fillRect( 0+deviationX, 615+deviationY+deviation,625+deviation,200);
        g.setColor(Color.green);
        g.drawRect(10+deviationX,615+deviationY+deviation,601+deviation,175);
        g.drawRect(10+deviationX,790+deviationY+deviation,601+deviation,20);
        //draw String
        g.setColor(Color.green);
        g.drawString(stringBuffer,10+deviationX,803+deviationY+deviation);
        g.drawString("Coordinate X : "+mouseX1+" Coordinate Y : "+mouseY1,10+deviationX,625+deviationY+deviation);
      
        if(imageLoad.length()>0) {
       	 g.drawString(imageLoad,10+deviationX,645+deviationY+deviation);
       }
        
        repaint();
    }

    private void drawLine() {
    	if(drawLineTF) {
        g2d.setColor(color);
        for (int i = 1; i < points.size(); i++) {
            Point p1 = points.get(i - 1);
            Point p2 = points.get(i);
            if(p1 == null||p2 == null) {

            }else {
            	g2d.drawLine(p1.x, p1.y, p2.x, p2.y);
            }
        }
        repaint();
    	}
    }

}





