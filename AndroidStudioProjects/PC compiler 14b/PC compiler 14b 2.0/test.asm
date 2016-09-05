;**********************************************************************
;                                                                     *
;    Filename:       test7.asm                                        *
;    Date:           Mon 03 Jun 2013 05:14:21 PM                      *
;    File Version:                                                    *
;                                                                     *
;    Author:                                                          *
;    Company:                                                         *
;                                                                     *
;                                                                     *
;**********************************************************************
;                                                                     *
;    Files required:                                                  *
;                                                                     *
;                                                                     *
;                                                                     *
;**********************************************************************
;                                                                     *
;    Notes:                                                           *
;                                                                     *
;                                                                     *
;                                                                     *
;                                                                     *
;**********************************************************************
list        p=16F887   ; list directive to define processor
#include <p16F887.inc> ; processor specific variable definitions
; '__CONFIG' directive is used to embed configuration data within .asm file. 
; The labels following the directive are located in the respective .inc file. 
; See respective data sheet for additional information on configuration word. 
__CONFIG    _CONFIG1, _WDTE_OFF & _PWRTE_ON & _MCLRE_OFF & _CP_ON & _CPD_ON & _BOREN_OFF & _IESO_OFF & _FCMEN_OFF & _LVP_OFF & _DEBUG_ON
__CONFIG    _CONFIG2, _BOR21V & _WRT_HALF
