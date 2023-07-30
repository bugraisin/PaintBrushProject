import javax.swing.*;
import javax.swing.event.MouseInputListener;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.util.ArrayList;

public class PaintBrush extends JFrame {
    public static void main(String[] args) {
        new PaintBrush();
    }
    public Shape shape;
    public Color col;
    public ArrayList<Shape> shapes= new ArrayList<>();
    public ArrayList<String> whichShape= new ArrayList<>();
    public ArrayList<Color> colors= new  ArrayList<>();

    JPanel topPanel,mainPanel;
    JPanel colorPanel, buttonPanel;
    JPanel blue,red,green,yellow,orange,magenta, black;
    String currentShape="";
    Color color= Color.BLACK;
    PaintBrush() {
        super("PaintBrush");
        setLayout(new BorderLayout());
        topPanel= new JPanel(new GridLayout(2,1));
        add(topPanel, BorderLayout.NORTH);
        colorPanel= new JPanel(new FlowLayout());
        JPanel helperPanel =new JPanel(new BorderLayout());
        buttonPanel= new JPanel(new FlowLayout());
        topPanel.add(colorPanel);
        topPanel.add(helperPanel);
        helperPanel.add(buttonPanel, BorderLayout.CENTER);
        JPanel blueLine= new JPanel();
        helperPanel.add(blueLine, BorderLayout.SOUTH);
        blueLine.setBackground(Color.BLUE);
        mainPanel= new JPanel();
        add(mainPanel, BorderLayout.CENTER);

        colorPanel.add(new ColorArea());
        buttonPanel.add(new ButtonArea());
        mainPanel.setLayout(new BorderLayout());
        mainPanel.add(new Draw());
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(1400,800);
        setVisible(true);
    }
    class Draw extends JPanel implements MouseInputListener, MouseMotionListener {
        public int x1,x2;
        public int y1,y2;
        public int oldX, oldY;
        public String deliverType;
        public Color deliverColor;
        public Shape deliverShape;
        public boolean drawing;
        public boolean inside= false;

        public Draw() {
            addMouseListener(this);
            addMouseMotionListener(this);
        }
        @Override
        public void mouseClicked(MouseEvent e) {

        }
        @Override
        public void mouseEntered(MouseEvent e) {

        }
        @Override
        public void mouseExited(MouseEvent e) {

        }
        @Override
        public void mouseMoved(MouseEvent e) {

        }
        @Override
        public void mousePressed(MouseEvent e) {
            x1= e.getX();
            y1= e.getY();
            oldX = e.getX();
            oldY = e.getY();
            drawing = true;
            if(currentShape.equals("Taşı")) {
                Shape tmp;
                for(int i=shapes.size()-1; i>=0; i--) {
                    tmp= shapes.get(i);
                    Point point= e.getPoint();
                    if(tmp.contains(point)) {
                        inside= true;
                        deliverShape= tmp;
                        deliverColor= colors.get(i);
                        deliverType= whichShape.get(i);
                        shapes.remove(i);
                        colors.remove(i);
                        whichShape.remove(i);
                        break;
                    }
                }
            }
        }

        public void printOnShape(Graphics g) {
            ArrayList<Shape> topShape = new ArrayList<>();
            ArrayList<Color> topColor= new ArrayList<>();
            ArrayList<String> topName= new ArrayList<>();
            if (currentShape.equals("Dikdörtgen Çiz")) {
                if (x1 > x2 && y2 > y1) {
                    topShape.add(new Rectangle(x2, y1, x1 - x2, y2 - y1));
                    topColor.add(color);
                    topName.add("Rectangle");
                } else if (x2 > x1 && y1 > y2) {
                    topShape.add(new Rectangle(x1, y2, x2 - x1, y1 - y2));
                    topColor.add(color);
                    topName.add("Rectangle");
                } else if (x1 > x2 && y1 > y2) {
                    topShape.add(new Rectangle(x2, y2, x1 - x2, y1 - y2));
                    topColor.add(color);
                    topName.add("Rectangle");
                } else if (x2 > x1 && y2 > y1) {
                    topShape.add(new Rectangle(x1, y1, x2 - x1, y2 - y1));
                    topColor.add(color);
                    topName.add("Rectangle");
                }
            } else if (currentShape.equals("Oval Çiz")) {
                if (x1 > x2 && y2 > y1) {
                    topShape.add(new Ellipse2D.Double(x2, y1, x1 - x2, y2 - y1));
                    topColor.add(color);
                    topName.add("Ellipse");
                } else if (x2 > x1 && y1 > y2) {
                    topShape.add(new Ellipse2D.Double(x1, y2, x2 - x1, y1 - y2));
                    topColor.add(color);
                    topName.add("Ellipse");
                } else if (x1 > x2 && y1 > y2) {
                    topShape.add(new Ellipse2D.Double(x2, y2, x1 - x2, y1 - y2));
                    topColor.add(color);
                    topName.add("Ellipse");
                } else if (x2 > x1 && y2 > y1) {
                    topShape.add(new Ellipse2D.Double(x1, y1, x2 - x1, y2 - y1));
                    topColor.add(color);
                    topName.add("Ellipse");
                }
            }
            for(int i=0; i<topShape.size(); i++) {
                shape= topShape.get(i);
                col= topColor.get(i);
                g.setColor(col);
                if(topName.get(i).equals("Rectangle"))
                    g.fillRect(shape.getBounds().x, shape.getBounds().y, shape.getBounds().width, shape.getBounds().height);
                else
                    g.fillOval(shape.getBounds().x, shape.getBounds().y, shape.getBounds().width, shape.getBounds().height);
            }
            topColor.clear();
            topShape.clear();
            topName.clear();
        }
        public void printElements(Graphics g) {
            for(int i=0; i<shapes.size(); i++) {
                shape= shapes.get(i);
                col= colors.get(i);
                g.setColor(col);
                if(whichShape.get(i).equals("Rectangle"))
                    g.fillRect(shape.getBounds().x, shape.getBounds().y, shape.getBounds().width, shape.getBounds().height);
                else if(whichShape.get(i).equals("Ellipse"))
                    g.fillOval(shape.getBounds().x, shape.getBounds().y, shape.getBounds().width, shape.getBounds().height);
                else if(whichShape.get(i).equals("Draw")) {
                    Line2D line2D= (Line2D) shape;
                    g.drawLine((int)line2D.getX1(),(int)line2D.getY1(),(int)line2D.getX2(),(int)line2D.getY2());
                }

            }
        }
        public void drawRectangle(Graphics g) {
            if (x1 > x2 && y2 > y1) {
                super.paint(g);
                // sağ üste çekilirse
                g.setColor(color);
                g.fillRect(x2, y1, x1 - x2, y2 - y1);
            } else if (x2 > x1 && y1 > y2) {
                // sol alta çekilirse
                super.paint(g);
                g.setColor(color);
                g.fillRect(x1, y2, x2 - x1, y1 - y2);
            } else if (x1 > x2 && y1 > y2) {
                //sol üste çekilirse
                super.paint(g);
                g.setColor(color);
                g.fillRect(x2, y2, x1 - x2, y1 - y2);
            } else if (x2 > x1 && y2 > y1) {
                // sağ alta çekilirse
                super.paint(g);
                g.setColor(color);
                g.fillRect(x1, y1, x2 - x1, y2 - y1);
            }
            printElements(g);
        }
        public void drawEllipse(Graphics g) {
            if (x1 > x2 && y2 > y1) {
                // sağ üste çekilirse
                super.paint(g);
                g.setColor(color);
                g.fillOval(x2, y1, x1 - x2, y2 - y1);
            } else if (x2 > x1 && y1 > y2) {
                // sol alta çekilirse
                super.paint(g);
                g.setColor(color);
                g.fillOval(x1, y2, x2 - x1, y1 - y2);
            } else if (x1 > x2 && y1 > y2) {
                //sol üste çekilirse
                super.paint(g);
                g.setColor(color);
                g.fillOval(x2, y2, x1 - x2, y1 - y2);
            } else if (x2 > x1 && y2 > y1) {
                // sağ alta çekilirse
                super.paint(g);
                g.setColor(color);
                g.fillOval(x1, y1, x2 - x1, y2 - y1);
            }
            printElements(g);
        }

        @Override
        public void mouseDragged(MouseEvent e) {
            Graphics g = getGraphics();
            if(drawing) {
                x2 = e.getX();
                y2 = e.getY();
                switch (currentShape) {
                    case "Dikdörtgen Çiz" -> drawRectangle(g);
                    case "Oval Çiz" -> drawEllipse(g);
                    case "Kalemle Çiz" -> {
                        g.setColor(color);
                        x1 = e.getX();
                        y1 = e.getY();
                        g.drawLine(oldX, oldY, x1, y1);
                        shapes.add(new Line2D.Double(oldX,oldY,x1,y1));
                        colors.add(color);
                        whichShape.add("Draw");
                        oldX = x1;
                        oldY = y1;
                        drawing = true;
                    }
                    case "Taşı" -> {
                        if(inside) {
                            super.paint(g);
                            g.setColor(deliverColor);
                            if(deliverType.equals("Rectangle")) {
                                g.fillRect(e.getX(),e.getY(),(int)deliverShape.getBounds().getWidth(),(int)deliverShape.getBounds().getHeight());
                            } else if(deliverType.equals("Ellipse")) {
                                g.fillOval(e.getX(),e.getY(),(int)deliverShape.getBounds().getWidth(),(int)deliverShape.getBounds().getHeight());
                            }
                            printElements(g);
                        }
                    }
                }
                printOnShape(g);
            }
        }
        @Override
        public void mouseReleased(MouseEvent e) {
            drawing = false;
            Graphics g = getGraphics();
            if (currentShape.equals("Dikdörtgen Çiz")) {
                if (x1 > x2 && y2 > y1) {
                    shapes.add(new Rectangle(x2, y1, x1 - x2, y2 - y1));
                    colors.add(color);
                    whichShape.add("Rectangle");
                } else if (x2 > x1 && y1 > y2) {
                    shapes.add(new Rectangle(x1, y2, x2 - x1, y1 - y2));
                    colors.add(color);
                    whichShape.add("Rectangle");
                } else if (x1 > x2 && y1 > y2) {
                    shapes.add(new Rectangle(x2, y2, x1 - x2, y1 - y2));
                    colors.add(color);
                    whichShape.add("Rectangle");
                } else if (x2 > x1 && y2 > y1) {
                    shapes.add(new Rectangle(x1, y1, x2 - x1, y2 - y1));
                    colors.add(color);
                    whichShape.add("Rectangle");
                }
                printElements(g);
            }
            else if(currentShape.equals("Oval Çiz")) {
                if (x1 > x2 && y2 > y1) {
                    shapes.add(new Ellipse2D.Double(x2, y1, x1 - x2, y2 - y1));
                    colors.add(color);
                    whichShape.add("Ellipse");
                } else if (x2 > x1 && y1 > y2) {
                    shapes.add(new Ellipse2D.Double(x1, y2, x2 - x1, y1 - y2));
                    colors.add(color);
                    whichShape.add("Ellipse");
                } else if (x1 > x2 && y1 > y2) {
                    shapes.add(new Ellipse2D.Double(x2, y2, x1 - x2, y1 - y2));
                    colors.add(color);
                    whichShape.add("Ellipse");
                } else if (x2 > x1 && y2 > y1) {
                    shapes.add(new Ellipse2D.Double(x1, y1, x2 - x1, y2 - y1));
                    colors.add(color);
                    whichShape.add("Ellipse");
                }
                printElements(g);
            }

            else if(currentShape.equals("Taşı"))  {
                if(inside) {
                    super.paint(g);
                    if (deliverType.equals("Rectangle")) {
                        shapes.add(new Rectangle(e.getX(), e.getY(), (int) deliverShape.getBounds().getWidth(), (int) deliverShape.getBounds().getHeight()));
                        colors.add(deliverColor);
                        whichShape.add(deliverType);
                    } else if (deliverType.equals("Ellipse")) {
                        shapes.add(new Ellipse2D.Double(e.getX(), e.getY(), (int) deliverShape.getBounds().getWidth(), (int) deliverShape.getBounds().getHeight()));
                        colors.add(deliverColor);
                        whichShape.add(deliverType);
                    }
                    printElements(g);
                    inside = false;
                }
            }

        }

    }
    class ColorArea extends JPanel implements MouseInputListener {
        public ColorArea() {
            blue= new JPanel();
            blue.setBackground(Color.BLUE);
            blue.setPreferredSize(new Dimension(120,50));
            red= new JPanel();
            red.setBackground(Color.RED);
            red.setPreferredSize(new Dimension(120,50));
            green= new JPanel();
            green.setBackground(Color.GREEN);
            green.setPreferredSize(new Dimension(120,50));
            yellow= new JPanel();
            yellow.setBackground(Color.YELLOW);
            yellow.setPreferredSize(new Dimension(120,50));
            orange= new JPanel();
            orange.setBackground(Color.ORANGE);
            orange.setPreferredSize(new Dimension(120,50));
            magenta= new JPanel();
            magenta.setBackground(Color.MAGENTA);
            magenta.setPreferredSize(new Dimension(120,50));
            black= new JPanel();
            black.setBackground(Color.BLACK);
            black.setPreferredSize(new Dimension(120,50));
            add(blue);
            add(red);
            add(green);
            add(yellow);
            add(orange);
            add(magenta);
            add(black);
            blue.addMouseListener(this);
            red.addMouseListener(this);
            green.addMouseListener(this);
            yellow.addMouseListener(this);
            orange.addMouseListener(this);
            magenta.addMouseListener(this);
            black.addMouseListener(this);
        }
        public void setColor(Color c) {
            color= c;
        }

        @Override
        public void mouseClicked(MouseEvent e) {
            if(e.getSource().equals(blue))
                setColor(Color.BLUE);
            else if(e.getSource().equals(red))
                setColor(Color.RED);
            else if(e.getSource().equals(green))
                setColor(Color.GREEN);
            else if(e.getSource().equals(yellow))
                setColor(Color.YELLOW);
            else if(e.getSource().equals(orange))
                setColor(Color.ORANGE);
            else if(e.getSource().equals(magenta))
                setColor(Color.MAGENTA);
            else if(e.getSource().equals(black))
                setColor(Color.BLACK);
        }

        @Override
        public void mousePressed(MouseEvent e) {

        }

        @Override
        public void mouseReleased(MouseEvent e) {

        }

        @Override
        public void mouseEntered(MouseEvent e) {

        }

        @Override
        public void mouseExited(MouseEvent e) {

        }

        @Override
        public void mouseDragged(MouseEvent e) {

        }

        @Override
        public void mouseMoved(MouseEvent e) {

        }
    }
    class ButtonArea extends JPanel implements ActionListener {
        public ButtonArea() {
            JButton b1 = new JButton("Dikdörtgen Çiz");
            JButton b2 = new JButton("Oval Çiz");
            JButton b3 = new JButton("Kalemle Çiz");
            JButton b4 = new JButton("Taşı");
            b1.addActionListener(this);
            b2.addActionListener(this);
            b3.addActionListener(this);
            b4.addActionListener(this);
            b1.setPreferredSize(new Dimension(200,75));
            b2.setPreferredSize(new Dimension(200,75));
            b3.setPreferredSize(new Dimension(200,75));
            b4.setPreferredSize(new Dimension(200,75));
            add(b1);
            add(b2);
            add(b3);
            add(b4);
        }
        @Override
        public void actionPerformed(ActionEvent e) {
            currentShape= e.getActionCommand();
        }
    }
}
