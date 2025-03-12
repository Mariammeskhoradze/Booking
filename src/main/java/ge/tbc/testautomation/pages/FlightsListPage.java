package ge.tbc.testautomation.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import java.util.List;

public class FlightsListPage {
   protected final Page page;

    public FlightsListPage(Page page) {
        this.page = page;
    }

    public Locator cheapestOption() {
        return page.locator("//span[contains(text(), 'Cheapest')]");
    }

    public Locator bookingBtn() {
        return page.locator("//label[@data-name='BOOKINGFLIGHTS']");
    }

    public Locator onlyBtn() {
        return page.locator("//button[@aria-label='Booking.com only']");
    }

    public Locator showMoreBtn() {
        return page.locator("//div/a[contains(text(), 'Show more results')]");
    }

    public List<Locator> prices() {
        return page.locator("//span[@class='price option-text']").all();
    }

    public List<Locator> bookingText() {
        return page.locator("//span[contains(text(), 'Booking.com')]").all();
    }
}
