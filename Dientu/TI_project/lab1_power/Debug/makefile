################################################################################
# Automatically-generated file. Do not edit!
################################################################################

SHELL = cmd.exe

CG_TOOL_ROOT := C:/ti/ccsv6/tools/compiler/ti-cgt-arm_5.2.7

GEN_OPTS__FLAG := 
GEN_CMDS__FLAG := 

ORDERED_OBJS += \
"./lab1_power.obj" \
"./lab1_power_startup_ccs.obj" \
"./driverlib/adc14.obj" \
"./driverlib/aes256.obj" \
"./driverlib/comp_e.obj" \
"./driverlib/cpu.obj" \
"./driverlib/crc32.obj" \
"./driverlib/cs.obj" \
"./driverlib/dma.obj" \
"./driverlib/flash.obj" \
"./driverlib/fpu.obj" \
"./driverlib/gpio.obj" \
"./driverlib/i2c.obj" \
"./driverlib/interrupt.obj" \
"./driverlib/mpu.obj" \
"./driverlib/pcm.obj" \
"./driverlib/pmap.obj" \
"./driverlib/pss.obj" \
"./driverlib/ref_a.obj" \
"./driverlib/reset.obj" \
"./driverlib/rtc_c.obj" \
"./driverlib/spi.obj" \
"./driverlib/sysctl.obj" \
"./driverlib/systick.obj" \
"./driverlib/timer32.obj" \
"./driverlib/timer_a.obj" \
"./driverlib/uart.obj" \
"./driverlib/wdt_a.obj" \
"../msp432p401r.cmd" \
$(GEN_CMDS__FLAG) \
-l"libc.a" \

-include ../makefile.init

RM := DEL /F
RMDIR := RMDIR /S/Q

# All of the sources participating in the build are defined here
-include sources.mk
-include subdir_vars.mk
-include driverlib/subdir_vars.mk
-include subdir_rules.mk
-include driverlib/subdir_rules.mk
-include objects.mk

ifneq ($(MAKECMDGOALS),clean)
ifneq ($(strip $(S_DEPS)),)
-include $(S_DEPS)
endif
ifneq ($(strip $(S_UPPER_DEPS)),)
-include $(S_UPPER_DEPS)
endif
ifneq ($(strip $(S62_DEPS)),)
-include $(S62_DEPS)
endif
ifneq ($(strip $(C64_DEPS)),)
-include $(C64_DEPS)
endif
ifneq ($(strip $(ASM_DEPS)),)
-include $(ASM_DEPS)
endif
ifneq ($(strip $(CC_DEPS)),)
-include $(CC_DEPS)
endif
ifneq ($(strip $(S55_DEPS)),)
-include $(S55_DEPS)
endif
ifneq ($(strip $(C67_DEPS)),)
-include $(C67_DEPS)
endif
ifneq ($(strip $(CLA_DEPS)),)
-include $(CLA_DEPS)
endif
ifneq ($(strip $(C??_DEPS)),)
-include $(C??_DEPS)
endif
ifneq ($(strip $(CPP_DEPS)),)
-include $(CPP_DEPS)
endif
ifneq ($(strip $(S??_DEPS)),)
-include $(S??_DEPS)
endif
ifneq ($(strip $(C_DEPS)),)
-include $(C_DEPS)
endif
ifneq ($(strip $(C62_DEPS)),)
-include $(C62_DEPS)
endif
ifneq ($(strip $(CXX_DEPS)),)
-include $(CXX_DEPS)
endif
ifneq ($(strip $(C++_DEPS)),)
-include $(C++_DEPS)
endif
ifneq ($(strip $(ASM_UPPER_DEPS)),)
-include $(ASM_UPPER_DEPS)
endif
ifneq ($(strip $(K_DEPS)),)
-include $(K_DEPS)
endif
ifneq ($(strip $(C43_DEPS)),)
-include $(C43_DEPS)
endif
ifneq ($(strip $(INO_DEPS)),)
-include $(INO_DEPS)
endif
ifneq ($(strip $(S67_DEPS)),)
-include $(S67_DEPS)
endif
ifneq ($(strip $(SA_DEPS)),)
-include $(SA_DEPS)
endif
ifneq ($(strip $(S43_DEPS)),)
-include $(S43_DEPS)
endif
ifneq ($(strip $(OPT_DEPS)),)
-include $(OPT_DEPS)
endif
ifneq ($(strip $(PDE_DEPS)),)
-include $(PDE_DEPS)
endif
ifneq ($(strip $(S64_DEPS)),)
-include $(S64_DEPS)
endif
ifneq ($(strip $(C_UPPER_DEPS)),)
-include $(C_UPPER_DEPS)
endif
ifneq ($(strip $(C55_DEPS)),)
-include $(C55_DEPS)
endif
endif

-include ../makefile.defs

# Add inputs and outputs from these tool invocations to the build variables 
EXE_OUTPUTS += \
lab1_power.out \

EXE_OUTPUTS__QUOTED += \
"lab1_power.out" \

BIN_OUTPUTS += \
lab1_power.hex \

BIN_OUTPUTS__QUOTED += \
"lab1_power.hex" \


# All Target
all: lab1_power.out

# Tool invocations
lab1_power.out: $(OBJS) $(CMD_SRCS) $(GEN_CMDS)
	@echo 'Building target: $@'
	@echo 'Invoking: MSP432 Linker'
	"C:/ti/ccsv6/tools/compiler/ti-cgt-arm_5.2.7/bin/armcl" -mv7M4 --code_state=16 --float_support=FPv4SPD16 --abi=eabi -me --advice:power="all" -g --gcc --define=ccs --define=USE_MSP430_REGISTER_FORMAT --define=USE_CMSIS_REGISTER_FORMAT --define=__MSP432P401R__ --define=TARGET_IS_MSP432P4XX --diag_wrap=off --display_error_number --diag_warning=225 -z -m"lab1_power.map" --heap_size=0 --stack_size=512 -i"C:/ti/ccsv6/tools/compiler/ti-cgt-arm_5.2.7/lib" -i"C:/ti/ccsv6/tools/compiler/ti-cgt-arm_5.2.7/include" --reread_libs --display_error_number --diag_wrap=off --warn_sections --xml_link_info="lab1_power_linkInfo.xml" --rom_model -o "lab1_power.out" $(ORDERED_OBJS)
	@echo 'Finished building target: $@'
	@echo ' '

lab1_power.hex: $(EXE_OUTPUTS)
	@echo 'Invoking: MSP432 Hex Utility'
	"C:/ti/ccsv6/tools/compiler/ti-cgt-arm_5.2.7/bin/armhex"  -o "lab1_power.hex" $(EXE_OUTPUTS__QUOTED)
	@echo 'Finished building: $@'
	@echo ' '

# Other Targets
clean:
	-$(RM) $(EXE_OUTPUTS__QUOTED)$(BIN_OUTPUTS__QUOTED)
	-$(RM) "lab1_power.pp" "lab1_power_startup_ccs.pp" "driverlib\adc14.pp" "driverlib\aes256.pp" "driverlib\comp_e.pp" "driverlib\cpu.pp" "driverlib\crc32.pp" "driverlib\cs.pp" "driverlib\dma.pp" "driverlib\flash.pp" "driverlib\fpu.pp" "driverlib\gpio.pp" "driverlib\i2c.pp" "driverlib\interrupt.pp" "driverlib\mpu.pp" "driverlib\pcm.pp" "driverlib\pmap.pp" "driverlib\pss.pp" "driverlib\ref_a.pp" "driverlib\reset.pp" "driverlib\rtc_c.pp" "driverlib\spi.pp" "driverlib\sysctl.pp" "driverlib\systick.pp" "driverlib\timer32.pp" "driverlib\timer_a.pp" "driverlib\uart.pp" "driverlib\wdt_a.pp" 
	-$(RM) "lab1_power.obj" "lab1_power_startup_ccs.obj" "driverlib\adc14.obj" "driverlib\aes256.obj" "driverlib\comp_e.obj" "driverlib\cpu.obj" "driverlib\crc32.obj" "driverlib\cs.obj" "driverlib\dma.obj" "driverlib\flash.obj" "driverlib\fpu.obj" "driverlib\gpio.obj" "driverlib\i2c.obj" "driverlib\interrupt.obj" "driverlib\mpu.obj" "driverlib\pcm.obj" "driverlib\pmap.obj" "driverlib\pss.obj" "driverlib\ref_a.obj" "driverlib\reset.obj" "driverlib\rtc_c.obj" "driverlib\spi.obj" "driverlib\sysctl.obj" "driverlib\systick.obj" "driverlib\timer32.obj" "driverlib\timer_a.obj" "driverlib\uart.obj" "driverlib\wdt_a.obj" 
	-@echo 'Finished clean'
	-@echo ' '

.PHONY: all clean dependents
.SECONDARY:

-include ../makefile.targets

