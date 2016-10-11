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

    GPIO_clearInterruptFlag(GPIO_PORT_P1, GPIO_PIN1);
    GPIO_enableInterrupt(GPIO_PORT_P1, GPIO_PIN1);
    GPIO_interruptEdgeSelect(GPIO_PORT_P1, GPIO_PIN1, GPIO_HIGH_TO_LOW_TRANSITION);
    Interrupt_enableInterrupt(INT_PORT1);

    SysTick_enableModule();
    SysTick_setPeriod(1500000);             // @ 1.5MHz, interrupt once per second
    SysTick_enableInterrupt();

    Interrupt_enableMaster();
    curFrequency = 0;

    /* Initialize MCLK to run from DCO with divider = 1*/
    CS_initClockSignal(CS_MCLK, CS_DCOCLK_SELECT, CS_CLOCK_DIVIDER_1);

    /* Set DCO frequency range to the option#0 from the frequencyCycle array*/
    CS_setDCOCenteredFrequency (frequencyCycle[0]);


    while (1)
    {
        /* Cycle through DCO frequencies */
        if (buttonPushed)
        {
            buttonPushed = 0;
            /* Set DCO center frequency to the option #curFrequency from the frequencyCycle array*/
            CS_setDCOCenteredFrequency(frequencyCycle[curFrequency]);
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
    uint32_t status;

    status = GPIO_getEnabledInterruptStatus(GPIO_PORT_P1);
    GPIO_clearInterruptFlag(GPIO_PORT_P1, status);
    //disable interrupt for a while
    GPIO_disableInterrupt(GPIO_PORT_P1, GPIO_PIN1);
    /* Toggling the output on the LED */
    buttonPushed = 1;
    /* Wake up from GPIO interrupt */
    Interrupt_disableSleepOnIsrExit();
}

/* SysTick ISr */
void SysTickIsrHandler(void)
{
    GPIO_toggleOutputOnPin(GPIO_PORT_P1, GPIO_PIN0);
}
