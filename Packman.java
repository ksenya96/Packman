
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.lang.*;
import java.awt.Toolkit;

public class Packman extends JComponent {
  private  int WindowWidth = 780;
  private  int WindowHeight = 540;
  private  int x0 = WindowWidth / 2;
  private  int y0 = WindowHeight / 2;
  private  int w;
  private  int h;
  private  int alpha = 30;
  private  int betta = 300;
  private  int Limit_alpha = 30;
  //шаг изменения угла
  private  int h_alpha = 30;
  private  int r = 10;
  private  double coeff_w = 0.5;
  private  double coeff_h = 0.25;
  private  boolean RightWasPressed = false;
  private  boolean LeftWasPressed = false;
  private  boolean UpWasPressed = false;
  private  boolean DownWasPressed = false;
  //шаг изменения координаты
  private  int h_coord;
  public  boolean Ex = false;
  public  boolean isDead = false;
  public  boolean newGame = false;



  public Packman (JFrame fr, int w, int h) {
     MyKeyListener key_listener = new MyKeyListener();
     fr.addKeyListener (key_listener);
     this.w = w;
     this.h = h;
     x0 = x0 - w / 2;
     y0 = y0 - h / 2;
     h_coord = w;
  }

    public void draw (Graphics g) {
     Graphics2D g2 = (Graphics2D)g;
     super.paintComponent (g2); 
     
     g2.setColor (Color.yellow);


	     //тело пакмана
     g2.fillArc (x0, y0, w, h, alpha, betta);
     BasicStroke pen = new BasicStroke(4);
     g2.setStroke(pen);
     g2.setColor (Color.black);
         //граница тела
     g2.drawArc (x0, y0, w, h, alpha, betta);
     g2.drawLine (x0 + w / 2, y0 + h / 2, (int)(x0 + w / 2 + w / 2 * Math.cos (alpha * Math.PI / 180)), (int)(y0 + h / 2 - h / 2 * Math.sin (alpha * Math.PI/180)));
     g2.drawLine (x0 + w / 2, y0 + h / 2, (int)(x0 + w / 2 + w / 2 * Math.cos ((alpha + betta) * Math.PI / 180)), (int)(y0 + h / 2 - h / 2 * Math.sin ((alpha + betta) * Math.PI/180)));
	     //глазик
     g2.setColor (Color.black);
     g2.fillOval (x0 + (int)(coeff_w * w) , y0 + (int)(coeff_h * h), r, r);
    

  }

   
  public int getX () {
    return (x0);
  } 
  
  public int getY () {
    return (y0);
  } 

  public int getH_coord () {
    return (h_coord);
  } 

  public void setX (int x) {
    x0 = x;;
  } 
  
  public void setY (int y) {
    y0 = y;
  } 


  public void Move (boolean left, boolean right, boolean up, boolean down) { 

     
     //подключаем слушатель клавиатуры
     //MyKeyListener key_listener = new MyKeyListener();

     alpha = alpha - h_alpha;
     //System.out.println (alpha);
     betta = betta + 2 * h_alpha;
     if (LeftWasPressed && x0 > 0 && left) {
         x0 = x0 - h_coord;
     }
     /*else if (LeftWasPressed && x0 == 0 && left)
        x0 = WindowWidth; */
 
     if (RightWasPressed && x0 + w < WindowWidth && right) {
         x0 = x0 + h_coord;
     }
     /*else if (RightWasPressed && x0 == WindowWidth && right)
          x0 = 0;*/
                  

     if (UpWasPressed && y0 > 0 && up) {
         y0 = y0 - h_coord;         
     }
     /*else if (UpWasPressed && y0 == 0 && up)
          y0 = WindowHeight;*/
     if (DownWasPressed && y0 + w < WindowHeight && down) {
         y0 = y0 + h_coord;
             
     }
     /*else if (DownWasPressed && y0 == WindowHeight && down)
          y0 = 0;*/
              
       //System.out.println (alpha);       
     if (alpha >= Limit_alpha || alpha <= Limit_alpha - 30)
           h_alpha = -h_alpha;        
     
  }
  //слушатель клавиатуры
  private static class MyKeyListener implements KeyListener {
    public void keyPressed (KeyEvent ke) {
      int key = ke.getKeyCode ();
      switch (key) {
        case KeyEvent.VK_LEFT:
            if (!LeftWasPressed) {
            	LeftWasPressed = true;
            	RightWasPressed = false;
            	UpWasPressed = false;
            	DownWasPressed = false;
            	alpha  = 210;
            	betta = 300;
            	Limit_alpha = alpha; 
            	h_alpha = 30; 
            	coeff_w = 0.5; 
            	coeff_h = 0.25;       
            }  
            break;
        case KeyEvent.VK_RIGHT:
            if (!RightWasPressed) {
	            LeftWasPressed = false;
	            RightWasPressed = true;
	            UpWasPressed = false;
	            DownWasPressed = false; 
	            alpha = 30;
	            betta = 300;
	            Limit_alpha = alpha;   
	            h_alpha = 30;      
	            coeff_w = 0.5; 
	            coeff_h = 0.25;        
	    } 
            break;  
        case KeyEvent.VK_UP:
            if (!UpWasPressed) {
	            LeftWasPressed = false;
	            RightWasPressed = false;
	            UpWasPressed = true;
	            DownWasPressed = false;
	            alpha = 120;
	            betta = 300;
	            Limit_alpha = alpha;
	            h_alpha = 30;          
	            coeff_w = 0.25; 
	            coeff_h = 0.5;     
            }    
            break; 
        case KeyEvent.VK_DOWN:
            if (!DownWasPressed) {
	            LeftWasPressed = false;
	            RightWasPressed = false;
	            UpWasPressed = false;
	            DownWasPressed = true; 
	            alpha = 300;
	            betta = 300;    
	            Limit_alpha = alpha;
	            h_alpha = 30;
	            coeff_w = 0.75; 
	            coeff_h = 0.5;        
            }          
            break;  
        case KeyEvent.VK_ESCAPE: 
		    if (!(isDead))
              Ex = true; 
            break; 
		case KeyEvent.VK_ENTER: 
            if (isDead) {
			  newGame = true;
			}
            break;
      }
           
    }  
    public void keyReleased (KeyEvent ke) {}
    public void keyTyped (KeyEvent ke) {}
  }

}