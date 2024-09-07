#include <stdio.h>

// go to path: cd src\main\resources
// compile: gcc -g -o file_1 file_1.c
// debug: gdb file_1.exe
    // break file_1.c:13
    // run
    // bt
    // continue
    // quit
// for later: p i
int main() {
    int sum = 0;
    for (int i = 1; i <= 10; ++i) {
        sum += i;
    }
    printf("Sum of first 10 natural numbers: %d\n", sum);
    return 0;
}
