package nju.software.tss.action;

import nju.software.tss.dialog.LoginDialog;
import nju.software.tss.resources.Resources;

import org.eclipse.jface.action.Action;
import org.eclipse.swt.widgets.Shell;


public class LoginAction extends Action {
	
	private Shell shell;
	
	public LoginAction(Shell shell) {
		super();
        this.shell = shell;
        this.setText("&Login@Ctrl+I");
        this.setImageDescriptor(Resources.getImageDescriptor(Resources.TEST_ICON));
	}
	
	public void run() {
		LoginDialog dialog = new LoginDialog(shell);
		dialog.open();
	}

}
