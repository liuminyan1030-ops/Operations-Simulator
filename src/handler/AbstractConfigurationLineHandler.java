package handler;

public abstract class AbstractConfigurationLineHandler implements ConfigurationLineHandler {

    private final String handledType;

    public AbstractConfigurationLineHandler(String handledType) {
        this.handledType = handledType;
    }

    @Override
    public String getLineTypeHandled() {
        return this.handledType;
    }

    
    public boolean hasValidPrefix(String line) {
        if (line == null || line.trim().isEmpty()) {
            return false;
        }
        return line.trim().startsWith(this.handledType);
    }
}