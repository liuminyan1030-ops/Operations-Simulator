package constants;

import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;

public class ConfigurationConstants {
    private ConfigurationConstants() {}
    public static final String KEY_START_DATE = "START_DATE";
    public static final String KEY_END_DATE = "END_DATE";
    public static final String KEY_STEP = "STEP";
    public static final String KEY_VAR = "VAR";
    public static final DateTimeFormatter DATE_PARSER = new DateTimeFormatterBuilder()
            .appendPattern("[yyyy/MM/dd][yyyy-MM-dd]")
            .toFormatter();
    public static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy/MM/dd");
}
