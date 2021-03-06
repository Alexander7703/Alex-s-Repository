 


import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;
import java.util.Timer;
import java.util.TimerTask;

/**
 * @author p6majo
 * @version 2019-10-28
 */
public abstract class CodingTrainGui {
    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        createUIComponents();
        mainPanel = new JPanel();
        mainPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc;
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        mainPanel.add(canvas, gbc);
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return mainPanel;
    }

    /*
     **********************
     ***   private classes   ***
     **********************
     */

    private class Keychecker extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent event) {
            char ch = event.getKeyChar();
            keyAction(event.getKeyChar());
        }
    }


    /*
     **********************
     ***   attributes   ***
     **********************
     */


    protected Canvas canvas;
    protected int width;
    protected int height;

    private int xmin, xmax;
    private int ymin, ymax;


    private JPanel mainPanel;
    private JFrame frame;
    private Timer timer;



    /*
     *********************
     ***  constructors ***
     *********************
     */

    /**
     * Create a CodingTrainGui that consists of a Canvas, where graphical objects can be drawn.
     * The parameters define the width and the height of the canvas and the frameRate determines, how often
     * the method draw() is called within a second. A framerate of 0 causes the method draw() to be called only once.
     *
     * @param width     with of the canvas
     * @param height    height of the canvas
     * @param frameRate frequency with which the draw() method is called.
     */
    public CodingTrainGui(int width, int height, int frameRate) {

        this.width = width;
        this.height = height;
        this.xmin = 0;
        this.xmax = width;
        this.ymin = 0;
        this.ymax = height;

        frame = new JFrame();
        $$$setupUI$$$();
        frame.setPreferredSize(new Dimension(width, height));
        frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        frame.setResizable(true);
        frame.setContentPane(mainPanel);
        frame.pack();
        frame.setVisible(true);
        frame.addKeyListener(new Keychecker());

        this.setup();


        //start timer that repeatedly calls the this.draw method
        if (frameRate > 0) {
            timer = new Timer();
            timer.scheduleAtFixedRate(new TimerTask() {
                @Override
                public void run() {
                    draw();
                    canvas.update();
                }
            }, 0, 1000 / frameRate);
        }
    }

    /*
     ***********************
     ****       getter   ***
     ***********************
     */


    /*
     ************************
     ****       setter    ***
     ************************
     */


    /*
     *****************************
     ***     public methods    ***
     *****************************
     */

    public void translate(double x, double y) {
        xmin -= x;
        xmax -= x;
        ymin -= y;
        ymax -= y;
        canvas.setXscale(xmin, xmax);
        canvas.setYscale(ymin, ymax);

    }

    /**
     * The setup function is called earlier, before the constructor and the attributes of the subclass are performed and initialized
     */
    public abstract void setup();

    public abstract void draw();

    /**
     * This method is called, once a key is pressed by the user
     * It can be overriden to implement some basic keyboard interaction
     *
     * @param keyChar
     */
    public void keyAction(char keyChar) {

    }


    public byte[] getPixelData(BufferedImage image) {
        return ((DataBufferByte) image.getRaster().getDataBuffer()).getData();
    }

    public static BufferedImage loadBufferedImage(String filename) {
        return toBufferedImage(loadImage(filename));
    }


    public static BufferedImage loadImage(URL url) {
        try {

            SocketAddress addr = new
                    InetSocketAddress("192.168.47.253", 3141);
            Proxy proxy = new Proxy(Proxy.Type.HTTP, addr);

            URLConnection conn = url.openConnection(proxy);
            //make sure proxy settings are included during build
            return ImageIO.read(conn.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static BufferedReader loadString(URL url) {
        try {

            SocketAddress addr = new
                    InetSocketAddress("192.168.47.253", 3141);
            Proxy proxy = new Proxy(Proxy.Type.HTTP, addr);

            URLConnection conn = url.openConnection(proxy);
            //make sure proxy settings are included during build
            return new BufferedReader(new InputStreamReader(conn.getInputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Image loadImage(String filename) {
        Image img = null;

        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        URL url = loader.getResource("codingchallenge/" + filename);
        if (url != null) {
            try {
                img = ImageIO.read(url);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


        return img;
    }

    /**
     * Converts a given Image into a BufferedImage
     *
     * @param img The Image to be converted
     * @return The converted BufferedImage
     */
    public static BufferedImage toBufferedImage(Image img) {
        if (img instanceof BufferedImage) {
            return (BufferedImage) img;
        }

        // Create a buffered image with transparency
        BufferedImage bimage = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_ARGB);

        // Draw the image on to the buffered image
        Graphics2D bGr = bimage.createGraphics();
        bGr.drawImage(img, 0, 0, null);
        bGr.dispose();

        // Return the buffered image
        return bimage;
    }

    /**
     * Linear mapping from one range into another one
     *
     * @param x
     * @param xmin
     * @param xmax
     * @param min
     * @param max
     * @return
     */
    public double map(double x, double xmin, double xmax, double min, double max) {
        return min + (x - xmin) / (xmax - xmin) * (max - min);
    }

    /**
     * Connect an array of points to draw a curve
     *
     * @param shape
     */
    public void drawShape(java.util.List<Point> shape) {
        for (int i = 0; i < shape.size() - 1; i++) {
            Point a = shape.get(i);
            Point b = shape.get(i + 1);
            canvas.line(a.x, a.y, b.x, b.y);
        }
    }

    public void noLoop() {
        this.timer.cancel();
    }

    /*
     ******************************
     ****     private methods   ***
     ******************************
     */


    /*
     ******************************
     ****     overrides         ***
     ******************************
     */

    /*
     ******************************
     ****     toString()        ***
     ******************************
     */

    @Override
    public String toString() {
        return super.toString();
    }

    private void createUIComponents() {
        canvas = new Canvas();
        canvas.enableDoubleBuffering();
        canvas.setCanvasSize(width, height);
        canvas.setXscale(xmin, xmax);
        canvas.setYscale(ymin, ymax);
        canvas.setPenColor(Color.BLACK);
    }


}
