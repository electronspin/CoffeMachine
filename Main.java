package machine;

import java.util.Scanner;

public class CoffeeMachine {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Machine machine = new Machine();

        machine.displayIdleMessage();
        while (true) {
            String input = scanner.next();
            if ("exit".equals(input)) {
                break;
            }
            machine.recv(input);
        }
    }
}

enum State {
    CHOOSING_ACTION,
    CHOOSING_VARIANT_COFEE,
    FILLING_WATER,
    FILLING_MILK,
    FILLING_BEANS,
    FILLING_CUPS
}

class Machine {
    private int water = 400;
    private int milk = 540;
    private int beans = 120;
    private int cups = 9;
    private int money = 550;

    private State state = State.CHOOSING_ACTION;

    public void recv(String input) {
        switch (state) {
            case CHOOSING_VARIANT_COFEE:
                buy(input);
                System.out.println();
                displayIdleMessage();
                state = State.CHOOSING_ACTION;
                break;
            case FILLING_WATER:
                water += Integer.parseInt(input);
                System.out.println("Write how many ml of milk do you want to add:");
                state = State.FILLING_MILK;
                break;
            case FILLING_MILK:
                milk += Integer.parseInt(input);
                System.out.println("Write how many grams of coffee beans do you want to add:");
                state = State.FILLING_BEANS;
                break;
            case FILLING_BEANS:
                beans += Integer.parseInt(input);
                System.out.println("Write how many disposable cups of coffee do you want to add:");
                state = State.FILLING_CUPS;
                break;
            case FILLING_CUPS:
                cups += Integer.parseInt(input);
                System.out.println();
                displayIdleMessage();
                state = State.CHOOSING_ACTION;
                break;
            case CHOOSING_ACTION:
                System.out.println();
                switch (input) {
                    case "buy":
                        System.out.println("What do you want to buy? 1 - espresso, 2 - latte, 3 - cappuccino, back - to main menu:");
                        state = State.CHOOSING_VARIANT_COFEE;
                        break;
                    case "fill":
                        System.out.println("Write how many ml of water do you want to add:");
                        state = State.FILLING_WATER;
                        break;
                    case "take":
                        take();
                        System.out.println();
                        displayIdleMessage();
                        state = State.CHOOSING_ACTION;
                        break;
                    case "remaining":
                        remaining();
                        System.out.println();
                        displayIdleMessage();
                        break;
                    default:
                        displayIdleMessage();
                        break;
                }
        }
    }

    public void displayIdleMessage() {
        System.out.println("Write action (buy, fill, take, remaining, exit):");
    }

    private void remaining() {
        System.out.println("The coffee machine has:");
        System.out.println(water + " of water");
        System.out.println(milk + " of milk");
        System.out.println(beans + " of coffee beans");
        System.out.println(cups + " of disposable cups");
        System.out.println(money + " of money");
    }

    private boolean isAvailable(String kind) {
        int water1 = water;
        int milk1 = milk;
        int beans1 = beans;
        int cups1 = cups;

        switch (kind) {
            case "1":
                water1 -= 250;
                beans1 -= 16;
                cups1--;
                break;
            case "2":
                water1 -= 350;
                milk1 -= 75;
                beans1 -= 20;
                cups1--;
                break;
            case "3":
                water1 -= 200;
                milk1 -= 100;
                beans1 -= 12;
                cups1--;
                break;
        }

        return water1 >= 0 && milk1 >= 0 && beans >= 0 && cups >= 0;
    }

    private void buy(String kind) {
        if ("back".equals(kind)) {
            return;
        }

        if (!isAvailable(kind)) {
            System.out.println("Sorry, not enough water!");
            return;
        }

        System.out.println("I have enough resources, making you a coffee!");
        switch (kind) {
            case "1":
                water -= 250;
                beans -= 16;
                cups--;
                money += 4;
                break;
            case "2":
                water -= 350;
                milk -= 75;
                beans -= 20;
                cups--;
                money += 7;
                break;
            case "3":
                water -= 200;
                milk -= 100;
                beans -= 12;
                cups--;
                money += 6;
                break;
        }
    }

    private void take() {
        System.out.println("I gave you $" + money);
        money = 0;
    }
}
