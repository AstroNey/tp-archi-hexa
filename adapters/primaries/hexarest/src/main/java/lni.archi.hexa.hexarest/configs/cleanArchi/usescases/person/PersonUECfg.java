package lni.archi.hexa.hexarest.configs.cleanArchi.usescases.person;

import lni.archi.hexa.core.ports.data.repositories.IPersonRepoPT;
import lni.archi.hexa.core.usescases.person.CreatePersonUE;
import lni.archi.hexa.core.usescases.person.GetAllPersonUE;
import lni.archi.hexa.core.usescases.person.GetPersonByIdUE;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PersonUECfg {

    @Bean
    public CreatePersonUE createPersonUE(IPersonRepoPT personRepoPT) {
        return new CreatePersonUE(personRepoPT);
    }

    @Bean
    public GetPersonByIdUE getPersonByIdUE(IPersonRepoPT personRepoPT) {
        return new GetPersonByIdUE(personRepoPT);
    }

    @Bean
    public GetAllPersonUE getAllPersonUE(IPersonRepoPT personRepoPT) {
        return new GetAllPersonUE(personRepoPT);
    }

    @Bean
    public PersonUseCases personUseCases(
            CreatePersonUE createPersonUE, GetAllPersonUE getAllPersonUE,
            GetPersonByIdUE getPersonByIdUE
    ) {
        PersonUseCases personUseCases = new PersonUseCases();
        personUseCases.setCreatePersonUE(createPersonUE);
        personUseCases.setGetAllPersonUE(getAllPersonUE);
        personUseCases.setGetPersonByIdUE(getPersonByIdUE);
        return personUseCases;
    }
}
