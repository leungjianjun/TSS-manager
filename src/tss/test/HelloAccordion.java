package tss.test;

import java.awt.*;  
import java.awt.event.*;  
import javax.swing.*;  
   
public class HelloAccordion {  
    private JPanel getContent() {  
        JPanel panel = new JPanel(new GridBagLayout());  
        GridBagConstraints gbc = new GridBagConstraints();  
        panel.add(new AccordianPanel().getPanel(), gbc);  
        return panel;  
    }  
   
    public static void main(String[] args) {  
        JFrame f = new JFrame();  
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  
        f.getContentPane().add(new HelloAccordion().getContent());  
        f.setSize(400,400);  
        f.setLocation(200,200);  
        f.setVisible(true);  
    }  
}  
   
class AccordianPanel extends JPanel {  
    boolean movingComponents = false;  
    int visibleIndex = 3;  
   
    public AccordianPanel() {  
        setLayout(null);  
        // Add children and compute prefSize.  
        int childCount = 4;  
        Dimension d = new Dimension();  
        int h = 0;  
        for(int j = 0; j < childCount; j++) {  
            ChildPanel child = new ChildPanel(j+1, ml);  
            add(child);  
            d = child.getPreferredSize();  
            child.setBounds(0, h, d.width, d.height);  
            if(j < childCount-1)  
                h += ControlPanel.HEIGHT;  
        }  
        h += d.height;  
        setPreferredSize(new Dimension(d.width, h));  
        // Set z-order for children.  
        setZOrder();  
    }  
   
    private void setZOrder() {  
        Component[] c = getComponents();  
        for(int j = 0; j < c.length-1; j++) {  
            setComponentZOrder(c[j], c.length-1 - j);  
        }  
    }  
   
    private void setChildVisible(int indexToOpen) {  
        // If visibleIndex < indexToOpen, components at  
        // [visibleIndex+1 down to indexToOpen] move up.  
        // If visibleIndex > indexToOpen, components at  
        // [indexToOpen+1 up to visibleIndex] move down.  
        // Collect indices of components that will move  
        // and determine the distance/direction to move.  
        int[] indices = new int[0];  
        int travelLimit = 0;  
        if(visibleIndex < indexToOpen) {  
            travelLimit = ControlPanel.HEIGHT -  
                              getComponent(visibleIndex).getHeight();  
            int n = indexToOpen - visibleIndex;  
            indices = new int[n];  
            for(int j = visibleIndex, k = 0; j < indexToOpen; j++, k++)  
                indices[k] = j + 1;  
        } else if(visibleIndex > indexToOpen) {  
            travelLimit = getComponent(visibleIndex).getHeight() -  
                              ControlPanel.HEIGHT;  
            int n = visibleIndex - indexToOpen;  
            indices = new int[n];  
            for(int j = indexToOpen, k = 0; j < visibleIndex; j++, k++)  
                indices[k] = j + 1;  
        }  
        movePanels(indices, travelLimit);  
        visibleIndex = indexToOpen;  
    }  
   
    private void movePanels(final int[] indices, final int travel) {  
        movingComponents = true;  
        Thread thread = new Thread(new Runnable() {  
            public void run() {  
                Component[] c = getComponents();  
                int limit = travel > 0 ? travel : 0;  
                int count = travel > 0 ? 0 : travel;  
                int dy    = travel > 0 ? 1 : -1;  
                while(count < limit) {  
                    try {  
                        Thread.sleep(25);  
                    } catch(InterruptedException e) {  
                        System.out.println("interrupted");  
                        break;  
                    }  
                    for(int j = 0; j < indices.length; j++) {  
                        // The z-order reversed the order returned  
                        // by getComponents. Adjust the indices to  
                        // get the correct components to relocate.  
                        int index = c.length-1 - indices[j];  
                        Point p = c[index].getLocation();  
                        p.y += dy;  
                        c[index].setLocation(p.x, p.y);  
                    }  
                    repaint();  
                    count++;  
                }  
                movingComponents = false;  
            }  
        });  
        thread.setPriority(Thread.NORM_PRIORITY);  
        thread.start();  
    }  
   
    private MouseListener ml = new MouseAdapter() {  
        public void mousePressed(MouseEvent e) {  
            int index = ((ControlPanel)e.getSource()).id-1;  
            if(!movingComponents)  
                setChildVisible(index);  
        }  
    };  
   
    public JPanel getPanel() {  
        JPanel panel = new JPanel(new GridBagLayout());  
        GridBagConstraints gbc = new GridBagConstraints();  
        panel.setBorder(BorderFactory.createLineBorder(Color.black, 1));  
        panel.add(this, gbc);  
        return panel;  
    }  
}  
   
class ChildPanel extends JPanel {  
    public ChildPanel(int id, MouseListener ml) {  
        setLayout(new BorderLayout());  
        add(new ControlPanel(id, ml), "First");  
        add(getContent(id));  
    }  
   
    private JPanel getContent(int id) {  
        JPanel panel = new JPanel(new GridBagLayout());  
        GridBagConstraints gbc = new GridBagConstraints();  
        gbc.insets = new Insets(2,2,2,2);  
        gbc.weightx = 1.0;  
        gbc.weighty = 1.0;  
        gbc.anchor = gbc.NORTHWEST;  
        panel.add(new JLabel("Panel " + id + " Content"), gbc);  
        return panel;  
    }  
   
    public Dimension getPreferredSize() {  
        return new Dimension(300,150);  
    }  
}  
   
class ControlPanel extends JPanel {  
    int id;  
    JLabel titleLabel;  
    Color c1 = new Color(200,180,180);  
    Color c2 = new Color(200,220,220);  
    Color fontFg = Color.blue;  
    Color rolloverFg = Color.red;  
    public final static int HEIGHT = 45;  
   
    public ControlPanel(int id, MouseListener ml) {  
        this.id = id;  
        setLayout(new BorderLayout());  
        add(titleLabel = new JLabel("Panel " + id, JLabel.CENTER));  
        titleLabel.setForeground(fontFg);  
        Dimension d = getPreferredSize();  
        d.height = HEIGHT;  
        setPreferredSize(d);  
        addMouseListener(ml);  
        addMouseListener(listener);  
    }  
   
    protected void paintComponent(Graphics g) {  
        int w = getWidth();  
        Graphics2D g2 = (Graphics2D)g;  
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,  
                            RenderingHints.VALUE_ANTIALIAS_ON);  
        g2.setPaint(new GradientPaint(w/2, 0, c1, w/2, HEIGHT/2, c2));  
        g2.fillRect(0,0,w,HEIGHT);  
    }  
   
    private MouseListener listener = new MouseAdapter() {  
        public void mouseEntered(MouseEvent e) {  
            titleLabel.setForeground(rolloverFg);  
        }  
   
        public void mouseExited(MouseEvent e) {  
            titleLabel.setForeground(fontFg);  
        }  
    };  
}  
