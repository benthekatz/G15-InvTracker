
import java.text.SimpleDateFormat;
import java.util.*;

//inventory objects (things that will populate main menu table)
public class InvObject {

    private String ID;
    private String objectTitle;
    private String quantity;
    private String dateLastUpdated;
    private String note;

//constructor for object
    public InvObject(String ot, String qu, String n) {
        //object metadata
        ID = app.getCurrentID();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        dateLastUpdated = sdf.format(new Date());

        //visible object properties
        objectTitle = ot;
        quantity = qu;
        note = n;
    }

    //returns object info in an arraylist that can be extracted if necessary
    //All data fields are provided from this method
    public ArrayList<String> getFullObjInfo() {
        ArrayList<String> objectsForReturn = new ArrayList();

        //objectsForReturn.add(ID.toString());
        objectsForReturn.add(objectTitle);
        objectsForReturn.add(quantity);
        objectsForReturn.add(note);
        //objectsForReturn.add(dateLastUpdated);
        

        return objectsForReturn;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getObjectTitle() {
        return objectTitle;
    }

    public void setObjectTitle(String objectTitle) {
        this.objectTitle = objectTitle;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getDateLastUpdated() {
        return dateLastUpdated;
    }

    public void setDateLastUpdated(String dateLastUpdated) {
        this.dateLastUpdated = dateLastUpdated;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String toString() {
        return "ID: " + ID + "\n"
                + "Object: " + objectTitle + "\n"
                + "Quantity: " + quantity + "\n"
                + "Notes: " + note + "\n"
                + "Data Last Updated: " + dateLastUpdated + "\n";
    }
}
