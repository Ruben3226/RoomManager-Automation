package main.java.pages.tablet;

import static main.java.utils.AppConfigConstants.URL_TABLET;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import main.java.selenium.SeleniumDriverManager;
import main.java.selenium.UIMethods;

/**
 * This class represent the home page of tablet.
 * This page is divided in the following sections:
 * Now tile,  New tile, Now tape, New tape, Time line.
 * Also has the buttons: Settings, Schedule, Search.
 * @author Eliana Navia 
 */
public class HomeTabletPage {
	private WebDriver driver;
	private WebDriverWait wait;

	@FindBy(xpath = "//div[@ng-bind='current._title']")
	WebElement nowTileLbl;

	@FindBy(xpath = "//div[@ng-bind='next._title']")
	WebElement nextTileLbl; 

	@FindBy(xpath = "//div[@ng-bind='current._organizer']")
	WebElement currentMeetingOrganizerLbl;

	@FindBy(xpath = "//div[@ng-bind='next._organizer']")
	WebElement nextMeetingOrganizerLbl;

	@FindBy(xpath = "//div[contains(@class,'timeleft-remaining')]")
	WebElement timeLeftLbl;

	@FindBy(xpath = "//span[contains(@ng-bind,'next._start')]")
	WebElement timeNextStartLbl;

	@FindBy(xpath = "//span[contains(@ng-bind,'next._end')]")
	WebElement timeNextEndLbl;

	@FindBy(xpath = "//div[@class='currenttime']")
	WebElement currentTimeLine;

	@FindBy(xpath = "//span[@ng-bind='currentTime']")
	WebElement currentTimeLbl;

	@FindBy(xpath = "//div[@ng-class='resource.icon']")
	WebElement resourceIcon;

	@FindBy(xpath = "//div[@ng-bind='resource.name']")
	WebElement resourceNameLbl;

	@FindBy(xpath = "//div[@ng-bind='resource.quantity']")
	WebElement resourceQuantityLbl;

	@FindBy(xpath = "//span[@ng-bind='room._customDisplayName']")
	WebElement roomDisplayNameLbl;

	@FindBy(xpath = "//div[@ng-click='goToSearch()']")
	WebElement searchBtn;

	@FindBy(xpath = "//div[@ng-click='goToSettings()']")
	WebElement settingsBtn;

	@FindBy(xpath = "//div[@ng-click='goToSchedule()']")
	WebElement scheduleBtn;

	@FindBy(id = "timeline-container") 
	WebElement timelineContainer; 

	@FindBy(xpath = "//span[@ng-bind = 'room._code']")
	WebElement roomCodeLbl;

	@FindBy(xpath = "//div[@ng-click='selectNowTile()']")
	WebElement nowTileBox;

	@FindBy(xpath = "//div[@ng-click='selectNextTile()']")
	WebElement nextTileBox;

	public HomeTabletPage() {
		driver = SeleniumDriverManager.getManager().getDriver();
		wait = SeleniumDriverManager.getManager().getWait();
		PageFactory.initElements(driver, this);
		if (!driver.getCurrentUrl().equals(URL_TABLET + "/#/home")) {
			driver.get(URL_TABLET);
		} 
	}

	/**
	 * [EN] Return the value of {Now} tile
	 * @return String it could be meeting subject or Available
	 */
	public String getNowTileLbl() {
		wait.until(ExpectedConditions.elementToBeClickable(nowTileLbl));
		return nowTileLbl.getText();
	}

	/**
	 * [EN] Return the value of time left on {Now} tape
	 * @return String 
	 */
	public String getTimeLeftLbl() {
		return timeLeftLbl.getText();
	}

	/**
	 * [EN] Return the value of {Next} tile
	 * @return String it could be next meeting subject or End of day
	 */
	public String getNextTileLbl() {
		wait.until(ExpectedConditions.elementToBeClickable(nextTileLbl));
		return nextTileLbl.getText();
	}

	/**
	 * [EN] Return the value of next meeting organizer that is displayed on {Next} tile.
	 * @return String
	 */
	public String getNextMeetingOrganizerNameLbl() {
		return nextMeetingOrganizerLbl.getText();
	}

	/**
	 * [EN] Return start time of next meeting set in the room that is displayed on {Next} tile.
	 * @return
	 */
	public String getStartTimeNextMeetingLbl() {
		return timeNextStartLbl.getText();
	}

	/**
	 * [EN] Return end time of next meeting set in the room that is displayed on {Next} tile.
	 * @return
	 */
	public String getEndTimeNextMeetingLbl() {
		return timeNextEndLbl.getText();
	}

	/**
	 * [EN] Return display name of the room displayed on the top of the main window.
	 * @return
	 */
	public String getRoomDisplayNameLbl() {
		wait.until(ExpectedConditions.visibilityOf(roomDisplayNameLbl));
		return roomDisplayNameLbl.getText();
	}

	/**
	 * [EN] Return Search page when {Search} button is clicked.
	 * @return
	 */
	public SearchPage clickSearchBtn() {
		wait.until(ExpectedConditions.elementToBeClickable(searchBtn));
		wait.until(ExpectedConditions.visibilityOf(timelineContainer));
		searchBtn.click();
		return new SearchPage();
	}

	/**
	 * [EN] Return Setting page when {Settings} button is clicked.
	 * @return
	 */
	public SettingsPage clickSettingsBtn() {

		//If current page is not Settings
		if (!UIMethods.isElementDisplayed(By.xpath("//button[@ng-click='saveSelectedRoom()']"))) {
			settingsBtn.click();
		}
		return new SettingsPage();
	}

	/**
	 * [EN] This method clicks {Now} tile.
	 * @return schedule page
	 */
	public SchedulePage clickNowTileLbl() {
		wait.until(ExpectedConditions.elementToBeClickable(nowTileLbl));
		nowTileLbl.click();
		return new SchedulePage();
	}

	/**
	 * [RB]This method gets the room code of selected room
	 * @return 
	 */
	public String getRoomCodeLbl() {
		return roomCodeLbl.getText();
	}

	/**
	 * [RB]This method verifies if an associated resource (display name and quantity
	 * is displayed in the tablet home page)
	 * @param resourceName
	 * @param amount
	 * @return
	 */
	public boolean isResourceAssociated(String resourceName, String amount) {

		//this condition call other methods to verify if a elements are present in the tablet
		if (isResourceQuantityDisplayed(amount)&&isResourceNameDisplayed(resourceName))
			return true;
		else
			return false;
	}

	/**
	 * [RB]This method verifies the display name of an associated resource
	 * is displayed in the tablet home page
	 * @param amount
	 * @return
	 */
	private boolean isResourceQuantityDisplayed(String amount) {
		return driver.findElement(By.xpath("//div[contains(text(),'"+ amount +
				"')and@ng-bind='resource.quantity']")).isDisplayed();
	}

	/**
	 * [RB]This method verifies the quantity of an associated resource
	 * is displayed in the tablet home page
	 * @param resourceDisplayName
	 * @return
	 */
	private boolean isResourceNameDisplayed(String resourceDisplayName) {
		try{
			return driver.findElement(By.xpath("//div[contains(text(),'"+resourceDisplayName+
					"')and@ng-bind='resource.name']")).isDisplayed();
		}catch (Exception e) {
			return false;
		}
	}

	public boolean isHomePageDisplayed() {
		return scheduleBtn.isDisplayed();
	}

	/**
	 * [JC] This method verify return the current date
	 * @return
	 */
	public String getTimeLineDate() {
		String time = currentTimeLine.getAttribute("title").replace("th","").replace("st","")
				.replace("nd","").replace("rd","").replace("Current time: ","");
		return time;
	}

	/**
	 * [YA] This method clicks Schedule button and waits until timeline is displayed
	 * @return SchedulePage
	 */
	public SchedulePage clickScheduleBtn() {
		wait.until(ExpectedConditions.visibilityOf(timelineContainer));
		scheduleBtn.click();
		return new SchedulePage();
	}

	/**
	 * [EN] Return the current time displayed in the top of main window.
	 * @return
	 */
	public String getCurrentTimeLbl() {
		return currentTimeLbl.getText();
	}

	/**
	 * [EN] Return the current meeting organizer displayed on {Now} tile.
	 * @return
	 */
	public String getCurrentMeetingOrganizerLbl() {
		return currentMeetingOrganizerLbl.getText();
	}

	/**
	 * [EN] This method clicks the time line container displayed in the bottom of main window
	 * @return Schedule Page
	 */
	public SchedulePage clickTimelineContainer() {
		timelineContainer.click();
		return new SchedulePage();
	}

	/**
	 * [YA] This method verifies if timeline container is displayed
	 * @return boolean
	 */
	public boolean isTimelineContainerPresent() {
		By timelineContainerLocator = By.id("timeline-container");
		return UIMethods.isElementPresent(timelineContainerLocator);
	}

	/**
	 * [RB]This method verifies if an associated resource (display name and quantity
	 * is displayed in the tablet home page)
	 * @param resourceName
	 * @param amount
	 * @return
	 */
	public boolean VerifyResourceIsAsociated(String resourceName, String amount) {

		//this condition call other methods to verify if a elements are present in the tablet
		if (isQuantityDisplayed(amount)&&isResourceNameDisplayed(resourceName))
			return true;
		else
			return false;
	}

	/**
	 * [RB]This method verifies the display name of an associated resource
	 * is displayed in the tablet home page
	 * @param amount
	 * @return
	 */
	private boolean isQuantityDisplayed(String amount) {
		try{
			return driver.findElement(By.xpath("//div[contains(text(),'"+amount
					+"')and@ng-bind='resource.quantity']")).isDisplayed();
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * [EN] return the color of now tile in RGBA format.
	 * @return String
	 */
	public String getNowTileColor() {
		return nowTileBox.getCssValue("background-color");
	}

	/**
	 * [EN] return the color of next tile in RGBA format.
	 * @return String
	 */
	public String getNextTileColor() {
		return nextTileBox.getCssValue("background-color");
	}
}
