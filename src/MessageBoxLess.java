//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.border.SoftBevelBorder;

class MessageBoxLess extends JDialog implements ActionListener {
    private Frame client;
    private Container c;

    public MessageBoxLess(JFrame var1, String var2, String var3) {
        super(var1, true);
        this.setTitle(var2);
        this.c = this.getContentPane();
        this.c.setLayout((LayoutManager)null);
        JLabel var4 = new JLabel(var3);
        var4.setFont(new Font("SanSerif", 0, 12));
        var4.setBounds(20, 10, 190, 20);
        this.c.add(var4);
        JButton var5 = new JButton("O K");
        var5.setBounds(60, 40, 70, 25);
        var5.setFont(new Font("SanSerif", 0, 12));
        var5.addActionListener(this);
        var5.setBorder(new SoftBevelBorder(0));
        this.c.add(var5);
        Dimension var6 = this.getToolkit().getScreenSize();
        this.setSize(200, 100);
        this.setLocation(var6.width / 2 - this.getWidth() / 2, var6.height / 2 - this.getHeight() / 2);
        this.show();
        this.client = var1;
    }

    public void actionPerformed(ActionEvent var1) {
        this.dispose();
        System.exit(0);
    }
}
