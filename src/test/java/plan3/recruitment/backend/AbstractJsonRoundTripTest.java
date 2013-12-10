package plan3.recruitment.backend;

import static com.yammer.dropwizard.testing.JsonHelpers.asJson;
import static com.yammer.dropwizard.testing.JsonHelpers.fromJson;
import static com.yammer.dropwizard.testing.JsonHelpers.jsonFixture;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public abstract class AbstractJsonRoundTripTest<T> {

    private T instance;
    private String fixtureName;
    private static final ObjectMapper mapper = new ObjectMapper(new JsonFactory());

    @Before
    public final void init() {
        this.instance = createInstance();
        this.fixtureName = this.instance.getClass().getSimpleName().toLowerCase();
    }

    @Test
    @SuppressWarnings("unchecked")
    public final void jsonRoundTripTest() throws Exception {
        final String clazz = this.instance.getClass().getName();
        final String fixture = jsonFixture("fixtures/" + this.fixtureName + ".json");
        final JsonNode expected = mapper.readTree(fixture);
        final JsonNode actual = mapper.readTree(asJson(this.instance));
        assertTrue(clazz + " bad json,", expected.equals(actual));
        T deserialized;
        try {
            deserialized = fromJson(fixture, (Class<T>) this.instance.getClass());
        } catch (final Exception e) {
            throw new Exception("Can't deserialize:\n" + asJson(this.instance), e);
        }
        //assertSame(clazz + " bad equals,", this.instance, deserialized);
        assertEquals(clazz + " bad hashcode,", deserialized.hashCode(), this.instance.hashCode());
    }

    public abstract T createInstance();
}