package etractor;
import java.applet.*;
import java.awt.event.KeyEvent;
import java.io.*;
import java.util.*;


public class Key1 {
	
public Key1() {
	
}
public String keyListener_Char(KeyEvent e,String command) {
	char keyChar = e.getKeyChar();
	if(Character.isLetterOrDigit(keyChar)) {
		command += keyChar;
	}else if(keyChar == KeyEvent.VK_SPACE) {
		command += keyChar;
	}
	return command;
}

}
