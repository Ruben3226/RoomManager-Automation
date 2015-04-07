package test.java.admin.conferenceroomresourceassociations;

import static main.java.utils.AppConfigConstants.EXCEL_INPUT_DATA;
import static main.java.utils.AppConfigConstants.RESOURCE1_PATH;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;
import java.util.Map;

import jxl.read.biff.BiffException;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import main.java.pages.admin.HomeAdminPage;
import main.java.pages.admin.conferencerooms.RoomInfoPage;
import main.java.pages.admin.conferencerooms.RoomResourceAssociationsPage;
import main.java.pages.admin.conferencerooms.RoomsPage;
import main.java.pages.admin.resources.ResourcesPage;
import main.java.rest.RoomManagerRestMethods;
import main.java.selenium.UIMethods;
import main.java.utils.readers.ExcelReader;
import main.java.utils.readers.JsonReader;

/**
 * TC06: Verify that resources quantity can be modify when it is associated to a room.
 * @author Juan Carlos Guevara
 */
public class ResourcesQuantityCanBeModifyWhenItIsAssociatedToARoom {
	
	//Reading resource data from an .xls file
	private ExcelReader excelReader = new ExcelReader(EXCEL_INPUT_DATA);
	private List<Map<String, String>> testData = excelReader.getMapValues("APIResources");
	private String roomName = testData.get(0).get("Room Name");
	private String quantity = testData.get(0).get("Value");

	//Reading json resource information
	private JsonReader jsonValue = new JsonReader();
	private String filePath = System.getProperty("user.dir") + RESOURCE1_PATH;
	private String resourceName = jsonValue.readJsonFile("name" , RESOURCE1_PATH);
	private String resourceDisplayName = jsonValue.readJsonFile("customName" , RESOURCE1_PATH);

	@BeforeClass(groups = "ACCEPTANCE")
	public void associateAresourceCreated() throws MalformedURLException, IOException {

		//Create resource by Rest
		RoomManagerRestMethods.createResource(filePath, "");
		HomeAdminPage homeAdminPage = new HomeAdminPage();				
		ResourcesPage resourcesPage = homeAdminPage.clickResourcesLink();

		//Associate resource to a room
		RoomsPage roomsPage = resourcesPage.clickConferenceRoomsLink();
		RoomInfoPage roomInfoPage = roomsPage.doubleClickOverRoomName(roomName);
		RoomResourceAssociationsPage roomResourceAssociationsPage = roomInfoPage
				.clickResourceAssociationsLink();
		roomResourceAssociationsPage.clickAddResourceToARoom(resourceDisplayName);
		roomsPage = roomResourceAssociationsPage.clickSaveBtn();
	}

	@Test(groups = "ACCEPTANCE")
	public void testResourcesQuantityCanBeModifyWhenItIsAssociatedToARoom() {

		//Change resource quantity
		HomeAdminPage homeAdminPage = new HomeAdminPage();
		RoomsPage roomsPage = homeAdminPage.clickConferenceRoomsLink();
		RoomInfoPage roomInfoPage = roomsPage.doubleClickOverRoomName(roomName);
		RoomResourceAssociationsPage roomResourceAssociationsPage = roomInfoPage.
				clickResourceAssociationsLink()
				.changeValueForResourceFromAssociatedList(resourceDisplayName,quantity);
		roomsPage = roomResourceAssociationsPage.clickSaveBtn();
		UIMethods.refresh();
		roomsPage.doubleClickOverRoomName(roomName);	
		roomResourceAssociationsPage = roomInfoPage.clickResourceAssociationsLink();

		//Assertion for TC06 
		Assert.assertEquals(roomResourceAssociationsPage.
				getResourceAmount(resourceDisplayName),quantity);		
	}

	@AfterClass(groups = "ACCEPTANCE")
	public void deleteResource() throws InterruptedException, BiffException, IOException {
		
		//Delete resource with API rest method
		RoomManagerRestMethods.deleteResource(resourceName);
	}
}