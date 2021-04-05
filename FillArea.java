import javax.swing.*;

public class FillArea extends JFrame {

    private final int wndWidth = 800, wndHeight = 600;

    public FillArea() {
        super("Window");
        setSize(wndWidth, wndHeight);
        setResizable(false);
        //setBackground(Color.WHITE);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        add(new DrawingComp());

    }

    public static void main(String[] args) {
        new FillArea().setVisible(true);
    }
}
