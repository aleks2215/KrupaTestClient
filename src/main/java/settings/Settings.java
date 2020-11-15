package settings;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Properties;

public class Settings {

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
        properties.setProperty(SettingsTypes.PORT.getName(), "");
        properties.setProperty(SettingsTypes.IP.getName(), "");
        try (FileWriter fileWriter = new FileWriter(propFile)) {
          properties.store(fileWriter, "Client options");
        }
      } else {
          properties.load(new FileReader(propFile));
      }
    } catch (IOException e) {
      //            e.printStackTrace();
      //      Launch.log.log(Level.WARNING,"Не удалось загрузить файл с настройками", e);
    }
  }

  public String getProperty(SettingsTypes type) {
    String result = null;
    if (type == SettingsTypes.IP) {
      result = properties.getProperty(SettingsTypes.IP.getName(), "");
    } else if (type == SettingsTypes.PORT) {
      result = properties.getProperty(SettingsTypes.PORT.getName(), "");
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
