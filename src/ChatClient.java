//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

import javax.swing.JOptionPane;

public class ChatClient {
    public ChatClient() {
    }

    public static String getLogonID() {
        String var0 = "";

        try {
            while(var0.equals("")) {
                var0 = JOptionPane.showInputDialog("로그온 ID를 입력하세요.");
            }
        } catch (NullPointerException var2) {
            System.exit(0);
        }

        return var0;
    }

    public static void main(String[] var0) {
        String var1 = getLogonID();

        try {
            ClientThread var2;
            if (var0.length == 0) {
                var2 = new ClientThread();
                var2.start();
                var2.requestLogon(var1);
            } else if (var0.length == 1) {
                var2 = new ClientThread(var0[0]);
                var2.start();
                var2.requestLogon(var1);
            }
        } catch (Exception var3) {
            System.out.println(var3);
        }

    }
}
