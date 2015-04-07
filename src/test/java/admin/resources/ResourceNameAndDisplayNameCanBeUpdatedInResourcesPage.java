package test.java.admin.resources;

import static main.java.utils.AppConfigConstants.EXCEL_INPUT_DATA;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;
import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import main.java.pages.admin.HomeAdminPage;
import main.java.pages.admin.resources.ResourceCreatePage;
import main.java.pages.admin.resources.ResourceInfoPage;
import main.java.pages.admin.resources.ResourcesPage;
import main.java.rest.RoomManagerRestMethods;
import main.java.utils.readers.ExcelReader;

/**
 * TC34: Verify that the display name in {Display name} column is changed when it is edited in 
 * {ResourceInfo} form.
 * TC30: Verify that the name displayed in {Name} column is changed when it is edited in 
 * {ResourceInfo} form
 * @author Marco Llano
 */
public class ResourceNameAndDisplayNameCanBeUpdatedInResourcesPage {
	
	//ExcelReader is used to read resources data from excel
	private ExcelReader excelReader = new ExcelReader(EXCEL_INPUT_DATA);
	private List<Map<String, String>> resourceDataList = excelReader.getMapValues("Resources");
	private String resourceName = resourceDataList.get(1).get("ResourceName");

	@Test(groups = "FUNCTIONAL")
	public void testResourceDisplayNameCanBeUpdatedInResourcePage() throws InterruptedException {
		HomeAdminPage homeAdminPage = new HomeAdminPage();
		ResourcesPage resourcesPage = homeAdminPage.clickResourcesLink();

		//Variable declaration and initialize
		String resourceDisplayName = resourceDataList.get(1).get("ResourceDisplayName");
		String newResourceDisplayName = resourceDataList.get(2).get("ResourceDisplayName");

		//Create new resource
		ResourceCreatePage resourceCreatePage = resourcesPage.clickAddResourceBtn();		
		resourcesPage = resourceCreatePage.setResourceName(resourceName)
				.setResourceDisplayName(resourceDisplayName)
				.clickSaveResourceBtn();

		//Update resourceDisplayName field
		ResourceInfoPage resourceInfoPage = resourcesPage.openResourceInfoPage(resourceName);		
		resourcesPage = resourceInfoPage.setResourceDisplayName(newResourceDisplayName)
				.clickSaveResourceBtn();

		//Assertion for TC34
		Assert.assertTrue(resourcesPage.isResourceDisplayNameDisplayedInResourcesPage(newResourceDisplayName));

		//Assertion for TC30
		Assert.assertTrue(resourcesPage.isResourceNameDisplayedInResourcesPage(resourceName));
	}

	@AfterMethod(groups = "FUNCTIONAL")
	public void afterMethod() throws MalformedURLException, IOException {	
		RoomManagerRestMethods.deleteResource(resourceName);
	}
}