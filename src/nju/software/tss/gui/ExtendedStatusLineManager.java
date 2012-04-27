package nju.software.tss.gui;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import nju.software.tss.resources.Resources;
import nju.software.tss.util.Config;

import org.eclipse.jface.action.StatusLineManager;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;

public class ExtendedStatusLineManager extends StatusLineManager{
	
	private static final int MB = 1024*1024;
	
	public Control createControl(Composite parent, int style) {
		Composite control = (Composite) super.createControl(parent, SWT.NONE);
		final Composite composite = new Composite(control, SWT.LEFT_TO_RIGHT); 
		GridLayout gl = new GridLayout(7, false);
		gl.marginHeight=1;
		gl.marginBottom=1;
		composite.setLayout(gl);
		//用户信息
		final CLabel userLabel = new CLabel(composite, SWT.NONE);
		userLabel.setImage(Resources.getImage(Resources.USER));
		
		String name;
		if((name = Main.tssClient.getRealName())!=null){
			userLabel.setText("用户："+name);
		}else{
			userLabel.setText("用户：请登录");
		}
		GridData gd = new GridData();
		gd.heightHint=15;
		(new Label(composite,SWT.SEPARATOR|SWT.VERTICAL|SWT.SHADOW_OUT)).setLayoutData(gd);
		//系统信息
		final CLabel systemLabel = new CLabel(composite, SWT.NONE);
		systemLabel.setImage(Resources.getImage(Resources.SYSTEM));
		systemLabel.setText("内存：00M/00M");
		
		(new Label(composite,SWT.SEPARATOR|SWT.VERTICAL|SWT.SHADOW_OUT)).setLayoutData(gd);
		//时间
		final CLabel timeLabel = new CLabel(composite, SWT.NONE);
		timeLabel.setImage(Resources.getImage(Resources.TIME));
		final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		timeLabel.setText("时间："+sdf.format(Calendar.getInstance().getTime()));
		//(new Label(composite,SWT.SEPARATOR|SWT.VERTICAL|SWT.SHADOW_OUT)).setLayoutData(gd);
		//版本信息
		//CLabel versionLabel = new CLabel(composite, SWT.NONE);
		//versionLabel.setImage(Resources.getImage(Resources.VERSION));
		//versionLabel.setText("版本："+Config.version);
		Display.getDefault().asyncExec(new Runnable(){
			//实时显示信息
			@Override
			public void run() {
				if(!Display.getDefault().isDisposed()){
					//显示登录信息
					String name;
					if((name = Main.tssClient.getRealName())!=null){
						userLabel.setText("用户："+name);
					}
					//显示时间
					timeLabel.setText("时间："+sdf.format(Calendar.getInstance().getTime()));
					//显示内存使用情况
					long totalMemory = Runtime.getRuntime().totalMemory()/MB;
					long usedMemory = totalMemory- Runtime.getRuntime().freeMemory()/MB;
					systemLabel.setText("内存："+usedMemory+ "M/"+totalMemory+"M");
					Display.getDefault().timerExec(Config.preferenceStore().getInt(Config.UPDATESLTIME),this);
				}
				
			} 
		});
		return control;
	}
	
}
