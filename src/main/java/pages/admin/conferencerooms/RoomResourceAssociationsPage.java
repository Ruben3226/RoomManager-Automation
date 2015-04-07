package main.java.pages.admin.conferencerooms;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;

import main.java.selenium.UIMethods;

/**
 * 
 * @author Juan Carlos Guevara
 *
 */
public class RoomResourceAssociationsPage extends RoomBaseAbstractPage {
	
	/**
	 * [CG]Button that allows to add an available resource to be associated to the room
	 * @param resourceName
	 * @return the same page
	 */
	public RoomResourceAssociationsPage clickAddResourceToARoom(String resourceName) {
		driver.findElement(By.xpath("//span[contains(text(),'" + resourceName +
				"')]/parent::div/following-sibling::div/button")).click();
		return this;
	}

	/**
	 * [CG]Method that allows to change the number of resources associated to a room
	 * @param resourceName
	 * @param value
	 * @return the same page
	 */
	public RoomResourceAssociationsPage changeValueForResourceFromAssociatedList(String resourceName,
			String value) {
		String locator = "//span[contains(text(),'" + resourceName + 
				"')]/ancestor::div/following-sibling::div/input"; 
		driver.findElement(By.xpath(locator)).clear();
		driver.findElement(By.xpath(locator)).sendKeys(value);
		return this;
	}

	/**
	 * [CG]Method that removes one resource associated to a room
	 * @param resourceName
	 * @return the same page 
	 */
	public RoomResourceAssociationsPage removeResourceFromAssociatedList(String resourceName) {
		driver.findElement(By.xpath("//span[contains(text(),'" + resourceName + 
				"')]/ancestor::div/following-sibling::div//i[@class='fa fa-minus']")).click();
		return this;
	}

	/**
	 * [CG]Method that returns the quantity of a resource associated to a room
	 * @param resourceName
	 * @return
	 */
	public String getResourceAmount(String resourceName) {
		return driver.findElement(By.xpath("//span[contains(text(),'" + resourceName + 
				"')]/ancestor::div/following-sibling::div/input")).getAttribute("value");
	}

	/**
	 * [CG]Boolean method that returns true or false when locates a resource
	 * @param resourceName
	 * @return
	 */
	public boolean searchResourcequantity(String resourceName) {
		return UIMethods.isElementPresent(By.xpath("//span[contains(text(),'" + resourceName + 
				"')]/ancestor::div/following-sibling::div/input"));
	}

	/**
	 * [CG]Method that returns True when it finds a resource associated to a room
	 * @param resourceName
	 * @return
	 */
	public boolean searchResource(String resourceName) {
			return UIMethods.isElementPresent(By.xpath("//span[contains(text(),'" + resourceName +
				"')and@class='ng-binding']"));	
	}
	/**
	 * [ML]Method that returns boolean if a resource displayed name exist in RoomResourceAssociationsPage 
	 * by column name and resource displayed name parameters
	 * @param columnName, could be 'Available' or 'Associated'
	 * @param resourceDisplayName, could be any name
	 * @return
	 */
	public boolean getResourceDisplayName(String columnName, String resourceDisplayName) {
		By resourceDName = By.xpath("//legend[contains(text(),'" + columnName + "')]/parent::div//div[@class='list-group']"
				+ "//span[@class='ng-binding'][contains(text(),'" + resourceDisplayName + "')]/parent::div");
		return UIMethods.isElementPresent(resourceDName);
	}

	/**
	 * [ML]Method that return boolean if resource display name is in available column
	 * @param resourceDisplayName
	 * @return
	 */
	public boolean getResourceDisplayNameAvailable(String resourceDisplayName) {
		By resourceDName = By.xpath("//legend[contains(text(),'Available')]/parent::div//div[@class='list-group']"
				+ "//span[@class='ng-binding'][contains(text(),'" + resourceDisplayName + "')]");
		return UIMethods.isElementPresent(resourceDName);
	}

	/**
	 * [RB]THis method verify if exists changes after click on cancel or save button
	 * @return tru or false
	 */
	public boolean verifyChanges(String resourceName) {
		return UIMethods.isElementPresent(By.xpath("//div[@class='list-group-item text-center col-xs-12 ng-scope']"
				+ "//span[contains(text(),'"+resourceName+"')]"));
	}
	
	/**
	 * [YA] This method clicks Save button when an error message is expected and 
	 * it should stay in the same page
	 * @param errorMessage
	 * @return
	 */
	public RoomResourceAssociationsPage clickSaveWithErrorBtn() {
		saveBtn.click();
		wait.until(ExpectedConditions.visibilityOf(errorMessageLbl));
		return this;
	}
}
