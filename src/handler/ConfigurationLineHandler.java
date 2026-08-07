package handler;

public interface ConfigurationLineHandler {
    boolean validateConfigurationLine(String line);
    Object getConfigurationValue(String line);
}