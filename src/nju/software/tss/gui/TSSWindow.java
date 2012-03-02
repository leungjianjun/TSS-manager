package nju.software.tss.gui;

import nju.software.tss.action.LoginAction;
import nju.software.tss.resources.ImageFactory;
import nju.software.tss.resources.Resources;

import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.StatusLineManager;
import org.eclipse.jface.action.ToolBarManager;
import org.eclipse.jface.window.ApplicationWindow;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabFolder2Adapter;
import org.eclipse.swt.custom.CTabFolderEvent;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.events.TreeEvent;
import org.eclipse.swt.events.TreeListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;
import org.jsoup.TSSClient;


/**
 * Main interface of the TSS-manager. It inherit the jface ApplicationWindow 
 * , which contains MenuBar, TooBar and StatusLine
 * 
 * @author ljj09
 *
 */
public class TSSWindow extends ApplicationWindow {
	
	public static TSSClient tssClient;
	
	LoginAction loginAction;
	
	public TSSWindow(Shell shell) {
		super(shell);
		addAction();
		addMenuBar();
		addToolBar(SWT.FLAT | SWT.WRAP);
		addStatusLine();
		//this.getShell().setSize(500, 300);
		tssClient = new TSSClient();
	}
	
	public void addAction(){
		loginAction = new LoginAction(getShell());
	}
	
	/**
	 * 创建状态栏
	 */
	protected StatusLineManager createStatusLineManager() {
		StatusLineManager statusLineManager = new ExtendedStatusLineManager();
		return statusLineManager;
	}
	
	protected ToolBarManager createToolBarManager(int style) {
		ToolBarManager toolbar=new ToolBarManager();
		toolbar.add(loginAction);
		return toolbar;
		
	}
	
	public StatusLineManager getStatusLineManager(){
		return super.getStatusLineManager();
	}
	
	protected MenuManager createMenuManager() {
		MenuManager menubar=new MenuManager();
		MenuManager fileMenu=new MenuManager("&Account");
		fileMenu.add(loginAction);
		menubar.add(fileMenu);
		 return menubar;
	}
	
	protected Control createContents(Composite parent) {
		final SashForm form = new SashForm(parent,SWT.HORIZONTAL|SWT.FLAT);
		form.setLayout(new FillLayout());
		
		Composite child1 = new Composite(form,SWT.NONE);
		child1.setLayout(new FillLayout());
		//new Label(child1,SWT.NONE).setText("窗口1");
		createSidebar(child1);
		// 创建自定义选项卡对象
		final CTabFolder folder = new CTabFolder(form, SWT.BORDER);
		// 设置选项卡的布局，通过布局的设置呈现出最大化和最小化的外观
		folder.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
		// 设置复杂的选项卡，也就是带有圆角的选项卡标签
		folder.setSimple(false);
		// 设置未选中标签，图标和关闭按钮的状态
		folder.setUnselectedImageVisible(true);
		folder.setUnselectedCloseVisible(true);
		// 设置前景色和背景色
		folder.setSelectionForeground(Display.getCurrent().getSystemColor(SWT.COLOR_WHITE));
		folder.setSelectionBackground(Display.getCurrent().getSystemColor(SWT.COLOR_BLUE));
		// 显示最大化和最小化按钮
		folder.setMinimizeVisible(true);
		folder.setMaximizeVisible(true);	
		
		Image image = Resources.getImage(Resources.SAMPLES_ICON);
		// 创建选项卡标签对象
		for (int i = 1; i < 5; i++) {
			CTabItem item = new CTabItem(folder, SWT.CLOSE);
			item.setText("选项卡 " + i);
			item.setImage(image);
			// 每个选项卡中放置一个Text文本框
			Text text = new Text(folder, SWT.MULTI | SWT.V_SCROLL| SWT.H_SCROLL);
			// 文本框中的文字带有\n表示，显示时换到下一行
			text.setText("这是第" + i + "页:\n该选项卡仿照Eclipse设计\n最大化和最小化按钮都可以使用");
			item.setControl(text);
		}
		// 注册选项卡事件
		folder.addCTabFolder2Listener(new CTabFolder2Adapter() {
			// 当单击最小化按钮时触发的事件
			public void minimize(CTabFolderEvent event) {
				// 设置选项卡的状态为最小化，选项卡的状态决定显示在右上角的窗口按钮
				folder.setMinimized(true);
				// 改变选项卡的布局，呈现最小化状态
				folder.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true,false));
				// 刷新布局，否则重新设置的布局将不起作用
				//shell.layout(true);
			}

			// 当单击最大化按钮时触发的事件
			public void maximize(CTabFolderEvent event) {
				folder.setMaximized(true);
				//folder.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true,true));
				form.setMaximizedControl(folder);
				//shell.layout(true);
			}

			// 当单击还原按钮时触发的事件
			public void restore(CTabFolderEvent event) {
				folder.setMinimized(false);
				folder.setMaximized(false);
				//folder.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true,false));
				form.setMaximizedControl(null);
				//shell.layout(true);
			}
		});
				
		form.setWeights(new int[] {30,70});
		return parent;
	}
	
	private void createSidebar(Composite parent){
		final Tree tree = new Tree(parent, SWT.BORDER);
		createTreeRoot(tree);
		createTreeRoot(tree);
		createTreeRoot(tree);
		createTreeRoot(tree);
	}
	
	private void createTreeRoot(Tree tree) {
		// 创建树的一个根节点
		TreeItem root = new TreeItem(tree, SWT.NULL);
		root.setText("根节点");
		// 创建子孙节点
		TreeItem child1 = new TreeItem(root, SWT.NULL);
		child1.setText("子孙1");
		TreeItem child2 = new TreeItem(root, SWT.NULL);
		child2.setText("子孙2");
		TreeItem child3 = new TreeItem(root, SWT.NULL);
		child3.setText("子孙3");

		TreeItem child11 = new TreeItem(child1, SWT.NULL);
		child11.setText("子孙11");
		TreeItem child12 = new TreeItem(child1, SWT.NULL);
		child12.setText("子孙12");

		TreeItem child111 = new TreeItem(child11, SWT.NULL);
		child111.setText("子孙111");
		TreeItem child112 = new TreeItem(child11, SWT.NULL);
		child112.setText("子孙112");
		// 调用convertImage方法来设置树的图标
		convertImage(tree);
		// 为树注册树监听事件
		tree.addTreeListener(new TreeListener() {
			// 当折叠树节点时
			public void treeCollapsed(TreeEvent e) {
				// 首先获得触发事件的TreeItem
				TreeItem item = (TreeItem) e.item;
				// 将该节点的图标设置为关闭状态
				item.setImage(Resources.getImage(Resources.TOC_CLOSED));
			}

			// 当展开树节点时
			public void treeExpanded(TreeEvent e) {
				TreeItem item = (TreeItem) e.item;
				item.setImage(Resources.getImage(Resources.TOC_OPEN));
			}

		});
	}

	// 设置树图标的方法
	public static void convertImage(Tree tree) {
		// 这里假设只有一个根节点
		TreeItem[] items = tree.getItems();
		// 首先根据根节点的状态设置图标
		if (items[0].getExpanded())// 如果该节点为展开状态
			items[0].setImage(Resources.getImage(Resources.TOC_OPEN));
		else
			// 否则，如果为折叠状态
			items[0].setImage(Resources.getImage(Resources.TOC_CLOSED));
		// 设置该根节点的图标
		setChildImage(items[0]);
	}

	// 设置一个节点的方法，该方法非常重要，要理解该方法的递归用法
	// 参数item可以把单独看作是树中的某一个TreeItem
	public static void setChildImage(TreeItem item) {
		// 首先获得该TreeItem的所有子TreeItem
		TreeItem[] items = item.getItems();
		// 循环每一个TreeItem
		for (int i = 0; i < items.length; i++) {
			// 如果这个TreeItem下没有子孙
			if (items[i].getItems().length == 0)
				items[i].setImage(Resources.getImage(Resources.TOPIC));
			else {// 如果这个TreeItem有多个子孙
					// 如果这个TreeItem是展开状态，则设置展开的图片
				if (items[i].getExpanded())
					items[i].setImage(ImageFactory
							.loadImage(ImageFactory.TOC_OPEN));
				else
					// 否则，则设置折叠的图片
					items[i].setImage(ImageFactory
							.loadImage(ImageFactory.TOC_CLOSED));
				// 要为该TreeItem得子孙设置图标，递归调用setChildImage方法
				setChildImage(items[i]);
			}
		}
	}
	
	public boolean close() {
		tssClient.exit();
		return super.close();
	}

}
