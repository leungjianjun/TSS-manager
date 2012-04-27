package nju.software.tss.action;

import java.io.File;

import nju.software.tss.dialog.AddTermsDialog;
import nju.software.tss.gui.Main;
import nju.software.tss.resources.Resources;
import nju.software.tss.util.Config;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.widgets.Shell;

public class AddTermsAction extends Action {
	
	private Shell shell;
	
	public AddTermsAction(Shell shell){
		super();
		this.shell = shell;
		this.setText("&新建学期@Ctrl+T");
        this.setImageDescriptor(Resources.getImageDescriptor(Resources.ADD_TERMS));
	}
	
	public void run() {
		AddTermsDialog inputDialog = new AddTermsDialog(shell,"输入学期名","请输入学期名","",null);
		if(inputDialog.open()== Window.OK){
			String termsName = inputDialog.getValue();
			File dir = new File(Config.preferenceStore().getString(Config.WORKSPACE)+"/"+termsName);
			if(!dir.exists()
					&&dir.mkdir()
					&&Config.addStringList(termsName, Config.TERMS)){
				Config.preferenceStore().setValue(termsName, "");
				if(inputDialog.getSelected()){
					//设为本学期
					Config.preferenceStore().setValue(Config.NOW_TERMS, termsName);
				}
				Main.tssWindow.sidebar.refreshLocal();
			}else{
				MessageDialog.openError(shell, "创建学期发生错误", "文件夹已经窜在，无法创建文件夹");
			}
		}
	}
}
