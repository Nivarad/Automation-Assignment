package PageObjects;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;
import static org.testng.Assert.assertEquals;
public class SignupPage {
    protected WebDriver driver;
    @FindBy(id = "id_gender1")
    private WebElement gender1;
    @FindBy(id = "id_gender2")
    private WebElement gender2;
    @FindBy(name = "customer_firstname")
    private WebElement firstname;
    @FindBy(name = "customer_lastname")
    private WebElement lastname;
    @FindBy(id = "passwd")
    private WebElement password;
    @FindBy(name = "days")
    private WebElement days;
    @FindBy(name = "months")
    private WebElement months;
    @FindBy(name = "years")
    private WebElement years;
    @FindBy(name = "company")
    private WebElement company;
    @FindBy(id = "address1")
    private WebElement address1;
    @FindBy(id = "address2")
    private WebElement address2;
    @FindBy(id = "city")
    private WebElement city;
    @FindBy(id = "postcode")
    private WebElement postcode;
    @FindBy(id = "other")
    private WebElement other;
    @FindBy(name = "phone_mobile")
    private WebElement phone_mobile;
    @FindBy(id = "id_state")
    private WebElement id_state;
    @FindBy(id = "alias")
    private WebElement alias;
    @FindBy(id = "submitAccount")
    private WebElement submitButton;
    public SignupPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public boolean signup(String gender, String firstName, String lastName, String password, int days, int months, int years, String company,
                          String address1, String address2, String city, String postcode, String other, String phone_mobile, int stateIndex, String alias) {
        if (gender.toLowerCase().equals("male"))
            gender1.click();
        else if (gender.toLowerCase().equals("female"))
            gender2.click();
        this.firstname.sendKeys(firstName);
        this.lastname.sendKeys(lastName);
        this.password.sendKeys(password);
        Select select = new Select(this.days);
        select.selectByValue("" + days);
        select = new Select(this.months);
        select.selectByValue("" + months);
        select = new Select(this.years);
        select.selectByValue("" + years);
        this.company.sendKeys(company);
        this.address1.sendKeys(address1);
        this.address2.sendKeys(address2);
        this.city.sendKeys(city);
        this.postcode.sendKeys(postcode);
        this.other.sendKeys(other);
        this.phone_mobile.sendKeys(phone_mobile);
        select = new Select(this.id_state);
        select.selectByValue("" + stateIndex);
        this.alias.clear();
        this.alias.sendKeys(alias);
        submitButton.click();

        String alert = driver.findElement(By.xpath("/html[1]/body[1]/div[1]/div[2]/div[1]/div[3]/div[1]/div[1]")).getText();
        if (alert.toLowerCase().contains("errors"))
            return false;
        return true;
    }


}
