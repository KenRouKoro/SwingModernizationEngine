package cn.korostudio.phy.panel;

import javax.swing.*;
import java.awt.*;

public class Particle extends JPanel {
    @Override
    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2d=(Graphics2D) g;
        g2d.fillRect(0,0,getWidth(),getHeight());
    }
}
