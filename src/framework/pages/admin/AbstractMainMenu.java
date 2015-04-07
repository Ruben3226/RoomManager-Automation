package framework.pages.admin;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

import framework.pages.admin.conferencerooms.RoomsPage;
import framework.pages.admin.resources.ResourcesPage;
import framework.selenium.SeleniumDriverManager;

/**
 * This class contains the main menu
 * @author Ruben Blanco
 *
 */
public abstract class AbstractMainMenu {
	protected WebDriver driver;	
	protected WebDriverWait wait;

	@FindBy(linkText = "Room Manager") 
	WebElement homeLink;

	@FindBy(linkText = "Email Servers") 
	WebElement emailServersLink;

	@FindBy(linkText = "Impersonation") 
	WebElement impersonationLink;

	@FindBy(linkText = "Conference Rooms") 
	WebElement conferenceRoomsLink;

	@FindBy(linkText = "Resources") 
	WebElement resourcesLink;

	@FindBy(linkText = "Issues") 
	WebElement issuesLink;

	@FindBy(linkText = "Locations") 
	WebElement locationsLink;

	@FindBy(linkText = "Tablets") 
	WebElement tabletsLink;	
	
	public AbstractMainMenu() {
		driver = SeleniumDriverManager.getManager().getDriver();
		wait = SeleniumDriverManager.getManager().getWait();
		PageFactory.initElements(driver, this);
	}

	public HomeAdminPage clickEmailServersLink() {
		emailServersLink.click();
		return new HomeAdminPage();
	}

	public void clickImpersonationLink() {
		impersonationLink.click();
	}

	public RoomsPage clickConferenceRoomsLink() {
		conferenceRoomsLink.click();
		return new RoomsPage();
	}

	public ResourcesPage clickResourcesLink() {
		resourcesLink.click();
		return new ResourcesPage();
	}

	public void clickHomeLink() {
		homeLink.click();
	}

	public void clickIssuesLink() {
		issuesLink.click();
	}

	public void clickLocationsLink() {
		locationsLink.click();
	}

	public void clickTabletsLink() {
		tabletsLink.click();
	}
}
