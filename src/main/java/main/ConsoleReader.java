package main;

import java.io.File;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.SAXException;
import settings.Settings;
import settings.Settings.SettingsTypes;
import xml.XmlHandler;

public class ConsoleReader {

  public static void main(String[] args) {

    Settings settings = Settings.getInstance();

    System.out.println("Запущен клиент программы для передачи данных серверу из xml файла");
    System.out.println("Для выхода из программы введите команду 'exit'");
    System.out.println();

    try (Scanner scanner = new Scanner(System.in)) {

      if (settings.getProperty(SettingsTypes.IP).equals("") ||
          settings.getProperty(SettingsTypes.PORT).equals("")) {
        System.out.println("Не удалось обнаружить информацию для подключения к серверу.");
        System.out.println("Укажите в файле настроек options.txt IP и Port для подключения "
            + "и перезапустите программу");
        System.out.println("Нажмите любую кнопку для выхода");
        scanner.nextLine();
        return;
      }

      String ip = settings.getProperty(SettingsTypes.IP);
      int port = 0;
      try {
        port = Integer.parseInt(settings.getProperty(SettingsTypes.PORT));
      } catch (NumberFormatException e) {
        System.out.println("Порт указан некорректно.");
        System.out.println("Укажите в файле настроек options.txt корректное значение Port"
            + " и перезапустите программу");
        System.out.println("Нажмите любую кнопку для выхода");
        scanner.nextLine();
        return;
      }




      System.out.println("Соединение с сервером установлено");
      System.out.println("Введите путь к .xml файлу для передачи");
      while (true) {

        String string = scanner.nextLine();

        Path pathToXML = Paths.get(string);
        if (string.toLowerCase().endsWith(".xml") && Files.isRegularFile(pathToXML)) {
          System.out.println("true");

          SAXParserFactory factory = SAXParserFactory.newInstance();
          SAXParser parser = factory.newSAXParser();
          XmlHandler handler = new XmlHandler();

          parser.parse(pathToXML.toFile(), handler);

          handler.printGoods();

          try {
            System.out.println(
                "Установка соединения с сервером используя IP=" + ip + " и Port=" + port + "...");
            Socket socket = new Socket(ip, port);
            ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
            outputStream.writeObject(handler.getGoods());
          } catch (IOException e) {
            System.out.println(
                "Не удалось установить соединение с сервером. Проверьте правильность IP и Port "
                    + "и удостоверьтесь что сервер готов к работе.");
            System.out.println("Нажмите любую кнопку для выхода");
            scanner.nextLine();
            return;
          }
        }
        //        System.out.println(Files.isRegularFile(path));
        //        PathMatcher pathMatcher = FileSystems.getDefault().getPathMatcher("glob:**.{xml}");
        //        System.out.println(pathMatcher.matches(path));
        string = scanner.nextLine();

        if (string.equals("exit")) {
          return;
        }
      }
    } catch (ParserConfigurationException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    } catch (SAXException e) {
      e.printStackTrace();
    }

    //    Good good = new Good(1,"ttt");
    //    System.out.println(good);
  }
}
