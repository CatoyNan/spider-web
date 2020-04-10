package top.catoy.spider;

import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;

/**
 * 浏览器抽象接口
 */
public interface Broswer {
    /**
     * 初始化浏览器
     */
    static Broswer init(){
        return null;
    }

    /**
     * 关闭浏览器
     */
    void close ();

    /**
     * 设置页面窗口大小
     */

    boolean setWindowSize();

    /**
     * 打开一个标签页
     */
    void openTab();

    /**
     * 设置cookie
     */
    Broswer setCookie(String cookieStr, String domain, String path);

    /**
     * 增加cookie
     */
    Broswer addCookie(String cookieStr, String domain, String path);

    /**
     * 删除cookie
     */
    boolean deleteCookie();

    /**
     * 获取浏览器的cookie
     * @return
     */
    Cookie getCookie();

    /**
     * 设置URL
     */
    Broswer setUrl(String url);

    /**
     * 发送请求
     */
    Broswer get();

    /**
     * 设置webDriver
     * @param webDriver
     */
    void setWebDriver(WebDriver webDriver);

    /**
     * 获取webDriver
     */
    WebDriver getWebDriver();

}
