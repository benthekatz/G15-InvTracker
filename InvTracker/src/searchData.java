
import java.awt.BorderLayout;
import java.awt.Font;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Zach
 */
public class searchData {
    
    ArrayList<InvObject> dataToSearch =  app.activeDB.getObjects();
    ArrayList<InvObject> searchResults = new ArrayList<InvObject>();
    
    String giantResultsString;
    
    public searchData(String keyword){
        
        for(int i = 0; i < dataToSearch.size(); i++){
            if(dataToSearch.get(i).getObjectTitle().contains(keyword)){
                searchResults.add(dataToSearch.get(i));
            }
        }
        giantResultsString="";
       for(int i = 0; i < searchResults.size(); i++){
           if(searchResults.isEmpty()){
               JOptionPane.showMessageDialog(null, "No results for: " + keyword);
           }
         giantResultsString = giantResultsString += "Name: " + searchResults.get(i).getObjectTitle() + " || Quantity: " + dataToSearch.get(i).getQuantity() + " || Notes: " + dataToSearch.get(i).getNote() + " || Date Added: " + dataToSearch.get(i).getDateLastUpdated() + "\n";
       }
       JOptionPane.showMessageDialog(null, giantResultsString);
       
    }
}
