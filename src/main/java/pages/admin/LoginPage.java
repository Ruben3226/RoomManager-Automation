package main.java.pages.admin;

import static main.java.utils.AppConfigConstants.URL_ADMIN;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import main.java.selenium.SeleniumDriverManager;

/**
 * This class provide methods to login into the system
 * @author Ruben Blanco
 *
 */
public class LoginPage {

	@FindBy(xpath = "//button") 
	WebElement signinBtn;

	private WebDriver driver;

	/**
	 * This constructor initialize the driver and waits
	 */
	public LoginPage() {		
		driver = SeleniumDriverManager.getManager().getDriver();
		driver.get(URL_ADMIN);
		PageFactory.initElements(driver, this);
	}

	/**
	 * This method clicks on signin link
	 * @return
	 */
	public HomeAdminPage clickSigninLink() {		
		signinBtn.click();
		return new HomeAdminPage();
	}

	/**
	 * [ML]This method close browser
	 */
	public void CloseWindow() {
		driver.quit();
	}
}
