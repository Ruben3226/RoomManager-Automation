package tests.admin.conferenceRoomResourceAssociations;
import static framework.common.AppConfigConstants.EXCEL_INPUT_DATA;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;
import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import framework.common.UIMethods;
import framework.pages.admin.HomeAdminPage;
import framework.pages.admin.conferencerooms.RoomInfoPage;
import framework.pages.admin.conferencerooms.RoomResourceAssociationsPage;
import framework.pages.admin.conferencerooms.RoomsPage;
import framework.rest.RootRestMethods;
import framework.utils.readers.ExcelReader;
import framework.utils.readers.JsonReader;

/**
 * TC02: Verify that a resource can be associated to a room enabled.
 * @author Juan Carlos Guevara
 */
public class AResourceCanBeAssociatedToARoomEnabled {
	
	//Reading resource data from an .xls file
	private ExcelReader excelReader = new ExcelReader(EXCEL_INPUT_DATA);
	private List<Map<String, String>> testData = excelReader.getMapValues("APIResources");
	private String roomName = testData.get(0).get("Room Name");

	//Reading json resource information
	JsonReader jsonValue = new JsonReader();
	private String resourceFileJSON = "\\src\\tests\\Resource1.json";
	private String filePath = System.getProperty("user.dir") + resourceFileJSON;
	private String resourceName = jsonValue.readJsonFile("name" , resourceFileJSON);
	private String resourceDisplayName = jsonValue.readJsonFile("customName" , resourceFileJSON);

	@BeforeClass(groups = "ACCEPTANCE")
	public void createResource() throws MalformedURLException, IOException {

		//Create resource by Rest
		RootRestMethods.createResource(filePath, "");
	}

	@Test(groups = "ACCEPTANCE")
	public void testAResourceCanBeAssociatedToARoomEnabled() {
		HomeAdminPage homeAdminPage = new HomeAdminPage();

		//Associate the resource to a room
		RoomsPage roomsPage = homeAdminPage.clickConferenceRoomsLink();
		RoomInfoPage roomInfoPage = roomsPage.doubleClickOverRoomName(roomName);
		RoomResourceAssociationsPage roomsResourceAssociationsPage = roomInfoPage
				.clickResourceAssociationsLink();
		roomsResourceAssociationsPage.clickAddResourceToARoom(resourceDisplayName);
		roomsPage = roomsResourceAssociationsPage.clickSaveBtn();
		UIMethods.refresh();
		roomsPage.clickConferenceRoomsLink();

		//Assertion for TC02 
		Assert.assertTrue(roomsResourceAssociationsPage.searchResource(resourceDisplayName));	
	}

	@AfterClass(groups = "ACCEPTANCE")
	public void deleteResource() throws MalformedURLException, IOException {

		//Delete resource with API rest method
		RootRestMethods.deleteResource(resourceName);
	}
}



