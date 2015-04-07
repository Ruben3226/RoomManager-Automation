package test.java.admin.resources;

import static main.java.utils.AppConfigConstants.EXCEL_INPUT_DATA;
import static main.java.utils.AppConfigConstants.RESOURCE1_PATH;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;
import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import main.java.pages.admin.HomeAdminPage;
import main.java.pages.admin.conferencerooms.RoomInfoPage;
import main.java.pages.admin.conferencerooms.RoomResourceAssociationsPage;
import main.java.pages.admin.conferencerooms.RoomsPage;
import main.java.pages.admin.resources.ResourceDeletePage;
import main.java.pages.admin.resources.ResourcesPage;
import main.java.pages.tablet.HomeTabletPage;
import main.java.pages.tablet.SearchPage;
import main.java.rest.RoomManagerRestMethods;
import main.java.selenium.UIMethods;
import main.java.utils.readers.ExcelReader;
import main.java.utils.readers.JsonReader;

/** 
 * TC07: Verify that a resource deleted is removed of {Filter by Resource:}  section on {Advanced > Search} page
 * @author Marco Llano
 */
public class ResourceDeletedUpdatesAdvancedSearchPageInTablet {
	private HomeAdminPage homeAdminPage = new HomeAdminPage();
	private ResourcesPage resourcesPage = homeAdminPage.clickResourcesLink();	

	//ExcelReader is used to read resources data from excel		
	private ExcelReader excelReader = new ExcelReader(EXCEL_INPUT_DATA);
	private List<Map<String, String>> testData = excelReader.getMapValues("Resources");

	//JsonReader is used to read resources data
	private JsonReader value = new JsonReader();
	private String resourceDisplayName = value.readJsonFile("name" , RESOURCE1_PATH);	

	@BeforeMethod(groups = "FUNCTIONAL")
	public void beforeMethod() throws MalformedURLException, IOException {

		//Create resource by Rest
		String resourceFilePath = System.getProperty("user.dir") + RESOURCE1_PATH;
		RoomManagerRestMethods.createResource(resourceFilePath, "");
		UIMethods.refresh();

		//Recover roomDisplayName
		String roomDisplayName = testData.get(0).get("Room Name");
		String quantity = testData.get(0).get("Value");

		//Associate a resource to a room by resource display name
		RoomsPage roomsPage = homeAdminPage.clickConferenceRoomsLink();
		RoomInfoPage roomInfoPage = roomsPage.doubleClickOverRoomName(roomDisplayName);
		RoomResourceAssociationsPage roomResourceAssociationsPage = roomInfoPage.clickResourceAssociationsLink();
		roomsPage = roomResourceAssociationsPage.clickAddResourceToARoom(resourceDisplayName)
				.changeValueForResourceFromAssociatedList(resourceDisplayName, quantity)
				.clickSaveBtn();
	}

	@Test(groups = "FUNCTIONAL")
	public void testResourceDeletedUpdatesAdvancedSearchPageInTablet() throws InterruptedException {

		//Open Advanced search from tablet
		HomeTabletPage homeTabletPage =  new HomeTabletPage();
		SearchPage searchPage = homeTabletPage.clickSearchBtn();
		searchPage.clickCollapseAdvancedBtn();

		//Delete created resource
		homeAdminPage = new HomeAdminPage();
		resourcesPage = homeAdminPage.clickResourcesLink();
		ResourceDeletePage resourceDeletePage = resourcesPage
				.selectResourceCheckbox(resourceDisplayName)
				.clickRemoveBtn();
		resourcesPage = resourceDeletePage.clickConfirmRemoveBtn();

		//Go back to tablet to verify if the resource is displayed
		homeTabletPage =  new HomeTabletPage();
		searchPage = homeTabletPage.clickSearchBtn();
		searchPage.clickCollapseAdvancedBtn();

		//Assertion for TC07
		Assert.assertFalse(searchPage.isResourceInAdvancedSearch(resourceDisplayName));
	}
}