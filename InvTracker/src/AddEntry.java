import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import javax.swing.*;

public class AddEntry extends JFrame implements ActionListener {
    
    private static final int FRAME_WIDTH = 350;
    private static final int FRAME_HEIGHT = 250;
    
    private JButton AddEntry, Cancel;
    
    private JLabel objectName, objectQuantity;
    private JTextField nameField, quantityField;
    private JTextArea noteField;

    public static Database database;
    

    public AddEntry() {
        this.setSize(FRAME_WIDTH, FRAME_HEIGHT);
        this.setTitle("Add Entry");
        this.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        this.setVisible(true);
        
        //Labels
        objectName = new JLabel("Name:");
        objectQuantity = new JLabel("   Quantity:");
        
        //Text fields
        nameField = new JTextField(10);
        quantityField = new JTextField(10);
        noteField = new JTextArea(4,20);
        noteField.setLineWrap(true);
        noteField.append("Enter notes here.");
        noteField.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseClicked(MouseEvent e){
                noteField.setText("");
            }
        });

        //Jbuttons
        AddEntry = new JButton("Add Entry");
        Cancel = new JButton("Cancel");
        this.add(createPanel()); 
        
        //actionlisteners
        AddEntry.addActionListener(this);
        Cancel.addActionListener(this);
        this.setVisible(true);

    }

    private JPanel createPanel() {
        JPanel panel = new JPanel(new GridLayout(4,1));
        JPanel namepanel = new JPanel(new FlowLayout());
        JPanel numpanel = new JPanel(new FlowLayout());
        JPanel notepanel = new JPanel(new FlowLayout());
        JPanel buttonpanel = new JPanel(new FlowLayout());
        namepanel.add(objectName);
        namepanel.add(nameField);
        numpanel.add(objectQuantity);
        numpanel.add(quantityField);
        notepanel.add(noteField);
        buttonpanel.add(AddEntry);
        buttonpanel.add(Cancel);
        panel.add(namepanel);
        panel.add(numpanel);
        panel.add(notepanel);
        panel.add(buttonpanel);
        return panel;
    }

    public void actionPerformed(ActionEvent ae) {
        
        if (ae.getSource() == AddEntry) {
        	//makes sure text fields are not empty
                if(nameField.getText().equals(""))
                {
                    JOptionPane.showMessageDialog(null,"You didn't provide a name","Failed",JOptionPane.ERROR_MESSAGE);
                    return;
                }
                 if(String.valueOf(quantityField.getText()).equals(""))
                {
                    JOptionPane.showMessageDialog(null,"You didn't provide a quantity","Failed",JOptionPane.ERROR_MESSAGE);
                    return;
                }       
                
                 //entry is placeholder for addentry information
                InvObject entry = new InvObject(nameField.getText(),quantityField.getText(), noteField.getText());
                
                //append InvObject information to activeDB in main class (app)
                app.appendObject(entry);
                
                MainMenu.addRows(nameField.getText(), quantityField.getText(), noteField.getText());
                
                //writes to username.ser file
        	appendToSer(LoginPanel.whosLoggedOn(), entry); 
                
                this.dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
        }
        if (ae.getSource() == Cancel) {
            this.dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
        }
    }
      private void appendToSer(String usr, InvObject entry) {
        try{
            FileOutputStream fo = new FileOutputStream(usr+"Inventory.ser");
            ObjectOutputStream os = new ObjectOutputStream(fo);
            os.writeObject(database);
            os.close();
            fo.close();

        }
        catch(Exception e)
        {
            JOptionPane.showMessageDialog(null, "Cant not save your info into local", "Error", JOptionPane.ERROR_MESSAGE);
            System.out.print(e);
            return;
        }
    }
    
    
}

