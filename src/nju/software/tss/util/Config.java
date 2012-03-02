package nju.software.tss.util;

import java.io.IOException;

import nju.software.tss.wizard.setup.SetupWizard;

import org.eclipse.jface.dialogs.IPageChangedListener;
import org.eclipse.jface.dialogs.PageChangedEvent;
import org.eclipse.jface.preference.PreferenceStore;
import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.widgets.Display;

public class Config {
	
	private static final String SETTING_PATH = "tssmanager.setting";
	
	//public static int activeTime = 600000;//10mingutes
	//public static int connectTimeout = 60000;//1minutes
	//public static int updateStatusLineTime = 600;
	//public static String version = "1.0-2011-Windows";
	
	static PreferenceStore preferenceStore = new PreferenceStore(SETTING_PATH);
	static{
		try {
			preferenceStore.load();
		} catch (IOException e) {
			//找不到配置文件，或者文件被占用
			WizardDialog dlg = new WizardDialog(Display.getCurrent().getActiveShell(), new SetupWizard());
			dlg.addPageChangedListener( new IPageChangedListener(){
				public void pageChanged(PageChangedEvent event) {				
					IWizardPage page = (IWizardPage) event.getSelectedPage();
					//可以保存DialogSettings的一些设置
				}
			});
			dlg.open();
		}
	}
	
	public static PreferenceStore preferenceStore(){
		return preferenceStore;
	}
	
	////////////////////////////////////////////////////////////////
	///////////////////定义各种配置变量/////////////////////////////
	public static final String ACTIVETIME = "activeTime";
	public static final String CONNECTTIMEOUT = "connectTimeout";
	public static final String UPDATESLTIME = "updateStatusLineTime";
	public static final String  VERSION = "version";
	

}
