#include "WiFi.h"
#include <PubSubClient.h>

#define WIFI_SSID "w17w"
#define WIFI_PASSWORD "2tm31337W101"
#define DEVICE_TOKEN "mG6zq8Tv2wdGwBN7pPMF" //mG6zq8Tv2wdGwBN7pPMF -- Access Token of device from MAIOT, of Washing Machine A

// #define OUTPUT_PIN 2  // if led does not working, try testing with led embedded at ESP32 (OUTPUT_PIN 2)

int led = 13; // the pin the LED is connected to

static unsigned char ledState = LOW;
static unsigned long ledCameOn = 0;


WiFiClient wifi;
PubSubClient psc(wifi);

//if somehow can't upload to device, try press the boot button on device first, when arduino ide loading to do connection
//problem - why need to press boot button to perform connection
// -> solution either include transistor or everytime upload arduino sketch to esp32, need to press the boot button of esp32, when at the prompt below shows line "Connecting...."

void setup() {
  // put your setup code here, to run once: 
  Serial.begin(115200);
  Serial.println("Connecting to WiFi");
  WiFi.begin(WIFI_SSID, WIFI_PASSWORD);

  while(WiFi.status() != WL_CONNECTED)
    delay(500);

    Serial.println("WiFi Connected");
    psc.setServer("iot.maiot.academy", 1883);

    psc.setCallback(triggerDevice); 
    psc.connect(DEVICE_TOKEN, DEVICE_TOKEN, NULL);
    psc.subscribe("v1/devices/me/rpc/request/+");

  // pinMode(OUTPUT_PIN, OUTPUT); // Declare the LED as an output
     pinMode(led, OUTPUT);

}

void triggerDevice(char* topic, byte* payload, unsigned int length)
{
  Serial.println(topic);

   // If the LED has been on for at least 5 seconds then turn it off.
  if(ledState == HIGH)
  {
    if(millis()-ledCameOn > 5000)
    {
      digitalWrite(13,LOW);
      ledState = LOW;
    }
  }
 // digitalWrite(OUTPUT_PIN, HIGH); // Turn on the OUTPUT_PIN of ESP32 
  digitalWrite(led, HIGH); // Turn on the LED at pin 13 of ESP32 
  delay(5000);
  // digitalWrite(OUTPUT_PIN, LOW); // Turn off the OUTPUT_PIN of ESP32 
  digitalWrite(led, LOW); // Turn off the LED at pin 13 of ESP32 
}

void loop() {
  //static unsigned char ledState = LOW;
  //static unsigned long ledCameOn = 0;
  // put your main code here, to run repeatedly:
  psc.loop();
  delay(1000);
}
