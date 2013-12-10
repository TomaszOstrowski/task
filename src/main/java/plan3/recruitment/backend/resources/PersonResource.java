package plan3.recruitment.backend.resources;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

import java.util.Collection;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import plan3.recruitment.backend.model.Person;
import plan3.recruitment.backend.model.PersonStorage;

@Path("person")
@Produces(PersonResource.APPLICATION_JSON_UTF8)
@Consumes(PersonResource.APPLICATION_JSON_UTF8)
public class PersonResource {

    public static final String APPLICATION_JSON_UTF8 = APPLICATION_JSON + "; charset=utf-8";
    private static final String EMAIL_PARAM = "email";
    private static final String EMAIL_PATH_PARAM = '{' + EMAIL_PARAM + '}';
    private final PersonStorage storage = null;

    @GET
    public Collection<Person> list() {
        return this.storage.list();
    }

    @GET
    @Path(EMAIL_PATH_PARAM)
    public Person fetch(@PathParam(EMAIL_PARAM) final String email) {
        return null;
    }

    // Creating an Order, Customer, or Product, str 21 w Restful java
    @PUT
    public Response save(final Person person, @Context final UriInfo uri) {
        this.storage.save(person);
        return Response.ok().build();
    }
}