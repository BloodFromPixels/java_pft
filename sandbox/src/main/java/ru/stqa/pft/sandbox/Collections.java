package ru.stqa.pft.sandbox;

import java.util.ArrayList;
import java.util.List;

public class Collections {

  public static void main(String[] args) {

    /*
    Объявление массива, способного хранить 4 элемента.

    Нумерация в массивах начинается с 0.

    Базовое описание массива:

    String[] langs = new String[4];
    langs[0] = "Java";
    langs[1] = "C#";
    langs[2] = "Python";
    langs[3] = "PHP";
    */

    // Короткое описание массива:

    String[] langs = {"Java", "C#", "Python", "PHP"};

    // Интерфейс List и класс, который реализует этот интерфейс. В скобках <> указан тип элементов в списке:

    List<String> languages = new ArrayList<String>();

    // Заполняем список:

    languages.add("Java");
    languages.add("C#");
    languages.add("Python");
    languages.add("PHP");

    // List<String> languages = Arrays.asList("Java", "C#", "Python", "PHP"); - короткая запись с наполнением списка.

    /*
    Описание массива, элементы которого перечисляются циклом:

    for (int i = 0; i < langs.length; i++) {
      System.out.println("Я хочу выучить " + langs[i]);
    */

    // Специальное описание коллекций в Java, l - ссылка на элемент массива/списка:

    for (String l : langs) {
      System.out.println("Я хочу выучить " + l);
    }

    for (String l : languages) {
      System.out.println("Я хочу выучить " + l);
    }
  }
}
