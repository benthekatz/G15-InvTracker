import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import javax.swing.*;

public class NewUser extends JFrame implements ActionListener {
    
    private static final int FRAME_WIDTH = 350;
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

        Username = new JTextField(10);
        UsernamePassword = new JPasswordField(10);
        confirmUsernamePassword = new JPasswordField(10);

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
        JPanel panel = new JPanel(new GridLayout(4, 1));
        JPanel usr = new JPanel(new FlowLayout());
        JPanel pass = new JPanel(new FlowLayout());
        JPanel conf = new JPanel(new FlowLayout());
        JPanel buttons = new JPanel(new FlowLayout());
        usr.add(NewUserLabel);
        usr.add(Username);
        pass.add(PasswordLabel);
        pass.add(UsernamePassword);
        conf.add(confirmPasswordLabel);
        conf.add(confirmUsernamePassword);
        buttons.add(CreateUsername);
        buttons.add(Cancel);
        panel.add(usr);
        panel.add(pass);
        panel.add(conf);
        panel.add(buttons);
        return panel;
    }

    public void actionPerformed(ActionEvent ae) {
        
        if (ae.getSource() == CreateUsername) {
        	//makes sure text fields are not empty
                if(Username.getText().equals(""))
                {
                    JOptionPane.showMessageDialog(null,"Please enter a username.","Failed",JOptionPane.ERROR_MESSAGE);
                    return;
                }
                 if(String.valueOf(UsernamePassword.getPassword()).equals(""))
                {
                    JOptionPane.showMessageDialog(null,"Please enter a password.","Failed",JOptionPane.ERROR_MESSAGE);
                    return;
                }
                 if(String.valueOf(confirmUsernamePassword.getPassword()).equals(""))
                 {
                    JOptionPane.showMessageDialog(null,"Please confirm your password.","Failed",JOptionPane.ERROR_MESSAGE);
                    return;                     
                 }
                 if(new File(Username.getText() +".ser").exists())
                 {
                    JOptionPane.showMessageDialog(null,"Account already exists.","Failed",JOptionPane.ERROR_MESSAGE);
                    return;
                 }
                if(!(String.valueOf(UsernamePassword.getPassword()).equals(String.valueOf(confirmUsernamePassword.getPassword()))))
                {
                    JOptionPane.showMessageDialog(null,"Your original and confirmed passwords do not match.","Failed",JOptionPane.ERROR_MESSAGE);
                    return;
                }
                
                System.out.print(UsernamePassword.getPassword());
                
                
                
                database = new Database(Username.getText(), String.valueOf(UsernamePassword.getPassword()));
        	writeToSer(Username.getText());
                JOptionPane.showMessageDialog(null,"Account created successfully.");
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
            JOptionPane.showMessageDialog(null, "Error: Can't save your info into local storage.", "Error", JOptionPane.ERROR_MESSAGE);
            System.out.print(e);
            return;
        }
    }
    
    
}

