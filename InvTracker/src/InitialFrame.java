import java.awt.*;
import javax.swing.*;
import static javax.swing.JFrame.EXIT_ON_CLOSE;

public class InitialFrame extends JFrame{

    public InitialFrame(){
        super("Log In");
        LoginPanel lp = new LoginPanel();
        this.add(lp);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(350,150);
        setResizable(false);
        setVisible(true);
    }
    
    public void setInvisible(){
        this.setVisible(false);
    }

}
