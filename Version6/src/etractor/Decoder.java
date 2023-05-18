package etractor;

import java.awt.image.BufferedImage;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.imageio.ImageIO;

import DataManager.DataInteraction1;

class Decoder implements Runnable{
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

	public Decoder(File file,File p,String n,int t) throws IOException {
		fileLocation = file;
		parent = p;
		if(n.lastIndexOf(".")>-1) {
			name = n.substring(0,n.indexOf("."));
		}else {
			name = n;
		}
	    taskNumber = t;
		
	}
	 private BufferedImage readImageData(FileInputStream fis) throws IOException {
		    // Create a DataInputStream for reading the custom file format
		    DataInputStream dis = new DataInputStream(fis);

		    // Read the magic number from the header and verify that it matches the expected value
		    int magicNumber = dis.readInt();
		    if (magicNumber != MAGIC_NUMBER) {
		    	return null;
		      //throw new IOException("Invalid magic number");
		    }

		    // Read the version number from the header and verify that it matches the expected value
		    int versionNumber = dis.readInt();
		    if (versionNumber != VERSION_NUMBER) {
		    	return null;
		    //	throw new IOException("Unsupported version number");
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
		    int size_All = width * height;
		    DI1.totalSizeSet(size_All);
		    int sum  = 0;
		    for (int y = 0; y < height; y++) {
		    	for (int x = 0; x < width; x++) {
				    DI1.sumSet(1);
					DI1.changeTitle1("Binary Transformation");
		    		int pixel = dis.readInt();
		    		image.setRGB(x, y, pixel);
		    	}
		    	}
			DI1.changeTitleEnd("Finish extraction : "+ taskNumber,size_All);

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
	
}

