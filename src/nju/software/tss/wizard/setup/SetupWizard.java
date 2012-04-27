package nju.software.tss.wizard.setup;

import java.io.IOException;

import nju.software.tss.util.Config;

import org.eclipse.jface.wizard.Wizard;

public class SetupWizard extends Wizard{
	
	private Welcome welcomePage;
	private ChooseTerms chooseTerms;
	private ChooseWorkspace chooseWorkspace;
	private Preference preference;
	
	public SetupWizard(){
		try {
			Config.preferenceStore().save();
		} catch (IOException e1) {
		}
		welcomePage = new Welcome();
		chooseTerms = new ChooseTerms();
		chooseWorkspace = new ChooseWorkspace();
		preference = new Preference();
		this.addPage(welcomePage);
		this.addPage(chooseWorkspace);
		this.addPage(chooseTerms);
		this.addPage(preference);
		this.setWindowTitle("设置向导");
		//this.setTitleBarColor(new RGB(100,100,100));
	}
	
	/**
	 * 配置什么时候完成按钮有效
	 */
	public boolean canFinish() {
		return true;
		
	}

	/**
	 * 完成设置后调用此方法
	 */
	@Override
	public boolean performFinish() {
		doDefaultConfig();
		try {
			Config.preferenceStore().save();
		} catch (IOException e1) {
		}
		return true;
	}

	private void doDefaultConfig() {
		Config.preferenceStore().setValue(Config.ACTIVETIME, 600000);
		Config.preferenceStore().setValue(Config.CONNECTTIMEOUT, 60000);
		Config.preferenceStore().setValue(Config.UPDATESLTIME, 600);
		Config.preferenceStore().setValue(Config.VERSION, "1.0-2011-Windows");
	}

}
