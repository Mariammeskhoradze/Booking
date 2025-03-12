package ge.tbc.testautomation.pages;

import com.microsoft.playwright.Page;
import com.microsoft.playwright.Locator;

public class MainPage {
    protected final Page page;

    public MainPage(Page page) {
        this.page = page;
    }


    public Locator flightsSection() {
        return page.locator("#flights");
    }

}
