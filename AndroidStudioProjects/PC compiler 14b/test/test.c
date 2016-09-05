#include <stdio.h>
void changePointer1(int *ptr) {
	printf("start function : pointer at %d points to %d which value is %d\n", &ptr, ptr, *ptr);
	int temp = 10;
	ptr = &temp;
	printf("end function : pointer at %d points to %d which value is %d\n", &ptr, ptr, *ptr);
}

void changePointer2(int *ptr) {
	printf("start function : pointer at %d points to %d which value is %d\n", &ptr, ptr, *ptr);
	int temp = 10;
	*ptr = temp;
	printf("end function : pointer at %d points to %d which value is %d\n", &ptr, ptr, *ptr);
}

int main (void) {
	int *pointer;
	*pointer = 5;
	int t = 16;
	char test = 128;
	printf("hi \n");
	printf("pointer at %d points to %d which value is %d\n", &pointer, pointer, *pointer);
	changePointer1(pointer);
	printf("pointer at %d points to %d which value is %d\n", &pointer, pointer, *pointer);
	changePointer2(pointer);
	printf("pointer at %d points to %d which value is %d\n", &pointer, pointer, *pointer);
	pointer = &t;
	printf("pointer at %d points to %d which value is %d\n", &pointer, pointer, *pointer);
	
	printf("char value : %d\n", test);
	int morevar = 10;
	int anothervar = 16;
	printf("char value : %d\n", morevar);	
	printf("char value : %d\n", anothervar);	
	for (morevar = 0; morevar; morevar++) {};
	do {
		
		
	}while(morevar > anothervar, morevar++);
	
}
