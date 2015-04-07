package test.java.admin.resources;

import static main.java.utils.AppConfigConstants.EXCEL_INPUT_DATA;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;
import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import main.java.pages.admin.HomeAdminPage;
import main.java.pages.admin.resources.ResourceCreatePage;
import main.java.pages.admin.resources.ResourceInfoPage;
import main.java.pages.admin.resources.ResourcesPage;
import main.java.rest.RoomManagerRestMethods;
import main.java.utils.readers.ExcelReader;

/**
 * TC42:Verify that an error message is displayed when {Display Name} text box is left empty on 
 * Resource
 * Info form.
 * TC43: Verify that an error message is displayed when {Name} text box is left empty on Resource 
 * Info form.
 * @author Juan Carlos Guevara 
 */
public class AnErrorMessageIsDisplayedWhenTextBoxesAreLeftEmptyInResourceInfoForm {
	ResourcesPage resourcesPage;
	ResourceInfoPage resourceInfoPage;
	
	//Reading resource data from an .xls file
	private ExcelReader excelReader = new ExcelReader(EXCEL_INPUT_DATA);
	private List<Map<String, String>> testData = excelReader.getMapValues("Resources");
	private String resourceName = testData.get(0).get("ResourceName");
	private String resourceDisplayName = testData.get(0).get("ResourceDisplayName");
	private String resourceDescription = testData.get(0).get("Description");
	private String iconTitle = testData.get(0).get("Icon");

	@BeforeClass(groups = {"NEGATIVE"})
	public void createAResource() {
		HomeAdminPage homeAdminPage = new HomeAdminPage();
		ResourcesPage resourcesPage = homeAdminPage.clickResourcesLink();	

		//create a resource
		ResourceCreatePage resourceCreatePage = resourcesPage.clickAddResourceBtn();		
		resourceCreatePage.clickResourceIcon()
		.selectResourceIcon(iconTitle)
		.setResourceName(resourceName)
		.setResourceDisplayName(resourceDisplayName)
		.setResourceDescription(resourceDescription)
		.clickSaveResourceBtn();
	}

	@Test(groups = "NEGATIVE")
	public void testAnErrorMessageIsDisplayedWhenNameTextBoxisLeftEmptyInResourceInfoForm()
			throws InterruptedException {
		HomeAdminPage homeAdminPage = new HomeAdminPage();
		ResourcesPage resourcesPage = homeAdminPage.clickResourcesLink();
		
		//Cleaning resource name textbox 
		resourceInfoPage = resourcesPage.openResourceInfoPage(resourceName);		
		resourceInfoPage.clearResourceName()
		.clickSaveResourceWithErrorBtn();

		//Assertion for TC43		
		Assert.assertTrue(resourceInfoPage.isNameTxtBoxEmptyErrorDisplayed());	
		resourceInfoPage.clickCancelResourceBtn();

		//Cleaning resource display name textbox 		
		resourceInfoPage = resourcesPage.openResourceInfoPage(resourceName);
		resourceInfoPage.setResourceName(resourceName)
		.clearResourceDisplayName()
		.clickSaveResourceWithErrorBtn();

		//Assertion for TC42  		
		Assert.assertTrue(resourceInfoPage.isDisplayNameTxtBoxEmptyErrorDisplayed());	
	}

	@AfterClass(groups = "NEGATIVE")
	public void postCondition() throws MalformedURLException, IOException {
		resourcesPage = resourceInfoPage.clickCancelResourceBtn();
		
		//Delete resource with API rest method
		RoomManagerRestMethods.deleteResource(resourceName);
	}
}
