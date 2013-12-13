package plan3.recruitment.backend.serialize;
/*
* Copyright (c) 2011 by Sabre Holdings Corp.
* 3150 Sabre Drive, Southlake, TX 76092 USA
* All rights reserved.
*
* This software is the confidential and proprietary information
* of Sabre Holdings Corporation ("Confidential Information").
* You shall not disclose such Confidential Information and shall
* use it only in accordance with the terms of the license agreement
* you entered into with Sabre Holdings Corporation.
*/


import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import plan3.recruitment.backend.model.Person;

import java.io.IOException;

public class PersonSerializer extends JsonSerializer<Person> {

    @Override
    public void serialize(Person person, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException, JsonProcessingException {
    }
}
