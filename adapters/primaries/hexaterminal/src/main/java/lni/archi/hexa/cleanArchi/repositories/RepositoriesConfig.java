package lni.archi.hexa.cleanArchi.repositories;

import lni.archi.hexa.core.ports.data.repositories.IPersonRepoPT;
import lni.archi.hexa.core.ports.data.repositories.ITeamRepoPT;
import lni.archi.hexa.data.jpa.repositories.PersonJpaRepo;
import lni.archi.hexa.data.jpa.repositories.TeamJpaRepo;
import lni.archi.hexa.data.mongo.repositories.PersonMongoRepo;
import lni.archi.hexa.data.mongo.repositories.TeamMongoRepo;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RepositoriesConfig {

    @Bean
    public IPersonRepoPT personRepoPT() {
        //return new PersonMongoRepo();
        return new PersonJpaRepo();
    }

    @Bean
    public ITeamRepoPT teamRepoPT() {
        //return new TeamMongoRepo();
        return new TeamJpaRepo();
    }
}
