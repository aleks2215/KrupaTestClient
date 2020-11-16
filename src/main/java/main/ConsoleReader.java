package main;

import java.util.Scanner;
import network.ClientSocket;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import settings.Settings;
import settings.Settings.SettingsTypes;
import settings.SettingsChecker;
import xml.XmlReader;

public class ConsoleReader {

  private static Logger log = Logger.getLogger(ConsoleReader.class.getName());
  private static final Settings settings = Settings.getInstance();

  public static void main(String[] args) {
    String ip;
    int port;

    log.log(Level.INFO, "Запущен клиент программы для передачи данных серверу из xml файла");

    try (Scanner scanner = new Scanner(System.in)) {

      SettingsChecker settingsChecker = new SettingsChecker();

      if (!settingsChecker.checkIP(settings.getProperty(SettingsTypes.IP))) {
        log.log(Level.WARN,"Не удалось прочитать корректный ip адрес из файла options.txt");
        while (true) {
          System.out.println("Введите корректный ip:");
          String string = scanner.nextLine();

          if (string.equals("exit")) {
            System.exit(0);
          }

          if (settingsChecker.checkIP(string)) {
            ip = string;
            settings.setProperty(SettingsTypes.IP,ip);
            settings.save();
            break;
          }
        }
      } else {
        ip = settings.getProperty(SettingsTypes.IP);
      }

      if (!settingsChecker.checkPort(settings.getProperty(SettingsTypes.PORT))) {
        log.log(Level.WARN,"Не удалось прочитать корректный порт из файла options.txt");
        while (true) {
          System.out.println("Введите корректный порт:");
          String string = scanner.nextLine();

          if (string.equals("exit")) {
            System.exit(0);
          }

          if (settingsChecker.checkPort(string)) {
            port = Integer.parseInt(string);
            settings.setProperty(SettingsTypes.PORT, String.valueOf(port));
            settings.save();
            break;
          }
        }
      } else {
        port = Integer.parseInt(settings.getProperty(SettingsTypes.PORT));
      }

      XmlReader xmlReader = new XmlReader(scanner);
      xmlReader.readAndParseXMLFile();

      ClientSocket clientSocket = new ClientSocket(ip, port, xmlReader.getGoods());

      clientSocket.connectAndSendXMLToServer();
    }
  }
}


