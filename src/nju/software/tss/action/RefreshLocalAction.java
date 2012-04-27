package nju.software.tss.action;

import nju.software.tss.gui.Main;
import nju.software.tss.resources.Resources;

import org.eclipse.jface.action.Action;

public class RefreshLocalAction extends Action{
	
	public RefreshLocalAction(){
		this.setText("&刷新@Ctrl+R");
        this.setImageDescriptor(Resources.getImageDescriptor(Resources.REFRESH_LOGO));
	}
	
	public void run() {
		Main.tssWindow.sidebar.refreshLocal();
	}

}
