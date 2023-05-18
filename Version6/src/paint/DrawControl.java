package paint;
import java.util.ArrayList;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;

import java.awt.Color;
import java.io.File;
import java.io.IOException;

public class DrawControl extends DrawPanel{
	private String commandType = "";
	public DrawControl() {
	}
	 public void getClipboardText() {
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
//part 1
    public void commandLine(String command) {
    	//first layer

    	if(command.length()>4&&command.indexOf("set ")>-1) {
    		command = command.substring(4);
    		setType(command);
    	}else if(command.indexOf("kernel ")>-1&& command.length() > 7) {
    		command = command.substring(7);
    		kernelType(command);
    	}else if(command.indexOf("import ")>-1&&command.length() > 7) {
    		command = command.substring(7);
    		importSet(command);
    	}
    }
    //set XXX XXX.....etc
    public void importSet(String command) {
    	if(command.indexOf("image ")>-1&&command.length()>6) {
    		command = command.substring(6);
    	}
    }
    
    
    
    
    
    public void kernelType(String command) {
    	if(command.indexOf("model [")>-1&&command.indexOf("]")>-1&&command.length()>8) {
    	}
    }

    public void setType(String command) {
    	if(command.length()>6&&command.indexOf("pixel ")>-1) {
    		command = command.substring(6);
    		setPixel(command);
    	}else if(command.length()>5&&command.indexOf("name ")>-1) {
    		command =command.substring(5);
    		setName(command);
    	}else if(command.indexOf("pixelsize ")>-1&&command.length()>10) {
    		command = command.substring(10);
    		setPixelSize(command);
    	}else if(command.indexOf("pixelsizen ")>-1&&command.length()>11) {
    		command = command.substring(11);
    		setPixelSizeN(command);
    	}
    }
    //start
    public void dividedKernelData(String data) {
    }
    public void setPixelSizeN(String size) {
    	try {
    		int s = (int)Double.parseDouble(size);
    		deviation = -s;
    		if(deviation <=-500) {
    		deviation = -500;
    		}
    	}catch(NumberFormatException e) {
    	}

    }
    
    
    public void setPixelSize(String size) {
    	try {
    		deviation = Integer.parseInt(size);
    		if(deviation <=-500) {
        		deviation = -500;
        		}
    	}catch(NumberFormatException e) {
    	}
    }
    public int getDeviation() {
    	return deviation;
    }
    @Override
	public void setName(String i) {
    	name = i;
    }
    @Override
	public String getName() {
    	return name;
    }

    public void setPixel(String i) {
    	try{
    		pixel = Integer.parseInt(i);
    	}catch(NumberFormatException e) {
    	}
    }
    public int getPixel() {
    	return pixel;
    }
//part2
}

