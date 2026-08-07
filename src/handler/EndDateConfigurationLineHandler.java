package handler;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class EndDateConfigurationLineHandler implements ConfigurationLineHandler {

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy/MM/dd");
    private static final String PREFIX = "END_DATE"; 

    @Override
    public boolean validateConfigurationLine(String line) {
        if (line == null) return false;
        String trimmedLine = line.trim();
        if (!trimmedLine.startsWith(PREFIX)) return false;

        String[] parts = trimmedLine.split("\\|");
        if (parts.length != 2) return false;

        String endDate = parts[1].trim();

        if (endDate.isEmpty()) return false;

        try {
            LocalDate.parse(endDate, DATE_FORMATTER);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public LocalDate getConfigurationValue(String line) {
        String[] parts = line.split("\\|");
        String endDate = parts[1].trim();
        return LocalDate.parse(endDate, DATE_FORMATTER);
    }
}