/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package task8;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.CubicCurve2D;
import java.awt.geom.Line2D;
import java.awt.geom.QuadCurve2D;
import java.util.ArrayList;
import javax.swing.JPanel;

/**
 *
 * @author ebrarsahin
 */
public class BezierCurve extends JPanel {

    //(1-t) P0 + t P1
    Point p1, p2, p3,p4;
    boolean quad=false;
    ArrayList<Point> points = new ArrayList<>();
    boolean isItCurve=false;
    BezierCurve(Point p1, Point p2) {
        this.p1 = p1;
        this.p2 = p2;
    }

    BezierCurve(Point p1, Point p2, Point p3) {
        quad=true;
        isItCurve=true;
        this.p1 = p1;
        this.p2 = p2;
        this.p3 = p3;
    }

    BezierCurve(Point p1, Point p2, Point p3, Point p4) {
        isItCurve=true;
        quad=false;
        this.p1 = p1;
        this.p2 = p2;
        this.p3 = p3;
        this.p4=p4;
    }

    private void calculatePoints() {
        
        for (float t = 0; t <= 1; t += 0.1) {
            if (t == 0) {
                points.add(p1);
            } else if (t == 1) {
                points.add(p2);
            } 
            else {
                float x = p1.x;
                float y = p1.y;
                x = ((1 - t) * x) + (t * p2.x);
                y = ((1 - t) * y) + (t * p2.y);
                points.add(new Point(Math.round(x), Math.round(y)));
            }
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        calculatePoints();
        
        Graphics2D g2 = (Graphics2D) g;
        super.paintComponent(g2);

        BasicStroke bs = new BasicStroke(6.0f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_MITER);

        g2.setStroke(bs);
        g2.setColor(Color.red);
        if(isItCurve==false){
            for (int i = 1; i < points.size(); i++) {
                g2.draw(new Line2D.Double(points.get(i - 1).x, points.get(i - 1).y, points.get(i).x, points.get(i).y));
            }       
        }
        else if(isItCurve==true && quad==true){
            QuadCurve2D quadcurve = new QuadCurve2D.Float(p1.x, p1.y, p2.x, p2.y, p3.x, p3.y);
            g2.draw(quadcurve);
        }
        else if(isItCurve==true && quad==false){
            CubicCurve2D cc= new CubicCurve2D.Float(p1.x, p1.y, p2.x, p2.y, p3.x, p3.y,p4.x,p4.y);
            g2.draw(cc);
        }
        points.removeAll(points);
    }
}
