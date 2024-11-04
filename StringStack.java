public class StringStack {
    static String[] stringStack;
    static int stringStackTop;
    static int stringStackCapacity;

    // overloading.
    StringStack(int size) {
        stringStack = new String[size];
        stringStackCapacity = size;
        stringStackTop = -1;

    }

    public Boolean isEmpty() {
        return stringStackTop == -1;
    }

    public Boolean isFull() {
        return stringStackTop == stringStackCapacity - 1;
    }

    // overloading.
    public void push(String x) {
        if (isFull()) {
            System.out.println("Overflow\nProgram Terminated\n");
            System.exit(1);
        }

        stringStack[++stringStackTop] = x;
    }

    // overloading.
    public String pop() {

        if (isEmpty()) {
            System.out.println("Stack is empty\n");
            System.exit(1);
        }

        return stringStack[stringStackTop--];
    }

    // overloading.
    public String peek() {
        if (!(stringStackTop >= 0)) {
            System.out.println("Stack is empty");
            System.exit(1);
        }

        return stringStack[stringStackTop];
    }
}
