package paint;
import java.awt.image.BufferedImage;
import java.io.*;

import javax.imageio.ImageIO;
public class FileControl extends TheWorld{
	private SaveFile s = new SaveFile();
	public FileControl() {
		
	}
	public String commandFilter(String command) {
		if(command.length()>1) {
			if(command.indexOf("mkdir ")>-1&&command.length()>6) {
				command = command.substring(7);
				command = mkdir(command);
			}else if(command.indexOf("move ")>-1&&command.length()>5) {
				command = command.substring(6);
			}
		}
		s.setStorePath(command);
		return command; 
	}
	
	
	private String mkdir(String command) {
		File file = new File(command);
		if(!file.exists()) {
			
			 boolean tf = file.mkdirs();
			 if(tf) {
				 storePath = file;
			 }
		}
		return command;
	}
	
}
class SaveFile extends TheWorld{
	public SaveFile() {
		
	}
	public void setStorePath(String command) {
		File f =  new File(command);
		if(f.exists()) {
			storePath = f;
		}
	}
	public boolean saveImage(String name,BufferedImage image) {
		  File file = new File(storePath+"\\"+name+".png");
		  boolean TF = false;
		if(storePath.exists()&&!file.exists()) {
		        try {
		            ImageIO.write(image, "png", file);
		            TF = true;
		        } catch (IOException e) {
		        }
		    }
		return TF;
		}
	
	
	}
