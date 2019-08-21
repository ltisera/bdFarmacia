package modelo;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

public class MongoDateSerializer extends JsonSerializer<Date>{
    public void serialize(Date date, JsonGenerator jgen, SerializerProvider provider) throws IOException {
    	TimeZone tz = TimeZone.getTimeZone("UTC");
    	DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm'Z'"); // Quoted "Z" to indicate UTC, no timezone offset
    	df.setTimeZone(tz);
    	String isoDate = df.format(date);
    	String text = "{ \"$date\" : \""+   isoDate   +"\"}";
        jgen.writeRawValue(text);
    }
}
