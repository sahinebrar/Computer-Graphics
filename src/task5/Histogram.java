/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package task5;

/**
 *
 * @author ebrarsahin
 */
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Paint;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.awt.image.Raster;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.DefaultDrawingSupplier;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.StandardXYBarPainter;
import org.jfree.chart.renderer.xy.XYBarRenderer;
import org.jfree.data.statistics.HistogramDataset;
import task4.MyImage;

/**
 * @see http://stackoverflow.com/q/40537278/230513
 * @see http://stackoverflow.com/q/11870416/230513
 * @see http://stackoverflow.com/a/28519356/230513
 */
public class Histogram {

    private static final int BINS = 256;
    
    private HistogramDataset dataset;
    private XYBarRenderer renderer;
    BufferedImage img;
    JFrame myframe;
    private void getImage() {
        String userDir = System.getProperty("user.home");
        JFileChooser fileChooser = new JFileChooser(userDir + "/Desktop");
        int result = fileChooser.showOpenDialog(null);
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            try {
                img = ImageIO.read(selectedFile);
                myframe = get_MYIMAGE();
            } catch (IOException ex) {
                Logger.getLogger(MyImage.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
    }

    private ChartPanel createChartPanel() {
        // dataset
        dataset = new HistogramDataset();
        Raster raster = img.getRaster();
        final int w = img.getWidth();
        final int h = img.getHeight();
        double[] r = new double[w * h+1];
        r = raster.getSamples(0, 0, w, h, 0, r);
        dataset.addSeries("Red", r, BINS);
        r = raster.getSamples(0, 0, w, h, 1, r);
        dataset.addSeries("Green", r, BINS);
        r = raster.getSamples(0, 0, w, h, 2, r);
        dataset.addSeries("Blue", r, BINS);
        // chart
        JFreeChart chart = ChartFactory.createHistogram("Histogram", "Value",
            "Count", dataset, PlotOrientation.VERTICAL, true, true, false);
        XYPlot plot = (XYPlot) chart.getPlot();
        renderer = (XYBarRenderer) plot.getRenderer();
        renderer.setBarPainter(new StandardXYBarPainter());
        // translucent red, green & blue
        Paint[] paintArray = {
            new Color(0x80ff0000, true),
            new Color(0x8000ff00, true),
            new Color(0x800000ff, true)
        };
        plot.setDrawingSupplier(new DefaultDrawingSupplier(
            paintArray,
            DefaultDrawingSupplier.DEFAULT_FILL_PAINT_SEQUENCE,
            DefaultDrawingSupplier.DEFAULT_OUTLINE_PAINT_SEQUENCE,
            DefaultDrawingSupplier.DEFAULT_STROKE_SEQUENCE,
            DefaultDrawingSupplier.DEFAULT_OUTLINE_STROKE_SEQUENCE,
            DefaultDrawingSupplier.DEFAULT_SHAPE_SEQUENCE));
        ChartPanel panel = new ChartPanel(chart);
        panel.setMouseWheelEnabled(true);
        return panel;
    }

    private JPanel createControlPanel() {
        JPanel panel = new JPanel();
        panel.add(new JCheckBox(new VisibleAction(0)));
        panel.add(new JCheckBox(new VisibleAction(1)));
        panel.add(new JCheckBox(new VisibleAction(2)));
        return panel;
    }

    private class VisibleAction extends AbstractAction {

        private final int i;

        public VisibleAction(int i) {
            this.i = i;
            this.putValue(NAME, (String) dataset.getSeriesKey(i));
            this.putValue(SELECTED_KEY, true);
            renderer.setSeriesVisible(i, true);
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            renderer.setSeriesVisible(i, !renderer.getSeriesVisible(i));
        }
    }

    private void display() {
        JFrame f = new JFrame("Histogram");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.add(createChartPanel());
        f.add(createControlPanel(), BorderLayout.SOUTH);
        f.add(new JLabel(new ImageIcon(img)), BorderLayout.WEST);
        f.pack();
        f.setLocationRelativeTo(null);
        f.setVisible(true);
    }
    private JFrame get_MYIMAGE() {
        JFrame frame = new JFrame("picture");
        frame.setSize(img.getWidth(), img.getHeight());
        JLabel l = new JLabel();
        l.setSize(img.getWidth(), img.getHeight());
        l.setIcon((new ImageIcon(img)));
        frame.add(l);
        frame.setVisible(true);
        return frame;
    }
    public static void main(String[] args) {
        Histogram h=new Histogram();
        h.getImage();
        EventQueue.invokeLater(() -> {
            h.display();
        });
    }
}
