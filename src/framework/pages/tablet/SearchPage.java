package framework.pages.tablet;

/**Created by Jose Cabrera
 * 3/4/15
 * 
 */

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import framework.common.UIMethods;
import framework.selenium.SeleniumDriverManager;

/**
 * @title  SearchPage
 * @author Jose Cabrera
 * @description This page allow search by different criteria
 */
public class SearchPage {

	private WebDriver driver;
	private WebDriverWait wait;

	@FindBy(xpath = "//span[@ng-click='goBack()']")
	WebElement backBtn;

	@FindBy(xpath = "//span[contains(text(),'Advanced')]")
	WebElement advancedBtn;
	
	@FindBy(xpath = "//button[@ng-click='clear()']")
	WebElement clearBtn;

	@FindBy(id = "txtRoomName")
	WebElement roomNameTxtBox;

	@FindBy(id = "txtMinimumCapacity")
	WebElement minimumCapacityTxtBox;

	@FindBy(id = "listLocation")
	WebElement locationCmbBox;

	@FindBy(xpath = "//div[@class='row v-space ng-scope']/div[@class='col-xs-12']/following-sibling::div")
	WebElement listResources;

	@FindBy(xpath = "//span[@ng-bind='currentTime']")
	WebElement dateLabel;
	
	@FindBy(xpath = "//div[@class='currenttime']")
	WebElement timeLine;
	
	@FindBy(xpath = "//span[contains(text(),'Search')]")
	WebElement searchLbl;
	
	public SearchPage() {
		this.driver = SeleniumDriverManager.getManager().getDriver();
		PageFactory.initElements(driver, this);
		wait = SeleniumDriverManager.getManager().getWait();
	}
	
	/**
	 * [JC] This method click on Clear button
	 * @return
	 */
	public SearchPage clickClearBtn() {
		clearBtn.click();
		return this;
	}

	/**
	 * [JC] This method click on Back button
	 * @return
	 */
	public HomeTabletPage clickBackBtn() {
		backBtn.click();
		return new HomeTabletPage();
	}

	/**
	 * [JC] This method click on Advanced button
	 * @return
	 */
	public SearchPage clickCollapseAdvancedBtn() {
		advancedBtn.click();
		wait.until(ExpectedConditions.visibilityOf(locationCmbBox));
		return this;
	}
	
	/**
	 * [JC] This method click on Advanced button
	 * @return
	 */
	public SearchPage clickHiddenAdvancedBtn() {
		advancedBtn.click();
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("txtRoomName")));
		return this;
	}
	
	/**
	 * [RB]This method verifies that room display name is present when is searched
	 * @param roomDisplayName is the display name of room 
	 * @return true or false
	 */
	public boolean roomIsDiplayed(String roomDisplayName) {
		return UIMethods.isElementPresent(By.xpath("//button[contains(text(),'" + roomDisplayName 
				+ "')and@class='ng-scope']"));
	}

	/**
	 * [JC] This method set the new value to RoomName textBox
	 * @return
	 */
	public SearchPage setName(String strName) {
		roomNameTxtBox.sendKeys(strName);
		return this;
	}

	/**
	 * [JC] This method set the new value to MinimumCap textBox
	 * @return
	 */
	public SearchPage setMinimumCap(String strMinimumCap) {
		minimumCapacityTxtBox.clear();
		minimumCapacityTxtBox.sendKeys(strMinimumCap);  
		return this;
	}
	
	/**
	 * [JC] This method verify if MinimumCapTxtBox are Empty
	 * @return
	 */
	public boolean isEmptyMinimumCap() {
		return minimumCapacityTxtBox.getAttribute("value").isEmpty();
	}

	/**
	 * [JC] This method set the new value to Location textBox
	 * @return
	 */	
	public SearchPage setLocation(String strLocation) {
		locationCmbBox.sendKeys(strLocation); 
		return this;
	}

	/**
	 * [JC] This method set all values for a complete search
	 * @return
	 */
	public SearchPage search(String strName, String strMinimumCap, String strLocation, String resource){
		return clickCollapseAdvancedBtn()
		.setName(strName)
		.setMinimumCap(strMinimumCap)
		.setLocation(strLocation)
		.selectResource(resource);
	}
	
	/**
	 * [JC] This method verify if all fields to search are present
	 * @return
	 */
	public Boolean filtersArePresent(){
		return locationCmbBox.isDisplayed()&&
				minimumCapacityTxtBox.isDisplayed()&&
				roomNameTxtBox.isDisplayed()&&
				listResources.isDisplayed();

	}

	/**
	 * [JC] This method verify if the date are present
	 * @return
	 */
	public Boolean dateIsPresent(){
		Date d = new java.util.Date(Calendar.getInstance().getTimeInMillis());
		String date=new SimpleDateFormat("MMMM d YYY").format(d).toString();
		return dateLabel.getText().replace("th","").replace("st","")
				.replace("nd","").replace("rd","").equals(date);
	}
	
	/**
	 * [JC] This method verify if the dateLabel are displayed
	 * @return
	 */
	public Boolean dateLblIsDisplayed(){
		return dateLabel.isDisplayed();
	}
	
	/**
	 * [JC] This method return the current date displayed
	 * @return
	 */
	public String getTimeLineDate(){
		String time = timeLine.getAttribute("title").replace("th","").replace("st","")
		.replace("nd","").replace("rd","").replace("Current time: ","");
		return time;
	}

	/**
	 * [JC] This method select a room
	 * @return
	 */
	public SchedulePage selectRoom(String roomName) {
		driver.findElement(By.xpath("//button[contains(text(),'" + roomName + "')and@class='ng-scope']")).click();
		return new SchedulePage();
	}

	/**
	 * [JC] This method select a resource
	 * @return
	 */
	public SearchPage selectResource(String resourceName) {
		driver.findElement(By.xpath("//div[contains(text(),'" + resourceName + 
				"')and@class='ng-binding']/preceding-sibling::div")).click();
		return this;
	}

	/**
	 * [JC] This method verify if the rooms in the list are displayed
	 * @return
	 */
	public boolean roomsInList(LinkedList<String> names) {
		boolean allInList=true;
		while(!names.isEmpty()) {
			allInList=driver.findElement(By.xpath("//button[contains(text(),'" + 
					  names.remove(0) + "')and@class='ng-scope']")).isDisplayed();
		}
		return allInList;
	}
	
	/**
	 * [JC] This method verify if a resource are selected
	 * @return
	 */
	public boolean resourceIsSelected(String resourceName) {
		return driver.findElement(By.xpath("//div[contains(text(),'" + 
			   resourceName + "')and@class='ng-binding']/preceding-sibling::div")).isSelected();
	}

	/**
	 * [JC] This method verify if all resources are selected
	 * @return
	 */
	public boolean resourcesAreSelected(LinkedList<String> names) {
		boolean allSelectedInList=false;
		while(!names.isEmpty()) {
			allSelectedInList=driver.findElement(By.xpath("//div[contains(text(),'" + 
							  names.remove(0) + "')and@class='ng-binding']/preceding-sibling::div")).isSelected();
		}
		return allSelectedInList;	
	}

	/**
	 * [JC] This method verify if the resources in the list are displayed
	 * @return
	 */
	public boolean resourcesInList(LinkedList<String> names) {
		boolean allInList=false;
		while(!names.isEmpty()) {
			allInList=driver.findElement(By.xpath("//div[contains(text(),'" + 
					  names.remove(0) + "')and@class='ng-binding']/preceding-sibling::div")).isDisplayed();
		}
		return allInList;	
	}
	
	/**
	 * [JC] allFiltersAreCleaned
	 * @return a boolean answer true if all elements of search are cleaned(without information)
	 */
	public boolean allFiltersAreCleaned() {
		return roomNameTxtBox.getText().equals("")
				&&minimumCapacityTxtBox.getText().equals("")
				&&!locationCmbBox.isSelected();
	}
	
	/**
	 * [YA]This method verifies if an Out Of Order is displayed
	 * @param title: Out Of Order Title
	 * @return boolean
	 */
	public boolean isOutOfOrderBoxDisplayed(String title) {
		return isMeetingBoxDisplayed(title);
	}
	
	/**
	 * [EN] This method verifies if the search title label is displayed in the page
	 * @return
	 */
	public boolean isSearchLblDisplayed() {			
			return searchLbl.isDisplayed();
	}
	
	/**
	 * [YA]This method verifies if a Meeting BOx is displayed
	 * @param meetingSubject: Meeting Subject
	 * @return boolean
	 */
	public boolean isMeetingBoxDisplayed(String meetingSubject) {
		By meetingBoxLocator = By.xpath("//div[contains(text(),'" + meetingSubject + "')]");
		return UIMethods.isElementPresent(meetingBoxLocator); 
	}	
	
	/**
	 * [ML]This method verify if a created resource exist in advanced searchPage from tablet
	 * @param resourceDisplayName
	 * @return boolean
	 */
	public boolean isResourceInAdvancedSearch(String resourceDisplayName) {
		By resource = By.xpath("//*[@id='collapseExample']//div[contains(text(),'" + resourceDisplayName + "')]");
		return UIMethods.isElementPresent(resource);
	}
}

