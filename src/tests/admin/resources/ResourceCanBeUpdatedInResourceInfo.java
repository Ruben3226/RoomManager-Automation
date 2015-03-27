package tests.admin.resources;

import static framework.common.AppConfigConstants.EXCEL_INPUT_DATA;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;
import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import framework.common.UIMethods;
import framework.pages.admin.HomeAdminPage;
import framework.pages.admin.resources.ResourceCreatePage;
import framework.pages.admin.resources.ResourceInfoPage;
import framework.pages.admin.resources.ResourcesPage;
import framework.rest.RootRestMethods;
import framework.utils.readers.ExcelReader;

/**
 * TC02: Verify that display name of a resource can be updated in {ResourceInfo} form.  
 * TC04: Verify that name of a resource can be updated in {ResourceInfo} form .
 * TC05: Verify that icon of a resource can be updated in {ResourceInfo} form .
 * TC27: Verify that description of a resource can be updated and its new information is displayed in 
 * 		 {ResourceInfo} form. 
 * @author Marco Llano
 */
public class ResourceCanBeUpdatedInResourceInfo {
	private ResourcesPage resource;
	private ResourceInfoPage resourceInfo;
	private ExcelReader excelReader = new ExcelReader(EXCEL_INPUT_DATA);
	private List<Map<String, String>> testData = excelReader.getMapValues("Resources");

	@Test(groups = {"FUNCTIONAL"})
	public void testResourceCanBeUpdatedInResourceInfo() throws InterruptedException {
		HomeAdminPage home = new HomeAdminPage();
		resource = home.clickResourcesLink();

		//Variables declaration and initialize
		String iconTitle = testData.get(0).get("Icon");
		String resourceName = testData.get(0).get("ResourceName");
		String resourceDisplayName = testData.get(0).get("ResourceDisplayName");
		String resourceDescription = testData.get(0).get("Description");
		String newIconTitle = testData.get(1).get("Icon");
		String newResourceName = testData.get(1).get("ResourceName");
		String newResourceDisplayName = testData.get(1).get("ResourceDisplayName");
		String newResourceDescription = testData.get(1).get("Description");

		//Create a resource
		ResourceCreatePage resourceCreate  = resource.clickAddResourceBtn();		
		resource = resourceCreate.clickResourceIcon()
				.selectResourceIcon(iconTitle)
				.setResourceName(resourceName)
				.setResourceDisplayName(resourceDisplayName)
				.setResourceDescription(resourceDescription)
				.clickSaveResourceBtn();

		//Update the created resource
		resourceInfo = resource.openResourceInfoPage(resourceName);
		resource = resourceInfo.clickResourceIcon()
				.selectResourceIcon(newIconTitle)
				.setResourceName(newResourceName)
				.setResourceDisplayName(newResourceDisplayName)
				.setResourceDescription(newResourceDescription)
				.clickSaveResourceBtn();
		resourceInfo = resource.openResourceInfoPage(newResourceDisplayName);

		//Assertion for TC02		
		Assert.assertTrue(resourceInfo.getResourceDisplayName().contains(newResourceDisplayName));

		//Assertion for TC04
		Assert.assertTrue(resourceInfo.getResourceName().contains(newResourceName));

		//Assertion for TC05
		Assert.assertTrue(resourceInfo.getResourceIcon(newIconTitle));

		//Assertion for TC27
		Assert.assertTrue(resourceInfo.getResourceDescription().contains(newResourceDescription));
	}	

	@AfterMethod
	public void afterMethod() throws MalformedURLException, IOException {	
		resource = resourceInfo.clickCancelResourceBtn();		
		RootRestMethods.deleteResource(testData.get(1).get("ResourceName"));
		UIMethods.refresh();
	}
}