package nju.software.tss.gui;

import nju.software.tss.resources.Resources;
import nju.software.tss.util.Config;
import nju.software.tss.wizard.setup.SetupWizard;

import org.eclipse.jface.dialogs.IPageChangedListener;
import org.eclipse.jface.dialogs.PageChangedEvent;
import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

public class Main {
	
	public static TSSWindow tssWindow;
	
	public static void main(String args[]){
		new Shell();//已知问题，没有初始化shell的时候不能使用jfaceResources
		tssWindow = new TSSWindow(null);
		TSSWindow.setDefaultImage(Resources.getImage(Resources.LOGO));
		tssWindow.setBlockOnOpen(true);
		tssWindow.open();
		Display.getCurrent().dispose();
	}

}
