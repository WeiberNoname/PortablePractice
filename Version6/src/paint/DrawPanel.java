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


import javax.swing.JPanel;

public class DrawPanel extends JPanel{

    public BufferedImage canvas,canvas1,image;
    public BufferedImage offscreen, offscreen1;
    
    private SaveFile s = new SaveFile();
    private String imageLoad = "";
    
    public Graphics2D g2d,g2d1;
    public Graphics2D g2,g21;
    public Graphics g1;
    
    public Color color = Color.WHITE,colorSeparate = Color.green,rgb = Color.green;
    
    public static DrawControl DC = new DrawControl();
    
    public int mouseX ,mouseY;
    public int deviation = 0,moveDeviation = 70;
    private int tempX = 0 ,tempY = 0;
    public  int deviationX = 0, deviationY = 0;
    public int pixel = 0,pixelNumber = 0;
    public int pixel_c = 1,pixelNumber_c = 1,deviation_c = 1;

    public String name = "";
    public static String stringBuffer = "";
    
    public ArrayList<Color> colorSet = new ArrayList<Color>();
    public ArrayList<Point> point1 = new ArrayList<>();
    private ArrayList<Point> points = new ArrayList<>();
    public ArrayList<Point> pointColor = new ArrayList<Point>();
    
    public boolean drawLineTF = false,drawGridTF = true,focusKernel = true,coord1inate = false;
    private CloseCommand CC = new CloseCommand();

    //Enlarge
    public void clicked() {
    	if(mouseX <= pixel&&mouseY <= pixel) {
    		int size = point1.size();
    	for (int i = 0 ; i < size;i++) {
    		Point p1 = point1.get(i);
    		g2d.setColor(Color.green);
    		g2d.fillRect((pixelNumber*p1.x)+1, (pixelNumber*p1.y)+1, pixelNumber, pixelNumber);
    	}
    	}else {
    	//	g2d.fillRect(pixelNumber*(pixel-1)+1, 1, 500-pixelNumber*pixel, 500);
    		//g2d.fillRect(1, pixelNumber*(pixel-1)+1, 500, 500-pixelNumber*pixel);
    	}
    	repaint();
    }
    
    
    public void point1Set(int x,int y) {
    	if(!point1.contains(new Point(x-1,y-1))&&!drawLineTF) {
    		point1.add(new Point(mouseX-1,mouseY-1));
    	  	clicked();
    	}
    }
    public void detectPositionPoint1(MouseEvent e) {
    	pixelNumber = (600+deviation) / pixel;
    	int totalPixel = 600 + deviation;
    	int x = (e.getX()-10)-deviationX;
    	int y = (e.getY()-10)-deviationY;
    	try {
			if(x <=totalPixel&&y<=totalPixel) {
				mouseX = x/pixelNumber+1;
            	mouseY = y/pixelNumber+1;
			}else if(x >=totalPixel){
				mouseX = (totalPixel)/pixelNumber;
			}else if(y >=totalPixel){
				mouseY = (totalPixel)/pixelNumber;
			}else if(x <=totalPixel) {
				mouseX = 0;
			}else if(y <= totalPixel) {
				mouseY = 0;
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
    public DrawPanel() {
    	setBackground(Color.WHITE);
        addMouseListener(new MouseAdapter() {
            @Override
			public void mousePressed(MouseEvent e) {
            		detectPositionPoints(e);
            		point1Set(mouseX,mouseY);
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
				detectPositionPoint1(e);
			}
        });

        addMouseMotionListener(new MouseAdapter() {
            @Override
			public void mouseDragged(MouseEvent e) {
            	  	detectPositionPoints(e);
            	  	detectPositionPoint1(e);
            	  	point1Set(mouseX,mouseY);
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
        		}else if(KeyChar <= '9'&&KeyChar >= '0') {
        			stringBuffer += (e.getKeyChar());
        		}else if(KeyCode != KeyEvent.VK_CONTROL&&KeyCode != KeyEvent.VK_SHIFT&&KeyCode != KeyEvent.VK_LEFT&&KeyCode != KeyEvent.VK_RIGHT&&KeyCode != KeyEvent.VK_UP&&KeyCode != KeyEvent.VK_DOWN){
        			stringBuffer += (e.getKeyChar());
        		}
        		}else {
        			stringBuffer = "";
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
            			CC.removeComponent("DP");
        		}else if(e.isControlDown()&&KeyCode == KeyEvent.VK_F) {
        				focusKernelSet();
        		}else if(e.isControlDown()&&KeyCode == KeyEvent.VK_C) {
        		}else if(e.isControlDown()&&KeyCode == KeyEvent.VK_V) {
        			DC.getClipboardText();
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
    	}else{
    		focusKernel = true;
    	}
    }
    
    public void renew(int i) {
    	canvas = null;
		offscreen = null;
		points.clear();
		point1.clear();
		colorSet.clear();
		pointColor.clear();
		stringBuffer = "";
		imageLoad = "";
		deviation = DC.getDeviation();
		if(i <= 0&& i > (600+deviation)) {
			pixel = 1;
		}else {
			pixel = i;
		}
    }
    public void cleanDrawLine() {
    	canvas = null;
		offscreen = null;
		paintStart(getGraphics());
		stringBuffer = "";
		imageLoad = "";
		clicked();
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

	public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(new Color(0,0,0));
        g.fillRect(0,0,getWidth(),getHeight());
        paintStart(g);
        repaint();
    }

    //two method that provide for user dragging skill
    private void paintStart(Graphics g) {
    	   // Create the off-screen image if it hasn't been created yet
        if (offscreen == null) {
            offscreen = new BufferedImage(625+deviation,850+deviation, BufferedImage.TYPE_INT_ARGB);
        }

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
        g.drawString("Coordinate X : "+mouseX+" Coordinate Y : "+mouseY,10+deviationX,625+deviationY+deviation);
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

