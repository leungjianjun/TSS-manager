package nju.software.tss.action;

import java.io.File;

import nju.software.tss.gui.Main;
import nju.software.tss.model.Course;
import nju.software.tss.resources.Resources;
import nju.software.tss.util.Config;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Shell;

public class AddCourseAction extends Action {
	
	private Shell shell;
	
	public AddCourseAction(Shell shell){
		super();
		this.shell = shell;
		this.setText("&添加课程到@Ctrl+A");
        this.setImageDescriptor(Resources.getImageDescriptor(Resources.ADD_COURSE_LOGO));
	}
	
	public void run(String terms,Course course){
		//更新配置文件
		Config.addStringList(course.courseNo, terms);
		//添加相应的文件夹
		File dir = new File(Config.preferenceStore().getString(Config.WORKSPACE)+"/"+terms+"/"+course.courseName);
		if(!dir.exists()&&dir.mkdir()){
			
			dir = new File(Config.preferenceStore().getString(Config.WORKSPACE)+"/"+terms+"/"
				+course.courseName+"/"+Config.preferenceStore().getString(Config.TSS_FILE));
			dir.mkdir();
			
			dir = new File(Config.preferenceStore().getString(Config.WORKSPACE)+"/"+terms+"/"
				+course.courseName+"/"+Config.preferenceStore().getString(Config.REVIEW_EXAM));
			dir.mkdir();
			
			dir = new File(Config.preferenceStore().getString(Config.WORKSPACE)+"/"+terms+"/"
				+course.courseName+"/"+Config.preferenceStore().getString(Config.RELATE_FILE));
			dir.mkdir();			

			dir = new File(Config.preferenceStore().getString(Config.WORKSPACE)+"/"+terms+"/"
					+course.courseName+"/"+Config.preferenceStore().getString(Config.ASSIGNMENT));
			dir.mkdir();
				
			Main.tssWindow.relcAction.run();
			Main.tssWindow.rereAction.run();
		}else{
			MessageDialog.openError(shell, "创建课程发生错误", "文件夹已经窜在，无法创建文件夹");
		}
	}
	
	public void run() {

	}
}

