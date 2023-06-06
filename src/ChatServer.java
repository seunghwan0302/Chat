//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ChatServer {
    public static final int cs_port = 2777;
    public static final int cs_maxclient = 50;

    public ChatServer() {
    }

    public static void main(String[] var0) {
        try {
            ServerSocket var1 = new ServerSocket(2777);
            System.out.println("서버소켓 실행 : 클라이언트의 접속을 기다립니다.");

            while(true) {
                Socket var2 = null;
                ServerThread var3 = null;

                try {
                    var2 = var1.accept();
                    var3 = new ServerThread(var2);
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
            }
        } catch (IOException var14) {
        }
    }
}
