package lni.archi.hexa;

import lni.archi.hexa.core.enums.exception.techException.PersonErrorMessage;
import lni.archi.hexa.core.exceptions.tech.TechException;
import lni.archi.hexa.menu.PersonMenu;
import lni.archi.hexa.menu.TeamMenu;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Scanner;

@SpringBootApplication
public class Main implements CommandLineRunner {

    private static Scanner scan = new Scanner(System.in);

    @Autowired
    PersonMenu personMenu;

    @Autowired
    TeamMenu TeamMenu;

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }

    @Override
    public void run(String... args) throws InterruptedException {
        System.out.println();
        System.out.println("---------------Welcome to HexaTerminal!---------------");
        firstMenuPart();
    }

    private void firstMenuPart() {
        do {
            System.out.print("=====================================================\n");
            System.out.print("In this menu, you can choose, what you want to do :\n");
            System.out.print("1 - Person management\n");
            System.out.print("2 - Team management\n");
            System.out.print("=====================================================\n");
            int choice = scan.nextInt();

            try {
                switch (choice) {
                    case 1 -> personMenu.menu(scan);
                    case 2 -> TeamMenu.menu(scan);
                    default -> {
                        System.out.println("Invalid choice, please try again.\n");
                        System.out.println("=================================\n");
                        firstMenuPart();
                    }
                }
            } catch (TechException e) {
                System.out.println("Error: " + e.getMessage() + "\n");
                System.out.println("Please try again.\n");
                firstMenuPart();
            } catch (InterruptedException e) {
                throw new TechException("Not managed exception.", PersonErrorMessage.NOT_MANAGED_EXCEPTION);
            }

        } while (true);
    }
}