package com.udacity.jwdnd.course1.cloudstorage;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.io.File;
import java.util.Arrays;
import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class CloudStorageApplicationTests {

	@LocalServerPort
	private int port;

	private WebDriver driver;

	@BeforeAll
	static void beforeAll() {
		WebDriverManager.firefoxdriver().setup();
	}

	@BeforeEach
	public void beforeEach() {
		this.driver = new FirefoxDriver();
	}

	@AfterEach
	public void afterEach() {
		if (this.driver != null) {
			driver.quit();
		}
	}



	public void doLogout(){
		driver.get("http://localhost:" + this.port + "/home");
		WebElement buttonSignUp = driver.findElement(By.id("logout-button"));
		buttonSignUp.click();
	}

	public void navToNotes(){
		driver.get("http://localhost:" + this.port + "/home");
		WebElement notesTab = driver.findElement(By.id("nav-notes-tab"));
		notesTab.click();
	}

	public void navToCredentials(){
		driver.get("http://localhost:" + this.port + "/home");
		WebElement credentialsTab = driver.findElement(By.id("nav-credentials-tab"));
		credentialsTab.click();
	}

	public void submitNote(String title, String description){
		WebElement noteTitleInput = driver.findElement(By.id("note-title"));
		WebElement noteDescriptionInput = driver.findElement(By.id("note-description"));
		noteTitleInput.click();
		noteTitleInput.clear();
		noteTitleInput.sendKeys(title);
		noteDescriptionInput.click();
		noteDescriptionInput.clear();
		noteDescriptionInput.sendKeys(description);
		WebElement noteSubmitButton = driver.findElement(By.id("save-new-note-button"));
		noteSubmitButton.click();
	}

	public void submitCredential(String url, String username, String password){
		WebElement credentialUrlInput = driver.findElement(By.id("credential-url"));
		WebElement credentialUsernameInput = driver.findElement(By.id("credential-username"));
		WebElement credentialPasswordInput = driver.findElement(By.id("credential-password"));

		credentialUrlInput.click();
		credentialUrlInput.clear();
		credentialUrlInput.sendKeys(url);

		credentialUsernameInput.click();
		credentialUsernameInput.clear();
		credentialUsernameInput.sendKeys(username);

		credentialPasswordInput.click();
		credentialPasswordInput.clear();
		credentialPasswordInput.sendKeys(password);

		WebElement credentialSubmitButton = driver.findElement(By.id("save-new-credential-button"));
		credentialSubmitButton.click();
	}

	public void addNote(String title, String description){
		// open notes-tab
		navToNotes();

		// locate the add-note button
		WebElement addNoteButton = driver.findElement(By.id("add-note-button"));
		addNoteButton.click();

		// enter the new note and submit
		submitNote(title,description);
	}

	public void addCredential(String url, String username, String password){
		// open notes-tab
		navToCredentials();

		// locate the add-note button
		WebElement addCredentialButton = driver.findElement(By.id("add-credential-button"));
		addCredentialButton.click();

		// enter the new note and submit
		submitCredential(url,username, password);
	}

	public void editCredential(Integer credentialId, String url, String username, String newPassword){
		// open notes-tab
		navToCredentials();

		// locate the add-note button
		WebElement editCredentialButton = driver.findElement(By.id("credential-edit-" + credentialId ));
		editCredentialButton.click();

		// enter the new note and submit
		submitCredential(url, username, newPassword);
	}

	public void editNote(Integer noteId, String title, String description){
		// open notes-tab
		navToNotes();

		// locate the edit credential button
		WebElement editNoteButton = driver.findElement(By.id("note-edit-" + noteId.toString()));
		editNoteButton.click();

		// enter the new note and submit
		submitNote(title,description);
	}

	@Test
	@Order(1)
	public void getLoginPage() {
		driver.get("http://localhost:" + this.port + "/login");
		Assertions.assertEquals("Login", driver.getTitle());
	}

	@Test
	@Order(2)
	public void verifyUnauthorizedAccessRestrictions(){
		driver.get("http://localhost:" + this.port + "/home");
		Assertions.assertEquals("Login", driver.getTitle());

		driver.get("http://localhost:" + this.port + "/signup");
		Assertions.assertEquals("Sign Up", driver.getTitle());

		getLoginPage();
	}

	@Test
	@Order(3)
	public void signUpLoginLogout(){
		String firstName = "Max";
		String lastName = "Mustermann";
		String userName = "mamu";
		String password = "mamu";

		doMockSignUp(firstName,lastName,userName,password);

		doLogIn(userName, password);
		Assertions.assertEquals("Home", driver.getTitle());

		doLogout();

		verifyUnauthorizedAccessRestrictions();
	}

	@Test
	@Order(4)
	public void addNoteWithVerification() throws InterruptedException{
		String firstName = "Note";
		String lastName = "Nerd";
		String userName = "none";
		String password = "none";

		String title = "Title";
		String description = "Description";

		doMockSignUp(firstName,lastName,userName,password);
		doLogIn(userName, password);
		Assertions.assertEquals("Home", driver.getTitle());
		addNote(title,description);

		driver.get("http://localhost:" + this.port + "/home");
		navToNotes();

		WebElement firstNoteTitle = driver.findElement(By.id("note-title-1"));
		WebElement firstNoteDesc = driver.findElement(By.id("note-description-1"));
		Assertions.assertEquals(title,firstNoteTitle.getText());
		Assertions.assertEquals(description,firstNoteDesc.getText());
		doLogout();
		//Thread.sleep(100000);
	}

	@Test
	@Order(5)
	public void editNoteWithVerification(){
		String title = "Title";
		String changedTitle = "changed Title";
		String description = "Description";
		String changedDescription = "changed Description";

		doLogIn("none", "none");
		driver.get("http://localhost:" + this.port + "/home");
		navToNotes();

		// repeat existing tests from add-note stage
		WebElement firstNoteTitle = driver.findElement(By.id("note-title-1"));
		WebElement firstNoteDesc = driver.findElement(By.id("note-description-1"));
		Assertions.assertEquals(title,firstNoteTitle.getText());
		Assertions.assertEquals(description,firstNoteDesc.getText());

		WebElement editButton = driver.findElement(By.id("note-edit-1"));
		editButton.click();

		editNote(1,changedTitle,changedDescription);

		//WebDriverWait webDriverWait = new WebDriverWait(driver, 3);
		driver.get("http://localhost:" + this.port + "/home");
		navToNotes();

		//webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("note-title-1")));
		//webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("note-description-1")));

		WebElement changedNoteTitle = driver.findElement(By.id("note-title-1"));
		WebElement changedNoteDesc = driver.findElement(By.id("note-description-1"));
		Assertions.assertEquals(changedTitle,changedNoteTitle.getText());
		Assertions.assertEquals(changedDescription,changedNoteDesc.getText());

	}
	@Test
	@Order(6)
	public void deleteNoteWithVerification() throws InterruptedException {

		doLogIn("none", "none");
		driver.get("http://localhost:" + this.port + "/home");
		navToNotes();

		Assertions.assertTrue(driver.findElements(By.id("note-title-1")).size() > 0);
		driver.get("http://localhost:" + this.port + "/notedelete?noteId=1");
		driver.get("http://localhost:" + this.port + "/home");
		navToNotes();
		Assertions.assertFalse(driver.findElements(By.id("note-title-1")).size() > 0);
	}

	public void addFiveNotes() throws InterruptedException {

		doLogIn("none", "none");
		Assertions.assertEquals("Home", driver.getTitle());

		List<String> iterationMessage = Arrays.asList("FIRST", "SECOND", "THIRD", "FOURTH", "FIFTH");
		for (String noteText : iterationMessage){
			addNote(noteText,noteText);
		}
		navToNotes();
	}

	@Test
	public void credentialsWorkflow() throws InterruptedException{
		String firstName = "Bob";
		String lastName = "Uncle";
		String userName = "bobun";
		String password = "gandalf";

		String url = "www.google.de";
		String websiteUsername = "fabulousBob";
		String websitePassword = "catsAndDogs";

		String changedUrl = "www.facebook.com";
		String changedWebsiteUsername = "hirschhausen";
		String changedWebsitePassword = "dogsAndCats";

		// sign up test-user
		doMockSignUp(firstName,lastName,userName,password);
		doLogIn(userName, password);
		Assertions.assertEquals("Home", driver.getTitle());

		// add first credential
		addCredential(url, websiteUsername, websitePassword);

		// verify credential was created and verify
		driver.get("http://localhost:" + this.port + "/home");
		navToCredentials();
		//Thread.sleep(3000);
		WebElement firstCredUrl = driver.findElement(By.id("credential-url-1"));
		WebElement firstCredUsername = driver.findElement(By.id("credential-username-1"));
		WebElement firstCredPassword = driver.findElement(By.id("credential-password-1"));

		// check values - especially password is not plaintext
		Assertions.assertEquals(url,firstCredUrl.getText());
		Assertions.assertEquals(websiteUsername,firstCredUsername.getText());
		Assertions.assertNotEquals(websitePassword,firstCredPassword.getText());
		String firstHash = firstCredPassword.getText();

		// check that password is unencrypted in modal
		WebElement editNoteButton = driver.findElement(By.id("credential-edit-1"));
		System.out.println(editNoteButton.getAttribute("data3"));
		Assertions.assertEquals(editNoteButton.getAttribute("data3"),websitePassword);

		editCredential(1,changedUrl,changedWebsiteUsername,changedWebsitePassword);

		// verify credential was edited and verify
		driver.get("http://localhost:" + this.port + "/home");
		navToCredentials();
		WebElement firstChangedCredUrl = driver.findElement(By.id("credential-url-1"));
		WebElement firstChangedCredUsername = driver.findElement(By.id("credential-username-1"));
		WebElement firstChangedCredPassword = driver.findElement(By.id("credential-password-1"));

		// check values - especially password is not plaintext
		Assertions.assertEquals(changedUrl,firstChangedCredUrl.getText());
		Assertions.assertEquals(changedWebsiteUsername,firstChangedCredUsername.getText());
		Assertions.assertNotEquals(changedWebsitePassword,firstChangedCredPassword.getText());
		Assertions.assertNotEquals(firstHash,firstChangedCredPassword.getText());

		driver.get("http://localhost:" + this.port + "/home");
		navToCredentials();

		Assertions.assertTrue(driver.findElements(By.id("credential-url-1")).size() > 0);
		driver.get("http://localhost:" + this.port + "/credentialdelete?credentialId=1");
		driver.get("http://localhost:" + this.port + "/home");
		navToCredentials();
		Assertions.assertFalse(driver.findElements(By.id("credential-url-1")).size() > 0);

		doLogout();

		//Thread.sleep(100000);
	}

	/**
	 * PLEASE DO NOT DELETE THIS method.
	 * Helper method for Udacity-supplied sanity checks.
	 **/
	private void doMockSignUp(String firstName, String lastName, String userName, String password){
		// Create a dummy account for logging in later.

		// Visit the sign-up page.
		WebDriverWait webDriverWait = new WebDriverWait(driver, 2);
		driver.get("http://localhost:" + this.port + "/signup");
		webDriverWait.until(ExpectedConditions.titleContains("Sign Up"));
		
		// Fill out credentials
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputFirstName")));
		WebElement inputFirstName = driver.findElement(By.id("inputFirstName"));
		inputFirstName.click();
		inputFirstName.sendKeys(firstName);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputLastName")));
		WebElement inputLastName = driver.findElement(By.id("inputLastName"));
		inputLastName.click();
		inputLastName.sendKeys(lastName);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputUsername")));
		WebElement inputUsername = driver.findElement(By.id("inputUsername"));
		inputUsername.click();
		inputUsername.sendKeys(userName);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputPassword")));
		WebElement inputPassword = driver.findElement(By.id("inputPassword"));
		inputPassword.click();
		inputPassword.sendKeys(password);

		// Attempt to sign up.
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("buttonSignUp")));
		WebElement buttonSignUp = driver.findElement(By.id("buttonSignUp"));
		buttonSignUp.click();

		/* Check that the sign up was successful. 
		// You may have to modify the element "success-msg" and the sign-up 
		// success message below depening on the rest of your code.
		*/
		Assertions.assertTrue(driver.findElement(By.id("success-msg")).getText().contains("You successfully signed up!"));
	}

	
	
	/**
	 * PLEASE DO NOT DELETE THIS method.
	 * Helper method for Udacity-supplied sanity checks.
	 **/
	private void doLogIn(String userName, String password)
	{
		// Log in to our dummy account.
		driver.get("http://localhost:" + this.port + "/login");
		WebDriverWait webDriverWait = new WebDriverWait(driver, 2);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputUsername")));
		WebElement loginUserName = driver.findElement(By.id("inputUsername"));
		loginUserName.click();
		loginUserName.sendKeys(userName);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputPassword")));
		WebElement loginPassword = driver.findElement(By.id("inputPassword"));
		loginPassword.click();
		loginPassword.sendKeys(password);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("login-button")));
		WebElement loginButton = driver.findElement(By.id("login-button"));
		loginButton.click();

		webDriverWait.until(ExpectedConditions.titleContains("Home"));

	}

	/**
	 * PLEASE DO NOT DELETE THIS TEST. You may modify this test to work with the 
	 * rest of your code. 
	 * This test is provided by Udacity to perform some basic sanity testing of 
	 * your code to ensure that it meets certain rubric criteria. 
	 * 
	 * If this test is failing, please ensure that you are handling redirecting users 
	 * back to the login page after a succesful sign up.
	 * Read more about the requirement in the rubric: 
	 * https://review.udacity.com/#!/rubrics/2724/view 
	 */
	@Test
	public void testRedirection() {
		// Create a test account
		doMockSignUp("Redirection","Test","RT","123");
		
		// Check if we have been redirected to the log in page.
		Assertions.assertEquals("http://localhost:" + this.port + "/login", driver.getCurrentUrl());
	}

	/**
	 * PLEASE DO NOT DELETE THIS TEST. You may modify this test to work with the 
	 * rest of your code. 
	 * This test is provided by Udacity to perform some basic sanity testing of 
	 * your code to ensure that it meets certain rubric criteria. 
	 * 
	 * If this test is failing, please ensure that you are handling bad URLs 
	 * gracefully, for example with a custom error page.
	 * 
	 * Read more about custom error pages at: 
	 * https://attacomsian.com/blog/spring-boot-custom-error-page#displaying-custom-error-page
	 */
	@Test
	public void testBadUrl() {
		// Create a test account
		doMockSignUp("URL","Test","UT","123");
		doLogIn("UT", "123");
		
		// Try to access a random made-up URL.
		driver.get("http://localhost:" + this.port + "/some-random-page");
		Assertions.assertFalse(driver.getPageSource().contains("Whitelabel Error Page"));
	}


	/**
	 * PLEASE DO NOT DELETE THIS TEST. You may modify this test to work with the 
	 * rest of your code. 
	 * This test is provided by Udacity to perform some basic sanity testing of 
	 * your code to ensure that it meets certain rubric criteria. 
	 * 
	 * If this test is failing, please ensure that you are handling uploading large files (>1MB),
	 * gracefully in your code. 
	 * 
	 * Read more about file size limits here: 
	 * https://spring.io/guides/gs/uploading-files/ under the "Tuning File Upload Limits" section.
	 */
	@Test
	@Order(8)
	public void testLargeUpload() {
		// Create a test account
		doMockSignUp("Large File","Test","LFT","123");
		doLogIn("LFT", "123");

		// Try to upload an arbitrary large file
		WebDriverWait webDriverWait = new WebDriverWait(driver, 2);
		String fileName = "upload5m.zip";

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("fileUpload")));
		WebElement fileSelectButton = driver.findElement(By.id("fileUpload"));
		fileSelectButton.sendKeys(new File(fileName).getAbsolutePath());

		WebElement uploadButton = driver.findElement(By.id("uploadButton"));
		uploadButton.click();
		try {
			webDriverWait.until(ExpectedConditions.presenceOfElementLocated(By.id("success")));
		} catch (org.openqa.selenium.TimeoutException e) {
			System.out.println("Large File upload failed");
		}
		Assertions.assertFalse(driver.getPageSource().contains("HTTP Status 403 – Forbidden"));
	}

	public void testUpload() {
		// Create a test account
		doMockSignUp("Fileupload","Tester","ftest","1234");
		doLogIn("ftest", "1234");

		// Try to upload an arbitrary large file
		WebDriverWait webDriverWait = new WebDriverWait(driver, 2);
		String fileName = "upload5m.zip";

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("fileUpload")));
		WebElement fileSelectButton = driver.findElement(By.id("fileUpload"));
		fileSelectButton.sendKeys(new File(fileName).getAbsolutePath());

		WebElement uploadButton = driver.findElement(By.id("uploadButton"));
		uploadButton.click();
		try {
			webDriverWait.until(ExpectedConditions.presenceOfElementLocated(By.id("success")));
		} catch (org.openqa.selenium.TimeoutException e) {
			System.out.println("Large File upload failed");
		}
		Assertions.assertFalse(driver.getPageSource().contains("HTTP Status 403 – Forbidden"));

	}

	@Test
	@Order(9)
	public void fileTestSuite() throws InterruptedException {
		testUpload();
		//Thread.sleep(2000);
		WebDriverWait webDriverWait = new WebDriverWait(driver, 2);

		// verify success message
		Assertions.assertTrue(driver.findElements(By.id("success-link")).size() > 0);
		WebElement successLink = driver.findElement(By.id("success-link"));
		//Thread.sleep(2000);
		successLink.click();
		//Thread.sleep(2000);

		//verify testfile is in filelist
		Assertions.assertTrue(driver.findElements(By.id("file-2")).size() > 0);
		//Thread.sleep(2000);
		// try to add same file again -> expected to fail
		String fileName = "upload5m.zip";
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("fileUpload")));
		WebElement fileSelectButton = driver.findElement(By.id("fileUpload"));
		fileSelectButton.sendKeys(new File(fileName).getAbsolutePath());
		WebElement uploadButton = driver.findElement(By.id("uploadButton"));
		//Thread.sleep(2000);
		uploadButton.click();
		Thread.sleep(2000);
		// verify error message
		Assertions.assertTrue(driver.findElements(By.id("error-link")).size() > 0);
		WebElement errorLink = driver.findElement(By.id("error-link"));
		errorLink.click();

		//Thread.sleep(500000);
	}


}
