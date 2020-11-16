package xml;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import model.Good;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class XmlReader {

  private static Logger log = Logger.getLogger(XmlReader.class.getName());
  private Scanner scanner;
  private List<Good> goods = new ArrayList<>();

  public XmlReader(Scanner scanner) {
    this.scanner = scanner;
  }

  public void readAndParseXMLFile() {
    Path pathToXML = readXMLFile();
    parseXMLFile(pathToXML);
  }

  public Path readXMLFile() {
    System.out.println("Введите путь к .xml файлу для передачи");
    System.out.println("Для выхода из программы введите команду 'exit'");
    while (true) {
      String string = scanner.nextLine();

      if (string.equals("exit")) {
        System.exit(0);
      }

      Path pathToXML = Paths.get(string);
      if (string.toLowerCase().endsWith(".xml") && Files.isRegularFile(pathToXML)) {
        return pathToXML;
      } else {
        System.out.println("Введите корректный путь к .xml файлу для передачи");
      }
    }
  }

  public void parseXMLFile(Path pathToXML) {
    try {
      SAXParserFactory factory = SAXParserFactory.newInstance();
      SAXParser parser = factory.newSAXParser();
      XmlHandler handler = new XmlHandler();
      parser.parse(pathToXML.toFile(), handler);

      log.log(Level.INFO,"Файл прочитан");
      System.out.println("Содержимое .xml файла:");
      printGoods();
    } catch (ParserConfigurationException | IOException | SAXException e) {
      log.log(Level.ERROR,"Ошибка в работе парсера",e);
    }
  }

  public void printGoods() {
    for (Good good : goods) {
      System.out
          .printf("Код товара: %s, наименование товара: %s%n", good.getCode(), good.getTitle());
    }
  }

  public List<Good> getGoods() {
    return goods;
  }


  public class XmlHandler extends DefaultHandler {

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes)
        throws SAXException {
      if (qName.equals("good")) {
        Integer code = Integer.valueOf(attributes.getValue("code"));
        String title = attributes.getValue("title");
        goods.add(new Good(code, title));
      }
    }
  }
}


