package PageObjects;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.util.List;

public class WomenPage {
    protected WebDriver driver;
    protected WebDriverWait wait;
    @FindBy(xpath = "/html[1]/body[1]/div[1]/div[2]/div[1]/div[3]/div[2]/ul[1]/li/div[1]/div[1]/div[1]/a[1]/img[1]")
    private List<WebElement> images;

    public WomenPage(WebDriver driver, WebDriverWait wait) {
        this.driver = driver;
        this.wait = wait;
        PageFactory.initElements(driver, this);
    }

    public void insertDressesToCart() {
        //move to the products page and create a list of products
        if (!driver.getCurrentUrl().equals("http://automationpractice.com/index.php?id_category=3&controller=category"))
            driver.get("http://automationpractice.com/index.php?id_category=3&controller=category");

        for (int i = 0; i < images.size(); i++) {
            //if the product is a dress - order it
            WebElement imageLink = images.get(i);
            if (imageLink.getAttribute("alt").toLowerCase().contains("dress")) {
                imageLink.click();
                WebElement iframe = driver.findElement(By.xpath("//iframe[@class='fancybox-iframe']"));
                driver.switchTo().frame(iframe);
                driver.findElement(By.name("Submit")).click();
                wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("cross"))).click();
                driver.switchTo().defaultContent();
            }
        }
        System.out.println("All dresses were added to the cart! \n Test Case 2 Passed");
    }
}
