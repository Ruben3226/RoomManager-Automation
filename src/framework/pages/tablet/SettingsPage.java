package framework.pages.tablet;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import framework.selenium.SeleniumDriverManager;

/**
 * @title  Settings
 * @author Jose Cabrera
 * @description This page allows to select a room
 */
public class SettingsPage {

	//declare the instance of Selenium Webdriver
	private WebDriver driver;
	
	@FindBy(xpath = "//button[@ng-click='saveSelectedRoom()']")
	WebElement acceptBtn;
		
	public SettingsPage() {
		driver = SeleniumDriverManager.getManager().getDriver();
		PageFactory.initElements(driver, this);
	}
	
	public HomeTabletPage selectRoom(String roomNum) {
		driver.findElement(By.xpath("//h4[contains(text(),'" + roomNum + "')]")).click();
		acceptBtn.click();
		return new HomeTabletPage();
	}	
	
	public void quit() {
		driver.quit();
	}
}
