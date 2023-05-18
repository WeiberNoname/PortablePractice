package paint;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.io.*;
import java.awt.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.JPanel;
import DataManager.*;

public class FileManagement extends JPanel{
		
		private File[] file = File.listRoots();
		private int rootNum = 0;
		private File storePath ;
		private FileControl FC = new FileControl();
		private String copy = "";
		
		private boolean appearTF = false;
		
	  public BufferedImage canvas;
	  
	  private ArrayList<File> fileList = new ArrayList<File>();
	  
	  
	    public Graphics2D g2d,g2d1;
	    public Graphics2D g2,g21;
	    public Graphics g1;
	    
	    public Color color = Color.WHITE,colorSeparate = Color.green,rgb = Color.green;
	    
	    
	    private int mouseX ,mouseY,deviation = 0;
	    public static String stringBuffer = "File Manager",clickedContent = "";
	    
	    public boolean drawLineTF = false,drawGridTF = true,focusKernel = true,coordinate = false;
	    private KeyCommon KC = new KeyCommon();
	    private AddAPI api = new AddAPI();
	    private CloseCommand CC = new CloseCommand();
	    private ExtendsInteraction1 EI1 = new ExtendsInteraction1();
	    private CopyContentManager CCM = new CopyContentManager();
	    
	    public FileManagement() {
	    	
	    	setBackground(Color.WHITE);
	        addMouseListener(new MouseAdapter() {
				public void mousePressed(MouseEvent e) {
						directorySearch();
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
	        			fileListAdd();
	        		}else if(e.isControlDown()&& KeyCode == KeyEvent.VK_Q) {
	        			if(appearTF) {
	        				appearSet(false);
	        			}else {
	        				CC.removeComponent("file");
	        			}
	        			
	        		}else if(KeyCode == KeyEvent.VK_ENTER) {
	        			basicSearch();
	        		}else if(e.isControlDown()&& KeyCode == KeyEvent.VK_Z) {
	        			goBack();
	        		}else if(e.isControlDown()&&KeyCode == KeyEvent.VK_V) {
	        			if(stringBuffer.indexOf("mkdir ")>-1||stringBuffer.indexOf("move ")>-1) {
	        				pasteCopy();
	        			}else {
	        				getClipboardText();
	        			}
	        		}else if(e.isControlDown()&&KeyCode == KeyEvent.VK_C) {
	        			copy();
	        		}
	        	}
	        });
	    }
	    private void copy() {
	    	EI1.setCopyContent(clickedContent);
			copy = EI1.getCopyContent();
	    }
	    private void basicSearch() {
	    	File f = new File(FC.commandFilter(stringBuffer));
			if(f.exists()&&f.isDirectory()) {
				storePath = f;
				EI1.setFileStoreLocation(f.toString(),"File Manager","Parallel path location");
			} 
			if(stringBuffer.indexOf("default 0")>-1) {
				EI1.setAllDefalut();
			}else if(stringBuffer.indexOf("open buffer")>-1) {
				appearSet(true);
			}else if(stringBuffer.indexOf("off buffer")>-1) {
				appearSet(false);
			}
			stringBuffer = "";
	    }
	    private void appearSet(boolean TF) {
	    	appearTF = TF;
	    }
	    private void pasteCopy() {
	    	stringBuffer += copy;
	    }
	    private void goBack() {
	    	try {
	    		if(fileList.size()>0) {
	    			String f1 = fileList.get(0).getParent();
	    			String check = new File(f1).getParent();
	    		File[] f = new File(check).listFiles();
		    	fileList.clear();
		    	if(f != null) {
		    	for(int i = 0 ; i < f.length;i++) {
		    		fileList.add(f[i]);
		    		}
		    	}
	    		}
	    	}catch(NullPointerException e){
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
			paintStart(getGraphics());
			deviation = 0;
	    }
	    
	    private void fileListAdd() {
	    	try {
	    		file = File.listRoots();
	    		int length = file[rootNum].listFiles().length;
	    		File[] listFile = file[rootNum].listFiles();
	    		fileList.clear();
	    		for(int i = 0 ; i < length;i++){
	    			fileList.add(listFile[i]);
	    		}
	    		rootNum+=1;
	    		if(rootNum >= file.length) {
	    			rootNum = 0;
	    		}
	    		if(!storePath.exists()) {
	    			storePath = fileList.get(0);
	    		}
	    	}catch(NullPointerException e) {
	    	}
	    }
	    
	    private void directorySearch() {
	    	File check = new File(clickedContent);
	    	try {
	    	if(check.isDirectory()) {
	    		File[] f = new File(clickedContent).listFiles();
		    	fileList.clear();
		    	for(int i = 0 ; i < f.length;i++) {
		    		fileList.add(f[i]);
		    	}
	    	}
	    	}catch(NullPointerException e) {
	    	}
	    }
	    private void fileListStart(Graphics g) {
	    	Font font = new Font("one",Font.CENTER_BASELINE,16);
	    	g.setFont(font);
	    	int fontMetrics = 8;
	    	int width = getWidth()-100*2;
	    	int height = getHeight();
	    	int size = fileList.size();
	    	
	    	File f ;
	    	for(int i = 0 ; i < size;i++) {
	    		int startY = 100+20*i+deviation;
	    		f = new File(fileList.get(i).getAbsoluteFile().toString());
	    		if(startY<=height-30&&startY >= 80) {
	    		String content = fileList.get(i).getName().toString();
	    		if(mouseY <(100+(20*i)+deviation)&&mouseY > (100 + (20*(i-1))+deviation)) {
	    			g.setColor(new Color(0,130,0));
	    			if(fileList.get(i).getAbsolutePath().length() > 0) {
	    				clickedContent =fileList.get(i).getAbsolutePath();
	    			}
	    			content = fileList.get(i).getAbsolutePath().toString();
	    			 
	    		}else {
	    			g.setColor(new Color(0,200,0));
	    		}
	    		if(f.isDirectory()) {
	    			g.setColor(new Color(150,120,10));
	    		}
	    		if(content.indexOf(stringBuffer)>-1&&stringBuffer.length()>0) {
	    			g.setColor(new Color(130,10,10));
	    		}
	    		g.drawString(content,100,100+20*i+deviation);
	    		}else {
	    		}
	    	}
	    }
	    
		 private void getClipboardText() {
		        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
		        Transferable contents = clipboard.getContents(null);
		        boolean hasText = (contents != null) && contents.isDataFlavorSupported(DataFlavor.stringFlavor);
		        if (hasText) {
		            try {
		            	stringBuffer += (String) contents.getTransferData(DataFlavor.stringFlavor);
		            } catch (Exception ex) {
		                ex.printStackTrace();
		            }
		        }
		    }

		public void paintComponent(Graphics g) {
	        super.paintComponent(g);
	        g.setColor(new Color(0,0,0));
	        g.fillRect(0,0,getWidth(),getHeight());
	        paintStart(g);
	        if(appearTF) {
	        CCM.paintBufferFileManager(g, mouseX, mouseY, "directory");
	        }
	        repaint();
	    }

	    //two method that provide for user dragging skill
	    private void paintStart(Graphics g) {
	    	int width = getWidth();
	    	int height = getHeight();
	        if (canvas == null) {
	            canvas = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
	            g2d = (Graphics2D) canvas.getGraphics();
	            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

	            g2d.setColor(Color.BLACK);
	            g2d.fillRect(0,0,width, height);
	            g2d.setColor(Color.green);
	            g2d.drawRect(0,0,width, height);
	            g2d.setColor(colorSeparate);
	        }

	        g.drawImage(canvas, 0,0, null);

	        //scale
	        g.setColor(Color.BLACK);
	        g.fillRect(0, 0,width, height);
	        g.setColor(Color.green);
	        g.drawRect(0,0,width, height);
	        //draw String
	        g.setColor(Color.green);
	        
	        g.setFont(new Font("one",Font.CENTER_BASELINE,15));
	        g.drawString(stringBuffer,50,50);
	        g.drawLine(50,60,width-50,60);
	        
	        fileListStart(g);
	        try {
	        g.setColor(new Color(10,100,10));
	        if(storePath.exists()) {
	        g.drawString(storePath.toString()+" (Save in this directory)", 10, 15);
	        }
	        g.setColor(Color.green);
	        g.drawLine(50,height-35,width-100,height-35);	
	        g.drawString(new File(clickedContent).getParent().toString(),50,height-16);
	        repaint();
	        }catch(NullPointerException e) {
	        }
	        repaint();
	    }
	    
}
