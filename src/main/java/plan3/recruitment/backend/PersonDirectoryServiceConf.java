package plan3.recruitment.backend;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.yammer.dropwizard.config.Configuration;
import com.yammer.dropwizard.db.DatabaseConfiguration;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

public class PersonDirectoryServiceConf extends Configuration {

    @Valid
    @NotNull
    @JsonProperty("database")
    private final DatabaseConfiguration database = new DatabaseConfiguration();

    public DatabaseConfiguration getDatabaseConfiguration() {
        return database;
    }

}
