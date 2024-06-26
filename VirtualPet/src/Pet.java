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
    private Timer timer;
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
    }

    public void startTimer() {
        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                update();
            }
        }, 0, 60000);
    }

    public void stopTimer() {
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth() {
        if(health < 0) {
            health = 0;
        }

        if(health == 0) {
            killPet();
        }

        if(hunger == 0 && thirst == 0) {
            health -= 30;
            happiness = 0;
            System.out.println("Pet is starving...");
        }

        if(hunger >= 50 && thirst >= 50){
            health += 10;
        }
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getHunger() {
        return hunger;
    }

    public void setHunger(int hunger) {
        this.hunger = hunger;
    }

    public int getThirst() {
        return thirst;
    }

    public void setThirst(int thirst) {
        this.thirst = thirst;
    }

    public int getHappiness() {
        return happiness;
    }

    public void setHappiness(int happiness) {
        this.happiness = happiness;
    }

    public boolean isNeedsToilet() {
        return needsToilet;
    }

    public void setNeedsToilet(boolean needsToilet) {
        this.needsToilet = needsToilet;
    }

    public boolean isSick() {
        return isSick;
    }

    public void setSick(boolean sick) {
        isSick = sick;
    }

    public Timer getTimer() {
        return timer;
    }

    public void setTimer(Timer timer) {
        this.timer = timer;
    }

    public int getTimeCount() {
        return timeCount;
    }

    public void setTimeCount(int timeCount) {
        this.timeCount = timeCount;
    }

    public void update() {
        // A clause to drastically reduce happiness.
        if(hunger < 50 && thirst < 50){
            happiness -=  10;
        }

        setHealth();
        gainHunger();
        gainThirst();
        loseHappiness();
        agePet();
        System.out.println(name + "'s status updated.");
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
        System.out.println("Pet DEAD! You are a terrible person!");
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

    public void showStatus() {
        System.out.println(name + "'s Stats:");
        System.out.println("Health: " + getHealth());
        System.out.println("Age: " + getAge());
        System.out.println("Hunger: " + getHunger());
        System.out.println("Thirst: " + getThirst());
        System.out.println("Happiness: " + getHappiness());
        System.out.println("Sick: " + isSick());
        System.out.println("Needs toilet: " + isNeedsToilet());
        System.out.println("Time Count: " + getTimeCount());
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
