package plan3.recruitment.backend.resources;

import com.google.common.base.Optional;
import com.google.common.collect.Lists;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.GenericType;
import com.sun.jersey.api.client.WebResource;
import com.yammer.dropwizard.testing.ResourceTest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import plan3.recruitment.backend.model.Person;
import plan3.recruitment.backend.storage.PersonStorage;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static javax.ws.rs.core.Response.Status.*;
import static org.fest.assertions.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;
import static plan3.recruitment.backend.resources.PersonResource.*;

@RunWith(MockitoJUnitRunner.class)
public class PersonsResourceTest extends ResourceTest {

    @Mock
    PersonStorage personStorage_mock;

    @Override
    protected void setUpResources() throws Exception {
        addResource(new PersonResource(personStorage_mock));
    }

    @Test
    public void shouldReturn200ifResourceFound() {
        // given
        final WebResource person = client().resource("/person");

        // when
        final ClientResponse response = person.get(ClientResponse.class);

        // then
        assertThat(response.getStatus()).isEqualTo(OK.getStatusCode());
    }

    @Test
    public void shouldReturnEmptyListIfNoEntitiesStored() {
        // given
        when(personStorage_mock.list()).thenReturn(Collections.<Person>emptyList());
        final WebResource person = client().resource("/person");

        // when
        final List<Person> emptyList = person.get(new GenericType<List<Person>>() {
        });

        // then
        assertTrue(emptyList.isEmpty());
    }

    @Test
    public void shouldBeSortedOnLastname() {
        // given
        final WebResource client = client().resource("/person");
        final Person stefan = Person.valueOf("Stefan", "Petersson", "stefan@plan3.se");
        final Person markus = Person.valueOf("Markus", "Gustavsson", "markus@plan3.se");
        final Person ian = Person.valueOf("Ian", "Vännman", "ian@plan3.se");
        final Person marten = Person.valueOf("Mårten", "Gustafson", "marten@plan3.se");
        for (final Person person : Arrays.asList(marten, ian, stefan, markus)) {
            client.type(APPLICATION_JSON_UTF8).put(ClientResponse.class, person);
        }
        when(personStorage_mock.list()).thenReturn(Lists.newArrayList(marten, markus, stefan, ian));

        // when
        final List<Person> persons = client.get(new GenericType<List<Person>>() { });

        // then
        verify(personStorage_mock).save(stefan);
        verify(personStorage_mock).save(markus);
        verify(personStorage_mock).save(ian);
        verify(personStorage_mock).save(marten);
        assertThat(persons).isNotNull().hasSize(4).containsSequence(marten, markus, stefan, ian);
    }


    @Test
    public void shouldReturn200ifFetchByValidEmail() {
        // given
        final String existingEmail = "quick@brown.fox";
        final WebResource person = client().resource("/person/" + existingEmail);
        final Person quickFox = Person.valueOf("Quick", "Fox", existingEmail);
        when(personStorage_mock.fetch(existingEmail)).thenReturn(Optional.of(quickFox));

        // when
        final ClientResponse response = person.get(ClientResponse.class);

        // then
        assertThat(response.getStatus()).isEqualTo(OK.getStatusCode());
    }

    @Test
    public void shouldReturn400ifFetchByNotValidEmail() {
        // given
        final String notValidEmail = "Latest-survey-shows-that-3-out-of-4-people-make-up-75-percent-of-the-world's-population";
        final WebResource client = client().resource("/person/" + notValidEmail);

        // when
        final ClientResponse response = client.get(ClientResponse.class);

        // then
        assertThat(response.getStatus()).isEqualTo(BAD_REQUEST.getStatusCode());
        verifyZeroInteractions(personStorage_mock);
    }

    @Test
    public void shouldReturn404ifFetchByValidEmailThatDoesNotExist() {
        // given
        final String validEmail = "quick@brown.fox";
        final WebResource client = client().resource("/person/" + validEmail);
        when(personStorage_mock.fetch(validEmail)).thenReturn(Optional.<Person>absent());

        // when
        final ClientResponse response = client.get(ClientResponse.class);

        // then
        assertThat(response.getStatus()).isEqualTo(NOT_FOUND.getStatusCode());
        verify(personStorage_mock).fetch(validEmail);
    }

    @Test
    public void shouldReturnSinglePersonForEmail() {
        // given
        final String existingEmail = "quick@brown.fox";
        final WebResource person = client().resource("/person/" + existingEmail);
        final Person quickFox = Person.valueOf("Quick", "Fox", existingEmail);
        when(personStorage_mock.fetch(existingEmail)).thenReturn(Optional.of(quickFox));

        // when
        final Person result = person.get(Person.class);

        // then
        assertThat(result).isNotNull().isEqualTo(quickFox);
    }


    @Test
    public void shouldSaveValidPerson() {
        // given
        final Person person = Person.valueOf("Quick", "Fox", "quick@brown.fox");
        final WebResource client = client().resource("/person");

        // when
        client.type(APPLICATION_JSON_UTF8).put(ClientResponse.class, person);

        // then
        verify(personStorage_mock).save(person);
    }

    @Test
    public void shouldReturn201forSuccessfulSave() {
        // given
        final Person person = Person.valueOf("Quick", "Fox", "quick@brown.fox");
        final WebResource client = client().resource("/person");

        // when
        ClientResponse response = client.type(APPLICATION_JSON_UTF8).put(ClientResponse.class, person);

        // then
        assertThat(response.getStatus()).isEqualTo(CREATED.getStatusCode());
    }

    @Test
    public void shouldBeAbleToFetchPersonWithReturnedLocationHeaderUri() {
        // given
        final Person person = Person.valueOf("Mårten", "Gustafson", "marten@plan3.se");
        final WebResource client = client().resource("/person");
        ClientResponse saveResult = client.type(APPLICATION_JSON_UTF8).put(ClientResponse.class, person);
        when(personStorage_mock.fetch("marten@plan3.se")).thenReturn(Optional.of(person));

        // when
        final Person fetched = client().resource(saveResult.getLocation()).get(Person.class);

        // then
        verify(personStorage_mock).save(person);
        verify(personStorage_mock).fetch("marten@plan3.se");
        assertEquals(person, fetched);
    }
}