import java.util.Scanner;

public class GameEngine {
    private Pet pet;

    public GameEngine(Pet pet) {
        this.pet = pet;
    }

    public void start() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome to Virtual Pet App!");
        pet.startTimer();

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
                    break;
                case 2:
                    pet.drink();
                    break;
                case 3:
                    pet.play();
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
                    pet.stopTimer();
                    return;
                default:
                    System.out.println("Invalid choice. Please enter a number between 1 and 4.");
            }
//            System.out.println("\n" + pet);
//            System.out.println("Your pet has passed away. Game over.");
//            pet.stopTimer(); // Stop the timer
        }
    }

    public static void main(String[] args) {
        Pet pet = new Pet();
        GameEngine gameEngine = new GameEngine(pet);
        gameEngine.start();
    }
}