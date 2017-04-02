
import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.*;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.lang.String;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;

public class MainMenu extends JFrame implements ActionListener, TableModelListener {

    private static ArrayList<InvObject> tableObjs = app.getDBObjects();
    private static String[] objsToDisplay;
    private static DefaultTableModel model;

    private static final int FRAME_WIDTH = 600;
    private static final int FRAME_HEIGHT = 400;

    private static boolean changed = false;

    //table
    private JTable table;
    
    private JLabel hints;

    //buttons
    private JButton AddEntry;
    private JButton editEntry;
    private JButton deleteEntry;
    private static JButton save;
    private JButton Logout;

    //other
    private JFrame ui;
    private JPopupMenu pm;
    private JMenuItem d;
    private JPanel jp;

    private int rowSelect;
    private int colSelect;

    public MainMenu() {

        setObjDisplay();
        save = new JButton("Save");
        save.setEnabled(false);
        save.addActionListener(this);

        table = new JTable(model){
        	public boolean isCellEditable(int row, int column){
                return false;
        	}
        };
        table.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 16));
        table.setFont(new Font("Serif", Font.PLAIN, 16));
        
        getContentPane().add(new JScrollPane(table), BorderLayout.CENTER);
        table.getModel().addTableModelListener(this);

        hints = new JLabel("<html>Click a cell and 'Edit Entry' to edit said cell.<br>Click a cell and 'Delete Entry' to delete the row.");
        
        //New object and action listener
        AddEntry = new JButton("Add Entry");
        AddEntry.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                new AddEntry().setVisible(true);
            }
        });
        
        //Edit and action listener
        editEntry = new JButton("Edit Entry");
        editEntry.setEnabled(false);
        editEntry.addActionListener(this);
        
        //Delete and action listener
        deleteEntry = new JButton("Delete Entry");
        deleteEntry.setEnabled(false);
        deleteEntry.addActionListener(this);
        
        //Logout and action listener
        Logout = new JButton("Logout");
        Logout.addActionListener(this);

        jp = new JPanel();
        jp.add(hints);
        jp.add(AddEntry);
        jp.add(editEntry);
        jp.add(deleteEntry);
        jp.add(Logout);
        jp.add(save);
        jp.setFocusable(true);
        
        pm = new JPopupMenu();
        d = new JMenuItem("Deleted");
        pm.add(d);
        d.addActionListener(this);
        table.setComponentPopupMenu(pm);
        table.setCellSelectionEnabled(true);
        ListSelectionModel cellSelectionModel = table.getSelectionModel();
        cellSelectionModel.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
        table.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                int row = table.rowAtPoint(evt.getPoint());
                rowSelect = row;
                int column = table.columnAtPoint(evt.getPoint());
                colSelect = column;
                if (row >= 0 && column >= 0) {
                    editEntry.setEnabled(true);
                    deleteEntry.setEnabled(true);

                }
            }
        });

        this.add(jp, BorderLayout.SOUTH);
        this.setTitle("Inventory Tracker");
        //this.setSize(FRAME_WIDTH, FRAME_HEIGHT);
        this.pack();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private static void updateTableObjs() {
        tableObjs = app.getDBObjects();
    }

    public static void setObjDisplay() {
        updateTableObjs();

        String[] info = null;
        if (tableObjs.size() > 0) {
            for (int i = 0; i < tableObjs.size(); i++) {
                info = new String[3];
                for (int j = 0; j < tableObjs.get(i).getFullObjInfo().size(); j++) {
                    info[j] = tableObjs.get(i).getFullObjInfo().get(j);
                }

                addRows(info[0], info[1], info[2]);
            }

        }
        model = new DefaultTableModel(null, new Object[]{"Object Title", "Quantity", "Note"});

    }

    public void rmRows() {//throws ArrayIndexOutOfBoundsException {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                app.activeDB.getObjects().remove(table.getSelectedRow());
                model.removeRow(table.getSelectedRow());
                System.out.println(table.getSelectedRow());

                save.setEnabled(true);
                changed = true;
            }

        });

    }

    public static void addRows(String title, String quantity, String note) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                model.addRow(new Object[]{title, quantity, note});
                save.setEnabled(true);
                changed = true;
            }

        });

    }

    @Override

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == d) {
            rmRows();
            
        } else if (e.getSource() == save) {
            savingObject();
            save.setEnabled(false);
            changed = false;
            
        } else if (e.getSource()== editEntry){
        	String temp = "";
        	
        	if(colSelect==0){
        	temp = JOptionPane.showInputDialog("Enter a new name value: ");
                if(temp == null || (temp != null && ("".equals(temp))))   
                    {
                        //do nothing
                    }
                else{
        	table.setValueAt(temp, rowSelect, colSelect);
                updateArrayList(rowSelect, colSelect);
                }
        	
        	} else if (colSelect==1){
        	temp = JOptionPane.showInputDialog("Enter a new quantity value: ");
                if(temp == null || (temp != null && ("".equals(temp))))   
                    {
                        //do nothing
                    }
                else{
        	int x = Integer.parseInt(temp);
        	table.setValueAt(x, rowSelect, colSelect);
                updateArrayList(rowSelect, colSelect);
                }
        	
        	} else if (colSelect==2){
        	temp = JOptionPane.showInputDialog("Enter a new notes value: ");
                if(temp == null || (temp != null && ("".equals(temp))))   
                    {
                        //do nothing
                    }
                else{
                table.setValueAt(temp, rowSelect, colSelect);
                updateArrayList(rowSelect, colSelect);
                }
        	}

        	jp.requestFocus();
        	editEntry.setEnabled(false);
        	updateTableObjs();
        	
        } else if (e.getSource() == deleteEntry){
        	String name = (String) table.getModel().getValueAt(rowSelect, colSelect);
        	int choice = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete your entry: " +name+ "?", "Are you sure?", JOptionPane.YES_NO_OPTION);
        	if(choice == 0){
        	rmRows();
        	}
        	
        	deleteEntry.setEnabled(false);
        	
        } else if (e.getSource() == Logout) {
        //old logout method below
            /*int x = JOptionPane.showConfirmDialog(null, "Are you sure you want to log out?");
            if (x == JOptionPane.YES_OPTION) {
                this.setVisible(false);
                new InitialFrame();
            }*/
        	
        if(changed == true){
        int answer = JOptionPane.showConfirmDialog(null, "Would You Like to Save your Changes?", "Warning", JOptionPane.YES_NO_CANCEL_OPTION);
                if (answer == JOptionPane.YES_OPTION) {
                    savingObject();
                    this.setVisible(false);
                    app.main(new String[0]);
                } else if (answer == JOptionPane.NO_OPTION) {
                    this.setVisible(false);
                    app.main(new String[0]);
                } else if (answer == JOptionPane.CANCEL_OPTION) {
                    
                }
        } else if (changed == false){
        	this.setVisible(false);
            app.main(new String[0]);
        }
        }
    }

    private void savingObject() {
        try {
            FileOutputStream fos = new FileOutputStream(app.activeDB.getUserName() + ".ser");
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(app.activeDB);
            oos.close();
            fos.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Unable to save", "Fatal Error", JOptionPane.ERROR_MESSAGE);
            System.out.println(e);
        }

    }

    @Override
    public void tableChanged(TableModelEvent e) {

        int row = e.getFirstRow();
        int col = e.getColumn();
        if (row < 0 || col < 0) {
            return;
        }
        // System.out.println(row + " " + col);
        updateArrayList(row, col);
        save.setEnabled(true);
        changed = true;
    }

    private void updateArrayList(int row, int col) {
    	int actualVal = row;
    	if (col == 0) {
            app.activeDB.getObjects().get(actualVal).setObjectTitle(table.getValueAt(row, col).toString());
            System.out.print(actualVal);
        }
        else if (col == 1) {
            app.activeDB.getObjects().get(actualVal).setQuantity(table.getValueAt(row, col).toString());
            System.out.print(actualVal);
        }
        else if (col == 2) {
            app.activeDB.getObjects().get(actualVal).setNote(table.getValueAt(row, col).toString());
            System.out.print(actualVal);
        }
    }
}
