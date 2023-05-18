package DataManager;
import java.awt.Color;
import paint.*;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.io.File;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import paint.TheWorld;
public class DataInteraction1 extends TheWorld{
	
	public static File storePathLocation = new File("");
	public static String storePathType = "",detail_Type = "";
	public static String copyContent = "";
	
	public static int percentageForBinary = 0;
	private static int totalSize = 1;
	private static int sum = 1;
	
	public static ArrayList<File> directoryList = new ArrayList<File>();
	public static ArrayList<File> fileList = new ArrayList<File>();
	public static ArrayList<String> copyContents = new ArrayList<String>();
	private CloseCommand CC = new CloseCommand();
	
	private String appName="Frod Noname AI Laboratory";
	private Color colorDefault = new Color(20,20,20);
	
	public DataInteraction1() {
		close.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String command = close.getText();
				command1(command);
			}
		});
		close.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				int keyCode = e.getKeyCode();
				if(keyCode == KeyEvent.VK_C&&e.isControlDown()) {
					close.setText(copyContent);
				}
			}
		});
	}
	public void changeTitle() {
		labelName.setText(storePathType+" >> Connect to global: "+detail_Type);
		frameTool.getContentPane().setBackground(new Color(10,100,10));
		close.setBackground(new Color(10,100,10));
		close.setBorder(BorderFactory.createLineBorder((new Color(10,100,10))));
	}

	public void changeTitleEnd(String end,int size) {
		totalSize -=size;
		sum -= size;
		System.out.println("Size: "+size);
		
		if(sum <=0 ||totalSize <=0) {
			sum = 1;
			totalSize = 1;
		}
		labelName.setText(end);
		frameTool.getContentPane().setBackground(new Color(10,100,10));
		close.setBackground(new Color(10,100,10));
		close.setBorder(BorderFactory.createLineBorder((new Color(10,100,10))));
	}
	
	public void sumSet(int s) {
		sum += s;
	}
	public void totalSizeSet(int t) {
		totalSize += t;
		System.out.println("totalSize"+totalSize);
	}
	
	public void changeTitle1(String purpose) {
		percentageForBinary = (sum*100)/totalSize;
		int percentage = percentageForBinary/3;
		String graphic = "";
		for(int i = 0 ; i < percentage;i++) {
			graphic += "|";
		}
		labelName.setText(purpose+" "+graphic);
		frameTool.getContentPane().setBackground(new Color(10,10,120));
		close.setBackground(new Color(10,10,120));
		close.setBorder(BorderFactory.createLineBorder((new Color(10,10,120))));
	}
	
	public void errorInfoAndSolution(String type) {
		String purpose = "";
		switch(type) {
		case"connection":
			//add file for solution
			CC.addComponent("file");
			purpose = "Store path connection exception";
			break;
		case"fileListNull":
			CC.addComponent("file");
			purpose = "NullPointerException : Please copy image file";
			break;
		}
		labelName.setText(purpose);
		frameTool.getContentPane().setBackground(new Color(130,10,10));
		close.setBackground(new Color(130,10,10));
		close.setBorder(BorderFactory.createLineBorder((new Color(130,10,10))));
	}
	
	public void recoveryTitle() {
		storePathLocation = new File("");
		labelName.setText(appName);
		close.setBackground(colorDefault);
		close.setBorder(BorderFactory.createLineBorder((new Color(20,20,20))));
		frameTool.getContentPane().setBackground(colorDefault);
	}
	//syntax tree
	private void command1(String command) {
		if(command.indexOf("clean ")>-1&&command.length()>6) {
			command = command.substring(6);
			cleanFileOrDirectoryList(command);
		}
	}
	public boolean connectionTest() {
		if(storePathLocation.exists()) {
			return true;
		}
		return false;
	}
	private void cleanFileOrDirectoryList(String type) {
		switch(type) {
		case"file":
			fileList.clear();
			break;
		case"directory":
			directoryList.clear();
			break;
		case"copy":
			copyContents.clear();
			break;
		}
	}
	
}
