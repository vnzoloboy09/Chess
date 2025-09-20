import javax.swing.*;
import java.awt.*;

public class Board extends JPanel {
    private final int m_tileSize = 64;
    private final int m_Rows = 8;
    private final int m_Cols = 8;

    public Board() {
        this.setPreferredSize(new Dimension(m_Cols * m_tileSize, m_Rows * m_tileSize));
    }

    public void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;

        for(int r = 0; r < m_Rows; r++) {
            for(int c = 0; c < m_Cols; c++) {
                g2d.setColor(((c + r) % 2) == 1? new Color(85, 150, 242) : new Color(242, 246, 250));
                g2d.fillRect(r * m_tileSize, c * m_tileSize, m_tileSize, m_tileSize);
            }
        }
    }
}
