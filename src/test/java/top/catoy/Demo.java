package top.catoy;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxBinary;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName Demo
 * @Description TODO
 * @Author admin
 * @Date 2020-03-10 16:47
 * @Version 1.0
 **/
public class Demo {
    private static String LOGIN_URL = "https://login.taobao.com/member/login.jhtml?spm=a1z10.3-c-s.754894437.1.752c2ca5FDjX3x&f=top&redirectURL=https%3A%2F%2Fshop148742543.taobao.com%2Fcategory.htm%3Fspm%3Da1z10.1-c-s.w5001-14954951140.3.187b4b16Xwqv2c%26search%3Dy%26scene%3Dtaobao_shop";

    public void snatch(String url) {
        FirefoxBinary firefoxBinary;
        firefoxBinary = new FirefoxBinary();
        System.setProperty("webdriver.gecko.driver","/Users/admin/Desktop/geckodriver");
        FirefoxOptions firefoxOptions = new FirefoxOptions();
        firefoxOptions.setBinary(firefoxBinary);
        FirefoxDriver driver = new FirefoxDriver(firefoxOptions);
        WebDriverWait wait = new WebDriverWait(driver, 45);
        driver.manage().window().maximize();
        System.out.println("初始化完成，开始加载登录链接:" + LOGIN_URL);
        driver.get(LOGIN_URL);
        try {
            WebElement userName = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#TPL_username_1")));
            userName.sendKeys("17858906105");
            WebElement password = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#TPL_password_1")));
            password.sendKeys("x19761216");
            password.sendKeys(Keys.ENTER);
            System.out.println("登录");
            Thread.sleep(2000);
            List<WebElement> items = new ArrayList<>();
            items = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.cssSelector("dl.item")));
            for(WebElement i:items){
                System.out.println(i.getAttribute("data-id"));
            }
//			WebElement nextPage = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div.pagination-mini>a:nth-child(3)")));
            WebElement pageInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div.pagination>form>input[name='pageNo']")));
            try {
                for(int i = 1 ; i < 36 ; i++) {
                    System.out.println("当前第" + i + "页,下一页");
//				nextPage.click();
//				driver.executeScript("var tar=arguments[0];tar.click();",nextPage);
                    pageInput.clear();
                    pageInput.sendKeys(String.valueOf(i+1));
                    pageInput.sendKeys(Keys.ENTER);
                    Thread.sleep(5000);
                    items.addAll(wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.cssSelector("dl.item"))));
                    List<WebElement> list = new ArrayList();
                    list  = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.cssSelector("dl.item")));
                    for(WebElement item : list) {
                        System.out.println(item.getAttribute("data-id"));
                    }
                    pageInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div.pagination>form>input[name='pageNo']")));
                }
            } catch (Exception e) {
                System.out.println("异常，可能已被封");
            }
//            File itemId = new File("/Users/admin/Desktop/shop.txt");
//            if (itemId.exists() && itemId.isFile()) {
//                itemId.delete();
//            }
//            itemId = new File("/Users/admin/Desktop/shop.txt");
//            FileWriter itemIdWriter = new FileWriter(itemId);
//            BufferedWriter bufferItemIdWriter = new BufferedWriter(itemIdWriter);
//            for(WebElement item : items) {
//                bufferItemIdWriter.write(item.getAttribute("data-id"));
//                bufferItemIdWriter.newLine();
//            }
//            bufferItemIdWriter.flush();
//            bufferItemIdWriter.close();
//            System.out.println("爬取结束");

        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public static void main(String[] args) {
        Demo demo2 = new Demo();
        String url = "https://shop148742543.taobao.com/category.htm?spm=a1z10.1-c-s.w5001-14954951140.3.187b4b16Xwqv2c&search=y&scene=taobao_shop";
        demo2.snatch(url);
    }
}
