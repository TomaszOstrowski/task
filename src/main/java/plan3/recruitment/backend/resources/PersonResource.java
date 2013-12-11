package plan3.recruitment.backend.resources;

import com.google.common.base.Optional;
import com.yammer.dropwizard.hibernate.UnitOfWork;
import org.hibernate.validator.constraints.Email;
import plan3.recruitment.backend.model.Person;
import plan3.recruitment.backend.storage.PersonStorage;

import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.util.Collection;

import static plan3.recruitment.backend.resources.PersonConstants.*;

@Path("person")
@Produces(APPLICATION_JSON_UTF8)
@Consumes(APPLICATION_JSON_UTF8)
public class PersonResource {

    private PersonStorage personStorage;

    public PersonResource(PersonStorage personStorage) {
        this.personStorage = personStorage;
    }

    @GET
    @UnitOfWork
    public Collection<Person> list() {
        return personStorage.list();
    }

    @GET
    @Path(EMAIL_PATH_PARAM)
    @UnitOfWork
    public Person fetch(@PathParam(EMAIL_PARAM) @Email @Valid final String email) {
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
        return Response.created(person.provideLocation(uri)).build();
    }
}