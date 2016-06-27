package address.view;

import java.text.DateFormatSymbols;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import address.model.Person;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.XYChart;

/**
 * Controller for birthday statistics view.
 * @author htha9587
 *
 */
public class BirthdayStatisticsController 
{
	
	@FXML 
	private BarChart<String, Integer> barChart;
	
	@FXML
	private CategoryAxis xAxis;
	
	private ObservableList<String> monthNames = FXCollections.observableArrayList();
	
	/**
	 * Initializes controller class after fxml file has been loaded.
	 */
	@FXML
	private void initialize()
	{
		//Array with English month names.
		String[] months = DateFormatSymbols.getInstance(Locale.ENGLISH).getMonths();
		//Convert to list and add it to observable list of months.
		monthNames.addAll(Arrays.asList(months));
		
		//Assign month names as categories for the horizontal axis.
		xAxis.setCategories(monthNames);
	}
	
	
	/**
	 * Sets persons to show statistics for.
	 */
	public void setPersonData(List<Person> persons)
	{
		//Count number of people having their birthday in the same month.
		int[] monthCounter = new int[12];
		for(Person p: persons)
		{
			int month = p.getBirthday().getMonthValue() - 1;
			monthCounter[month]++;
		}
		
		XYChart.Series<String, Integer> series = new XYChart.Series<>();
		
		//Creates xy chart object for each month and adds to the series.
		for(int i = 0; i< monthCounter.length; i++)
			{
			series.getData().add(new XYChart.Data<>(monthNames.get(i), monthCounter[i]));
			}
		barChart.getData().add(series);
	}
	
}
