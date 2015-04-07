package test.java.admin.conferenceroomresourceassociations;

import static main.java.utils.AppConfigConstants.EXCEL_INPUT_DATA;
import static main.java.utils.AppConfigConstants.RESOURCE1_PATH;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;
import java.util.Map;

import main.java.pages.admin.HomeAdminPage;
import main.java.pages.admin.conferencerooms.RoomInfoPage;
import main.java.pages.admin.conferencerooms.RoomResourceAssociationsPage;
import main.java.pages.admin.conferencerooms.RoomsPage;
import main.java.rest.RoomManagerRestMethods;
import main.java.selenium.UIMethods;
import main.java.utils.readers.ExcelReader;
import main.java.utils.readers.JsonReader;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

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
	private String filePath = System.getProperty("user.dir") + RESOURCE1_PATH;
	private String resourceName = jsonValue.readJsonFile("name" , RESOURCE1_PATH);
	private String resourceDisplayName = jsonValue.readJsonFile("customName" , RESOURCE1_PATH);

	@BeforeClass(groups = "ACCEPTANCE")
	public void createResource() throws MalformedURLException, IOException {

		//Create resource by Rest
		RoomManagerRestMethods.createResource(filePath, "");
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
		RoomManagerRestMethods.deleteResource(resourceName);
	}
}



