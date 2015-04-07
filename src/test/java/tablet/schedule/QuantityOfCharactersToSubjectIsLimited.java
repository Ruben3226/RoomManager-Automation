package test.java.tablet.schedule;

import static main.java.utils.AppConfigConstants.EXCEL_INPUT_DATA;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;
import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import main.java.pages.tablet.HomeTabletPage;
import main.java.pages.tablet.SchedulePage;
import main.java.rest.RoomManagerRestMethods;
import main.java.utils.readers.ExcelReader;

/**
 * TC25: Verify that the quantity of characters in the subject text box should be limited
 * @author Asael Calizaya
 *
 */
public class QuantityOfCharactersToSubjectIsLimited {
	private ExcelReader excelReader = new ExcelReader(EXCEL_INPUT_DATA);
	private List<Map<String, String>> meetingData = excelReader.getMapValues("MeetingData");

	private String organizer = meetingData.get(3).get("Organizer");
	private String subject = meetingData.get(3).get("Subject");
	private String password = meetingData.get(3).get("Password");
	private String roomName = meetingData.get(1).get("Room");
	private String authentication = organizer + ":" + password;

	@Test(groups = "NEGATIVE")
	public void testQuantityOfCharactersToSubjectIsLimited() {
		String startTime = meetingData.get(3).get("Start time");
		String endTime = meetingData.get(3).get("End time");
		String attendee = meetingData.get(3).get("Attendee");
		String body = meetingData.get(3).get("Body");

		HomeTabletPage homeTabletPage = new HomeTabletPage();
		SchedulePage schedulePage = homeTabletPage.clickScheduleBtn();
		schedulePage
		.createMeeting(organizer, subject, startTime, endTime, attendee, body, password)
		.clickOkButton();

		/*Fails, an error message should be displayed when more than expected 
		quantity of characters is inserted in Subject text box*/
		Assert.assertTrue(schedulePage.isMessageErrorPopUpDisplayed());
	}

	@AfterMethod(groups = "NEGATIVE")
	public void deleteMeetings() throws MalformedURLException, IOException {
		RoomManagerRestMethods.deleteMeeting(roomName, subject, authentication);
	}
}