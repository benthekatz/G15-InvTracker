;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.ObjectInputStream;
import javax.swing.*;

/**
 *
 * @author Ben
 */
public class LoginPanel extends JPanel implements ActionListener {

    //access to database
    static private String currentUser;
    
    private JTextField username;
    private JPasswordField password;

    private JLabel u;
    private JLabel p;

    private JButton submit;
    private JButton newUser;
    private InitialFrame f;
    public LoginPanel() {
        
        //initilizing
        setBackground(Color.LIGHT_GRAY);

        //Jtextfields
        username = new JTextField(20);
        password = new JPasswordField(20);

        //JLabels
        u = new JLabel("Username:");
        p = new JLabel("Password:");

        //Jbuttons
        submit = new JButton("Log In");
        newUser = new JButton("New User");

        //add
        add(u);
        add(username);
        add(p);
        add(password);
        add(submit);
        add(newUser);
        
        submit.addActionListener(this);
        newUser.addActionListener(this);
        
    }

    private String getUsername() {
        String temp_user = username.getText();
        return temp_user;
    }

    private String getPassword() {
        String temp_pass = String.valueOf(password.getPassword());
        return temp_pass;
    }

    public void verifyDB(){
    	if (getInfo()==1)
    	{
            app.setInvisible();
           // JOptionPane.showMessageDialog(null, "Login Successful");
            currentUser = getUsername();
            new MainMenu().setVisible(true);
    	}
        else if (getInfo() == 2)
        {
            JOptionPane.showMessageDialog(null, "Incorrect username or password.","Authentication Failed",JOptionPane.ERROR_MESSAGE);
        }
        else if (getInfo() == 3)
        {
            JOptionPane.showMessageDialog(null, "Incorrect username or password.", "Fatal Error", JOptionPane.ERROR_MESSAGE);
        }
        else if(getInfo() == 4)
        {
            JOptionPane.showMessageDialog(null,"Error: Cant read the file.","Fatal Error",JOptionPane.ERROR_MESSAGE);
        }
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == newUser) {
            new NewUser().setVisible(true);
        }
        if (ae.getSource() == submit) {
                if(username.getText().equals(""))
                {
                    JOptionPane.showMessageDialog(null, "You did not enter a username.", "warning", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                if(String.valueOf(password.getPassword()).equals(""))
                {
                    JOptionPane.showMessageDialog(null, "You did not enter a password.", "warning", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                verifyDB();
        }
    }

    //login confirmation
    private int getInfo() {
        
        Database db = null;
        try
        {
            FileInputStream fi = new FileInputStream (getUsername()+".ser");
            ObjectInputStream oi = new ObjectInputStream(fi);
            db = (Database)oi.readObject();
            oi.close();
            fi.close();
            app.setDatabase(db);
            
        }
        catch(FileNotFoundException e)
        {
            return 3;
        }
        catch(Exception en)
        {    
            System.out.println(en);
            return 4;
            
        }
        
        
        if(db.verifyLoginCredentials(getUsername(),getPassword()))
        {
            return 1;
        }
        else
            return 2;
    }
    
    static String whosLoggedOn(){
        return currentUser;
    }
}

