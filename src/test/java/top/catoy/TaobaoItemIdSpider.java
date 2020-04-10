package top.catoy;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxBinary;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class TaobaoItemIdSpider {

    private final static Logger logger = LoggerFactory.getLogger(TaobaoItemIdSpider.class);

    protected FirefoxDriver driver = null;

    public TaobaoItemIdSpider() {
    }

    protected void init(boolean isWap,boolean isNeedDeleteCookie){
        logger.debug("初始化驱动");
        try {
            FirefoxBinary firefoxBinary = new FirefoxBinary();
//            FirefoxProfile firefoxProfile = new FirefoxProfile();
            System.setProperty("webdriver.gecko.driver","/Users/admin/DeskTop/geckodriver");
            FirefoxOptions firefoxOptions = new FirefoxOptions();
//            firefoxOptions.setBinary(firefoxBinary);
//            FirefoxOptions firefoxOptions = new FirefoxOptions();
//            firefoxOptions.setBinary(firefoxBinary);
            if(isWap){
                logger.debug("手机版浏览");
//                firefoxProfile.setPreference("general.useragent.override", "Mozilla/5.0 (iPhone; CPU iPhone OS 8_1_3 like Mac OS X) AppleWebKit/600.1.4 (KHTML, like Gecko) Mobile/12B466 MicroMessenger/6.2.4 NetType/WIFI Language/zh_CN");
//                firefoxOptions.setProfile(firefoxProfile);
                driver = new FirefoxDriver(firefoxOptions);
            }else{
                logger.debug("pc浏览");
//                firefoxOptions.setProfile(firefoxProfile);
                driver = new FirefoxDriver(firefoxOptions);
            }
            if (isNeedDeleteCookie) {
                logger.debug("删除原有cookie");
                driver.manage().deleteAllCookies();
            }
            logger.debug("最大化浏览器窗口");
            driver.manage().window().maximize();
        } catch (Exception e0) {
            if(driver != null){
                try{
                    logger.error("上次浏览器未关闭",e0);
                    driver.quit();
                }catch(Exception e1){
                    logger.error("关闭浏览器异常，需手动操作",e1);
                }
            }
        }
    }

    public void snatch(List<Param> params) {
        int fail = 0;
        init(false, false);
        driver.get("https://login.taobao.com/member/login.jhtml");
        try {
            Thread.sleep(15000);
        } catch (Exception e) {
        }
        WebDriverWait wait = new WebDriverWait(driver, 15);
        for (Param param : params) {
            List<String> itemIdList = new ArrayList<String>();
            int size = param.getSize();
            String url = param.getUrl() + "&pageNo=";
            logger.error("当前爬取店铺为【" + param.getUrl() + "】，一共有" + size + "页商品。");
            try {
                for(int i = 1;i <= size;i++) {
                    int time = 2000+ (2000 * (int)Math.random());
                    String tempUrl = url + i;
                    driver.get(tempUrl);
                    Thread.sleep(time);
                    try {
                        List<WebElement> items = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.cssSelector(".item")));
                        logger.error("本页首个ID为：" + items.get(0).getAttribute("data-id"));
                        for(WebElement item : items) {
                            itemIdList.add(item.getAttribute("data-id"));
                            System.out.print(" " + item.getAttribute("data-id"));
                        }
                    } catch (Exception e0) {
                        logger.error("当前页爬取异常，url：" + tempUrl);
                        e0.printStackTrace();
                        continue;
                    }
                }
            } catch (Exception e1) {
                logger.error("当前店铺爬取异常，跳过");
                fail++;
                e1.printStackTrace();
                continue;
            }
            logger.error("当前店铺开始写入文件/Users/admin/Desktop/items" + params.indexOf(param) + ".txt");
            File file = new File("/Users/admin/Desktop/shop/items" + params.indexOf(param) + ".rtf");
            try {
                if (!file.exists()) {
                    file.createNewFile();
                }
                FileWriter fileWriter = new FileWriter(file);
                BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
                for(String itemId : itemIdList) {
                    bufferedWriter.write(itemId);
                    bufferedWriter.newLine();
                }
                bufferedWriter.flush();
                bufferedWriter.close();
            } catch (Exception e2) {
                logger.error("当前店铺写入本地文件时发生错误，终止。");
                e2.printStackTrace();
            }

        }
        logger.error("所有店铺爬取结束，一共有" + fail + "家店铺出现爬取异常");
        logger.error("程序结束!");
    }

    public static void main(String[] args) {
        TaobaoItemIdSpider taobaoItemIdSpider = new TaobaoItemIdSpider();
        List<Param> params = new ArrayList<Param>();
//        params.add(new Param("https://qimi.tmall.com/category.htm?search=y",12));
//        params.add(new Param("https://sanjiaoyigui.taobao.com/category.htm?search=y",73));
        params.add(new Param("https://shop72037804.taobao.com/category.htm?search=y",40));
//        params.add(new Param("https://shop439466281.taobao.com/category.htm?search=y",2));
//        params.add(new Param("https://shop100772935.taobao.com/category.htm?search=y",60));//
//        params.add(new Param("https://shop128750514.taobao.com/category.htm?search=y",38));
//        params.add(new Param("https://shop59762318.taobao.com/category.htm?search=y",97));//
        taobaoItemIdSpider.snatch(params);
    }
}

