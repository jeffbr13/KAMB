	import java.awt.Color;
	import java.awt.Graphics;
	import java.awt.image.BufferedImage;
	import java.util.logging.Level;
	import java.util.logging.Logger;
	import javax.swing.JFrame;

	public class Fun {

	    public static void main(String[] args) {
	        new Fun();
	    }

	    // attributes
	    JFrame window;
	    BufferedImage img;

		 // constructor
	    public Fun() {
	        img = new BufferedImage(1000, 200, BufferedImage.OPAQUE);
	        window = new JFrame() {
	            public void paint(Graphics g) {
	                g.drawImage(img, 0, 0, rootPane);
	            }
	        };

	        window.setSize(1000, 200);
	        window.setLocationRelativeTo(null);
	        window.setDefaultCloseOperation(window.EXIT_ON_CLOSE);
	        window.setVisible(true);
	        animate();
	    }

	    public void animate() {
	        Graphics g = img.getGraphics();
	        for (int i = 0; i < 640; i += 10) {
	            g.setColor(Color.BLACK);
	            g.fillRect(0, 0, 1000, 200);
	            g.setColor(Color.red);
	            g.fillOval(i, 100, 20, 20);
	            window.repaint();
	            try {
	                Thread.sleep(50);
	            } catch (InterruptedException ex) {
	                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
	            }
	        }
	    }
	}
