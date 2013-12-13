package plan3.recruitment.backend;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;

import static com.yammer.dropwizard.testing.JsonHelpers.*;
import static org.junit.Assert.assertEquals;

public abstract class AbstractJsonRoundTripTest<T> {

    private T instance;
    private String fixtureName;
    private static final ObjectMapper mapper = new ObjectMapper(new JsonFactory());

    @Before
    public final void init() {
        instance = createInstance();
        fixtureName = instance.getClass().getSimpleName().toLowerCase();
    }

    @Test
    @SuppressWarnings("unchecked")
    public final void jsonRoundTripTest() throws Exception {
        final String clazz = instance.getClass().getName();
        final String fixture = jsonFixture("fixtures/" + fixtureName + ".json");

        final JsonNode expected = mapper.readTree(fixture);
        final JsonNode actual = mapper.readTree(asJson(instance));
        assertEquals(clazz + " has unexpected json representation after conversion\n", expected, actual);

        T deserialized = getDeserialized(fixture);

        assertEquals(clazz + " has bad equals() method,", instance, deserialized);
        assertEquals(clazz + " has bad hashCode() method,", instance.hashCode(), deserialized.hashCode());
    }

    private T getDeserialized(String fixture) throws Exception {
        T deserialized;
        try {
            deserialized = fromJson(fixture, (Class<T>) instance.getClass());
        } catch (final Exception e) {
            throw new RuntimeException("Can't deserialize:\n" + asJson(instance), e);
        }
        return deserialized;
    }

    public abstract T createInstance();
}