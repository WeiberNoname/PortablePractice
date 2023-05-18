package paint;
import java.awt.Color;
import etractor.*;
import code.*;
import DataManager.DataInteraction1;
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

public class TheWorld{
	
	//public static JTextField field1 = new JTextField(),textFieldCommand = new JTextField();
	//ActionListener MouseListener
	public static String listPassword = "",userName,userPassword;
	private static String inputString = "";
	public static JFrame frame = new JFrame(),frameTool = new JFrame();
	public static JTextField close = new JTextField("Search for words");
	public static JPanel  panelMain = new JPanel(),paintPanel = new JPanel();
	public static JLabel labelName = new JLabel("Frod Noname AI Laboratory");
	public static int locationX = 0,locationY = 0;
	public static boolean TF = false,focusKernel;
	public static byte number1 = 5;
	//special
	public static Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();

	public int pixelSize0 = 0,pixel0,pixelNumber0,mouseX,mouseY;
	public ArrayList<Point> point10 = new ArrayList<>();
	public static DrawPanel DP = new DrawPanel();
	public static DataAnalyze DA = new DataAnalyze();
	public static DescriptionGuide DG = new DescriptionGuide();
	private static CloseCommand CC = new CloseCommand();
	public static CodeFirstGeneration CFG = new CodeFirstGeneration();
	public static FileManagement FM = new FileManagement();
	public static KernelModel KM = new KernelModel();
	public static ImageExtractor1 IE1 = new ImageExtractor1();
	private static DataInteraction1 DI1 = new DataInteraction1();
	public static CodeExplainer CE = new CodeExplainer();
	
	public static File storePath = new File("");
	//public int pixelSize0 = 0,pixel0,pixelNumber0,mouseX,mouseY;

private static void frameTop(JFrame f) {
	frameTool.setAlwaysOnTop(true);
}

private static void labelSet(JLabel l) {
	l.setForeground(new Color(200,200,200));
	l.setFont(new Font("one",Font.PLAIN,10));
}

public static void main(String[] args) {
TheWorld1();
}

public static int frameSizeX = frame.getWidth(),frameSizeY = frame.getHeight();

public static void TheWorld1() {
	allFrameSetUp();

	panelMainSetUp();

	basisCalculation();

	labelNameSetUp();
	
	frameTopSetUp();

	paintPanelSetUp();

	frameToolSetUp();
	
	closeSetUp();


	try {
		Thread.sleep(1);
		frame.setVisible(true);
		frameTool.setVisible(true);
	}catch(InterruptedException e) {
	}

}

//All the method list
private static void allFrameSetUp() {
	int x = (int)dimension.getWidth();
	int y = (int)dimension.getHeight();
	frame.setUndecorated(true);
	frameTool.setUndecorated(true);

	frame.setLayout(null);
	frameTool.getContentPane().setBackground(new Color(20,20,20));

	frame.setLayout(new GridLayout(1,1));
	frame.setBackground(new Color(250,250,250,50));
	frameTool.setLayout(null);
	frame.pack();
	frame.setSize(800,600);
	frame.setLocation(x/2-(frame.getWidth()/2),y/2-(frame.getHeight()/2));
	frameTool.pack();
	frameTool.setSize(800,27);
	frameTool.setLocation(x/2-(frame.getWidth()/2),y/2-(frame.getHeight()/2)-27);
}

private static void panelMainSetUp() {
	panelMain.setLayout(new GridLayout(1,1));
	panelMain.setBackground(new Color(30,30,100,100));
	panelMain.setBounds(3,3,frame.getWidth()-6,500-30);
	frame.add(panelMain);
}

private static void basisCalculation() {
	frameSizeX = frame.getWidth();
	frameSizeY = frame.getHeight();
}

private static void labelNameSetUp() {
	labelSet(labelName);
	labelName.setBounds(5,2,230,20);
	frameTool.add(labelName);
}

private static void frameTopSetUp() {
	frameTop(frame);
	frameTop(frameTool);
}

private static void paintPanelSetUp() {
	paintPanel.addKeyListener(new KeyAdapter() {
		public void keyPressed(KeyEvent e) {
			paintPanel.requestFocusInWindow();
		}
	});
		panelMain.add(paintPanel);
		paintPanel.setLayout(new GridLayout(1,1));
}

private static void frameToolSetUp() {
	frameTool.addMouseMotionListener(new MouseMotionListener() {
		@Override
		public void mouseDragged(MouseEvent e) {
			int mouseX = (int)MouseInfo.getPointerInfo().getLocation().getX()-locationX;
			int mouseY = (int)MouseInfo.getPointerInfo().getLocation().getY()-locationY;
			frameTool.setLocation(mouseX,mouseY);
			frame.setLocation(mouseX,mouseY+25);
			panelResize();
		}
		@Override
		public void mouseMoved(MouseEvent e) {
		}
	});
	frameTool.addMouseListener(new MouseAdapter() {
		@Override
		public void mousePressed(MouseEvent e) {
			locationX = e.getX();
			locationY = e.getY();
			frame.setAlwaysOnTop(true);
		}
		});
}

private static void closeSetUp() {
	close.setFont(new Font("one",Font.CENTER_BASELINE,15));
	close.setBackground(new Color(20,20,20));
	close.setForeground(new Color(200,200,200));
	frameTool.add(close);
	close.addActionListener(new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			String command = close.getText();
			if(command.equals("close")) {
				System.exit(0);
			}
		}
	});
	close.addMouseListener(new MouseAdapter() {
		@Override
		public void mouseEntered(MouseEvent e) {
			close.requestFocusInWindow();
			close.setText("");
		}
		@Override
		public void mouseExited(MouseEvent e) {
			close.setText("Search for words");
		}
	});
	close.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			String command = close.getText();
			if(command.equals("close")) {
				System.exit(0);
			}else {
				CC.commandFilter(command);
			}
		}
	});
	
	close.addKeyListener(new KeyAdapter() {
		public void keyPressed(KeyEvent e) {
			int keyCode = e.getKeyCode();
			if(e.isControlDown()&&keyCode == KeyEvent.VK_G) {
				CC.addComponent("guide");
			}
		}
	});
	
	close.setBorder(BorderFactory.createLineBorder((new Color(20,20,20))));
	close.setBounds(frameTool.getWidth()-540,0,500,25);
}
private static void panelResize() {
	if(frameTool.getLocationOnScreen().getX() < 0&&frameTool.getLocationOnScreen().getY() < 0) {
	int framePX = frame.getWidth()-frameSizeX;
	int framePY = frame.getHeight()-frameSizeY;
	frameTool.setSize((int)dimension.getWidth(),27);
	frame.setSize((int)dimension.getWidth(),(int)dimension.getHeight()-27);
	close.setBounds(frameTool.getWidth()-540,0,500,25);
	frame.setLocation(0,27);
	frameTool.setLocation(0,0);
	}else {
	frame.setSize(800,600);
	frameTool.setSize(800,27);
	close.setBounds(frameTool.getWidth()-540,0,500,25);
	}
}
public void updateInfo() {
	//draw
	pixelSize0 = DP.deviation;
	pixelNumber0 = DP.pixelNumber;
	pixel0 = DP.pixel;
	point10 = DP.point1;
	mouseX = DP.mouseX;
	mouseY = DP.mouseY;
	focusKernel = DP.focusKernel;
	//data
	
}
public int updateInfo_Draw(String type) {
	switch(type) {
	case"pixel":
		return DA.pixel;
	case"pixelNumber":
		return DA.pixelNumber;
	case"deviation":
		return DA.deviation;
	}
	return -1;
}

}

