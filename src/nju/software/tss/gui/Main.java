package nju.software.tss.gui;

import java.io.IOException;

import nju.software.tss.resources.Resources;
import nju.software.tss.util.Config;
import nju.software.tss.util.Helper;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.jsoup.TSSClient;

public class Main {
	//已知问题，没有初始化shell的时候不能使用jfaceResources
	static {
		new Shell();
	}
	
	public static TSSWindow tssWindow;
	
	/**
	 * tssClient后台服务进程，获取网络相关的数据
	 */
	public static TSSClient tssClient;
	
	public static void main(String args[]){
		tssWindow = new TSSWindow();
		tssClient = new TSSClient();
		TSSWindow.setDefaultImage(Resources.getImage(Resources.LOGO));
		tssWindow.setBlockOnOpen(true);
		init();
		tssWindow.open();
		Display.getCurrent().dispose();
	}
	
	private static void init(){
		Display.getDefault().asyncExec(new Runnable(){

			@Override
			public void run() {
				//自动登录
				if(Config.preferenceStore().getBoolean(Config.AUTOLOGIN)){
					try {
						Main.tssClient.login(Config.preferenceStore().getString(Config.ACCOUNT), Helper.decode(Config.preferenceStore().getString(Config.PASSWORD)));
					} catch (IOException e) {
						
					}
				}
			}
			
		});
	}

}
