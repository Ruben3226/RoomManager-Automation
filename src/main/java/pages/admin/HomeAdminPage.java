package main.java.pages.admin;

import static main.java.utils.AppConfigConstants.URL_ADMIN;
import main.java.selenium.UIMethods;

/**
 * 
 * @author Ruben Blanco
 *
 */
public class HomeAdminPage extends AbstractMainMenu {
	
	public HomeAdminPage () {
		driver.get(URL_ADMIN);
		UIMethods.refresh();
	}
}
