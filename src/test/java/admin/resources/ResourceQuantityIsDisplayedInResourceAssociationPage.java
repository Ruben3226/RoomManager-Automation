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
import main.java.pages.admin.conferencerooms.RoomInfoPage;
import main.java.pages.admin.conferencerooms.RoomResourceAssociationsPage;
import main.java.pages.admin.conferencerooms.RoomsPage;
import main.java.pages.admin.resources.ResourceAssociationsPage;
import main.java.pages.admin.resources.ResourceCreatePage;
import main.java.pages.admin.resources.ResourceInfoPage;
import main.java.pages.admin.resources.ResourcesPage;
import main.java.rest.RoomManagerRestMethods;
import main.java.utils.readers.ExcelReader;

/**
 * TC32: Verify that resource quantity associated to a room is displayed on {Quantity} column
 * in {ResourceInfo>Resource Associations} form when it is selected
 * @author Marco Llano
 */
public class ResourceQuantityIsDisplayedInResourceAssociationPage {
	private ResourcesPage resourcesPage;
	private ResourceAssociationsPage resourceAssociationsPage;
	
	//ExcelReader is used to read resources data from excel
	private ExcelReader excelReader = new ExcelReader(EXCEL_INPUT_DATA);
	private List<Map<String, String>> resourceDataList = excelReader.getMapValues("Resources");
	private String resourceName = resourceDataList.get(2).get("ResourceName");

	@Test(groups = "FUNCTIONAL")
	public void testResourceQuantityIsDisplayedInResourceAssociationPage() throws InterruptedException {
		HomeAdminPage homeAdminPage = new HomeAdminPage();
		resourcesPage = homeAdminPage.clickResourcesLink();

		//Variables declaration and initialize
		String resourceDisplayName = resourceDataList.get(2).get("ResourceDisplayName");
		String resourceDescription = resourceDataList.get(2).get("Description");
		String iconTitle = resourceDataList.get(2).get("Icon");
		String quantity = resourceDataList.get(2).get("Value");
		String roomDisplayName = resourceDataList.get(2).get("Room Name");

		//Create a resource
		ResourceCreatePage resourceCreatePage  = resourcesPage.clickAddResourceBtn();		
		resourcesPage = resourceCreatePage.clickResourceIcon()
				.selectResourceIcon(iconTitle)
				.setResourceName(resourceName)
				.setResourceDisplayName(resourceDisplayName)
				.setResourceDescription(resourceDescription)
				.clickSaveResourceBtn();

		//Associate a resource to a room by resourcesPage display name
		RoomsPage roomsPage = homeAdminPage.clickConferenceRoomsLink();
		RoomInfoPage roomInfoPage = roomsPage.doubleClickOverRoomName(roomDisplayName);
		RoomResourceAssociationsPage roomResourceAssociation = roomInfoPage.clickResourceAssociationsLink();
		roomsPage = roomResourceAssociation.clickAddResourceToARoom(resourceDisplayName)
				.changeValueForResourceFromAssociatedList(resourceDisplayName, quantity)
				.clickSaveBtn();

		//Assertion for TC32
		resourcesPage = homeAdminPage.clickResourcesLink();
		ResourceInfoPage resourceInfoPage = resourcesPage.openResourceInfoPage(resourceDisplayName);
		resourceAssociationsPage = resourceInfoPage.clickResourceAssociationLink();
		String result = resourceAssociationsPage.getResourceQuantityByRoomDisplayName(roomDisplayName);
		Assert.assertEquals(result, "x " + quantity);
	}

	@AfterMethod(groups = "FUNCTIONAL")
	public void afterMethod() throws MalformedURLException, IOException {	
		resourcesPage = resourceAssociationsPage.clickCloseBtn();		
		RoomManagerRestMethods.deleteResource(resourceName);
	}
}