
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.lang.*;
import java.awt.Toolkit;

import java.awt.image.BufferedImage;
class Monstr extends JComponent {
  //координаты левого верхнего угла
  private  int x0;
  private  int y0;
   //размеры
  private  int w;
  private  int h;
  private  int WindowWidth = 780;
  private  int WindowHeight = 540;
  //файлы с картинками
  private  String name_l = "monstr_l.png";
  private  String name_r = "monstr_r.png";
  private  String name = name_l; 
  //шаг изменения координаты
  private  int h_coord;

  //конструктор
  public Monstr (int w, int h) {
     this.w = w; 
     this.h = h;
     x0 = w;
     y0 = h;
     h_coord = w;
  }

  //получить x
  public int getX () {
    return (x0);
  } 
  
  //получить y
  public int getY () {
    return (y0);
  } 

  //получить шаг изменения координаты
  public int getH_coord () {
    return (h_coord);
  } 
 
  public void setX (int x) {
    x0 = x;
  } 
  
  //
  public void setY (int y) {
    y0 = y;
  } 



  //рисуем монстра
  public void draw (Graphics g) {
    Graphics2D g2 = (Graphics2D)g;
    super.paintComponent (g2);
    Image monstr = Toolkit.getDefaultToolkit().getImage(name);
    g2.drawImage (monstr, x0, y0, w, h, this); 
    //System.out.println (x0 + " " + y0);
  }

  //движение монстра
  public void Move (boolean l, boolean r, boolean u, boolean d) {  
     if (l && x0 > 0)
        x0 = x0 - h_coord;
     else if (l && x0 == 0)
        x0 = WindowWidth;

     if (r && x0 < WindowWidth) 
        x0 = x0 + h_coord;
     else if (r && x0 == WindowWidth)
         x0 = 0;

     if (u && y0 > 0) {
         y0 = y0 - h_coord;         
     }
     else if (u && y0 == 0)
          y0 = WindowHeight;

     if (d && y0 < WindowHeight) {
         y0 = y0 + h_coord;
             
     }
     else if (d && y0 == WindowHeight)
          y0 = 0;

     if (r)
         name = name_r;
     else if (l)
         name = name_l;
    
  }

}