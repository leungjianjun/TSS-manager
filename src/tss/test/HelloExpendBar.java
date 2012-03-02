package tss.test;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.ExpandBar;
import org.eclipse.swt.widgets.ExpandItem;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;

import tss.test.resources.Resources;

public class HelloExpendBar {
	public static void main(String[] args) {
	    Display display = new Display();
	    Shell shell = new Shell(display);
	    shell.setLayout(new FillLayout());
	    shell.setText("ExpandBar Example");
	    ExpandBar bar = new ExpandBar(shell, SWT.V_SCROLL);
	    Image image = ImageDescriptor.createFromURL(Resources.class.getResource("test.png")).createImage();
	    

	    // First item
	    Composite composite = new Composite(bar, SWT.NONE);
	    GridLayout layout = new GridLayout (2, false);
	    layout.marginLeft = layout.marginTop = layout.marginRight = layout.marginBottom = 10;
	    layout.verticalSpacing = 10;
	    composite.setLayout(layout);  
	    Label label = new Label (composite, SWT.NONE);
	    label.setImage(display.getSystemImage(SWT.ICON_ERROR));
	    label = new Label (composite, SWT.NONE);
	    label.setText("SWT.ICON_ERROR");
	    label = new Label (composite, SWT.NONE);
	    label.setImage(display.getSystemImage(SWT.ICON_INFORMATION));
	    label = new Label (composite, SWT.NONE);
	    label.setText("SWT.ICON_INFORMATION");
	    label = new Label (composite, SWT.NONE);
	    label.setImage(display.getSystemImage(SWT.ICON_WARNING));
	    label = new Label (composite, SWT.NONE);
	    label.setText("SWT.ICON_WARNING");
	    label = new Label (composite, SWT.NONE);
	    label.setImage(display.getSystemImage(SWT.ICON_QUESTION));
	    label = new Label (composite, SWT.NONE);
	    label.setText("SWT.ICON_QUESTION");
	    ExpandItem item0 = new ExpandItem(bar, SWT.NONE, 0);
	    item0.setText("What is your favorite button");
	    item0.setHeight(composite.computeSize(SWT.DEFAULT, SWT.DEFAULT).y);
	    item0.setControl(composite);
	    item0.setImage(image);

	    item0.setExpanded(true);

	    bar.setSpacing(8);
	    shell.setSize(400, 350);
	    shell.open();
	    while (!shell.isDisposed()) {
	      if (!display.readAndDispatch()) {
	        display.sleep();
	      }
	    }
	    image.dispose();
	    display.dispose();
	  }

}
