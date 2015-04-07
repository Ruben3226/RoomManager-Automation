package framework.pages.admin.resources;

import static framework.common.UIMethods.doubleClick;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import framework.common.UIMethods;
import framework.pages.admin.AbstractMainMenu;

/**
 * @author Marco Llano
 *
 */
public class ResourcesPage extends AbstractMainMenu{

	@FindBy(xpath = "//div[@class='pull-left']/button[@ng-click='addResourceDialog()']") 
	WebElement addResourceBtn;

	@FindBy(xpath = "//input[@class='ngSelectionCheckbox']")
	WebElement resourceChkbox;

	@FindBy(id = "btnRemove")
	WebElement removeBtn;

	@FindBy(xpath = "//div[@class='ngPagerLastTriangle']")
	WebElement lastPageBtn;

	@FindBy(xpath = "//div[@class='ngPagerFirstTriangle ngPagerPrevTriangle']")
	WebElement previousPageBtn;

	@FindBy(xpath = "//div[@class='ngPagerLastTriangle ngPagerNextTriangle']")
	WebElement nextPageBtn;

	public ResourcesPage() {
		PageFactory.initElements(driver, this);
	}

	/**
	 * [ML]Click on "+Add" resource button	
	 * @return
	 */
	public ResourceCreatePage clickAddResourceBtn() {
		wait.until(ExpectedConditions.elementToBeClickable(addResourceBtn));
		addResourceBtn.click();
		return new ResourceCreatePage();
	}

	/**
	 * [ML]Double click action in a given resource name from ResourcesPage
	 * @param resourceNameToSearch
	 * @return
	 * @throws InterruptedException
	 */
	public ResourceInfoPage openResourceInfoPage(String resourceNameToSearch) throws InterruptedException {	
		WebElement resourceName = driver.findElement(By.xpath("//span[contains(text(),'" + resourceNameToSearch + "')]"));
		doubleClick(resourceName);
		return new ResourceInfoPage();
	}		

	/**
	 * [ML]Selects a resource from ResourcesPage by click, according to the name given in parameter
	 * @param resource
	 * @return
	 * @throws InterruptedException
	 */	
	public ResourcesPage selectResourceCheckbox(String resource) throws InterruptedException {
		WebElement resourceName = driver.findElement(By.xpath("//*[@id='resourcesGrid']/descendant::"
				+ "*/span[contains(text(),'" + resource + "')]"));
		if (resourceName.isDisplayed()) {
			resourceName.click();
		}		
		return this;
	}

	/**
	 * [ML]Click on "remove" resource button
	 * @return
	 */
	public ResourceDeletePage clickRemoveBtn() {
		wait.until(ExpectedConditions.elementToBeClickable(removeBtn));
		removeBtn.click();
		return new ResourceDeletePage();
	}	

	/**
	 * [ML]Returns resource displayName from ResourcesPage
	 * @param resourceDisplayName
	 * @return
	 */
	public boolean isResourceDisplayNameDisplayedInResourcesPage(String resourceDisplayName) {
		return driver.findElement(By.xpath(".//*[@id='resourcesGrid']/descendant::*/span[contains(text(),'"
				+ resourceDisplayName + "')]")).isDisplayed();		
	}

	/**
	 * [ML]Returns resource name from resource page
	 * @param resourceName
	 * @return
	 */	
	public boolean isResourceNameDisplayedInResourcesPage(String resourceName) {
		By resource = By.xpath("//div[@id='resourcesGrid']/div[2]/descendant::*/span[contains(text(),'"
				+ resourceName + "')]");
		return UIMethods.isElementPresent(resource);
	}

	/**
	 * [CG]Method that returns true if the number of resources send is found in "Total Items Value"
	 * Label
	 * @param value
	 * @return
	 * @throws InterruptedException
	 */
	public boolean searchTotalItemsValue(String value) throws InterruptedException {
		String locator = "//span[contains(text(),'Total Items: " + value + "')]";
		wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath(locator)));
		return UIMethods.isElementPresent(By.xpath(locator));
	}

	/**
	 * [CG]Method that returns true if the number of resources send is found in "Selected Items 
	 * Value" Label 
	 * @param value
	 * @return
	 * @throws InterruptedException
	 */
	public boolean searchSelectedItemsValue(String value) throws InterruptedException {
		By locator = By.xpath("//span[contains(text(),'Selected Items: " + value + "')]");
		wait.until(ExpectedConditions.presenceOfElementLocated(locator));
		return UIMethods.isElementPresent(locator);
	}

	/**
	 * [CG]Method that clicks "Select All Resources" Checkbox 
	 */
	public void clickSelectAllResources() {
		driver.findElement(By.xpath("//input[@ng-model='allSelected']")).click();
	}


	/**
	 * [ML]Return the resource icon name from in resourceInfoPage if is present
	 * @param iconTitle
	 * @return boolean if is or not present
	 */
	public boolean getResourceIcon(String iconTitle) {
		By resourceIcon = By.xpath(".//*[@id='resourcesGrid']/descendant::*/span[@class='fa " +
				iconTitle + "']");
		return UIMethods.isElementPresent(resourceIcon);
	}
}
