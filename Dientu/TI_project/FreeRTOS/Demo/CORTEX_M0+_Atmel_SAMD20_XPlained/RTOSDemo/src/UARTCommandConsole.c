/*
    FreeRTOS V9.0.0rc2 - Copyright (C) 2016 Real Time Engineers Ltd.
    All rights reserved

    VISIT http://www.FreeRTOS.org TO ENSURE YOU ARE USING THE LATEST VERSION.

    This file is part of the FreeRTOS distribution.

    FreeRTOS is free software; you can redistribute it and/or modify it under
    the terms of the GNU General Public License (version 2) as published by the
    Free Software Foundation >>>> AND MODIFIED BY <<<< the FreeRTOS exception.

    ***************************************************************************
    >>!   NOTE: The modification to the GPL is included to allow you to     !<<
    >>!   distribute a combined work that includes FreeRTOS without being   !<<
    >>!   obliged to provide the source code for proprietary components     !<<
    >>!   outside of the FreeRTOS kernel.                                   !<<
    ***************************************************************************

    FreeRTOS is distributed in the hope that it will be useful, but WITHOUT ANY
    WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
    FOR A PARTICULAR PURPOSE.  Full license text is available on the following
    link: http://www.freertos.org/a00114.html

    ***************************************************************************
     *                                                                       *
     *    FreeRTOS provides completely free yet professionally developed,    *
     *    robust, strictly quality controlled, supported, and cross          *
     *    platform software that is more than just the market leader, it     *
     *    is the industry's de facto standard.                               *
     *                                                                       *
     *    Help yourself get started quickly while simultaneously helping     *
     *    to support the FreeRTOS project by purchasing a FreeRTOS           *
     *    tutorial book, reference manual, or both:                          *
     *    http://www.FreeRTOS.org/Documentation                              *
     *                                                                       *
    ***************************************************************************

    http://www.FreeRTOS.org/FAQHelp.html - Having a problem?  Start by reading
    the FAQ page "My application does not run, what could be wrong?".  Have you
    defined configASSERT()?

    http://www.FreeRTOS.org/support - In return for receiving this top quality
    embedded software for free we request you assist our global community by
    participating in the support forum.

    http://www.FreeRTOS.org/training - Investing in training allows your team to
    be as productive as possible as early as possible.  Now you can receive
    FreeRTOS training directly from Richard Barry, CEO of Real Time Engineers
    Ltd, and the world's leading authority on the world's leading RTOS.

    http://www.FreeRTOS.org/plus - A selection of FreeRTOS ecosystem products,
    including FreeRTOS+Trace - an indispensable productivity tool, a DOS
    compatible FAT file system, and our tiny thread aware UDP/IP stack.

    http://www.FreeRTOS.org/labs - Where new FreeRTOS products go to incubate.
    Come and try FreeRTOS+TCP, our new open source TCP/IP stack for FreeRTOS.

    http://www.OpenRTOS.com - Real Time Engineers ltd. license FreeRTOS to High
    Integrity Systems ltd. to sell under the OpenRTOS brand.  Low cost OpenRTOS
    licenses offer ticketed support, indemnification and commercial middleware.

    http://www.SafeRTOS.com - High Integrity Systems also provide a safety
    engineered and independently SIL3 certified version for use in safety and
    mission critical applications that require provable dependability.

    1 tab == 4 spaces!
*/

/* Standard includes. */
#include "string.h"
#include "stdio.h"

/* FreeRTOS includes. */
#include "FreeRTOS.h"
#include "task.h"
#include "queue.h"
#include "semphr.h"

/* Library includes. */
#include "asf.h"

/* Example includes. */
#include "FreeRTOS_CLI.h"
#include "UARTCommandConsole.h"

/* Dimensions the buffer into which input characters are placed. */
#define cmdMAX_INPUT_SIZE		50

/* The maximum time in ticks to wait for the UART access mutex. */
#define cmdMAX_MUTEX_WAIT		( 200 / portTICK_PERIOD_MS )

/* Characters are only ever received slowly on the CLI so it is ok to pass
received characters from the UART interrupt to the task on a queue.  This sets
the length of the queue used for that purpose. */
#define cmdRXED_CHARS_QUEUE_LENGTH			( 10 )

/* DEL acts as a backspace. */
#define cmdASCII_DEL		( 0x7F )

/*-----------------------------------------------------------*/

/*
 * The task that implements the command console processing.
 */
static void prvUARTCommandConsoleTask( void *pvParameters );

/*
 * Ensure a previous interrupt driven Tx has completed before sending the next
 * data block to the UART.
 */
static void prvSendBuffer( struct usart_module *pxCDCUsart, const char * pcBuffer, size_t xBufferLength );

/*
 * Register the 'standard' sample CLI commands with FreeRTOS+CLI.
 */
extern void vRegisterSampleCLICommands( void );

/*
 * Configure the UART used for IO.and register prvUARTRxNotificationHandler()
 * to handle UART Rx events.
 */
static void prvConfigureUART( struct usart_module *pxCDCUsart );

/*
 * Callback functions registered with the Atmel UART driver.  Both functions
 * just 'give' a semaphore to unblock a task that may be waiting for a
 * character to be received, or a transmission to complete.
 */
static void prvUARTTxNotificationHandler( const struct usart_module *const pxUSART );
static void prvUARTRxNotificationHandler( const struct usart_module *const pxUSART );

/*-----------------------------------------------------------*/

/* Const messages output by the command console. */
static char * const pcWelcomeMessage = "\r\n\r\nFreeRTOS command server.\r\nType Help to view a list of registered commands.\r\n\r\n>";
static const char * const pcEndOfOutputMessage = "\r\n[Press ENTER to execute the previous command again]\r\n>";
static const char * const pcNewLine = "\r\n";

/* This semaphore is used to allow the task to wait for a Tx to complete
without wasting any CPU time. */
static SemaphoreHandle_t xTxCompleteSemaphore = NULL;

/* This semaphore is sued to allow the task to wait for an Rx to complete
without wasting any CPU time. */
static SemaphoreHandle_t xRxCompleteSemaphore = NULL;

/*-----------------------------------------------------------*/

void vUARTCommandConsoleStart( uint16_t usStackSize, unsigned portBASE_TYPE uxPriority )
{
	vRegisterSampleCLICommands();

	/* Create that task that handles the console itself. */
	xTaskCreate( 	prvUARTCommandConsoleTask,			/* The task that implements the command console. */
					"CLI",								/* Text name assigned to the task.  This is just to assist debugging.  The kernel does not use this name itself. */
					usStackSize,						/* The size of the stack allocated to the task. */
					NULL,								/* The parameter is not used, so NULL is passed. */
					uxPriority,							/* The priority allocated to the task. */
					NULL );								/* A handle is not required, so just pass NULL. */
}
/*-----------------------------------------------------------*/

static void prvUARTCommandConsoleTask( void *pvParameters )
{
char cRxedChar, *pcOutputString;
uint8_t ucInputIndex = 0;
static char cInputString[ cmdMAX_INPUT_SIZE ], cLastInputString[ cmdMAX_INPUT_SIZE ];
portBASE_TYPE xReturned;
static struct usart_module xCDCUsart; /* Static so it doesn't take up too much stack. */

	( void ) pvParameters;

	/* A UART is used for printf() output and CLI input and output.  Note there
	is no mutual exclusion on the UART, but the demo as it stands does not
	require mutual exclusion. */
	prvConfigureUART( &xCDCUsart );

	/* Obtain the address of the output buffer.  Note there is no mutual
	exclusion on this buffer as it is assumed only one command console
	interface will be used at any one time. */
	pcOutputString = FreeRTOS_CLIGetOutputBuffer();

	/* Send the welcome message. */
	prvSendBuffer( &xCDCUsart, pcWelcomeMessage, strlen( pcWelcomeMessage ) );

	for( ;; )
	{
		/* Wait for the next character to arrive.  A semaphore is used to
		ensure no CPU time is used until data has arrived. */
		usart_read_buffer_job( &xCDCUsart, ( uint8_t * ) &cRxedChar, sizeof( cRxedChar ) );
		if( xSemaphoreTake( xRxCompleteSemaphore, portMAX_DELAY ) == pdPASS )
		{
			/* Echo the character back. */
			prvSendBuffer( &xCDCUsart, &cRxedChar, sizeof( cRxedChar ) );

			/* Was it the end of the line? */
			if( cRxedChar == '\n' || cRxedChar == '\r' )
			{
				/* Just to space the output from the input. */
				prvSendBuffer( &xCDCUsart, pcNewLine, strlen( pcNewLine ) );

				/* See if the command is empty, indicating that the last command is
				to be executed again. */
				if( ucInputIndex == 0 )
				{
					/* Copy the last command back into the input string. */
					strcpy( cInputString, cLastInputString );
				}

				/* Pass the received command to the command interpreter.  The
				command interpreter is called repeatedly until it returns pdFALSE
				(indicating there is no more output) as it might generate more than
				one string. */
				do
				{
					/* Get the next output string from the command interpreter. */
					xReturned = FreeRTOS_CLIProcessCommand( cInputString, pcOutputString, configCOMMAND_INT_MAX_OUTPUT_SIZE );

					/* Write the generated string to the UART. */
					prvSendBuffer( &xCDCUsart, pcOutputString, strlen( pcOutputString ) );

				} while( xReturned != pdFALSE );

				/* All the strings generated by the input command have been sent.
				Clear the input	string ready to receive the next command.  Remember
				the command that was just processed first in case it is to be
				processed again. */
				strcpy( cLastInputString, cInputString );
				ucInputIndex = 0;
				memset( cInputString, 0x00, cmdMAX_INPUT_SIZE );

				prvSendBuffer( &xCDCUsart, pcEndOfOutputMessage, strlen( pcEndOfOutputMessage ) );
			}
			else
			{
				if( cRxedChar == '\r' )
				{
					/* Ignore the character. */
				}
				else if( ( cRxedChar == '\b' ) || ( cRxedChar == cmdASCII_DEL ) )
				{
					/* Backspace was pressed.  Erase the last character in the
					string - if any. */
					if( ucInputIndex > 0 )
					{
						ucInputIndex--;
						cInputString[ ucInputIndex ] = '\0';
					}
				}
				else
				{
					/* A character was entered.  Add it to the string
					entered so far.  When a \n is entered the complete
					string will be passed to the command interpreter. */
					if( ( cRxedChar >= ' ' ) && ( cRxedChar <= '~' ) )
					{
						if( ucInputIndex < cmdMAX_INPUT_SIZE )
						{
							cInputString[ ucInputIndex ] = cRxedChar;
							ucInputIndex++;
						}
					}
				}
			}
		}
	}
}
/*-----------------------------------------------------------*/

static void prvSendBuffer( struct usart_module *pxCDCUsart, const char * pcBuffer, size_t xBufferLength )
{
const TickType_t xBlockMax100ms = 100UL / portTICK_PERIOD_MS;

	if( xBufferLength > 0 )
	{
		usart_write_buffer_job( pxCDCUsart, ( uint8_t * ) pcBuffer, xBufferLength );

		/* Wait for the Tx to complete so the buffer can be reused without
		corrupting the data that is being sent. */
		xSemaphoreTake( xTxCompleteSemaphore, xBlockMax100ms );
	}
}
/*-----------------------------------------------------------*/

static void prvConfigureUART( struct usart_module *pxCDCUsart )
{
struct usart_config xUARTConfig;

	/* This semaphore is used to allow the task to wait for the Tx to complete
	without wasting any CPU time. */
	vSemaphoreCreateBinary( xTxCompleteSemaphore );
	configASSERT( xTxCompleteSemaphore );

	/* This semaphore is used to allow the task to block for an Rx to complete
	without wasting any CPU time. */
	vSemaphoreCreateBinary( xRxCompleteSemaphore );
	configASSERT( xRxCompleteSemaphore );

	/* Take the semaphores so they start in the wanted state.  A block time is
	not necessary, and is therefore set to 0, as it is known that the semaphores
	exists - they have just been created. */
	xSemaphoreTake( xTxCompleteSemaphore, 0 );
	xSemaphoreTake( xRxCompleteSemaphore, 0 );

	/* Configure the hardware. */
	usart_get_config_defaults( &xUARTConfig );
	xUARTConfig.baudrate    = 115200;
	xUARTConfig.mux_setting = EDBG_CDC_SERCOM_MUX_SETTING;
	xUARTConfig.pinmux_pad0 = EDBG_CDC_SERCOM_PINMUX_PAD0;
	xUARTConfig.pinmux_pad1 = EDBG_CDC_SERCOM_PINMUX_PAD1;
	xUARTConfig.pinmux_pad2 = EDBG_CDC_SERCOM_PINMUX_PAD2;
	xUARTConfig.pinmux_pad3 = EDBG_CDC_SERCOM_PINMUX_PAD3;
	while( usart_init( pxCDCUsart, EDBG_CDC_MODULE, &xUARTConfig ) != STATUS_OK )
	{
		/* Nothing to do here.  Should include a timeout really but this is
		init code only. */
	}
	usart_enable( pxCDCUsart );

	/* Register the driver callbacks. */
	usart_register_callback( pxCDCUsart, prvUARTTxNotificationHandler, USART_CALLBACK_BUFFER_TRANSMITTED );
	usart_register_callback( pxCDCUsart, prvUARTRxNotificationHandler, USART_CALLBACK_BUFFER_RECEIVED );
	usart_enable_callback( pxCDCUsart, USART_CALLBACK_BUFFER_TRANSMITTED );
	usart_enable_callback( pxCDCUsart, USART_CALLBACK_BUFFER_RECEIVED );
}
/*-----------------------------------------------------------*/

static void prvUARTRxNotificationHandler( const struct usart_module *const pxUSART )
{
portBASE_TYPE xHigherPriorityTaskWoken = pdFALSE;

	/* Remove compiler warnings. */
	( void ) pxUSART;

	/* Give the semaphore  to unblock any tasks that might be waiting for an Rx
	to complete.  If a task is unblocked, and the unblocked task has a priority
	above the currently running task, then xHigherPriorityTaskWoken will be set
	to pdTRUE inside the xSemaphoreGiveFromISR() function. */
	xSemaphoreGiveFromISR( xRxCompleteSemaphore, &xHigherPriorityTaskWoken );

	/* portEND_SWITCHING_ISR() or portYIELD_FROM_ISR() can be used here. */
	portYIELD_FROM_ISR( xHigherPriorityTaskWoken );
}
/*-----------------------------------------------------------*/

static void prvUARTTxNotificationHandler( const struct usart_module *const pxUSART )
{
portBASE_TYPE xHigherPriorityTaskWoken = pdFALSE;

	/* Remove compiler warnings. */
	( void ) pxUSART;

	/* Give the semaphore  to unblock any tasks that might be waiting for a Tx
	to complete.  If a task is unblocked, and the unblocked task has a priority
	above the currently running task, then xHigherPriorityTaskWoken will be set
	to pdTRUE inside the xSemaphoreGiveFromISR() function. */
	xSemaphoreGiveFromISR( xTxCompleteSemaphore, &xHigherPriorityTaskWoken );

	/* portEND_SWITCHING_ISR() or portYIELD_FROM_ISR() can be used here. */
	portYIELD_FROM_ISR( xHigherPriorityTaskWoken );
}


