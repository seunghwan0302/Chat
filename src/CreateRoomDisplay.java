//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

class CreateRoomDisplay extends JDialog implements ActionListener, ItemListener {
    private ClientThread client;
    private String roomName;
    private String str_password;
    private int roomMaxUser;
    private int isRock;
    private JFrame main;
    private Container c;
    private JTextField tf;
    private JPanel radioPanel;
    private JRadioButton radio1;
    private JRadioButton radio2;
    private JRadioButton radio3;
    private JRadioButton radio4;
    private JRadioButton rock;
    private JRadioButton unrock;
    private JPasswordField password;
    private JButton ok;
    private JButton cancle;

    public CreateRoomDisplay(JFrame var1, ClientThread var2) {
        super(var1, true);
        this.main = var1;
        this.setTitle("대화방 개설");
        this.client = var2;
        this.isRock = 0;
        this.roomMaxUser = 2;
        this.str_password = "0";
        this.c = this.getContentPane();
        this.c.setLayout((LayoutManager)null);
        JLabel var3 = new JLabel("방제목");
        var3.setBounds(10, 10, 100, 20);
        var3.setForeground(Color.blue);
        this.c.add(var3);
        this.tf = new JTextField();
        this.tf.setBounds(10, 30, 270, 20);
        this.c.add(this.tf);
        var3 = new JLabel("최대인원");
        var3.setForeground(Color.blue);
        var3.setBounds(10, 60, 100, 20);
        this.c.add(var3);
        this.radioPanel = new JPanel();
        this.radioPanel.setLayout(new FlowLayout(0, 5, 0));
        ButtonGroup var4 = new ButtonGroup();
        this.radio1 = new JRadioButton("2명");
        this.radio1.setSelected(true);
        this.radio1.addItemListener(this);
        var4.add(this.radio1);
        this.radio2 = new JRadioButton("3명");
        this.radio2.addItemListener(this);
        var4.add(this.radio2);
        this.radio3 = new JRadioButton("4명");
        this.radio3.addItemListener(this);
        var4.add(this.radio3);
        this.radio4 = new JRadioButton("5명");
        this.radio4.addItemListener(this);
        var4.add(this.radio4);
        this.radioPanel.add(this.radio1);
        this.radioPanel.add(this.radio2);
        this.radioPanel.add(this.radio3);
        this.radioPanel.add(this.radio4);
        this.radioPanel.setBounds(10, 80, 280, 20);
        this.c.add(this.radioPanel);
        var3 = new JLabel("공개여부");
        var3.setForeground(Color.blue);
        var3.setBounds(10, 110, 100, 20);
        this.c.add(var3);
        this.radioPanel = new JPanel();
        this.radioPanel.setLayout(new FlowLayout(0, 5, 0));
        var4 = new ButtonGroup();
        this.unrock = new JRadioButton("공개");
        this.unrock.setSelected(true);
        this.unrock.addItemListener(this);
        var4.add(this.unrock);
        this.rock = new JRadioButton("비공개");
        this.rock.addItemListener(this);
        var4.add(this.rock);
        this.radioPanel.add(this.unrock);
        this.radioPanel.add(this.rock);
        this.radioPanel.setBounds(10, 130, 280, 20);
        this.c.add(this.radioPanel);
        var3 = new JLabel("비밀번호");
        var3.setForeground(Color.blue);
        var3.setBounds(10, 160, 100, 20);
        this.c.add(var3);
        this.password = new JPasswordField();
        this.password.setBounds(10, 180, 150, 20);
        this.password.setEditable(false);
        this.c.add(this.password);
        this.ok = new JButton("확 인");
        this.ok.setForeground(Color.blue);
        this.ok.setBounds(75, 220, 70, 30);
        this.ok.addActionListener(this);
        this.c.add(this.ok);
        this.cancle = new JButton("취 소");
        this.cancle.setForeground(Color.blue);
        this.cancle.setBounds(155, 220, 70, 30);
        this.cancle.addActionListener(this);
        this.c.add(this.cancle);
        Dimension var5 = this.getToolkit().getScreenSize();
        this.setSize(300, 300);
        this.setLocation(var5.width / 2 - this.getWidth() / 2, var5.height / 2 - this.getHeight() / 2);
        this.show();
        this.addWindowListener(new WindowAdapter() {
            public void windowActivated(WindowEvent var1) {
                CreateRoomDisplay.this.tf.requestFocusInWindow();
            }
        });
        this.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent var1) {
                CreateRoomDisplay.this.dispose();
            }
        });
    }

    public void itemStateChanged(ItemEvent var1) {
        if (var1.getSource() == this.unrock) {
            this.isRock = 0;
            this.str_password = "0";
            this.password.setText("");
            this.password.setEditable(false);
        } else if (var1.getSource() == this.rock) {
            this.isRock = 1;
            this.password.setEditable(true);
        } else if (var1.getSource() == this.radio1) {
            this.roomMaxUser = 2;
        } else if (var1.getSource() == this.radio2) {
            this.roomMaxUser = 3;
        } else if (var1.getSource() == this.radio3) {
            this.roomMaxUser = 4;
        } else if (var1.getSource() == this.radio4) {
            this.roomMaxUser = 5;
        }

    }

    public void actionPerformed(ActionEvent var1) {
        if (var1.getSource() == this.ok) {
            if (this.tf.getText().equals("")) {
                JOptionPane.showMessageDialog(this.main, "방제목을 입력하세요", "대화방 개설.", 0);
            } else {
                this.roomName = this.tf.getText();
                if (this.isRock == 1) {
                    this.str_password = this.password.getText();
                }

                if (this.isRock == 1 && this.str_password.equals("")) {
                    JOptionPane.showMessageDialog(this.main, "비밀번호를 입력하세요", "대화방 개설.", 0);
                } else {
                    this.client.requestCreateRoom(this.roomName, this.roomMaxUser, this.isRock, this.str_password);
                    this.dispose();
                }
            }
        } else {
            this.dispose();
        }

    }
}
