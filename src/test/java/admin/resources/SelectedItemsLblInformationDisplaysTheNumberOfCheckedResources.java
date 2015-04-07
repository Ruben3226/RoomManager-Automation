package test.java.admin.resources;

import static main.java.utils.AppConfigConstants.EXCEL_INPUT_DATA;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;
import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;

import main.java.pages.admin.HomeAdminPage;
import main.java.pages.admin.resources.ResourceCreatePage;
import main.java.pages.admin.resources.ResourcesPage;
import main.java.rest.RoomManagerRestMethods;
import main.java.utils.readers.ExcelReader;

/**
 * TC21: Verify that {Selected items} information displays the number of  checked resources below 
 * of {Resource grid} according {page size & items > 1- 50} setting.
 * @author Juan Carlos Guevara 
 */
public class SelectedItemsLblInformationDisplaysTheNumberOfCheckedResources {
	
	//Reading resource data from an .xls file
	private ExcelReader excelReader = new ExcelReader(EXCEL_INPUT_DATA);
	private List<Map<String, String>> testData = excelReader.getMapValues("Resources");

	@Test(groups = "FUNCTIONAL")
	public void testSelectedItemsLblInformationDisplaysTheNumberOfCheckedResources() 
			throws InterruptedException {
		int counter = 0;
		HomeAdminPage homeAdminPage = new HomeAdminPage();
		ResourcesPage resourcesPage = homeAdminPage.clickResourcesLink();
		for(Map<String, String> resource : testData) {

			//Create a resource
			ResourceCreatePage resourceCreatePage = resourcesPage.clickAddResourceBtn();		
			resourceCreatePage.clickResourceIcon()
			.selectResourceIcon(resource.get("Icon"))
			.setResourceName(resource.get("ResourceName"))
			.setResourceDisplayName(resource.get("ResourceDisplayName"))
			.setResourceDescription(resource.get("Description"));
			resourcesPage = resourceCreatePage.clickSaveResourceBtn();
			counter = counter + 1;	
		}
		resourcesPage.clickSelectAllResources();

		//Assertion for TC21 
		Assert.assertTrue(resourcesPage.searchSelectedItemsValue(Integer.toString(counter)));
	}

	@AfterClass(groups = "FUNCTIONAL")
	public void deleteResource() throws MalformedURLException, IOException {

		//Delete resources with API rest method
		for(Map<String, String> resource : testData){					
			RoomManagerRestMethods.deleteResource(resource.get("ResourceName"));
		}
	}
}
