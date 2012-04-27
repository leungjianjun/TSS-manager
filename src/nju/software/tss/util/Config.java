package nju.software.tss.util;

import java.io.File;
import java.io.IOException;

import nju.software.tss.wizard.setup.SetupWizard;

import org.eclipse.jface.preference.PreferenceStore;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.widgets.Display;

public class Config {
	
	private static final String SETTING_PATH = "tssmanager.setting";
	
	static PreferenceStore preferenceStore = new PreferenceStore(SETTING_PATH);
	static{
		try {
			preferenceStore.load();
		} catch (IOException e) {
			//找不到配置文件，或者文件被占用
			WizardDialog dlg = new WizardDialog(Display.getCurrent().getActiveShell(), new SetupWizard());
			dlg.open();
		}
	}
	
	public static void create(){
		File file = new File(SETTING_PATH);
		try {
			file.createNewFile();
		} catch (IOException e) {
		}
		preferenceStore = new PreferenceStore(SETTING_PATH);
		try {
			preferenceStore.load();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static PreferenceStore preferenceStore(){
		return preferenceStore;
	}
	
	public static void save(){
		try {
			preferenceStore.save();
		} catch (IOException e) {
		}
	}
	
	public static String[] getStringList(String key){
		return preferenceStore.getString(key).split(" ");
	}
	
	public static void setStringList(String[] values,String key){
		int l = values.length;
		String result = values[0];
		for(int i =1;i<l;i++){
			result = result+" "+values[i];
		}
		preferenceStore.setValue(key, result);
		save();
	}
	
	public static boolean addStringList(String value,String key){
		String s = preferenceStore.getString(key);
		if(s.contains(value)){
			return false;
		}else{
			s = s+" "+value;
			preferenceStore.setValue(key, s);
			save();
			return true;
		}
	}
	
	////////////////////////////////////////////////////////////////
	///////////////////定义各种配置变量/////////////////////////////
	public static final String ACTIVETIME = "activeTime";
	public static final String CONNECTTIMEOUT = "connectTimeout";
	public static final String UPDATESLTIME = "updateStatusLineTime";
	public static final String VERSION = "version";
	public static final String WORKSPACE = "workspace";
	public static final String REMBERME = "rememberme";
	public static final String AUTOLOGIN = "autologin";
	public static final String ACCOUNT = "account";
	public static final String PASSWORD = "password";
	public static final String TERMS = "terms";
	public static final String TSS_FILE = "tss_file";
	public static final String REVIEW_EXAM = "review_exam";
	public static final String RELATE_FILE = "relate_file";
	public static final String ASSIGNMENT = "assignment";
	public static final String NOW_TERMS = "currentterms";
}
