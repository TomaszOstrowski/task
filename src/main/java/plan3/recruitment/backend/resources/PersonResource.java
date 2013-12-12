package plan3.recruitment.backend.resources;

import com.google.common.base.Optional;
import com.yammer.dropwizard.hibernate.UnitOfWork;
import plan3.recruitment.backend.model.Person;
import plan3.recruitment.backend.storage.PersonStorage;
import plan3.recruitment.backend.validators.InputValidator;

import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.util.Collection;

import static plan3.recruitment.backend.resources.PersonResourceConstants.*;

@Path("person")
@Produces(APPLICATION_JSON_UTF8)
@Consumes(APPLICATION_JSON_UTF8)
public class PersonResource {

    private final PersonStorage personStorage;
    private final InputValidator inputValidator;

    public PersonResource(PersonStorage personStorage) {
        this.personStorage = personStorage;
        this.inputValidator = new InputValidator();
    }

    @GET
    @UnitOfWork
    public Collection<Person> list() {
        return personStorage.list();
    }

    @GET
    @Path(EMAIL_PATH_PARAM)
    @UnitOfWork
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
    public Response save(@Valid final Person person, @Context final UriInfo uri) {
        personStorage.save(person);
        return Response.created(person.provideLocation()).build();
    }
}