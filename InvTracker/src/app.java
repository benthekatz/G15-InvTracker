
import java.util.ArrayList;

//main class used for initializing and holding of active database
public class app {

    //database
    public static Database activeDB;

    public static InitialFrame mjf;

    public static void main(String[] args) {
        mjf = new InitialFrame();
    }

    public static void setDatabase(Database toSetDB) {
        activeDB = toSetDB;
    }

    public static String getCurrentID() {
        return activeDB.getUserName();
    }

    public static void setInvisible() {
        mjf.setVisible(false);
    }

    public static ArrayList<InvObject> getDBObjects() {
        return activeDB.getObjects();
    }

    public static void appendObject(InvObject toAdd) {
        activeDB.addNewObject(toAdd);
    }
}
