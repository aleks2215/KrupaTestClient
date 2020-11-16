package network;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;
import model.Good;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

public class ClientSocket {

  private static Logger log = Logger.getLogger(ClientSocket.class.getName());
  String ip;
  int port;
  private List<Good> goods;

  public ClientSocket(String ip, int port, List<Good> goods) {
    this.ip = ip;
    this.port = port;
    this.goods = goods;
  }

  public void connectAndSendXMLToServer() {
    try {
      log.log(Level.INFO,
          "Установка соединения с сервером используя IP=" + ip + " и Port=" + port + "...");
      Socket socket = new Socket(ip, port);
      log.log(Level.INFO,"Соединение с сервером установлено");

      ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
      outputStream.writeObject(goods);
      log.log(Level.INFO,"Данные о товарах успешно отправлены");
    } catch (IOException e) {
      log.log(Level.ERROR, "Не удалось установить соединение с сервером", e);
      System.out.println(
          "Проверьте правильность IP и Port "
              + ",удостоверьтесь что сервер готов к работе и перезапустите программу.");
    }
  }
}


