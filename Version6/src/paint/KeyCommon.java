package paint;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyCommon  {
public KeyCommon() {
	
}
public String keyCheck(KeyEvent e,String stringBuffer) {
	int KeyCode = e.getKeyCode();
	char KeyChar = e.getKeyChar();

	if(KeyCode == KeyEvent.VK_BACK_SPACE&&stringBuffer.length()>0) {
		stringBuffer = stringBuffer.substring(0,stringBuffer.length()-1);
	}else if(KeyCode<= KeyEvent.VK_Z&&KeyCode>= KeyEvent.VK_A){
		stringBuffer += (e.getKeyChar());
	}else if(KeyCode == KeyEvent.VK_SPACE) {
		stringBuffer += (e.getKeyChar());
	}else if(KeyChar <= '9'&&KeyChar >= '0') {
		stringBuffer += (e.getKeyChar());
	}else if(KeyCode != KeyEvent.VK_ENTER&&KeyCode != KeyEvent.VK_CONTROL&&KeyCode != KeyEvent.VK_UP&&KeyCode != KeyEvent.VK_DOWN&&KeyCode != KeyEvent.VK_LEFT&&KeyCode != KeyEvent.VK_RIGHT&&KeyCode != KeyEvent.VK_SHIFT){
		stringBuffer += (e.getKeyChar());
	}
	return stringBuffer;
}

}
