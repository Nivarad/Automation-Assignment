package PageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.Random;

public class LoginPage {

    protected WebDriver driver;
    @FindBy(id = "email")
    private WebElement loginMail;
    @FindBy(id = "passwd")
    private WebElement password;
    @FindBy(xpath = "//span[normalize-space()='Sign in']")
    private WebElement loginButton;
    @FindBy(id = "email_create")
    private WebElement signupMail;
    @FindBy(xpath = "//button[@name=\"SubmitCreate\"]")
    private WebElement createAccountButton;
    public LoginPage(WebDriver driver) {

        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public String createMail() {
        Random rand = new Random();
        int emailNumber = rand.nextInt(10000);
        String alertString;
        String alert;

        while (true) {
            signupMail.clear();
            signupMail.sendKeys("arady" + emailNumber + "@gmail.com");

            createAccountButton.click();
            try {
                alert = driver.findElement(By.xpath("//li[contains(text(),'An account using this email address has already be')]")).getText();
            } catch (Exception e) {

                break;
            }

            if (alert.trim().equals("An account using this email address has already been registered. Please enter a valid password or request a new one."))
                emailNumber = rand.nextInt(10000);
            else
                break;


        }
        WebElement heading = driver.findElement(By.className("page-heading"));
        if (heading.getText().trim().equals("CREATE AN ACCOUNT"))
            return "arady" + emailNumber;
        else
            return "";

    }

    public boolean verifyUserExist(String email, String password) {
        if (driver.getCurrentUrl().equals("http://automationpractice.com/index.php?controller=my-account")) {
            if (driver.findElement(By.tagName("h1")).getText().equals("MY ACCOUNT"))
                return true;
        } else {
            driver.get("http://automationpractice.com/index.php?controller=my-account");
            this.loginMail.sendKeys(email);
            this.password.sendKeys(email);
            this.loginButton.click();
            if (driver.findElement(By.tagName("h1")).getText().equals("MY ACCOUNT"))
                return true;
            else {
                WebElement error = driver.findElement(By.xpath("//body/div/div[@class='columns-container']/div[@class='container']/div[@class='row']/div[@class='center_column col-xs-12 col-sm-12']/div[1]"));
                if (error.getText().toLowerCase().contains("Authentication failed."))
                    return false;
            }


        }
        return false;
    }

    public boolean buyCartAuthentication(String email, String password) {
        if (driver.getCurrentUrl().equals("http://automationpractice.com/index.php?controller=authentication&multi-shipping=0&display_guest_checkout=0&back=http%3A%2F%2Fautomationpractice.com%2Findex.php%3Fcontroller%3Dorder%26step%3D1%26multi-shipping%3D0")) {
            this.loginMail.sendKeys(email);
            this.password.sendKeys(password);
            this.loginButton.click();
            return true;
        } else
            return false;
    }
}
