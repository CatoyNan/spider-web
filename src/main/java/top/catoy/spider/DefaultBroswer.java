package top.catoy.spider;

import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * @ClassName DefaultBroswer
 * @Description TODO
 * @Author admin
 * @Date 2020-02-01 17:59
 * @Version 1.0
 **/
public class DefaultBroswer implements Broswer{
    private static Logger logger = LoggerFactory.getLogger(DefaultBroswer.class);

//    //驱动路径
//    private static String DRIVERPATH = "/Users/admin/Desktop/chromedriver 2";
//
//    static {System.setProperty("webdriver.chrome.driver",DRIVERPATH);}
    //驱动路径
    private static String DRIVERPATH = "/Users/admin/Desktop/geckodriver";

    static {System.setProperty("webdriver.gecko.driver",DRIVERPATH);}

    //驱动配置

//    FirefoxBinary firefoxBinary = new FirefoxBinary();

    //驱动
    private WebDriver webDriver = null;

    //WebDriverWait
    private WebDriverWait webDriverWait = null;

    //网址URL
    private String url = null;

    public static Broswer init() {
//        logger.info("start init broswer");
//        Broswer defaultBroswer = new DefaultBroswer();
//        ChromeOptions chromeOptions = new ChromeOptions();
//        WebDriver webDriver = new ChromeDriver(chromeOptions);
//        webDriver.manage().window().maximize();
//        defaultBroswer.setWebDriver(webDriver);
//        logger.info("init broswer success");
        logger.info("start init broswer");
        Broswer defaultBroswer = new DefaultBroswer();
        FirefoxOptions firefoxOptions = new FirefoxOptions();
        WebDriver webDriver = new FirefoxDriver(firefoxOptions);
        webDriver.manage().window().maximize();
        defaultBroswer.setWebDriver(webDriver);
        WebDriverWait webDriverWait = new WebDriverWait(webDriver, 20);
        defaultBroswer.setWebDriverWait(webDriverWait);
        logger.info("init broswer success");
        return defaultBroswer;
    }

    @Override
    public void close() {

    }

    @Override
    public boolean setWindowSize() {
        return false;
    }

    @Override
    public void openTab() {

    }

    @Override
    public Broswer setCookie(String cookieStr, String domain, String path) {
        this.webDriver.manage().deleteAllCookies();
        this.addCookie(cookieStr,domain,path);
        return this;
    }

    @Override
    public Broswer addCookie(String cookieStr, String domain, String path) {
        if (cookieStr == null || cookieStr.length() == 0) {
            return this;
        }
        String pattern = "(\\S*?)=(.*?)(?:;|$)";
        Pattern r = Pattern.compile(pattern);
        Matcher m = r.matcher(cookieStr);
        while (m.find()) {
            logger.info("{}={};",m.group(1),m.group(2));
            this.webDriver.manage().addCookie(new Cookie(m.group(1),m.group(2),domain,path,null));
        }
        System.out.println(this.webDriver.manage().getCookies().toString());
        return this;
    }

    @Override
    public boolean deleteCookie() {
        return false;
    }

    @Override
    public Cookie getCookie() {
        return null;
    }

    @Override
    public Broswer setUrl(String url) {
        this.url = url;
        return this;
    }

    @Override
    public Broswer get() {
        this.webDriver.get(url);
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return this;
    }

    @Override
    public void setWebDriver(WebDriver webDriver) {
        this.webDriver = webDriver;
    }

    @Override
    public WebDriver getWebDriver() {
        return this.webDriver;
    }

    @Override
    public List<WebElement> cssSelector(String cssSelector) {
        return this.getWebDriver().findElements(By.cssSelector(cssSelector));
    }

    @Override
    public WebElement cssSelectorOne(String cssSelector) {
        return this.getWebDriver().findElement(By.cssSelector(cssSelector));
    }

    @Override
    public void setWebDriverWait(WebDriverWait webDriverWait) {
        this.webDriverWait = webDriverWait;
    }

    @Override
    public WebDriverWait getWebDriverWait() {
        return this.webDriverWait;
    }

    public static void main(String[] args) {
        Broswer defaultBroswer = DefaultBroswer.init().setUrl("https://www.baidu.com").get();
        defaultBroswer.getWebDriverWait().until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("div#lg")));
        WebElement element = defaultBroswer.cssSelectorOne("div#lg");
        logger.error(element.getTagName());
    }
}
