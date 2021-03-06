package plan3.recruitment.backend.resources;

import com.google.common.base.Optional;
import com.yammer.dropwizard.hibernate.UnitOfWork;
import com.yammer.metrics.annotation.Timed;
import plan3.recruitment.backend.model.Person;
import plan3.recruitment.backend.storage.PersonStorage;
import plan3.recruitment.backend.validators.InputValidator;

import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.Collection;

import static com.google.common.base.Preconditions.checkNotNull;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

@Path("person")
@Produces(PersonResource.APPLICATION_JSON_UTF8)
@Consumes(PersonResource.APPLICATION_JSON_UTF8)
public class PersonResource {
    static final String APPLICATION_JSON_UTF8 = APPLICATION_JSON + "; charset=utf-8";
    private static final String EMAIL_PARAM = "email";
    private static final String EMAIL_PATH_PARAM = '{' + EMAIL_PARAM + '}';

    private final PersonStorage personStorage;
    private final InputValidator inputValidator;

    public PersonResource(PersonStorage personStorage) {
        this.personStorage = checkNotNull(personStorage);
        this.inputValidator = new InputValidator();
    }

    @GET
    @Timed
    @UnitOfWork(readOnly=true)
    public Collection<Person> list() {
        return personStorage.list();
    }

    @GET
    @Path(EMAIL_PATH_PARAM)
    @Timed
    @UnitOfWork(readOnly=true)
    public Person fetch(@PathParam(EMAIL_PARAM) final String email) {
        checkIfValid(email);
        Optional<Person> optionalPerson = personStorage.fetch(email);
        if (optionalPerson.isPresent()) {
            return optionalPerson.get();
        }
        throw new WebApplicationException(Response.Status.NOT_FOUND);
    }

    private void checkIfValid(String email) {
        if (!inputValidator.isEmailValid(email)) {
            throw new WebApplicationException(Response.Status.BAD_REQUEST);
        }
    }

    @PUT
    @UnitOfWork
    @Timed
    public Response save(@Valid final Person person) {
        personStorage.save(person);
        return Response.created(person.provideLocation()).build();
    }
}