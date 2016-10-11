################################################################################
# Automatically-generated file. Do not edit!
################################################################################

# Each subdirectory must supply rules for building sources it contributes
lab3_memory.obj: ../lab3_memory.c $(GEN_OPTS) $(GEN_HDRS)
	@echo 'Building file: $<'
	@echo 'Invoking: MSP432 Compiler'
	"C:/ti/ccsv6/tools/compiler/ti-cgt-arm_5.2.7/bin/armcl" -mv7M4 --code_state=16 --float_support=FPv4SPD16 --abi=eabi -me --include_path="C:/ti/ccsv6/ccs_base/arm/include" --include_path="C:/ti/ccsv6/tools/compiler/ti-cgt-arm_5.2.7/include" --include_path="C:/Users/Nhut/workspace_v6_1/lab3_memory/driverlib" --include_path="C:/ti/ccsv6/ccs_base/arm/include/CMSIS" -g --gcc --define=TARGET_IS_MSP432P4XX --define=ccs --define=__MSP432P401R__ --diag_wrap=off --display_error_number --diag_warning=225 --preproc_with_compile --preproc_dependency="lab3_memory.pp" $(GEN_OPTS__FLAG) "$<"
	@echo 'Finished building: $<'
	@echo ' '

lab3_memory_startup_ccs.obj: ../lab3_memory_startup_ccs.c $(GEN_OPTS) $(GEN_HDRS)
	@echo 'Building file: $<'
	@echo 'Invoking: MSP432 Compiler'
	"C:/ti/ccsv6/tools/compiler/ti-cgt-arm_5.2.7/bin/armcl" -mv7M4 --code_state=16 --float_support=FPv4SPD16 --abi=eabi -me --include_path="C:/ti/ccsv6/ccs_base/arm/include" --include_path="C:/ti/ccsv6/tools/compiler/ti-cgt-arm_5.2.7/include" --include_path="C:/Users/Nhut/workspace_v6_1/lab3_memory/driverlib" --include_path="C:/ti/ccsv6/ccs_base/arm/include/CMSIS" -g --gcc --define=TARGET_IS_MSP432P4XX --define=ccs --define=__MSP432P401R__ --diag_wrap=off --display_error_number --diag_warning=225 --preproc_with_compile --preproc_dependency="lab3_memory_startup_ccs.pp" $(GEN_OPTS__FLAG) "$<"
	@echo 'Finished building: $<'
	@echo ' '


