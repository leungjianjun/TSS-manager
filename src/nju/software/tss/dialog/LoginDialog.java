package nju.software.tss.dialog;

import java.io.IOException;

import nju.software.tss.gui.Main;
import nju.software.tss.resources.Resources;
import nju.software.tss.util.Config;
import nju.software.tss.util.Helper;

import org.eclipse.jface.dialogs.IMessageProvider;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.FocusAdapter;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;


public class LoginDialog extends TitleAreaDialog {
	
	//定义按钮的常量
	public static final int LOGIN_ID = 0;
	public static final int LOGOUT_ID = 1;
	public static final String LOGIN_LABEL = "登录";
	public static final String LOGOUT_LABEL = "取消";
	
	private Text userName;
	private Text password;

	public LoginDialog(Shell parentShell) {
		super(parentShell);
	}
	
	protected Control createContents(Composite parent) {
		super.createContents(parent);
		this.getShell().setText("TSS Login Dialog");//设置对话框标题栏
		this.getShell().setSize(450,270);
		this.setTitle("用户登录");//设置标题信息
		this.setMessage("请输入要登录的用户名和密码",IMessageProvider.INFORMATION);//设置初始化对话框的提示信息
		//设置右侧的图片，一般为48*48大小
		this.setTitleImage( Resources.getImage(Resources.LOGIN_TIP));
		return parent;
	}
	
	protected Control createDialogArea(Composite parent) {
		Composite composite = new Composite(parent , SWT.NONE);
		GridLayout gl = new GridLayout(4,true);
		gl.marginLeft=20;
		composite.setLayout(gl);
		GridData gd = new GridData();
		gd.horizontalSpan=3;
		gd.horizontalAlignment=GridData.FILL;
		
		new Label( composite , SWT.NONE).setText("用户名：");
		userName = new Text(composite ,SWT.BORDER|SWT.SINGLE|SWT.CENTER);
		userName.setLayoutData(gd);
		if(Config.preferenceStore().getBoolean(Config.REMBERME))
			userName.setText(Config.preferenceStore().getString(Config.ACCOUNT));
		
		new Label( composite , SWT.NONE).setText("密码：");
		password = new Text(composite ,SWT.BORDER|SWT.PASSWORD|SWT.SINGLE|SWT.CENTER);
		password.setLayoutData(gd);
		if(Config.preferenceStore().getBoolean(Config.REMBERME))
			password.setText(Helper.decode(Config.preferenceStore().getString(Config.PASSWORD)));
		
		final Button remberBtn = new Button(composite,SWT.CHECK|SWT.LEFT);
		remberBtn.setText("记住密码");
		//根据配置设置
		remberBtn.setSelection(Config.preferenceStore().getBoolean(Config.REMBERME));
		Button autoBtn = new Button(composite,SWT.CHECK|SWT.LEFT);
		autoBtn.setText("启动后自动登录到TSS");
		//根据配置设置
		autoBtn.setSelection(Config.preferenceStore().getBoolean(Config.AUTOLOGIN));
		
		userName.addFocusListener( new FocusAdapter(){
			//当确认密码文本框失去焦点时，判断是否有效
			public void focusLost(FocusEvent e) {
				//修改文本后要保存到配置文件中
				if(remberBtn.getSelection()){
					Config.preferenceStore().setValue(Config.ACCOUNT, userName.getText());
				}
				if(userName.getText().equals("")){
					setMessage("用户名不能为空!",IMessageProvider.ERROR);
				}
			}
		});
		
		password.addFocusListener( new FocusAdapter(){
			//当确认密码文本框失去焦点时，判断是否有效
			public void focusLost(FocusEvent e) {
				if(remberBtn.getSelection()){
					Config.preferenceStore().setValue(Config.PASSWORD, Helper.encode(password.getText()));
				}
				if(password.getText().equals("")){
					setMessage("密码不能为空!",IMessageProvider.ERROR);
				}
			}
		});
		
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
		
		GridData gd2 = new GridData();
		gd2.horizontalSpan=2;
		autoBtn.setLayoutData(gd2);
		return parent;
	}
	
	protected void createButtonsForButtonBar(Composite parent) {
		//使用父类中创建按钮的方法创建登陆和退出按钮
		createButton(parent, LoginDialog.LOGIN_ID, LoginDialog.LOGIN_LABEL, true);
		createButton(parent, LoginDialog.LOGOUT_ID, LoginDialog.LOGOUT_LABEL, false);
	}
	
	protected void buttonPressed(int buttonId) {
		//如果此时单击了登陆按钮
		if (LoginDialog.LOGIN_ID == buttonId){
			setMessage("正在登录，请稍候...",IMessageProvider.INFORMATION);
			try {
				if(Main.tssClient.login(userName.getText(), password.getText())){
					close();
				}else{
					setMessage("用户名或密码错误!     ",IMessageProvider.ERROR);
				}
			} catch (IOException e) {
				e.printStackTrace();
				setMessage("网络连接异常，请检查网络连接!",IMessageProvider.ERROR);
			}
		}else if (LoginDialog.LOGOUT_ID == buttonId){//如果此时单击了取消按钮，调用父类的
			close();
		}
	}

}
