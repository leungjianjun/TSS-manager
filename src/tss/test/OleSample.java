package tss.test;

import java.io.File;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.ole.win32.OLE;
import org.eclipse.swt.ole.win32.OleClientSite;
import org.eclipse.swt.ole.win32.OleFrame;
import org.eclipse.swt.widgets.*;

public class OleSample {
	private Shell sShell;
	private MenuItem[] fileItem;//OLE的菜单项
	private OleClientSite clientSite;//OLE控件对象
	private OleFrame frame;//OLE的面板的对象
	private File openFile;//打开的文件
	public static void main(String[] args) {
		Display display = Display.getDefault();
		OleSample thisClass = new OleSample();
		thisClass.createSShell();
		thisClass.sShell.open();
		while (!thisClass.sShell.isDisposed()) {
			if (!display.readAndDispatch())
				display.sleep();
		}
		thisClass.clientSite.dispose();
		display.dispose();
	}

	private void createSShell() {
		sShell = new Shell();
		sShell.setText("OLE Sample");
		sShell.setLayout(new FillLayout());
		createMenu();
		sShell.setSize(new Point(300, 200));
	}
	//创建OLE控件对象
	private void createOle() {
		frame = new OleFrame(sShell, SWT.NONE);
		frame.setFileMenus(fileItem);
		if (openFile != null)
			clientSite = new OleClientSite(frame, SWT.NONE, openFile);
		clientSite.doVerb(OLE.OLEIVERB_PRIMARY);
	}

	private void createMenu() {
		Menu main = new Menu(sShell, SWT.BAR);

		MenuItem file = new MenuItem(main, SWT.CASCADE);
		file.setText("文件(&F)");

		Menu fileMenu = new Menu(file);
		fileItem = new MenuItem[2];
		fileItem[0] = new MenuItem(fileMenu, SWT.PUSH);
		fileItem[0].setText("打开");
		fileItem[1] = new MenuItem(fileMenu, SWT.PUSH);
		fileItem[1].setText("保存");
		file.setMenu(fileMenu);

		sShell.setMenuBar(main);

		fileItem[0].addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				FileDialog dialog = new FileDialog(sShell, SWT.OPEN);
				dialog.setFilterExtensions(new String[] { "*.doc", "*.*" });
				String file = dialog.open();
				if (file != null) {
					openFile = new File(file);
					//打开OLE控件
					createOle();
				}
			}

		});
		fileItem[1].addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				//如果打开文件被修改过
				if (clientSite.isDirty()) {
					//创建一个临时文件
					File tempFile = new File(openFile.getAbsolutePath() + ".tmp");
					openFile.renameTo(tempFile);
					//如果保存成功，则删除临时文件，否则恢复到临时文件保存的状态
					if (clientSite.save(openFile, true))
						tempFile.delete();
					else
						tempFile.renameTo(openFile);
				}
			}
		});
	}

}
