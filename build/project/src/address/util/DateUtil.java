package address.util;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * Helper functions for adding dates.
 * @author htha9587
 *
 */
public class DateUtil 
{
	//Date Conversion Pattern.
	private static final String DATE_PATTERN = "dd.MM.yyyy";
	//Date formatter.
	private static final DateTimeFormatter DATE_FORMATTER =
	DateTimeFormatter.ofPattern(DATE_PATTERN);
	
	/**
	 * Returns date pattern as a well defined String. 
	 * {@link DateUtil#DATE_PATTERN} is used.
	 */
	public static String format(LocalDate date)
	{
		if(date == null)
		{
			return null;
		}
		return DATE_FORMATTER.format(date);
	}
	
	/**Defines the String in the form of DATE_PATTERN to a 
	 * LocalDate object.
	 */
	public static LocalDate parse(String dateString)
	{
		try
		{
			return DATE_FORMATTER.parse(dateString, LocalDate::from);
		}
		catch (DateTimeParseException e)
		{
			return null;
		}
	}
	
	/**
	 * Checks String to determine if it's a valid date.
	 */
	public static boolean validDate(String dateString)
	{
		//Try to parse string.
		return DateUtil.parse(dateString) != null;
	}
	
}
