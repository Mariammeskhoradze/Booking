package ge.tbc.testautomation.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;


public class FlightsPage {
    protected final Page page;

    public FlightsPage(Page page) {
        this.page = page;
    }

    public Locator flightPageText() {
        return page.locator("//span[@class='title-wrapper']/h1");
    }

    public Locator dropDownBtn() {
        return page.locator("//span[contains(text(), 'Round-trip')]");
    }

    public Locator oneWayTripBtn() {
        return page.locator("//li[@aria-label='One-way']");
    }

    public Locator fromField() {
        return page.locator("//div/input[@placeholder='From?']");
    }

    public Locator list() {
        return page.locator("//span[@class='dX-j-input-wrapper']");
    }

    public Locator destinationField() {
        return page.locator("//div/input[@placeholder='To?']");
    }

    public Locator searchBtn() {
        return page.locator("//span[contains(text(), 'Search')]");
    }

    public Locator clearInputs() {
        return page.locator("//div[@class='c_neb-item-close']");
    }
}
