//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;

class ChatRoom {
    private static final String DELIMETER = "'";
    private static final String DELIMETER1 = "=";
    public static int roomNumber = 0;
    private Vector userVector;
    private Hashtable userHash;
    private String roomName;
    private int roomMaxUser;
    private int roomUser;
    private boolean isRock;
    private String password;
    private String admin;

    public ChatRoom(String var1, int var2, boolean var3, String var4, String var5) {
        ++roomNumber;
        this.roomName = var1;
        this.roomMaxUser = var2;
        this.roomUser = 0;
        this.isRock = var3;
        this.password = var4;
        this.admin = var5;
        this.userVector = new Vector(var2);
        this.userHash = new Hashtable(var2);
    }

    public boolean addUser(String var1, ServerThread var2) {
        if (this.roomUser == this.roomMaxUser) {
            return false;
        } else {
            this.userVector.addElement(var1);
            this.userHash.put(var1, var2);
            ++this.roomUser;
            return true;
        }
    }

    public boolean checkPassword(String var1) {
        return this.password.equals(var1);
    }

    public boolean checkUserIDs(String var1) {
        Enumeration var2 = this.userVector.elements();

        String var3;
        do {
            if (!var2.hasMoreElements()) {
                return false;
            }

            var3 = (String)var2.nextElement();
        } while(!var3.equals(var1));

        return true;
    }

    public boolean isRocked() {
        return this.isRock;
    }

    public boolean delUser(String var1) {
        this.userVector.removeElement(var1);
        this.userHash.remove(var1);
        --this.roomUser;
        return this.userVector.isEmpty();
    }

    public synchronized String getUsers() {
        StringBuffer var1 = new StringBuffer();
        Enumeration var3 = this.userVector.elements();

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

    public Hashtable getClients() {
        return this.userHash;
    }

    public String toString() {
        StringBuffer var1 = new StringBuffer();
        var1.append(this.roomName);
        var1.append("=");
        var1.append(String.valueOf(this.roomUser));
        var1.append("=");
        var1.append(String.valueOf(this.roomMaxUser));
        var1.append("=");
        if (this.isRock) {
            var1.append("비공개");
        } else {
            var1.append("공개");
        }

        var1.append("=");
        var1.append(this.admin);
        return var1.toString();
    }

    public static synchronized int getRoomNumber() {
        return roomNumber;
    }
}
