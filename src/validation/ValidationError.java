package validation;

public class ValidationError {
    private final int lineNumber;
    private final String problematicText;
    private final String errorDescription;

    public ValidationError(int lineNumber, String problematicText, String errorDescription) {
        this.lineNumber = lineNumber;
        this.problematicText = problematicText;
        this.errorDescription = errorDescription;
    }

    public int getLineNumber() {
        return lineNumber;
    }

    public String getProblematicText() {
        return problematicText;
    }

    public String getErrorDescription() {
        return errorDescription;
    }

    @Override
    public String toString() {
        return "Line " + lineNumber + ": '" + problematicText + "' - " + errorDescription;
    }
}