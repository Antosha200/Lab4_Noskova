import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Stack;

public class DrawingComp extends JPanel {
    private LinkedList<Point> points = new LinkedList<>();
    private LinkedList<Point> fillPoints = new LinkedList<>();
    private LinkedList<Point> filledPt = new LinkedList<>();
    private Stack<Point> stack = new Stack<>();
    private int startRGB;

    public DrawingComp() {
        setBackground(Color.WHITE);
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                if (e.getButton() == MouseEvent.BUTTON1) {
                    points.add(new Point(e.getX(), e.getY()));
                    repaint();
                }
                if (e.getButton() == MouseEvent.BUTTON3) {
                    fillPoints.add(new Point(e.getX(), e.getY()));
                    fillAreas2();
                    repaint();
                }
            }

        });

    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D gr = (Graphics2D) g;
        drawAxes(gr);
        drawLines(gr);
        Iterator<Point> itFill = filledPt.iterator();
        while (itFill.hasNext()) {
            Point p = itFill.next();
            gr.drawLine(p.x, p.y, p.x, p.y);
        }
    }

    private void fillAreas(Graphics2D gr) {
        Robot rob = null;
        try {
            rob = new Robot();
        } catch (AWTException ex) {
            ex.printStackTrace();
        }
        Dimension d = new Dimension(DrawingComp.this.getWidth(), DrawingComp.this.getHeight());
        Rectangle r = new Rectangle(DrawingComp.this.getLocationOnScreen(), d);
        BufferedImage bufferedImage = rob.createScreenCapture(r);
        Iterator<Point> it = fillPoints.iterator();
        gr.setColor(Color.BLACK);
        gr.setStroke(new BasicStroke(1));
        while (it.hasNext()) {
            Point p = it.next();
            stack.push(p);
            startRGB = bufferedImage.getRGB((int) p.getX(), (int) p.getY());
            fillingAlgorithm(gr, bufferedImage);
            filledPt.clear();
        }

    }

    private void fillAreas2() {
        Robot rob = null;
        try {
            rob = new Robot();
        } catch (AWTException ex) {
            ex.printStackTrace();
        }
        Dimension d = new Dimension(DrawingComp.this.getWidth(), DrawingComp.this.getHeight());
        Rectangle r = new Rectangle(DrawingComp.this.getLocationOnScreen(), d);
        BufferedImage bufferedImage = rob.createScreenCapture(r);
        Iterator<Point> it = fillPoints.iterator();
        while (it.hasNext()) {
            Point p = it.next();
            stack.push(p);
            startRGB = bufferedImage.getRGB((int) p.getX(), (int) p.getY());
            fillingAlgorithm2(bufferedImage);
        }

    }

    private void fillingAlgorithm(Graphics2D gr, BufferedImage bufferedImage) {
        while (!stack.isEmpty()) {
            Point activePt = stack.pop();
            filledPt.add(activePt);
            gr.drawLine((int) activePt.getX(), (int) activePt.getY(),
                    (int) activePt.getX(), (int) activePt.getY());
            if (((int) activePt.getX() + 1) <= bufferedImage.getWidth())
                if ((bufferedImage.getRGB((int) activePt.getX() + 1, (int) activePt.getY()) == startRGB)
                        && !filledPt.contains(new Point((int) activePt.getX() + 1, (int) activePt.getY())))
                    stack.push(new Point((int) activePt.getX() + 1, (int) activePt.getY()));
            if (((int) activePt.getX() - 1) >= 0)
                if ((bufferedImage.getRGB((int) activePt.getX() - 1, (int) activePt.getY()) == startRGB)
                        && !filledPt.contains(new Point((int) activePt.getX() - 1, (int) activePt.getY())))
                    stack.push(new Point((int) activePt.getX() - 1, (int) activePt.getY()));
            if (((int) activePt.getY() + 1) <= bufferedImage.getHeight())
                if ((bufferedImage.getRGB((int) activePt.getX(), (int) activePt.getY() + 1) == startRGB)
                        && !filledPt.contains(new Point((int) activePt.getX(), (int) activePt.getY() + 1)))
                    stack.push(new Point((int) activePt.getX(), (int) activePt.getY() + 1));
            if (((int) activePt.getY() - 1) >= 0)
                if ((bufferedImage.getRGB((int) activePt.getX(), (int) activePt.getY() - 1) == startRGB)
                        && !filledPt.contains(new Point((int) activePt.getX(), (int) activePt.getY() - 1)))
                    stack.push(new Point((int) activePt.getX(), (int) activePt.getY() - 1));
        }
    }

    private void fillingAlgorithm2(BufferedImage bufferedImage) {
        while (!stack.isEmpty()) {
            Point activePt = stack.pop();
            filledPt.add(activePt);
            if (((int) activePt.getX() + 1) <= bufferedImage.getWidth())
                if ((bufferedImage.getRGB((int) activePt.getX() + 1, (int) activePt.getY()) == startRGB)
                        && !filledPt.contains(new Point((int) activePt.getX() + 1, (int) activePt.getY())))
                    stack.push(new Point((int) activePt.getX() + 1, (int) activePt.getY()));
            if (((int) activePt.getX() - 1) >= 0)
                if ((bufferedImage.getRGB((int) activePt.getX() - 1, (int) activePt.getY()) == startRGB)
                        && !filledPt.contains(new Point((int) activePt.getX() - 1, (int) activePt.getY())))
                    stack.push(new Point((int) activePt.getX() - 1, (int) activePt.getY()));
            if (((int) activePt.getY() + 1) <= bufferedImage.getHeight())
                if ((bufferedImage.getRGB((int) activePt.getX(), (int) activePt.getY() + 1) == startRGB)
                        && !filledPt.contains(new Point((int) activePt.getX(), (int) activePt.getY() + 1)))
                    stack.push(new Point((int) activePt.getX(), (int) activePt.getY() + 1));
            if (((int) activePt.getY() - 1) >= 0)
                if ((bufferedImage.getRGB((int) activePt.getX(), (int) activePt.getY() - 1) == startRGB)
                        && !filledPt.contains(new Point((int) activePt.getX(), (int) activePt.getY() - 1)))
                    stack.push(new Point((int) activePt.getX(), (int) activePt.getY() - 1));
        }
    }

    public void drawLines(Graphics2D gr) {
        Iterator<Point> it = points.iterator();
        gr.setColor(Color.BLACK);
        gr.setStroke(new BasicStroke(1));
        while (it.hasNext()) {
            Point p = it.next();
            if (it.hasNext()) {
                Point p1 = it.next();
                gr.drawLine((int) p.getX(), (int) p.getY(), (int) p1.getX(), (int) p1.getY());
            }
        }
    }

    public void drawAxes(Graphics2D gr) {
        gr.setStroke(new BasicStroke(2));
        gr.setColor(Color.GRAY);
        gr.drawLine(0, this.getHeight() / 2, this.getWidth(), this.getHeight() / 2);
        gr.drawLine(this.getWidth() / 2, 0, this.getWidth() / 2, this.getHeight());
        gr.setStroke(new BasicStroke(1));
        int buf1 = this.getHeight() / 2 + 10;
        int buf2 = this.getHeight() / 2 - 10;
        while (buf1 < this.getHeight()) {
            gr.drawLine(this.getWidth() / 2 - 3, buf1, this.getWidth() / 2 + 3, buf1);
            buf1 += 10;
            gr.drawLine(this.getWidth() / 2 - 3, buf2, this.getWidth() / 2 + 3, buf2);
            buf2 -= 10;
        }
        buf1 = this.getWidth() / 2 + 10;
        buf2 = this.getWidth() / 2 - 10;
        while (buf1 < this.getWidth()) {
            gr.drawLine(buf1, this.getHeight() / 2 - 3, buf1, this.getHeight() / 2 + 3);
            buf1 += 10;
            gr.drawLine(buf2, this.getHeight() / 2 - 3, buf2, this.getHeight() / 2 + 3);
            buf2 -= 10;
        }
    }

    public static final void makeScreenshot(JFrame argFrame) {
        Rectangle rec = argFrame.getBounds();
        BufferedImage bufferedImage = new BufferedImage(rec.width, rec.height,
                BufferedImage.TYPE_INT_ARGB);
        argFrame.paint(bufferedImage.getGraphics());
    }
}