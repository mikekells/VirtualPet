import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

public class Main {

    public static void main(String[] args) {
        Pet pet = new Pet();
        Scanner scanner = new Scanner(System.in);

        System.out.println("Welcome to Virtual Pet App!");

        if (pet.checkSaveExists()) {
            pet.loadData();
        } else {
            System.out.println("Enter your pet's name:");
            String petName = scanner.nextLine();
            pet.setName(petName);
        }

        while (true) {
            System.out.println("\nWhat would you like to do?");
            System.out.println("1. Feed " + pet.getName());
            System.out.println("2. Give Drink to " + pet.getName());
            System.out.println("3. Play with " + pet.getName());
            System.out.println("4. Show " + pet.getName() + "'s status");
            System.out.println("5. Put " + pet.getName() + " in the bucket of water");
            System.out.println("6. Quit playing");
            System.out.println("\n");

            pet.showStatus();

            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    pet.feed();
                    pet.showStatus();
                    break;
                case 2:
                    pet.drink();
                    pet.showStatus();
                    break;
                case 3:
                    pet.play();
                    pet.showStatus();
                    break;
                case 4:
                    pet.showStatus();
                    break;
                case 5:
                    System.out.println("You hold " + pet.getName() + "'s head in the bucket of water.");
                    System.out.println(pet.getName() + " is dead...");
                    System.out.println("Thanks for playing!");
                    pet.killPet();

                case 6:
                    System.out.println("Thanks for playing!");
                    pet.saveData();
                    System.exit(0);
                default:
                    System.out.println("Invalid choice. Please enter a number between 1 and 4.");
            }
        }
    }
}