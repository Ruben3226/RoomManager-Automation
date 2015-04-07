package tests.tablet.search;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import jxl.read.biff.BiffException;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import framework.common.AppConfigConstants;
import framework.pages.tablet.HomeTabletPage;
import framework.pages.tablet.SearchPage;
import framework.utils.readers.ExcelReader;

/**
 * TC12: Verify that an error message is displayed when invalid values are 
 * inserted on "Minimum Capacity" field
 * @author Jose Cabrera
 */
public class MinimumCapacityShowAmessageWhenInsertInvalidValues { 
	private SearchPage searchPage;

	@Test(groups = "NEGATIVE")
	public void testMinimumCapacityShowAmessageifInsertInvalidValues () 
			throws BiffException, IOException {
		ExcelReader excelReader = new ExcelReader(AppConfigConstants.EXCEL_INPUT_DATA);
		List<Map<String, String>> testData = excelReader.getMapValues("Search");
		String capacity = testData.get(0).get("Capacity");
		HomeTabletPage homeTabletPage = new HomeTabletPage();
		searchPage = homeTabletPage
				.clickSearchBtn()
				.clickCollapseAdvancedBtn()
				.setMinimumCap(capacity);
		Assert.assertFalse(searchPage.isEmptyMinimumCap());
	}

	@AfterMethod(groups = "NEGATIVE")
	public void toHome() {
		searchPage.clickBackBtn();
	}
}
