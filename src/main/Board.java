package main;

import java.awt.*;
import java.util.ArrayList;

import main.piece.*;

public class Board {
    public final int tileSize = 100;
    public final int rowSize = 8;
    public final int colSize = 8;

    public Board() {
    }

    public void draw(Graphics2D g2d) {
        for(int r = 0; r < rowSize; r++) {
            for(int c = 0; c < colSize; c++) {
                g2d.setColor((c + r) % 2 == 0? new Color(242, 246, 250) : new Color(85, 150, 242));
                g2d.fillRect(r * tileSize, c * tileSize, tileSize, tileSize);
            }
        }
    }
}