#include <stdio.h>

// go to path: cd src\main\resources
// compile: gcc -g -o file_2 file_2.c
// debug: gdb file_2.exe
    // break file_2.c:13
    // run
    // bt
    // continue
    // quit
// for later: p i

int fibonacci(int n) {
    if (n <= 1) {
        return n;
    }
    return fibonacci(n - 1) + fibonacci(n - 2);
}

int main() {
    printf("%d\n", fibonacci(5));

    for (int i = 0; i < 10; i++) {
        printf("%d ", fibonacci(i));
    }
    return 0;
}
