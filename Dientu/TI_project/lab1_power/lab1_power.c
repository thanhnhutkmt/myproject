/*******************************************************************************
 * MSP432 Power Lab - Cycle through available power states & measure power
 *
 * Description: The goal of the lab is to explore and use the power API to
 * exercise various power states available on the MSP432P401.
 * The program will start up in default mode PCM_AM0_LDO.
 * Push button P1.1 can be used to cycle to the next power mode from the below.
 * P1.0 LED blinks to indicate the power state transition.
 *
 * In each power state, use EnergyTrace+ Tool to measure the energy consumption
 * of that power state. Document results across all 13 different states.
 * *
 *             - \b PCM_AM_LDO_VCORE0,       [Active Mode, LDO, VCORE0]
 *             - \b PCM_AM_LDO_VCORE1,      [Active Mode, LDO, VCORE1]
 *             - \b PCM_AM_DCDC_VCORE0,     [Active Mode, DCDC, VCORE0]
 *             - \b PCM_AM_DCDC_VCORE1,     [Active Mode, DCDC, VCORE1]
 *             - \b PCM_AM_LPR_VCORE0,      [Active Mode, Low Frequency, VCORE0]
 *             - \b PCM_AM_LPR_VCORE1,      [Active Mode, Low Frequency, VCORE1]
 *             - \b PCM_LPM0_LDO_VCORE0,    [LMP0, LDO, VCORE0]
 *             - \b PCM_LPM0_LDO_VCORE1,    [LMP0, LDO, VCORE1]
 *             - \b PCM_LPM0_DCDC_VCORE0,   [LMP0, DCDC, VCORE0]
 *             - \b PCM_LPM0_DCDC_VCORE1,   [LMP0, DCDC, VCORE1]
 *             - \b PCM_LPM0_LPR_VCORE0,    [LMP0, Low Frequency, VCORE0]
 *             - \b PCM_LPM0_LPR_VCORE1,    [LMP0, Low Frequency, VCORE1]
 *             - \b PCM_LPM3,               [LPM3]
 *             - \b PCM_LPM35_VCORE0,       [LPM3.5 VCORE 0]
 *
 *
 * Once Deep Sleep mode is entered, the next transition will start again with
 * PCM_AM0_LDO.
 *
 *
 * Power mode transition is accomplished using DriverLib API: PCM_setPowerState()
 * Other related APIs: PCM_setPowerMode(), PCM_gotoSleep(), PCM_gotoLPM3()
 *
 *
 *                MSP432P401
 *             ------------------
 *         /|\|                  |
 *          | |                  |
 *          --|RST         P1.1  |<--Toggle Switch
 *            |                  |
 *            |            P1.0  |----> LED (red)
 *            |                  |
 *            |                  |
 *            |                  |
 *
 *   Dung Dang
 *   Texas Instruments Inc.
 *   April 2014
 *   Built with Code Composer Studio V6.0
 *******************************************************************************/
#include "driverlib.h"


/* Application Data */
volatile uint32_t curPowerState, ledState=0;
volatile bool stateChange;
volatile uint32_t ledBlinkCount, ledBlinkMax=0;


#define NUMBER_OF_POWER_STATES    13


void InitializeDevice(void);

int main(void)
{
    /* Halting the Watchdog */
	WDT_A_holdTimer();
    InitializeDevice();
    curPowerState=0;


    while (1)
    {
        /* If we have a state change request... */
        if (stateChange)
        {
            Interrupt_disableMaster();
            stateChange = false;

            Interrupt_enableMaster();

            /* Step 1: Find the correct Power API to change power state
             * Step 2: Fill in different switch cases to change device to different power states
             * Step 3: Notice special clock handling for the Low-Power Run modes where MCLK
             * is restricted to <=128kHz
             * Hint: Comment out the //#error line after adding your solution code
             */
            switch (curPowerState)
            {
				case 0:
						////#error "Invoke PCM API to change to Active Mode, VCORE = 0 using LDO"
						break;
				case 1:
//						//#error "Invoke PCM API to change to Active Mode, VCORE = 1 using LDO"
						break;
				case 2:
//						//#error "Invoke PCM API to change to Active Mode, VCORE = 0 using DC-DC"
						break;
				case 3:
//						//#error "Invoke PCM API to change to Active Mode, VCORE = 1 using DC-DC"
						break;
				case 4:
						/* Switch all clocks to low-frequency operation prior to LF operations */
						CS_initClockSignal(CS_MCLK, CS_REFOCLK_SELECT, CS_CLOCK_DIVIDER_1);
						CS_initClockSignal(CS_SMCLK, CS_REFOCLK_SELECT, CS_CLOCK_DIVIDER_1);
						CS_initClockSignal(CS_ACLK, CS_REFOCLK_SELECT, CS_CLOCK_DIVIDER_1);
//						//#error "Invoke PCM API to change to Active Mode, VCORE = 0 using Low-Frequency Mode"

						break;
				case 5:
//						//#error "Invoke PCM API to change to Active Mode, VCORE = 1 using Low-Frequency Mode"
						break;
				case 6:
						/* Switch back to using LDO regulator first before increasing the clocks */
//						//#error "Invoke PCM API to change to Active Mode, VCORE = 0 using LDO"
						/* Switch clocks back to high-frequency operation */
						CS_initClockSignal(CS_MCLK, CS_DCOCLK_SELECT, CS_CLOCK_DIVIDER_1);
						CS_initClockSignal(CS_SMCLK, CS_DCOCLK_SELECT, CS_CLOCK_DIVIDER_1);
						////#error "Invoke PCM API to change to LPM0 Mode, VCORE = 0 using LDO"
						break;
				case 7:
						////#error "Invoke PCM API to change to LPM0 Mode, VCORE = 1 using LDO"
						break;
				case 8:
						////#error "Invoke PCM API to change to LPM0 Mode, VCORE = 0 using DC-DC"
						break;
				case 9:
						//#error "Invoke PCM API to change to LPM0 Mode, VCORE = 1 using DC-DC"
						break;
				case 10:
						/* Switch all clocks to low-frequency operation prior to LF operations */
						CS_initClockSignal(CS_MCLK, CS_REFOCLK_SELECT, CS_CLOCK_DIVIDER_1);
						CS_initClockSignal(CS_SMCLK, CS_REFOCLK_SELECT, CS_CLOCK_DIVIDER_1);
						CS_initClockSignal(CS_ACLK, CS_REFOCLK_SELECT, CS_CLOCK_DIVIDER_1);
						//#error "Invoke PCM API to change to LPM0 Mode, VCORE = 0 using Low-Frequency Mode"

						break;
				case 11:
						//#error "Invoke PCM API to change to LPM0 Mode, VCORE = 1 using Low-Frequency Mode"
						break;
				case 12:
						/* Switch back to using LDO regulator first before increasing the clocks */
						//#error "Invoke PCM API to change to Active Mode, VCORE = 0 using LDO"
						/* Switch clocks back to high-frequency operation */
						CS_initClockSignal(CS_MCLK, CS_DCOCLK_SELECT, CS_CLOCK_DIVIDER_1);
						CS_initClockSignal(CS_SMCLK, CS_DCOCLK_SELECT, CS_CLOCK_DIVIDER_1);
						//#error "Invoke PCM API to change to LPM3 Mode"
						break;

				default:
						break;
            }
        }
    }
}

/*
 * Port 1 interrupt handler. This handler is called whenever the switch attached
 * to P1.1 is pressed. A status flag is set to signal for the main application
 * to change power states
 */
void Port1IsrHandler(void)
{
    uint32_t status = GPIO_getEnabledInterruptStatus(GPIO_PORT_P1);
    GPIO_clearInterruptFlag(GPIO_PORT_P1, status);


    if (status & GPIO_PIN1)
    {
        Interrupt_disableInterrupt(INT_PORT1);
        if (curPowerState == 12)  /*Power State is PCM_DSL[Deep Sleep Mode]*/
        {
            curPowerState = 0;

        } else
        {

            curPowerState++;

        }

        ledBlinkMax = (curPowerState) * 2;

        ledBlinkCount = 0;
        if (++ledState==8)
            ledState = 1;
        P2OUT &= ~(0x07);


        Interrupt_enableInterrupt(INT_TA0_0);
        Timer_A_startCounter(TIMER_A0_MODULE,TIMER_A_UP_MODE);

    }
}
/* Flashes LED */
 void Timer_AIsrHandler(void)
{
    Timer_A_clearCaptureCompareInterrupt(TIMER_A0_MODULE,
            TIMER_A_CAPTURECOMPARE_REGISTER_0);
    if (ledBlinkMax>0)
    	P2OUT ^= ledState;

    ledBlinkCount++;

     if ( (ledBlinkCount == ledBlinkMax) || (ledBlinkMax==0))
     {
         stateChange = true;
         Timer_A_stopTimer(TIMER_A0_MODULE);
         Interrupt_disableInterrupt(INT_TA0_0);
         /* Re-enabling port pin interrupt */
         GPIO_clearInterruptFlag(GPIO_PORT_P1, GPIO_PIN1);
         Interrupt_enableInterrupt(INT_PORT1);
     }
}

void TerminateGPIO(void)
{

    P1DIR = 0x00;
    P2DIR = 0x00;
    P3DIR = 0x00;
    P4DIR = 0x00;
    P5DIR = 0x00;
    P6DIR = 0x00;
    P7DIR = 0x00;
    P8DIR = 0x00;
    P9DIR = 0x00;
    P10DIR = 0x00;
    P1REN = 0xff;
    P2REN = 0xff;
    P3REN = 0xff;
    P4REN = 0xff;
    P5REN = 0xff;
    P6REN = 0xff;
    P7REN = 0xff;
    P8REN = 0xff;
    P9REN = 0xff;
    P10REN = 0xff;
    P1OUT = 0x00;
    P2OUT = 0x00;
    P3OUT = 0x00;
    P4OUT = 0x00;
    P5OUT = 0x00;
    P6OUT = 0x00;
    P7OUT = 0x00;
    P8OUT = 0x00;
    P9OUT = 0x00;
    P10OUT = 0x00;

    PSS_setHighSidePerformanceMode(PSS_NORMAL_PERFORMANCE_MODE);
    //PSS_setLowSidePerformanceMode(PSS_NORMAL_PERFORMANCE_MODE);
    PCM_enableRudeMode();
}


void InitializeDevice(void)
{
	/* TimerA UpMode Configuration Parameter */
	Timer_A_UpModeConfig upConfig =
	{
	        TIMER_A_CLOCKSOURCE_ACLK,              // SMCLK Clock SOurce
	        TIMER_A_CLOCKSOURCE_DIVIDER_1,          // SCLK/1 = 3MHz
	        16000,                           // 50000 tick period
	        TIMER_A_TAIE_INTERRUPT_DISABLE,         // Disable Timer interrupt
	        TIMER_A_CCIE_CCR0_INTERRUPT_ENABLE ,    // Enable CCR0 interrupt
	        TIMER_A_SKIP_CLEAR                      // Clear value
	};

	Interrupt_disableMaster();
	TerminateGPIO();
	/* Initializing Variables */
    curPowerState = 0;
	stateChange = false;
	ledBlinkCount = 0;

	/* Setting the Reference Oscillator to 128KHz. For Low Power Run modes, the
	 * MCLK frequency is required to be scaled back to 128KHz.
	 */
	CS_setReferenceOscillatorFrequency(CS_REFO_128KHZ);

	/* Setting up TimerA to be sourced from ACLK and for ACLK to be sourced from
	 * the 128Khz REFO. Since the frequency of MCLK will be changed when we go
	 * into LPR mode, we want to make our LED blink look consistent.
	 */
	CS_initClockSignal(CS_ACLK, CS_REFOCLK_SELECT, CS_CLOCK_DIVIDER_1);
	Timer_A_configureUpMode(TIMER_A0_MODULE, &upConfig);
	Timer_A_enableCaptureCompareInterrupt(TIMER_A0_MODULE,
			TIMER_A_CAPTURECOMPARE_REGISTER_0);


	/* Configuring P2 as output and P1.1 (switch) as input */
	GPIO_setAsOutputPin(GPIO_PORT_P2, GPIO_PIN0 | GPIO_PIN1 | GPIO_PIN2 );


	/* Confinguring P1.1 as an input and enabling interrupts */
	GPIO_setAsInputPinWithPullUpResistor(GPIO_PORT_P1, GPIO_PIN1);
	GPIO_clearInterruptFlag(GPIO_PORT_P1, GPIO_PIN1);
	GPIO_enableInterrupt(GPIO_PORT_P1, GPIO_PIN1);
	GPIO_interruptEdgeSelect(GPIO_PORT_P1, GPIO_PIN1, GPIO_HIGH_TO_LOW_TRANSITION);
	Interrupt_enableInterrupt(INT_PORT1);

    Interrupt_disableSleepOnIsrExit();
    SysCtl_enableSRAMBankRetention(SYSCTL_SRAM_BANK7);
    Interrupt_enableMaster();
}
