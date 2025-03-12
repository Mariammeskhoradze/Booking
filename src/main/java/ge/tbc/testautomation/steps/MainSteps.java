package ge.tbc.testautomation.steps;

import ge.tbc.testautomation.pages.MainPage;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.LoadState;
import ge.tbc.testautomation.data.Constants;
import io.qameta.allure.Step;


public class MainSteps extends MainPage {

    public MainSteps(Page page) {
        super(page);
    }

    @Step("Navigate to Flights section")
    public MainSteps goToFlightsSection() {
        flightsSection().click();
        page.waitForLoadState(LoadState.DOMCONTENTLOADED);
        page.waitForURL(Constants.URL_FOR_WAIT);

        return this;
    }

}
