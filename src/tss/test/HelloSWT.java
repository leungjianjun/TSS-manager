package tss.test;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

public class HelloSWT {
	
	public static void main(String args[]){
		Display display = new Display();
		Shell shell = new Shell(display);
		Text helloText = new Text(shell, SWT.CENTER);
		helloText.setText("Hello,World!");
		helloText.pack();
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
