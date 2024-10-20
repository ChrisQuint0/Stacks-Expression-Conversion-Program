import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * A Program that converts infix notation to post-fix notation which is a
 * definite example of the stack data structures
 * 
 * Group 2
 * Authors: Goto, Ipei B. (Leader)
 * Quinto, Christopher A. (Members)
 * Talato, Joshua P.
 * Laboratory Excercise #2
 * Date : October 20, 2024
 */
public class IT2A_Group2_Lab2 {

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

      if (choice < 0 || choice > 2) {
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
    System.out.println("             MENU         ");
    System.out.println("-----------------------------");
    System.out.println("[1] Convert Infix to Post Fix");
    System.out.println();
    System.out.println("[2] Convert Postfix to Infix");

    System.out.print("\nEnter your choice: ");
  }

  public static void executeChoice(int choice) {
    switch (choice) {
      case 1:
        // convertInfixToPostFix();
        clearScreen();
        System.out.println("Convert Infix to Post Fix\n");
        pause();
        break;
      case 2:
        // convertPostFixtoInfix();
        clearScreen();
        System.out.println("Convert Post Fix to Infix\n");
        pause();
        break;
      default:
        break;
    }
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