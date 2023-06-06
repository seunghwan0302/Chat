//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.StringTokenizer;

public class FileThread extends Thread {
    private ReciveFile recv;
    private Socket sock;
    private DataInputStream in;
    private String header;
    private byte[] data;
    private static final String SEPARATOR = "|";

    public FileThread(ReciveFile var1, Socket var2) {
        try {
            this.recv = var1;
            this.sock = var2;
            this.in = new DataInputStream(var2.getInputStream());
        } catch (IOException var4) {
            System.out.println(var4);
        }

    }

    public void run() {
        try {
            try {
                this.header = this.in.readUTF();
                StringTokenizer var1 = new StringTokenizer(this.header, "|");
                String var2 = var1.nextToken();
                int var3 = Integer.parseInt(var1.nextToken());
                this.data = new byte[var3];
                this.recv.lbl.setText(var2 + "(" + var3 + "byte) 파일을 수신합니다.");
                BufferedInputStream var4 = new BufferedInputStream(this.in, 2048);
                DataOutputStream var5 = new DataOutputStream(this.sock.getOutputStream());
                this.readFile(var4, var5, this.data, var3);
                var4.close();
                var5.close();
                File var6 = new File("받은파일\\");
                if (!var6.exists()) {
                    var6.mkdir();
                }

                File var7 = new File(var6, var2);
                if (var7.exists()) {
                    var7 = new File(var6, "re_" + var2);
                    this.recv.txt.append(var2 + " 파일이 이미 존재합니다.\n");
                    this.recv.txt.append(var7.getName() + " 으로 파일명을 변경합니다.\n");
                } else if (!var7.createNewFile()) {
                    this.recv.txt.append(var2 + "파일 생성 에러.\n");
                    this.recv.txt.append(var2 + "파일 수신이 취소되었습니다.\n");
                    return;
                }

                FileOutputStream var8 = new FileOutputStream(var7);
                BufferedOutputStream var9 = new BufferedOutputStream(var8, var3);
                var9.write(this.data, 0, var3);
                var9.flush();
                var9.close();
                this.recv.txt.append(var2 + "파일 수신이 성공했습니다.\n");
                this.recv.txt.append(var2 + "저장위치 : " + var6.getAbsolutePath() + "\\" + var7.getName() + "\n");
                this.sock.close();
                this.recv.btn.setVisible(true);
            } catch (Exception var185) {
                System.out.println(var185);
            }

        } finally {
            try {
                if (this.sock != null) {
                    this.sock.close();
                }
            } catch (IOException var183) {
            } finally {
                this.sock = null;
            }

            try {
                if (this.in != null) {
                    this.in.close();
                }
            } catch (IOException var181) {
            } finally {
                this.in = null;
            }

        }
    }

    private void readFile(BufferedInputStream var1, DataOutputStream var2, byte[] var3, int var4) throws IOException {
        short var5 = 2048;
        int var6 = var4 / var5;
        int var7 = var4 % var5;
        boolean var8 = true;
        if (var6 == 0) {
            var8 = false;
        }

        for(int var9 = 0; var9 <= var6; ++var9) {
            if (var9 == var6 && !var8) {
                var1.read(var3, 0, var7);
                this.recv.lbl.setText("파일수신완료......(" + var4 + "/" + var4 + " Byte)");
                return;
            }

            if (var9 == var6) {
                var1.read(var3, var9 * var5, var7);
                this.recv.lbl.setText("파일수신완료......(" + var4 + "/" + var4 + " Byte)");
                return;
            }

            var1.read(var3, var9 * var5, var5);
            this.recv.lbl.setText("파일수신중......(" + (var9 + 1) * var5 + "/" + var4 + " Byte)");
            var2.writeUTF("flag");
        }

    }
}
