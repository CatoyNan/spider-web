package top.catoy.spider;

import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
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

    //驱动路径
    private static String DRIVERPATH = "/Users/admin/Desktop/geckodriver";

//    private static String DRIVERPATH = "/Users/admin/Desktop/chromedriver 2";
    static {System.setProperty("webdriver.gecko.driver",DRIVERPATH);}

    //驱动
    private WebDriver webDriver = null;

    //WebDriverWait
    private WebDriverWait webDriverWait = null;

    //网址URL
    private String url = null;

    public static Broswer init() {
        logger.info("start init broswer");
        Broswer defaultBroswer = new DefaultBroswer();
//        FirefoxOptions firefoxOptions = new FirefoxOptions();
//        ChromeOptions chromeOptions = new ChromeOptions();
//        DesiredCapabilities caps = DesiredCapabilities.chrome();
//        caps.setCapability("pageLoadStrategy", "none");
        DesiredCapabilities caps = DesiredCapabilities.firefox();
        caps.setCapability("pageLoadStrategy","eager");
        WebDriver webDriver = new FirefoxDriver(caps);
//        WebDriver webDriver = new ChromeDriver(caps);
        webDriver.manage().window().maximize();
        defaultBroswer.setWebDriver(webDriver);
        WebDriverWait webDriverWait = new WebDriverWait(webDriver, 30);
        defaultBroswer.setWebDriverWait(webDriverWait);
        logger.info("init broswer success");
        return defaultBroswer;
    }

    @Override
    public void close() {
        this.getWebDriver().quit();
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
//        System.out.println(this.webDriver.manage().getCookies().toString());
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
    public List<WebElement> cssSelector(String cssSelector, WebElement webElement) {
        return webElement.findElements(By.cssSelector(cssSelector));
    }

    @Override
    public WebElement cssSelectorOne(String cssSelector) {
        return this.getWebDriver().findElement(By.cssSelector(cssSelector));
    }

    @Override
    public WebElement cssSelectorOne(String cssSelector, WebElement webElement) {
        return webElement.findElement(By.cssSelector(cssSelector));
    }

    @Override
    public void setWebDriverWait(WebDriverWait webDriverWait) {
        this.webDriverWait = webDriverWait;
    }

    @Override
    public WebDriverWait getWebDriverWait() {
        return this.webDriverWait;
    }

    @Override
    public boolean isExist(String cssSelector) {
        try {
            WebElement element = this.getWebDriver().findElement(By.cssSelector(cssSelector));
            if (element == null) {
                return false;
            }
        } catch (Exception e){
            return false;
        }
        return true;
    }

    @Override
    public boolean isExist(String cssSelector, WebElement webElement) {
        try {
            WebElement element = webElement.findElement(By.cssSelector(cssSelector));
            if (element == null) {
                return false;
            }
        } catch (Exception e){
            return false;
        }
        return true;
    }

    public static void main(String[] args) {
        Broswer defaultBroswer = DefaultBroswer.init();
        defaultBroswer.setUrl("https://www.zhihu.com/hot").get()
                                                          .getWebDriverWait().until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("div#root")));
        System.out.println("正在使用cookie进行登入");
        defaultBroswer.setCookie("z_c0=2|1:0|10:1586685572|4:z_c0|92:Mi4xOExtckJRQUFBQUFBSU55YVVvZ2JFU1lBQUFCZ0FsVk5oRFNBWHdCMGlXUDFiZ2o2elJLYTdWWU9jNU9hUUFkUDZB|489d4fc41438f30abeee3a82b3af0d7c4cb393932e67a0cebf59a620cb651000",".zhihu.com","/").get();
        defaultBroswer.getWebDriverWait().until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("div#root")));
        System.out.println("判断是否登入");
        if (defaultBroswer.isExist("div.SignContainer-content")) {
            System.out.println("cookie登入失败");
            System.out.println("正在使用账户密码登入");
            if (defaultBroswer.isExist("div.SignFlow-tabs")){
                List<WebElement> tab = defaultBroswer.cssSelector("div.SignFlow-tabs div.SignFlow-tab");
                for (WebElement webElement:tab) {
                    if ("密码登录".equals(webElement.getText())) {
                        webElement.click();
                        WebElement userNameInput = defaultBroswer.cssSelectorOne("input[name=username]");
                        WebElement passwordInput = defaultBroswer.cssSelectorOne("input[name=password]");
                        WebElement loginButton = defaultBroswer.cssSelectorOne("button[type=submit]");
                        userNameInput.clear();
                        userNameInput.sendKeys("17858906105");
                        passwordInput.clear();
                        passwordInput.sendKeys("ffdsd4564545");
                        loginButton.click();
                        if ( defaultBroswer.isExist("div.Captcha-chineseOperator")) {
                            System.out.println("遇到验证码");
                            defaultBroswer.close();
                        }
                    }
                }
            }
        }

        if (!defaultBroswer.isExist("div.Topstory-mainColumnCard nav.TopstoryTabs a")) {
            defaultBroswer.close();
            System.out.println("不能存在标签页");
        }

        List<WebElement> tabs = defaultBroswer.cssSelector("div.Topstory-mainColumnCard nav.TopstoryTabs a");
        for (WebElement element:tabs) {
            if (element != null && "热榜".equals(element.getText())) {
                element.click();
                System.out.println("切换到热榜");
            }
        }

        if (!defaultBroswer.isExist("div.HotList-list section")) {
            defaultBroswer.close();
            System.out.println("没有数据");
        }

        defaultBroswer.getWebDriverWait().until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("div.HotList-list section")));
        List<WebElement> selections = defaultBroswer.cssSelector("div.HotList-list section");
        for (WebElement element:selections) {
            String id = defaultBroswer.cssSelectorOne("div.HotItem-rank", element).getText();
            String href = defaultBroswer.cssSelectorOne("div.HotItem-content a",element).getAttribute("href");
            String title = defaultBroswer.cssSelectorOne("div.HotItem-content a",element).getAttribute("title");
            String content = defaultBroswer.isExist("div.HotItem-content a p",element)?
                    defaultBroswer.cssSelectorOne("div.HotItem-content a p",element).getText():"";
            String img = defaultBroswer.isExist("a.HotItem-img img",element)?
                    defaultBroswer.cssSelectorOne("a.HotItem-img img",element).getAttribute("src"):"";
            System.out.println(id);
            System.out.println(href);
            System.out.println(title);
            System.out.println(content);
            System.out.println(img);
        }


        defaultBroswer.getWebDriver().quit();
    }
}
