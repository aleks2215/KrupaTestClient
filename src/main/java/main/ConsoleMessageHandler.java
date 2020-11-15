package main;

import java.util.LinkedList;
import java.util.Queue;

public class ConsoleMessageHandler {

  private Queue<String> messageQueue = new LinkedList<>();

  public ConsoleMessageHandler() {
    messageQueue.add("Запущен клиент");
    messageQueue.add("Для выхода из программы введите команду exit");
  }

  public void printMessage() {
    while (!messageQueue.isEmpty()) {
      System.out.println(messageQueue.poll());
    }
  }
}
