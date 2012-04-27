package tss.test;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.TableEditor;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.ProgressBar;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;

public class HelloProgressBar {
	public static void main(String[] args) {
		Display display = new Display();
		Shell shell = new Shell(display);
		shell.setLayout(new FillLayout());
		// ����������
		Table table = new Table(shell, SWT.BORDER);
		table.setHeaderVisible(true);
		table.setLinesVisible(true);
		// ������ͷ
		for (int i = 0; i < 3; i++) {
			new TableColumn(table, SWT.NONE);
		}
		table.getColumn(0).setText("a");
		table.getColumn(1).setText("a");
		table.getColumn(2).setText("b");
		// ����10�����
		for (int i = 0; i < 10; i++) {
			//����һ��
			TableItem item = new TableItem(table, SWT.NONE);
			item.setText(new String[] {"a","a","a","a"});
			//item.setText(2, "a");
			//����һ�������
			ProgressBar bar = new ProgressBar(table, SWT.NONE);
			bar.setSelection((i + 1) * 10);
			//����һ���ɱ༭�ı�����
			TableEditor editor = new TableEditor(table);
			editor.grabHorizontal = true;
			editor.grabVertical = true;
			//��������󶨵���Ԫ����
			editor.setEditor(bar, item, 1);
		}
		table.getColumn(0).pack();
		table.getColumn(1).setWidth(100);
		table.getColumn(2).pack();
		table.pack();
		shell.pack();
		shell.open();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
		display.dispose();

	}
}
