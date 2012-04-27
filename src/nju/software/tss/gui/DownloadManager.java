package nju.software.tss.gui;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import nju.software.tss.model.Course;
import nju.software.tss.model.DownloadFile;
import nju.software.tss.resources.Resources;
import nju.software.tss.util.Helper;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;

public class DownloadManager{
	
	public Thread thread;
	
	private static DownloadManager dm;
	
	public Table table;
	
	private List<DownloadFile> downList = new ArrayList<DownloadFile>();
	
	private DownloadState DS = new DownloadState();
	
	/**
	 * 只允许有一个DownloadManager实例，打开后返回该实例
	 * @return
	 */
	public static DownloadManager open(){
		if(dm==null){
			dm = new DownloadManager(Main.tssWindow.folder);
		}
		return dm;
	}
	
	public static DownloadManager get(){
		return dm;
	}
	
	private DownloadManager(CTabFolder folder){
		table = new Table(folder, SWT.BORDER);
		initTable();
		
		CTabItem item = new CTabItem(folder, SWT.MULTI|SWT.FULL_SELECTION);
		item.setText("下载管理");
		item.setImage(Resources.getImage(Resources.DOWNLOAD_MANAGER_16));
		item.setControl(table);
		folder.setSelection(item);
	}
	
	private void initTable(){
		table.setHeaderVisible(true);
		table.setLinesVisible(true);
		final String[] tableHeader = {"状态","课程","文件名","大小","所用时间"};
		for(String head:tableHeader){
			TableColumn tc = new TableColumn(table, SWT.NONE);
			tc.setText(head);
			tc.setMoveable(true);
		}
				
		for(TableColumn tc:table.getColumns()){
			tc.pack();
		}
	}

	public void refresh(){
		table.removeAll();
		for(DownloadFile df:downList){
				TableItem item = new TableItem(table, SWT.NONE);
				
				if(df.download){
					item.setImage(0, Resources.getImage(Resources.DONE_STATE));
				}else{
					item.setImage(0, Resources.getImage(Resources.WAITING_DOWN_STATE));
				}
				item.setText(1, df.course.courseName);
				String fileName = df.url.substring(df.url.lastIndexOf("/")+1);
				try {
					fileName = java.net.URLDecoder.decode(fileName,"GBK");
				} catch (UnsupportedEncodingException e) {}
				
				item.setText(2, fileName);
				item.setImage(2,Resources.getLogoImage(Helper.getFileExt(fileName)));
				item.setText(3, "0");
				item.setText(4, "-");
		}
		for(TableColumn tc:table.getColumns()){
			tc.pack();
		}
	}
	
	public void startDownload(){
		thread = new Thread(new Runnable(){

			@Override
			public void run() {
				int l = downList.size();
				for(int i =0;i<l;i++){
					if(!Display.getDefault().isDisposed()){
						try {
							this.finalize();
						} catch (Throwable e) {
						}
					}
					DownloadFile df=downList.get(i);
					if(!df.download){
						DS.current_download = i;
						DS.time= System.currentTimeMillis();
						
						Display.getDefault().syncExec(new Runnable(){
							@Override
							public void run() {
								//下载前修改
								TableItem item = table.getItem(DS.current_download);
								item.setImage(0, Resources.getImage(Resources.DOWNLOAD_STATE));
								table.update();
							}
						});
						try {
							Main.tssClient.downloadFile(Helper.dlFileLocation(df.url, df.course), df.url);
							DS.fileSize = Main.tssClient.fileSize/1024;
							df.download=true;
						} catch (Exception e) {
						}

						Display.getDefault().syncExec(new Runnable(){
							@Override
							public void run() {
								//下载后修改
								TableItem item = table.getItem(DS.current_download);
								item.setImage(0, Resources.getImage(Resources.DONE_STATE));
								item.setText(3, DS.fileSize+"KB");
								item.setText(4, String.valueOf(System.currentTimeMillis()-DS.time)+"ms");
								table.update();
							}
						});
					}
				}
			}
			
		});
		thread.start();
	}
	
	public void addDownloadCourse(Course course){
		for(DownloadFile df:this.downList){
			if(df.course==course){
				return;
			}
		}
		for(String url:course.unDWFilesURL){
			DownloadFile df = new DownloadFile();
			df.course=course;
			df.download=false;
			df.url=url;
			downList.add(df);
		}
	}
	
	static class DownloadState{
		public int current_download= -1;
		public long time = 0;
		public boolean stop = false;
		public long fileSize;
	}

}
