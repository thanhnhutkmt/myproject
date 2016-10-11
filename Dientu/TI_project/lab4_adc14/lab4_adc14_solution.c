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
// MSP432 ADC14 Lab
// 
//****************************************************************************
//******************************************************************************
//  MSP432 - ADC14, Sample A15, AVcc Ref, Drive PWM output to an LED
//
//   Description: A single sample is made on A15 with reference to AVcc.
//   Manually trigger the conversion. ADC14 sourced by SYSCLK sample (96x)
//   and conversion. In Mainloop MSP432 triggers one conversion, goes to sleep,
//   ADC14 ISR to wake up upon conversion completion. ADC14 result is fed into
//   the PWM output on TimerA2_CCR1. Device delays for ~10ms before triggering
//   the next conversion.
//   This version of the lab uses direct register accesses to control the core, the
//   ADC14, TimerA, & other peripherals.
//
//
//                MSP432P401R
//             -----------------
//         /|\|              XIN|-
//          | |                 |
//          --|RST          XOUT|-
//            |                 |
//        >---|P6.0/A15     P5.6|-->PWM to Blue LED
//
//   Dung Dang
//   Texas Instruments Inc.
//   April 2014
//   Built with Code Composer Studio V6.0
//******************************************************************************

#include "msp432.h"
#include "stdint.h"

#define INT_ADC14_BIT (1<<24)
#define MCLK_FREQUENCY 1500000
#define PWM_PERIOD (MCLK_FREQUENCY/5000)


int main(void)
{
    uint32_t adcResult, dutyCycle;
    volatile uint32_t i;

    WDTCTL = WDTPW | WDTHOLD;   // Stop watchdog timer


    /* GPIO Setup */
    P6SEL1 |= BIT0;                         // Configure P6.0 for ADC
    P6SEL0 |= BIT0;
    P5SEL0 |= BIT6;						    // Configure P5.6 as Timer A PWM output
    P5SEL1 &= ~BIT6;
    P5DIR |= BIT6;


    /* Configure TimerA */

    TA2CCR0 = PWM_PERIOD;                   // PWM Period
    TA2CCTL1 = OUTMOD_7;                    // CCR1 reset/set
    TA2CCR1 = PWM_PERIOD/2;                 // CCR1 PWM duty cycle
    TA2CTL = TASSEL__SMCLK | MC__UP | TACLR;  // SMCLK, up mode, clear TAR

    /* Configure ADC14
     * Sampling time, S&H=96, ADC14 on
     * Use sampling timer, 14-bit conversion results
     */
    ADC14CTL0 = ADC14SHT0_5 | ADC14SHP |  ADC14SSEL_1 | ADC14ON ;
    ADC14CTL1 = ADC14RES_3;

    ADC14MCTL0 |= ADC14INCH_15;             // A15 ADC input select; Vref=AVCC
    ADC14IER0 |= ADC14IE0;                  // Enable ADC conv complete interrupt

    NVIC_ISER0 |= INT_ADC14_BIT;         // Enable ADC interrupt in NVIC module

    __enable_interrupt();                   // Enable NVIC global/master interrupt
    SCB_SCR &= ~SCB_SCR_SLEEPONEXIT;        // Wake up on exit from ISR

    while (1)
    {
        ADC14CTL0 |= ADC14ENC |ADC14SC ;    // Start sampling/conversion
        __wfi();                            // alternatively you can also use __sleep();

        adcResult = ADC14MEM0;
        dutyCycle = PWM_PERIOD * adcResult / 16384;

        if (dutyCycle == 0)
            dutyCycle  = 1;
        TA2CCR1 = dutyCycle;                // Change PWM duty cycle based on ADC result

        for (i=0;i<3000;i++);               // Delay ~10ms


        /* Optional exercise: add another TimerA instance as an interval timer
         * and use sleep mode */

    }

}

void adc14IsrHandler(void)
{
    /* Clear ADC14 IFG */
    ADC14CLRIFGR0 = CLRADC14IFG20;

}
