package Config;

public class Config {
    public static final String baseURL = "https://www.saucedemo.com/";
    public static final String productURL = "https://www.saucedemo.com/inventory.html";
    public static final String cartURL = "https://www.saucedemo.com/cart.html";
    public static final String checkoutURL = "https://www.saucedemo.com/checkout-step-one.html";
    public static final String successCheckoutURL = "https://www.saucedemo.com/checkout-step-two.html";
    public static final String completeCheckout = "https://www.saucedemo.com/checkout-complete.html";
    public static final String backPackURL = "https://www.saucedemo.com/static/media/sauce-backpack-1200x1500.0a0b85a3.jpg";
    public static final String bikeLightURL = "https://www.saucedemo.com/static/media/bike-light-1200x1500.37c843b0.jpg";
    public static final String boltShirtURL = "https://www.saucedemo.com/static/media/bolt-shirt-1200x1500.c2599ac5.jpg";
    public static final String saucePulloverURL = "https://www.saucedemo.com/static/media/sauce-pullover-1200x1500.51d7ffaf.jpg";
    public static final String redOnsieURL = "https://www.saucedemo.com/static/media/red-onesie-1200x1500.2ec615b2.jpg";
    public static final String redTattURL = "https://www.saucedemo.com/static/media/red-tatt-1200x1500.30dadef4.jpg";

    public static final String problematicImag = "https://www.saucedemo.com/static/media/sl-404.168b1cce.jpg";
    public static final String aboutURL = "https://saucelabs.com/";

    public static final String twitterURL = "https://x.com/saucelabs";
    public  static  final String facebookURL = "https://www.facebook.com/saucelabs";
    public  static final String linkedinURL = "https://www.linkedin.com/company/sauce-labs/";

    public static final String testDataPath = "D:\\Samira\\testingNotes\\GraduationProject\\SwagLab\\src\\test\\java\\test_data\\";
    public  static final String mockDataPath = "D:\\Samira\\testingNotes\\GraduationProject\\SwagLab\\src\\test\\java\\Mocks\\";


    public enum FileNames {
        CHECKOUT_TEST_DATA("checkoutTestData.json"),
        LOGIN_TEST_DATA("loginPageTestData.json"),
        PRODUCT_TEST_DATA("productDetailsData.json"),
        MOCK_DATA("mockData.json"),
        PRODUCT_NAMES_STORED("productNames.json");



        private final String fileName;
        FileNames(String fileName) {
            this.fileName = fileName;
        }

        public String getFileName() {
            return fileName;
        }
    }

}
