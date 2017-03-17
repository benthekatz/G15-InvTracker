import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import javax.swing.*;

public class NewUser extends JFrame implements ActionListener {
    
    private static final int FRAME_WIDTH = 250;
    private static final int FRAME_HEIGHT = 250;
    
    private JButton CreateUsername;
    private JButton Cancel;
    
    private JLabel NewUserLabel;
    private JTextField Username;
    
    private JLabel PasswordLabel;
    private JPasswordField UsernamePassword;
    
    private JLabel confirmPasswordLabel;
    private JPasswordField confirmUsernamePassword;

    public static Database database;
    

    public NewUser() {
        this.setSize(FRAME_WIDTH, FRAME_HEIGHT);
        this.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        this.setVisible(true);

        Username = new JTextField(20);
        UsernamePassword = new JPasswordField();
        confirmUsernamePassword = new JPasswordField();

        //JLabels
        NewUserLabel = new JLabel("User Name:");
        PasswordLabel = new JLabel("Password:");
        confirmPasswordLabel = new JLabel("Confirm Password:");

        //Jbuttons
        CreateUsername = new JButton("Submit");
        Cancel = new JButton("Cancel");
        this.add(createPanel()); 
        
        //actionlisteners
        CreateUsername.addActionListener(this);
        Cancel.addActionListener(this);

    }

    private JPanel createPanel() {
        JPanel panel = new JPanel(new GridLayout(4, 2));
        panel.add(NewUserLabel);
        panel.add(Username);
        panel.add(PasswordLabel);
        panel.add(UsernamePassword);
        panel.add(confirmPasswordLabel);
        panel.add(confirmUsernamePassword);
        panel.add(CreateUsername);
        panel.add(Cancel);
        return panel;
    }

    public void actionPerformed(ActionEvent ae) {
        
        if (ae.getSource() == CreateUsername) {
        	//makes sure text fields are not empty
                if(Username.getText().equals(""))
                {
                    JOptionPane.showMessageDialog(null,"Your Account entry is empty","Failed",JOptionPane.ERROR_MESSAGE);
                    return;
                }
                 if(String.valueOf(UsernamePassword.getPassword()).equals(""))
                {
                    JOptionPane.showMessageDialog(null,"Your password entry is empty","Failed",JOptionPane.ERROR_MESSAGE);
                    return;
                }
                 if(String.valueOf(confirmUsernamePassword.getPassword()).equals(""))
                 {
                    JOptionPane.showMessageDialog(null,"Your confirmed password entry is empty","Failed",JOptionPane.ERROR_MESSAGE);
                    return;                     
                 }
                 if(new File(Username.getText() +".ser").exists())
                 {
                    JOptionPane.showMessageDialog(null,"Account already exists","Failed",JOptionPane.ERROR_MESSAGE);
                    return;
                 }
                if(!(String.valueOf(UsernamePassword.getPassword()).equals(String.valueOf(confirmUsernamePassword.getPassword()))))
                {
                    JOptionPane.showMessageDialog(null,"Your confirmed password does not matach","Failed",JOptionPane.ERROR_MESSAGE);
                    return;
                }
                
                System.out.print(UsernamePassword.getPassword());
                
                
                
                database = new Database(Username.getText(), String.valueOf(UsernamePassword.getPassword()));
        	writeToSer(Username.getText());
                JOptionPane.showMessageDialog(null,"Account Created Successfully.");
        	this.setVisible(false);
                
        }
        if (ae.getSource() == Cancel) {
            this.dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
        }
    }
      private void writeToSer(String un) {
        try{
            FileOutputStream fo = new FileOutputStream(un+".ser");
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

