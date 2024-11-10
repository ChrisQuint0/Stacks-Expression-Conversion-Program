import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * A Program that converts an expression from infix notation to post-fix
 * notation, from infix to prefix, and from postfix to infix which is a
 * definite example application of the stack data structure
 * 
 * Group 2
 * Authors: Goto, Ipei B. (Leader)
 * Quinto, Christopher A. (Members)
 * Talato, Joshua P.
 * Laboratory Excercise #2
 * Date : October 20, 2024
 * 
 * Test Cases:
 * 1. (A + B) * (C - D) / (E + F * G) - H
 * -Infix to Postfix: A B + C D - * E F G * + / H -
 * -Infix to Prefix: - / * + A B - C D + E * F G H
 * 
 */
public class IT2A_Group2_Lab4 {

  static char[] array;
  static int top;
  static int capacity;
  static boolean convertingInfixToPrefix = false;

  // Creating the stack
  IT2A_Group2_Lab4(int size) {
    array = new char[size];
    capacity = size;
    top = -1;
  }

  public void push(char x) {
    if (isFull()) {
      System.out.println("Overflow\nProgram Terminated\n");
      System.exit(1);
    }

    array[++top] = x;
  }

  public char pop() {
    if (isEmpty()) {
      System.out.println("Stack is empty\n");
      System.exit(1);
    }

    return array[top--];
  }

  public char peek() {
    if (!(top >= 0)) {
      System.out.println("Stack is empty");
      System.exit(1);
    }

    return array[top];
  }

  public int size() {
    return top + 1;
  }

  public Boolean isEmpty() {
    return top == -1;
  }

  public Boolean isFull() {
    return top == capacity - 1;
  }

  public void printStack() {
    for (int i = 0; i <= top; i++) {
      System.out.print(array[i]);
    }
  }

  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);

    int choice = -1;

    do {
      clearScreen();
      displayMenu();

      try {
        choice = sc.nextInt();
      } catch (InputMismatchException e) {
        sc.next();
        clearScreen();

        System.out.println("ERROR! ");
        System.out.println("-----------------------------");
        System.out.println("Invalid Input. Please enter an integer\n");

        pause();
        clearScreen();
        continue;
      }

      if (choice < 0 || choice > 3) {
        clearScreen();

        System.out.println("ERROR!");
        System.out.println("-----------------------------");
        System.out.println("Please enter an integer from the choices\n");
        pause();

        clearScreen();
        continue;
      } else {
        executeChoice(choice);
      }

    } while (choice != 0);

    clearScreen();
    System.out.println("EXIT SUCCESSFUL");
    System.out.println("-----------------------------");
    System.out.println("Program Terminated\n");
  }

  public static void displayMenu() {
    System.out.println(" MENU ");
    System.out.println("-----------------------------");
    System.out.println("[1] Infix to Postfix");
    System.out.println();
    System.out.println("[2] Infix to Prefix");
    System.out.println();
    System.out.println("[3] Postfix to Infix");
    System.out.println();
    System.out.println("[0] Exit");

    System.out.print("\nEnter your choice: ");

  }

  public static void executeChoice(int choice) {
    switch (choice) {
      case 1:
        convertInfixToPostFix(null);
        clearScreen();
        break;
      case 2:
        convertInfixToPrefix();
        break;
      case 3:
        convertPostfixtoInfix();
      default:
        break;
    }
  }

  // Method to check if the character is an operator
  public static boolean isOperator(char c) {
    return c == '+' || c == '-' || c == '*' || c == '/' || c == '^';
  }

  public static int getPrecendence(char op) {
    switch (op) {
      case '+':
      case '-':
        return 1;
      case '*':
      case '/':
        return 2;
      case '^':
        return 3;
      default:
        return -1;
    }
  }

  public static String reverse(String str) {
    StringBuilder sb = new StringBuilder(str);
    return sb.reverse().toString();
  }

  // Josh
  public static String convertInfixToPostFix(String passedInfix) {
    Scanner sc = new Scanner(System.in);

    String infix = "";

    while (true) {
      if (convertingInfixToPrefix) {
        infix = passedInfix;
      } else {
        clearScreen();
        System.out.println("INFIX TO POSTFIX");
        System.out.println("-----------------------------");

        System.out.print("Enter the infix expression: ");

        infix = sc.nextLine().replaceAll("\\s", "");
      }

      // Validate the input expression
      if (!isValidInfix(infix)) {
        pause();
        continue;
      }

      StringBuilder result = new StringBuilder();
      IT2A_Group2_Lab4 stack = new IT2A_Group2_Lab4(infix.length());

      for (char c : infix.toCharArray()) {
        if (Character.isLetterOrDigit(c)) {
          result.append(c); // Append operands directly to result
        } else if (c == '(') {
          stack.push(c); // Push '(' to stack
        } else if (c == ')') {
          while (!stack.isEmpty() && stack.peek() != '(') {
            result.append(stack.pop());
          }
          stack.pop(); // Pop '('
        } else if (isOperator(c)) {
          if (convertingInfixToPrefix) {
            while (!stack.isEmpty() && getPrecendence(c) < getPrecendence(stack.peek())) {
              result.append(stack.pop());
            }
            stack.push(c); // Push current operator to stack
          } else {
            while (!stack.isEmpty() && getPrecendence(c) <= getPrecendence(stack.peek())) {
              result.append(stack.pop());
            }
            stack.push(c); // Push current operator to stack
          }

        }
      }

      // Pop any remaining operators in the stack
      while (!stack.isEmpty()) {
        result.append(stack.pop());
      }

      String choice = "";

      if (!convertingInfixToPrefix) {
        System.out.println("\nInfix expression: " + infix);
        System.out.println("Postfix expression: " + result + "\n");

        // Prompt user to try again
        while (true) {
          System.out.print("Try Again? (Y/N): ");
          choice = sc.nextLine().trim().toUpperCase();

          if (choice.equals("N")) {
            return "";
          } else if (choice.equals("Y")) {
            break;
          } else {
            System.out.println("Invalid Input. Please enter Y/N");
            continue;
          }
        }

        if (choice.equals("Y")) {
          continue;
        }

      }

      convertingInfixToPrefix = false;
      return result.toString();

    }
  }

  // Quinto
  public static void convertInfixToPrefix() {
    while (true) {
      convertingInfixToPrefix = true;
      clearScreen();
      Scanner sc = new Scanner(System.in);

      String infix;

      System.out.println("INFIX TO PREFIX");
      System.out.println("-----------------------------");
      System.out.print("Enter the infix expression: ");
      infix = sc.nextLine();

      infix = infix.replaceAll("\\s", "");

      boolean isValid = isValidInfix(infix);

      if (!isValid) {
        pause();
        continue;
      }

      System.out.println("\nInfix expression: " + infix);

      infix = reverse(infix);

      char[] chars = infix.toCharArray();
      for (int i = 0; i < chars.length; i++) {
        if (chars[i] == '(') {
          chars[i] = ')';
        } else if (chars[i] == ')') {
          chars[i] = '(';
        }
      }

      infix = new String(chars);

      String postfix = convertInfixToPostFix(infix);

      System.out.println("Prefix expression: " + reverse(postfix) + "\n");

      String choice;

      while (true) {
        System.out.print("\nTry Again? (Y/N): ");

        try {
          choice = sc.nextLine().toUpperCase();

          if (choice.length() > 1 || choice.length() <= 0 || choice.charAt(0) != 'Y' && choice.charAt(0) != 'N') {
            System.out.println("You must enter Y/N\n");
            continue;
          }

          break;
        } catch (InputMismatchException e) {
          clearScreen();
          System.out.println("ERROR!");
          System.out.println("-------------------------");
          System.out.println("Invalid Input\n");

          pause();
          clearScreen();

          System.out.println("INFIX TO PREFIX");
          System.out.println("-----------------------------");

        }

      }

      if (choice.charAt(0) == 'N') {
        convertingInfixToPrefix = false;
        break;
      }

    }
  }

  public static void convertPostfixtoInfix() {
    while (true) {
      clearScreen();
      Scanner sc = new Scanner(System.in);

      String postfix;

      System.out.println("POSTFIX TO INFIX");
      System.out.println("-----------------------------");
      System.out.print("Enter the postfix expression: ");
      postfix = sc.nextLine();
      postfix = postfix.replaceAll("\\s", "");

      boolean isValid = isValidPostfix(postfix);

      if (!isValid) {
        pause();
        continue;
      }

      String infix = postfixToInfix(postfix);

      System.out.println("Infix expression: " + infix);

      String choice;

      while (true) {
        System.out.println("\nTry Again? (Y/N): ");

        try {
          choice = sc.nextLine().toUpperCase();

          if (choice.length() > 1 || choice.charAt(0) != 'Y' && choice.charAt(0) != 'N') {
            System.out.println("You must enter Y/N\n");
            continue;
          }

          break;
        } catch (InputMismatchException e) {
          clearScreen();
          System.out.println("ERROR!");
          System.out.println("-------------------------");
          System.out.println("Invalid Input\n");

          pause();
          clearScreen();

          System.out.println("POSTFIX TO INFIX");
          System.out.println("-----------------------------");

        }

      }

      if (choice.charAt(0) == 'N') {
        break;
      }

    }
  }

  static String postfixToInfix(String exp) {
    StringStack stack = new StringStack(exp.length());

    for (int i = 0; i < exp.length(); i++) {
      // Push operands
      if (!isOperator(exp.charAt(i))) {
        stack.push(exp.charAt(i) + "");
      }

      // We assume that input is
      // a valid postfix and expect
      // an operator.
      else {
        String op1 = stack.peek();
        stack.pop();
        String op2 = stack.peek();
        stack.pop();
        stack.push("(" + op2 + exp.charAt(i) +
            op1 + ")");
      }
    }

    // There must be a single element
    // in stack now which is the required
    // infix.
    return stack.peek();
  }

  public static boolean isValidPostfix(String expression) {

    if (expression.isEmpty()) {
      System.out.println("\nGiven expression is empty!\n");
      return false;
    }

    for (int i = 0; i < expression.length(); i++) {
      char currentChar = expression.charAt(i);

      // Check for valid characters
      if (!Character.isLetterOrDigit(currentChar) &&
          "+-*/^()".indexOf(currentChar) == -1) {
        System.out.println("\nInvalid character detected: " + currentChar + "\n");
        return false;
      }

      // check if first character is an operator
      if (i == 0 && isOperator(currentChar)) {
        System.out.println("\nOperators cannot be in the first position or come before operands!\n");
        return false;
      }
    }

    // check amount of operands to operators
    int operatorAmount = 0;
    int operandsAmount = 0;
    for (int i = 0; i < expression.length(); i++) {
      char currentChar = expression.charAt(i);
      if (isOperator(currentChar)) {
        operatorAmount++;
      } else {
        operandsAmount++;
      }
    }

    if (operatorAmount >= operandsAmount) {
      System.out.println("\nToo many operators! Amount of operators must be less than the amount of operands!\n");
      return false;
    }

    if (operandsAmount > operatorAmount + 1) {
      System.out.println("\nToo many operands! Not enough operators available for the amount of operands!\n");
      return false;
    }
    return true;
  }

  // Method to check if the input expression is a valid infix expression
  public static boolean isValidInfix(String expression) {
    int parenthesesCount = 0;
    char previousChar = ' ';

    if (expression.length() <= 0) {
      System.out.println("Invalid. You must input an expression\n");
      return false;
    }

    for (int i = 0; i < expression.length(); i++) {
      char currentChar = expression.charAt(i);

      // Check for valid characters
      if (!Character.isLetterOrDigit(currentChar) &&
          "+-*/^()".indexOf(currentChar) == -1) {
        System.out.println("\nInvalid character detected: " + currentChar + "\n");
        return false;
      }

      // Check for balanced parentheses
      if (currentChar == '(') {
        parenthesesCount++;
      } else if (currentChar == ')') {
        parenthesesCount--;
        if (parenthesesCount < 0) {
          System.out.println("\nUnmatched closing parenthesis detected." + "\n");
          return false;
        }

      }

      // Consecutive Operands
      if (Character.isLetterOrDigit(currentChar) &&
          Character.isLetterOrDigit(previousChar)) {
        System.out.println("\nConsecutive operands detected: " + previousChar +
            currentChar + "\n");
        return false;
      }

      // Check for consecutive operators
      if ("+-*/^".indexOf(currentChar) != -1) {
        if ("+-*/^".indexOf(previousChar) != -1) {
          System.out.println("\nConsecutive operators detected: " + previousChar + currentChar + "\n");
          return false;
        }
      }

      // Check for empty parentheses
      if (currentChar == '(' && i < expression.length() - 1 && expression.charAt(i + 1) == ')') {
        System.out.println("\nEmpty parentheses detected.");
        return false;
      }

      // Leading or trailing operators
      if (i == 0 && "+*/^".indexOf(currentChar) != -1) {
        System.out.println("\nExpression starts with an invalid operator: " + currentChar + "\n");
        return false;
      }

      // Check for leading operators after an opening parenthesis
      if (i > 0 && expression.charAt(i - 1) == '(' && "+*/^".indexOf(currentChar) != -1) {
        System.out.println("\nExpression has an invalid leading operator after '(': " + currentChar + "\n");
        return false;
      }

      if (i == expression.length() - 1 && "+-*/^".indexOf(currentChar) != -1) {
        System.out.println("\nExpression ends with an invalid operator: " + currentChar + "\n");
        return false;
      }

      // Check for trailing operators before a closing parenthesis
      if (i < expression.length() - 1 && expression.charAt(i + 1) == ')' && "+-*/^".indexOf(currentChar) != -1) {
        System.out.println("\nExpression has an invalid trailing operator before ')': " + currentChar + "\n");
        return false;
      }

      // Check for missing operator between two parenthesized expressions
      if (previousChar == ')' && currentChar == '(') {
        System.out.println("\nMissing operator between expressions: " + previousChar + currentChar + "\n");
        return false;
      }

      previousChar = currentChar;
    }

    // Final check for balanced parentheses
    if (parenthesesCount != 0) {
      System.out.println("\nUnbalanced parentheses in the expression.\n");
      return false;
    }

    return true;
  }

  public static void clearScreen() {
    System.out.print("\033[H\033[2J");
    System.out.flush();
  }

  public static void pause() {
    Scanner sc = new Scanner(System.in);
    System.out.println("Press any key to continue...");

    sc.nextLine();

  }
}

class StringStack {
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
