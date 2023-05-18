package Test;

import java.awt.Color;
import java.util.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import javax.imageio.ImageIO;
import DataManager.*;

/*public class ImageFileFormatTest {
	private DataInteraction1 DI1 = new DataInteraction1();
	private ExtendsInteraction1 EI1 = new ExtendsInteraction1();
	private File fileStore = new File("");
  public ImageFileFormatTest() {
	  
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
}
class ImageBinary1 implements Runnable{
	private DataInteraction1 DI1 = new DataInteraction1();
	private File fileLocation = new File(""),parent = new File("");
	private String name = "";
	private int taskNumber = 0;
	
	 // Define the magic number for the custom format
	  private static final int MAGIC_NUMBER = 0xCAFECAFE;

	  // Define the version number for the custom format
	  private static final int VERSION_NUMBER = 1;

	  // Define the length of the custom header in bytes
	  private static final int HEADER_LENGTH = 16; // 4 bytes for magic number, 4 bytes for version number, and 8 bytes for additional metadata

	  // Write the header of the custom file format
	  private void writeHeader(File file,File f, long fileSize, String metadata) throws IOException {
	    fileSize = file.length();
	    
	    FileOutputStream fos = new FileOutputStream(f);
	    
		// Create a DataOutputStream for writing the header fields
	    DataOutputStream dos = new DataOutputStream(fos);

	    // Write the magic number to the header
	    dos.writeInt(MAGIC_NUMBER);

	    // Write the version number to the header
	    dos.writeInt(VERSION_NUMBER);

	    // Write the file size to the header
	    dos.writeLong(fileSize);

	    // Write the metadata to the header
	    byte[] metadataBytes = metadata.getBytes("UTF-8");
	    int metadataLength = metadataBytes.length;
	    dos.writeInt(metadataLength);
	    dos.write(metadataBytes);

	    // Flush the data to the output stream
	    dos.flush();
	    BufferedImage image = ImageIO.read(fileLocation);
	    writeImageData(fos,image);
	  }
	   private void writeImageData(FileOutputStream fos, BufferedImage image) throws IOException {
		    // Create a DataOutputStream for writing the image data
		    DataOutputStream dos = new DataOutputStream(fos);

		    // Get the image width and height
		    if(image != null){
		    int width = image.getWidth();
		    int height = image.getHeight();

		    // Write the image width and height to the output stream
		    dos.writeInt(width);
		    dos.writeInt(height);

		    // Write the image data to the output stream
		    int sum = 0;
		    int size = ((width * height)/100);
		    for (int y = 0; y < height; y++) {
		      for (int x = 0; x < width; x++) {
		    	sum += 1;
		    	int percentage = sum/size;
		    	DI1.changeTitle1("Binary Transformation",percentage );
		        int pixel = image.getRGB(x, y);
		        dos.writeInt(pixel);
		      }
		    }
		    DI1.changeTitleEnd("Finish extraction : "+ taskNumber);

		    // Flush the data to the output stream
		    dos.flush();
		    }
		  }
	
	public ImageBinary1(File file,File p,String n,int t) throws IOException {
		fileLocation = file;
		parent = p;
		name = n;
		taskNumber = t;
	}
	public void run() {
		
		File checkFile = new File(parent+"\\"+name+"Binary.bin");

		int v = 0;
		do {
			checkFile = new File(parent+"\\"+name+v+"Binary.bin");
			v+=1;
		}while(checkFile.exists());

		try {
			writeHeader(fileLocation,checkFile,0,"ImageBinary");
		}catch(IOException e) {
		}
		
	}
}


class DecoderEditing implements Runnable{
	private DataInteraction1 DI1 = new DataInteraction1();
	private File fileLocation = new File(""),parent = new File("");
	private String name = "";
	private int taskNumber = 0;
	 // Define the magic number for the custom format
	  private static final int MAGIC_NUMBER = 0xCAFECAFE;

	  // Define the version number for the custom format
	  private static final int VERSION_NUMBER = 1;

	  // Define the length of the custom header in bytes
	  private static final int HEADER_LENGTH = 16; // 4 bytes for magic number, 4 bytes for version number, and 8 bytes for additional metadata

	public DecoderEditing(File file,File p,String n,int t) throws IOException {
		fileLocation = file;
		parent = p;
		name = n;
	    taskNumber = t;
		
	}
	 private BufferedImage readImageData(FileInputStream fis) throws IOException {
		    // Create a DataInputStream for reading the custom file format
		    DataInputStream dis = new DataInputStream(fis);

		    // Read the magic number from the header and verify that it matches the expected value
		    int magicNumber = dis.readInt();
		    if (magicNumber != MAGIC_NUMBER) {
		      throw new IOException("Invalid magic number");
		    }

		    // Read the version number from the header and verify that it matches the expected value
		    int versionNumber = dis.readInt();
		    if (versionNumber != VERSION_NUMBER) {
		      throw new IOException("Unsupported version number");
		    }

		    // Read the file size from the header
		    long fileSize = dis.readLong();

		    // Read the metadata length from the header
		    int metadataLength = dis.readInt();
		    
		    
		    // Read the metadata from the header
		    byte[] metadataBytes = new byte[metadataLength];
		    dis.readFully(metadataBytes);
		    String metadata = new String(metadataBytes, "UTF-8");

		    // Read the image width and height from the input stream
		    int width = dis.readInt();
		    int height = dis.readInt();

		    // Create a BufferedImage object with the specified width and height
		    BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

		    // Read the image data from the input stream
		    int size = ((width*height)/100);
		    int sum  = 0;
		    for (int y = 0; y < height; y++) {
		    	for (int x = 0; x < width; x++) {
		    		sum += 1;
					DI1.changeTitle1("Binary Transformation", sum/size);
		    		int pixel = dis.readInt();
		    		image.setRGB(x, y, pixel);
		    	}
		    	}
			DI1.changeTitleEnd("Finish extraction : "+ taskNumber);

		    	// Close the input stream
		    	dis.close();

		    	// Return the BufferedImage object
		    	return image;
		    	}
	 
	public void run() {
	        if(fileLocation.getAbsoluteFile().toString().indexOf(".bin")>-1) {
		BufferedImage outputImage = null;
		try {
			
			FileInputStream inputFile = new FileInputStream(fileLocation);
			outputImage = readImageData(inputFile);
		}catch(IOException e) {
		}
		
		File checkFile = new File(parent+"\\"+name+"Image.png");
		int v = 0;
		do {
			checkFile = new File(parent+"\\"+name+v+"Image.png");
			v+=1;
		}while(checkFile.exists());
		
		if(!checkFile.exists()&&outputImage != null) {
		try (OutputStream out = new FileOutputStream(checkFile)){
			ImageIO.write(outputImage, "png", checkFile);
		} catch (IOException e) {
		}
		}
	        }
	}
	
}*/
