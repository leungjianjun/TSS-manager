package nju.software.tss.util;

import org.eclipse.swt.graphics.Image;

import nju.software.tss.gui.Main;
import nju.software.tss.resources.Resources;

public class Log {
	
	static Log log = new Log();
	
	private Log(){}
	
	public static Log instance(){
		return log;
	}
	
	public void info(String message){
		if(Main.tssWindow!=null && Main.tssWindow.getStatusLineManager()!=null)
			Main.tssWindow.getStatusLineManager().setMessage(Resources.getImage(Resources.SUCCESS),message);
	}
	
	public void info(Image image,String message){
		if(Main.tssWindow.getStatusLineManager()!=null)
			Main.tssWindow.getStatusLineManager().setMessage(image,message);
	}
	
	public void warn(String message){
		System.out.println("warn:"+message);
	}
	
	public void error(String message){
		System.out.println("erro:"+message);
	}

}
