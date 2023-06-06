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
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.StringTokenizer;
import java.util.Vector;
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
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

class ChatRoomDisplay extends JFrame implements ActionListener, KeyListener, ListSelectionListener, ChangeListener {
    private ClientThread cr_thread;
    private String idTo;
    private boolean isSelected;
    public boolean isAdmin;
    private JLabel roomer;
    public JList roomerInfo;

    private JButton testChat;
    private JButton coerceOut;
    private JButton sendWord;
    private JButton sendFile;
    private JButton quitRoom;
    private Font font;
    private JViewport view;
    private JScrollPane jsp3;
    public JTextArea messages;
    public JTextField message;

    public ChatRoomDisplay(ClientThread var1) {
        super("Chat-Application-대화방");
        this.cr_thread = var1;
        this.isSelected = false;
        this.isAdmin = false;
        this.font = new Font("SanSerif", 0, 12);
        Container var2 = this.getContentPane();
        var2.setLayout((LayoutManager)null);
        JPanel var3 = new JPanel();
        var3.setLayout((LayoutManager)null);
        var3.setBounds(425, 10, 140, 175);
        var3.setBorder(new TitledBorder(new EtchedBorder(1), "참여자"));
        this.roomerInfo = new JList();
        this.roomerInfo.setFont(this.font);
        JScrollPane var4 = new JScrollPane(this.roomerInfo);
        this.roomerInfo.addListSelectionListener(this);
        var4.setBounds(15, 25, 110, 135);
        var3.add(var4);
        var2.add(var3);
        var3 = new JPanel();
        var3.setLayout((LayoutManager)null);
        var3.setBounds(10, 10, 410, 340);
        var3.setBorder(new TitledBorder(new EtchedBorder(1), "채팅창"));
        this.view = new JViewport();
        this.messages = new JTextArea();
        this.messages.setFont(this.font);
        this.messages.setEditable(false);
        this.view.add(this.messages);
        this.view.addChangeListener(this);
        this.jsp3 = new JScrollPane(this.view);
        this.jsp3.setBounds(15, 25, 380, 270);
        var3.add(this.jsp3);
        this.message = new JTextField();
        this.message.setFont(this.font);
        this.message.addKeyListener(this);
        this.message.setBounds(15, 305, 380, 20);
        this.message.setBorder(new SoftBevelBorder(1));
        var3.add(this.message);
        var2.add(var3);
        this.coerceOut = new JButton("강 제 퇴 장");
        this.coerceOut.setFont(this.font);
        this.coerceOut.addActionListener(this);
        this.coerceOut.setBounds(445, 195, 100, 30);
        this.coerceOut.setBorder(new SoftBevelBorder(0));
        var2.add(this.coerceOut);
        this.sendWord = new JButton("귓말보내기");
        this.sendWord.setFont(this.font);
        this.sendWord.addActionListener(this);
        this.sendWord.setBounds(445, 235, 100, 30);
        this.sendWord.setBorder(new SoftBevelBorder(0));
        var2.add(this.sendWord);
        this.sendFile = new JButton("파 일 전 송");
        this.sendFile.setFont(this.font);
        this.sendFile.addActionListener(this);
        this.sendFile.setBounds(445, 275, 100, 30);
        this.sendFile.setBorder(new SoftBevelBorder(0));
        var2.add(this.sendFile);
        this.quitRoom = new JButton("퇴 실 하 기");
        this.quitRoom.setFont(this.font);
        this.quitRoom.addActionListener(this);
        this.quitRoom.setBounds(445, 315, 100, 30);
        this.quitRoom.setBorder(new SoftBevelBorder(0));
        var2.add(this.quitRoom);
        Dimension var5 = this.getToolkit().getScreenSize();
        this.setSize(580, 400);
        this.setLocation(var5.width / 2 - this.getWidth() / 2, var5.height / 2 - this.getHeight() / 2);
        this.show();
        this.addWindowListener(new WindowAdapter() {
            public void windowActivated(WindowEvent var1) {
                ChatRoomDisplay.this.message.requestFocusInWindow();
            }
        });
        this.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent var1) {
                ChatRoomDisplay.this.cr_thread.requestQuitRoom();
            }
        });
    }

    public void resetComponents() {
        this.messages.setText("");
        this.message.setText("");
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
                this.cr_thread.requestSendWordTo(var3, var4);
                this.message.setText("");
            } else {
                this.cr_thread.requestSendWord(var2);
                this.message.requestFocusInWindow();
            }
        }


    }
    public void setAdminUi(Vector vector){
        if(this.isAdmin){
            for (int i = 1; i <vector.size(); i++){
                vector.set(i, new JButton(vector.get(i)+"채팅금지"));
            }
        }
    }
    public void valueChanged(ListSelectionEvent var1) {
        this.isSelected = true;
        this.idTo = String.valueOf(((JList)var1.getSource()).getSelectedValue());
    }

    public void actionPerformed(ActionEvent var1) {
        if (var1.getSource() == this.coerceOut) {
            if (!this.isAdmin) {
                JOptionPane.showMessageDialog(this, "당신은 방장이 아닙니다.", "강제퇴장", 0);
            } else if (!this.isSelected) {
                JOptionPane.showMessageDialog(this, "강제퇴장 ID를 선택하세요.", "강제퇴장", 0);
            } else {
                this.cr_thread.requestCoerceOut(this.idTo);
                this.isSelected = false;
            }
        } else if (var1.getSource() == this.quitRoom) {
            this.cr_thread.requestQuitRoom();
        } else {
            String var2;
            if (var1.getSource() == this.sendWord) {
                String var3;
                if ((var2 = JOptionPane.showInputDialog("아이디를 입력하세요.")) != null && (var3 = JOptionPane.showInputDialog("메세지를 입력하세요.")) != null) {
                    this.cr_thread.requestSendWordTo(var3, var2);
                }
            } else if (var1.getSource() == this.sendFile && (var2 = JOptionPane.showInputDialog("상대방 아이디를 입력하세요.")) != null) {
                this.cr_thread.requestSendFile(var2);
            }
        }

    }

    public void stateChanged(ChangeEvent var1) {
        this.jsp3.getVerticalScrollBar().setValue(this.jsp3.getVerticalScrollBar().getValue() + 20);
    }

    public void keyReleased(KeyEvent var1) {
    }

    public void keyTyped(KeyEvent var1) {
    }
}
