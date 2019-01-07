import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;

public class MainWindow extends JFrame {

    public static final String MAIN_WINDOW_NAME = "Auctioneer";
    private final JLabel sniperStatus = createLabel(Main.STATUS_JOINING);

    public MainWindow() {
        super("AuctionSniper");
        setName(MAIN_WINDOW_NAME);
        add(sniperStatus);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }
    private static JLabel createLabel(String initialText) {
        JLabel result = new JLabel(initialText);
        result.setName(Main.SNIPER_STATUS_NAME);
        result.setBorder(new LineBorder(Color.BLACK));
        return result;
    }

}
