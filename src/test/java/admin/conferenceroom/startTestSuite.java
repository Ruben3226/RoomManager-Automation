package test.java.admin.conferenceroom;

import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

import main.java.pages.admin.LoginPage;

public class startTestSuite {

	private LoginPage loginPage = new LoginPage();

	@BeforeSuite(groups = {"FUNCTIONAL","NEGATIVE","ACCEPTANCE"})
	public void init() {
	}

	@AfterSuite(groups = {"FUNCTIONAL","NEGATIVE","ACCEPTANCE"})
	public void afterSuite() {
		loginPage.CloseWindow();
	}
}
