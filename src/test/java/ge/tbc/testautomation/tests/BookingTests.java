package ge.tbc.testautomation.tests;

import ge.tbc.testautomation.steps.FlightsListSteps;
import ge.tbc.testautomation.steps.FlightsSteps;
import ge.tbc.testautomation.steps.MainSteps;
import com.microsoft.playwright.*;
import ge.tbc.testautomation.data.Constants;
import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import ge.tbc.testautomation.steps.LightHouseSteps;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.List;

public class BookingTests {
    private Page page;
    private MainSteps mainSteps;
    private FlightsSteps flightsSteps;
    private FlightsListSteps flightsListSteps;
    private LightHouseSteps lightHouseSteps;

    @BeforeClass
    public void setUpBrowser() {
        page = Playwright.create().chromium().launch(
                new BrowserType.LaunchOptions().setHeadless(false).setArgs(List.of("--start-maximized"))
        ).newContext(new Browser.NewContextOptions().setViewportSize(null)).newPage();
    }

    @BeforeMethod
    public void setUpTest() {
        page.navigate("http://booking.com");
        mainSteps = new MainSteps(page);
        flightsSteps = new FlightsSteps(page);
        flightsListSteps = new FlightsListSteps(page);

        lightHouseSteps = new LightHouseSteps();
    }

    @Description("Validates the flight booking flow")
    @Severity(SeverityLevel.CRITICAL)
    @Test
    public void bookingFlightsTest() {
        mainSteps
                .goToFlightsSection();
        flightsSteps
                .validateFlightsPage(Constants.FLIGHT_PAGE_TEXT)
                .chooseOneWayTrip()
                .chooseFromField(Constants.DEPARTURE_FIELD)
                .chooseFlyDestination(Constants.DESTINATION_FIELD)
                .clickOnSearchBtn();
        flightsListSteps
                .chooseCheapestFlies()
                .clickOnOnlyBtnToChooseBooking()
                .validateFirstFlightIsCheapest()
                .validatePricesSortedInAscendingOrder()
                .validateAllFlightsAreFromBooking();
    }

    @Description("Measure performance of the website with a configurable score")
    @Severity(SeverityLevel.NORMAL)
    @Test
    public void testPerformanceScore() {
        lightHouseSteps.runLighthouseTest(58);
    }
}


