package settings;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Properties;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;


public class Settings {

  private static Logger log = Logger.getLogger(Settings.class.getName());
  private static Settings settings;
  private Properties properties;

  private Settings() {
    initProperties();
  }

  public static Settings getInstance() {
    if (settings == null) {
      settings = new Settings();
    }

    return settings;
  }

  private void initProperties() {
    properties = new Properties();
    try {
      String propPath = System.getProperty("user.dir") + "\\options.txt";
      File propFile = new File(propPath);
      if (!propFile.exists()) {
        propFile.createNewFile();
        properties.setProperty(SettingsTypes.PORT.getName(), "4567");
        properties.setProperty(SettingsTypes.IP.getName(), "");
        try (FileWriter fileWriter = new FileWriter(propFile)) {
          properties.store(fileWriter, "Client options");
        }
      } else {
          properties.load(new FileReader(propFile));
      }
    } catch (IOException e) {
      log.log(Level.WARN, "Не удалось загрузить файл с настройками", e);
    }
  }

  public String getStringProperty(SettingsTypes type) {
    String result = null;
    if (type == SettingsTypes.IP) {
      result = properties.getProperty(SettingsTypes.IP.getName(), "");
    }
    return result;
  }

  public int getIntProperty(SettingsTypes type) {
    int result = 0;
    if (type == SettingsTypes.PORT) {
      try {
        result = Integer.parseInt(properties.getProperty(SettingsTypes.PORT.getName(), "0"));
      } catch (NumberFormatException e) {
        log.log(Level.ERROR, "Порт указан некорректно", e);
        System.out.println("Укажите в файле настроек options.txt корректное значение Port"
            + " и перезапустите программу");
        System.exit(0);
      }
    }
    return result;
  }

  public enum SettingsTypes {
    IP("IP"),
    PORT("Port");

    private final String name;

    SettingsTypes(String name) {
      this.name = name;
    }

    public String getName() {
      return name;
    }
  }
}
