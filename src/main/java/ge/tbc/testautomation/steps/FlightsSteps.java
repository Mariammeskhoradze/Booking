package ge.tbc.testautomation.steps;

import ge.tbc.testautomation.pages.FlightsPage;
import com.microsoft.playwright.Page;
import ge.tbc.testautomation.data.Constants;
import io.qameta.allure.Step;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;
import static org.testng.Assert.assertTrue;

public class FlightsSteps extends FlightsPage{

    public FlightsSteps(Page page) {
        super(page);
    }

    @Step("Validate that the Flights page contains the expected text: {expectedText}")
    public FlightsSteps validateFlightsPage(String expectedText) {
        flightPageText().waitFor();

        assertTrue(flightPageText().isVisible(), Constants.FLIGHT_PAGETEXT_VISIBLE_MSG);
        assertTrue(flightPageText().textContent().contains(expectedText),
                Constants.FLIGHT_TEXTCONTENT_MSG + expectedText);
        return this;
    }

    @Step("Choose one-way trip")
    public FlightsSteps chooseOneWayTrip() {
        assertThat(dropDownBtn()).isVisible();
        dropDownBtn().click();
        assertThat(oneWayTripBtn()).isVisible();
        oneWayTripBtn().click();

        return this;
    }

    @Step("Enter departure city: {field}")
    public FlightsSteps chooseFromField(String field) {
        clearInputs().click();
        fromField().fill(field);
        list().click();
        return this;
    }

    @Step("Enter destination city: {Destination}")
    public FlightsSteps chooseFlyDestination(String Destination) {
        destinationField().click();
        destinationField().fill(Destination);
        list().click();
        return this;
    }

    @Step("Click on the search button")
    public FlightsSteps clickOnSearchBtn() {
        searchBtn().click();
        return this;
    }
}
