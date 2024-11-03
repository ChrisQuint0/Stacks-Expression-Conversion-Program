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
 */
public class IT2A_Group2_Lab4 {

  static char[] array;
  static int top;
  static int capacity;

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
        convertInfixToPostFix();
        clearScreen();
        System.out.println("Convert Infix to Post Fix\n");
        pause();
        break;
      case 2:
        convertInfixToPrefix();
        break;
      case 3:
        // convertPostfixtoInfix();
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

  public static void convertInfixToPostFix() {
    Scanner sc = new Scanner(System.in);

    while (true) {
        clearScreen();
        System.out.println("INFIX TO POSTFIX");
        System.out.println("-----------------------------");

        System.out.print("Enter the infix expression: ");
        String infix = sc.nextLine().replaceAll("\\s", "");

        // Validate the input expression
        if (!isValidInfix(infix)) {
            pause();
            return;
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
                while (!stack.isEmpty() && getPrecendence(c) <= getPrecendence(stack.peek())) {
                    result.append(stack.pop());
                }
                stack.push(c); // Push current operator to stack
            }
        }

        // Pop any remaining operators in the stack
        while (!stack.isEmpty()) {
            result.append(stack.pop());
        }

        System.out.println("\nInfix expression: " + infix);
        System.out.println("Postfix expression: " + result + "\n");

        // Prompt user to try again
        System.out.print("Try Again? (Y/N): ");
        String choice = sc.nextLine().trim().toUpperCase();
        if (choice.equals("N")) break;
    }
}


  // Quinto
  public static void convertInfixToPrefix() {
    while (true) {
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
        return;
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

      String postfix = infixToPostFix(infix, infix.length());

      System.out.println("Prefix expression: " + reverse(postfix) + "\n");

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

          System.out.println("INFIX TO PREFIX");
          System.out.println("-----------------------------");

        }

      }

      if (choice.charAt(0) == 'N') {
        break;
      }

    }
  }

  // Quinto Infix to Postfix I will erase this once Josh made his own algorithm
  // and that's the one that I will use for the infix to prefix algo
  public static String infixToPostFix(String infix, int size) {
    StringBuilder result = new StringBuilder();
    IT2A_Group2_Lab4 stack = new IT2A_Group2_Lab4(size);

    for (int i = 0; i < infix.length(); i++) {
      char c = infix.charAt(i);

      if (Character.isLetterOrDigit(c)) {
        result.append(c);
      }

      else if (c == '(') {
        stack.push(c);
      }

      else if (c == ')') {
        while (!stack.isEmpty() && stack.peek() != '(') {
          result.append(stack.pop());
        }
        stack.pop();
      }

      else if (isOperator(c)) {
        while (!stack.isEmpty() && getPrecendence(c) <= getPrecendence(stack.peek())) {
          result.append(stack.pop());
        }

        stack.push(c);
      }
    }

    while (!stack.isEmpty()) {
      result.append(stack.pop());
    }

    return result.toString();

  }

  // Method to check if the input expression is a valid infix expression
  public static boolean isValidInfix(String expression) {
    int parenthesesCount = 0;
    char previousChar = ' ';

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