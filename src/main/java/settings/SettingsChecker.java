package settings;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SettingsChecker {
  private static final String IP_PATTERN = "^([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
      "([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
      "([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
      "([01]?\\d\\d?|2[0-4]\\d|25[0-5])$";

  public boolean checkIP(String ip) {
    Pattern pattern = Pattern.compile(IP_PATTERN);
    Matcher matcher = pattern.matcher(ip);
    return matcher.matches();
  }

  public boolean checkPort(String strPort) {
    try {
      Settings settings = Settings.getInstance();
      int port = Integer.parseInt(strPort);
      if (port < 0 || port > 65535) {
        return false;
      }
      return true;
    } catch (NumberFormatException e) {
      return false;
    }
  }
}
