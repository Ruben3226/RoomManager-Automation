package framework.pages.admin.resources;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

import framework.selenium.SeleniumDriverManager;

/**
 * @author Marco Llano
 *
 */
public class ResourceDeletePage extends ResourceBaseAbstractPage {
	
	@FindBy(css = "button.info")
	WebElement confirmRemoveBtn;

	public ResourceDeletePage() {		
		driver = SeleniumDriverManager.getManager().getDriver();
		wait = SeleniumDriverManager.getManager().getWait();
		PageFactory.initElements(driver, this);
	}

	/**
	 * [ML]This is the button used for confirm to delete a resource
	 * @return
	 */
	public ResourcesPage clickConfirmRemoveBtn() {
		wait.until(ExpectedConditions.elementToBeClickable(confirmRemoveBtn));
		confirmRemoveBtn.click();
		return new ResourcesPage();
	}
}
