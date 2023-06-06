//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;

class WaitRoom {
    private static final int MAX_ROOM = 10;
    private static final int MAX_USER = 100;
    private static final String SEPARATOR = "|";
    private static final String DELIMETER = "'";
    private static final String DELIMETER1 = "=";
    private static final int ERR_ALREADYUSER = 3001;
    private static final int ERR_SERVERFULL = 3002;
    private static final int ERR_ROOMSFULL = 3011;
    private static final int ERR_ROOMERFULL = 3021;
    private static final int ERR_PASSWORD = 3022;
    private static Vector userVector = new Vector(100);
    private static Vector roomVector = new Vector(10);
    private static Hashtable userHash = new Hashtable(100);
    private static Hashtable roomHash = new Hashtable(10);
    private static int userCount = 0;
    private static int roomCount = 0;

    public WaitRoom() {
    }

    public synchronized int addUser(String var1, ServerThread var2) {
        if (userCount == 100) {
            return 3002;
        } else {
            Enumeration var3 = userVector.elements();

            String var4;
            do {
                if (!var3.hasMoreElements()) {
                    Enumeration var6 = roomVector.elements();

                    ChatRoom var5;
                    do {
                        if (!var6.hasMoreElements()) {
                            userVector.addElement(var1);
                            userHash.put(var1, var2);
                            var2.st_ID = var1;
                            var2.st_roomNumber = 0;
                            ++userCount;
                            return 0;
                        }

                        var5 = (ChatRoom)var6.nextElement();
                    } while(!var5.checkUserIDs(var1));

                    return 3001;
                }

                var4 = (String)var3.nextElement();
            } while(!var4.equals(var1));

            return 3001;
        }
    }

    public synchronized void delUser(String var1) {
        userVector.removeElement(var1);
        userHash.remove(var1);
        --userCount;
    }

    public synchronized String getRooms() {
        StringBuffer var1 = new StringBuffer();
        Enumeration var4 = roomHash.keys();

        while(var4.hasMoreElements()) {
            Integer var3 = (Integer)var4.nextElement();
            ChatRoom var5 = (ChatRoom)roomHash.get(var3);
            var1.append(String.valueOf(var3));
            var1.append("=");
            var1.append(var5.toString());
            var1.append("'");
        }

        try {
            String var2 = new String(var1);
            var2 = var2.substring(0, var2.length() - 1);
            return var2;
        } catch (StringIndexOutOfBoundsException var6) {
            return "empty";
        }
    }

    public synchronized String getUsers() {
        StringBuffer var1 = new StringBuffer();
        Enumeration var3 = userVector.elements();

        while(var3.hasMoreElements()) {
            var1.append(var3.nextElement());
            var1.append("'");
        }

        try {
            String var2 = new String(var1);
            var2 = var2.substring(0, var2.length() - 1);
            return var2;
        } catch (StringIndexOutOfBoundsException var5) {
            return "";
        }
    }

    public synchronized int addRoom(ChatRoom var1) {
        if (roomCount == 10) {
            return 3011;
        } else {
            roomVector.addElement(var1);
            roomHash.put(new Integer(ChatRoom.roomNumber), var1);
            ++roomCount;
            return 0;
        }
    }

    public String getWaitRoomInfo() {
        StringBuffer var1 = new StringBuffer();
        var1.append(this.getRooms());
        var1.append("|");
        var1.append(this.getUsers());
        return var1.toString();
    }

    public synchronized int joinRoom(String var1, ServerThread var2, int var3, String var4) {
        Integer var5 = new Integer(var3);
        ChatRoom var6 = (ChatRoom)roomHash.get(var5);
        if (var6.isRocked()) {
            if (!var6.checkPassword(var4)) {
                return 3022;
            }

            if (!var6.addUser(var1, var2)) {
                return 3021;
            }
        } else if (!var6.addUser(var1, var2)) {
            return 3021;
        }

        userVector.removeElement(var1);
        userHash.remove(var1);
        return 0;
    }

    public String getRoomInfo(int var1) {
        Integer var2 = new Integer(var1);
        ChatRoom var3 = (ChatRoom)roomHash.get(var2);
        return var3.getUsers();
    }

    public synchronized boolean quitRoom(String var1, int var2, ServerThread var3) {
        boolean var4 = false;
        Integer var5 = new Integer(var2);
        ChatRoom var6 = (ChatRoom)roomHash.get(var5);
        if (var6.delUser(var1)) {
            roomVector.removeElement(var6);
            roomHash.remove(var5);
            --roomCount;
            var4 = true;
        }

        userVector.addElement(var1);
        userHash.put(var1, var3);
        return var4;
    }

    public synchronized Hashtable getClients(int var1) {
        if (var1 == 0) {
            return userHash;
        } else {
            Integer var2 = new Integer(var1);
            ChatRoom var3 = (ChatRoom)roomHash.get(var2);
            return var3.getClients();
        }
    }
}
