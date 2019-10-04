

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.lang.*;
import java.awt.Toolkit;

public class Game extends JComponent{

  static int size = 60;
  static int WindowWidth = 780 + 15;
  static int WindowHeight = 540 + 40;
  static JFrame frame = new JFrame ("Packman");
  static Labyrinth labyrinth;

 

  public static void main (String[] args) {
    
     frame.setPreferredSize (new Dimension (WindowWidth, WindowHeight + 100));
     frame.pack();
     frame.setResizable (false);   
     frame.setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);
     labyrinth = new Labyrinth (frame, size);
	 
     JPanel panel = new JPanel () {
        @Override
        public void paintComponent (Graphics g) {
           super.paintComponent (g);
           Image background = Toolkit.getDefaultToolkit().getImage("background.jpg");
           g.drawImage (background, 0, 0, WindowWidth, WindowHeight + 50, this);
           labyrinth.MoveInLabyrinth (g);   
        }
     };
     frame.add (panel);

     frame.setVisible (true);
     frame.repaint ();
	 
	 //JButton button = new JButton ("Continue");
     while (true) {
	    try {
		     if (labyrinth.ex) {
			   break;
			 }
             if (labyrinth.dead || labyrinth.next_level) 
               Thread.sleep (2000);
             else
               Thread.sleep (200);
		     frame.repaint ();
		   
       }
       catch (InterruptedException e) {
          System.out.println ("Исключение");
       }	   
	 }
     

     //panel.setBackground (background);
     
 
    

  }



  
  

  
  
 }
