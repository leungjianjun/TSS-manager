package tss.test;

import nju.software.tss.resources.Resources;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Shell;


public class HelloAction extends Action {
	
	private Shell shell;

	public HelloAction(Shell shell) {
		super();
        this.shell = shell;
        this.setText("&SayHi@Ctrl+H");
        this.setImageDescriptor(Resources.getImageDescriptor(Resources.TEST_ICON));
	}

	public void run() {
		MessageDialog.openInformation(shell, "Hello", "Hello,Action!");
	}
}