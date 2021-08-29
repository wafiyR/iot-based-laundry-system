#include "WiFi.h"
#include <PubSubClient.h>

#define WIFI_SSID "w17w"
#define WIFI_PASSWORD "2TM31337"
#define DEVICE_TOKEN "mG6zq8Tv2wdGwBN7pPMF"
#define OUTPUT_PIN 2

WiFiClient wifi;
PubSubClient psc(wifi);

//if somehow can't upload to device, try press the boot button on device first, when arduino ide loading to do connection
//problem - why need to press boot button to perform connection

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

    pinMode(OUTPUT_PIN, OUTPUT);

}

void triggerDevice(char* topic, byte* payload, unsigned int length)
{
  Serial.println(topic);
  digitalWrite(OUTPUT_PIN, HIGH);
  delay(5000);
  digitalWrite(OUTPUT_PIN, LOW);
}

void loop() {
  // put your main code here, to run repeatedly:
  psc.loop();
  delay(1000);
}
