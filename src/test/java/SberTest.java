import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.concurrent.TimeUnit;

public class SberTest {

    WebDriver driver;
    String baseURL;

    @Before
    public void Setup() {
        System.setProperty("webdriver.chrome.driver", "Z:\\IT.Proganie\\TestDrivers\\chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        driver.manage().window().maximize();
        baseURL = "http://www.sberbank.ru/ru/person";
        driver.get(baseURL);
    }

    @Test
    public void insuranceTest() {
        driver.findElement(By.xpath("//*[@id=\"main-page\"]/div[4]/div/div/div/button")).click();


        Wait<WebDriver> wait = new WebDriverWait(driver, 5, 1000);
        WebElement insurance = driver.findElement(By.xpath("//A[@class='kitt-service-menu__link'][text()='Страхование']/.."));
        wait.until(ExpectedConditions.visibilityOf(insurance));
        
        insurance.click();


        WebElement travelTitle = driver.findElement(By.xpath("//H3[text()='Страхование для путешественников']"));
        wait.until(ExpectedConditions.visibilityOf(travelTitle));
        travelTitle.click();

        WebElement travelTitle2 = driver.findElement(By.xpath("//h1[text()='Страхование путешественников']"));
        wait.until(ExpectedConditions.visibilityOf(travelTitle2));
        Assert.assertEquals("Страхование путешественников", travelTitle2.getText());
        // сравнил название заголовка

        driver.findElement(By.xpath("//span[text()='Оформить онлайн']")).click();
        WebElement title3 = driver.findElement(By.xpath("//H2[text()='Страхование путешественников']"));
        wait.until(ExpectedConditions.visibilityOf(title3));
        //дождался заголовка страницы

        driver.findElement(By.xpath("//DIV[@class='online-card-program selected']")).click();
        // Нажал на минимальный пакет. Данный шаг отключен, так как он выбран изначально + из-за него тест падает

        driver.findElement(By.xpath("//BUTTON[text()='Оформить']")).click();

        WebElement lastname = driver.findElement(By.xpath("//INPUT[@id='surname_vzr_ins_0']"));
        WebElement firstName = driver.findElement(By.xpath("//INPUT[@id='name_vzr_ins_0']"));
        WebElement date = driver.findElement(By.xpath("//INPUT[@id='birthDate_vzr_ins_0']"));
        wait.until(ExpectedConditions.visibilityOf(lastname));

        lastname.sendKeys("Иванов");
        firstName.sendKeys("Иван");
        lastname.sendKeys("");
        // Фамилия, имя страхованных

        driver.findElement(By.xpath("//INPUT[@id='person_lastName']")).sendKeys("Петров");
        driver.findElement(By.xpath("//INPUT[@id='person_firstName']")).sendKeys("Петр");
        driver.findElement(By.xpath("//INPUT[@id='person_middleName']")).sendKeys("Петрович");

        driver.findElement(By.xpath("//label[text()='Женский']")).click();
        // Заполнил ФИО, рождения и выбрал женский пол Страхователя

        driver.findElement(By.xpath("//INPUT[@id='passportSeries']")).sendKeys("1234");
        driver.findElement(By.xpath("//INPUT[@id='passportNumber']")).sendKeys("123456");
        driver.findElement(By.xpath("//INPUT[@id='documentIssue']")).sendKeys("Отдоелом ");
        // Заполнил паспортные данные

        date.sendKeys("10102000");
        driver.findElement(By.xpath("//INPUT[@id='documentDate']")).sendKeys("10012015");
        driver.findElement(By.xpath("//INPUT[@id='person_birthDate']")).sendKeys("10101990");
        // Даты вынесены в отдельную часть кода, так как они вызывали окно, которое не давало заполнить след поле. Нажатие на кнопку в календаре ломало весь тест

        driver.findElement(By.xpath("//BUTTON[@class='btn btn-primary page__btn']")).click();
        WebElement alert = driver.findElement(By.xpath("//div[@class='alert-form alert-form-error']"));
        Assert.assertEquals("При заполнении данных произошла ошибка", alert.getText());
        // Заполнены не все обязательные поля отсутствует, вместо этого возникает такая ошибка
        WebElement alert2 = driver.findElement(By.xpath("//SPAN[@class='invalid-validate form-control__message'][text()='Поле не заполнено.']"));
        Assert.assertEquals("Поле не заполнено.", alert2.getText());
        // Проверка другой ошибки, что поле не заполнено
    }


    @After
    public void shutDown() {
    //    driver.quit();
    }
}
