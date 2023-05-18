package code;

import paint.*;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class CodeExplainer extends JPanel{
	  public static String string = "",dot = "",character = "{}[]:;1234567890*+-/@#$%^&*()_=!~`x.?><.,\"\"\'\'\\|\\/";
	  private CodeTranslator CT = new CodeTranslator();
	   public CodeExplainer() {
		  setLayout(new GridLayout(1,1));
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				add(CT);
			}
		});
		
		MouseListenerClass MLC = new MouseListenerClass();
		KeyListenerClass KLC = new KeyListenerClass();
		
		CT.addKeyListener(KLC);
		CT.addMouseListener(MLC);
		
	  }
	   
	   
}





/*Font change able and even in different line>> and use cursor color indicating to different type*/
