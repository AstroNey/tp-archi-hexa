package lni.archi.hexa.menu;

import lni.archi.hexa.core.model.PersonML;
import lni.archi.hexa.core.model.TeamML;
import lni.archi.hexa.core.model.TeamMLF;

public class MenuUtils {

    public static void printPerson(PersonML personML) {
        System.out.println("=================Person n°" + personML.getId() +"===============");
        System.out.println("Id: " + personML.getId());
        System.out.println("Name: " + personML.getName());
        System.out.println("FirstName: " + personML.getFirstName());
        System.out.println("Age: " + personML.getAge());
    }

    public static void printTeam(TeamML teamML) {
        System.out.println("=================Team n°" + teamML.getId() +"===============");
        System.out.println("Id: " + teamML.getId());
        System.out.println("Name: " + teamML.getName());
        if (teamML instanceof TeamMLF teamMlf)  {
            System.out.println();
            System.out.println("================Members of the team===============");
            for (PersonML personML : teamMlf.getPersons()) {
                printPerson(personML);
            }
        }
    }

    public static void clearScreen() {
        for (int i = 0; i < 50; ++i) System.out.println();
    }
}
