package nju.software.tss.action;

import nju.software.tss.gui.Main;
import nju.software.tss.resources.Resources;

import org.eclipse.jface.action.Action;
import org.eclipse.swt.graphics.Point;

public class MiniChangeAction extends Action{
	
	Point oldSize;
	
	public MiniChangeAction(){
		super();
		this.setText("&迷你模式@Ctrl+T");
        this.setImageDescriptor(Resources.getImageDescriptor(Resources.MINI_LOGO));
	}
	
	public void run() {
		if(Main.tssWindow.folder.getVisible()){
			Main.tssWindow.folder.setVisible(false);
			oldSize = Main.tssWindow.getShell().getSize();
			Main.tssWindow.getShell().setSize(Main.tssWindow.sidebar.tabFolder.getSize());
		}else{
			Main.tssWindow.folder.setVisible(true);
			Main.tssWindow.getShell().setSize(oldSize);
		}
	}
}
