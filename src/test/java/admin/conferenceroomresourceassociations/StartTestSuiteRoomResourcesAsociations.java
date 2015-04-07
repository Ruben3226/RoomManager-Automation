package test.java.admin.conferenceroomresourceassociations;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

import main.java.selenium.SeleniumDriverManager;

public class StartTestSuiteRoomResourcesAsociations {
	private WebDriver driver = SeleniumDriverManager.getManager().getDriver();

	@BeforeSuite(groups = {"NEGATIVE","ACCEPTANCE","FUNCTIONAL"})
	public void init() {		
	}

	@AfterSuite(groups = {"NEGATIVE","ACCEPTANCE","FUNCTIONAL"})
	public void afterSuite() {
		driver.quit();
	}
}
