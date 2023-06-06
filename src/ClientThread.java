//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.util.NoSuchElementException;
import java.util.StringTokenizer;
import java.util.Vector;
import javax.swing.JOptionPane;

public class ClientThread extends Thread {
    private WaitRoomDisplay ct_waitRoom = new WaitRoomDisplay(this);
    private ChatRoomDisplay ct_chatRoom = null;
    private Socket ct_sock;
    private DataInputStream ct_in;
    private DataOutputStream ct_out;
    private StringBuffer ct_buffer;
    private Thread thisThread;
    private String ct_logonID;
    private int ct_roomNumber;
    private static MessageBox msgBox;
    private static MessageBox logonbox;
    private static MessageBox fileTransBox;
    private static final String SEPARATOR = "|";
    private static final String DELIMETER = "'";
    private static final String DELIMETER2 = "=";
    private static final int REQ_LOGON = 1001;
    private static final int REQ_CREATEROOM = 1011;
    private static final int REQ_ENTERROOM = 1021;
    private static final int REQ_QUITROOM = 1031;
    private static final int REQ_LOGOUT = 1041;
    private static final int REQ_SENDWORD = 1051;
    private static final int REQ_SENDWORDTO = 1052;
    private static final int REQ_COERCEOUT = 1053;
    private static final int REQ_SENDFILE = 1061;
    private static final int YES_LOGON = 2001;
    private static final int NO_LOGON = 2002;
    private static final int YES_CREATEROOM = 2011;
    private static final int NO_CREATEROOM = 2012;
    private static final int YES_ENTERROOM = 2021;
    private static final int NO_ENTERROOM = 2022;
    private static final int YES_QUITROOM = 2031;
    private static final int YES_LOGOUT = 2041;
    private static final int YES_SENDWORD = 2051;
    private static final int YES_SENDWORDTO = 2052;
    private static final int NO_SENDWORDTO = 2053;
    private static final int YES_COERCEOUT = 2054;
    private static final int YES_SENDFILE = 2061;
    private static final int NO_SENDFILE = 2062;
    private static final int MDY_WAITUSER = 2003;
    private static final int MDY_WAITINFO = 2013;
    private static final int MDY_ROOMUSER = 2023;
    private static final int ERR_ALREADYUSER = 3001;
    private static final int ERR_SERVERFULL = 3002;
    private static final int ERR_ROOMSFULL = 3011;
    private static final int ERR_ROOMERFULL = 3021;
    private static final int ERR_PASSWORD = 3022;
    private static final int ERR_REJECTION = 3031;
    private static final int ERR_NOUSER = 3032;

    public ClientThread() {
        try {
            this.ct_sock = new Socket(InetAddress.getLocalHost(), 2777);
            this.ct_in = new DataInputStream(this.ct_sock.getInputStream());
            this.ct_out = new DataOutputStream(this.ct_sock.getOutputStream());
            this.ct_buffer = new StringBuffer(4096);
            this.thisThread = this;
        } catch (IOException var3) {
            MessageBoxLess var2 = new MessageBoxLess(this.ct_waitRoom, "연결에러", "서버에 접속할 수 없습니다.");
            var2.show();
        }

    }

    public ClientThread(String var1) {
        try {
            this.ct_sock = new Socket(var1, 2777);
            this.ct_in = new DataInputStream(this.ct_sock.getInputStream());
            this.ct_out = new DataOutputStream(this.ct_sock.getOutputStream());
            this.ct_buffer = new StringBuffer(4096);
            this.thisThread = this;
        } catch (IOException var4) {
            MessageBoxLess var3 = new MessageBoxLess(this.ct_waitRoom, "연결에러", "서버에 접속할 수 없습니다.");
            var3.show();
        }

    }

    public void run() {
        try {
            for(Thread var1 = Thread.currentThread(); var1 == this.thisThread; Thread.sleep(200L)) {
                String var2 = this.ct_in.readUTF();
                StringTokenizer var3 = new StringTokenizer(var2, "|");
                int var4 = Integer.parseInt(var3.nextToken());
                int var5;
                String var6;
                String var7;
                String var21;
                int var22;
                StringTokenizer var23;
                Vector var25;
                Vector var26;
                switch (var4) {
                    case 1061:
                        var21 = var3.nextToken();
                        var22 = Integer.parseInt(var3.nextToken());
                        var7 = var21 + "로 부터 파일전송을 수락하시겠습니까?";
                        int var28 = JOptionPane.showConfirmDialog(this.ct_chatRoom, var7, "파일수신", 0);
                        if (var28 == 1) {
                            try {
                                this.ct_buffer.setLength(0);
                                this.ct_buffer.append(2062);
                                this.ct_buffer.append("|");
                                this.ct_buffer.append(this.ct_logonID);
                                this.ct_buffer.append("|");
                                this.ct_buffer.append(var22);
                                this.ct_buffer.append("|");
                                this.ct_buffer.append(var21);
                                this.send(this.ct_buffer.toString());
                            } catch (IOException var15) {
                                System.out.println(var15);
                            }
                        } else {
                            StringTokenizer var30 = new StringTokenizer(InetAddress.getLocalHost().toString(), "/");
                            String var10 = "";
                            String var11 = "";
                            var10 = var30.nextToken();

                            try {
                                var11 = var30.nextToken();
                            } catch (NoSuchElementException var14) {
                                var11 = var10;
                            }

                            try {
                                this.ct_buffer.setLength(0);
                                this.ct_buffer.append(2061);
                                this.ct_buffer.append("|");
                                this.ct_buffer.append(this.ct_logonID);
                                this.ct_buffer.append("|");
                                this.ct_buffer.append(var22);
                                this.ct_buffer.append("|");
                                this.ct_buffer.append(var21);
                                this.ct_buffer.append("|");
                                this.ct_buffer.append(var11);
                                this.send(this.ct_buffer.toString());
                            } catch (IOException var13) {
                                System.out.println(var13);
                            }

                            new ReciveFile();
                        }
                        break;
                    case 2001:
                        logonbox.dispose();
                        this.ct_roomNumber = 0;

                        try {
                            var23 = new StringTokenizer(var3.nextToken(), "'");
                            var25 = new Vector();

                            while(var23.hasMoreTokens()) {
                                var7 = var23.nextToken();
                                if (!var7.equals("empty")) {
                                    var25.addElement(var7);
                                }
                            }

                            this.ct_waitRoom.roomInfo.setListData(var25);
                            this.ct_waitRoom.message.requestFocusInWindow();
                        } catch (NoSuchElementException var18) {
                            this.ct_waitRoom.message.requestFocusInWindow();
                        }
                        break;
                    case 2002:
                        var22 = Integer.parseInt(var3.nextToken());
                        if (var22 == 3001) {
                            logonbox.dispose();
                            JOptionPane.showMessageDialog(this.ct_waitRoom, "이미 다른 사용자가 있습니다.", "로그온", 0);
                            var21 = ChatClient.getLogonID();
                            this.requestLogon(var21);
                        } else if (var22 == 3002) {
                            logonbox.dispose();
                            JOptionPane.showMessageDialog(this.ct_waitRoom, "대화방이 만원입니다.", "로그온", 0);
                            var21 = ChatClient.getLogonID();
                            this.requestLogon(var21);
                        }
                        break;
                    case 2003:
                        var23 = new StringTokenizer(var3.nextToken(), "'");
                        var25 = new Vector();

                        while(var23.hasMoreTokens()) {
                            var25.addElement(var23.nextToken());
                        }

                        this.ct_waitRoom.waiterInfo.setListData(var25);
                        this.ct_waitRoom.message.requestFocusInWindow();
                        break;
                    case 2011:
                        this.ct_roomNumber = Integer.parseInt(var3.nextToken());
                        this.ct_waitRoom.hide();
                        if (this.ct_chatRoom == null) {
                            this.ct_chatRoom = new ChatRoomDisplay(this);
                            this.ct_chatRoom.isAdmin = true;
//                            this.ct_chatRoom.setAdminUi();
                        } else {
                            this.ct_chatRoom.show();
                            this.ct_chatRoom.isAdmin = true;
                            this.ct_chatRoom.resetComponents();
//                            this.ct_chatRoom.setAdminUi();
                        }
                        break;
                    case 2012:
                        var5 = Integer.parseInt(var3.nextToken());
                        if (var5 == 3011) {
                            msgBox = new MessageBox(this.ct_waitRoom, "대화방개설", "더 이상 대화방을 개설 할 수 없습니다.");
                            msgBox.show();
                        }
                        break;
                    case 2013:
                        var23 = new StringTokenizer(var3.nextToken(), "'");
                        StringTokenizer var24 = new StringTokenizer(var3.nextToken(), "'");
                        Vector var31 = new Vector();
                        var26 = new Vector();

                        while(var23.hasMoreTokens()) {
                            String var9 = var23.nextToken();
                            if (!var9.equals("empty")) {
                                var31.addElement(var9);
                            }
                        }

                        this.ct_waitRoom.roomInfo.setListData(var31);

                        while(var24.hasMoreTokens()) {
                            var26.addElement(var24.nextToken());
                        }

                        this.ct_waitRoom.waiterInfo.setListData(var26);
                        this.ct_waitRoom.message.requestFocusInWindow();
                        break;
                    case 2021:
                        this.ct_roomNumber = Integer.parseInt(var3.nextToken());
                        var21 = var3.nextToken();
                        this.ct_waitRoom.hide();
                        if (this.ct_chatRoom == null) {
                            this.ct_chatRoom = new ChatRoomDisplay(this);
                        } else {
                            this.ct_chatRoom.show();
                            this.ct_chatRoom.resetComponents();
                        }
                        break;
                    case 2022:
                        var5 = Integer.parseInt(var3.nextToken());
                        if (var5 == 3021) {
                            msgBox = new MessageBox(this.ct_waitRoom, "대화방입장", "대화방이 만원입니다.");
                            msgBox.show();
                        } else if (var5 == 3022) {
                            msgBox = new MessageBox(this.ct_waitRoom, "대화방입장", "비밀번호가 틀립니다.");
                            msgBox.show();
                        }
                        break;
                    case 2023:
                        var21 = var3.nextToken();
                        var22 = Integer.parseInt(var3.nextToken());
                        StringTokenizer var29 = new StringTokenizer(var3.nextToken(), "'");
                        var26 = new Vector();

                        while(var29.hasMoreTokens()) {
                            var26.addElement(var29.nextToken());
                        }
                        this.ct_chatRoom.setAdminUi(var26);
                        this.ct_chatRoom.roomerInfo.setListData(var26);
                        if (var22 == 1) {
                            this.ct_chatRoom.messages.append("### " + var21 + "님이 입장하셨습니다. ###\n");
                        } else if (var22 == 2) {
                            this.ct_chatRoom.messages.append("### " + var21 + "님이 강제퇴장 되었습니다. ###\n");
                        } else {
                            this.ct_chatRoom.messages.append("### " + var21 + "님이 퇴장하셨습니다. ###\n");
                        }

                        this.ct_chatRoom.message.requestFocusInWindow();
                        break;
                    case 2031:
                        var21 = var3.nextToken();
                        if (this.ct_chatRoom.isAdmin) {
                            this.ct_chatRoom.isAdmin = false;
                        }

                        this.ct_chatRoom.hide();
                        this.ct_waitRoom.show();
                        this.ct_waitRoom.resetComponents();
                        this.ct_roomNumber = 0;
                        break;
                    case 2041:
                        this.ct_waitRoom.dispose();
                        if (this.ct_chatRoom != null) {
                            this.ct_chatRoom.dispose();
                        }

                        this.release();
                        break;
                    case 2051:
                        var21 = var3.nextToken();
                        var22 = Integer.parseInt(var3.nextToken());

                        try {
                            var7 = var3.nextToken();
                            if (var22 == 0) {
                                this.ct_waitRoom.messages.append(var21 + " : " + var7 + "\n");
                                if (var21.equals(this.ct_logonID)) {
                                    this.ct_waitRoom.message.setText("");
                                    this.ct_waitRoom.message.requestFocusInWindow();
                                }

                                this.ct_waitRoom.message.requestFocusInWindow();
                            } else {
                                this.ct_chatRoom.messages.append(var21 + " : " + var7 + "\n");
                                if (var21.equals(this.ct_logonID)) {
                                    this.ct_chatRoom.message.setText("");
                                }

                                this.ct_chatRoom.message.requestFocusInWindow();
                            }
                        } catch (NoSuchElementException var17) {
                            if (var22 == 0) {
                                this.ct_waitRoom.message.requestFocusInWindow();
                                break;
                            }

                            this.ct_chatRoom.message.requestFocusInWindow();
                        }
                        break;
                    case 2052:
                        var21 = var3.nextToken();
                        var6 = var3.nextToken();
                        int var27 = Integer.parseInt(var3.nextToken());

                        try {
                            String var8 = var3.nextToken();
                            if (var27 == 0) {
                                if (var21.equals(this.ct_logonID)) {
                                    this.ct_waitRoom.message.setText("");
                                    this.ct_waitRoom.messages.append("귓속말<to:" + var6 + "> : " + var8 + "\n");
                                } else {
                                    this.ct_waitRoom.messages.append("귓속말<from:" + var21 + "> : " + var8 + "\n");
                                }

                                this.ct_waitRoom.message.requestFocusInWindow();
                            } else {
                                if (var21.equals(this.ct_logonID)) {
                                    this.ct_chatRoom.message.setText("");
                                    this.ct_chatRoom.messages.append("귓속말<to:" + var6 + "> : " + var8 + "\n");
                                } else {
                                    this.ct_chatRoom.messages.append("귓속말<from:" + var21 + "> : " + var8 + "\n");
                                }

                                this.ct_chatRoom.message.requestFocusInWindow();
                            }
                        } catch (NoSuchElementException var16) {
                            if (var27 == 0) {
                                this.ct_waitRoom.message.requestFocusInWindow();
                                break;
                            }

                            this.ct_chatRoom.message.requestFocusInWindow();
                        }
                        break;
                    case 2053:
                        var21 = var3.nextToken();
                        var22 = Integer.parseInt(var3.nextToken());
                        var7 = "";
                        if (var22 == 0) {
                            var7 = "대기실에 " + var21 + "님이 존재하지 않습니다.";
                            JOptionPane.showMessageDialog(this.ct_waitRoom, var7, "귓속말 에러", 0);
                        } else {
                            var7 = "이 대화방에 " + var21 + "님이 존재하지 않습니다.";
                            JOptionPane.showMessageDialog(this.ct_chatRoom, var7, "귓속말 에러", 0);
                        }
                        break;
                    case 2054:
                        this.ct_chatRoom.hide();
                        this.ct_waitRoom.show();
                        this.ct_waitRoom.resetComponents();
                        this.ct_roomNumber = 0;
                        this.ct_waitRoom.messages.append("### 방장에 의해 강제퇴장 되었습니다. ###\n");
                        break;
                    case 2062:
                        var5 = Integer.parseInt(var3.nextToken());
                        var6 = var3.nextToken();
                        fileTransBox.dispose();
                        if (var5 == 3031) {
                            var7 = var6 + "님이 파일수신을 거부하였습니다.";
                            JOptionPane.showMessageDialog(this.ct_chatRoom, var7, "파일전송", 0);
                            break;
                        } else if (var5 == 3032) {
                            var7 = var6 + "님은 이 방에 존재하지 않습니다.";
                            JOptionPane.showMessageDialog(this.ct_chatRoom, var7, "파일전송", 0);
                            break;
                        }
                    case 2061:
                        var21 = var3.nextToken();
                        var6 = var3.nextToken();
                        fileTransBox.dispose();
                        new SendFile(var6);
                }
            }
        } catch (InterruptedException var19) {
            System.out.println(var19);
            this.release();
        } catch (IOException var20) {
            System.out.println(var20);
            this.release();
        }

    }

    public void requestLogon(String var1) {
        try {
            logonbox = new MessageBox(this.ct_waitRoom, "로그온", "서버에 로그온 중입니다.");
            logonbox.show();
            this.ct_logonID = var1;
            this.ct_buffer.setLength(0);
            this.ct_buffer.append(1001);
            this.ct_buffer.append("|");
            this.ct_buffer.append(var1);
            this.send(this.ct_buffer.toString());
        } catch (IOException var3) {
            System.out.println(var3);
        }

    }

    public void requestCreateRoom(String var1, int var2, int var3, String var4) {
        try {
            this.ct_buffer.setLength(0);
            this.ct_buffer.append(1011);
            this.ct_buffer.append("|");
            this.ct_buffer.append(this.ct_logonID);
            this.ct_buffer.append("|");
            this.ct_buffer.append(var1);
            this.ct_buffer.append("'");
            this.ct_buffer.append(var2);
            this.ct_buffer.append("'");
            this.ct_buffer.append(var3);
            this.ct_buffer.append("'");
            this.ct_buffer.append(var4);
            this.send(this.ct_buffer.toString());
        } catch (IOException var6) {
            System.out.println(var6);
        }

    }

    public void requestEnterRoom(int var1, String var2) {
        try {
            this.ct_buffer.setLength(0);
            this.ct_buffer.append(1021);
            this.ct_buffer.append("|");
            this.ct_buffer.append(this.ct_logonID);
            this.ct_buffer.append("|");
            this.ct_buffer.append(var1);
            this.ct_buffer.append("|");
            this.ct_buffer.append(var2);
            this.send(this.ct_buffer.toString());
        } catch (IOException var4) {
            System.out.println(var4);
        }

    }

    public void requestQuitRoom() {
        try {
            this.ct_buffer.setLength(0);
            this.ct_buffer.append(1031);
            this.ct_buffer.append("|");
            this.ct_buffer.append(this.ct_logonID);
            this.ct_buffer.append("|");
            this.ct_buffer.append(this.ct_roomNumber);
            this.send(this.ct_buffer.toString());
        } catch (IOException var2) {
            System.out.println(var2);
        }

    }

    public void requestLogout() {
        try {
            this.ct_buffer.setLength(0);
            this.ct_buffer.append(1041);
            this.ct_buffer.append("|");
            this.ct_buffer.append(this.ct_logonID);
            this.send(this.ct_buffer.toString());
        } catch (IOException var2) {
            System.out.println(var2);
        }

    }

    public void requestSendWord(String var1) {
        try {
            this.ct_buffer.setLength(0);
            this.ct_buffer.append(1051);
            this.ct_buffer.append("|");
            this.ct_buffer.append(this.ct_logonID);
            this.ct_buffer.append("|");
            this.ct_buffer.append(this.ct_roomNumber);
            this.ct_buffer.append("|");
            this.ct_buffer.append(var1);
            this.send(this.ct_buffer.toString());
        } catch (IOException var3) {
            System.out.println(var3);
        }

    }

    public void requestSendWordTo(String var1, String var2) {
        try {
            this.ct_buffer.setLength(0);
            this.ct_buffer.append(1052);
            this.ct_buffer.append("|");
            this.ct_buffer.append(this.ct_logonID);
            this.ct_buffer.append("|");
            this.ct_buffer.append(this.ct_roomNumber);
            this.ct_buffer.append("|");
            this.ct_buffer.append(var2);
            this.ct_buffer.append("|");
            this.ct_buffer.append(var1);
            this.send(this.ct_buffer.toString());
        } catch (IOException var4) {
            System.out.println(var4);
        }

    }

    public void requestCoerceOut(String var1) {
        try {
            this.ct_buffer.setLength(0);
            this.ct_buffer.append(1053);
            this.ct_buffer.append("|");
            this.ct_buffer.append(this.ct_roomNumber);
            this.ct_buffer.append("|");
            this.ct_buffer.append(var1);
            this.send(this.ct_buffer.toString());
        } catch (IOException var3) {
            System.out.println(var3);
        }

    }

    public void requestSendFile(String var1) {
        fileTransBox = new MessageBox(this.ct_chatRoom, "파일전송", "상대방의 승인을 기다립니다.");
        fileTransBox.show();

        try {
            this.ct_buffer.setLength(0);
            this.ct_buffer.append(1061);
            this.ct_buffer.append("|");
            this.ct_buffer.append(this.ct_logonID);
            this.ct_buffer.append("|");
            this.ct_buffer.append(this.ct_roomNumber);
            this.ct_buffer.append("|");
            this.ct_buffer.append(var1);
            this.send(this.ct_buffer.toString());
        } catch (IOException var3) {
            System.out.println(var3);
        }

    }

    private void send(String var1) throws IOException {
        this.ct_out.writeUTF(var1);
        this.ct_out.flush();
    }

    public void release() {
        if (this.thisThread != null) {
            this.thisThread = null;
        }

        try {
            if (this.ct_out != null) {
                this.ct_out.close();
            }
        } catch (IOException var27) {
        } finally {
            this.ct_out = null;
        }

        try {
            if (this.ct_in != null) {
                this.ct_in.close();
            }
        } catch (IOException var25) {
        } finally {
            this.ct_in = null;
        }

        try {
            if (this.ct_sock != null) {
                this.ct_sock.close();
            }
        } catch (IOException var23) {
        } finally {
            this.ct_sock = null;
        }

        System.exit(0);
    }
}
