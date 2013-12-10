package plan3.recruitment.backend.resources;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import javax.ws.rs.core.Response.Status;

import org.junit.Ignore;
import org.junit.Test;

import plan3.recruitment.backend.model.Person;

import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.GenericType;
import com.sun.jersey.api.client.WebResource;
import com.yammer.dropwizard.testing.ResourceTest;

public class PersonsResourceTest extends ResourceTest {

    @Override
    protected void setUpResources() throws Exception {
        addResource(new PersonResource());
    }

    @Ignore
    @Test
    public void emptyList() {
        final WebResource person = client().resource("/person");
        final List<Person> emptyList = person.get(new GenericType<List<Person>>() {
        });
        assertTrue(emptyList.isEmpty());
    }

    @Ignore
    @Test
    public void listShouldBeSortedOnLastname() {
        final WebResource client = client().resource("/person");
        final Person stefan = Person.valueOf("Stefan", "Petersson", "stefan@plan3.se");
        final Person markus = Person.valueOf("Markus", "Gustavsson", "markus@plan3.se");
        final Person ian = Person.valueOf("Ian", "Vännman", "ian@plan3.se");
        final Person marten = Person.valueOf("Mårten", "Gustafson", "marten@plan3.se");
        for (final Person person : Arrays.asList(marten, ian, stefan, markus)) {
            client.type(PersonResource.APPLICATION_JSON_UTF8).put(ClientResponse.class, person);
        }
        final List<Person> persons = client.get(new GenericType<List<Person>>() {
        });
        assertEquals(4, persons.size());
        final Iterator<Person> iterator = persons.iterator();
        assertEquals(marten, iterator.next());
        assertEquals(markus, iterator.next());
        assertEquals(stefan, iterator.next());
        assertEquals(ian, iterator.next());
    }

    @Ignore
    @Test
    public void fetchNonExistingReturns404() {
        final ClientResponse response = client()
                .resource("/person/a-non-existant-email-address-thats-not-even-valid")
                .get(ClientResponse.class);
        assertSame(Status.NOT_FOUND.getStatusCode(), response.getStatus());
    }

    @Ignore
    @Test
    public void saveAndFetch() {
        // Save one person
        final Person person = Person.valueOf("Mårten", "Gustafson", "marten@plan3.se");
        final ClientResponse response = client()
                .resource("/person")
                .type(PersonResource.APPLICATION_JSON_UTF8)
                .put(ClientResponse.class, person);
        assertSame(Status.CREATED.getStatusCode(), response.getStatus());
        // Fetch the saved person based on the URI in the response
        final Person fetched = client().resource(response.getLocation()).get(Person.class);
        assertEquals(person, fetched);
    }
}