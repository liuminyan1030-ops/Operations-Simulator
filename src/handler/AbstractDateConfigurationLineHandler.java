package handler;


import java.time.LocalDate;

import java.time.format.DateTimeParseException;

import constants.ConfigurationConstants;

public abstract class AbstractDateConfigurationLineHandler extends AbstractConfigurationLineHandler {
	

    public AbstractDateConfigurationLineHandler(String keyPrefix) {
        super(keyPrefix);
    }

    @Override
    public boolean validateConfigurationLine(String line) {
        if (!hasValidPrefix(line)) {
            return false;
        }
        String[] parts = line.split("\\|");
        if (parts.length != 2) {
            return false;
        }
        try {
            LocalDate.parse(parts[1].trim(),ConfigurationConstants.DATE_FORMATTER);
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }

    @Override
    public Object getConfigurationValue(String line) {
        String[] parts = line.split("\\|");
        return LocalDate.parse(parts[1].trim(),ConfigurationConstants.DATE_PARSER);
    }
}
