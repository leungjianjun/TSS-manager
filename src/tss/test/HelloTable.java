package tss.test;

import org.eclipse.jface.window.ApplicationWindow;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;

public class HelloTable extends ApplicationWindow{
	
	Table table;
	
	public HelloTable(Shell parentShell) {
		super(parentShell);
		// TODO Auto-generated constructor stub
	}

	// Create the Table and TableColumns 
	protected Table createTable(Composite parent, int mode, Object[] contents) {
	    table = new Table(parent, mode | SWT.SINGLE | SWT.FULL_SELECTION | 
	                      SWT.V_SCROLL | SWT.H_SCROLL);
	    table.setHeaderVisible(true);
	    table.setLinesVisible(true);

	    createTableColumn(table, SWT.LEFT,   "Column 1", 100);
	    createTableColumn(table, SWT.CENTER, "Column 2", 100);
	    createTableColumn(table, SWT.RIGHT,  "Column 3", 100);

	    addTableContents(contents);
	    return table;
	}
	protected TableColumn createTableColumn(Table table, int style, String title, int width) {
	    TableColumn tc = new TableColumn(table, style);
	    tc.setText(title);
	    tc.setResizable(true);
	    tc.setWidth(width);
	    return tc;
	}
	protected void addTableContents(Object[] items) {
	    for (int i = 0; i < items.length; i++) {
	        String[] item = (String[])items[i];
	        TableItem ti = new TableItem(table, SWT.NONE);
	        ti.setText(item);
	    }
	}
	
	// sample creation code
	protected void initGui() {
	    Object[] items = {
	        new String[] {"A", "a", "0"}, new String[] {"B", "b", "1"},
	        new String[] {"C", "c", "2"}, new String[] {"D", "d", "3"},
	        new String[] {"E", "e", "4"}, new String[] {"F", "f", "5"},
	        new String[] {"G", "g", "6"}, new String[] {"H", "h", "7"},
	        new String[] {"I", "i", "8"}, new String[] {"J", "j", "9"}
	    };
	}

}
