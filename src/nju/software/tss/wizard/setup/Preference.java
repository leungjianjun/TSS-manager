package nju.software.tss.wizard.setup;

import nju.software.tss.resources.Resources;
import nju.software.tss.util.Config;
import nju.software.tss.util.Helper;

import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

public class Preference extends WizardPage {
	
	Text tssName;
	Text reviewName;
	Text resourceName;
	Text assignmentName;
	
	public Preference() {
		super("欢迎页面","偏好设置",Resources.getImageDescriptor(Resources.SETUP));
		this.setMessage("详细的偏好设置");
	}

	@Override
	public void createControl(Composite parent) {
		Composite composite = new Composite(parent, SWT.NONE);
		GridLayout gl = new GridLayout(4,true);
		gl.marginLeft=20;
		composite.setLayout(gl);
		loginPre(composite);
		dirnamePre(composite);
		setControl(composite);
		
		
	}

	private void dirnamePre(Composite composite){
		Label label = new Label( composite , SWT.NONE);
		label.setText("文件名设置");
		GridData gd = new GridData();
		gd.horizontalSpan=4;
		label.setLayoutData(gd);
		GridData gd2 = new GridData();
		gd2.horizontalSpan=2;
		
		Label label2 = new Label( composite , SWT.NONE);
		label2.setText("下载文件夹名");
		label2.setLayoutData(gd2);
		tssName = new Text(composite ,SWT.BORDER|SWT.SINGLE|SWT.CENTER);
		tssName.setText("TSS文件");
		tssName.setLayoutData(gd2);
		
		Label label3 = new Label( composite , SWT.NONE);
		label3.setText("考试复习文件夹名");
		label3.setLayoutData(gd2);
		reviewName = new Text(composite ,SWT.BORDER|SWT.SINGLE|SWT.CENTER);
		reviewName.setText("考试复习");
		reviewName.setLayoutData(gd2);
		
		Label label4 = new Label( composite , SWT.NONE);
		label4.setText("资料文件夹名");
		label4.setLayoutData(gd2);
		resourceName = new Text(composite ,SWT.BORDER|SWT.SINGLE|SWT.CENTER);
		resourceName.setText("相关资料");
		resourceName.setLayoutData(gd2);
		
		Label label5 = new Label( composite , SWT.NONE);
		label5.setText("作业文件夹名");
		label5.setLayoutData(gd2);
		assignmentName = new Text(composite ,SWT.BORDER|SWT.SINGLE|SWT.CENTER);
		assignmentName.setText("作业和答案");
		assignmentName.setLayoutData(gd2);
	}
	
	private void loginPre(Composite composite){
		
		GridData gd = new GridData();
		gd.horizontalSpan=3;
		gd.horizontalAlignment=GridData.FILL;
		
		Label label = new Label( composite , SWT.NONE);
		label.setText("帐号设置");
		GridData gd3 = new GridData();
		gd3.horizontalSpan=4;
		label.setLayoutData(gd3);
		new Label( composite , SWT.NONE).setText("用户名：");
		final Text userName = new Text(composite ,SWT.BORDER|SWT.SINGLE|SWT.CENTER);
		userName.setLayoutData(gd);
		if(Config.preferenceStore().getBoolean(Config.REMBERME))
			userName.setText(Config.preferenceStore().getString(Config.ACCOUNT));
		
		new Label( composite , SWT.NONE).setText("密码：");
		final Text password = new Text(composite ,SWT.BORDER|SWT.PASSWORD|SWT.SINGLE|SWT.CENTER);
		password.setLayoutData(gd);
		if(Config.preferenceStore().getBoolean(Config.REMBERME))
			password.setText(Helper.decode(Config.preferenceStore().getString(Config.PASSWORD)));
		
		final Button remberBtn = new Button(composite,SWT.CHECK|SWT.LEFT);
		remberBtn.setText("记住密码");
		//根据配置设置
		remberBtn.setSelection(Config.preferenceStore().getBoolean(Config.REMBERME));
		Button autoBtn = new Button(composite,SWT.CHECK|SWT.LEFT);
		autoBtn.setText("启动后自动登录到TSS");
		GridData gd2 = new GridData();
		gd2.horizontalSpan=2;
		autoBtn.setLayoutData(gd2);
		
		remberBtn.addSelectionListener(new SelectionAdapter(){
			public void widgetSelected(SelectionEvent e) {
				Config.preferenceStore().setValue(Config.REMBERME, e.doit);
				if(e.doit){
					//记住密码帐号
					Config.preferenceStore().setValue(Config.ACCOUNT, userName.getText());
					Config.preferenceStore().setValue(Config.PASSWORD, Helper.encode(password.getText()));
				}else{
					//否则清除密码帐号
					Config.preferenceStore().setValue(Config.ACCOUNT, "");
					Config.preferenceStore().setValue(Config.PASSWORD, "");
				}
				Config.save();
			}
		});
		
		autoBtn.addSelectionListener(new SelectionAdapter(){
			public void widgetSelected(SelectionEvent e) {
				Config.preferenceStore().setValue(Config.AUTOLOGIN, e.doit);
				//如果能够自动登录，则能够记住密码
				if(e.doit && !remberBtn.getSelection()){
					remberBtn.setSelection(true);
					Config.preferenceStore().setValue(Config.REMBERME, true);
					Config.preferenceStore().setValue(Config.ACCOUNT, userName.getText());
					Config.preferenceStore().setValue(Config.PASSWORD, Helper.encode(password.getText()));
				}
				Config.save();
			}
		});
	}
	
	public IWizardPage getNextPage() {
		Config.preferenceStore().setValue(Config.TSS_FILE, tssName.getText());
		Config.preferenceStore().setValue(Config.REVIEW_EXAM, reviewName.getText());
		Config.preferenceStore().setValue(Config.RELATE_FILE, resourceName.getText());
		Config.preferenceStore().setValue(Config.ASSIGNMENT, assignmentName.getText());
		return super.getNextPage();
		
	}
}
