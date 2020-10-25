package ru.stqa.pft.sandbox;

import org.testng.Assert;
import org.testng.annotations.Test;

public class PointTests {

  @Test
  public void testDistance() {
    // стандартная проверка (разные значения)
    Point p1 = new Point(4,5);
    Point p2 = new Point(3,2);
    Assert.assertEquals(p1.distance(p2), 3.1622776601683795);

  }
  @Test
  public void testDistanceSamePoints() {
    // проверка точек с одинаковыми координитами
    Point p3 = new Point(5,5);
    Point p4 = new Point(5,5);
    Assert.assertEquals(p3.distance(p4), 0);
  }
  @Test
  public void testDistanceNegativeCoordinates() {
    // проверка точек с негативными координатами
    Point p1 = new Point(4,-5);
    Point p2 = new Point(-3,2);
    Assert.assertEquals(p1.distance(p2), 9.899494936611665);
  }

}
