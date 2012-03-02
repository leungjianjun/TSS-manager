package nju.software.tss.gui;

import nju.software.tss.wizard.setup.SetupWizard;

import org.eclipse.jface.dialogs.IPageChangedListener;
import org.eclipse.jface.dialogs.PageChangedEvent;
import org.eclipse.jface.window.ApplicationWindow;
import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;

public class NoConfigWarn extends ApplicationWindow{
	
	public NoConfigWarn(){
		super(null);
	}
	
	protected Control createContents(Composite parent) {
		parent.setLayout(new GridLayout(2,true));
		GridData gd = new GridData();
		gd.horizontalSpan=2;
		//gd.horizontalAlignment=GridData.FILL;
		CLabel cl = new CLabel(parent, SWT.CENTER);
		cl.setLayoutData(gd);
		cl.setText("没有找到配置文件，要进入设置向导吗？");
		cl.setImage(Display.getCurrent().getSystemImage(SWT.ICON_ERROR));
		Button button = new Button( parent ,SWT.CENTER);
		button.setText("确定");
		button.addSelectionListener( new SelectionAdapter(){
			public void widgetSelected(SelectionEvent e) {
				//调用该对话框
				WizardDialog dlg = new WizardDialog(Display.getCurrent().getActiveShell(), new SetupWizard());
				dlg.addPageChangedListener( new IPageChangedListener(){
					public void pageChanged(PageChangedEvent event) {				
						IWizardPage page = (IWizardPage) event.getSelectedPage();
						//可以保存DialogSettings的一些设置
					}
				});
				dlg.open();
			}
		});
		
		Button closeBtn = new Button( parent ,SWT.CENTER);
		closeBtn.setText("取消");
		closeBtn.addSelectionListener( new SelectionAdapter(){
			public void widgetSelected(SelectionEvent e) {
				close();
			}
		});
		return parent;
		
	}
	
	public static void main(String args[]){
		NoConfigWarn noConfigWarn = new NoConfigWarn();
		noConfigWarn.setBlockOnOpen( true );
		noConfigWarn.open();
		Display.getCurrent().dispose();
	}

}
