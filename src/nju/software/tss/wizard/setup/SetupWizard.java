package nju.software.tss.wizard.setup;

import org.eclipse.jface.wizard.Wizard;

public class SetupWizard extends Wizard{
	
	private Welcome welcomePage;
	
	public SetupWizard(){
		welcomePage = new Welcome();
		this.addPage(welcomePage);
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
		return true;
	}

}
