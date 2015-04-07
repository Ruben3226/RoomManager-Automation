package main.java.pages.admin.conferencerooms;

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
 * This class represents the Menu for conference rooms, Save and Cancel button
 * @author Yesica Acha
 *
 */
public abstract class RoomBaseAbstractPage {
	protected WebDriver driver;	
	protected WebDriverWait wait;

	@FindBy(linkText = "Room Info") 
	WebElement roomInfoLink;

	@FindBy(linkText = "Resource Associations") 
	WebElement resourceAssociationsLink;

	@FindBy(linkText = "Out of Order Planning") 
	WebElement outOfOrderPlanningLink;

	@FindBy(xpath = "//button[@ng-click='save()']") 
	WebElement saveBtn;

	@FindBy(xpath = "//button[@ng-click='cancel()']") 
	WebElement cancelBtn;

	@FindBy(xpath = "//small[contains(text(),' ')]")
	WebElement errorMessageLbl;

	@FindBy (xpath = "//div[@class='toast-message']/div")
	WebElement messagePopUp;

	@FindBy(xpath = "//div[@class = 'row v-space ng-scope']")
	WebElement background;

	public RoomBaseAbstractPage() {
		driver = SeleniumDriverManager.getManager().getDriver();
		wait = SeleniumDriverManager.getManager().getWait();
		PageFactory.initElements(driver, this);
	}

	public RoomInfoPage clickRoomInfoLink(){
		roomInfoLink.click();
		return new RoomInfoPage();
	}

	public RoomResourceAssociationsPage clickResourceAssociationsLink(){
		wait.until(ExpectedConditions.visibilityOf(resourceAssociationsLink));
		resourceAssociationsLink.click();
		return new RoomResourceAssociationsPage();
	}

	public RoomOutOfOrderPage clickOutOfOrderPlanningLink(){
		wait.until(ExpectedConditions.visibilityOf(outOfOrderPlanningLink));
		outOfOrderPlanningLink.click();
		return new RoomOutOfOrderPage();
	}

	public RoomsPage clickCancelBtn(){
		cancelBtn.click();
		UIMethods.waitForMaskDisappears(background);
		return new RoomsPage();
	}

	public RoomsPage clickSaveBtn(){
		saveBtn.click();
		UIMethods.waitForMaskDisappears(background);
		return new RoomsPage();
	}
	
	/**
	 * [YA]This method verifies if any error message is displayed
	 * @return boolean
	 */
	public boolean isErrorMessagePresent() {
		return errorMessageLbl.isDisplayed();
	}

	/**
	 * [YA]This method verifies if an error message is correct
	 * @return boolean
	 */
	public static boolean isErrorMessageCorrect(String errorMessage) {
		return UIMethods.isElementPresent(By.xpath("//small[contains(text(),'" 
				+ errorMessage + "')]"));
	}
}