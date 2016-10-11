//*****************************************************************************
//
// MSP432 main.c template - P1.0 port toggle
//
//****************************************************************************

#include "msp.h"

void main(void)
{
    volatile uint32_t i;

    WDTCTL = WDTPW | WDTHOLD;           // Stop watchdog timer

    // The following code toggles P1.0 port
    P1DIR |= BIT0;                      // Configure P1.0 as output
    P2DIR |= BIT2;                      // Configure P2.2 as output
    P1OUT |= BIT0;                 		// Toggle P1.0
    P2OUT |= BIT2;                  	// Toggle P2.2

    while(1)
    {
        P1OUT ^= BIT0;                  // Toggle P1.0
        P2OUT ^= BIT2;                  // Toggle P2.2
        for(i=100000; i>0; i--);        // Delay
    }
}
