package address.model;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Helper class to wrap list of persons into XML code and back.
 */
@XmlRootElement(name = "persons")
public class PersonListWrapper 
{
	private List<Person> persons;
	
	@XmlElement(name = "person")
	public List<Person> getPersons()
	{
		return persons;
	}
	
	public void setPersons(List<Person> persons)
	{
		this.persons = persons;
	}
	
}
