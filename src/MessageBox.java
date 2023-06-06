//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.border.SoftBevelBorder;

class MessageBox extends JDialog implements ActionListener {
    private Container c;
    private JButton bt;

    public MessageBox(JFrame var1, String var2, String var3) {
        super(var1, false);
        this.setTitle(var2);
        this.c = this.getContentPane();
        this.c.setLayout((LayoutManager)null);
        JLabel var4 = new JLabel(var3);
        var4.setFont(new Font("SanSerif", 0, 12));
        var4.setBounds(20, 10, 190, 20);
        this.c.add(var4);
        this.bt = new JButton("확 인");
        this.bt.setBounds(60, 40, 70, 25);
        this.bt.setFont(new Font("SanSerif", 0, 12));
        this.bt.setBorder(new SoftBevelBorder(0));
        this.bt.addActionListener(this);
        this.c.add(this.bt);
        Dimension var5 = this.getToolkit().getScreenSize();
        this.setSize(200, 100);
        this.setLocation(var5.width / 2 - this.getWidth() / 2, var5.height / 2 - this.getHeight() / 2);
        this.show();
        this.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent var1) {
                MessageBox.this.dispose();
            }
        });
    }

    public void actionPerformed(ActionEvent var1) {
        if (var1.getSource() == this.bt) {
            this.dispose();
        }

    }
}
