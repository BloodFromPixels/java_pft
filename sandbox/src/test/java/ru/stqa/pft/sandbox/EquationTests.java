package ru.stqa.pft.sandbox;

import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

public class EquationTests {

  @Test
  public void test0() {
    Equation e = new Equation(1, 1, 1);
    assertEquals(e.rootNumber(), 0);
  }

  @Test
  public void test1() {
    Equation e = new Equation(1, 2, 1);
    assertEquals(e.rootNumber(), 1);
  }

  @Test
  public void test2() {
    Equation e = new Equation(1, 5, 6);
    assertEquals(e.rootNumber(), 2);
  }

  @Test
  public void testLinear() {
    Equation e = new Equation(0, 1, 1);
    assertEquals(e.rootNumber(), 1);
  }

  @Test
  public void testConstant() {
    Equation e = new Equation(0, 0, 1);
    assertEquals(e.rootNumber(), 0);
  }

  @Test
  public void testZero() {
    Equation e = new Equation(0, 0, 0);
    assertEquals(e.rootNumber(), -1);
  }
}
