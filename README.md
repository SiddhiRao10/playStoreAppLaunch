# playStoreAppLaunch
This project showcases how to load a PlayStore app from Browserstack using App Automate with appStoreConfiguration in the capabilities, then install an app from there (in this example, we download whatsapp) and confirm the app is installed on the device.

## Requirements
- usual environment variables (BROWSERSTACK_USERNAME,BROWSERSTACK_ACCESS_KEY,BROWSERSTACK_BUILD_NAME)
- a Google account without 2FA (advised to create a new one, as your corporate and personal ones should always have 2FA)
- Google account credentials in environment variables GOOGLE_USERNAME and GOOGLE_PASSWORD
- replace the "bs://enter-your-app-location" value in BaseTest.java with your own app location

## To run your tests
`mvn test -P playstore-android`

## run your tests with debug options
`mvn test -P playstore-android -e -X`
