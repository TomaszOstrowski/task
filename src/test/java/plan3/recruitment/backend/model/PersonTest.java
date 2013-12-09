package plan3.recruitment.backend.model;

import plan3.recruitment.backend.AbstractJsonRoundTripTest;

public class PersonTest extends AbstractJsonRoundTripTest<Person> {
    @Override
    public Person createInstance() {
        return Person.valueOf("first", "last", "first@last.se");
    }
}
