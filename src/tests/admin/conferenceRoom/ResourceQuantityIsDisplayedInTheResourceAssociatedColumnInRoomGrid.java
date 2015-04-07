package tests.admin.conferenceRoom;

import static framework.common.AppConfigConstants.EXCEL_INPUT_DATA;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;
import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import framework.pages.admin.HomeAdminPage;
import framework.pages.admin.conferencerooms.RoomInfoPage;
import framework.pages.admin.conferencerooms.RoomResourceAssociationsPage;
import framework.pages.admin.conferencerooms.RoomsPage;
import framework.rest.RootRestMethods;
import framework.utils.readers.ExcelReader;
import framework.utils.readers.JsonReader;

/**
 * TC08: Verify that resources added are listed in the top of room list pane
 * TC27: Verify that resource quantity and icon are displayed as columns in Room grid.
 * TC32: Verify that resources added are inserted as columns in Room grid. 
 * @author Juan Carlos Guevara
 */
public class ResourceQuantityIsDisplayedInTheResourceAssociatedColumnInRoomGrid {

	//Reading resource data from an .xls file
	private ExcelReader excelReader = new ExcelReader(EXCEL_INPUT_DATA);
	private List<Map<String, String>> testData = excelReader.getMapValues("APIResources");
	private String roomName = testData.get(0).get("Room Name");
	private String quantity = testData.get(0).get("Value");

	//Reading json resource information
	private JsonReader jsonValue = new JsonReader();
	private String resourceFileJSON = "\\src\\tests\\Resource1.json";
	private String filePath = System.getProperty("user.dir") + resourceFileJSON;
	private String resourceName = jsonValue.readJsonFile("name" , resourceFileJSON);
	private String resourceDisplayName = jsonValue.readJsonFile("customName" , resourceFileJSON);

	@BeforeClass(groups = "FUNCTIONAL")
	public void createResource() throws MalformedURLException, IOException {

		//Creating resource by Rest
		RootRestMethods.createResource(filePath, "");		
	}

	@Test(groups = "FUNCTIONAL")
	public void testAResourceIsDisplayedAsAColumnWhenItsIconIsSelectedInTheTopOfRoomGrid() {
		HomeAdminPage homeAdminPage = new HomeAdminPage();
		RoomsPage roomsPage = homeAdminPage.clickConferenceRoomsLink();

		//Associating resource to a room
		RoomInfoPage roomInfoPage = roomsPage.doubleClickOverRoomName(roomName);
		RoomResourceAssociationsPage roomResourceAssociationsPage = roomInfoPage
				.clickResourceAssociationsLink();
		roomResourceAssociationsPage.clickAddResourceToARoom(resourceDisplayName)
		.changeValueForResourceFromAssociatedList(resourceDisplayName,quantity);
		roomsPage = roomResourceAssociationsPage.clickSaveBtn();
		roomsPage.clickResourceIcon(resourceDisplayName);

		//Assertion for TC27 
		Assert.assertEquals(roomsPage.getResourceQuantity(roomName),"x " + quantity);

		//Assertion for TC32 
		Assert.assertTrue(roomsPage.isResourcePresentInTableHeader(resourceDisplayName));

		//Assertion for TC08 
		Assert.assertTrue(roomsPage.searchResource(resourceDisplayName));
	}

	@AfterClass(groups = "FUNCTIONAL")
	public void deleteResource() throws MalformedURLException, IOException {

		//Deleting resource with API rest method
		RootRestMethods.deleteResource(resourceName);
	}
}
