package ru.stqa.pft.mantis.appmanager;

import org.apache.commons.net.ftp.FTPClient;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class FtpHelper {

  private final ApplicationManager app;
  private final FTPClient ftp;

  // Инициализация, создаётся FTPClient
  public FtpHelper(ApplicationManager app) {
    this.app = app;
    ftp = new FTPClient();
  }

  // Загрузка файла
  public void upload(File file, String target, String backup) throws IOException {
    // Установка соединения с сервером
    ftp.connect(app.getProperty("ftp.host"));
    // Выполняется залогин
    ftp.login(app.getProperty("ftp.login"), app.getProperty("ftp.password"));
    // Удаление предыдущей резервной копии
    ftp.deleteFile(backup);
    // Переименовываем удалённый файл, делаем резервную копию
    ftp.rename(target, backup);
    ftp.enterLocalPassiveMode();
    // Передача локального файла, из которого берутся данные и передаются на удалённую машину
    ftp.storeFile(target, new FileInputStream(file));
    ftp.sendSiteCommand("chmod 755 config_inc.php");
    // Разрыв соединения
    ftp.disconnect();
  }

  // Восстановление исходной конфигурации тестируемой системы
  public void restore(String target, String backup) throws IOException {
    ftp.connect(app.getProperty("ftp.host"));
    ftp.login(app.getProperty("ftp.login"), app.getProperty("ftp.password"));
    // Удаление файла, который мы изначально загрузили
    ftp.deleteFile(target);
    // Восстановление оригинального файла из резервной копии
    ftp.rename(backup, target);
    ftp.disconnect();
  }
}