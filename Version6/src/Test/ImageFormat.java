package Test;

import java.io.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

public class ImageFormat {

  // Define the magic number for the custom format
  private static final int MAGIC_NUMBER = 0xCAFECAFE;

  // Define the version number for the custom format
  private static final int VERSION_NUMBER = 1;

  // Define the length of the custom header in bytes
  private static final int HEADER_LENGTH = 16; // 4 bytes for magic number, 4 bytes for version number, and 8 bytes for additional metadata

  // Write the header of the custom file format
  public static void writeHeader(FileOutputStream fos, long fileSize, String metadata) throws IOException {
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
    
    BufferedImage image1 = ImageIO.read(new File("D:\\MyLife\\kkjbkjsbdckj.png"));
    writeImageData(fos,image1);
    
  }

  // Write the image data to the custom file format
  public static void writeImageData(FileOutputStream fos, BufferedImage image) throws IOException {
    // Create a DataOutputStream for writing the image data
    DataOutputStream dos = new DataOutputStream(fos);

    // Get the image width and height
    int width = image.getWidth();
    int height = image.getHeight();

    // Write the image width and height to the output stream
    dos.writeInt(width);
    dos.writeInt(height);

    // Write the image data to the output stream
    for (int y = 0; y < height; y++) {
      for (int x = 0; x < width; x++) {
        int pixel = image.getRGB(x, y);
        dos.writeInt(pixel);
      }
    }

    // Flush the data to the output stream
    dos.flush();
  }

  // Read the image data from the custom file format and create a BufferedImage object
  public static BufferedImage readImageData(FileInputStream fis) throws IOException {
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
    System.out.println(fileSize+"f");

    // Read the metadata length from the header
    int metadataLength = dis.readInt();
    System.out.println(metadataLength+"f");
    
    
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
    for (int y = 0; y < height; y++) {
    	for (int x = 0; x < width; x++) {
    	int pixel = dis.readInt();
    	image.setRGB(x, y, pixel);
    	}
    	}

    	// Close the input stream
    	dis.close();

    	// Return the BufferedImage object
    	return image;
    	}

    	public static void main(String[] args) throws IOException {
    	// Create a BufferedImage object from the input file
    	FileOutputStream o = new FileOutputStream("D:\\MyLife\\kom.bin");
    	writeHeader(o,10,"Coding");	
    		
    	FileInputStream fis = new FileInputStream("D:\\MyLife\\kom.bin");
    	BufferedImage image = readImageData(fis);

    	// Write the BufferedImage object to an output file
    	File outputFile = new File("D:\\MyLife\\newimage2.png");
    	ImageIO.write(image, "png", outputFile);
    	}
}
