package plan3.recruitment.backend.resources;

import com.google.common.base.Optional;
import com.yammer.dropwizard.hibernate.UnitOfWork;
import plan3.recruitment.backend.model.Person;
import plan3.recruitment.backend.model.PersonStorage;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.util.Collection;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

@Path("person")
@Produces(PersonResource.APPLICATION_JSON_UTF8)
@Consumes(PersonResource.APPLICATION_JSON_UTF8)
public class PersonResource {

    public static final String APPLICATION_JSON_UTF8 = APPLICATION_JSON + "; charset=utf-8";
    private static final String EMAIL_PARAM = "email";
    private static final String EMAIL_PATH_PARAM = '{' + EMAIL_PARAM + '}';
    private PersonStorage personStorage;

    public PersonResource(PersonStorage personStorage) {
        this.personStorage = personStorage;
    }

    @GET
    @UnitOfWork
    public Collection<Person> list() {
        return this.personStorage.list();
    }

    @GET
    @Path(EMAIL_PATH_PARAM)
    @UnitOfWork
    public Person fetch(@PathParam(EMAIL_PARAM) final String email) {
        Optional<Person> optionalPerson = this.personStorage.fetch(email);
        return optionalPerson.or(new Person("xxx","xxx","xxx"));
    }

    @PUT
    @UnitOfWork
    public Response save(final Person person, @Context final UriInfo uri) {
        this.personStorage.save(person);
        return Response.ok().build();
    }
}