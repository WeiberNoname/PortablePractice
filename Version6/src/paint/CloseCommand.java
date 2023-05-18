package paint;
import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.SwingUtilities;

public class CloseCommand extends TheWorld{
	
	public CloseCommand() {
	}
	
	public void commandFilter(String command) {
		if(command.indexOf("remove ")>-1&&command.length()>7) {
			command = command.substring(7);
			removeComponent(command);
		}else if(command.indexOf("add ")>-1&&command.length()>4) {
			command = command.substring(4);
			addComponent(command);
		}else if(command.indexOf("reset ")>-1&&command.length()>6) {
			command = command.substring(6);
			resetLayout(command);
		}
	}
	private void resetLayout(String command) {
		int x , y;
		if(command.indexOf("(")>-1&&command.indexOf(")")>-1) {
			command = command.substring(command.indexOf("(")+1);
			if(command.indexOf(",")>-1) {
				try {
					x = Integer.parseInt(command.substring(0,command.indexOf(",")));
					y = Integer.parseInt(command.substring(command.indexOf(",")+1,command.indexOf(")")));
					paintPanel.setLayout(new GridLayout(x,y));
					paintPanel.revalidate();
					paintPanel.repaint();
				}catch(NumberFormatException e) {
				}
			}
		}
	}
	
	public void removeComponent(String type) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				switch(type) {
				case"DP":
					paintPanel.remove(DP);
					break;
				case"DA":
					paintPanel.remove(DA);
					break;
				case"guide":
					paintPanel.remove(DG);
					break;
				case"code":
					paintPanel.remove(CFG);
					break;
				case"file":
					paintPanel.remove(FM);
					break;
				case"kernel":
					paintPanel.remove(KM);
					break;
				case"image":
					paintPanel.remove(IE1);
					break;
				case"translate":
					paintPanel.remove(CE);
					break;
				}
				paintPanel.revalidate();
				paintPanel.repaint();
			}
		});
		
	}
	public void addComponent(String command) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				switch(command) {
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


