package tests.admin.conferenceRoomResourceAssociations;

import static framework.common.AppConfigConstants.EXCEL_INPUT_DATA;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;
import java.util.Map;

import jxl.read.biff.BiffException;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import framework.pages.admin.HomeAdminPage;
import framework.pages.admin.conferencerooms.RoomInfoPage;
import framework.pages.admin.conferencerooms.RoomResourceAssociationsPage;
import framework.pages.admin.conferencerooms.RoomsPage;
import framework.pages.admin.resources.ResourcesPage;
import framework.rest.RootRestMethods;
import framework.utils.readers.ExcelReader;
import framework.utils.readers.JsonReader;

/**
 * TC03: Verify that resources associated to a room enabled can be removed.
 * @author Juan Carlos Guevara  
 */
public class ResourcesAssociatedToARoomEnabledCanBeRemoved {
	
	//Reading resource data from an .xls file
	private ExcelReader excelReader = new ExcelReader(EXCEL_INPUT_DATA);
	private List<Map<String, String>> testData = excelReader.getMapValues("APIResources");
	private String roomName = testData.get(0).get("Room Name");

	//Reading json resource information
	private JsonReader jsonValue = new JsonReader();
	private String resourceFileJSON = "\\src\\tests\\Resource1.json";
	private String filePath = System.getProperty("user.dir") + resourceFileJSON;
	private String resourceName = jsonValue.readJsonFile("name" , resourceFileJSON);	
	private String resourceDisplayName = jsonValue.readJsonFile("customName" , resourceFileJSON);

	@BeforeClass(groups = "ACCEPTANCE")
	public void associateAResourceCreated() throws MalformedURLException, IOException {

		//Create resource by Rest
		RootRestMethods.createResource(filePath, "");
		HomeAdminPage homeAdminPage = new HomeAdminPage();				
		ResourcesPage resourcesPage = homeAdminPage.clickResourcesLink();

		//Associating resource to a room
		RoomsPage roomsPage = resourcesPage.clickConferenceRoomsLink();
		RoomInfoPage roomInfoPage = roomsPage.doubleClickOverRoomName(roomName);
		RoomResourceAssociationsPage roomResourceAssociationsPage = roomInfoPage
				.clickResourceAssociationsLink();
		roomResourceAssociationsPage.clickAddResourceToARoom(resourceDisplayName)
		.clickSaveBtn();
	}

	@Test(groups = "ACCEPTANCE")
	public void testResourcesAssociatedToARoomEnabledCanBeRemoved() {

		//Disassociate resource
		RoomsPage roomsPage = new RoomsPage();
		roomsPage.clickConferenceRoomsLink();
		RoomInfoPage roomInfoPage = roomsPage.doubleClickOverRoomName(roomName);
		RoomResourceAssociationsPage roomResourceAssociationsPage = roomInfoPage
				.clickResourceAssociationsLink();
		roomResourceAssociationsPage.removeResourceFromAssociatedList(resourceDisplayName);
		roomsPage = roomResourceAssociationsPage.clickSaveBtn();

		//Assertion for TC03
		Assert.assertTrue(roomResourceAssociationsPage.searchResource(resourceDisplayName));
	}

	@AfterClass(groups = "ACCEPTANCE")
	public void deleteResource() throws InterruptedException, BiffException, IOException {

		//Delete resource with API rest method
		RootRestMethods.deleteResource(resourceName);
	}
}
