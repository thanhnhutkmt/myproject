################################################################################
# Automatically-generated file. Do not edit!
################################################################################

# Each subdirectory must supply rules for building sources it contributes
driverlib/cpu.obj: ../driverlib/cpu.c $(GEN_OPTS) $(GEN_HDRS)
	@echo 'Building file: $<'
	@echo 'Invoking: MSP432 Compiler'
	"C:/ti/ccsv6/tools/compiler/ti-cgt-arm_5.2.7/bin/armcl" -mv7M4 --code_state=16 --float_support=FPv4SPD16 --abi=eabi -me -O2 --include_path="C:/ti/ccsv6/ccs_base/arm/include" --include_path="C:/ti/ccsv6/tools/compiler/ti-cgt-arm_5.2.7/include" --include_path="C:/ti/ccsv6/ccs_base/arm/include/" --include_path="C:/ti/ccsv6/ccs_base/arm/include/CMSIS" --include_path="C:/ti/ccsv6/tools/compiler/ti-cgt-arm_5.2.7/include/" --include_path="C:/Users/Nhut/workspace_v6_1/FreeRTOS/Demo/CORTEX_M4F_MSP432_LaunchPad_IAR_CCS_Keil" --include_path="C:/DevTools/ti/msp430/MSP430ware_1_97_00_47/examples/boards/MSP-EXP432P401R/MSP-EXP432P401R_Software_Examples/Source/OutOfBox_MSP432P401R/driverlib/MSP432P4xx/" --advice:power=all --gcc --define=__MSP432P401R__ --define=TARGET_IS_MSP432P4XX --define=ccs --diag_wrap=off --display_error_number --diag_warning=225 --preproc_with_compile --preproc_dependency="driverlib/cpu.pp" --obj_directory="driverlib" $(GEN_OPTS__FLAG) "$<"
	@echo 'Finished building: $<'
	@echo ' '

driverlib/cs.obj: ../driverlib/cs.c $(GEN_OPTS) $(GEN_HDRS)
	@echo 'Building file: $<'
	@echo 'Invoking: MSP432 Compiler'
	"C:/ti/ccsv6/tools/compiler/ti-cgt-arm_5.2.7/bin/armcl" -mv7M4 --code_state=16 --float_support=FPv4SPD16 --abi=eabi -me -O2 --include_path="C:/ti/ccsv6/ccs_base/arm/include" --include_path="C:/ti/ccsv6/tools/compiler/ti-cgt-arm_5.2.7/include" --include_path="C:/ti/ccsv6/ccs_base/arm/include/" --include_path="C:/ti/ccsv6/ccs_base/arm/include/CMSIS" --include_path="C:/ti/ccsv6/tools/compiler/ti-cgt-arm_5.2.7/include/" --include_path="C:/Users/Nhut/workspace_v6_1/FreeRTOS/Demo/CORTEX_M4F_MSP432_LaunchPad_IAR_CCS_Keil" --include_path="C:/DevTools/ti/msp430/MSP430ware_1_97_00_47/examples/boards/MSP-EXP432P401R/MSP-EXP432P401R_Software_Examples/Source/OutOfBox_MSP432P401R/driverlib/MSP432P4xx/" --advice:power=all --gcc --define=__MSP432P401R__ --define=TARGET_IS_MSP432P4XX --define=ccs --diag_wrap=off --display_error_number --diag_warning=225 --preproc_with_compile --preproc_dependency="driverlib/cs.pp" --obj_directory="driverlib" $(GEN_OPTS__FLAG) "$<"
	@echo 'Finished building: $<'
	@echo ' '

driverlib/fpu.obj: ../driverlib/fpu.c $(GEN_OPTS) $(GEN_HDRS)
	@echo 'Building file: $<'
	@echo 'Invoking: MSP432 Compiler'
	"C:/ti/ccsv6/tools/compiler/ti-cgt-arm_5.2.7/bin/armcl" -mv7M4 --code_state=16 --float_support=FPv4SPD16 --abi=eabi -me -O2 --include_path="C:/ti/ccsv6/ccs_base/arm/include" --include_path="C:/ti/ccsv6/tools/compiler/ti-cgt-arm_5.2.7/include" --include_path="C:/ti/ccsv6/ccs_base/arm/include/" --include_path="C:/ti/ccsv6/ccs_base/arm/include/CMSIS" --include_path="C:/ti/ccsv6/tools/compiler/ti-cgt-arm_5.2.7/include/" --include_path="C:/Users/Nhut/workspace_v6_1/FreeRTOS/Demo/CORTEX_M4F_MSP432_LaunchPad_IAR_CCS_Keil" --include_path="C:/DevTools/ti/msp430/MSP430ware_1_97_00_47/examples/boards/MSP-EXP432P401R/MSP-EXP432P401R_Software_Examples/Source/OutOfBox_MSP432P401R/driverlib/MSP432P4xx/" --advice:power=all --gcc --define=__MSP432P401R__ --define=TARGET_IS_MSP432P4XX --define=ccs --diag_wrap=off --display_error_number --diag_warning=225 --preproc_with_compile --preproc_dependency="driverlib/fpu.pp" --obj_directory="driverlib" $(GEN_OPTS__FLAG) "$<"
	@echo 'Finished building: $<'
	@echo ' '

driverlib/gpio.obj: ../driverlib/gpio.c $(GEN_OPTS) $(GEN_HDRS)
	@echo 'Building file: $<'
	@echo 'Invoking: MSP432 Compiler'
	"C:/ti/ccsv6/tools/compiler/ti-cgt-arm_5.2.7/bin/armcl" -mv7M4 --code_state=16 --float_support=FPv4SPD16 --abi=eabi -me -O2 --include_path="C:/ti/ccsv6/ccs_base/arm/include" --include_path="C:/ti/ccsv6/tools/compiler/ti-cgt-arm_5.2.7/include" --include_path="C:/ti/ccsv6/ccs_base/arm/include/" --include_path="C:/ti/ccsv6/ccs_base/arm/include/CMSIS" --include_path="C:/ti/ccsv6/tools/compiler/ti-cgt-arm_5.2.7/include/" --include_path="C:/Users/Nhut/workspace_v6_1/FreeRTOS/Demo/CORTEX_M4F_MSP432_LaunchPad_IAR_CCS_Keil" --include_path="C:/DevTools/ti/msp430/MSP430ware_1_97_00_47/examples/boards/MSP-EXP432P401R/MSP-EXP432P401R_Software_Examples/Source/OutOfBox_MSP432P401R/driverlib/MSP432P4xx/" --advice:power=all --gcc --define=__MSP432P401R__ --define=TARGET_IS_MSP432P4XX --define=ccs --diag_wrap=off --display_error_number --diag_warning=225 --preproc_with_compile --preproc_dependency="driverlib/gpio.pp" --obj_directory="driverlib" $(GEN_OPTS__FLAG) "$<"
	@echo 'Finished building: $<'
	@echo ' '

driverlib/interrupt.obj: ../driverlib/interrupt.c $(GEN_OPTS) $(GEN_HDRS)
	@echo 'Building file: $<'
	@echo 'Invoking: MSP432 Compiler'
	"C:/ti/ccsv6/tools/compiler/ti-cgt-arm_5.2.7/bin/armcl" -mv7M4 --code_state=16 --float_support=FPv4SPD16 --abi=eabi -me -O2 --include_path="C:/ti/ccsv6/ccs_base/arm/include" --include_path="C:/ti/ccsv6/tools/compiler/ti-cgt-arm_5.2.7/include" --include_path="C:/ti/ccsv6/ccs_base/arm/include/" --include_path="C:/ti/ccsv6/ccs_base/arm/include/CMSIS" --include_path="C:/ti/ccsv6/tools/compiler/ti-cgt-arm_5.2.7/include/" --include_path="C:/Users/Nhut/workspace_v6_1/FreeRTOS/Demo/CORTEX_M4F_MSP432_LaunchPad_IAR_CCS_Keil" --include_path="C:/DevTools/ti/msp430/MSP430ware_1_97_00_47/examples/boards/MSP-EXP432P401R/MSP-EXP432P401R_Software_Examples/Source/OutOfBox_MSP432P401R/driverlib/MSP432P4xx/" --advice:power=all --gcc --define=__MSP432P401R__ --define=TARGET_IS_MSP432P4XX --define=ccs --diag_wrap=off --display_error_number --diag_warning=225 --preproc_with_compile --preproc_dependency="driverlib/interrupt.pp" --obj_directory="driverlib" $(GEN_OPTS__FLAG) "$<"
	@echo 'Finished building: $<'
	@echo ' '

driverlib/pcm.obj: ../driverlib/pcm.c $(GEN_OPTS) $(GEN_HDRS)
	@echo 'Building file: $<'
	@echo 'Invoking: MSP432 Compiler'
	"C:/ti/ccsv6/tools/compiler/ti-cgt-arm_5.2.7/bin/armcl" -mv7M4 --code_state=16 --float_support=FPv4SPD16 --abi=eabi -me -O2 --include_path="C:/ti/ccsv6/ccs_base/arm/include" --include_path="C:/ti/ccsv6/tools/compiler/ti-cgt-arm_5.2.7/include" --include_path="C:/ti/ccsv6/ccs_base/arm/include/" --include_path="C:/ti/ccsv6/ccs_base/arm/include/CMSIS" --include_path="C:/ti/ccsv6/tools/compiler/ti-cgt-arm_5.2.7/include/" --include_path="C:/Users/Nhut/workspace_v6_1/FreeRTOS/Demo/CORTEX_M4F_MSP432_LaunchPad_IAR_CCS_Keil" --include_path="C:/DevTools/ti/msp430/MSP430ware_1_97_00_47/examples/boards/MSP-EXP432P401R/MSP-EXP432P401R_Software_Examples/Source/OutOfBox_MSP432P401R/driverlib/MSP432P4xx/" --advice:power=all --gcc --define=__MSP432P401R__ --define=TARGET_IS_MSP432P4XX --define=ccs --diag_wrap=off --display_error_number --diag_warning=225 --preproc_with_compile --preproc_dependency="driverlib/pcm.pp" --obj_directory="driverlib" $(GEN_OPTS__FLAG) "$<"
	@echo 'Finished building: $<'
	@echo ' '

driverlib/sysctl.obj: ../driverlib/sysctl.c $(GEN_OPTS) $(GEN_HDRS)
	@echo 'Building file: $<'
	@echo 'Invoking: MSP432 Compiler'
	"C:/ti/ccsv6/tools/compiler/ti-cgt-arm_5.2.7/bin/armcl" -mv7M4 --code_state=16 --float_support=FPv4SPD16 --abi=eabi -me -O2 --include_path="C:/ti/ccsv6/ccs_base/arm/include" --include_path="C:/ti/ccsv6/tools/compiler/ti-cgt-arm_5.2.7/include" --include_path="C:/ti/ccsv6/ccs_base/arm/include/" --include_path="C:/ti/ccsv6/ccs_base/arm/include/CMSIS" --include_path="C:/ti/ccsv6/tools/compiler/ti-cgt-arm_5.2.7/include/" --include_path="C:/Users/Nhut/workspace_v6_1/FreeRTOS/Demo/CORTEX_M4F_MSP432_LaunchPad_IAR_CCS_Keil" --include_path="C:/DevTools/ti/msp430/MSP430ware_1_97_00_47/examples/boards/MSP-EXP432P401R/MSP-EXP432P401R_Software_Examples/Source/OutOfBox_MSP432P401R/driverlib/MSP432P4xx/" --advice:power=all --gcc --define=__MSP432P401R__ --define=TARGET_IS_MSP432P4XX --define=ccs --diag_wrap=off --display_error_number --diag_warning=225 --preproc_with_compile --preproc_dependency="driverlib/sysctl.pp" --obj_directory="driverlib" $(GEN_OPTS__FLAG) "$<"
	@echo 'Finished building: $<'
	@echo ' '

driverlib/timer32.obj: ../driverlib/timer32.c $(GEN_OPTS) $(GEN_HDRS)
	@echo 'Building file: $<'
	@echo 'Invoking: MSP432 Compiler'
	"C:/ti/ccsv6/tools/compiler/ti-cgt-arm_5.2.7/bin/armcl" -mv7M4 --code_state=16 --float_support=FPv4SPD16 --abi=eabi -me -O2 --include_path="C:/ti/ccsv6/ccs_base/arm/include" --include_path="C:/ti/ccsv6/tools/compiler/ti-cgt-arm_5.2.7/include" --include_path="C:/ti/ccsv6/ccs_base/arm/include/" --include_path="C:/ti/ccsv6/ccs_base/arm/include/CMSIS" --include_path="C:/ti/ccsv6/tools/compiler/ti-cgt-arm_5.2.7/include/" --include_path="C:/Users/Nhut/workspace_v6_1/FreeRTOS/Demo/CORTEX_M4F_MSP432_LaunchPad_IAR_CCS_Keil" --include_path="C:/DevTools/ti/msp430/MSP430ware_1_97_00_47/examples/boards/MSP-EXP432P401R/MSP-EXP432P401R_Software_Examples/Source/OutOfBox_MSP432P401R/driverlib/MSP432P4xx/" --advice:power=all --gcc --define=__MSP432P401R__ --define=TARGET_IS_MSP432P4XX --define=ccs --diag_wrap=off --display_error_number --diag_warning=225 --preproc_with_compile --preproc_dependency="driverlib/timer32.pp" --obj_directory="driverlib" $(GEN_OPTS__FLAG) "$<"
	@echo 'Finished building: $<'
	@echo ' '

driverlib/uart.obj: ../driverlib/uart.c $(GEN_OPTS) $(GEN_HDRS)
	@echo 'Building file: $<'
	@echo 'Invoking: MSP432 Compiler'
	"C:/ti/ccsv6/tools/compiler/ti-cgt-arm_5.2.7/bin/armcl" -mv7M4 --code_state=16 --float_support=FPv4SPD16 --abi=eabi -me -O2 --include_path="C:/ti/ccsv6/ccs_base/arm/include" --include_path="C:/ti/ccsv6/tools/compiler/ti-cgt-arm_5.2.7/include" --include_path="C:/ti/ccsv6/ccs_base/arm/include/" --include_path="C:/ti/ccsv6/ccs_base/arm/include/CMSIS" --include_path="C:/ti/ccsv6/tools/compiler/ti-cgt-arm_5.2.7/include/" --include_path="C:/Users/Nhut/workspace_v6_1/FreeRTOS/Demo/CORTEX_M4F_MSP432_LaunchPad_IAR_CCS_Keil" --include_path="C:/DevTools/ti/msp430/MSP430ware_1_97_00_47/examples/boards/MSP-EXP432P401R/MSP-EXP432P401R_Software_Examples/Source/OutOfBox_MSP432P401R/driverlib/MSP432P4xx/" --advice:power=all --gcc --define=__MSP432P401R__ --define=TARGET_IS_MSP432P4XX --define=ccs --diag_wrap=off --display_error_number --diag_warning=225 --preproc_with_compile --preproc_dependency="driverlib/uart.pp" --obj_directory="driverlib" $(GEN_OPTS__FLAG) "$<"
	@echo 'Finished building: $<'
	@echo ' '

driverlib/wdt_a.obj: ../driverlib/wdt_a.c $(GEN_OPTS) $(GEN_HDRS)
	@echo 'Building file: $<'
	@echo 'Invoking: MSP432 Compiler'
	"C:/ti/ccsv6/tools/compiler/ti-cgt-arm_5.2.7/bin/armcl" -mv7M4 --code_state=16 --float_support=FPv4SPD16 --abi=eabi -me -O2 --include_path="C:/ti/ccsv6/ccs_base/arm/include" --include_path="C:/ti/ccsv6/tools/compiler/ti-cgt-arm_5.2.7/include" --include_path="C:/ti/ccsv6/ccs_base/arm/include/" --include_path="C:/ti/ccsv6/ccs_base/arm/include/CMSIS" --include_path="C:/ti/ccsv6/tools/compiler/ti-cgt-arm_5.2.7/include/" --include_path="C:/Users/Nhut/workspace_v6_1/FreeRTOS/Demo/CORTEX_M4F_MSP432_LaunchPad_IAR_CCS_Keil" --include_path="C:/DevTools/ti/msp430/MSP430ware_1_97_00_47/examples/boards/MSP-EXP432P401R/MSP-EXP432P401R_Software_Examples/Source/OutOfBox_MSP432P401R/driverlib/MSP432P4xx/" --advice:power=all --gcc --define=__MSP432P401R__ --define=TARGET_IS_MSP432P4XX --define=ccs --diag_wrap=off --display_error_number --diag_warning=225 --preproc_with_compile --preproc_dependency="driverlib/wdt_a.pp" --obj_directory="driverlib" $(GEN_OPTS__FLAG) "$<"
	@echo 'Finished building: $<'
	@echo ' '


