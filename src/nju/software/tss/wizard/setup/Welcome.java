package nju.software.tss.wizard.setup;


import nju.software.tss.resources.Resources;

import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;


public class Welcome extends WizardPage{
	
	public Welcome() {
		super("欢迎页面","欢迎您使用TSS-manager",Resources.getImageDescriptor(Resources.SETUP));
		this.setMessage("首次使用请先进行必要的配置");
	}

	@Override
	public void createControl(Composite parent) {
		Composite composite = new Composite(parent, SWT.NONE);
		composite.setLayout(new FillLayout());
		//new CLabel(composite,SWT.NONE).setBackgroundImage(Resources.getImage(Resources.SETUP_WELCOME));
		composite.setBackgroundImage(Resources.getImage(Resources.SETUP_WELCOME));
		//composite.setBackground(new Color(Display.getCurrent(),0,0,0));
		//new Label(composite, SWT.CENTER).setText("感谢您的支持");
		setControl(composite);
	}

}
