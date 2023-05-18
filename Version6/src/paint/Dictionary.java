package paint;
import java.util.ArrayList;

import javax.swing.SwingUtilities;
public class Dictionary {
	private ArrayList<String> detail = new ArrayList<String>();
	private String[] typeList = new String[9];
	public Dictionary() {
		typeList[3] = "DP";
		typeList[2] = "DA";
		typeList[1] = "guide";
		typeList[0] = "Principle";
		typeList[4] = "code";
		typeList[5] = "file";
		typeList[6] = "kernel";
		typeList[7] = "image";
		typeList[8] = "translate";
	}
	
	public ArrayList<String> dictionary(int fontMetrics, int width){
//use String and for to paint more
		//depend on the type List
		for(int o = 0; o < typeList.length;o++) {
		String content;
		int sampledIndex = width / fontMetrics;
		int times;
		//typeList
			content = contentProvider(typeList[o]);
			times = content.length()/sampledIndex+1;
			
			for(int i = 0 ; i < times;i++) {
				if(content.length() > 0) {
					if(content.length()>sampledIndex) {
						detail.add(content.substring(0,sampledIndex));
						content = content.substring(sampledIndex);
					}else {
						detail.add(content.substring(0,content.length()-1));
						content = content.substring(content.length()-1);	
					}
				}
			}
		}
		return detail;
	}
	private String contentProvider(String type) {
		switch(type) {
		case"Principle":	
			detail.add(" ");
			detail.add("Rules : "+"("+type+")!");
			detail.add("General Regulation!");
			detail.add("ctrl + G >> Display the guidelines!");
			detail.add("ctrl + Q >> Escape and close the API!");
			detail.add("");
			detail.add("Close Command : General Rules!");
			detail.add("remove + API abbreviation >> remove a component!");
			detail.add("add + API abbreviation >> Insert a component!");
			detail.add("reset +  +(x,y) >> Reset layout [x(row),y(column)]!");
			detail.add("close >> Close the window!");
			detail.add("clean + file/directory >> Clear the file or directory copy buffer!");
			
			detail.add("Up Left Right Down!");
			
			type = "";
			break;
		case"DP":	
			detail.add(" ");
			detail.add("DrawPanel "+"("+type+")");
			type = "Draw Panel : This API provides the user to use this function for basic extraction of inverse numbers for kernel size scanning or convolutional algorithm executing.";
			detail.add("[ctrl + F >> Focusable(Use with DA).]");
			generalRule();
			break;
		case"DA":
			detail.add(" ");
			detail.add("DataAnalyze "+"("+type+")");
			type = "Data Analyze : This is a function to check the details of an image and is focusable in a specific location and downloadable into an image format that could be portable and used in research documentation.";
			generalRule();
			break;
		case"guide":
			detail.add(" ");
			detail.add("DescriptionGuide "+"("+type+")");
			type = "Description Guide : Form of an introduction for the user to click the button and guide to using the command line for further understanding of the AI laboratory, and  in this guideline would prefer to prompt the function and even how to use the command in different API.";
			break;
		case"code":
			detail.add(" ");
			detail.add("CodeFirstGeneration "+"("+type+")");
			detail.add("[ctrl + N >> Change the location and renew]");
			type = "Code First Generation : This area provides a testing function for users to form an experiment, and the reason this API is for training a model or even performing an innovation algorithm laboratory, which means it at least manufactures a data calculation and visualizes in a versatile function, such as kernel practice and neural networks executing.";
			break;
		case"file":
			detail.add(" ");
			detail.add("FileManagement "+"("+type+")");
			detail.add("[default 0 >> Set default location and interrupt parallel location path.]");
			detail.add("[ctrl + C >> Copy content.]");
			detail.add("[ctrl + Z >> Go back]");
			detail.add("[ctrl + N >> Change the location and renew]");
			detail.add("[ctrl + V >> Paste the text content]");
			detail.add("[move + Path with name >> Change default storage]");
			detail.add("[mkdir + Path with name >> Used to create a folder to store the data]");
			type = "File Manager : Including create a native folder for users to handle data and work separately, and even supporting the user to save images that are created by users through the software process in this API. ";
			break;
		case"kernel":
			detail.add(" ");
			detail.add("KernelModel "+"("+type+")");
			detail.add("[ctrl + N >> Change the location and renew]");
			detail.add("[set kernel + number >> Set kernel size]");
			detail.add("[open/off + buffer >> Open and close copy buffer]");
			type = "Kernel Model Generator : This feature provides the user to create a specific algorithm model, and that manufactured by the user, and use a general format that is capable for the user to generate it without incompatible potential effect. ";
			break;
		case"image":
			detail.add(" ");
			detail.add("ImageExtractor1 "+"("+type+")");
			detail.add("[r/g/b + set >> Add additional calculation for transformations]");
			detail.add("[decode >> Transform each image back into image file]");
			detail.add("[extract all >> Transform each image into binary file]");
			type = "Image Extractor : This is an API that extracts image data into binary file, and provides a function that is used to invent a new file format that helps the user explore more options for storing a file. ";
			break;
		case"translate":
			detail.add(" ");
			detail.add("CodeExplainer "+"("+type+")");
			type = "Code Explainer:";
			break;
		}
		return type;
	}
	public void clear() {
		detail.clear();
	}
	private void generalRule() {
		detail.add("[ctrl + N >> Renew a new canvas.] ");
		detail.add("[ctrl + S >> Grid aid.]");
		detail.add("[ctrl + I >> Load an image.]");
		detail.add("[ctrl + L >> Without grid aid.]");
		detail.add("[ctrl + P >> Edit pixel.]");
		detail.add("[ctrl + V >> Paste the word.] ");
		detail.add("[ctrl + D >> Painting pencil.] ");
		detail.add("[set + naming + name >> naming.] ");
		detail.add("[set + pixel + number >> pixel number]");
		detail.add("[set + pixelsize + deviation >> start from 600]");
	}
}

 class AddAPI extends TheWorld{
	public AddAPI() {
		
	}
	public void addComponent(String command) {
		String c;
		if(command.indexOf("(")>-1) {
			command = command.substring(command.indexOf("(")+1,command.indexOf(")"));
		}
		c = command;
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				switch(c) {
				case"DA":
					paintPanel.add(DA);
					break;
				case"DP":
					paintPanel.add(DP);
					break;
				case"guide":
					paintPanel.add(DG);
					break;
				case"code":
					paintPanel.add(CFG);
					break;
				case"file":
					paintPanel.add(FM);
					break;
				case"kernel":
					paintPanel.add(KM);
					break;
				case"image":
					paintPanel.add(IE1);
					break;
				case"translate":
					paintPanel.add(CE);
					break;
				}
				
				paintPanel.revalidate();
				paintPanel.repaint();
			}
		});
	}
}

