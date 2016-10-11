#include "msp432.h"
#include "driverlib.h"

#include "string.h"
//#pragma NOINIT(buffer)

#define BUFFER_MEMORY_START 0x000200000

void EraseAndProgramFlash(void);
void TestSRAMBankEnable(void);
int main(void)
{
    MAP_WDT_A_holdTimer();

    MAP_Interrupt_disableMaster();

    /* Setting our MCLK to 48MHz for faster programming */
    MAP_PCM_setCoreVoltageLevel(PCM_VCORE1);
    MAP_FlashCtl_setWaitState(FLASH_BANK0, 2);
    MAP_FlashCtl_setWaitState(FLASH_BANK1, 2);
    MAP_CS_setDCOCenteredFrequency(CS_DCO_FREQUENCY_48);

    /* Memory test #1: Flash */
    EraseAndProgramFlash();
    /* Memory test #2: SRAM */
    TestSRAMBankEnable();



    //![FlashCtl Program]
    while(1);
}

void EraseAndProgramFlash(void)
{
    uint8_t buffer[4096];
    /* Initializing our buffer to a pattern of 0xA5 */
    memset(buffer, 0xA5, 4096);
    //![FlashCtl Program]
    /* Unprotecting Info Bank 0, Sector 0  */

    /* Set breakpoint here to check memory @ 0x000200000 before erase */
    __no_operation();

    MAP_FlashCtl_unprotectSector(FLASH_INFO_MEMORY_SPACE_BANK0,FLASH_SECTOR0);

    /* Erase the flash sector starting BUFFER_MEMORY_START.  */
    while(!MAP_FlashCtl_eraseSector(BUFFER_MEMORY_START));

    /* Set breakpoint here to check memory @ 0x000200000 after erase */
            __no_operation();

    /* Program the sector with the new data. */

    while (!MAP_FlashCtl_programMemory(buffer,
            (void*) BUFFER_MEMORY_START, 4096 ));

    /* Setting the sector back to protected  */
    MAP_FlashCtl_protectSector(FLASH_INFO_MEMORY_SPACE_BANK0,FLASH_SECTOR0);

    /* Set breakpoint here to check memory @ 0x000200000 after programming*/
    __no_operation();

}


void TestSRAMBankEnable(void)
{
    uint32_t i;


    /* Test #1 */
    /* Write & read back from an SRAM memory location in bank 7*/
    while (!(SYSCTL_SRAM_BANKEN & SYSCTL_SRAM_BANKEN_SRAM_RDY));
    SysCtl_enableSRAMBank(SYSCTL_SRAM_BANK7);
    while (!(SYSCTL_SRAM_BANKEN & SYSCTL_SRAM_BANKEN_SRAM_RDY));

    /* Checking for valid read & write access to memory address
     * located in SRAM bank 7
     */

    * ( (uint32_t*) 0x2000EFFC) = 0xDEADBEEF;
    i = * ( (uint32_t*) 0x2000EFFC);
    if (i== 0xDEADBEEF)
    {
        /* Set breakpoint here, or watch for the pulsing LED*/
        P1DIR |= BIT0;
        P1OUT |= BIT0;
        for (i=0;i<100000;i++);
        P1OUT &= ~BIT0;
    }
    else
        while(1);

    /* Test #2 */
    /* After disabling SRAM banks, Write/access should have invalid results */

    /* Disable SRAM Banks # 4 to 7 */
    while (!(SYSCTL_SRAM_BANKEN & SYSCTL_SRAM_BANKEN_SRAM_RDY));
    SysCtl_disableSRAMBank(SYSCTL_SRAM_BANK4);
    while (!(SYSCTL_SRAM_BANKEN & SYSCTL_SRAM_BANKEN_SRAM_RDY));

    /* Confirming invalid read & write access to memory address
    * located in SRAM bank 7
    */

    * ( (uint32_t*) 0x2000EFFC) = 0xDEADBEEF;
    i = * ( (uint32_t*) 0x2000EFFC);
    if (i!= 0)
    {
        /* Set breakpoint here, or watch for the pulsing LED*/
        P1DIR |= BIT0;
        P1OUT |= BIT0;
        for (i=0;i<100000;i++);
        P1OUT &= ~BIT0;
    }
    else
        while(1);

}
