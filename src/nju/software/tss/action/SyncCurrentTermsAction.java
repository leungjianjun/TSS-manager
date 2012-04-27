package nju.software.tss.action;

import java.io.IOException;

import nju.software.tss.gui.DownloadManager;
import nju.software.tss.gui.Main;
import nju.software.tss.model.Course;
import nju.software.tss.util.Config;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Shell;

public class SyncCurrentTermsAction extends Action {
	
	private Shell shell;
	
	public SyncCurrentTermsAction(Shell shell) {
		super();
		this.shell = shell;
	}
	
	public void run() {
		String current_terms = Config.preferenceStore().getString(Config.NOW_TERMS);
		if(current_terms==null){
			MessageDialog.openError(shell, "同步文件发生错误", "没有确定当前学期");
			return ;
		}
		for(String courseNo:Config.getStringList(current_terms)){
			Course course = Course.newCourse(courseNo);
			try {
				Main.tssClient.getCourseUnDLList(course);
				DownloadManager.open().addDownloadCourse(course);
			} catch (IOException e) {
				MessageDialog.openError(shell, "同步文件发生错误", "网络连接出现问题");
			}
		}
		DownloadManager.get().refresh();
		DownloadManager.get().startDownload();
	}
}
