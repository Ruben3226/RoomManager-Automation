package framework.pages.admin;

import static framework.common.AppConfigConstants.URL_ADMIN;
import framework.common.UIMethods;

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
