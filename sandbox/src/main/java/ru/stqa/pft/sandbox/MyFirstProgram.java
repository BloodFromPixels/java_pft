package ru.stqa.pft.sandbox;

public class MyFirstProgram {

  public static void main(String[] args) {
    Somebody m = new Somebody("world!");
    System.out.println("Hello, " + m.someone());

    Square s = new Square(5);
    System.out.println("Площадь квадрата со стороной " + s.l + " = " + s.area());

    Rectangle r = new Rectangle(3, 6);
    System.out.println("Площадь прямоугольника со сторонами " + r.a + " и " + r.b + " = " + r.area());
  }

}