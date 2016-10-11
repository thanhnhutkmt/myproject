
//******************************************************************************
//  MSP432P401 Demo - ADC14, Sample A1, AVcc Ref, Set P1.0 if A1 > 0.5*AVcc
//
//   Description: A single sample is made on A1 with reference to AVcc.
//   Software sets ADC14SC to start sample and conversion - ADC14SC
//   automatically cleared at EOC. ADC14 internal oscillator times sample (16x)
//   and conversion. In Mainloop MSP432 waits in LPM0 to save power until ADC14
//   conversion complete, ADC14_ISR will force exit from LPM0 in Mainloop on
//   reti. If A0 > 0.5*AVcc, P1.0 set, else reset. The full, correct handling of
//   and ADC14 interrupt is shown as well.
//
//
//                MSP432p401rpz
//             -----------------
//         /|\|              XIN|-
//          | |                 |
//          --|RST          XOUT|-
//            |                 |
//        >---|P5.4/A1      P1.0|-->LED
//
//   Dung Dang
//   Texas Instruments Inc.
//   November 2013
//   Built with Code Composer Studio V6.0
//******************************************************************************
#include "msp.h"
#include "stdint.h"

int main(void) {
    volatile unsigned int i;
    WDTCTL = WDTPW | WDTHOLD;                 // Stop WDT

    // GPIO Setup
    P1OUT &= ~BIT0;                           // Clear LED to start
    P1DIR |= BIT0;                            // Set P1.0/LED to output
    P5SEL1 |= BIT4;                           // Configure P5.4 for ADC
    P5SEL0 |= BIT4;
//    P6SEL1 |= BIT0;							  // Configure P6.0 for ADC
//    P6SEL0 |= BIT0;

    //__enable_interrupt();
    NVIC_ISER0 = 1 << ((INT_ADC14 - 16) & 31);         // Enable ADC interrupt in NVIC module

    // Configure ADC14

    ADC14CTL0 = ADC14SHT0_2 | ADC14SHP | ADC14ON;          // Sampling time, S&H=16, ADC14 on
    ADC14CTL1 = ADC14RES_2;                   // Use sampling timer, 12-bit conversion results

    ADC14MCTL0 |= ADC14INCH_1;                // A1 ADC input select; Vref=AVCC
    ADC14IER0 |= ADC14IE0;                    // Enable ADC conv complete interrupt

    SCB_SCR &= ~SCB_SCR_SLEEPONEXIT;           // Wake up on exit from ISR

    while (1)
    {
      for (i = 20000; i > 0; i--);            // Delay
      ADC14CTL0 |= ADC14ENC | ADC14SC;        // Start sampling/conversion
      ADC14CLRIFGR0 |= CLRADC14IFG0;        //Attempt at clearing flag
      if (ADC14MEM0 >= 0x7FF)               // ADC12MEM0 = A1 > 0.5AVcc?
      	P1OUT &= ~BIT0;                     // P1.0 = 0
      else
      	P1OUT |= BIT0;                      // P1.0 = 1

//      __sleep();

//      __bis_SR_register(LPM0_bits | GIE);     // LPM0, ADC14_ISR will force exit
//      __no_operation();                       // For debugger
    }
}

// ADC14 interrupt service routine

void ADC14IsrHandler(void) {
//    ADC14CLRIFGR0 |= CLRADC14IFG0;        //Attempt at clearing flag
    if (ADC14MEM0 >= 0x7FF)               // ADC12MEM0 = A1 > 0.5AVcc?
    	P1OUT &= ~BIT0;                     // P1.0 = 0
    else
    	P1OUT |= BIT0;                      // P1.0 = 1
}
