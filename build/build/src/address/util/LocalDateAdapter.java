package address.util;

import java.time.LocalDate;

import javax.xml.bind.annotation.adapters.XmlAdapter;

/**
 * Adapter for JAXB to between the local date and the ISO 8601
 * String representation of a date.
 * @author htha9587
 *
 */
public class LocalDateAdapter extends XmlAdapter<String, LocalDate>
{

	@Override
	public LocalDate unmarshal(String v) throws Exception {
		return LocalDate.parse(v);
	}

	@Override
	public String marshal(LocalDate v) throws Exception {
		return v.toString();
	}
	
	
	
	
	
}
