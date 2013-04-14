package ohtu;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

public class Tester {

    public static void main(String[] args) {
        WebDriver driver = new HtmlUnitDriver();

        driver.get("http://localhost:8080");
        System.out.println(driver.getPageSource());
        WebElement element = driver.findElement(By.linkText("login"));
        element.click();

        System.out.println("==");

        System.out.println(driver.getPageSource());
        element = driver.findElement(By.name("username"));
        element.sendKeys("pekka");
        element = driver.findElement(By.name("password"));
        element.sendKeys("akkep");
        element = driver.findElement(By.name("login"));
        element.submit();

        System.out.println("==");

        System.out.println(driver.getPageSource());

        driver = new HtmlUnitDriver();

        driver.get("http://localhost:8080");
        
        element = driver.findElement(By.linkText("login"));
        element.click();

        element = driver.findElement(By.name("username"));
        element.sendKeys("pekka");
        element = driver.findElement(By.name("password"));
        element.sendKeys("vaarapassu");
        element = driver.findElement(By.name("login"));
        element.submit();

        System.out.println("==");

        System.out.println(driver.getPageSource());
        
        
        driver = new HtmlUnitDriver();

        driver.get("http://localhost:8080");
        
        element = driver.findElement(By.linkText("login"));
        element.click();
        element = driver.findElement(By.name("username"));
        element.sendKeys("omgzolli");
        element = driver.findElement(By.name("password"));
        element.sendKeys("vaarapassu");
        element = driver.findElement(By.name("login"));
        element.submit();

        
        
        System.out.println(driver.getPageSource());
        
        System.out.println("1==");

        //Yhistetty luonti + testaus
        
        
        driver = new HtmlUnitDriver();

        driver.get("http://localhost:8080");
        
        element = driver.findElement(By.linkText("register new user"));
        element.click();
        
        
        element = driver.findElement(By.name("username"));
        element.sendKeys("kayttajar");
        element = driver.findElement(By.name("password"));
        element.sendKeys("sala1nen");
        element = driver.findElement(By.name("passwordConfirmation"));
        element.sendKeys("sala1nen");
        element.submit();

        
        System.out.println(driver.getPageSource());
        
        System.out.println("2==");

        
        
        driver = new HtmlUnitDriver();

        driver.get("http://localhost:8080");
        
        element = driver.findElement(By.linkText("login"));
        element.click();
        element = driver.findElement(By.name("username"));
        element.sendKeys("kayttajar");
        element = driver.findElement(By.name("password"));
        element.sendKeys("sala1nen");
        element.submit();
        System.out.println(driver.getPageSource());
    }
}
