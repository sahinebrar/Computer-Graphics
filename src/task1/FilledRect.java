/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package task1;

import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import javax.swing.JPanel;

/**
 *
 * @author ebrarsahin
 */
public class FilledRect extends JPanel{
    int x,y,w,h;
    
    FilledRect(int x, int y, int w, int h){
        this.x= x;
        this.y= y;
        this.w= w;
        this.h= h;
    }
    @Override
    public void paintComponent(Graphics g){
        Graphics2D g2= (Graphics2D) g;
        super.paintComponent(g2);
        
        g2.setPaint(new GradientPaint(x,y,Color.RED,w, h,Color.WHITE));
        g2.fill(new Rectangle2D.Double(x, y, w, h));
    }
}
