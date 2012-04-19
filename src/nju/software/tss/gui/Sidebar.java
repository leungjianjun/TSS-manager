package nju.software.tss.gui;

import java.io.File;

import nju.software.tss.resources.ImageFactory;
import nju.software.tss.resources.Resources;
import nju.software.tss.util.Config;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.TreeEvent;
import org.eclipse.swt.events.TreeListener;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.ole.win32.OLE;
import org.eclipse.swt.ole.win32.OleClientSite;
import org.eclipse.swt.ole.win32.OleFrame;
import org.eclipse.swt.program.Program;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;

@SuppressWarnings("deprecation")
public class Sidebar {
	
	OleFrame frame;
	
	public Sidebar(Composite parent){
		final CTabFolder tabFolder = new CTabFolder(parent, SWT.CLOSE|SWT.BORDER);
		tabFolder.setLayout(new FillLayout());
		tabFolder.setTabHeight(20);
		//第一个tab
		CTabItem localItem = new CTabItem(tabFolder, SWT.NONE);
		localItem.setText("本地课程");
		final Tree localTree = new Tree(tabFolder, SWT.BORDER|SWT.SINGLE);
		localItem.setControl(localTree);
		//第二个tab
		CTabItem remoteItem = new CTabItem(tabFolder, SWT.NONE);
		remoteItem.setText("远程课程");
		final Tree remoteTree = new Tree(tabFolder, SWT.BORDER|SWT.SINGLE);
		remoteItem.setControl(remoteTree);
		
		buildLocaleTree(localTree);
		buildRemoteTree(remoteTree);
				
	}
	
	private void buildLocaleTree(Tree localTree){
		File workDir = new File(Config.preferenceStore().getString(Config.WORKSPACE));
		if(!workDir.isDirectory() || !workDir.exists()){
			MessageDialog.openError(Display.getCurrent().getActiveShell(), "读取配置发生错误", "读取工作目录有错，请重新配置");
		}
		for(File file:workDir.listFiles()){//按学期分
			if(file.isDirectory()) createFileTree(localTree,file);
		}
	}
	
	private void buildRemoteTree(Tree remoteTree){
		File workDir = new File(Config.preferenceStore().getString(Config.WORKSPACE));
		if(!workDir.isDirectory() || !workDir.exists()){
			MessageDialog.openError(Display.getCurrent().getActiveShell(), "读取配置发生错误", "读取工作目录有错，请重新配置");
		}
		for(File file:workDir.listFiles()){//按学期分
			if(file.isDirectory()) createFileTree(remoteTree,file);
		}
	}
	
	private void createFileTree(Tree tree,File dir) {
		// 创建树的一个根节点
		TreeItem root = new TreeItem(tree, SWT.NULL);
		root.setImage(Resources.getImage(Resources.SEMESTER_ICON));
		root.setText(dir.getName());
		// 创建子孙节点
		for(File file:dir.listFiles()){
			TreeItem child = new TreeItem(root, SWT.NULL);
			child.setText(file.getName());
			child.setData(file.getAbsolutePath());
			if(file.isDirectory()){
				child.setImage(Resources.getImage(Resources.FOLDER_ICON));
				_createFileTree(child,file);
			}else{
				child.setImage(Resources.getLogoImage(getFileExt(file)));
			}
		}
		
		tree.addSelectionListener(new SelectionAdapter(){
			public void widgetDefaultSelected(SelectionEvent e){
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
			item.setImage(Resources.getLogoImage(getFileExt(openFile)));
			item.setControl(frame);
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
				child.setImage(Resources.getLogoImage(getFileExt(file)));
			}
		}
	}
	
	private String getFileExt(File file){
		String fileName = file.getName();
		int i = fileName.lastIndexOf('.');
		if(i<0){
			return null;
		}
		return fileName.substring(i);
	}
	
	

}
