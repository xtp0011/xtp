package com.imooc.sell.serializer;

import java.io.IOException;
import java.util.Date;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

public class DateToLongSerializer extends JsonSerializer<Date> {
	@Override
	public void serialize(Date date, JsonGenerator gen, SerializerProvider serializers) throws IOException {
		gen.writeNumber(date.getTime()/1000);
	}

}
