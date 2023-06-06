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
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.StringTokenizer;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JViewport;
import javax.swing.border.EtchedBorder;
import javax.swing.border.SoftBevelBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

class WaitRoomDisplay extends JFrame implements ActionListener, KeyListener, MouseListener, ChangeListener {
    private ClientThread cc_thread;
    private int roomNumber;
    private String password;
    private String select;
    private boolean isRock;
    private boolean isSelected;
    private JLabel rooms;
    private JLabel waiter;
    private JLabel label;
    public JList roomInfo;
    public JList waiterInfo;
    private JButton create;
    private JButton join;
    private JButton sendword;
    private JButton logout;
    private Font font;
    private JViewport view;
    private JScrollPane jsp3;
    public JTextArea messages;
    public JTextField message;

    public WaitRoomDisplay(ClientThread var1) {
        super("Chat-Application-대기실");
        this.cc_thread = var1;
        this.roomNumber = 0;
        this.password = "0";
        this.isRock = false;
        this.isSelected = false;
        this.font = new Font("SanSerif", 0, 12);
        Container var2 = this.getContentPane();
        var2.setLayout((LayoutManager)null);
        this.rooms = new JLabel("대화방");
        JPanel var3 = new JPanel();
        var3.setLayout((LayoutManager)null);
        var3.setBounds(5, 10, 460, 215);
        var3.setFont(this.font);
        var3.setBorder(new TitledBorder(new EtchedBorder(1), "대화방 목록"));
        this.label = new JLabel("번 호");
        this.label.setBounds(15, 25, 40, 20);
        this.label.setBorder(new SoftBevelBorder(0));
        this.label.setFont(this.font);
        var3.add(this.label);
        this.label = new JLabel("제 목");
        this.label.setBounds(55, 25, 210, 20);
        this.label.setBorder(new SoftBevelBorder(0));
        this.label.setFont(this.font);
        var3.add(this.label);
        this.label = new JLabel("현재/최대");
        this.label.setBounds(265, 25, 60, 20);
        this.label.setBorder(new SoftBevelBorder(0));
        this.label.setFont(this.font);
        var3.add(this.label);
        this.label = new JLabel("공개여부");
        this.label.setBounds(325, 25, 60, 20);
        this.label.setBorder(new SoftBevelBorder(0));
        this.label.setFont(this.font);
        var3.add(this.label);
        this.label = new JLabel("개 설 자");
        this.label.setBounds(385, 25, 58, 20);
        this.label.setBorder(new SoftBevelBorder(0));
        this.label.setFont(this.font);
        var3.add(this.label);
        this.roomInfo = new JList();
        this.roomInfo.setFont(this.font);
        WaitListCellRenderer var4 = new WaitListCellRenderer();
        JScrollPane var5 = new JScrollPane(this.roomInfo);
        this.roomInfo.addMouseListener(this);
        var4.setDefaultTab(20);
        var4.setTabs(new int[]{40, 265, 285, 315, 375, 430});
        this.roomInfo.setCellRenderer(var4);
        var5.setBounds(15, 45, 430, 155);
        var3.add(var5);
        var2.add(var3);
        var3 = new JPanel();
        var3.setLayout((LayoutManager)null);
        var3.setBounds(470, 10, 150, 215);
        var3.setBorder(new TitledBorder(new EtchedBorder(1), "대기자"));
        this.waiterInfo = new JList();
        this.waiterInfo.setFont(this.font);
        JScrollPane var6 = new JScrollPane(this.waiterInfo);
        var6.setBounds(15, 25, 115, 175);
        var3.add(var6);
        var2.add(var3);
        var3 = new JPanel();
        var3.setLayout((LayoutManager)null);
        var3.setBounds(5, 230, 460, 200);
        var3.setBorder(new TitledBorder(new EtchedBorder(1), "채팅창"));
        this.view = new JViewport();
        this.messages = new JTextArea();
        this.messages.setEditable(false);
        this.messages.setFont(this.font);
        this.view.add(this.messages);
        this.view.addChangeListener(this);
        this.jsp3 = new JScrollPane(this.view);
        this.jsp3.setBounds(15, 25, 430, 135);
        this.view.addChangeListener(this);
        var3.add(this.jsp3);
        this.view = (JViewport)this.jsp3.getViewport().getView();
        this.view.addChangeListener(this);
        this.message = new JTextField();
        this.message.setFont(this.font);
        this.message.setBounds(15, 170, 430, 20);
        this.message.addKeyListener(this);
        this.message.setBorder(new SoftBevelBorder(1));
        var3.add(this.message);
        var2.add(var3);
        this.create = new JButton("대화방개설");
        this.create.setFont(this.font);
        this.create.setBounds(500, 250, 100, 30);
        this.create.setBorder(new SoftBevelBorder(0));
        this.create.addActionListener(this);
        var2.add(this.create);
        this.join = new JButton("대화방참여");
        this.join.setFont(this.font);
        this.join.setBounds(500, 290, 100, 30);
        this.join.setBorder(new SoftBevelBorder(0));
        this.join.addActionListener(this);
        var2.add(this.join);
        this.sendword = new JButton("귓말보내기");
        this.sendword.setFont(this.font);
        this.sendword.setBounds(500, 330, 100, 30);
        this.sendword.setBorder(new SoftBevelBorder(0));
        this.sendword.addActionListener(this);
        var2.add(this.sendword);
        this.logout = new JButton("로 그 아 웃");
        this.logout.setFont(this.font);
        this.logout.setBounds(500, 370, 100, 30);
        this.logout.setBorder(new SoftBevelBorder(0));
        this.logout.addActionListener(this);
        var2.add(this.logout);
        Dimension var7 = this.getToolkit().getScreenSize();
        this.setSize(640, 460);
        this.setLocation(var7.width / 2 - this.getWidth() / 2, var7.height / 2 - this.getHeight() / 2);
        this.show();
        this.addWindowListener(new WindowAdapter() {
            public void windowActivated(WindowEvent var1) {
                WaitRoomDisplay.this.message.requestFocusInWindow();
            }
        });
        this.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent var1) {
                WaitRoomDisplay.this.cc_thread.requestLogout();
            }
        });
    }

    public void resetComponents() {
        this.messages.setText("");
        this.message.setText("");
        this.roomNumber = 0;
        this.password = "0";
        this.isRock = false;
        this.isSelected = false;
        this.message.requestFocusInWindow();
    }

    public void keyPressed(KeyEvent var1) {
        if (var1.getKeyChar() == '\n') {
            String var2 = this.message.getText();
            if (var2.startsWith("/w")) {
                StringTokenizer var5 = new StringTokenizer(var2, " ");
                String var6 = var5.nextToken();
                String var4 = var5.nextToken();
                String var3 = var5.nextToken();
                this.cc_thread.requestSendWordTo(var3, var4);
                this.message.setText("");
            } else {
                this.cc_thread.requestSendWord(var2);
                this.message.requestFocusInWindow();
            }
        }

    }

    public void mouseClicked(MouseEvent var1) {
        try {
            this.isSelected = true;
            String var2 = String.valueOf(((JList)var1.getSource()).getSelectedValue());
            this.setSelectedRoomInfo(var2);
        } catch (Exception var3) {
        }

    }

    public void actionPerformed(ActionEvent var1) {
        if (var1.getSource() == this.create) {
            new CreateRoomDisplay(this, this.cc_thread);
        } else if (var1.getSource() == this.join) {
            if (!this.isSelected) {
                JOptionPane.showMessageDialog(this, "입장할 방을 선택하세요.", "대화방 입장.", 0);
            } else if (this.isRock && this.password.equals("0")) {
                if ((this.password = JOptionPane.showInputDialog("비밀번호를 입력하세요.")) != null) {
                    if (!this.password.equals("")) {
                        this.cc_thread.requestEnterRoom(this.roomNumber, this.password);
                        this.password = "0";
                    } else {
                        this.password = "0";
                        this.cc_thread.requestEnterRoom(this.roomNumber, this.password);
                    }
                } else {
                    this.password = "0";
                }
            } else {
                this.cc_thread.requestEnterRoom(this.roomNumber, this.password);
            }
        } else if (var1.getSource() == this.logout) {
            this.cc_thread.requestLogout();
        } else {
            String var2;
            String var3;
            if (var1.getSource() == this.sendword && (var2 = JOptionPane.showInputDialog("아이디를 입력하세요.")) != null && (var3 = JOptionPane.showInputDialog("메세지를 입력하세요.")) != null) {
                this.cc_thread.requestSendWordTo(var3, var2);
            }
        }

    }

    private void setSelectedRoomInfo(String var1) {
        StringTokenizer var2 = new StringTokenizer(var1, "=");
        this.roomNumber = Integer.parseInt(var2.nextToken());
        String var3 = var2.nextToken();
        int var4 = Integer.parseInt(var2.nextToken());
        int var5 = Integer.parseInt(var2.nextToken());
        this.isRock = var2.nextToken().equals("비공개");
    }

    public void stateChanged(ChangeEvent var1) {
        this.jsp3.getVerticalScrollBar().setValue(this.jsp3.getVerticalScrollBar().getValue() + 20);
    }

    public void keyReleased(KeyEvent var1) {
    }

    public void keyTyped(KeyEvent var1) {
    }

    public void mousePressed(MouseEvent var1) {
    }

    public void mouseReleased(MouseEvent var1) {
    }

    public void mouseEntered(MouseEvent var1) {
    }

    public void mouseExited(MouseEvent var1) {
    }
}
