package tss.test;

import org.eclipse.jface.dialogs.InputDialog;
import org.eclipse.jface.window.ApplicationWindow;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;


public class HelloDialog extends ApplicationWindow{

	public HelloDialog() {
		super(null);
	}
	protected void configureShell(Shell shell) {
		super.configureShell( shell );
		shell.setText("输入对话框示例");
	}
	
	protected Control createContents(Composite parent) {
		Composite composite = new Composite( parent , SWT.NONE);
		composite.setLayout( new GridLayout());
		Button button = new Button( composite ,SWT.NONE);
		button.setText("打开输入对话框");
		button.addSelectionListener( new SelectionAdapter(){
			public void widgetSelected(SelectionEvent e) {
				InputDialog inputDialog = new InputDialog(Display.getCurrent().getActiveShell(),
						"输入电子邮件",//对话框的标题
						"请输入电子邮件地址：",//对话框的提示信息
						"ABC@hotmail.com",//输入框中默认值
						new EmailValidator()//验证输入字符的有效性
						);
				int r = inputDialog.open();//打开窗口
				if (r==Window.OK)//如果输入有效，则输出输入的值
					System.out.println(inputDialog.getValue());
				else
					System.out.println("取消");
			}
		});
		return parent;
	}
	public static void main(String[] args) {
		//InputDialogTest test = new InputDialogTest();
		//test.setBlockOnOpen( true );
		//test.open();
		Display.getCurrent().dispose();
	}

}