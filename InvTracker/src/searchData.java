
import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Font;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.event.TableModelListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;
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
    
    JFrame frame;
    JTable table;
    JPanel tablePanel;
    String col[] = {"Name","Quantity","Notes","Date Added"};
    
    public searchData(String keyword){
        
        fillSearchResults(keyword);
                DefaultTableModel tableModel = new DefaultTableModel(col, 0);
                table = new JTable(tableModel);
                table.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 16));
                table.getColumnModel().getColumn(2).setPreferredWidth(100);
                tablePanel = new JPanel();
                tablePanel.add(table);
                
                String rowName;
                String rowQuantity;
                String rowNote;
                String rowDate;
                for(int i = 0; i < searchResults.size(); i++){
                    
                    rowName = searchResults.get(i).getObjectTitle();
                    rowQuantity = searchResults.get(i).getQuantity();
                    rowNote = searchResults.get(i).getNote();
                    rowDate = searchResults.get(i).getDateLastUpdated();
                    
                    tableModel.addRow(new Object[]{rowName, rowQuantity, rowNote, rowDate});
                }
                
                frame = new JFrame("Search Results");
                frame.setSize(600,600);
                frame.add(tablePanel);
                frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                frame.add(new JScrollPane(table));
                //frame.pack();
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
       
    }
    public void fillSearchResults(String searchKey){
        
        for(int i = 0; i < dataToSearch.size(); i++){
            if(dataToSearch.get(i).getObjectTitle().contains(searchKey)){
                searchResults.add(dataToSearch.get(i));
            }
        }
    }
}