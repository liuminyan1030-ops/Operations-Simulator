package validation;

import handler.*;

import java.util.*;
import java.util.List;

public class LineValidator {
    private final List<ConfigurationLineHandler> handlers;

    public LineValidator(List<ConfigurationLineHandler> handlers) {
        this.handlers = handlers;
    }

    public List<ValidationError> validate(List<String> lines) {
        List<ValidationError> validationErrors = new ArrayList<>();

        for (int i = 0; i < lines.size(); i++) {
            int lineNumber = i + 1;
            String rawLine = lines.get(i);
            String line = rawLine.trim();

            if (line.isEmpty()) {
                continue;
            }

            boolean isValid = false;
            for (ConfigurationLineHandler handler : handlers) {
                if (handler.validateConfigurationLine(line)) {
                    isValid = true;
                    break; 
                }
            }

         
            if (!isValid) {
                validationErrors.add(new ValidationError(lineNumber, rawLine, "Invalid or unrecognized configuration line format"));
            }
        }

        return validationErrors;
    }
}