//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.NoSuchElementException;
import java.util.StringTokenizer;

public class ServerThread extends Thread {
    private Socket st_sock;
    private DataInputStream st_in;
    private DataOutputStream st_out;
    private StringBuffer st_buffer;
    private WaitRoom st_waitRoom;
    public String st_ID;
    public int st_roomNumber;
    private static final String SEPARATOR = "|";
    private static final String DELIMETER = "'";
    private static final int WAITROOM = 0;
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

    public ServerThread(Socket var1) {
        try {
            this.st_sock = var1;
            this.st_in = new DataInputStream(var1.getInputStream());
            this.st_out = new DataOutputStream(var1.getOutputStream());
            this.st_buffer = new StringBuffer(2048);
            this.st_waitRoom = new WaitRoom();
        } catch (IOException var3) {
            System.out.println(var3);
        }

    }

    private void sendErrCode(int var1, int var2) throws IOException {
        this.st_buffer.setLength(0);
        this.st_buffer.append(var1);
        this.st_buffer.append("|");
        this.st_buffer.append(var2);
        this.send(this.st_buffer.toString());
    }

    private void modifyWaitRoom() throws IOException {
        this.st_buffer.setLength(0);
        this.st_buffer.append(2013);
        this.st_buffer.append("|");
        this.st_buffer.append(this.st_waitRoom.getWaitRoomInfo());
        this.broadcast(this.st_buffer.toString(), 0);
    }

    private void modifyWaitUser() throws IOException {
        String var1 = this.st_waitRoom.getUsers();
        this.st_buffer.setLength(0);
        this.st_buffer.append(2003);
        this.st_buffer.append("|");
        this.st_buffer.append(var1);
        this.broadcast(this.st_buffer.toString(), 0);
    }

    private void modifyRoomUser(int var1, String var2, int var3) throws IOException {
        String var4 = this.st_waitRoom.getRoomInfo(var1);
        this.st_buffer.setLength(0);
        this.st_buffer.append(2023);
        this.st_buffer.append("|");
        this.st_buffer.append(var2);
        this.st_buffer.append("|");
        this.st_buffer.append(var3);
        this.st_buffer.append("|");
        this.st_buffer.append(var4);
        this.broadcast(this.st_buffer.toString(), var1);
    }

    private void send(String var1) throws IOException {
        synchronized(this.st_out) {
            System.out.println(var1);
            this.st_out.writeUTF(var1);
            this.st_out.flush();
        }
    }

    private synchronized void broadcast(String var1, int var2) throws IOException {
        Hashtable var4 = this.st_waitRoom.getClients(var2);
        Enumeration var5 = var4.keys();

        while(var5.hasMoreElements()) {
            ServerThread var3 = (ServerThread)var4.get(var5.nextElement());
            var3.send(var1);
        }

    }

    public void run() {
        boolean var2;
        try {
            while(true) {
                String var1 = this.st_in.readUTF();
                System.out.println(var1);
                StringTokenizer var20 = new StringTokenizer(var1, "|");
                int var3 = Integer.parseInt(var20.nextToken());
                String var4;
                int var5;
                String var6;
                Hashtable var7;
                ServerThread var8;
                int var21;
                String var22;
                boolean var23;
                int var27;
                switch (var3) {
                    case 1001:
                        this.st_roomNumber = 0;
                        this.st_ID = var20.nextToken();
                        var21 = this.st_waitRoom.addUser(this.st_ID, this);
                        this.st_buffer.setLength(0);
                        if (var21 == 0) {
                            this.st_buffer.append(2001);
                            this.st_buffer.append("|");
                            this.st_buffer.append(this.st_waitRoom.getRooms());
                            this.send(this.st_buffer.toString());
                            this.modifyWaitUser();
                            System.out.println(this.st_ID + "의 연결요청 승인");
                        } else {
                            this.sendErrCode(2002, var21);
                        }
                        break;
                    case 1011:
                        var4 = var20.nextToken();
                        String var10 = var20.nextToken();
                        StringTokenizer var11 = new StringTokenizer(var10, "'");
                        var22 = var11.nextToken();
                        var27 = Integer.parseInt(var11.nextToken());
                        boolean var29 = Integer.parseInt(var11.nextToken()) != 0;
                        var6 = var11.nextToken();
                        ChatRoom var12 = new ChatRoom(var22, var27, var29, var6, var4);
                        int var30 = this.st_waitRoom.addRoom(var12);
                        if (var30 == 0) {
                            this.st_roomNumber = ChatRoom.getRoomNumber();
                            var12.addUser(this.st_ID, this);
                            this.st_waitRoom.delUser(this.st_ID);
                            this.st_buffer.setLength(0);
                            this.st_buffer.append(2011);
                            this.st_buffer.append("|");
                            this.st_buffer.append(this.st_roomNumber);
                            this.send(this.st_buffer.toString());
                            this.modifyWaitRoom();
                            this.modifyRoomUser(this.st_roomNumber, var4, 1);
                        } else {
                            this.sendErrCode(2012, var30);
                        }
                        break;
                    case 1021:
                        var4 = var20.nextToken();
                        int var25 = Integer.parseInt(var20.nextToken());

                        try {
                            var22 = var20.nextToken();
                        } catch (NoSuchElementException var16) {
                            var22 = "0";
                        }

                        var27 = this.st_waitRoom.joinRoom(var4, this, var25, var22);
                        if (var27 == 0) {
                            this.st_buffer.setLength(0);
                            this.st_buffer.append(2021);
                            this.st_buffer.append("|");
                            this.st_buffer.append(var25);
                            this.st_buffer.append("|");
                            this.st_buffer.append(var4);
                            this.st_roomNumber = var25;
                            this.send(this.st_buffer.toString());
                            this.modifyRoomUser(var25, var4, 1);
                            this.modifyWaitRoom();
                        } else {
                            this.sendErrCode(2022, var27);
                        }
                        break;
                    case 1031:
                        var4 = var20.nextToken();
                        var5 = Integer.parseInt(var20.nextToken());
                        var23 = this.st_waitRoom.quitRoom(var4, var5, this);
                        this.st_buffer.setLength(0);
                        this.st_buffer.append(2031);
                        this.st_buffer.append("|");
                        this.st_buffer.append(var4);
                        this.send(this.st_buffer.toString());
                        this.st_roomNumber = 0;
                        if (var23) {
                            this.modifyWaitRoom();
                        } else {
                            this.modifyWaitRoom();
                            this.modifyRoomUser(var5, var4, 0);
                        }
                        break;
                    case 1041:
                        var4 = var20.nextToken();
                        this.st_waitRoom.delUser(var4);
                        this.st_buffer.setLength(0);
                        this.st_buffer.append(2041);
                        this.send(this.st_buffer.toString());
                        this.modifyWaitUser();
                        this.release();
                        break;
                    case 1051:
                        var4 = var20.nextToken();
                        var5 = Integer.parseInt(var20.nextToken());
                        this.st_buffer.setLength(0);
                        this.st_buffer.append(2051);
                        this.st_buffer.append("|");
                        this.st_buffer.append(var4);
                        this.st_buffer.append("|");
                        this.st_buffer.append(this.st_roomNumber);
                        this.st_buffer.append("|");

                        try {
                            var6 = var20.nextToken();
                            this.st_buffer.append(var6);
                        } catch (NoSuchElementException var15) {
                        }

                        this.broadcast(this.st_buffer.toString(), var5);
                        break;
                    case 1052:
                        var4 = var20.nextToken();
                        var5 = Integer.parseInt(var20.nextToken());
                        var6 = var20.nextToken();
                        var7 = this.st_waitRoom.getClients(var5);
                        var8 = null;
                        if ((var8 = (ServerThread)var7.get(var6)) != null) {
                            this.st_buffer.setLength(0);
                            this.st_buffer.append(2052);
                            this.st_buffer.append("|");
                            this.st_buffer.append(var4);
                            this.st_buffer.append("|");
                            this.st_buffer.append(var6);
                            this.st_buffer.append("|");
                            this.st_buffer.append(this.st_roomNumber);
                            this.st_buffer.append("|");

                            try {
                                String var28 = var20.nextToken();
                                this.st_buffer.append(var28);
                            } catch (NoSuchElementException var14) {
                            }

                            var8.send(this.st_buffer.toString());
                            this.send(this.st_buffer.toString());
                        } else {
                            this.st_buffer.setLength(0);
                            this.st_buffer.append(2053);
                            this.st_buffer.append("|");
                            this.st_buffer.append(var6);
                            this.st_buffer.append("|");
                            this.st_buffer.append(this.st_roomNumber);
                            this.send(this.st_buffer.toString());
                        }
                        break;
                    case 1053:
                        var21 = Integer.parseInt(var20.nextToken());
                        var22 = var20.nextToken();
                        var7 = this.st_waitRoom.getClients(var21);
                        var8 = null;
                        var8 = (ServerThread)var7.get(var22);
                        var23 = this.st_waitRoom.quitRoom(var22, var21, var8);
                        this.st_buffer.setLength(0);
                        this.st_buffer.append(2054);
                        var8.send(this.st_buffer.toString());
                        var8.st_roomNumber = 0;
                        if (var23) {
                            this.modifyWaitRoom();
                        } else {
                            this.modifyWaitRoom();
                            this.modifyRoomUser(var21, var22, 2);
                        }
                        break;
                    case 1061:
                        var4 = var20.nextToken();
                        var5 = Integer.parseInt(var20.nextToken());
                        var6 = var20.nextToken();
                        var7 = this.st_waitRoom.getClients(var5);
                        var8 = null;
                        if ((var8 = (ServerThread)var7.get(var6)) != null) {
                            this.st_buffer.setLength(0);
                            this.st_buffer.append(1061);
                            this.st_buffer.append("|");
                            this.st_buffer.append(var4);
                            this.st_buffer.append("|");
                            this.st_buffer.append(this.st_roomNumber);
                            var8.send(this.st_buffer.toString());
                        } else {
                            this.st_buffer.setLength(0);
                            this.st_buffer.append(2062);
                            this.st_buffer.append("|");
                            this.st_buffer.append(3032);
                            this.st_buffer.append("|");
                            this.st_buffer.append(var6);
                            this.send(this.st_buffer.toString());
                        }
                        break;
                    case 2061:
                        var4 = var20.nextToken();
                        var5 = Integer.parseInt(var20.nextToken());
                        var6 = var20.nextToken();
                        String var24 = var20.nextToken();
                        Hashtable var26 = this.st_waitRoom.getClients(var5);
                        ServerThread var9 = null;
                        var9 = (ServerThread)var26.get(var6);
                        this.st_buffer.setLength(0);
                        this.st_buffer.append(2061);
                        this.st_buffer.append("|");
                        this.st_buffer.append(var4);
                        this.st_buffer.append("|");
                        this.st_buffer.append(var24);
                        var9.send(this.st_buffer.toString());
                        break;
                    case 2062:
                        var4 = var20.nextToken();
                        var5 = Integer.parseInt(var20.nextToken());
                        var6 = var20.nextToken();
                        var7 = this.st_waitRoom.getClients(var5);
                        var8 = null;
                        var8 = (ServerThread)var7.get(var6);
                        this.st_buffer.setLength(0);
                        this.st_buffer.append(2062);
                        this.st_buffer.append("|");
                        this.st_buffer.append(3031);
                        this.st_buffer.append("|");
                        this.st_buffer.append(var4);
                        var8.send(this.st_buffer.toString());
                }

                Thread.sleep(100L);
            }
        } catch (NullPointerException var17) {
        } catch (InterruptedException var18) {
            System.out.println(var18);
            if (this.st_roomNumber == 0) {
                this.st_waitRoom.delUser(this.st_ID);
            } else {
                var2 = this.st_waitRoom.quitRoom(this.st_ID, this.st_roomNumber, this);
                this.st_waitRoom.delUser(this.st_ID);
            }

            this.release();
        } catch (IOException var19) {
            System.out.println(var19);
            if (this.st_roomNumber == 0) {
                this.st_waitRoom.delUser(this.st_ID);
            } else {
                var2 = this.st_waitRoom.quitRoom(this.st_ID, this.st_roomNumber, this);
                this.st_waitRoom.delUser(this.st_ID);
            }

            this.release();
        }

    }

    public void release() {
        try {
            if (this.st_in != null) {
                this.st_in.close();
            }
        } catch (IOException var27) {
        } finally {
            this.st_in = null;
        }

        try {
            if (this.st_out != null) {
                this.st_out.close();
            }
        } catch (IOException var25) {
        } finally {
            this.st_out = null;
        }

        try {
            if (this.st_sock != null) {
                this.st_sock.close();
            }
        } catch (IOException var23) {
        } finally {
            this.st_sock = null;
        }

        if (this.st_ID != null) {
            System.out.println(this.st_ID + "와 연결을 종료합니다.");
            this.st_ID = null;
        }

    }
}
