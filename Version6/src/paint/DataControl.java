package paint;

import java.awt.Point;
import java.util.ArrayList;

public class DataControl extends DrawPanel{
	private String commandType = "";
	private ArrayList<Integer> integer1 = new ArrayList<>();
	private int kernelWidth = 0,kernelValue;


	public DataControl() {
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
    	}
    }
    //set XXX XXX.....etc
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
}


class Get extends TheWorld{
public Get() {
}
public int dataGetInt(String type) {
	updateInfo();
	switch(type) {
	case"pixelSize0":
		return  pixelSize0;
	case"pixel0":
		return pixel0;
	case"pixelNumber0":
		return pixelNumber0;
	case"mouseX":
		return mouseX;
	case"mouseY":
		return mouseY;
	}
	return 1;

}
public ArrayList<Point> pointUpdate(String type){
	switch(type) {
	case"point10":
		return point10;
	}
	return null;
}
public boolean booleanGet(String type) {
	switch(type) {
	case"focusKernel":
		return focusKernel;
	}
	return false;
}
} 

