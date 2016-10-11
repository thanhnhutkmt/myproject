################################################################################
# Automatically-generated file. Do not edit!
################################################################################

# Each subdirectory must supply rules for building sources it contributes
FreeRTOS_Source/portable/CCS/ARM_CM4F/port.obj: C:/Users/Nhut/workspace_v6_1/FreeRTOSV8.2.3/FreeRTOS/Source/portable/CCS/ARM_CM4F/port.c $(GEN_OPTS) $(GEN_HDRS)
	@echo 'Building file: $<'
	@echo 'Invoking: MSP432 Compiler'
	"C:/ti/ccsv6/tools/compiler/ti-cgt-arm_5.2.7/bin/armcl" -mv7M4 --code_state=16 --float_support=FPv4SPD16 --abi=eabi -me -O2 --include_path="C:/ti/ccsv6/ccs_base/arm/include" --include_path="C:/ti/ccsv6/tools/compiler/ti-cgt-arm_5.2.7/include" --include_path="C:/ti/ccsv6/ccs_base/arm/include/" --include_path="C:/ti/ccsv6/ccs_base/arm/include/CMSIS" --include_path="C:/ti/ccsv6/tools/compiler/ti-cgt-arm_5.2.7/include/" --include_path="C:/Users/Nhut/workspace_v6_1/FreeRTOSV8.2.3/FreeRTOS/Demo/CORTEX_M4F_MSP432_LaunchPad_IAR_CCS_Keil" --include_path="C:/DevTools/ti/msp430/MSP430ware_1_97_00_47/examples/boards/MSP-EXP432P401R/MSP-EXP432P401R_Software_Examples/Source/OutOfBox_MSP432P401R/driverlib/MSP432P4xx/" --advice:power=all --gcc --define=__MSP432P401R__ --define=TARGET_IS_MSP432P4XX --define=ccs --diag_wrap=off --display_error_number --diag_warning=225 --preproc_with_compile --preproc_dependency="FreeRTOS_Source/portable/CCS/ARM_CM4F/port.pp" --obj_directory="FreeRTOS_Source/portable/CCS/ARM_CM4F" $(GEN_OPTS__FLAG) "$<"
	@echo 'Finished building: $<'
	@echo ' '

FreeRTOS_Source/portable/CCS/ARM_CM4F/portasm.obj: C:/Users/Nhut/workspace_v6_1/FreeRTOSV8.2.3/FreeRTOS/Source/portable/CCS/ARM_CM4F/portasm.asm $(GEN_OPTS) $(GEN_HDRS)
	@echo 'Building file: $<'
	@echo 'Invoking: MSP432 Compiler'
	"C:/ti/ccsv6/tools/compiler/ti-cgt-arm_5.2.7/bin/armcl" -mv7M4 --code_state=16 --float_support=FPv4SPD16 --abi=eabi -me -O2 --include_path="C:/ti/ccsv6/ccs_base/arm/include" --include_path="C:/ti/ccsv6/tools/compiler/ti-cgt-arm_5.2.7/include" --include_path="C:/ti/ccsv6/ccs_base/arm/include/" --include_path="C:/ti/ccsv6/ccs_base/arm/include/CMSIS" --include_path="C:/ti/ccsv6/tools/compiler/ti-cgt-arm_5.2.7/include/" --include_path="C:/Users/Nhut/workspace_v6_1/FreeRTOSV8.2.3/FreeRTOS/Demo/CORTEX_M4F_MSP432_LaunchPad_IAR_CCS_Keil" --include_path="C:/DevTools/ti/msp430/MSP430ware_1_97_00_47/examples/boards/MSP-EXP432P401R/MSP-EXP432P401R_Software_Examples/Source/OutOfBox_MSP432P401R/driverlib/MSP432P4xx/" --advice:power=all --gcc --define=__MSP432P401R__ --define=TARGET_IS_MSP432P4XX --define=ccs --diag_wrap=off --display_error_number --diag_warning=225 --preproc_with_compile --preproc_dependency="FreeRTOS_Source/portable/CCS/ARM_CM4F/portasm.pp" --obj_directory="FreeRTOS_Source/portable/CCS/ARM_CM4F" $(GEN_OPTS__FLAG) "$<"
	@echo 'Finished building: $<'
	@echo ' '


