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

public class CodeFirstGeneration extends JPanel{

	  public BufferedImage canvas;
	  
	  private ArrayList<String> codeList = new ArrayList<String>();
	  private int line = 0;
	  
	    public Graphics2D g2d,g2d1;
	    public Graphics2D g2,g21;
	    public Graphics g1;
	    
	    public Color color = Color.WHITE,colorSeparate = Color.green,rgb = Color.green;
	    
	    private int positionX = 0,positionY = 0;
	    
	    private int mouseX,mouseY,deviation=0,cursor = 0;
	    public static String stringBuffer = "Terminal",clickedContent = "",stringCode = "";
	    
	    private boolean codeTF = false,terminalTF = true,editingTF = false;
	    public boolean drawLineTF = false,drawGridTF = true,focusKernel = true,coordinate = false;
	    private KeyCommon KC = new KeyCommon();
	    private AddAPI api = new AddAPI();
	    private CloseCommand CC = new CloseCommand();

	    public CodeFirstGeneration() {
	    	setBackground(Color.WHITE);
	        addMouseListener(new MouseAdapter() {
				public void mousePressed(MouseEvent e) {
						api.addComponent(clickedContent);
	            		requestFocusInWindow();
	            }
				public void mouseClicked(MouseEvent e) {
					enableCoding(e);
					editingSet();
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
	        			if(line<codeList.size()) {
	        				line+=1;
	        			}
	        			
	        		}else if(keyCode== KeyEvent.VK_UP) {
	        			deviation -= 80;
	        			if(line>0) {
	        			line-= 1;
	        			}
	        		}else if(keyCode == KeyEvent.VK_LEFT) {
	        			if(stringCode.length()+cursor>=0) {
	        				cursor -= 1;
	        				
	        				System.out.println("cursor");
	        			}
	        		}else if(keyCode== KeyEvent.VK_RIGHT) {
	        			if(cursor < 0) {
	        				cursor += 1;
	        			}
	        		}
				}
				public void keyReleased(KeyEvent e) {
					if(terminalTF) {
						keyListenerTerminal(e);
					}else if(codeTF) {
						stringCode += "|";
						cursor = 0;
						keyListenerCode(e);
					}
				}
	        });
	    }
	    
	    private void enableCoding(MouseEvent e) {
	    	positionX = e.getX();
			positionY = e.getY();
			if(positionY <= 60&&codeTF) {
				terminalTF = true;
				codeTF = false;
			}else {
				codeTF = true;
				terminalTF = false;
			}
	    }
	    
	    private void keyListenerTerminal(KeyEvent e) {
	    	stringBuffer  = KC.keyCheck(e, stringBuffer);
    		int KeyCode = e.getKeyCode();
    		char KeyChar = e.getKeyChar();
    		if(e.isControlDown()&& KeyCode == KeyEvent.VK_N) {
    			cleanDrawLine();
    		}else if(e.isControlDown()&& KeyCode == KeyEvent.VK_Q) {
    			CC.removeComponent("code");
    		}else if(KeyCode == KeyEvent.VK_ENTER) {
    			stringBuffer = "";
    		}
	    }
	    private void keyListenerCode(KeyEvent e) {
    		int KeyCode = e.getKeyCode();
    		char KeyChar = e.getKeyChar();
    		if(!(KeyCode == KeyEvent.VK_CONTROL)) {
    			stringCode =  KC.keyCheck(e, stringCode);
    		}
    		if(e.isControlDown()&& KeyCode == KeyEvent.VK_N) {
    			cleanDrawLine();
    		} else if(e.isControlDown()&& KeyCode == KeyEvent.VK_Q) {
    			CC.removeComponent("code");
    		}else if(KeyCode == KeyEvent.VK_ENTER) {
    			if(editingTF&&codeList.size()>1) {
    				codeList.set(line, stringCode);
    			}else {
    				codeList.add(stringCode);
    			}
    			stringCode = "";
    		}
	    }
	    
	    
	    private void editingSet() {
	    	if(editingTF) {
	    		editingTF = false;
	    	}else {
	    		editingTF = true;
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
	    
	    public void renew(int i) {
	    	canvas = null;
			stringBuffer = "";
	    }
	    public void cleanDrawLine() {
	    	canvas = null;
			paintStart(getGraphics());
			stringBuffer = "";
			stringCode = "";
			codeList.clear();
			clickedContent = "";
			line = 0;
	    }
	    

	    
	    private void coding(Graphics g) {
	    	Font font = new Font("one",Font.CENTER_BASELINE,16);
	    	g.setFont(font);
	    	int fontMetrics = 8;
	    	int width = getWidth()-100*2;
	    	int height = getHeight();
	    	int size = codeList.size();
	    	int position = 0;
	    	for(int i = 0 ; i < size;i++) {
	    		int startY = 100+20*i+deviation;
	    		position = i;
	    		if(startY<=height&&startY >= 80) {
	    		g.setColor(new Color(30,150,30));
	    		if(mouseY <(100+(20*i)+deviation)&&mouseY > (100 + (20*(i-1))+deviation)&&!editingTF) {
	    			g.setColor(new Color(70,210,70));
	    			clickedContent = codeList.get(i);
	    			line = i;
	    		}
	    		String content = codeList.get(i);
	    		otherPaint(content,g,i); 
	    		paintBuffer(i,size, g,content,position);
	    		}else {
	    		}
	    		
	    	}
	    }
	    
	    public void otherPaint(String content,Graphics g,int i) {
	    	if(content.indexOf(stringBuffer)>-1&&stringBuffer.length()>0) {
    			g.setColor(new Color(130,10,10));
    		}
    		if(i == line&&editingTF) {
    			g.setColor(Color.orange);
    		}
	    }
	    
	    public void paintBuffer(int i, int size, Graphics g,String content, int position) {
	    	if(i+1 == size) {
    			if(editingTF&&clickedContent.length()>0) {
    				stringCode += clickedContent;
    				clickedContent = "";
    			}
    			g.setColor(Color.white);
    			g.drawString("Buffer "+line+" : "+stringCode,15,120+20*i+deviation);
    		}
	    	g.drawString(String.valueOf(position),20,100+20*i+deviation);
    		g.drawString(content,100,100+20*i+deviation);
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
	        //Code
	        
	        coding(g);
	        
	        
	        repaint();
	    }
	    
    
}
