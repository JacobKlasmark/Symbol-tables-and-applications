#include <stdio.h>

char reversed_recursion (void){
		
		char c = getc(stdin);
		if (c != '\n')
			reversed_recursion(stdin); 
		putc(c, stdout);
	}	

int main (void){
reversed_recursion();
}
