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
 * TC03: Verify that a resource is created when required values are inserted in Add form
 * TC15: Verify that a resource is created when all values (required, optional), are inserted in Add form
 * @author Marco Llano
 */
public class ResourceCreatedWithRequiredAndNotRequiredValue {	
	private ResourcesPage resourcesPage;
	private ResourceInfoPage resourceInfoPage;
	
	//ExcelReader is used to read rooms data
	private ExcelReader excelReader = new ExcelReader(EXCEL_INPUT_DATA);
	private List<Map<String, String>> resourcesDataList = excelReader.getMapValues("Resources");
	private String resourceName = resourcesDataList.get(1).get("ResourceName");

	@Test(groups = "ACCEPTANCE")
	public void testResourceCreatedWithRequiredValue() throws InterruptedException {
		HomeAdminPage homeAdminPage = new HomeAdminPage();
		resourcesPage = homeAdminPage.clickResourcesLink();

		//Variable declaration and initialize
		String iconTitle = resourcesDataList.get(1).get("Icon");		
		String resourceDisplayName = resourcesDataList.get(1).get("ResourceDisplayName");
		String resourceDescription = resourcesDataList.get(1).get("Description");

		//Create new resource
		ResourceCreatePage resourceCreatePage  = resourcesPage.clickAddResourceBtn();		
		resourcesPage = resourceCreatePage.clickResourceIcon()
				.selectResourceIcon(iconTitle)
				.setResourceName(resourceName)
				.setResourceDisplayName(resourceDisplayName)
				.setResourceDescription(resourceDescription)
				.clickSaveResourceBtn();

		//Assertion for TC03 and required values from TC15
		resourceInfoPage = resourcesPage.openResourceInfoPage(resourceName);
		Assert.assertTrue(resourceInfoPage.getResourceName().contains(resourceName));
		Assert.assertTrue(resourceInfoPage.getResourceDisplayName().contains(resourceDisplayName));

		//Assertion for TC15 optional values
		Assert.assertTrue(resourceInfoPage.getResourceIcon(iconTitle));
		Assert.assertTrue(resourceInfoPage.getResourceDescription().contains(resourceDescription));
	}

	@AfterMethod(groups = "ACCEPTANCE")
	public void afterMethod() throws MalformedURLException, IOException {			
		RootRestMethods.deleteResource(resourceName);	
	}
}