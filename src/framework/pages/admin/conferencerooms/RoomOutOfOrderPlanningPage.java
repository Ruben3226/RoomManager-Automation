package framework.pages.admin.conferencerooms;

import static framework.common.MessageConstants.OUT_OF_ORDER_IN_THE_PAST;
import static framework.common.MessageConstants.OUT_OF_ORDER_SHOULD_HAVE_A_TITLE;
import static framework.common.MessageConstants.TO_GRATER_THAN_FROM;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

import framework.common.UIMethods;
import framework.utils.TimeManager;

/**
 * This class represents Conference Room's Our Of Order Planning page in Admin.
 * @author Yesica Acha
 *
 */
public class RoomOutOfOrderPlanningPage extends RoomBaseAbstractPage {
	@FindBy(xpath = "//div[@class='check-button']")
	WebElement activationBtn;

	@FindBy(xpath = "//label[@for='title-dropdown']")
	WebElement titleCmbBox;

	@FindBy(xpath = "//input[@ng-model='form.title.value']")
	WebElement titleTxtBox;

	@FindBy(xpath = "//textarea[@ng-model='form.description.value']")
	WebElement descriptionTxtBox;

	@FindBy(xpath = "//span[contains(text(),'Send email notification')]")
	WebElement emailNotificationChkBox;

	@FindBy(xpath = "//small[contains(text(),' ')]")
	WebElement errorMessageLbl;

	/**
	 *[YA]This method sets the title for an Out Of Order Planning Period
	 * @param title
	 * @return RoomOutOfOrderPlanningPage
	 */
	public RoomOutOfOrderPlanningPage setTitleTxtBox (String title) {
		titleTxtBox.clear();
		titleTxtBox.sendKeys(title);
		return this;
	}

	/**
	 *[YA]This method sets the description for an Out Of Order Planning Period
	 * @param description
	 * @return RoomOutOfOrderPlanningPage
	 */
	public RoomOutOfOrderPlanningPage setDescriptionTxtBox (String description) {
		descriptionTxtBox.clear();
		descriptionTxtBox.sendKeys(description);
		return this;
	} 

	/**
	 *[YA]This method selects a checkBox to send an email notification to attendees
	 * that have a meeting scheduled when an out of order period is established
	 * @return RoomOutOfOrderPlanningPage
	 */
	public RoomOutOfOrderPlanningPage selectEmailNotificationChkBox() {
		emailNotificationChkBox.click();
		return this;
	}

	/**
	 *[YA]This method clicks the activation button (calendar or clock) to confirm an out of order period
	 * @return RoomOutOfOrderPlanningPage
	 */
	private RoomOutOfOrderPlanningPage clickActivationBtn() {
		activationBtn.click();
		return this;
	}

	/**
	 *[YA]This method sets a start date (DD-MM-YYYY)
	 * @param date
	 * @return RoomOutOfOrderPlanningPage
	 */
	public RoomOutOfOrderPlanningPage setStartDateWithCalendar(String date) {
		setDateWithCalendar(date, "from");
		return this;
	}

	/**
	 *[YA]This method sets an end date (DD-MM-YYYY)
	 * @param date
	 * @return RoomOutOfOrderPlanningPage
	 */
	public RoomOutOfOrderPlanningPage setEndDateWithCalendar(String date) {
		setDateWithCalendar(date, "to");
		return this;
	}

	/**
	 *[YA]This method sets a date (start or end) in the application using the calendar
	 * @param date
	 * @param dateSelector
	 */
	private void setDateWithCalendar(String modifierValue, String dateSelector) {

		//to click the calendar button next to date textBox
		clickCalendarElementBtn("calendarBtn", dateSelector);

		//to clicks date-picker button (month) as a precondition
		clickCalendarElementBtn("datePickerBtn", dateSelector);

		//to click date=picker button (year) as a precondition 
		clickCalendarElementBtn("datePickerBtn", dateSelector);

		//to get date adding days 
		String date = TimeManager.getDate(Integer.parseInt(modifierValue), "MMM dd YYYY");

		//to click date's year button
		clickCalendarElementBtn(TimeManager.getDateElement(date, "year"), dateSelector);

		//to click date's month button
		clickCalendarElementBtn(TimeManager.getDateElement(date, "month"), dateSelector);

		//to click date's day button
		clickCalendarElementBtn(TimeManager.getDateElement(date, "day"), dateSelector);
	}

	/**
	 *[YA]This method clicks on an element of the calendar (day, month, year, todayBtn, 
	 * cancelBtn, clearBtn, etc)
	 * @param calendarElement
	 * @param dateSelector
	 */
	private void clickCalendarElementBtn(String calendarElement, String dateSelector) {
		String locator;
		switch (calendarElement) {
		case "calendarBtn":  locator = "div/span/button";
		break;
		case "datePickerBtn":  locator = "th/button[@ng-click='toggleMode()']";
		break;		
		case "previousBtn":  locator = "th/button[@ng-click='toggleMode()']/ancestor::"
				+ "th/preceding-sibling::th//button";
		break;
		case "nextBtn":  locator = "th/button[@ng-click='toggleMode()']/ancestor::"
				+ "th/following-sibling::th//button";
		break;
		case "todayBtn":  locator = "button[contains(text(),'Today')]";
		break;
		case "clearBtn":  locator = "button[contains(text(),'Clear')]";
		break;
		case "closeBtn":  locator = "button[contains(text(),'Close')]";
		break;
		default: locator = "span[contains(text(),'" + calendarElement + "')]/ancestor::td/button";
		break;
		}
		WebElement calendarElementBtn = driver.findElement(By.xpath("//date-picker[@model='form." 
				+ dateSelector + ".value']//" + locator));
		calendarElementBtn.click();
	}

	/**
	 *[YA]This method sets a star time (HH:MM AM/PM) for out of order planning period
	 * @param startTime
	 * @return RoomOutOfOrderPlanningPage
	 */
	public RoomOutOfOrderPlanningPage setStartTime (String startTime) {
		setTime(startTime, "from");
		return this;
	}

	/**
	 *[YA]This method sets an end time (HH:MM AM/PM) for out of order planning period
	 * @param endTime
	 * @return RoomOutOfOrderPlanningPage
	 */
	public RoomOutOfOrderPlanningPage setEndTime (String endTime) {
		setTime(endTime, "to");
		return this;
	}

	/**
	 *[YA]This method sets a time (start or end) for out of order planning period
	 * @param time
	 * @param timeSelector
	 */
	private void setTime (String modifierValue, String timeSelector) {
		int minutesToAdd = Integer.parseInt(modifierValue);
		String time = TimeManager.getTime(minutesToAdd, "hh:mm a");
		String hours = TimeManager.getTimeElement(time,"hours");
		String minutes = TimeManager.getTimeElement(time, "minutes");
		String meridian = TimeManager.getTimeElement(time,"meridian");
		setTimeTxtBox("hours", hours, timeSelector);
		setTimeTxtBox("minutes", minutes, timeSelector);
		clickMeridianBtn(meridian, timeSelector);		
	}

	/**
	 *[YA]This method set the time (hours or minutes) in the corresponding textBox
	 * @param selector
	 * @param time
	 * @param timeSelector
	 */
	private void setTimeTxtBox (String selector, String time, String timeSelector) {
		WebElement timeTxtBox = findDateElement(selector, timeSelector);
		UIMethods.doubleClick(timeTxtBox);
		timeTxtBox.sendKeys(time);
	}

	/**
	 *[YA]This method sets the meridian (AM or PM)for the out of order time
	 * @param meridian
	 * @param timeSelector
	 */
	private void clickMeridianBtn (String meridian, String timeSelector) {
		WebElement meridianBtn = findDateElement("meridian", timeSelector);
		if(!meridianBtn.getText().equalsIgnoreCase(meridian)) {
			meridianBtn.click();
		}
	}

	/**
	 *[YA]This method finds hoursTxtBox, minutesTxtBox, or meridianTxtBox based on the parameters
	 * @param element
	 * @param timeSelector
	 * @return WebElement
	 */
	private WebElement findDateElement(String element, String timeSelector) {
		String locator;
		if(element == "meridian") {
			locator = "button[@ng-click='toggleMeridian()']";
		} else {
			locator = "input[@ng-model='" + element + "']";
		}
		WebElement timeElement = driver.findElement(By.xpath("//table[@ng-model='form." 
				+ timeSelector + ".value']//" + locator));
		return timeElement;
	}

	/**
	 *[YA]This method clicks the arrow to increment hours in start time
	 * @return RoomOutOfOrderPlanningPage
	 */
	public RoomOutOfOrderPlanningPage clickStartTimeIncrementHoursBtn() {
		clickArrowBtn("from", "incrementHours");
		return this;
	}

	/**
	 *[YA]This method clicks the arrow to decrement hours in start time
	 * @return RoomOutOfOrderPlanningPage
	 */
	public RoomOutOfOrderPlanningPage clickStartTimeDecrementHoursBtn() {
		clickArrowBtn("from", "decrementHours");
		return this;
	}

	/**
	 *[YA]This method clicks the arrow to increment minutes in start time
	 * @return RoomOutOfOrderPlanningPage
	 */
	public RoomOutOfOrderPlanningPage clickStartTimeIncrementMinutesBtn() {
		clickArrowBtn("from", "incrementMinutes");
		return this;
	}

	/**
	 * [YA]This method clicks the arrow to decrement minutes in start time
	 * @return RoomOutOfOrderPlanningPage
	 */
	public RoomOutOfOrderPlanningPage clickStartTimeDecrementMinutesBtn() {
		clickArrowBtn("from", "decrementMinutes");
		return this;
	}

	/**
	 * [YA]This method clicks the arrow to increment hours in end time
	 * @return RoomOutOfOrderPlanningPage
	 */
	public RoomOutOfOrderPlanningPage clickEndTimeIncrementHoursBtn() {
		clickArrowBtn("to", "incrementHours");
		return this;
	}

	/**
	 * [YA]This method clicks the arrow to increment hours in end time
	 * @return RoomOutOfOrderPlanningPage
	 */
	public RoomOutOfOrderPlanningPage clickEndTimeDecrementHoursBtn() {
		clickArrowBtn("to", "decrementHours");
		return this;
	}

	/**
	 * [YA]This method clicks the arrow to increment minutes in end time
	 * @return RoomOutOfOrderPlanningPage
	 */
	public RoomOutOfOrderPlanningPage clickEndTimeIncrementMinutesBtn() {
		clickArrowBtn("to", "incrementMinutes");
		return this;
	}

	/**
	 * [YA]This method clicks the arrow to decrement minutes in end time
	 * @return RoomOutOfOrderPlanningPage
	 */
	public RoomOutOfOrderPlanningPage clickEndTimeDecrementMinutesBtn() {
		clickArrowBtn("to", "decrementMinutes");
		return this;
	}

	/**
	 * [YA]This method clicks an arrow to change hours or minutes 
	 * @param timeSelector
	 * @param action
	 * @return RoomOutOfOrderPlanningPage
	 */
	private RoomOutOfOrderPlanningPage clickArrowBtn(String timeSelector, String action) {
		WebElement calendarElementBtn = driver.findElement(By.xpath("//table[@ng-model='form." 
				+ timeSelector + ".value']//a[@ng-click='" + action + "()']/span"));
		calendarElementBtn.click();
		return this;
	}

	/**
	 * [YA]This method finds date TextBox for Start Date or End date depending on timeSelector
	 * @param timeSelector
	 * @return WebElement
	 */
	private WebElement findDateTxtBox(String timeSelector) {
		By dateTxtBoxLocator = By.xpath("//date-picker[@model='form." + timeSelector + ".value']//input");
		return driver.findElement(dateTxtBoxLocator);
	}

	/**
	 * [YA]This method returns the date displayed in DateTextBox
	 * @param dateSelector
	 * @return String
	 */
	private String getDateValue(String dateSelector) {
		String date = findDateTxtBox("from").getAttribute("value");
		String[] dateElements = date.split(" ");
		String newDate = dateElements[1] + " " + dateElements[2] + " " + dateElements[3];
		return newDate;
	}

	/**
	 * [YA]This method returns the Start date displayed in DateTextBox
	 * @return String
	 */
	public String getStartDateValue() {
		return getDateValue("from");
	}

	/**
	 * [YA]This method returns the End date displayed in DateTextBox
	 * @return String
	 */
	public String getEndDateValue() {
		return getDateValue("to");
	}	

	/**
	 * [YA]This method sets all the information for an Out Of Order Period to be created
	 * @param startingDate
	 * @param finishingDate
	 * @param startingTime
	 * @param finishingTime
	 * @param title
	 * @param description
	 * @return RoomOutOfOrderPlanningPage
	 */
	public RoomOutOfOrderPlanningPage setOutOfOrderPeriodInformation(String startDate, String endDate, 
			String startTime, String endTime, String title, String description) {
		setTitleTxtBox(title);
		setEndTime(endTime);
		setStartTime(startTime);
		setDescriptionTxtBox(description);
		String currentDate = TimeManager.getCurrentDate("MMM dd YYYY");
		if (!(Integer.parseInt(startDate) == 0) && (getStartDateValue().equals(currentDate))) {
			setStartDateWithCalendar(startDate);
		}
		if(!(Integer.parseInt(endDate)== 0) && (getEndDateValue().equals(currentDate))) {
			setEndDateWithCalendar(endDate);
		}
		return this;
	}

	/**
	 * [YA]This method activates an Out Of Order if it is deactivated 
	 * @return RoomOutOfOrderPlanningPage
	 */
	public RoomOutOfOrderPlanningPage activateOutOfOrder(){
		if(!isOutOfOrderActivated()) {
			clickActivationBtn();
		}
		return this;
	}

	/**
	 * [YA]This method deactivates an Out Of Order if it is activated
	 * @return RoomOutOfOrderPlanningPage
	 */
	public RoomOutOfOrderPlanningPage deactivateOutOfOrder(){
		if(isOutOfOrderActivated()) {
			clickActivationBtn();
		}
		return this;
	}

	/**
	 * [YA]This method verifies that a message that says: "'To' field must be greater than 'From' field" 
	 * is displayed
	 * @return boolean
	 */
	public boolean isToGreaterThanFromErrorDisplayed() {
		return isErrorMessageCorrect(TO_GRATER_THAN_FROM);
	}

	/**
	 * [YA]This method verifies that a message that says: "Cannot establish out of order as a past event"
	 * is displayed
	 * @return boolean
	 */
	public boolean isOutOfOrderInThePastErrorDisplayed() {
		return isErrorMessageCorrect(OUT_OF_ORDER_IN_THE_PAST);
	}

	/**
	 * [YA]This method verifies that a message that says: "Cannot establish out of order as a past event"
	 * is displayed
	 * @return boolean
	 */
	public boolean isOutOfOrderShouldHaveTitleErrorDisplayed() {
		return isErrorMessageCorrect(OUT_OF_ORDER_SHOULD_HAVE_A_TITLE);
	}

	/**
	 * [YA]This method returns the default value should be set for start time
	 * @return String
	 */
	public String getDefaultStartTimeValue() {
		String currentTime = TimeManager.getCurrentDate("hh:mm a");
		return TimeManager.getNextHalfHourPeriod(currentTime);
	}

	/**
	 * [YA]This method returns the default value should be set for end time
	 * @return
	 */
	public String getDefaultEndTimeValue() {
		return TimeManager.getNextHalfHourPeriod(getDefaultStartTimeValue());
	}

	/**
	 * [YA]This method gets the time (hours and minutes) set as start time or end time 
	 * depending on the selector
	 * @param selector
	 * @return String
	 */
	private String getTimeValue(String selector) {
		String hours = findDateElement("hours", selector).getAttribute("value");
		String minutes = findDateElement("minutes", selector).getAttribute("value"); 
		return hours + ":" + minutes;
	}

	/**
	 * [YA] This methods gets the value in hour TextBox or minute TextBox
	 * @param selector
	 * @param element
	 * @return String
	 */
	private String getTimeValue(String selector, String element) {
		return findDateElement(element, selector).getAttribute("value");
	}

	/**
	 * [YA] This method gets the value in hour TextBox of Start time
	 * @return String
	 */
	public String getStartHourValue() {
		return getTimeValue("from", "hours");
	}

	/**
	 * [YA] This method gets the value in minute TextBox of Start time
	 * @return
	 */
	public String getStartMinuteValue() {
		return getTimeValue("from", "minutes");
	}

	/**
	 * [YA] This method gets the value in hour TextBox of End time
	 * @return String
	 */
	public String getEndHourValue() {
		return getTimeValue("to", "hours");
	}

	/**
	 * [YA] This method gets the value in minute TextBox of End time
	 * @return String
	 */
	public String getEndMinuteValue() {
		return getTimeValue("to", "minutes");
	}

	/**
	 * [YA]This method gets the time (hours and minutes) set as start time
	 * @return String
	 */
	public String getStartTimeValue() {
		return getTimeValue("from");
	}

	/**
	 * [YA]This method gets the time (hours and minutes) set as end time
	 * @return String
	 */
	public String getEndTimeValue() {
		return getTimeValue("to");
	}

	/**
	 * [YA]This method clicks saveBtn when an Out Of Order is created
	 * @return RoomsPage
	 */
	public RoomsPage clickSaveOutOfOrderBtn(){
		clickSaveBtn();
		wait.until(ExpectedConditions.visibilityOf(messagePopUp));
		if(messagePopUp.isDisplayed()){
			messagePopUp.click();
		}
		return new RoomsPage();
	}

	/**
	 * [YA]This method gets Out Of Order's title
	 * @return String
	 */
	public String getTitleValue() {
		return titleTxtBox.getAttribute("value");
	}

	/**
	 * [YA]This method gets Out Of Order's description
	 * @return String
	 */
	public String getDescriptionValue() {
		return descriptionTxtBox.getAttribute("value");
	}

	/**
	 * [YA] This method verifies if Out Of Order is Activated
	 * @return boolean
	 */
	public boolean isOutOfOrderActivated() {
		return !emailNotificationChkBox.getAttribute("class").contains("text-disabled-color");
	}

	/**
	 * [YA] This method clicks Save button when an error message is expected and 
	 * it should stay in the same page
	 * @param errorMessage
	 * @return RoomOutOfOrderPlanningPage
	 */
	public RoomOutOfOrderPlanningPage clickSaveWithErrorBtn() {
		saveBtn.click();
		wait.until(ExpectedConditions.visibilityOf(errorMessageLbl));
		return this;
	}
}