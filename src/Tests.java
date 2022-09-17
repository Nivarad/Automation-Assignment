import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.time.Duration;
import java.time.LocalDate;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import PageObjects.LoginPage;
import PageObjects.SignupPage;
import PageObjects.Utils;
import PageObjects.WomenPage;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

public class Tests {
    private WebDriver driver;
    private SoftAssert softAssertion;

    private String other = "Hi , I am Niv and I am really excited of the opportunity to be part of your"
            + " company , I hope you will like my assignment.";
    private int emailNumber;
    WebDriverWait wait;
    private LoginPage loginPage;
    private SignupPage signPage;
    private WomenPage womenPage;
    private Utils utils;
    private String user = "";


    @BeforeClass(description = "initating WebDriver and open website")
    public void init() {

        //create and setup WebDriver
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\arady\\Desktop\\chromedriver.exe");
        this.driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    }

    @Test(priority = 1, description = "Create an account")
    public void signupTest() {
        driver.get("http://automationpractice.com/index.php?controller=authentication&back=my-account");
        signPage = new SignupPage(driver);
        loginPage = new LoginPage(driver);

        String mail = loginPage.createMail();
        if (mail.equals(""))
            assertEquals(false, true, "No mail was generated for the test");
        String password = "12345";
        boolean error = signPage.signup("male", "Niv", "Arad", password, 9, 3, 1996, "SecuritiThings",
                "Hey BeiHar", "132 Apartment 1", "Rosh HaAyin", "48056", this.other, "0545672517", 1, "Home");
        assertEquals(error, true, "There are errors in the information that was filled ");
        boolean checkExist = loginPage.verifyUserExist(mail, password);
        assertEquals(checkExist, true, "The user wasn't created , test Failed");
        System.out.println("User : " + mail + " was created \nTest Case 1 Passed!");
        this.user = mail;


    }

    @Test(priority = 2, description = "Add items to cart")
    public void addItemsToCart() {
        womenPage = new WomenPage(driver, wait);
        womenPage.insertDressesToCart();
        String productCounter = driver.findElement(By.xpath("/html[1]/body[1]/div[1]/div[1]/header[1]/div[3]/div[1]/div[1]/div[3]/div[1]/a[1]/span[1]")).getText();
        if (Integer.parseInt(productCounter) < Integer.parseInt("2"))
            assertEquals(true, false, "Not enough products were added\nTest Case 2 Failed");
        System.out.println(productCounter + " products were added to cart\nTest Case 2 Passed!");

    }


    @Test(priority = 3, description = "Buy items found in the cart")
    public void CompleteOrder() {

        utils=new Utils(driver,wait);
        boolean empty=utils.checkIfCartEmpty();
        if(empty){
            womenPage = new WomenPage(driver, wait);
            womenPage.insertDressesToCart();
        }
        String[] strArray=utils.purchaseCart();
        if(strArray ==null)
            assertEquals(true,false,"Purchase wasn't completed");
        else
            utils.saveUserFile(strArray[0],strArray[1]);





    }

    @AfterClass(description = "closing WebDriver")
    public void closeConnection() {
        //driver.close();
    }

}
