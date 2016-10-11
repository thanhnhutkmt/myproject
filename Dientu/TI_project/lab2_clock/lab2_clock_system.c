//*****************************************************************************
//
// Copyright (C) 2014 Texas Instruments Incorporated - http://www.ti.com/
//
// Redistribution and use in source and binary forms, with or without
// modification, are permitted provided that the following conditions
// are met:
//
//  Redistributions of source code must retain the above copyright
//  notice, this list of conditions and the following disclaimer.
//
//  Redistributions in binary form must reproduce the above copyright
//  notice, this list of conditions and the following disclaimer in the
//  documentation and/or other materials provided with the
//  distribution.
//
//  Neither the name of Texas Instruments Incorporated nor the names of
//  its contributors may be used to endorse or promote products derived
//  from this software without specific prior written permission.
//
// THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
// "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
// LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR
// A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT
// OWNER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
// SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT
// LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
// DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY
// THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
// (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
// OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
//
//
//****************************************************************************
/*******************************************************************************
 * MSP432 Clock System - Lab
 *
 * Description: Experiment with different MSP432 DCO clock frequencies
 * DCO:
 *      - Cycle through different DCO clock frequencies in active mode.
 *      - A SysTick interval is configured to wake up & toggle the LED P1.0 output.
 *      - Different LED toggling rate indicates a change in DCO clock frequency.
 *      - Using MSP432 DriverLib built library (msp432p4xx_driverlib.lib)
 *      - instead of using source like lab 1
 *
 *
 *
 *                MSP432P401R
 *             ------------------
 *         /|\|                  |
 *          | |                  |
 *          --|RST         P1.0  |---> P1.0 LED
 *            |                  |
 *            |            P4.3  |---> MCLK [Optional]
 *            |                  |
 *            |            P1.1  |<--- Push Button
 *
 *   Dung Dang
 *   Texas Instruments Inc.
 *   April 2014
 *   Built with Code Composer Studio V6.0
 *******************************************************************************/

#include "msp432.h"
#include "driverlib.h"

uint32_t buttonPushed = 0, blink = 0, jj=0;
volatile uint32_t i;
uint32_t frequencyCycle[6] = {  CS_DCO_FREQUENCY_1_5, CS_DCO_FREQUENCY_3,
                                CS_DCO_FREQUENCY_6, CS_DCO_FREQUENCY_12,
                                CS_DCO_FREQUENCY_24, CS_DCO_FREQUENCY_48 };
#define		ALLON	0
#define 	ALLOFF	1
void ChangeAllLED(int onoff);

int main(void)
{
    volatile uint32_t ii, curFrequency;
    WDT_A_holdTimer();                        // Hold watchdog timer

    PCM_setCoreVoltageLevel(PCM_VCORE1);
    FlashCtl_setWaitState(FLASH_BANK0, 2);
    FlashCtl_setWaitState(FLASH_BANK1, 2);

    GPIO_setAsPeripheralModuleFunctionOutputPin(GPIO_PORT_P4,
            GPIO_PIN2 | GPIO_PIN3, GPIO_PRIMARY_MODULE_FUNCTION);

    P4DIR |= BIT2 | BIT3;
    P4SEL0 |= BIT2 | BIT3;                         // Output ACLK & MCLK
    P4SEL1 &= ~(BIT2 | BIT3);
    P1DIR |= BIT0;

    /* Configuring P1.0 as output and P1.1 (switch) as input */
    GPIO_setAsOutputPin(GPIO_PORT_P1, GPIO_PIN0);

    /* Confinguring P1.1 as an input and enabling interrupts */
    GPIO_setAsInputPinWithPullUpResistor(GPIO_PORT_P1, GPIO_PIN1);

    // my code
    P2OUT &= ~BIT2;                           // Clear LED to start
    P2OUT &= ~BIT1;                           // Clear LED to start
    P2OUT &= ~BIT0;                           // Clear LED to start
    P2DIR |= BIT2;                      // Configure P2.2 as output
    P2DIR |= BIT1;                      // Configure P2.1 as output
    P2DIR |= BIT0;                      // Configure P2.0 as output

    GPIO_clearInterruptFlag(GPIO_PORT_P1, GPIO_PIN1);
    GPIO_enableInterrupt(GPIO_PORT_P1, GPIO_PIN1);
    GPIO_interruptEdgeSelect(GPIO_PORT_P1, GPIO_PIN1, GPIO_HIGH_TO_LOW_TRANSITION);
    Interrupt_enableInterrupt(INT_PORT1);
    ChangeAllLED(ALLOFF);

    SysTick_enableModule();
//    SysTick_setPeriod(1500000);             // @ 1.5MHz, interrupt once per second
    SysTick_setPeriod(frequencyCycle[jj]);
    SysTick_enableInterrupt();

    Interrupt_enableMaster();
    curFrequency = 0;

    /* Initialize MCLK to run from DCO with divider = 1*/
    //#error "Insert your code here to invoke MSP432 DriverLib API to initialize MCLK to be sourced by DCO / 1"


    /* Set DCO calibrated center frequency to the option #0 from the frequencyCycle array
     * In other words, use the value frequencyCycle[0] */

    // Hint #1: frequencyCycle is an array with different DCO ranges
    // Hint #2: The API used to set the DCO to the calibrated center frequency is currently
    //          referred to as xxxxxxxxFrequencyRange.
    //#error "Insert MSP432DriverLib API call to set DCO Center Frequency to the 0th value in the frequencyCycle array"
	// frequencyCycle[0] = CS_DCO_FREQUENCY_1_5

    while (1)
    {
//    	Check pressing state of button P1.1
//    	if ((P1IN & BIT1) == BIT1) {
//    		P1OUT |= BIT0;
//    	} else {
//    		P1OUT &= ~BIT0;
//    	}
//    	continue;
        /* Cycle through DCO frequencies */
        if (buttonPushed)  // flag buttonPushed is high ? This flag is high when GPIO interrupt on P1.1 was disabled
        {
            buttonPushed = 0;
            /* Set DCO center frequency to the option #curFrequency from the frequencyCycle array*/
            //#error "Insert MSP432DriverLib API call to set DCO Center Frequency to the value frequencyCycle[curFrequency]"

            if (++curFrequency==6)
                curFrequency = 0;

            GPIO_enableInterrupt(GPIO_PORT_P1, GPIO_PIN1);

        }

        __no_operation();
    }
}

/* Port 1 ISR */
void Port1IsrHandler(void)
{
    uint32_t status, i;
    uint8_t temp;
    status = GPIO_getEnabledInterruptStatus(GPIO_PORT_P1);
    GPIO_clearInterruptFlag(GPIO_PORT_P1, status);
    //disable interrupt for a while
    GPIO_disableInterrupt(GPIO_PORT_P1, GPIO_PIN1);
    /* Toggling the output on the LED */
    buttonPushed = 1;
    /* Wake up from GPIO interrupt */
    Interrupt_disableSleepOnIsrExit();
    SysTick_disableModule(); SysTick_disableInterrupt();

    ChangeAllLED(ALLON);
    for(i=10000; i>0; i--);        // Delay
    ChangeAllLED(ALLOFF);
    for(i=10000; i>0; i--);        // Delay
    ChangeAllLED(ALLON);
    for(i=10000; i>0; i--);        // Delay
    ChangeAllLED(ALLOFF);
    for(i=10000; i>0; i--);        // Delay
    ChangeAllLED(ALLON);
    for(i=10000; i>0; i--);        // Delay
    ChangeAllLED(ALLOFF);
    for(i=10000; i>0; i--);        // Delay
    temp = P1IN & BIT1;
    while ((P1IN & BIT1) == 0)     // if = 0 means holding, wait for P1.1 release
    {

    }
    for(i=10000; i>0; i--);        // Delay
    while ((P1IN & BIT1) == 0)     // if = bit1 means not holding, wait for P1.1 become stable
    {

    }

    P2OUT |= BIT0;                  // P2.0 = 1

    SysTick_enableModule();
    SysTick_setPeriod(frequencyCycle[jj]);             // Change interrupt interval
    if (++jj == 6) {
    	jj = 0;
    }
    SysTick_enableInterrupt();

    P2OUT &= ~BIT0;                  // P2.0 = 0
}
/* SysTick ISr */
void SysTickIsrHandler(void)
{
    GPIO_toggleOutputOnPin(GPIO_PORT_P1, GPIO_PIN0);
    P2OUT ^= BIT2;                  // Toggle P2.2
    P2OUT ^= BIT1;                  // Toggle P2.1
}

#define		ALLON	0
#define 	ALLOFF	1
void ChangeAllLED(int onoff) {
	if (onoff == ALLOFF) {
		P1OUT &= ~BIT0;                  // P1.0 = 0
		P2OUT &= ~BIT0;                  // P2.0 = 0
		P2OUT &= ~BIT1;                  // P2.1 = 0
		P2OUT &= ~BIT2;                  // P2.2 = 0
	} else if (onoff == ALLON) {
		P1OUT |= BIT0;                  // P1.0 = 1
		P2OUT |= BIT0;                  // P2.0 = 1
		P2OUT |= BIT1;                  // P2.1 = 1
		P2OUT |= BIT2;                  // P2.2 = 1
	}
}
