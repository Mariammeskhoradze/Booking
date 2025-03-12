package ge.tbc.testautomation.steps;

import ge.tbc.testautomation.pages.FlightsListPage;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.WaitForSelectorState;
import ge.tbc.testautomation.data.Constants;
import io.qameta.allure.Step;
import org.testng.asserts.SoftAssert;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class FlightsListSteps extends FlightsListPage {

    SoftAssert softAssert = new SoftAssert();

    public FlightsListSteps(Page page) {
        super(page);
    }

    @Step("Choose the cheapest flight option")
    public FlightsListSteps chooseCheapestFlies() {
        cheapestOption().waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE));

        cheapestOption().scrollIntoViewIfNeeded();
        page.waitForTimeout(1000);

        cheapestOption().click(new Locator.ClickOptions().setForce(true));

        return this;
    }

    @Step("Click on the 'Only' button to choose Booking.com")
    public FlightsListSteps clickOnOnlyBtnToChooseBooking() {
        assertThat(bookingBtn()).isVisible();
        bookingBtn().scrollIntoViewIfNeeded();
        bookingBtn().hover();
        assertThat(onlyBtn()).isVisible();
        onlyBtn().click();
        page.waitForTimeout(5000);
        return this;
    }

    @Step("Validate that the first displayed flight is the cheapest")
    public FlightsListSteps validateFirstFlightIsCheapest() {
        List<Double> prices = new ArrayList<>();

        do {
            Locator showMoreBtn = showMoreBtn();
            if (showMoreBtn.isVisible()) {
                showMoreBtn.scrollIntoViewIfNeeded();
                showMoreBtn.click();
            }

            Locator lastPriceElement = prices().get(prices().size() - 1);
            lastPriceElement.waitFor(new Locator.WaitForOptions().setTimeout(5000));

            for (Locator priceElement : prices()) {
                priceElement.scrollIntoViewIfNeeded();
                String priceText = priceElement.innerText().replaceAll("[^\\d]", "");
                Double price = Double.parseDouble(priceText);
                if (!prices.contains(price)) {
                    prices.add(price);
                }
            }

        } while (showMoreBtn().isVisible());

        if (prices.isEmpty()) {
            throw new AssertionError(Constants.PRICE_ASSERT_ERROR_MSG);
        }

        Double firstPrice = prices.get(0);
        Double minPrice = Collections.min(prices);

        softAssert.assertTrue(firstPrice.equals(minPrice),
                Constants.FIRST_PRICE_ASSERT_MSG + firstPrice + Constants.MIN_PRICE + minPrice);

        softAssert.assertAll();

        return this;
    }

    @Step("Validate that flight prices are sorted in ascending order")
    public FlightsListSteps validatePricesSortedInAscendingOrder() {
        List<Double> priceValues = new ArrayList<>();

        for (Locator priceElement : prices()) {
            String priceText = priceElement.textContent().trim().replaceAll("[^\\d]", "");
            if (!priceText.isEmpty()) {
                priceValues.add(Double.parseDouble(priceText));
            }
        }

        for (int i = 0; i < priceValues.size() - 1; i++) {
            Double currentPrice = priceValues.get(i);
            Double nextPrice = priceValues.get(i + 1);

            softAssert.assertTrue(currentPrice <= nextPrice,
                    Constants.ASCENDING_PRICES_ASSERT_MSG +
                            Constants.PRICE + currentPrice + Constants.GREATER_THAN + nextPrice);
        }

        softAssert.assertAll();

        return this;
    }

    @Step("Validate that all flights displayed are from Booking.com")
    public FlightsListSteps validateAllFlightsAreFromBooking() {

        String expectedText = bookingBtn().textContent().trim();

        for (Locator i : bookingText()) {
            String actualText = i.textContent().trim();

            softAssert.assertTrue(
                    actualText.equals(expectedText),
                    Constants.TEXT_ASSERT_MSG + expectedText + Constants.BUT_FOUND + actualText
            );
        }
        softAssert.assertAll();
        return this;
    }
}
