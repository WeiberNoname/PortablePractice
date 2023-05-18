package etractor;

import java.awt.image.BufferedImage;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

import DataManager.DataInteraction1;

class ImageBinary implements Runnable{
	private DataInteraction1 DI1 = new DataInteraction1();
	private File fileLocation = new File(""),parent = new File("");
	private String name = "";
	private int taskNumber = 0;
	
	private ImageDataExtractor IDE = new ImageDataExtractor();
	
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
		    int size_All = width *height;
		    DI1.totalSizeSet(size_All);
		    for (int y = 0; y < height; y++) {
		      for (int x = 0; x < width; x++) {
		    	DI1.sumSet(1);
		    	DI1.changeTitle1("Binary Transformation");
		        int pixel = image.getRGB(x, y);
		        dos.writeInt(pixel);
		      }
		    }
		    DI1.changeTitleEnd("Finish extraction : "+ taskNumber,size_All);

		    // Flush the data to the output stream
		    dos.flush();
		    }
		  }
	
	public ImageBinary(File file,File p,String n,int t) throws IOException {
		fileLocation = file;
		parent = p;
		if(n.lastIndexOf(".")>-1) {
			name = n.substring(0,n.indexOf("."));
		}else {
			name = n;
		}
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

