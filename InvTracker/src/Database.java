
import java.io.*;
import java.util.ArrayList;
import javax.swing.JOptionPane;

public class Database implements Serializable {
    
    private String userName;
    private String password;
    
    private ArrayList<InvObject> objects;
    private int credID;
    
    
    public Database(String un, String pw) {
        
        this.userName = un;
        this.password = pw;
        
        this.objects = new ArrayList<InvObject>();
        this.credID = 0;
    }
        
    public Database(String un, String pw, ArrayList<InvObject> credentials) {
        
        this.userName = un;
        this.password = pw;

        this.objects = credentials;
        this.credID = 0;
    }

    public boolean verifyLoginCredentials(String us, String pw) {
        return us.equals(userName) && pw.equals(password);
    }
    
    public ArrayList<InvObject> getObjects() {
        return objects;
    }
    
    public void addNewObject(InvObject toAdd) {
        objects.add(toAdd);
    }
    
    public boolean accountExisted(String userName)
    {
        if(new File(userName+".ser").exists())
        {
            return true;
        }
        else 
            return false;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
