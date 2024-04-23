import java.io.*;
import java.util.Timer;
import java.util.TimerTask;

public class Pet {
    private String name;
    private int health;
    private int age;
    private int hunger;
    private int thirst;
    private int happiness;
    private boolean needsToilet;
    private boolean isSick;
    private int timeCount;

    private static final String SAVE_FILE_PATH = "virtual_pet_data.txt";

    public Pet(String name) {
        this.name = name;
        this.health = 100;
        this.age = 0;
        this.hunger = 50;
        this.thirst = 50;
        this.happiness = 50;
        this.needsToilet = false;
        this.isSick = false;
        this.timeCount = 0;
        startTimer();
    }

    public Pet() {
        this.name = getName();
        this.health = 100;
        this.age = 0;
        this.hunger = 50;
        this.thirst = 50;
        this.happiness = 50;
        this.needsToilet = false;
        this.isSick = false;
        this.timeCount = 0;
        startTimer();
    }

    void startTimer() {
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                    decreaseStatsOverTime();
            }
        }, 1000, 60000);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void decreaseStatsOverTime() {
        gainHunger();
        gainThirst();
        loseHappiness();
        agePet();
    }

    public void agePet() {
        timeCount++;
        if (timeCount >= 5) {
            age++;
            timeCount = 0;
        }

        if (age >= 3) {
            System.out.println("Your pet lived to its full potential and died peacefully at the grand old age of " + 5 * age + " minutes");
            System.out.println("Thanks for playing!");
            killPet();
        }
    }

    public void killPet() {
        File fileToDelete = new File(SAVE_FILE_PATH);
        if (fileToDelete.exists()) {
            boolean deleted = fileToDelete.delete();
        }
        System.exit(0);
    }

    public void gainHunger() {
        hunger -= 10;

        if (hunger < 0) {
            hunger = 0;
        }

        if (hunger < 50) {
            loseHappiness();
        } else if (hunger > 50) {
            gainHappiness();
        }

    }

    public void gainThirst() {
        thirst -= 10;

        if (thirst < 0) {
            thirst = 0;
        }

        if (thirst < 50) {
            loseHappiness();
        } else if (thirst > 50) {
            gainHappiness();
        }
    }

    public void loseHappiness() {
        happiness -= 1;

        if (happiness < 0) {
            happiness = 0;
        }
    }

    public void gainHappiness() {
        if (hunger > 50 && thirst > 50) {
            happiness += 10;
        } else {
            happiness += 1;
        }

        if (happiness > 100) {
            happiness = 100;
        }

    }

    public void feed() {
        hunger += 10;

        if (hunger >= 100) {
            hunger = 100;
        }
        gainHappiness();
        System.out.println(name + " is fed. Hunger decreased.");
    }

    public void drink() {
        thirst += 10;

        if (thirst >= 100) {
            thirst = 100;
        }
        gainHappiness();
    }

    public void play() {
        happiness += 10;

        gainHunger();
        gainThirst();

        if (happiness > 100) {
            happiness = 100;
        }
    }

    public void update() {
        hunger += 5;
        happiness -= 5;
        if (hunger > 100) {
            hunger = 100;
        }
        if (happiness < 0) {
            happiness = 0;
        }
        System.out.println(name + "'s status updated.");
    }

    public void showStatus() {
        System.out.println(name + "'s Stats:");
        System.out.println("Health: " + health);
        System.out.println("Age: " + age);
        System.out.println("Hunger: " + hunger);
        System.out.println("Thirst: " + thirst);
        System.out.println("Happiness: " + happiness);
        System.out.println("Sick: " + isSick);
        System.out.println("Needs toilet: " + needsToilet);
        System.out.println("Time Count: " + timeCount);
    }

    @Override
    public String toString() {
        return "Name: " + name + ", Hunger: " + hunger + ", Happiness: " + happiness;
    }

    void saveData() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(SAVE_FILE_PATH))) {
            writer.println(name);
            writer.println(health);
            writer.println(age);
            writer.println(hunger);
            writer.println(thirst);
            writer.println(happiness);
            writer.println(isSick);
            writer.println(needsToilet);
            writer.println(timeCount);
        } catch (IOException e) {
            System.err.println("Error saving pet data: " + e.getMessage());
        }
    }

    void loadData() {
        try (BufferedReader reader = new BufferedReader(new FileReader(SAVE_FILE_PATH))) {
            name = reader.readLine();
            health = Integer.parseInt(reader.readLine());
            age = Integer.parseInt(reader.readLine());
            hunger = Integer.parseInt(reader.readLine());
            thirst = Integer.parseInt(reader.readLine());
            happiness = Integer.parseInt(reader.readLine());
            isSick = Boolean.parseBoolean(reader.readLine());
            needsToilet = Boolean.parseBoolean(reader.readLine());
            timeCount = Integer.parseInt(reader.readLine());
        } catch (IOException | NumberFormatException e) {
            System.err.println("Error loading pet data: " + e.getMessage());
        }
    }

    boolean checkSaveExists() {
        File fileToCheck = new File(SAVE_FILE_PATH);
        return fileToCheck.exists();
    }

}
