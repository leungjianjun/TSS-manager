package nju.software.tss.wizard.setup;

import java.io.File;

import nju.software.tss.resources.Resources;
import nju.software.tss.util.Config;

import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;

public class ChooseTerms extends WizardPage{
	
	public ChooseTerms() {
		super("欢迎页面","配置学期",Resources.getImageDescriptor(Resources.SETUP));
		this.setMessage("选择你现在所在的学期，并为勾选的学期创建文件夹");
	}

	@Override
	public void createControl(Composite parent) {
		Composite composite = new Composite(parent, SWT.NONE);
		composite.setLayout(new FillLayout());
		
		GridLayout gl = new GridLayout(4,false);
		gl.marginHeight=80;
		gl.marginRight=50;
		gl.horizontalSpacing=20;
		composite.setLayout(gl);
		
		final Button gradeOneUPBtn = new Button(composite,SWT.CHECK|SWT.LEFT);
		final Text gradeOneUPTxt = new Text(composite ,SWT.BORDER|SWT.SINGLE|SWT.CENTER);
		gradeOneUPTxt.setText("大一上");
		
		final Button gradeOneDownBtn = new Button(composite,SWT.CHECK|SWT.LEFT);
		final Text gradeOneDownTxt = new Text(composite ,SWT.BORDER|SWT.SINGLE|SWT.CENTER);
		gradeOneDownTxt.setText("大一下");
		
		final Button gradeTwoUpBtn = new Button(composite,SWT.CHECK|SWT.LEFT);
		final Text gradeTwoUpTxt = new Text(composite ,SWT.BORDER|SWT.SINGLE|SWT.CENTER);
		gradeTwoUpTxt.setText("大二上");
		
		final Button gradeTwoDownBtn = new Button(composite,SWT.CHECK|SWT.LEFT);
		final Text gradeTwoDownTxt = new Text(composite ,SWT.BORDER|SWT.SINGLE|SWT.CENTER);
		gradeTwoDownTxt.setText("大二下");
		
		final Button gradeThreeUpBtn = new Button(composite,SWT.CHECK|SWT.LEFT);
		final Text gradeThreeUpTxt = new Text(composite ,SWT.BORDER|SWT.SINGLE|SWT.CENTER);
		gradeThreeUpTxt.setText("大三上");
		
		final Button gradeThreeMidBtn = new Button(composite,SWT.CHECK|SWT.LEFT);
		final Text gradeThreeMidTxt = new Text(composite ,SWT.BORDER|SWT.SINGLE|SWT.CENTER);
		gradeThreeMidTxt.setText("大三中");
		
		final Button gradeThreeDownBtn = new Button(composite,SWT.CHECK|SWT.LEFT);
		final Text gradeThreeDownTxt = new Text(composite ,SWT.BORDER|SWT.SINGLE|SWT.CENTER);
		gradeThreeDownTxt.setText("大三下");
		
		final Button gradeFourUpBtn = new Button(composite,SWT.CHECK|SWT.LEFT);
		final Text gradeFourUpTxt = new Text(composite ,SWT.BORDER|SWT.SINGLE|SWT.CENTER);
		gradeFourUpTxt.setText("大四上");
		
		final Button gradeFourMidBtn = new Button(composite,SWT.CHECK|SWT.LEFT);
		final Text gradeFourMidTxt = new Text(composite ,SWT.BORDER|SWT.SINGLE|SWT.CENTER);
		gradeFourMidTxt.setText("大四中");
		
		final Button gradeFourDownBtn = new Button(composite,SWT.CHECK|SWT.LEFT);
		final Text gradeFourDownTxt = new Text(composite ,SWT.BORDER|SWT.SINGLE|SWT.CENTER);
		gradeFourDownTxt.setText("大四下");
		
		gradeOneUPBtn.addSelectionListener(new SelectionAdapter(){
			public void widgetSelected(SelectionEvent e) {
				if(gradeOneUPBtn.getSelection()){
					gradeOneUPBtn.setSelection(true);
					gradeOneDownBtn.setSelection(false);
					gradeTwoUpBtn.setSelection(false);
					gradeTwoDownBtn.setSelection(false);
					gradeThreeUpBtn.setSelection(false);
					gradeThreeMidBtn.setSelection(false);
					gradeThreeDownBtn.setSelection(false);
					gradeFourUpBtn.setSelection(false);
					gradeFourMidBtn.setSelection(false);
					gradeFourDownBtn.setSelection(false);
					Config.setStringList(new String[]{gradeOneUPTxt.getText()}, Config.TERMS);
					Config.preferenceStore().setValue(Config.NOW_TERMS, gradeOneUPTxt.getText());
				}
			}
		});
		
		gradeOneDownBtn.addSelectionListener(new SelectionAdapter(){
			public void widgetSelected(SelectionEvent e) {
				if(gradeOneDownBtn.getSelection()){
					gradeOneDownBtn.setSelection(true);
					gradeOneUPBtn.setSelection(true);
					gradeTwoUpBtn.setSelection(false);
					gradeTwoDownBtn.setSelection(false);
					gradeThreeUpBtn.setSelection(false);
					gradeThreeMidBtn.setSelection(false);
					gradeThreeDownBtn.setSelection(false);
					gradeFourUpBtn.setSelection(false);
					gradeFourMidBtn.setSelection(false);
					gradeFourDownBtn.setSelection(false);
					Config.setStringList(new String[]{gradeOneUPTxt.getText(),gradeOneDownTxt.getText()}, Config.TERMS);
					Config.preferenceStore().setValue(Config.NOW_TERMS, gradeOneDownTxt.getText());
				}
			}
		});
		
		gradeTwoUpBtn.addSelectionListener(new SelectionAdapter(){
			public void widgetSelected(SelectionEvent e) {
				if(gradeTwoUpBtn.getSelection()){
					gradeOneUPBtn.setSelection(true);
					gradeOneDownBtn.setSelection(true);
					gradeTwoUpBtn.setSelection(true);
					gradeTwoDownBtn.setSelection(false);
					gradeThreeUpBtn.setSelection(false);
					gradeThreeMidBtn.setSelection(false);
					gradeThreeDownBtn.setSelection(false);
					gradeFourUpBtn.setSelection(false);
					gradeFourMidBtn.setSelection(false);
					gradeFourDownBtn.setSelection(false);
					Config.setStringList(new String[]{gradeOneUPTxt.getText(),gradeOneDownTxt.getText(),gradeTwoUpTxt.getText()}, Config.TERMS);
					Config.preferenceStore().setValue(Config.NOW_TERMS, gradeTwoUpTxt.getText());
				}
			}
		});
		
		gradeTwoDownBtn.addSelectionListener(new SelectionAdapter(){
			public void widgetSelected(SelectionEvent e) {
				if(gradeTwoDownBtn.getSelection()){
					gradeOneUPBtn.setSelection(true);
					gradeOneDownBtn.setSelection(true);
					gradeTwoUpBtn.setSelection(true);
					gradeTwoDownBtn.setSelection(true);
					gradeThreeUpBtn.setSelection(false);
					gradeThreeMidBtn.setSelection(false);
					gradeThreeDownBtn.setSelection(false);
					gradeFourUpBtn.setSelection(false);
					gradeFourMidBtn.setSelection(false);
					gradeFourDownBtn.setSelection(false);
					Config.setStringList(new String[]{gradeOneUPTxt.getText(),gradeOneDownTxt.getText(),gradeTwoUpTxt.getText(),gradeTwoDownTxt.getText()}, Config.TERMS);
					Config.preferenceStore().setValue(Config.NOW_TERMS, gradeTwoDownTxt.getText());
				}
			}
		});
		
		gradeThreeUpBtn.addSelectionListener(new SelectionAdapter(){
			public void widgetSelected(SelectionEvent e) {
				if(gradeThreeUpBtn.getSelection()){
					gradeOneUPBtn.setSelection(true);
					gradeOneDownBtn.setSelection(true);
					gradeTwoUpBtn.setSelection(true);
					gradeTwoDownBtn.setSelection(true);
					gradeThreeUpBtn.setSelection(true);
					gradeThreeMidBtn.setSelection(false);
					gradeThreeDownBtn.setSelection(false);
					gradeFourUpBtn.setSelection(false);
					gradeFourMidBtn.setSelection(false);
					gradeFourDownBtn.setSelection(false);
					Config.setStringList(new String[]{gradeOneUPTxt.getText(),gradeOneDownTxt.getText(),gradeTwoUpTxt.getText(),gradeTwoDownTxt.getText(),gradeThreeUpTxt.getText()}, Config.TERMS);
					Config.preferenceStore().setValue(Config.NOW_TERMS, gradeThreeUpTxt.getText());
				}
			}
		});
		
		gradeThreeMidBtn.addSelectionListener(new SelectionAdapter(){
			public void widgetSelected(SelectionEvent e) {
				if(gradeThreeMidBtn.getSelection()){
					gradeOneUPBtn.setSelection(true);
					gradeOneDownBtn.setSelection(true);
					gradeTwoUpBtn.setSelection(true);
					gradeTwoDownBtn.setSelection(true);
					gradeThreeUpBtn.setSelection(true);
					gradeThreeMidBtn.setSelection(true);
					gradeThreeDownBtn.setSelection(false);
					gradeFourUpBtn.setSelection(false);
					gradeFourMidBtn.setSelection(false);
					gradeFourDownBtn.setSelection(false);
					Config.setStringList(new String[]{gradeOneUPTxt.getText(),gradeOneDownTxt.getText(),gradeTwoUpTxt.getText(),gradeTwoDownTxt.getText(),gradeThreeUpTxt.getText(),gradeThreeMidTxt.getText()}, Config.TERMS);
					Config.preferenceStore().setValue(Config.NOW_TERMS, gradeThreeMidTxt.getText());
				}
			}
		});
		
		gradeThreeDownBtn.addSelectionListener(new SelectionAdapter(){
			public void widgetSelected(SelectionEvent e) {
				if(gradeThreeDownBtn.getSelection()){
					gradeOneUPBtn.setSelection(true);
					gradeOneDownBtn.setSelection(true);
					gradeTwoUpBtn.setSelection(true);
					gradeTwoDownBtn.setSelection(true);
					gradeThreeUpBtn.setSelection(true);
					gradeThreeMidBtn.setSelection(true);
					gradeThreeDownBtn.setSelection(true);
					gradeFourUpBtn.setSelection(false);
					gradeFourMidBtn.setSelection(false);
					gradeFourDownBtn.setSelection(false);
					Config.setStringList(new String[]{gradeOneUPTxt.getText(),gradeOneDownTxt.getText(),gradeTwoUpTxt.getText(),gradeTwoDownTxt.getText(),gradeThreeUpTxt.getText(),gradeThreeMidTxt.getText(),gradeThreeDownTxt.getText()}, Config.TERMS);
					Config.preferenceStore().setValue(Config.NOW_TERMS, gradeThreeDownTxt.getText());
				}
			}
		});
		
		gradeFourUpBtn.addSelectionListener(new SelectionAdapter(){
			public void widgetSelected(SelectionEvent e) {
				if(gradeFourUpBtn.getSelection()){
					gradeOneUPBtn.setSelection(true);
					gradeOneDownBtn.setSelection(true);
					gradeTwoUpBtn.setSelection(true);
					gradeTwoDownBtn.setSelection(true);
					gradeThreeUpBtn.setSelection(true);
					gradeThreeMidBtn.setSelection(true);
					gradeThreeDownBtn.setSelection(true);
					gradeFourUpBtn.setSelection(true);
					gradeFourMidBtn.setSelection(false);
					gradeFourDownBtn.setSelection(false);
					Config.setStringList(new String[]{gradeOneUPTxt.getText(),gradeOneDownTxt.getText(),gradeTwoUpTxt.getText(),gradeTwoDownTxt.getText(),gradeThreeUpTxt.getText(),gradeThreeMidTxt.getText(),gradeThreeDownTxt.getText(),gradeFourUpTxt.getText()}, Config.TERMS);
					Config.preferenceStore().setValue(Config.NOW_TERMS, gradeFourUpTxt.getText());
				}
			}
		});
		
		gradeFourMidBtn.addSelectionListener(new SelectionAdapter(){
			public void widgetSelected(SelectionEvent e) {
				if(gradeFourMidBtn.getSelection()){
					gradeOneUPBtn.setSelection(true);
					gradeOneDownBtn.setSelection(true);
					gradeTwoUpBtn.setSelection(true);
					gradeTwoDownBtn.setSelection(true);
					gradeThreeUpBtn.setSelection(true);
					gradeThreeMidBtn.setSelection(true);
					gradeThreeDownBtn.setSelection(true);
					gradeFourUpBtn.setSelection(true);
					gradeFourMidBtn.setSelection(true);
					gradeFourDownBtn.setSelection(false);
					Config.setStringList(new String[]{gradeOneUPTxt.getText(),gradeOneDownTxt.getText(),gradeTwoUpTxt.getText(),gradeTwoDownTxt.getText(),gradeThreeUpTxt.getText(),gradeThreeMidTxt.getText(),gradeThreeDownTxt.getText(),gradeFourUpTxt.getText(),gradeFourMidTxt.getText()}, Config.TERMS);
					Config.preferenceStore().setValue(Config.NOW_TERMS, gradeFourMidTxt.getText());
				}
			}
		});
		
		gradeFourDownBtn.addSelectionListener(new SelectionAdapter(){
			public void widgetSelected(SelectionEvent e) {
				if(gradeFourDownBtn.getSelection()){
					gradeOneUPBtn.setSelection(true);
					gradeOneDownBtn.setSelection(true);
					gradeTwoUpBtn.setSelection(true);
					gradeTwoDownBtn.setSelection(true);
					gradeThreeUpBtn.setSelection(true);
					gradeThreeMidBtn.setSelection(true);
					gradeThreeDownBtn.setSelection(true);
					gradeFourUpBtn.setSelection(true);
					gradeFourMidBtn.setSelection(true);
					gradeFourDownBtn.setSelection(true);
					Config.setStringList(new String[]{gradeOneUPTxt.getText(),gradeOneDownTxt.getText(),gradeTwoUpTxt.getText(),gradeTwoDownTxt.getText(),gradeThreeUpTxt.getText(),gradeThreeMidTxt.getText(),gradeThreeDownTxt.getText(),gradeFourUpTxt.getText(),gradeFourMidTxt.getText(),gradeFourDownTxt.getText()}, Config.TERMS);
					Config.preferenceStore().setValue(Config.NOW_TERMS, gradeFourDownTxt.getText());
				}
			}
		});
		setControl(composite);
	}
	
	
	public IWizardPage getNextPage() {
		for(String term:Config.getStringList(Config.TERMS)){
			File dir = new File(Config.preferenceStore().getString(Config.WORKSPACE)+"/"+term);
			if(!dir.exists()&&dir.mkdir()){
				Config.preferenceStore().setValue(term, "");
			}
		}
		return super.getNextPage();
		
	}

}
