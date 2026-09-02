package test;

import model.Frequency;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FrequencyTest {

	enum Mode {
		FirstRun,
		NextRun
	}

	@ParameterizedTest
	@CsvSource({
			"FirstRun, 2026-08-01, 2026-08-01, 'When start is first day of month first run is the start date'",
			"FirstRun, 2026-08-02, 2026-09-01, 'When start is not first of month first run is next month start'",
			"FirstRun, 2026-08-15, 2026-09-01, 'When start is mid month first run is next month start'",
			"FirstRun, 2026-08-31, 2026-09-01, 'When start is last day of month first run is next month start'",
			"NextRun, 2026-08-01, 2026-09-01, 'Next run after a month start is the first day of next month'"
	})
	void Test_FirstRunAndNextRun_MonthStart(Mode mode, LocalDate inputDate, LocalDate expectedDate,
			String description) {
		assertEquals(expectedDate, runDate(Frequency.MONTHSTART, mode, inputDate), description);
	}

	@ParameterizedTest
	@CsvSource({
			"FirstRun, 2026-08-01, 2026-08-31, 'When start is first day of month first run is last day of that month'",
			"FirstRun, 2026-08-15, 2026-08-31, 'When start is mid month first run is still last day of that month'",
			"FirstRun, 2026-08-31, 2026-08-31, 'When start is already last day first run stays on that day'",
			"NextRun, 2026-08-31, 2026-09-30, 'Next run after month end is last day of the following month'",
			"NextRun, 2026-01-31, 2026-02-28, 'Next run after January 31 is last day of February'"
	})
	void Test_FirstRunAndNextRun_MonthEnd(Mode mode, LocalDate inputDate, LocalDate expectedDate,
			String description) {
		assertEquals(expectedDate, runDate(Frequency.MONTHEND, mode, inputDate), description);
	}

	@ParameterizedTest
	@CsvSource({
			"FirstRun, 2026-08-01, 2026-08-08, 'First weekly run is seven days after the start date'",
			"NextRun, 2026-08-08, 2026-08-15, 'Next weekly run adds another seven days'"
	})
	void Test_FirstRunAndNextRun_Weekly(Mode mode, LocalDate inputDate, LocalDate expectedDate,
			String description) {
		assertEquals(expectedDate, runDate(Frequency.WEEKLY, mode, inputDate), description);
	}

	@ParameterizedTest
	@CsvSource({
			"FirstRun, 2026-08-01, 2026-08-01, 'First daily run is the start date'",
			"NextRun, 2026-08-01, 2026-08-02, 'Next daily run is the following day'"
	})
	void Test_FirstRunAndNextRun_Daily(Mode mode, LocalDate inputDate, LocalDate expectedDate,
			String description) {
		assertEquals(expectedDate, runDate(Frequency.DAILY, mode, inputDate), description);
	}

	private LocalDate runDate(Frequency frequency, Mode mode, LocalDate inputDate) {
		if (mode == Mode.FirstRun) {
			return frequency.getFirstRunDate(inputDate);
		}
		return frequency.getNextRunDate(inputDate);
	}
}
