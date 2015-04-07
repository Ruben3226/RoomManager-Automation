package test.java.tablet.search;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import jxl.read.biff.BiffException;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import main.java.pages.tablet.HomeTabletPage;
import main.java.pages.tablet.SearchPage;
import main.java.utils.AppConfigConstants;
import main.java.utils.readers.ExcelReader;

/**
 * TC14: Verify on search page that "Minimum capacity"  field,
 * only allow numbers
 * @author Jose Cabrera
 */
public class MinimumCapacityOnlyAllowNumbers {
	private SearchPage searchPage;

	@Test(groups = "NEGATIVE")
	public void testMinimumCapacityOnlyAllowNumbers () throws BiffException, IOException {
		ExcelReader excelReader = new ExcelReader(AppConfigConstants.EXCEL_INPUT_DATA);
		List<Map<String, String>> testData = excelReader.getMapValues("Search");
		String capacity = testData.get(0).get("Capacity");
		HomeTabletPage homeTabletPage = new HomeTabletPage();		
		searchPage = homeTabletPage
				.clickSearchBtn()
				.clickCollapseAdvancedBtn()
				.setMinimumCap(capacity);
		Assert.assertTrue(searchPage.isEmptyMinimumCap());
	}

	@AfterMethod(groups = "NEGATIVE")
	public void toHome() {
		searchPage.clickBackBtn();
	}
}
