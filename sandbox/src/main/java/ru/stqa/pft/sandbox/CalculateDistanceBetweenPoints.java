package ru.stqa.pft.sandbox;

public class CalculateDistanceBetweenPoints {

  public static void main(String[] args) {

    // пример работы оригинальной функции
    Point p1 = new Point(4, 5);
    Point p2 = new Point(3, 2);
    System.out.println("Расстояние между точками координат " + "A(" + p1.x + "; " + p1.y + ") " + "и " + "B(" + p2.x +
            "; " + p2.y + ")" + " = " + distance(p1, p2));

    // пример работы метода из класса Point
    Point p3 = new Point(4,5);
    Point p4 = new Point(3,2);
    System.out.println("Расстояние между точками координат " + "A(" + p3.x + "; " + p3.y + ") " + "и " + "B(" + p4.x +
            "; " + p4.y + ")" + " = " + p3.distance(p4));

  }

  //Оригинальная функция для подсчёта, сделанная внутри запускающего класса
  public static double distance(Point p1, Point p2) {
    return Math.sqrt((p2.y - p1.y) * (p2.y - p1.y) + (p2.x - p1.x) * (p2.x - p1.x));
  }

}