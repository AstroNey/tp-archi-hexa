package lni.archi.hexa.core.ports.data.repositories;

import lni.archi.hexa.core.domain.PersonDN;

import java.util.List;

public interface IPersonRepoPT {

    PersonDN createPerson(String name, String firstname, int age);

    PersonDN getPersonById(Integer id);

    List<PersonDN> getAllPerson();

}
