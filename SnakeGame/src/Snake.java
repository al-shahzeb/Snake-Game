import javax.swing.*;
import java.awt.*;

public class Snake {
    public static void main(String[] args) {
        new MyFrame();
    }
}
class MyFrame{
    JPanel jPanel;
    MyFrame(){
        JFrame jFrame = new JFrame();
        jFrame.setTitle("SNAKE");
        jFrame.setBounds(10,10,900,700);
        jPanel = new MyPanel();

        jFrame.add(jPanel);
        jFrame.setVisible(true);
        jFrame.setResizable(false);
        jFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }
}
