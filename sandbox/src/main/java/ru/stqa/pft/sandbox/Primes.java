package ru.stqa.pft.sandbox;

public class Primes {

  /*
  Функция на проверку простоты числа. Простое число - то, которое делится на себя и на единицу. Число n делим
  последовательно на разные числа, пока не найдём, на что число делится;

  i++ - инкремент (увеличение переменной на единицу);

  (n % i == 0) - если n делится на i без остатка (в скобках процент остатка от деления), то делать ничего
  не нужно, ведь число составное. Если не делится - возвращаем true, т.к. число простое.
  */

  public static boolean isPrime(int n) {
    for (int i = 2; i < n; i++) {
      if (n % i == 0) {
        return  false;
      }
    }
    return true;
  }

  public static boolean isPrimeFast(int n) {
    int m = (int) Math.sqrt(n);
    for (int i = 2; i < m; i++) {
      if (n % i == 0) {
        return  false;
      }
    }
    return true;
  }

  /*
  В цикле while инициализация, условие окончания и действие по окончании итерации цикла с переменной i разносится по
  разным местам.
  */

  public static boolean isPrimeWhile(int n) {
    int i = 2;
    while (i < n && n % i == 0) {
      i++;
    }
    return i == n;
  }

  public static boolean isPrime(long n) {
    for (int i = 2; i < n; i++) {
      if (n % i == 0) {
        return  false;
      }
    }
    return true;
  }
}
