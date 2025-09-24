package lni.archi.hexa.cleanArchi.usescases.person;

import lni.archi.hexa.core.usescases.person.CreatePersonUE;
import lni.archi.hexa.core.usescases.person.GetAllPersonUE;
import lni.archi.hexa.core.usescases.person.GetPersonByIdUE;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PersonUseCases {

    CreatePersonUE createPersonUE;

    GetAllPersonUE getAllPersonUE;

    GetPersonByIdUE getPersonByIdUE;
}
