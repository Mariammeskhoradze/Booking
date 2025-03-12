package ge.tbc.testautomation.steps;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import ge.tbc.testautomation.data.Constants;
import io.qameta.allure.Step;
import io.qameta.allure.Allure;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class LightHouseSteps {

    SoftAssert sfa = new SoftAssert();

    @Step("Run Lighthouse test and measure performance score")
    public void runLighthouseTest(int acceptableScore) {
        try {
            String command = Constants.CMD_EXE_LIGHTHOUSE + Constants.URL +
                    Constants.OUTPUT_HTML_PATH + Constants.REPORT_PATH +
                    Constants.HEADLESS + Constants.CATEGORIES;

            Process process = Runtime.getRuntime().exec(command);
            process.waitFor();

            File reportFile = new File(Constants.REPORT_PATH);
            if (!reportFile.exists()) {
                throw new IOException(Constants.REPORT_FILE_EXCEPTION_MSG + Constants.REPORT_PATH);
            }

            Document doc = Jsoup.parse(reportFile, Constants.UTF_8);
            Element scriptElement = doc.selectFirst(Constants.SCRIPT_LIGHTHOUSE_JSON);

            if (scriptElement == null) {
                throw new IOException(Constants.SCRIPT_ELEMENT_EXCEPTION_ERR);
            }

            String scriptContent = scriptElement.html();
            String jsonString = scriptContent.substring(scriptContent.indexOf("{"), scriptContent.lastIndexOf("}") + 1);

            JSONObject jsonObject = new JSONObject(jsonString);
            double performanceScore = jsonObject.getJSONObject(Constants.JSON_OBJ_CATEGORIES).getJSONObject(Constants.JSON_OBJ_PERFORMANCE).getDouble(Constants.SCORE) * 100;

            System.out.println(Constants.SOUT_PERF_SCORE + performanceScore);
            sfa.assertTrue(performanceScore >= acceptableScore, Constants.ASSERT_SCORE_MSG);

            try {
                String content = Files.readString(Path.of(Constants.REPORT_PATH));
                attachHtmlAsPng(content);
                sfa.assertAll();
            } catch (IOException e) {
                e.printStackTrace();
            }

        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail(Constants.ASSERT_FAIL);
        }
    }

    public void attachHtmlAsPng(String htmlContent) {
        try (Playwright playwright = Playwright.create()) {
            Browser browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(true));
            Page page = browser.newPage();

            page.setContent(htmlContent);

            byte[] screenshot = page.screenshot(new Page.ScreenshotOptions().setPath(Paths.get(Constants.SCREENSHOT_PNG)));

            Allure.addAttachment(Constants.HTML_SCREENSHOT, Constants.IMAGE_PNG, new ByteArrayInputStream(screenshot), Constants.PNG);
            browser.close();
        }
    }
}
