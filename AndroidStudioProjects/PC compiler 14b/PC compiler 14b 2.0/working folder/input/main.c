#include "C:\Documents and Settings\Administrator\Desktop\my project\myclock\code\main.h"
#include "C:\Documents and Settings\Administrator\Desktop\my project\myclock\code\Lib\KeyPad_PIC\KeyPad_PIC.c"
#include "C:\Documents and Settings\Administrator\Desktop\my project\myclock\code\Lib\LM35\LM35_PIC.c"
#include "C:\Documents and Settings\Administrator\Desktop\my project\myclock\code\Lib\GLCD_PIC\GLCD_PIC.c"
#include "C:\Documents and Settings\Administrator\Desktop\my project\myclock\code\Lib\DS1307\DS1307_PIC.c"
//#include "M24C64.c"

#int_AD
void  AD_isr(void) 
{
  getADC();
}

#int_TBE
void  TBE_isr(void)
{

}

#int_RDA
void  RDA_isr(void) 
{

}

#int_SSP
void  SSP_isr(void) 
{

}

#int_TIMER0
void TIMER0_isr (void) 
{   
//  keyScan();
}

// My device info var
int envTemp = 0;
int page = 0;

void main()
{
   set_tris_a(0xE3);
   set_tris_b(0x00);
   set_tris_c(0x80);
   set_tris_d(0x0);
   set_tris_e(0x0);
   
   setup_adc_ports(sAN0|sAN1|VSS_VDD);
   setup_adc(ADC_CLOCK_INTERNAL);
   setup_timer_0(RTCC_INTERNAL|RTCC_DIV_1);
   setup_timer_1(T1_DISABLED);
   setup_timer_2(T2_DIV_BY_1,1,1);
   setup_ccp1(CCP_PWM);
   set_pwm1_duty(0);
   setup_comparator(NC_NC_NC_NC);// This device COMP currently not supported by the PICWizard
   enable_interrupts(INT_AD);
   enable_interrupts(INT_TBE);
   enable_interrupts(INT_RDA);
   enable_interrupts(INT_SSP);
   //enable_interrupts(INT_TIMER0);
   disable_interrupts(GLOBAL);
   setup_oscillator(OSC_8MHZ);

   // TODO: USER CODE!!

   init_GLCD(); 
   init_DS1307();
   init_keyPad();
   //startADC(tempSensor, &envTemp);   
   
   //showWelcomeScreen();
   while(1) {
      keyScan();
   }
}

void showWelcomeScreen() {
  char deviceName[] = "My Clock";
  char state[] = "Ready";
  
  moveToRC(0, 0);
  printc(77);
//!  prints(deviceName);
//!  
//!  moveToRC(3, 49);
//!  prints(state);
  
  movetoRC(0, 0);
}

void inputTime() {
   
}

void showSettingTimeScreen() {

}

void keyScan() {
   int temp = 9;
   
//!   Disable_interrupts(INT_KEYPAD);
//!   Disable_interrupts(GLOBAL);
   temp = getPressButton();

   switch (temp)
   {
      case 0 :
        clearScreen(0);
        break;
      case 1 :
        //getTime();
        clearScreen(0xFF);
        break;
      case 2 :
        do{
          eeromShowDataPage(page);
          page++;
        } while (page != 0);
        //showSettingTimeScreen();
        break;
      case 3 :
        eeromShowNormalCharData();
        break;
      case 4 :
        break;
      case 5 :
        break;
      case 6 :
        break;
      case 7 :
        break;
      case 8 :
        break;
      case 9 :
      default :
      
     }
//!     enable_interrupts(INT_KEYPAD);
//!     enable_interrupts(GLOBAL);
}

void eeromShowDataPage(int page){
  long address, nextPageAddress;
  int data;
  address = page * 32;
  nextPageAddress = (page + 1) * 32;
  
  i2c_start();
  i2c_write(M24C64writeAdd);
  i2c_write((int)(address >> 8));
  i2c_write((int)(address & 0x00FF));
  i2c_start();
  i2c_write(M24C64readAdd);
  do {
    data = i2c_read(1);
    sendData(data);
    address++;
  } while(address < nextPageAddress);
  i2c_stop();
}

void eeromShowNormalCharData(){
  int charCode = 0;
  do {
    getCharArray(charCode);
    printCharacter(character);
    charCode++;
  } while (charCode < 144);
}
