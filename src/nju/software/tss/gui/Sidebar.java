package nju.software.tss.gui;

import java.io.File;
import java.io.IOException;

import nju.software.tss.model.Course;
import nju.software.tss.model.Section;
import nju.software.tss.resources.Resources;
import nju.software.tss.util.Config;
import nju.software.tss.util.Helper;
import nju.software.tss.util.Type;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.ole.win32.OLE;
import org.eclipse.swt.ole.win32.OleClientSite;
import org.eclipse.swt.ole.win32.OleFrame;
import org.eclipse.swt.program.Program;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;

public class Sidebar {
	private static final Color color = new Color(Display.getCurrent(),153,204,255);
	private static int time = 0;//已知问题
	
	public final CTabFolder tabFolder;
	final Tree localTree;
	final Tree remoteTree;
	TreeItem selected;
	
	public Sidebar(Composite parent){
		
		tabFolder = new CTabFolder(parent, SWT.CLOSE|SWT.BORDER);
		localTree = new Tree(tabFolder, SWT.BORDER|SWT.SINGLE);
		remoteTree = new Tree(tabFolder, SWT.BORDER|SWT.SINGLE);
		
		buildTab(parent);//创建sidebar的tab
		buildLocaleTree();//创建本地课程的树
		localTabRightClickMenu();//创建本地课程的右键菜单
		buildRemoteTree();//创建远程课程的树
		remoteTabRightClickMenu();//创建远程课程的右键菜单
	}
	
	
	private void buildTab(Composite parent){
		
		tabFolder.setLayout(new FillLayout());
		tabFolder.setTabHeight(20);
		//第一个tab
		CTabItem localItem = new CTabItem(tabFolder, SWT.NONE);
		localItem.setText("本地课程");
		
		localItem.setControl(localTree);
		//第二个tab
		CTabItem remoteItem = new CTabItem(tabFolder, SWT.NONE);
		remoteItem.setText("远程课程");
		
		remoteItem.setControl(remoteTree);
	}
	
	public void refreshLocal(){
		buildLocaleTree();
	}
	
	public void refreshRemote(){
		buildRemoteTree();
	}
	
	private void buildLocaleTree(){
		localTree.removeAll();
		File workDir = new File(Config.preferenceStore().getString(Config.WORKSPACE));
		if(!workDir.isDirectory() || !workDir.exists()){
			MessageDialog.openError(Display.getCurrent().getActiveShell(), "读取配置发生错误", "读取工作目录有错，请重新配置");
		}
		for(File file:workDir.listFiles()){//按学期分
			if(file.isDirectory()) createFileTree(localTree,file);
		}		
	}
	
	private void buildRemoteTree(){
		remoteTree.removeAll();
		try {
			if(!Main.tssClient.checkLogin()){
				TreeItem root = new TreeItem(remoteTree, SWT.NULL);
				root.setImage(Resources.getImage(Resources.TEST_ICON));	
				remoteTree.addMouseListener(new MouseAdapter(){
					public void mouseDown(MouseEvent e) {
						if(!isLogin()) Main.tssWindow.loginAction.run();
						remoteTree.removeMouseListener(this);
						Main.tssWindow.sidebar.refreshRemote();
					}
					
					private boolean isLogin(){
						try {
							return Main.tssClient.checkLogin();
						} catch (IOException e1) {
							return false;
						}
					}
				});
				root.setText("请先登录");
				return;
			}
		} catch (IOException e) {
			return;
		}
		createMyCourseTree(remoteTree);
		careteActiveCourseTree(remoteTree);
		careteEndedCourseTree(remoteTree);
	}
	
	private void localTabRightClickMenu(){
		localTree.addMouseListener(new MouseAdapter(){
			public void mouseDown(MouseEvent e) {
				if(e.button==3){//右键菜单事件
					Menu rcMenu=new Menu(Main.tssWindow.getShell(),SWT.POP_UP);
					MenuItem refrItem=new MenuItem(rcMenu,SWT.NONE);
					refrItem.setText("&本地刷新\tCtrl+I");
					refrItem.setImage(Resources.getImage(Resources.REFRESH_LOGO));
					refrItem.addSelectionListener(new SelectionAdapter(){
						public void widgetSelected(SelectionEvent e) {
							Main.tssWindow.relcAction.run();
						}
					});
					MenuItem addTermsItem=new MenuItem(rcMenu,SWT.NONE);
					addTermsItem.setText("&新建学期\tCtrl+T");
					addTermsItem.setImage(Resources.getImage(Resources.ADD_TERMS));
					addTermsItem.addSelectionListener(new SelectionAdapter(){
						public void widgetSelected(SelectionEvent e) {
							Main.tssWindow.addTermsAction.run();
						}
					});
					MenuItem openDownloadItem=new MenuItem(rcMenu,SWT.NONE);
					openDownloadItem.setText("&下载管理\tCtrl+I");
					openDownloadItem.setImage(Resources.getImage(Resources.DOWNLOAD_MANAGER));
					openDownloadItem.addSelectionListener(new SelectionAdapter(){
						public void widgetSelected(SelectionEvent e) {
							Main.tssWindow.openDownloadManagerAction.run();
						}
					});
					MenuItem syncCurTermsItem=new MenuItem(rcMenu,SWT.NONE);
					syncCurTermsItem.setText("&同步本学期\tCtrl+I");
					syncCurTermsItem.setImage(Resources.getImage(Resources.DOWNLOAD_MANAGER));
					syncCurTermsItem.addSelectionListener(new SelectionAdapter(){
						public void widgetSelected(SelectionEvent e) {
							Main.tssWindow.syncCurrentTermsAction.run();
						}
					});
					TreeItem selected = remoteTree.getItem(new Point(e.x,e.y));
					/*if(selected!=null){
						String type = (String) selected.getData();
					}*/
					localTree.setMenu(rcMenu);
				}
			}
		});
	}
	
	private void remoteTabRightClickMenu(){
		remoteTree.addMouseListener(new MouseAdapter(){
			public void mouseDown(MouseEvent e) {
				selected = remoteTree.getItem(new Point(e.x,e.y));
				if(e.button==3){//右键菜单事件
					Menu rcMenu=new Menu(Main.tssWindow.getShell(),SWT.POP_UP);
					MenuItem refrItem=new MenuItem(rcMenu,SWT.NONE);
					refrItem.setText("&远程刷新\tCtrl+I");
					refrItem.setImage(Resources.getImage(Resources.REFRESH_LOGO));
					refrItem.addSelectionListener(new SelectionAdapter(){
						public void widgetSelected(SelectionEvent e) {
							Main.tssWindow.rereAction.run();
						}
					});
					
					if(selected!=null && selected.getData()!=null && selected.getData().getClass().getName().contains("Course")){
						final Course c = (Course) selected.getData();
						if(c.terms!=null){//已经是被我添加的课程
							MenuItem fileItem=new MenuItem(rcMenu,SWT.CASCADE);
							fileItem.setText("移动课程到");
							fileItem.setImage(Resources.getImage(Resources.MOVE_COURSE_LOGO));
							Menu fileMenu=new Menu(Main.tssWindow.getShell(),SWT.DROP_DOWN);
							fileItem.setMenu(fileMenu);
							for(String terms:Config.getStringList(Config.TERMS)){
								MenuItem mi = new MenuItem(fileMenu,SWT.RADIO);
								mi.setText(terms);
								if(terms.endsWith(c.terms)){
									mi.setSelection(true);
								}
							}
						}else{//没有被我添加
							MenuItem fileItem=new MenuItem(rcMenu,SWT.CASCADE);
							fileItem.setText("添加课程到");
							fileItem.setImage(Resources.getImage(Resources.ADD_COURSE_LOGO));
							Menu fileMenu=new Menu(Main.tssWindow.getShell(),SWT.DROP_DOWN);
							fileItem.setMenu(fileMenu);
							for(final String terms:Config.getStringList(Config.TERMS)){
								MenuItem mi = new MenuItem(fileMenu,SWT.RADIO);
								mi.setText(terms);
								mi.addSelectionListener(new SelectionAdapter(){
									public void widgetSelected(SelectionEvent e) {
										Main.tssWindow.addCourseAction.run(terms,c);
									}
								});
							}
						}
					}
					remoteTree.setMenu(rcMenu);
				}
			}
		});
	}
	
	private void careteActiveCourseTree(Tree tree){
		// 创建树的一个根节点
		TreeItem root = new TreeItem(tree, SWT.NULL);
		root.setImage(Resources.getImage(Resources.ACTIVECOURSE_LOGO));
		root.setText("在上课程");
		
		try {
			for(Section section:Main.tssClient.getActiveCourse()){
				TreeItem schild = new TreeItem(root, SWT.NULL);
				schild.setText(section.secName);
				schild.setImage(Resources.getImage(Resources.FOLDER_ICON));
				for(Course c:section.courseList){
					TreeItem child = new TreeItem(schild, SWT.NULL);
					if(c.terms!=null){
						child.setBackground(color);
					}
					child.setText(c.courseName);
					child.setData(c);
					child.setImage(Resources.getImage(Resources.FOLDER_ICON));
					
					TreeItem child1 = new TreeItem(child, SWT.NULL);
					child1.setText("home");
					child1.setImage(Resources.getImage(Resources.FILE_LOGO));
					
					TreeItem child2 = new TreeItem(child, SWT.NULL);
					child2.setText("Announcement");
					child2.setImage(Resources.getImage(Resources.FILE_LOGO));
					
					TreeItem child3 = new TreeItem(child, SWT.NULL);
					child3.setText("Lecture Notes");
					child3.setImage(Resources.getImage(Resources.FILE_LOGO));
					
					TreeItem child4 = new TreeItem(child, SWT.NULL);
					child4.setText("Assignment");
					child4.setImage(Resources.getImage(Resources.FILE_LOGO));
					
					TreeItem child5 = new TreeItem(child, SWT.NULL);
					child5.setText("Forum");
					child5.setImage(Resources.getImage(Resources.FILE_LOGO));
				}
			}
		} catch (IOException e) {
		}
	}
	
	private void careteEndedCourseTree(Tree tree){
		// 创建树的一个根节点
		TreeItem root = new TreeItem(tree, SWT.NULL);
		root.setImage(Resources.getImage(Resources.ENDCOURSE_LOGO));
		root.setText("结束课程");
		
		try {
			for(Section section:Main.tssClient.getEndedCourse()){
				TreeItem schild = new TreeItem(root, SWT.NULL);
				schild.setText(section.secName);
				schild.setImage(Resources.getImage(Resources.FOLDER_ICON));
				for(Course c:section.courseList){
					TreeItem child = new TreeItem(schild, SWT.NULL);
					if(c.terms!=null){
						child.setBackground(color);
					}
					child.setText(c.courseName);
					child.setData(c);
					child.setImage(Resources.getImage(Resources.FOLDER_ICON));
					
					TreeItem child1 = new TreeItem(child, SWT.NULL);
					child1.setText("home");
					child1.setImage(Resources.getImage(Resources.FILE_LOGO));
					
					TreeItem child2 = new TreeItem(child, SWT.NULL);
					child2.setText("Announcement");
					child2.setImage(Resources.getImage(Resources.FILE_LOGO));
					
					TreeItem child3 = new TreeItem(child, SWT.NULL);
					child3.setText("Lecture Notes");
					child3.setImage(Resources.getImage(Resources.FILE_LOGO));
					
					TreeItem child4 = new TreeItem(child, SWT.NULL);
					child4.setText("Assignment");
					child4.setImage(Resources.getImage(Resources.FILE_LOGO));
					
					TreeItem child5 = new TreeItem(child, SWT.NULL);
					child5.setText("Forum");
					child5.setImage(Resources.getImage(Resources.FILE_LOGO));
				}
			}
		} catch (IOException e) {
		}
	}	
	
	private void createMyCourseTree(Tree tree){
		// 创建树的一个根节点
		TreeItem root = new TreeItem(tree, SWT.NULL);
		root.setImage(Resources.getImage(Resources.MYCOURSE_LOGO));
		root.setText("我的课程");
		
		try {
			for(Course c:Main.tssClient.getMyCourses()){
				TreeItem child = new TreeItem(root, SWT.NULL);
				if(c.terms!=null){
					child.setBackground(color);
				}
				
				child.setText(c.courseName);
				child.setData(c);
				child.setImage(Resources.getImage(Resources.FOLDER_ICON));
				
				TreeItem child1 = new TreeItem(child, SWT.NULL);
				child1.setText("home");
				child1.setImage(Resources.getImage(Resources.FILE_LOGO));
				
				TreeItem child2 = new TreeItem(child, SWT.NULL);
				child2.setText("Announcement");
				child2.setImage(Resources.getImage(Resources.FILE_LOGO));
				
				TreeItem child3 = new TreeItem(child, SWT.NULL);
				child3.setText("Lecture Notes");
				child3.setImage(Resources.getImage(Resources.FILE_LOGO));
				
				TreeItem child4 = new TreeItem(child, SWT.NULL);
				child4.setText("Assignment");
				child4.setImage(Resources.getImage(Resources.FILE_LOGO));
				
				TreeItem child5 = new TreeItem(child, SWT.NULL);
				child5.setText("Forum");
				child5.setImage(Resources.getImage(Resources.FILE_LOGO));
			}
		} catch (IOException e) {
			//网络异常
		}
	}
	
	private void createFileTree(Tree tree,File dir) {
		// 创建树的一个根节点
		TreeItem root = new TreeItem(tree, SWT.NULL);
		root.setData(Type.TERMS);
		root.setText(dir.getName());
		if(dir.getName().endsWith(Config.preferenceStore().getString(Config.NOW_TERMS))){
			root.setImage(Resources.getImage(Resources.CURRENT_TERMS));
		}else{
			root.setImage(Resources.getImage(Resources.SEMESTER_ICON));
		}
		// 创建子孙节点
		for(File file:dir.listFiles()){
			TreeItem child = new TreeItem(root, SWT.NULL);
			child.setData(Type.COURSE);
			child.setText(file.getName());
			child.setData(file.getAbsolutePath());
			if(file.isDirectory()){
				child.setImage(Resources.getImage(Resources.FOLDER_ICON));
				_createFileTree(child,file);
			}else{
				child.setImage(Resources.getLogoImage(Helper.getFileExt(file)));
			}
		}
		
		tree.addSelectionListener(new SelectionAdapter(){
			public void widgetDefaultSelected(SelectionEvent e){
				if(e.time==time){
					return;
				}
				time=e.time;//已知问题
				_createOLETab((TreeItem) e.item);
			}
		});
	}
	
	private void _createOLETab(TreeItem selected){
		
		//查看是否已经存在该tab，如果存在，只是激活，否则创建tab
		for(CTabItem item:Main.tssWindow.folder.getItems()){
			if(item.getData()!=null && item.getData().equals(selected.getData())){
				//获取焦点
				//Main.tssWindow.folder.setSelection(item);
				return;
			}
		}
		
		String fileName = (String) selected.getData();
		//
		try{
			File openFile = new File(fileName);
			OleFrame frame = new OleFrame(Main.tssWindow.folder, SWT.NONE);
			OleClientSite clientSite = new OleClientSite(frame, SWT.NONE, openFile);
			clientSite.doVerb(OLE.OLEIVERB_PRIMARY);
			//创建tab
			CTabItem item = new CTabItem(Main.tssWindow.folder, SWT.CLOSE);
			item.setData(fileName);
			item.setText(openFile.getName());
			item.setImage(Resources.getLogoImage(Helper.getFileExt(openFile)));
			item.setControl(frame);
			Main.tssWindow.folder.setSelection(item);
		}catch(Exception e){
			//如果不支持OLE，则外部打开
			Program.launch(fileName);
		}
		
	}
	
	private void _createFileTree(TreeItem parent,File dir){
		for(File file:dir.listFiles()){
			TreeItem child = new TreeItem(parent, SWT.NULL);
			child.setText(file.getName());
			child.setData(file.getAbsolutePath());
			if(file.isDirectory()){
				child.setImage(Resources.getImage(Resources.FOLDER_ICON));
				_createFileTree(child,file);
			}else{
				child.setImage(Resources.getLogoImage(Helper.getFileExt(file)));
			}
		}
	}
	
}
