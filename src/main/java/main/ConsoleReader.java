package main;

import java.util.Scanner;

import network.ClientSocket;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import settings.Settings;
import settings.Settings.SettingsTypes;
import xml.XmlReader;

public class ConsoleReader {

  private static Logger log = Logger.getLogger(ConsoleReader.class.getName());
  private static final Settings settings = Settings.getInstance();

  public static void main(String[] args) {
    String ip;
    int port;

    log.log(Level.INFO,"Запущен клиент программы для передачи данных серверу из xml файла");

    try (Scanner scanner = new Scanner(System.in)) {

      if (!checkSettingsExists()) {
        System.exit(0);
      }

      ip = settings.getStringProperty(SettingsTypes.IP);
      port = settings.getIntProperty(SettingsTypes.PORT);
      if (port < 0) {
        System.exit(0);
      }

      XmlReader xmlReader = new XmlReader(scanner);
      xmlReader.readAndParseXMLFile();

      ClientSocket clientSocket = new ClientSocket(ip, port, xmlReader.getGoods());

      clientSocket.connectAndSendXMLToServer();
    }
  }

  private static boolean checkSettingsExists() {
    if (settings.getStringProperty(SettingsTypes.IP).equals("") ||
        settings.getIntProperty(SettingsTypes.PORT) == 0) {
      log.log(Level.ERROR, "Не удалось обнаружить информацию для подключения к серверу.");
      System.out.println("Укажите в файле настроек options.txt IP и Port для подключения "
          + "и перезапустите программу");
      return false;
    }
    return true;
  }
}


