package ru.stqa.pft.sandbox;

import org.testng.annotations.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static ru.stqa.pft.sandbox.Primes.isPrime;

public class PrimeTests {

  // Integer.MAX_VALUE - максимальное простое число. Тест проверяет, что для этого числа наш цикл возвращает true.
  @Test
  public void testPrime() {
    assertTrue(isPrime(Integer.MAX_VALUE));
  }

  @Test
  public void testPrimeFast() {
    assertTrue(Primes.isPrimeFast(Integer.MAX_VALUE));
  }

  // (enabled = false) - отключение теста.
  @Test (enabled = false)
  public void testPrimeLong() {
    long n = Integer.MAX_VALUE;
    assertFalse(isPrime(n));
  }

  @Test
  public void testNonPrime() {
    assertFalse(isPrime(Integer.MAX_VALUE - 2));
  }
}
