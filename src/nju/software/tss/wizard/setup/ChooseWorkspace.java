package nju.software.tss.wizard.setup;

import nju.software.tss.resources.Resources;
import nju.software.tss.util.Config;

import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

public class ChooseWorkspace extends WizardPage{
	
	Text workspace;
	
	public ChooseWorkspace() {
		super("欢迎页面","配置工作目录",Resources.getImageDescriptor(Resources.SETUP));
		this.setMessage("选择一个文件夹用于存放你的学习资料");
	}

	@Override
	public void createControl(Composite parent) {
		Composite composite = new Composite(parent, SWT.NONE);
		GridLayout gl = new GridLayout(2,false);
		gl.marginHeight=80;
		gl.marginRight=50;
		gl.horizontalSpacing=20;
		GridData gd = new GridData();
		gd.horizontalSpan=2;
		gd.horizontalAlignment=GridData.FILL;
		
		composite.setLayout(gl);
		Label label = new Label( composite , SWT.NONE);
		label.setText("选择工作目录：");
		
		workspace = new Text(composite ,SWT.BORDER|SWT.SINGLE|SWT.CENTER);
		workspace.setText(System.getProperty("user.home")+"\\学习");
		label.setLayoutData(gd);
		Button browser = new Button(composite,SWT.NONE);
		browser.setText(" 浏 览  ");
		browser.addSelectionListener(new SelectionAdapter(){
			public void widgetSelected(SelectionEvent e) {
				DirectoryDialog dialog = new DirectoryDialog(Display.getCurrent().getActiveShell());
				dialog.setText("选择目录");
				dialog.setMessage("选择工作目录");
				workspace.setText(dialog.open());
			}
		});
		setControl(composite);
	}
	
	public IWizardPage getNextPage() {
		Config.preferenceStore().setValue(Config.WORKSPACE, workspace.getText());
		return super.getNextPage();
	}
}
