package test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

import model.Frequency;

public class FrequencyTest {
	private static final LocalDate STARTDATE=LocalDate.of(2026, 8, 1);
	
	@Test 
	public void Test_MonthStart_FirstRun_WhenStartIsFirstOfMonth_ShouldBeStartDate() {
		LocalDate firstRunDate=Frequency.MONTHSTART.getFirstRunDate(STARTDATE);
		assertEquals(LocalDate.of(2026,8, 1),firstRunDate);	
	}
	
	@Test
	public void Test_MonthStart_FirstRun_WhenStartIsMidOfMonth_ShouldBeFirstOfNextMonth() {
		LocalDate firstRunDate=Frequency.MONTHSTART.getFirstRunDate(LocalDate.of(2026, 8, 15));
		assertEquals(LocalDate.of(2026, 9, 1),firstRunDate);
	}
	
	@Test
	public void Test_MonthEnd_FirstRun_WhenStartIsFirstofMonth_ShouldBeLastDayOfThatMonth() {
		LocalDate firstRunDate=Frequency.MONTHEND.getFirstRunDate(STARTDATE);
		assertEquals(LocalDate.of(2026, 8, 31),firstRunDate);
	}
	
	@Test
	public void Test_MonthEnd_FirstRun_WhenStartIsLastDayOfMonth_ShouldBeOnThatDay() {
		LocalDate firstRunDate=Frequency.MONTHEND.getFirstRunDate(LocalDate.of(2026, 8, 31));
		assertEquals(LocalDate.of(2026, 8, 31),firstRunDate);
	}
	
	@Test
	public void Test_Weekly_FirstRun_ShouldBeSevenDaysAfterStartDate() {
		LocalDate firstRunDate=Frequency.WEEKLY.getFirstRunDate(STARTDATE);
		assertEquals(LocalDate.of(2026, 8, 8),firstRunDate);
	}
	
	@Test
	public void Test_Daily_FirstRun_ShouldBeStartDate() {
		LocalDate firstRunDate=Frequency.DAILY.getFirstRunDate(STARTDATE);
		assertEquals(STARTDATE,firstRunDate);
	}
	
	@Test
	public void Test_Daily_NextRun_ShouldBeNextDayofStartDate() {
		LocalDate nextRunDate=Frequency.DAILY.getNextRunDate(STARTDATE);
		assertEquals(LocalDate.of(2026,8,2),nextRunDate);
	}
	
	@Test
	public void Test_Weekly_NextRun_ShouldBeAddSevenDays() {
		LocalDate nextRunDate=Frequency.WEEKLY.getNextRunDate(LocalDate.of(2026, 8, 8));
		assertEquals(LocalDate.of(2026, 8, 15),nextRunDate);
	}
	
	@Test
	public void Test_MonthEnd_NextRun_ShouldBeLastDayofNextMonth() {
		LocalDate nextRunDate1=Frequency.MONTHEND.getNextRunDate(LocalDate.of(2026, 8, 31));
		LocalDate nextRunDate2=Frequency.MONTHEND.getNextRunDate(LocalDate.of(2026, 1, 31));
		assertEquals(LocalDate.of(2026, 9, 30),nextRunDate1);
		assertEquals(LocalDate.of(2026, 2, 28),nextRunDate2);
	}
	
	@Test
	public void Test_MonthStart_NextRun_ShouldBeFirstDayOfNextMonth() {
		LocalDate nextRunDate=Frequency.MONTHSTART.getNextRunDate(STARTDATE);
		assertEquals(LocalDate.of(2026, 9, 1),nextRunDate);
	}
	
}
