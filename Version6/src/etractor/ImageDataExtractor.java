package etractor;

import java.awt.Color;
import java.util.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import javax.imageio.ImageIO;
import DataManager.*;

public class ImageDataExtractor{
	private DataInteraction1 DI1 = new DataInteraction1();
	private ExtendsInteraction1 EI1 = new ExtendsInteraction1();
	private File fileStore = new File("");
	
	private static Color[][] colorArray;
  
	public ImageDataExtractor() {
	  
  }
  public void imageExtractorBinary(ArrayList<File> fileList) throws IOException {
	  fileStore = EI1.getfileStoreLocation();
	  if(fileStore.exists()) {
		  int size = fileList.size();
		  if(size != 0) {
		  for(int i = 0 ; i <size; i++) {
			  Runnable task = new ImageBinary(fileList.get(i),fileStore,fileList.get(i).getName(),size);
			  Thread thread = new Thread(task);
			  thread.start();
		  }
		 }else if(size == 0){
			DI1.errorInfoAndSolution("fileListNull");
		 }
	  }
  }
  public void imageDecoderBinary(ArrayList<File> fileList) throws IOException {
	  fileStore = EI1.getfileStoreLocation();
	  if(fileStore.exists()) {
		  int size = fileList.size();
		  if(size != 0) {
		  for(int i = 0 ; i <size; i++) {
			  Runnable task = new Decoder(fileList.get(i),fileStore,fileList.get(i).getName(),size);
			  Thread thread = new Thread(task);
			  thread.start();
		  }
		 }else if(size == 0){
			DI1.errorInfoAndSolution("fileListNull");
		 }
	  }
  }
  public void setImageRGBArray(int sizeX,int sizeY,Color[][] c) {
	  colorArray = new Color[sizeX][sizeY];
  }
}



