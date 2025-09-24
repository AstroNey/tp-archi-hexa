package lni.archi.hexa.cleanArchi.menu;

import lni.archi.hexa.menu.PersonMenu;
import lni.archi.hexa.menu.TeamMenu;
import lni.archi.hexa.cleanArchi.usescases.person.PersonUseCases;
import lni.archi.hexa.cleanArchi.usescases.team.TeamUseCases;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MenuConfig {

    @Bean
    public PersonMenu personMenu(PersonUseCases personUseCases) {
        return new PersonMenu(personUseCases);
    }

    @Bean
    public TeamMenu teamMenu(TeamUseCases teamUseCases) {
        return new TeamMenu(teamUseCases);
    }
}
