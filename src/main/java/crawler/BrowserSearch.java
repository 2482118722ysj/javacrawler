package crawler;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.sql.SQLOutput;
import java.util.List;

public class BrowserSearch {
    public static void main(String[] args) {
        /*
        有两种方法来打开指定页面
        1 将chromedriver.exe放到游览器启动的统一路径下(注意将chromedriver.exe与浏览器版本的对应关系)
        String path = "F:\\Google\\Chrome\\Application\\chromedriver.exe";
        System.setProperty("webdriver.chrome.driver",path);
        WebDriver webDriver=new ChromeDriver();
        webDriver.get("https://www.lagou.com/zhaopin/Java/?labelWords=label");
        * */
        //方法2：
        //设置chromedriver路径
        System.setProperty("webdriver.chrome.driver",BrowserSearch.class.getClassLoader().getResource("chromedriver.exe").getPath());
        //根据不同的浏览器来设置对象
        ChromeOptions option = new ChromeOptions();
        //获取浏览器的启动程序的绝对路径
        option.setBinary("F:\\Google\\Chrome\\Application\\chrome.exe");
        WebDriver webDriver = new ChromeDriver(option);
        //打开指定的浏览器地址
        webDriver.get("https://www.lagou.com/zhaopin/Java/?labelWords=label");
        //打开页面用红包提示，点击取消
        WebElement redpaper =webDriver.findElement(By.xpath("//div[@class='body-btn']"));
        redpaper.click();
        //通过xpath语法来爬去网页信息
        browserSearchInfo(webDriver, "工作经验：", "应届毕业生");
        browserSearchInfo(webDriver, "学历要求：", "本科");
        browserSearchInfo(webDriver, "融资阶段：", "不限");
        browserSearchInfo(webDriver, "公司规模：", "不限");
        browserSearchInfo(webDriver, "行业领域：", "移动互联网");

        forselectInfo(webDriver);

    }

    protected static void forselectInfo(WebDriver webDriver) {
        selectWorkIfo(webDriver);
        WebElement pagenxet = webDriver.findElement(By.className("pager_next"));
        if(!pagenxet.getAttribute("class").equals("pager_next pager_next_disabled")){
            pagenxet.click();
            System.out.println("..................................");
            System.out.println("解析下一页");
            System.out.println("..................................");
            try {
                Thread.sleep(1000);  //当点击下一页，游览器还没加载出来，后面的方法就可能解析不到,此时应该让线程休眠1秒钟，在执行
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            forselectInfo(webDriver);
        }
    }

    protected static void selectWorkIfo(WebDriver webDriver) {
        List<WebElement> divTop = webDriver.findElements(By.className("p_top"));
        for (WebElement divelement : divTop) {
            String jobPositions = divelement.findElement(By.xpath("a//h3")).getText();
            System.out.print(jobPositions+"  :  ");
            String workwhere = divelement.findElement(By.xpath("a//span//em")).getText();
            System.out.print(workwhere+"  :  ");
            String money = divelement.findElement(By.xpath("../div//div//span")).getText();
            System.out.println(money);
        }
    }

    protected static void browserSearchInfo(WebDriver webDriver, String optionTitile, String optiondaugelement) {
        WebElement choseelement = webDriver.findElement(By.xpath("//li[@class='multi-chosen']//span[contains(text(),'" + optionTitile + "')]"));
        WebElement optionelement = choseelement.findElement(By.xpath("../a[contains(text(),'" + optiondaugelement + "')]"));
        optionelement.click();
    }
}
