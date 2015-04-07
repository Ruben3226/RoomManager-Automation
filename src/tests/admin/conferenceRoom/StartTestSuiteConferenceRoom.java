package tests.admin.conferenceRoom;

import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

import framework.pages.admin.LoginPage;

public class StartTestSuiteConferenceRoom {
	LoginPage loginPage = new LoginPage();
	
	@BeforeSuite(groups = {"FUNCTIONAL","NEGATIVE","ACCEPTANCE"})
	public void init() {
	}
	
	@AfterSuite(groups = {"FUNCTIONAL","NEGATIVE","ACCEPTANCE"})
	public void afterSuite() {
		loginPage.CloseWindow();
	}
}
