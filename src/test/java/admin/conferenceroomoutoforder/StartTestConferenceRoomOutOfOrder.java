package test.java.admin.conferenceroomoutoforder;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

import main.java.selenium.SeleniumDriverManager;

/**
 * This class contains BeforeSuit and AfterSuit methods for conferenceRoomOutOfOrderSuite
 * @author Yesica Acha
 *
 */
public class StartTestConferenceRoomOutOfOrder {
	WebDriver driver = SeleniumDriverManager.getManager().getDriver();

	@BeforeSuite(groups = {"ACCEPTANCE", "FUNCTIONAL", "NEGATIVE", "UI"})
	public void start() {
	}
	
	@AfterSuite(groups = {"ACCEPTANCE", "FUNCTIONAL", "NEGATIVE", "UI"})
	public void end() {
		driver.quit();
	}
}
