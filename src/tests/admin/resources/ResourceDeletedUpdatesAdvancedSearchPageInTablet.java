package tests.admin.resources;

import static framework.common.AppConfigConstants.EXCEL_INPUT_DATA;
import static framework.common.AppConfigConstants.RESOURCE1_PATH;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;
import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import framework.common.UIMethods;
import framework.pages.admin.HomeAdminPage;
import framework.pages.admin.conferencerooms.RoomInfoPage;
import framework.pages.admin.conferencerooms.RoomResourceAssociationsPage;
import framework.pages.admin.conferencerooms.RoomsPage;
import framework.pages.admin.resources.ResourceDeletePage;
import framework.pages.admin.resources.ResourcesPage;
import framework.pages.tablet.HomeTabletPage;
import framework.pages.tablet.SearchPage;
import framework.rest.RootRestMethods;
import framework.utils.readers.ExcelReader;
import framework.utils.readers.JsonReader;

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
		RootRestMethods.createResource(resourceFilePath, "");
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