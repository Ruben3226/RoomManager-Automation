package framework.common;

import static framework.common.AppConfigConstants.IMPLICIT_WAIT;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import framework.selenium.SeleniumDriverManager;

/**
 * 
 * @author Ruben Blanco
 *
 */
public class UIMethods {	

	public static void doubleClick(WebElement webElement) {
		Actions action = new Actions(SeleniumDriverManager.getManager().getDriver());
		action.doubleClick(webElement);
		action.perform();
	}

	public static boolean isElementPresent(By element) {
		boolean present;
		try {			
			SeleniumDriverManager.getManager().getDriver().findElement(element);
			present=true;
		} catch (NoSuchElementException ex) {
			present = false;
		}
		return present;
	}

	public static void refresh() {
		SeleniumDriverManager.getManager().getDriver().navigate().refresh();
	}

	/**
	 * This method is a workaround to wait for mask to disappear for Chrome
	 * @param webElement
	 */
	public static void waitForMaskDisappears(WebElement webElement) {
		boolean value = false;
		while (value == false) {
			try {
				webElement.click();
				value = true;
			} catch (Exception e) {
				value = false;
			}
		} 
	}

	/**
	 * [YA]This method verifies if an element is displayed
	 * @param locator
	 * @return boolean
	 */
	public static boolean isElementDisplayed(By locator) {
		try
		{
			SeleniumDriverManager.getManager().getDriver().manage().timeouts()
			.implicitlyWait(IMPLICIT_WAIT/5, TimeUnit.SECONDS);
			SeleniumDriverManager.getManager().getDriver().findElement(locator);
			return true;
		} catch (NoSuchElementException e) {
			e.printStackTrace();
			return false;
		} finally {
			SeleniumDriverManager.getManager().getDriver().manage().timeouts()
			.implicitlyWait(IMPLICIT_WAIT, TimeUnit.SECONDS);
		}
	}

	/** 
	 * [YA]This method takes screenshots 
	 * @param filePath 
	 * @param fileName 
	 * @throws IOException 
	 */ 
	public static void takeScreenShot(String filePath, String fileName) throws IOException { 
		try { 
			File scrFile = ((TakesScreenshot)SeleniumDriverManager.getManager().getDriver())
					.getScreenshotAs(OutputType.FILE); 
			FileUtils.copyFile(scrFile, new File(filePath + fileName)); 
		} catch (Exception e) { 
			e.printStackTrace(); 
		} 
	} 
}
