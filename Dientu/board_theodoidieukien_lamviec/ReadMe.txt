Project : monitor environmental condition.

+4 sensors :
MQ135 : air sensor for air quality.
DHT11 : humidity, temp sensor.
photo transistor : light intensity.
microphone : acoustic noise.
Hall sensor : magnetic field.

+Board : TI MSP432 P401R LaunchPad

Prepare : 
++Buy sensors, Sd card module and others.
++Connect sensors to board.
++Write code to get value from sensor.
++Write info to Sdcard.

1)Read : air quality, humidity, temperature, light intensity, noise, magnetic field
2)6 Leds for each : condition : on (red): bad; off : OK
3)Store on SdCard value (every 30 seconds)
File : condition.txt
-Date (format : Wed 4 Apr 2016 10:20:20)
air quality        : ### degree
humidity          : ### %
temperature   : ### C
light intensity   : ### lux
noise               : ### V or ### dB
magnetic field : ### Gauss
4)Software on computer by Java swing
Read condition.txt
Sketch line graph for 
	every 30 seconds : red point for bad condition
	average value of every hour, every minute, every day







