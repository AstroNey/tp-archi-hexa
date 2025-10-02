package lni.archi.hexa.menu;

import lni.archi.hexa.cleanArchi.usescases.team.TeamUseCases;
import lni.archi.hexa.core.domain.PersonDN;
import lni.archi.hexa.core.domain.TeamDN;
import lni.archi.hexa.core.enums.exception.techException.TeamErrorMessage;
import lni.archi.hexa.core.exceptions.job.JobException;
import lni.archi.hexa.core.exceptions.tech.TechException;
import lni.archi.hexa.core.model.TeamML;
import lni.archi.hexa.core.model.TeamMLF;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static java.lang.Thread.sleep;

public class TeamMenu {

    private final TeamUseCases teamUseCases;

    public TeamMenu(TeamUseCases teamUseCases) {
        this.teamUseCases = teamUseCases;
    }

    public void menu(Scanner sc) throws InterruptedException {
        MenuUtils.clearScreen();
        System.out.println("================Team Menu================");
        System.out.print("1 - Create a team\n");
        System.out.print("2 - Get a team by id\n");
        System.out.print("3 - Get all teams\n");
        System.out.print("===========================================\n");

        switch (sc.nextInt()) {
            case 1 -> createTeam(sc);
            case 2 -> getTeamById(sc);
            case 3 -> getAllTeams(sc);
            default -> {
                System.out.println("Invalid choice, please try again.\n");
                menu(sc);
            }
        }
    }

    private void createTeam(Scanner sc) throws InterruptedException {
        System.out.print("Enter the team's name: ");
        String teamName = sc.next();
        List<PersonDN> members = addMembersForATeam(sc);
        try {
            TeamDN result = this.teamUseCases.getCreateTeamUE().execute(teamName, members);
            TeamMLF resultMLF = new TeamMLF(result);
            MenuUtils.clearScreen();
            MenuUtils.printTeam(resultMLF);
            sleep(2000);
            MenuUtils.clearScreen();
        } catch (JobException e) {
            System.out.println(e.getMessage());
            sleep(2000);
            menu(sc);
        } catch (Exception e) {
            throw new TechException("Not managed exception.", TeamErrorMessage.NOT_MANAGED_EXCEPTION);
        }
    }

    private List<PersonDN> addMembersForATeam(Scanner sc) {
        int count = 0;
        List<PersonDN> members = new ArrayList<>();

        System.out.print("Now let's add members to the team.\n");
        System.out.print("A team have between 6 and 15 persons.\n");
        System.out.println();
        while (true) {
            System.out.println();
            if (count >= 6) {
                System.out.println("Do you want to add another member ? (y/n)");
                char choice = sc.next().charAt(0);
                if (choice == 'n' || choice == 'N') {
                    break;
                }
            }
            System.out.print("Enter the person's id: ");
            int id = sc.nextInt();
            System.out.print("Enter the person's name: ");
            String personName = sc.next();
            System.out.print("Enter the person's lastName: ");
            String lastName = sc.next();
            System.out.print("Enter the person's age: ");
            int age = sc.nextInt();
            members.add(new PersonDN(id, personName, lastName, age));
            count++;
        }

        return members;
    }

    private void getTeamById(Scanner sc) throws InterruptedException {
        System.out.print("Enter the team's id: ");
        int id = sc.nextInt();
        try {
            TeamDN result = this.teamUseCases.getGetTeamByIdUE().execute(id);
            TeamMLF resultML = new TeamMLF(result);
            MenuUtils.clearScreen();
            MenuUtils.printTeam(resultML);
            sleep(2000);
            MenuUtils.clearScreen();
        } catch (JobException e) {
            System.out.println(e.getMessage());
            sleep(2000);
            menu(sc);
        } catch (Exception e) {
            throw new TechException("Not managed exception.", TeamErrorMessage.NOT_MANAGED_EXCEPTION);
        }
    }

    private void getAllTeams(Scanner sc) throws InterruptedException {
        try {
            List<TeamDN> result = this.teamUseCases.getGetAllTeamUE().execute();
            MenuUtils.clearScreen();
            for (TeamDN teamDN : result) {
                MenuUtils.printTeam(new TeamML(teamDN));
            }
            sleep(2000);
            MenuUtils.clearScreen();
        } catch (JobException e) {
            System.out.println(e.getMessage());
            sleep(2000);
            menu(sc);
        } catch (Exception e) {
            throw new TechException("Not managed exception.", TeamErrorMessage.NOT_MANAGED_EXCEPTION);
        }
    }
}
