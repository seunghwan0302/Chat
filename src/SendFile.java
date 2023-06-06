//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

import java.awt.Button;
import java.awt.Color;
import java.awt.FileDialog;
import java.awt.Frame;
import java.awt.Label;
import java.awt.LayoutManager;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.Socket;

public class SendFile extends Frame implements ActionListener {
    private TextField tf_filename;
    private Button bt_dialog;
    private Button bt_send;
    private Button bt_close;
    private Label lb_status;
    private static final String SEPARATOR = "|";
    private String address;

    public SendFile(String var1) {
        super("파일전송");
        this.address = var1;
        this.setLayout((LayoutManager)null);
        Label var2 = new Label("파일이름");
        var2.setBounds(10, 30, 60, 20);
        this.add(var2);
        this.tf_filename = new TextField();
        this.tf_filename.setBounds(80, 30, 160, 20);
        this.add(this.tf_filename);
        this.bt_dialog = new Button("찾아보기");
        this.bt_dialog.setBounds(45, 60, 60, 20);
        this.bt_dialog.addActionListener(this);
        this.add(this.bt_dialog);
        this.bt_send = new Button("전송");
        this.bt_send.setBounds(115, 60, 40, 20);
        this.bt_send.addActionListener(this);
        this.add(this.bt_send);
        this.bt_close = new Button("종료");
        this.bt_close.setBounds(165, 60, 40, 20);
        this.bt_close.addActionListener(this);
        this.add(this.bt_close);
        this.lb_status = new Label("파일전송 대기중....");
        this.lb_status.setBounds(10, 90, 230, 20);
        this.lb_status.setBackground(Color.gray);
        this.lb_status.setForeground(Color.white);
        this.add(this.lb_status);
        this.addWindowListener(new WinListener());
        this.setSize(250, 130);
        this.show();
    }

    public void actionPerformed(ActionEvent var1) {
        if (var1.getSource() == this.bt_dialog) {
            FileDialog var2 = new FileDialog(this, "파일 칮기", 0);
            var2.show();
            this.tf_filename.setText(var2.getDirectory() + var2.getFile());
            if (this.tf_filename.getText().startsWith("null")) {
                this.tf_filename.setText("");
            }
        } else if (var1.getSource() == this.bt_send) {
            String var15 = this.tf_filename.getText();
            if (var15.equals("")) {
                this.lb_status.setText("파일이름을 입력하세요.");
                return;
            }

            this.lb_status.setText("파일검색중..");
            File var3 = new File(var15);
            if (!var3.exists()) {
                this.lb_status.setText("해당파일을 찾을 수 없습니다.");
                return;
            }

            StringBuffer var4 = new StringBuffer();
            int var5 = (int)var3.length();
            var4.append(var3.getName());
            var4.append("|");
            var4.append(var5);
            this.lb_status.setText("연결설정중......");

            try {
                Socket var6 = new Socket(this.address, 3777);
                FileInputStream var7 = new FileInputStream(var3);
                BufferedInputStream var8 = new BufferedInputStream(var7, var5);
                byte[] var9 = new byte[var5];

                try {
                    this.lb_status.setText("전송할 파일 로드중......");
                    var8.read(var9, 0, var5);
                    var8.close();
                } catch (IOException var13) {
                    this.lb_status.setText("파일읽기 오류.");
                    return;
                }

                DataOutputStream var10 = new DataOutputStream(var6.getOutputStream());
                var10.writeUTF(var4.toString());
                this.tf_filename.setText("");
                this.lb_status.setText("파일전송중......( 0 Byte)");
                BufferedOutputStream var11 = new BufferedOutputStream(var10, 2048);
                DataInputStream var12 = new DataInputStream(var6.getInputStream());
                this.sendFile(var11, var12, var9, var5);
                var11.close();
                var12.close();
                this.lb_status.setText(var3.getName() + " 파일전송이 완료되었습니다.");
                var6.close();
            } catch (IOException var14) {
                System.out.println(var14);
                this.lb_status.setText(this.address + "로의 연결에 실패하였습니다.");
            }
        } else if (var1.getSource() == this.bt_close) {
            this.dispose();
        }

    }

    private void sendFile(BufferedOutputStream var1, DataInputStream var2, byte[] var3, int var4) throws IOException {
        short var5 = 2048;
        int var6 = var4 / var5;
        int var7 = var4 % var5;
        boolean var8 = true;
        if (var6 == 0) {
            var8 = false;
        }

        for(int var9 = 0; var9 <= var6; ++var9) {
            if (var9 == var6 && !var8) {
                var1.write(var3, 0, var7);
                var1.flush();
                return;
            }

            if (var9 == var6) {
                var1.write(var3, var9 * var5, var7);
                var1.flush();
                return;
            }

            var1.write(var3, var9 * var5, var5);
            var1.flush();
            this.lb_status.setText("파일전송중......(" + (var9 + 1) * var5 + "/" + var4 + " Byte)");
            var2.readUTF();
        }

    }

    class WinListener extends WindowAdapter {
        WinListener() {
        }

        public void windowClosing(WindowEvent var1) {
            System.exit(0);
        }
    }
}
