package code;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyListenerClass implements KeyListener{
	private CodeTranslator CT = new CodeTranslator();
	private CodeCenterStyle CC1 = new CodeCenterStyle();
	
	private int end = 0,start = 0 ; 
	
	public void keyTyped(KeyEvent e) {
	}
	public void keyPressed(KeyEvent e) {
		int keyCode = e.getKeyCode();
		if(keyCode == KeyEvent.VK_ENTER) {
		if(CT.stringListRow == CT.maxLine) {
			}
		}
		if(e.isShiftDown()) {
			
			if(keyCode == KeyEvent.VK_RIGHT) {
			}
		}
	}
	public void keyReleased(KeyEvent e) {	
	}
}
