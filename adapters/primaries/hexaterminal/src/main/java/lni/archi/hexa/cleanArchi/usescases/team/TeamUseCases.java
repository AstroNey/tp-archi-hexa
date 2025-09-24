package lni.archi.hexa.cleanArchi.usescases.team;

import lni.archi.hexa.core.usescases.team.CreateTeamUE;
import lni.archi.hexa.core.usescases.team.GetAllTeamUE;
import lni.archi.hexa.core.usescases.team.GetTeamByIdUE;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TeamUseCases {

    CreateTeamUE createTeamUE;

    GetAllTeamUE getAllTeamUE;

    GetTeamByIdUE getTeamByIdUE;
}
