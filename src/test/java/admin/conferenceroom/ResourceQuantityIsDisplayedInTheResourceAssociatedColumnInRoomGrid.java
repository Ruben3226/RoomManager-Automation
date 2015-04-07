package test.java.admin.conferenceroom;

import static main.java.utils.AppConfigConstants.EXCEL_INPUT_DATA;
import static main.java.utils.AppConfigConstants.RESOURCE1_PATH;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;
import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import main.java.pages.admin.HomeAdminPage;
import main.java.pages.admin.conferencerooms.RoomInfoPage;
import main.java.pages.admin.conferencerooms.RoomResourceAssociationsPage;
import main.java.pages.admin.conferencerooms.RoomsPage;
import main.java.rest.RoomManagerRestMethods;
import main.java.utils.readers.ExcelReader;
import main.java.utils.readers.JsonReader;

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
	private String filePath = System.getProperty("user.dir") + RESOURCE1_PATH ;
	private String resourceName = jsonValue.readJsonFile("name" , RESOURCE1_PATH );
	private String resourceDisplayName = jsonValue.readJsonFile("customName" , RESOURCE1_PATH );

	@BeforeClass(groups = "FUNCTIONAL")
	public void createResource() throws MalformedURLException, IOException {

		//Creating resource by Rest
		RoomManagerRestMethods.createResource(filePath, "");		
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
		RoomManagerRestMethods.deleteResource(resourceName);
	}
}
