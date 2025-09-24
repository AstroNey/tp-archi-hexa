package lni.archi.hexa.hexarest.configs.cleanArchi.usescases.team;

import lni.archi.hexa.core.ports.data.repositories.ITeamRepoPT;
import lni.archi.hexa.core.usescases.team.CreateTeamUE;
import lni.archi.hexa.core.usescases.team.GetAllTeamUE;
import lni.archi.hexa.core.usescases.team.GetTeamByIdUE;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TeamUECfg {

    @Bean
    public CreateTeamUE createTeamUE(ITeamRepoPT teamRepoPT) {
        return new CreateTeamUE(teamRepoPT);
    }

    @Bean
    public GetTeamByIdUE getTeamByIdUE(ITeamRepoPT teamRepoPT) {
        return new GetTeamByIdUE(teamRepoPT);
    }

    @Bean
    public GetAllTeamUE getAllTeamUE(ITeamRepoPT teamRepoPT) {
        return new GetAllTeamUE(teamRepoPT);
    }

    @Bean
    public TeamUseCases TeamUseCases(
            CreateTeamUE createTeamUE, GetAllTeamUE getAllTeamUE,
            GetTeamByIdUE getTeamByIdUE
    ) {
        TeamUseCases teamUseCases = new TeamUseCases();
        teamUseCases.setCreateTeamUE(createTeamUE);
        teamUseCases.setGetAllTeamUE(getAllTeamUE);
        teamUseCases.setGetTeamByIdUE(getTeamByIdUE);
        return teamUseCases;
    }
}
