# Spring CORS Vulnerability Demo

## Running
1. Launch the vulnerable Spring Boot CORS support demo application by running the “./gradlew :vulnerable-app:bootRun” command in your terminal/shell.
2. In a second terminal window or tab, launch the vulnerable Spring Security CORS support demo application by executing the “./gradlew :vulnerable-spring-security-app:bootRun” command.
3. In a third terminal window or tab, launch the CORS vulnerability demo app by executing the command, “./gradlew :test-cors-app:bootRun”.
4. Open a browser window and navigate to “http://localhost:8080”, which will load the index page of the CORS vulnerability demo app, and automatically make credentialed, cross-origin requests to the apps launched in steps 2 and 3, and display the request headers and the request cookies in the browser window.
5. **Refresh/reload** the page in the browser, and note that the “Access-Control-Allow-Credentials” is set to “true”, the “Access-Control-Allow-Origin” header is set to “http://localhost:8080”, and the “cors-spring-vuln-demo” and “cors-spring-security-vuln-demo” cookies (which were automatically set by the response in step 5) are listed for each of the example cross-origin requests.
