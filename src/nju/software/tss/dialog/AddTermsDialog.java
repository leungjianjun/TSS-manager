package nju.software.tss.dialog;

import org.eclipse.jface.dialogs.IInputValidator;
import org.eclipse.jface.dialogs.InputDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;

public class AddTermsDialog extends InputDialog{
	
	Boolean check;
	Button checkbox;

	public AddTermsDialog(Shell parentShell, String dialogTitle,
			String dialogMessage, String initialValue, IInputValidator validator) {
		super(parentShell, dialogTitle, dialogMessage, initialValue, validator);
	}
	
	protected Control createDialogArea(Composite parent) {
		Composite composite = (Composite) super.createDialogArea(parent);
		checkbox = new Button(composite,SWT.CHECK|SWT.LEFT);
		checkbox.setText("设为本学期");
		checkbox.setSelection(true);
		return composite;
	}
	
	protected void buttonPressed(int buttonId) {
		check=checkbox.getSelection();
		super.buttonPressed(buttonId);
	}
	
	public boolean getSelected(){
		return check;
	}

}
