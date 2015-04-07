package tests.admin.resources;

import static framework.common.AppConfigConstants.EXCEL_INPUT_DATA;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;
import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import framework.pages.admin.HomeAdminPage;
import framework.pages.admin.resources.ResourceCreatePage;
import framework.pages.admin.resources.ResourceInfoPage;
import framework.pages.admin.resources.ResourcesPage;
import framework.rest.RootRestMethods;
import framework.utils.readers.ExcelReader;

/**
 * TC16: Verify that a resource is created when the default icon is changed.
 * TC29: Verify that the icon displayed in {Icon Column} is changed when it is edited in 
 * {ResourceInfo} form
 * @author Marco Llano
 */
public class ResourceIconCanBeUpdatedInResourcesPage {
	
	//ExcelReader is used to read resources data from excel
	private ExcelReader excelReader = new ExcelReader(EXCEL_INPUT_DATA);
	private List<Map<String, String>> resourceDataList = excelReader.getMapValues("Resources");
	private String resourceName = resourceDataList.get(2).get("ResourceName");

	@Test(groups = "FUNCTIONAL")
	public void testResourceIconCanBeUpdatedInResourcesPage() throws InterruptedException {		
		HomeAdminPage homeAdminPage = new HomeAdminPage();
		ResourcesPage resourcesPage = homeAdminPage.clickResourcesLink();

		//Variables declaration and initialize
		String resourceDisplayName = resourceDataList.get(2).get("ResourceDisplayName");
		String resourceIconTitle = resourceDataList.get(2).get("Icon");
		String newIconTitle = resourceDataList.get(1).get("Icon");

		//Create new resource
		ResourceCreatePage resourceCreatePage = resourcesPage.clickAddResourceBtn();		
		resourcesPage = resourceCreatePage.clickResourceIcon()
				.selectResourceIcon(resourceIconTitle)
				.setResourceName(resourceName)
				.setResourceDisplayName(resourceDisplayName)
				.clickSaveResourceBtn();

		//Assertion for TC16	
		Assert.assertTrue(resourcesPage.getResourceIcon(resourceIconTitle));

		//Update resourcesPage icon
		ResourceInfoPage resourceInfoPage = resourcesPage.openResourceInfoPage(resourceName);
		resourcesPage = resourceInfoPage.clickResourceIcon()
				.selectResourceIcon(newIconTitle)
				.clickSaveResourceBtn();

		//Assertion for TC29
		Assert.assertTrue(resourceInfoPage.getResourceIcon(newIconTitle));
	}

	@AfterMethod(groups = "FUNCTIONAL")
	public void afterMethod() throws MalformedURLException, IOException {		
		RootRestMethods.deleteResource(resourceName);
	}
}