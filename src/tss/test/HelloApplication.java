package tss.test;

import org.eclipse.swt.widgets.*;
import org.eclipse. jface.window.*;

public class HelloApplication extends ApplicationWindow {

	public HelloApplication(Shell parentShell) {
		super(parentShell);
		
	}
	
	protected Control createContents(Composite parent) {
		getShell().setText("Widget Window ");
		parent.setSize(400,250);
		return parent;
	}
	
	public static void main(String args[]){
		Display display = new Display();
		Shell shell = new Shell(display);
		
		HelloApplication ha = new HelloApplication(null);
		ha.setBlockOnOpen(true);
		ha.open();
		shell.pack();
		shell.open();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
		display.dispose();
	}

}
