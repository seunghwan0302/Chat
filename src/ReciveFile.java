//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

import java.awt.Button;
import java.awt.Color;
import java.awt.Frame;
import java.awt.Label;
import java.awt.LayoutManager;
import java.awt.TextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ReciveFile extends Frame implements ActionListener {
    public static final int port = 3777;
    public Label lbl;
    public TextArea txt;
    public Button btn;

    public ReciveFile() {
        super("파일전송");
        this.setLayout((LayoutManager)null);
        this.lbl = new Label("파일 전송을 기다립니다.");
        this.lbl.setBounds(10, 30, 230, 20);
        this.lbl.setBackground(Color.gray);
        this.lbl.setForeground(Color.white);
        this.add(this.lbl);
        this.txt = new TextArea("", 0, 0, 0);
        this.txt.setBounds(10, 60, 230, 100);
        this.txt.setEditable(false);
        this.add(this.txt);
        this.btn = new Button("닫기");
        this.btn.setBounds(105, 170, 40, 20);
        this.btn.setVisible(false);
        this.btn.addActionListener(this);
        this.add(this.btn);
        this.addWindowListener(new WinListener());
        this.setSize(250, 200);
        this.show();

        try {
            ServerSocket var1 = new ServerSocket(3777);
            Socket var2 = null;
            FileThread var3 = null;

            try {
                var2 = var1.accept();
                var3 = new FileThread(this, var2);
                var3.start();
            } catch (IOException var13) {
                System.out.println(var13);

                try {
                    if (var2 != null) {
                        var2.close();
                    }
                } catch (IOException var11) {
                    System.out.println(var11);
                } finally {
                    var2 = null;
                }
            }
        } catch (IOException var14) {
        }

    }

    public void actionPerformed(ActionEvent var1) {
        this.dispose();
    }

    class WinListener extends WindowAdapter {
        WinListener() {
        }

        public void windowClosing(WindowEvent var1) {
            ReciveFile.this.dispose();
        }
    }
}
