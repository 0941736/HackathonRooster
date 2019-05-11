#include <Wire.h>
#include <LiquidCrystal_I2C.h>
#include <ESP8266WiFi.h>
#include <WiFiClient.h>

const char* ssid = "hackathon";
const char* pass = "cmi-hackathon";
const char* host = "192.168.1.100";  //ip van de server
const int port = 6789;
// Set the LCD address to 0x27 for a 16 chars and 2 line display
LiquidCrystal_I2C lcd(0x27, 16, 4);

void setup() {
  Serial.begin(115200);

  //Initialize the LCD
  lcd.begin();
  lcd.backlight();
  lcd.print("TEST SCREEN");

  //setup wifi
  Serial.println();
  Serial.print("conecting to: ");
  Serial.println(ssid);

  WiFi.mode(WIFI_STA);
  delay(1000);
  WiFi.begin(ssid, pass);

  while (WiFi.status() != WL_CONNECTED) {
    delay(1000);
    Serial.print(". ");
  }

  Serial.println();
  Serial.print("My IP: ");
  Serial.println(WiFi.localIP());
  long rssi = WiFi.RSSI();
  Serial.print("RSSI: ");
  Serial.print(rssi);
  Serial.println(" dBm");

}

void loop() {
  WiFiClient client;

  //char data[6] = "12300";
  String roomId = "0;H.1.318";


  while (!client.connect(host, port))
  {
    Serial.println(". ");
  }


  Serial.println();
  Serial.print("Connected to IP: ");
  Serial.println(host);

  Serial.println("Sending string to server: ");
  Serial.println(roomId);
  client.println(roomId);
  delay(100);

  String currentTimeRaw = client.readStringUntil(';');
  String currentTime = currentTimeRaw.substring(12, 14) + ":" + currentTimeRaw.substring(14, 16);
  Serial.println(currentTime);
  Serial.println(currentTimeRaw.substring(4));
  String beginTimeRaw = client.readStringUntil(';');
  String beginTime = beginTimeRaw.substring(8, 10) + ":" + beginTimeRaw.substring(10, 12);
  Serial.println(beginTime);
  Serial.println(beginTimeRaw);
  String endTimeRaw = client.readStringUntil(';');
  String endTime = endTimeRaw.substring(8, 10) + ":" + endTimeRaw.substring(10, 12);
  Serial.println(endTimeRaw);
  Serial.println(endTime);
  String subject = client.readStringUntil(';');
  Serial.println(subject);
  String reserver = client.readStringUntil(';');
  Serial.println(reserver);
  bool freeClassroom;
  Serial.println(beginTimeRaw.substring(4, 12).toInt() - currentTimeRaw.substring(8, 16).toInt());
  if (beginTimeRaw.substring(4, 12).toInt() - currentTimeRaw.substring(8, 16).toInt()>0)
  {
    freeClassroom = true;
  } else {
    freeClassroom = false;
  }

  if (freeClassroom == false) {
    Serial.print("Reserved from:      ");
    Serial.print(beginTime);
    Serial.print(" till ");
    Serial.println(endTime);

    lcd.clear();
    lcd.print("Reserved from:      ");
    lcd.print(subject);
    Serial.println(subject.length());
    for (int i = 0; i < 20 - subject.length(); i++) {
      lcd.print(" ");
    }
    lcd.print(beginTime);
    lcd.print(" till ");
    lcd.print(endTime);
    lcd.print("    ");
    lcd.print(reserver);

    delay(1000);
  } else {
    Serial.print("Classroom is free");

    lcd.clear();
    lcd.print("Free untill:        ");
    lcd.print(subject);
    Serial.println(subject.length());
    for (int i = 0; i < 20 - subject.length(); i++) {
      lcd.print(" ");
    }
    lcd.print(beginTime);
    lcd.print(" to ");
    lcd.print(endTime);
    lcd.print("      ");
    lcd.print(reserver);
  }
  client.flush();
  client.stop();
  Serial.println("Connection Closed");


  delay(10000);
  //  als de persoon niet in de klas is
  //  if(userID != classRoom) {
  //
  //  }
  //
  //  expired = (begintTime - currentTime > 10 min)
  //  if the person isn't in his classroom within 10 minutes
}
