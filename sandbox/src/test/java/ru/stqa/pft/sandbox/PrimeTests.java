package ru.stqa.pft.sandbox;

import org.junit.Assert;
import org.testng.annotations.Test;

public class PrimeTests {

  // Integer.MAX_VALUE - максимальное простое число. Тест проверяет, что для этого числа наш цикл возвращает true.

  @Test
  public void testPrime() {
    Assert.assertTrue(Primes.isPrime(Integer.MAX_VALUE));
  }

  @Test
  public void testPrimeFast() {
    Assert.assertTrue(Primes.isPrimeFast(Integer.MAX_VALUE));
  }

  // (enabled = false) - отключение теста.

  @Test (enabled = false)
  public void testPrimeLong() {
    long n = Integer.MAX_VALUE;
    Assert.assertFalse(Primes.isPrime(n));
  }

  @Test
  public void testNonPrime() {
    Assert.assertFalse(Primes.isPrime(Integer.MAX_VALUE - 2));
  }
}
