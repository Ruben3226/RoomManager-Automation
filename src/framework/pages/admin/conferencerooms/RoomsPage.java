package framework.pages.admin.conferencerooms;

import static framework.common.MessageConstants.OUT_OF_ORDER_SUCCESSFULLY_CREATED;

import java.io.IOException;
import java.net.MalformedURLException;

import org.json.JSONException;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

import framework.common.UIMethods;
import framework.pages.admin.AbstractMainMenu;
import framework.rest.RootRestMethods;

/**
 * This class represents Conference room page
 * @author Ruben Blanco
 *
 */
public class RoomsPage extends AbstractMainMenu {

	@FindBy(id = "roomsGrid")
	WebElement roomsGrid;

	@FindBy (xpath = "//div[@class='toast-message']/div")
	WebElement messagePopUp;
	
	@FindBy(xpath = "//div[@class='ngGroupPanel']")
	WebElement container;

	@FindBy (xpath = "//span[@class='ngAggregateText ng-binding']")
	WebElement disableRoomsLbl;

	@FindBy (xpath = "//span[contains(text(),'true')]")
	WebElement enabledRoomsLbl;

	/**
	 * [RB]Click over Room 
	 * @param displayName is the display name of a Room
	 * @return
	 */
	public RoomInfoPage doubleClickOverRoomName(String displayName) {
		UIMethods.doubleClick(driver.findElement(By.xpath("//span[contains(text(),'" 
				+ displayName + "')and@class='ng-binding']")));
		return new RoomInfoPage();
	}

	/**
	 * [RB]Get the display name of a Room
	 * @param displayName
	 * @return 
	 */
	public String getRoomDisplayName(String roomName) {
		return driver.findElement(By.xpath("//span[contains(text(),'" 
				+ roomName + "')and@class='ng-binding']")).getText();
	}

	/**
	 * [RB]This method disables a selected room
	 * @param roomDisplayName
	 * @return ConferenceRoomPage object
	 */
	public Object enableDisableIcon(String roomDisplayName) {
		String xpathElement = "//span[contains(text(),'"+roomDisplayName
				+ "')]//ancestor::div[@ng-click='row.toggleSelected($event)']//span";
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath(xpathElement)));
		driver.findElement(By.xpath(xpathElement)).click();
		return this;
	}

	/**
	 * [YA] this method verifies if the icon of Out Of Order is displayed
	 * @param roomName
	 * @return
	 */
	public boolean isOutOfOrderIconDisplayed(String roomName) {
		return 	findOutOfOrderIcon(roomName).isDisplayed();
	}

	/**
	 * [YA]This method returns the icon is displayed in Out Of Order Column when an Out Of Order 
	 * Period is established
	 * @param roomDisplayName
	 * @return
	 */
	public String getOutOfOrderIconClass(String roomName) {
		WebElement outOfOrderIcon = findOutOfOrderIcon(roomName);
		return outOfOrderIcon.getAttribute("class");
	}

	/**
	 * [YA]This method finds Out Of Order Icon
	 * @param roomName
	 * @return WebElement
	 */
	private WebElement findOutOfOrderIcon(String roomName) {
		return driver.findElement(By.xpath("//span[contains(text(),'" 
				+ roomName + "')]//ancestor::div[@ng-click='row.toggleSelected($event)']"
				+ "//out-of-order-icon//span"));
	} 

	/**
	 * [YA]This method clicks outOfOrderIcon
	 * @param roomName
	 * @return
	 */
	public RoomsPage clickOutOfOrderIcon(String roomName) {
		WebElement outOfOrderIcon = findOutOfOrderIcon(roomName);
		String outOfOrderClass = getOutOfOrderIconClass(roomName);
		String action;
		if(outOfOrderClass.contains("calendar")) {
			action = "waiting";
		} else {
			action = "running";
		}
		outOfOrderIcon.click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(
				By.xpath("//span[contains(text(),'" + roomName + "')]//ancestor::div[@ng-click="
						+ "'row.toggleSelected($event)']//div[@ng-switch-when='" + action + "']")));
		return this;
	}

	/**
	 * [YA]This method verifies if a message is displayed and clicks on the message to make it 
	 * disappear.
	 * @return boolean
	 */
	public boolean isMessagePresent() {
		boolean messageDisplayed = messagePopUp.isDisplayed();
		if (messageDisplayed == true) {
			messagePopUp.click();
		}
		return messageDisplayed;
	}

	/**
	 * [YA]This method that verifies if a message is correct
	 * @return boolean
	 */
	private boolean isMessageCorrect(String message) {
		return UIMethods.isElementPresent(By.xpath("//div[contains(text(),'" 
				+ message + "')]"));
	}

	/**
	 * [YA]This method verifies that a message that says: "Out of order was created successfully"
	 * is displayed
	 * @return boolean
	 */
	public boolean isOutOfOrderSuccessfullyCreatedMessageDisplayed() {
		return isMessageCorrect(OUT_OF_ORDER_SUCCESSFULLY_CREATED);
	}

	/**
	 * [CG]Method that returns true when the search of a resource in the top of conference rooms 
	 * page icons is successful  
	 * @param resourceName
	 * @return
	 */
	public boolean searchResource(String resourceName) {
		return driver.findElement(By.xpath("//span[contains(text(),'" + resourceName 
				+ "')and@class='ng-binding']")).isDisplayed();	
	}

	/**
	 * [CG]Method that clicks the resource icon in the top of rooms page
	 * @param resourceName
	 * @return
	 */
	public RoomsPage clickResourceIcon(String resourceName) {
		String locator = "//span[contains(text(),'" + resourceName + "')and@class='ng-binding']";
		wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(locator)));
		driver.findElement(By.xpath(locator)).click();
		return this;
	}

	/**
	 * [CG]Method that returns true when the search of a resource in the top of rooms table 
	 * header is successful
	 * @param resourceName
	 * @return
	 */
	public boolean isResourcePresentInTableHeader(String resourceName) {
		String locator = "//div[@class='ngHeaderScroller']//div[contains(text(),'" 
				+ resourceName + "')]";
		wait.until(ExpectedConditions.textToBePresentInElementLocated(By.xpath(locator), 
				resourceName));
		return UIMethods.isElementPresent(By.xpath(locator));
	}

	/**
	 * [CG]Method that allows to verify if a room is disabled
	 * @param roomName
	 * @return true or false if the button is enabled or disabled
	 */
	public boolean isRoomDisabled(String roomName) {
		By enableDisableBtnLocator = By.xpath("//span[contains(text(),'" + roomName 
				+ "')]//ancestor::div[@ng-click='row.toggleSelected($event)']//div[@ng-if='"
				+ "(row.entity.enabled == false)']");
		return UIMethods.isElementPresent(enableDisableBtnLocator );
	}
	
	/**
	 * [CG]Method that allows to verify if a room is enabled
	 * @param roomName
	 * @return
	 */
	public boolean isRoomEnabled(String roomName) {
		By enableDisableBtnLocator = By.xpath("//span[contains(text(),'" + roomName 
				+ "')]//ancestor::div[@ng-click='row.toggleSelected($event)']//div[@ng-if='"
				+ "(row.entity.enabled == true)']");
		return UIMethods.isElementPresent(enableDisableBtnLocator );
	}

	/**
	 * [YA] This method waits for any message to be displayed and clicks it
	 * @return RoomsPage
	 */
	public RoomsPage waitForMessage() {
		wait.until(ExpectedConditions.visibilityOf(messagePopUp));
		messagePopUp.click();
		return this;
	}

	/**
	 * [CG]Method that returns the resource value from resources grid
	 * @param resourceName
	 * @return
	 */
	public String getResourceQuantity(String resourceName) {
		return driver.findElement(By.xpath("//span[contains(text(),'" + resourceName + "')]"
				+ "/ancestor::div/following-sibling::div[@class='ngCell centeredColumn col3 colt3']"
				+ "//span[@class='ng-binding']/parent::div")).getText();
	}

	/**
	 * [RB]This method use the rootRestClass to verify the made changes
	 * @return true if the roomDisplay was modified 
	 * @throws IOException 
	 * @throws MalformedURLException 
	 * @throws JSONException 
	 */
	public boolean verifyChangesMade(String ChangedDisplayName) throws JSONException, MalformedURLException, IOException {
		boolean flag = false;
		for (String displayName : RootRestMethods.getAllDisplayNameRooms()) {
			if (ChangedDisplayName.equals(displayName)) {
				flag = true;
			}
		}
		return flag;
	}
}
