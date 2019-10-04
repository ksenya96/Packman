

import java.awt.*;
import javax.swing.*; 
import java.util.*;
import java.awt.Toolkit;

public class Labyrinth extends JComponent{
  //матрица лабиринта
  private int[][] Matr = {
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
  
  private int[][] CopyMatr = new int[11][15];
  //персонажи
  private Packman packman;
  private Monstr[] monstr = new Monstr[4];
  //сторона квадрата
  private  int w;
  //логические переменные, показывающие возможно ли движение по направлениям 
  private  boolean LIsExist = true;
  private  boolean RIsExist = true;
  private  boolean UIsExist = true;
  private  boolean DIsExist = true;
  private  int r = 14;
  private  int WindowWidth = 780;
  private  int WindowHeight = 540;
  private  int free = -1;
  private  boolean[] MotionIsExist = new boolean[4];
  private  int score = 0;
  private  int max_score = 79; 
  public  int lifes = 4;
  private  int stage = 0;
  public  boolean dead = false;
  public boolean next_level = false;
  public boolean ex = false;


  //конструктор
  public Labyrinth (JFrame fr, int w) {
     this.w = w;
     packman = new Packman (fr, w, w);
     for (int i = 0; i <= stage; i++) { 
        monstr[i] = new Monstr (w, w);
        //System.out.println (monstr[i].getByte());
        MotionIsExist[i] = true;
     }
    SetMonstrCoord ();
    for (int i = 0; i < 11; i++)
      for (int j = 0; j < 15; j++)
        CopyMatr[i][j] = Matr[i][j];
     
  }

  //рисуем квадратик
  public void draw (Graphics g, int x0, int y0) {
     Graphics2D g2 = (Graphics2D)g;
     super.paintComponent (g2);
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

  //рисуем лабиринт
  public void Show (Graphics g) {
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
               g.drawImage (cherry, x, y, w, w, this);
	        }
            x += w;
        }
        x = 0;
        y += w;
     }

	 //g.setColor (new Color (255, 255, 0));
	 //g.fillRoundRect (0, WindowHeight + 5, WindowWidth, w, 20, 15);
     g.setColor (Color.black);
     g.drawRoundRect (0, WindowHeight + 5, WindowWidth, w - 5, 20, 15);
	 
     g.setColor (new Color (0, 0, 255));
     Font f = new Font ("Comic Sans MS", Font.BOLD, w - 10);
     g.setFont (f);
	 
	 //отображаем счет
     g.drawString ("SCORE " + score, 0, WindowHeight + f.getSize());
	 //жизни
     g.drawString ("" + lifes, WindowWidth / 2 - w / 2, WindowHeight + f.getSize());
	 //уровень
     g.drawString ("STAGE " + (stage + 1), WindowWidth / 2  + 2 * w, WindowHeight + f.getSize());
	 f = new Font ("Arial", Font.BOLD, w - 25);
     g.setFont (f);
	 if (!(packman.isDead))
	   g.drawString ("Press ESC to Exit", WindowWidth / 2 - w * 3, WindowHeight + w + f.getSize());
  }

  //установить начальные координаты монстра
  private void SetMonstrCoord () {
      for (int i = 0; i <= stage; i++) {
            switch (i) {
               case 0: {
                 monstr[i].setX (w);
                 monstr[i].setY (w);
                 break;
               }
	       case 1: {
                 monstr[i].setX (WindowWidth - 2 * w);
                 monstr[i].setY (w);
                 break;
               }
	       case 2: {
                 monstr[i].setX (w);
                 monstr[i].setY (WindowHeight - 2 * w);
                 break;
               }
 	       case 3: {
                 monstr[i].setX (WindowWidth - 2 * w);
                 monstr[i].setY (WindowHeight - 2 * w);
                 break;
               }
            }   
          }

  }

  //движение объектов в лабиринте
  public void MoveInLabyrinth (Graphics g) {
     Show (g);
	 //если нажата кнопка выхода
     if (packman.Ex && !(dead)) {
         Show (g);

	 g.setColor (Color.red);
         Font f = new Font ("Comic Sans MS", Font.BOLD, w + 20);
         g.setFont (f);

	   	 g.drawString ("OK! Score: " + (score * (stage + 1)), WindowWidth / 2 - w * 4, WindowHeight / 2 - w / 2);	
		 /*try {
			 Thread.sleep (3000);
		 }
		 catch (InterruptedException e) {
			 System.out.println ("Исключение");
	     }*/
	     ex = true;
     
	 }
	 
	 //если переход на следующий уровень
     if (next_level) {
         next_level = true;
		 stage++;
		 //добавляем нового монстра
		 monstr[stage] = new Monstr (w, w);
         g.setColor (Color.red);
         Font f = new Font ("Comic Sans MS", Font.BOLD, w - 10);
         g.setFont (f);
		 //если пройдены все уровни, то выходим из игры
		 if (stage == 4) {
		     g.drawString ("OK! Score: " + (score * (stage + 1)), WindowWidth / 2 - w * 4, WindowHeight / 2 - w / 2);	
			 try {
			    Thread.sleep (3000);
			 }
			 catch (InterruptedException e) {
			    System.out.println ("Исключение");
			 }
			 System.exit(0);
         }
         g.drawString ("Next level!. Score: " + (score * (stage + 1)), WindowWidth / 2 - w * 4, WindowHeight / 2 - w / 2);	
         //устанавливаем начальные данные
		 for (int i = 0; i < 11; i++)
		   for (int j = 0; j < 15; j++)
		     Matr[i][j] = CopyMatr[i][j];
         SetMonstrCoord ();
         packman.setX (WindowWidth / 2 - w / 2);
         packman.setY (WindowHeight / 2 - w / 2);
         lifes = 4;
         next_level = false;
		 
     }
     else {

	     //пакман
	     //если пакман пойман, то приостановить 
	     if (dead) {
		   //если не осталось жизней, то ожидаем нажатия клавиши Еnter
	       if (lifes == 0) {
	          g.setColor (Color.red);
	          Font f = new Font ("Comic Sans MS", Font.BOLD, w - 10);
	          g.setFont (f);
	          g.drawString ("Game over. Score: " + (score * (stage + 1)), WindowWidth / 2 - w * 4, WindowHeight / 2 - w / 2);	
                  f = new Font ("Arial", Font.BOLD, w - 25);
                  g.setFont (f);
			  g.setColor (Color.blue);
			  g.drawString ("Press ENTER to begin new game", WindowWidth / 2 - w * 4, WindowHeight + w + f.getSize());
			  if (packman.newGame) {
			    stage = 0;
				for (int i = 0; i < 11; i++)
		           for (int j = 0; j < 15; j++)
		              Matr[i][j] = CopyMatr[i][j];
				SetMonstrCoord ();
				packman.setX (WindowWidth / 2 - w / 2);
				packman.setY (WindowHeight / 2 - w / 2);
				score = 0;
                                lifes = 4;
                                max_score = 79;	
				packman.isDead = false;
				dead = false;
				packman.newGame = false;
			  }
	       }
	       else {
		      //если потеряна жизнь, то устанавливаем начальные координаты пакмана и монстра
	          for (int i = 0; i <= stage; i++)
	            monstr[i].draw (g);
	          dead = false;
	          g.setColor (Color.red);
	          Font f = new Font ("Comic Sans MS", Font.BOLD, w * 2);
	          g.setFont (f);
	          g.drawString ("Noooo!", WindowWidth / 2 - w * 3, WindowHeight / 2 - w / 2);
		 
	          SetMonstrCoord ();
	          packman.setX (WindowWidth / 2 - w / 2);
	          packman.setY (WindowHeight / 2 - w / 2); 
	       }
	     }
	     else {
	         //если все хорошо    
		     packman.draw (g);
		     for (int i = 0; i <= stage; i++) {
	    	       monstr[i].draw (g);
	               //System.out.println (i + " " + monstr[i].getX() + " " + monstr[i].getY());
	             }
		     int x = packman.getX();
		     int y = packman.getY();
		     int h_coord = packman.getH_coord();
		
		     //собираем шарики
		     if (Matr [y * 9 / WindowHeight][x * 13 / WindowWidth] == -1) { 
			score++;
		        Matr [y * 9 / WindowHeight][x * 13 / WindowWidth] = 0; 
		     }
		     else if (Matr [y * 9 / WindowHeight][x * 13 / WindowWidth] == -3) { 
			    score += 5;
		        Matr [y * 9 / WindowHeight][x * 13 / WindowWidth] = 0; 
	             }
             if (score == max_score) {
                 next_level = true;
                 max_score += 79;
             }
                
		       
             //проверяем столкновение со стенками			   
		     CheckCollision (x, y, h_coord);
		     packman.Move (LIsExist, RIsExist, UIsExist, DIsExist);
		
		     //монстр
	             for (int i = 0; i <= stage; i++) {
	                     x = packman.getX();
		                 y = packman.getY();
			             h_coord = monstr[i].getH_coord();
						 boolean left = false, right = false, up = false, down = false;
						 int[] next = new int[2];
						 //если координаты монстра не совпадают с координатами пакмана, ищем маршрут
						 if (monstr[i].getX() != x || monstr[i].getY() != y)
						   next = GetRoute (monstr[i].getX(), monstr[i].getY(), x, y); 
						 else
						   //иначе жизнь утрачена
						   if (lifes != 0) {
										 //System.out.println (i + " " + monstr[i].getX() + " " + monstr[i].getY() + x + " " + y);
							 lifes--;
							 dead = true;			                  
						   }
						   //если жизней нет, то это показатель, что игра завершилась
						   if (dead && lifes == 0) {
							  packman.isDead = true;			  
						   }
						 
						 if (!(dead)) {
							 x = monstr[i].getX();
							 y = monstr[i].getY();
							 //CheckCollision (x, y, h_coord);
							 
							 //System.out.println ("Монстр" + next[1] + " " + next[0]);
							 //определяем направление движения монстра
							 if (next[1] - x == -60 && next[0] - y == 0)
								   left = true;
							 else if (next[1] - x == 60 && next[0] - y == 0)
								 right = true;
								 else if (next[1] - x == 0 && next[0] - y == -60)
									   up = true;
									  else if (next[1] - x == 0 && next[0] - y == 60)
										down = true;
							//если движение существует, то монстр движется
							 if (MotionIsExist[i]) {  
										   //System.out.println (i);
							   monstr[i].Move (left, right, up, down);
							   MotionIsExist[i] = false;
							 }
							 else
								MotionIsExist[i] = true;
							 //monstr.Move (left, right, up, down);
						 }
								 else break;
	             }
	     }
     }
     
     
  } 

  //проверка на столкновение с закрашенными клетками
  private void CheckCollision (int x, int y, int h_coord) {
     if (x - h_coord >= 0 && Matr [y * 9 / WindowHeight][(x - h_coord) * 13 / WindowWidth] == -2) 
        LIsExist = false;
     else
        LIsExist = true;
     if ((x + h_coord) * 13 / WindowWidth >= 0 && Matr [y * 9 / WindowHeight][(x + h_coord) * 13 / WindowWidth] == -2) 
        RIsExist = false;
     else
        RIsExist = true;
     if (y - h_coord >= 0 && Matr [(y - h_coord) * 9 / WindowHeight][x * 13 / WindowWidth] == -2) 
        UIsExist = false;
     else
        UIsExist = true;
     if ((y + h_coord) * 9 / WindowHeight >= 0 && Matr [(y + h_coord) * 9 / WindowHeight][x * 13 / WindowWidth] == -2) 
        DIsExist = false;
     else
        DIsExist = true;

  }

  //получение следующей клетки маршрута движения монстра (волновой алгоритм)
  private int[] GetRoute (int start_x, int start_y, int p_x, int p_y) {
     int finish_i = p_y * 9 / WindowHeight;
     int finish_j = p_x * 13 / WindowWidth;
     int start_i = start_y * 9 / WindowHeight;
     int start_j = start_x * 13 / WindowWidth;

     int[][] moves = {{-1, 0}, {0, 1}, {1, 0}, {0, -1}};

     //кол-во ходов для достижения цели
     boolean found = false;
     Queue<Integer> queue_i = new LinkedList<Integer>();
     Queue<Integer> queue_j = new LinkedList<Integer>(); 
     queue_i.offer (start_i);
     queue_j.offer (start_j);

     int [][] desk = new int [11][15];
     for (int i = 0; i < 9; i++) 
       for (int j = 0; j < 13; j++)
         desk[i][j] = Matr[i][j];

     for (int i = 0; i < 11; i++) 
        for (int j = 0; j < 15; j++)
          if (desk[i][j] != -2)
             desk[i][j] = free;
     int k = 0;
     desk [start_i][start_j] = 0;

     int t_i = 0, t_j = 0, x_i = 0, x_j = 0;
     while (!(queue_i.isEmpty()) && !(found)) {
        t_i = queue_i.poll();
        t_j = queue_j.poll();
        k = desk [t_i][t_j];
        k++;
        for (int i = 0; i < 4; i++) {
           x_i = t_i + moves[i][0];
           x_j = t_j + moves[i][1];
           if (x_i >= 0 && x_j >= 0 && x_i < 9 && x_j < 13 && desk [x_i][x_j] == free)
              if (x_i == finish_i && x_j == finish_j)
                 found = true;
              else {
                 queue_i.offer (x_i);
                 queue_j.offer (x_j);
                 desk [x_i][x_j] = k;
              }
        }

     }
     desk[finish_i][finish_j] = k;
   
     //находим маршрут
     int[][] desk1 = new int[11][15];
     for (int i = 0; i < 9; i++) 
       for (int j = 0; j < 13; j++)
         desk1[i][j] = Matr[i][j];

     if (found) {
        found = false;
        Queue<Integer> queue_i1 = new LinkedList<Integer>();
        Queue<Integer> queue_j1 = new LinkedList<Integer>(); 
        queue_i1.offer (finish_i);
        queue_j1.offer (finish_j);
        int k1 = k;
        while (!(queue_i1.isEmpty()) && !(found)) {
            t_i = queue_i1.poll();
            t_j = queue_j1.poll();
            if (desk[t_i][t_j] == k1) {
               for (int i = 0; i < 4; i++) {
                  x_i = t_i + moves[i][0];
                  x_j = t_j + moves[i][1];
                  if (x_i >= 0 && x_j >= 0 && x_i < 9 && x_j < 13 && desk[x_i][x_j] == k1 - 1)
                     if (x_i == start_i && x_j == start_j) {
                            found = true;
                            desk1[t_i][t_j] = 1;                   
                     } 
                     else {
                        //System.out.println (x_i + " " + x_j);
                        
                        queue_i1.offer (x_i);
                        queue_j1.offer (x_j);
                        desk1[t_i][t_j] = 1;
                        
                     }
               }
               k1--;
               
            }
            
        }
 
     }
     
    /* for (int i = 0; i < 9; i++) {
       for (int j = 0; j < 13; j++)
         System.out.print (desk1[i][j] + " ");
       System.out.println ();
     }*/

     //получаем следующую клетку
     int i = 0;
     x_i = start_i + moves[i][0];
     x_j = start_j + moves[i][1];
     i++;
     while ((i < 4) && !(x_i >= 0 && x_j >= 0 && x_i < 9 && x_j < 13 && desk1[x_i][x_j] == 1)) {
        x_i = start_i + moves[i][0];
        x_j = start_j + moves[i][1];
        i++;
     }

     int[] result = new int [2];
     result[0] = x_i * 60;
     result[1] = x_j * 60;
     return result;
  }
  

}