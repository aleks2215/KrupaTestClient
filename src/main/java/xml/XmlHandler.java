package xml;

import java.util.ArrayList;
import java.util.List;
import model.Good;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class XmlHandler extends DefaultHandler {

  private List<Good> goods = new ArrayList<>();

  @Override
  public void startElement(String uri, String localName, String qName, Attributes attributes)
      throws SAXException {
    if (qName.equals("good")) {
      Integer code = Integer.valueOf(attributes.getValue("code"));
      String title = attributes.getValue("title");
      goods.add(new Good(code, title));
    }
  }

  public List<Good> getGoods() {
    return goods;
  }

  public void printGoods() {
    for (Good good : goods) {
      System.out
          .printf("Код товара: %s, наименование товара: %s%n", good.getCode(), good.getTitle());
    }
  }
}
