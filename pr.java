import java.awt.*;
import javax.swing.*; 
import java.util.*;
import java.awt.Toolkit;
class pr extends JComponent{
  private  static int w = 60;
  private  static int free  = -1;
  private  static int r = 14;
  private static int[][] Matr = {
     {-2, -2, -2, -2, -2, -2, -1, -2, -2, -2, -2, -2, -2, -1, -1},
     {-2, -3, -1, -1, -1, -1, -1, -1, -1, -1, -1, -3, -2, -1, -1},
     {-1, -1, -1, -2, -1, -2, -2, -2, -1, -2, -1, -1, -1, -1, -1},
     {-2, -1, -2, -2, -1, -1, -2, -1, -1, -2, -2, -1, -2, -1, -1}, 
     {-2, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -2, -1, -1},
     {-2, -1, -2, -2, -1, -1, -2, -1, -1, -2, -2, -1, -2, -1, -1}, 
     {-1, -1, -1, -2, -1, -2, -2, -2, -1, -2, -1, -1, -1, -1, -1}, 
     {-2, -3, -1, -1, -1, -1, -1, -1, -1, -1, -1, -3, -2, -1, -1},
     {-2, -2, -2, -2, -2, -2, -1, -2, -2, -2, -2, -2, -2, -1, -1}, 
     {-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1},
     {-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1} 
  };

  public static void Show (Graphics g) {
     int x = 0, y = 0;
     for (int i = 0; i < 9; i++) {
        for (int j = 0; j < 13; j++) {
            if (Matr[i][j] == -2)
               draw (g, x, y);
            g.setColor (Color.black);
            if (Matr[i][j] == free)
               g.fillOval (x + w / 2 - r / 2, y + w / 2 - r / 2, r, r);
	        if (Matr[i][j] == -3) {
               Image cherry = Toolkit.getDefaultToolkit().getImage("cherry.png");
               g.drawImage (cherry, x, y, w, w, null);
	        }
            x += w;
        }
        x = 0;
        y += w;
     }
   }

   public static void draw (Graphics g, int x0, int y0) {
     Graphics2D g2 = (Graphics2D)g;
     //super.paintComponent (g2);
     BasicStroke pen = new BasicStroke(4);
     g2.setStroke(pen);
     Color c = new Color (135, 206, 250);
     g2.setColor (c);
     g2.fillRect (x0, y0, w, w);
     c = new Color (70, 130, 180);
     g2.setColor (c);     
     g2.drawLine (x0 + w, y0 + w, x0, y0 + w);
     g2.drawLine (x0 + w, y0 + w, x0 + w, y0);
     c = new Color (240, 255, 255);
     g2.setColor (c);
     g2.drawLine (x0, y0, x0 + w, y0);
     g2.drawLine (x0, y0, x0, y0 + w);
  }

  public static void main(String[] args) {
      JFrame frame = new JFrame ("Packman");
      frame.setPreferredSize (new Dimension (780 + 15, 540 + 40));
     frame.pack();
     frame.setResizable (false);   
     frame.setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);

	 
     JPanel panel = new JPanel () {
        @Override
        public void paintComponent (Graphics g) {
           super.paintComponent (g);
           Image background = Toolkit.getDefaultToolkit().getImage("background.jpg");
           g.drawImage (background, 0, 0, 780 + 15, 540 + 40 + 50, this);
           Show(g);   
        }
     };
     frame.add (panel);

     frame.setVisible (true);
     frame.repaint ();

  }


}