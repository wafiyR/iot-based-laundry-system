# final-year-project

Final Year Project for Degree in Bachelor of Computer Science (Software Development) with Honours in Universiti Teknikal Malaysia Melaka (UTeM). This project focuses on developing Android Mobile Application for laundry shop that is integrated with IoT (Internet of Things) using Arduino and ESP32. Basically, IoT Based Laundry System is a demonstration of the users on utilizing the laundry machines (ESP32 devices represents the laundry machines) by making payment via QR code scanning and eWallet payment gateway.

The objectives of the system are as follows:

  i) To computerize the payment procedure in laundry services.
  ii) To assist customers finding the nearest laundry shop with their location.


1) Reload E-Wallet via Paypal Sandbox:

![reload-paypal-gif](https://user-images.githubusercontent.com/62368837/132645672-e3a853bb-fed9-449d-8e39-48f8501ab847.gif)


2) Make Payment via QR Code Sanner + Paypal Sandbox:

Melaka Artificial Intelligence of Things(MAIoT) is used as the web server to communicate with ESP32.

The QR codes contain the Access Token which is generated from Melaka Artificial Intelligence of Things(MAIoT). Access Token is crucial to authenticate the connection between Android Mobile Application and ESP32 devices. Users need to scan the provided QR codes to retrieve Access Token in order to establish connection with ESP32 devices via Bluetooth Low Energy (BLE, one of ESP32 features).

ZXing libray is used for QR scan implementation.

Upon scanning the QR codes, the Android Mobile Application will ask the users permission to switch on their phone's Blueooth if it's not turn on yet. Once the phone's Bluetooth is on, the mobile app will scan available Bluetooth within the phone’s surrounding. Only when online payment via Paypal Sandbox is completed, Android Mobile Application will signal ESP32 device via WiFi connection to switch on LED.

![scan-qr-and-paypal-gif](https://user-images.githubusercontent.com/62368837/132645809-74509148-ba57-4086-976f-e1a9ec273a03.gif)



3) Android Mobile App sends signal to ESP32 device:

Android Mobile Application will make comparisons on the retrieved Access Token from Bluetooth scanning process, which it will compare between Access Token from QR code scanning output and Access Token from Bluetooth Low Energy of ESP32. Once the Access Token is authenticated (Access Token from QR codes == Access Token from ESP32), then Android Mobile Application will establish connection with the ESP32 devices via Bluetooth Low Energy and signal ESP32 device via WiFi connection to switch on the LED.

![esp32-light-up-with-blynk-app-gif](https://user-images.githubusercontent.com/62368837/132645985-eb7d328a-75be-4c9c-990c-88932c26ea1e.gif)



4) Countdown Timer:

Once online payment is done, and successfully make connection with ESP32 devices, the Android Mobile Application will direct the users to the Countdown Timer page, which it will displays the time completion on the the laundry machine's progess. 

For demonstration purpose, the time of Countdown Timer is set to 15 seconds, and when it hits 10 seconds, a notification will pop-up to inform users on the remaining time. 

![timer-gif](https://user-images.githubusercontent.com/62368837/132646197-337bbc55-c841-42f7-bd87-b26960675a81.gif)
