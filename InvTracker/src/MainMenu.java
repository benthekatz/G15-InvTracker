
import java.awt.BorderLayout;
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
    private static JTable table;

    //buttons
    private JButton AddEntry;
    private static JButton save;
    private JButton Logout;

    //other
    private JFrame ui;
    private JPopupMenu pm;
    private JMenuItem d;

    public MainMenu() {

        setObjDisplay();
        save = new JButton("Save");
        save.setEnabled(false);
        save.addActionListener(this);

        table = new JTable(model);
        getContentPane().add(new JScrollPane(table), BorderLayout.CENTER);
        table.getModel().addTableModelListener(this);

        //New object and action listener
        AddEntry = new JButton("Add Entry");
        AddEntry.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                new AddEntry().setVisible(true);
            }
        });

        //Logout and action listener
        Logout = new JButton("Logout");
        Logout.addActionListener(this);

        JPanel jp = new JPanel();
        jp.add(AddEntry);
        jp.add(Logout);
        jp.add(save);

        pm = new JPopupMenu();
        d = new JMenuItem("Deleted");
        pm.add(d);
        d.addActionListener(this);
        table.setComponentPopupMenu(pm);
        table.setCellSelectionEnabled(true);
        ListSelectionModel cellSelectionModel = table.getSelectionModel();
        cellSelectionModel.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        this.add(jp, BorderLayout.SOUTH);
        this.setTitle("Inventory Tracker");
        this.setSize(FRAME_WIDTH, FRAME_HEIGHT);
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

    public void rmRows() throws ArrayIndexOutOfBoundsException {
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
        } else if (e.getSource() == Logout) {
        //old logout method below
            /*int x = JOptionPane.showConfirmDialog(null, "Are you sure you want to log out?");
            if (x == JOptionPane.YES_OPTION) {
                this.setVisible(false);
                new InitialFrame();
            }*/
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
        if (col == 0) {
            app.activeDB.getObjects().get(row).setID(table.getValueAt(row, col).toString());
        }
        if (col == 1) {
            app.activeDB.getObjects().get(row).setObjectTitle(table.getValueAt(row, col).toString());
        }
        if (col == 2) {
            app.activeDB.getObjects().get(row).setQuantity(table.getValueAt(row, col).toString());
        }
        if (col == 3) {
            app.activeDB.getObjects().get(row).setNote(table.getValueAt(row, col).toString());
        }
    }

}
