/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package task1;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;
import javax.swing.JPanel;

/**
 *
 * @author ebrarsahin
 */
public class Line extends JPanel{
    int x1,x2,y1,y2;
    
    Line(int x1,int x2,int y1,int y2){
        this.x1= x1;
        this.y1= y1;
        this.x2= x2;
        this.y2= y2;
    }
    
    @Override
    public void paintComponent(Graphics g){
        Graphics2D g2= (Graphics2D) g;
        super.paintComponent(g2);
        
        BasicStroke bs =new BasicStroke(6.0f,BasicStroke.CAP_ROUND, BasicStroke.JOIN_MITER);
        
        g2.setStroke(bs);
        g2.setColor(Color.red);
        g2.draw(new Line2D.Double(x1, y1, x2, y2));


    }
}
