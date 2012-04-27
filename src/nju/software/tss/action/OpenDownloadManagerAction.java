package nju.software.tss.action;

import nju.software.tss.gui.DownloadManager;
import nju.software.tss.resources.Resources;

import org.eclipse.jface.action.Action;


public class OpenDownloadManagerAction extends Action{
	
	
	public OpenDownloadManagerAction(){
		this.setText("&下载管理@Ctrl+I");
        this.setImageDescriptor(Resources.getImageDescriptor(Resources.DOWNLOAD_MANAGER));
	}
	
	public void run(){
		DownloadManager.open();
	}

}
