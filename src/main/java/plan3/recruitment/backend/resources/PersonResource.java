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

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

@Path("person")
@Produces(PersonResource.APPLICATION_JSON_UTF8)
@Consumes(PersonResource.APPLICATION_JSON_UTF8)
public class PersonResource {
    static final String APPLICATION_JSON_UTF8 = APPLICATION_JSON + "; charset=utf-8";
    static final String EMAIL_PARAM = "email";
    static final String EMAIL_PATH_PARAM = '{' + EMAIL_PARAM + '}';

    private final PersonStorage personStorage;
    private final InputValidator inputValidator;

    public PersonResource(PersonStorage personStorage) {
        this.personStorage = personStorage;
        this.inputValidator = new InputValidator();
    }

    @GET
    @UnitOfWork
    @Timed
    public Collection<Person> list() {
        return personStorage.list();
    }

    @GET
    @Path(EMAIL_PATH_PARAM)
    @UnitOfWork
    @Timed
    public Person fetch(@PathParam(EMAIL_PARAM) final String email) {
        inputValidator.validateEmail(email);
        Optional<Person> optionalPerson = personStorage.fetch(email);
        if (optionalPerson.isPresent()) {
            return optionalPerson.get();
        }
        throw new WebApplicationException(Response.Status.NOT_FOUND);
    }

    @PUT
    @UnitOfWork
    @Timed
    public Response save(@Valid final Person person) {
        personStorage.save(person);
        return Response.created(person.provideLocation()).build();
    }
}