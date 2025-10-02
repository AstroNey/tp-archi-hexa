package lni.archi.hexa.menu;

import lni.archi.hexa.cleanArchi.usescases.person.PersonUseCases;
import lni.archi.hexa.core.domain.PersonDN;
import lni.archi.hexa.core.enums.exception.techException.PersonErrorMessage;
import lni.archi.hexa.core.exceptions.job.JobException;
import lni.archi.hexa.core.exceptions.tech.TechException;
import lni.archi.hexa.core.model.PersonML;

import java.util.List;
import java.util.Scanner;

import static java.lang.Thread.sleep;

public class PersonMenu {

    private final PersonUseCases personUseCases;

    public PersonMenu(PersonUseCases personUseCases) {
        this.personUseCases = personUseCases;
    }

    public void menu(Scanner sc) {
        MenuUtils.clearScreen();
        System.out.println("================Person Menu================");
        System.out.print("1 - Create a person\n");
        System.out.print("2 - Get a person by id\n");
        System.out.print("3 - Get all persons\n");
        System.out.print("===========================================\n");

        switch (sc.nextInt()) {
            case 1 -> createPerson(sc);
            case 2 -> getPersonById(sc);
            case 3 -> getAllPersons();
            default -> {
                System.out.println("Invalid choice, please try again.\n");
                menu(sc);
            }
        }
    }

    private void createPerson(Scanner sc) {
        System.out.print("Enter the person's name: ");
        String name = sc.next();
        System.out.print("Enter the person's lastName: ");
        String lastName = sc.next();
        System.out.print("Enter the person's age: ");
        int age = sc.nextInt();

        try {
            PersonDN result = this.personUseCases.getCreatePersonUE().execute(name, lastName, age);
            PersonML resultML = new PersonML(result);
            MenuUtils.clearScreen();
            MenuUtils.printPerson(resultML);
            sleep(2000);
            MenuUtils.clearScreen();
        }  catch (JobException e) {
            throw e; // TODO manage exception in menu
        } catch (Exception e) {
            throw new TechException("Not managed exception.", PersonErrorMessage.NOT_MANAGED_EXCEPTION);
        }
    }

    private void getPersonById(Scanner sc) {
        System.out.print("Enter the person's id: ");
        int id = sc.nextInt();
        try {
            PersonDN result = this.personUseCases.getGetPersonByIdUE().execute(id);
            PersonML resultML = new PersonML(result);
            MenuUtils.clearScreen();
            MenuUtils.printPerson(resultML);
            sleep(2000);
            MenuUtils.clearScreen();
        } catch (JobException e) {
            throw e; // TODO manage exception in menu
        } catch (Exception e) {
            throw new TechException("Not managed exception.", PersonErrorMessage.NOT_MANAGED_EXCEPTION);
        }
    }

    private void getAllPersons() {
        try {
            List<PersonDN> result = this.personUseCases.getGetAllPersonUE().execute();
            MenuUtils.clearScreen();
            for (PersonDN person : result) {
                MenuUtils.printPerson(new PersonML(person));
            }
            sleep(2000);
            MenuUtils.clearScreen();
        }  catch (JobException e) {
            throw e; // TODO manage exception in menu
        } catch (Exception e) {
            throw new TechException("Not managed exception.", PersonErrorMessage.NOT_MANAGED_EXCEPTION);
        }
    }
}
