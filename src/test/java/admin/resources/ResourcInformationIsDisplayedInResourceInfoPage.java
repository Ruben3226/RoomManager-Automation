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
 * TC06: Verify that resource information is displayed in {ResourceInfo} form when a 
 * resourcesPage is selected
 * @author Marco Llano
 */
public class ResourcInformationIsDisplayedInResourceInfoPage {
	private ResourcesPage resourcesPage;
	private ResourceInfoPage resourceInfoPage;
	private ExcelReader excelReader = new ExcelReader(EXCEL_INPUT_DATA);
	private List<Map<String, String>> resourceDataList = excelReader.getMapValues("Resources");
	private String resourceName = resourceDataList.get(0).get("ResourceName");

	@Test(groups = "FUNCTIONAL")
	public void testResourcInformationIsDisplayedInResourceInfoPage() throws InterruptedException {
		HomeAdminPage homeAdminPage = new HomeAdminPage();		
		resourcesPage = homeAdminPage.clickResourcesLink();	

		//Variables declaration and initialize
		String resourceDisplayName = resourceDataList.get(0).get("ResourceDisplayName");
		String resourceDescription = resourceDataList.get(0).get("Description");
		String iconTitle = resourceDataList.get(0).get("Icon");

		//Create new resource
		ResourceCreatePage resourceCreatePage = resourcesPage.clickAddResourceBtn();
		resourcesPage = resourceCreatePage.clickResourceIcon()
				.selectResourceIcon(iconTitle)
				.setResourceName(resourceName)
				.setResourceDisplayName(resourceDisplayName)
				.setResourceDescription(resourceDescription)
				.clickSaveResourceBtn();

		//Assertion for TC06
		resourceInfoPage = resourcesPage.openResourceInfoPage(resourceName);
		Assert.assertTrue(resourceInfoPage.getResourceName().contains(resourceName));
		Assert.assertTrue(resourceInfoPage.getResourceDisplayName().contains(resourceDisplayName));
		Assert.assertTrue(resourceInfoPage.getResourceDescription().contains(resourceDescription));
		Assert.assertTrue(resourceInfoPage.getResourceIcon(iconTitle));		
	}	

	@AfterMethod(groups = "FUNCTIONAL")
	public void afterMethod() throws MalformedURLException, IOException {
		resourcesPage = resourceInfoPage.clickCancelResourceBtn();		
		RoomManagerRestMethods.deleteResource(resourceName);
	}
}