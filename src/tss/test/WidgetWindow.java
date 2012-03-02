package tss.test;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.*;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.ToolBarManager;
import org.eclipse.jface.window.*;

public class WidgetWindow extends ApplicationWindow {
	
	HelloAction action;
	
	public WidgetWindow(Shell shell) {
		super(shell);
		action = new HelloAction(getShell());
		addMenuBar();
		addToolBar(SWT.FLAT | SWT.WRAP);
		addStatusLine();
	}
	
	protected void createStatusLine(Shell shell){
		
	}
	
	protected ToolBarManager createToolBarManager(int style) {
		ToolBarManager toolbar=new ToolBarManager();
		toolbar.add(action);
		return toolbar;
		
	}
	
	protected MenuManager createMenuManager() {
		MenuManager menubar=new MenuManager();
		MenuManager fileMenu=new MenuManager("&File");
		fileMenu.add(action);
		menubar.add(fileMenu);
		 return menubar;
	}

	protected Control createContents(Composite parent) {
		getShell().setText("Widget Window ");
		parent.setSize(400, 250);
		//可自定义的强大的CtabFolder
		final CTabFolder tabFoler = new CTabFolder(parent,SWT.TOP|SWT.CLOSE|SWT.BORDER);
		tabFoler.setMaximizeVisible(true);
		tabFoler.setMinimizeVisible(true);
		tabFoler.setSelectionForeground(Display.getCurrent().getSystemColor(SWT.COLOR_GREEN));
		tabFoler.setSelectionBackground(Display.getCurrent().getSystemColor(SWT.COLOR_BLUE));
		tabFoler.setLayout(new FillLayout());
		tabFoler.marginHeight = 0;
		tabFoler.marginWidth = 0;
		
		for(int i =0;i<4 ;i++){
			CTabItem ti = new CTabItem(tabFoler,SWT.NONE);
			ti.setText("tab "+i);
			Text t = new Text(tabFoler,SWT.MULTI);
			t.setText("dfds dsf sdf sfds afda fdsa ");
			ti.setControl(t);
		}
		tabFoler.pack();
		
		//简单的tabfolder
		/*TabFolder tfr =new TabFolder(parent,SWT.TOP);
		tfr.setLayout(new FillLayout());
		for(int i =0 ;i<4;i++){
			TabItem ti = new TabItem(tfr,SWT.NONE);
			ti.setText("tab "+i);
			Text t = new Text(tfr,SWT.MULTI);
			t.setText("dfds dsf sdf sfds afda fdsa ");
			ti.setControl(t);
		}*/
		
		/*Label shadow_label = new Label(parent, SWT.CENTER);
		shadow_label.setText("SWT.SHADOW_OUT");
		shadow_label.setBounds(30, 60,110, 15);
		Label shadow_sep = new Label(parent, SWT.SEPARATOR | SWT.SHADOW_OUT);
		shadow_sep.setBounds(30,85,110,5);*/
		
		/*HelloAction action=new HelloAction(parent.getShell());
		ActionContributionItem aci=new ActionContributionItem(action);
		aci.fill(parent);*/
		return parent;
	}

	public static void main(String[] args) {
		WidgetWindow wwin = new WidgetWindow(null);
		wwin.setBlockOnOpen(true);
		wwin.open();
		Display.getCurrent().dispose();
	}
}
