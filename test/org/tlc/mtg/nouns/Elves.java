package org.tlc.mtg.nouns;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.Map;

/**
 */
public class Elves {
  
  static Map<String, Object> elves() throws IOException {

    JsonFactory factory = new JsonFactory();
    factory.enable(JsonParser.Feature.ALLOW_COMMENTS);

    JsonParser jp = factory.createParser(elfMap);
    jp.setCodec(new ObjectMapper());

    @SuppressWarnings("unchecked") Map<String,Object> m = jp.readValueAs(Map.class);
    return m;
  }


  static String elfMap =
"  {" +
"   \"artist\": \"Anson Maddocks\"," +
"      \"cmc\": 1," +
"      \"colorIdentity\": [" +
"    \"G\"" +
"    ]," +
"    \"colors\": [" +
"    \"Green\"" +
"    ]," +
"    \"flavor\": \"Whenever the Llanowar elves gather the fruits of their forest, they leave one plant of each type untouched, considering that nature's portion.\"," +
"      \"id\": \"5a36bcf9a7ebd6a5c914a8a801e34cd7e987a96b\"," +
"      \"imageName\": \"llanowar elves\"," +
"      \"layout\": \"normal\"," +
"      \"manaCost\": \"{G}\"," +
"      \"mciNumber\": \"9\"," +
"      \"name\": \"Llanowar Elves\"," +
"      \"number\": \"9\"," +
"      \"power\": \"1\"," +
"      \"rarity\": \"Special\"," +
"      \"releaseDate\": \"2007-10\"," +
"      \"subtypes\": [" +
"    \"Elf\"," +
"        \"Druid\"" +
"    ]," +
"    \"text\": \"{T}: Add {G} to your mana pool.\"," +
"      \"toughness\": \"1\"," +
"      \"type\": \"Creature — Elf Druid\"," +
"      \"types\": [" +
"    \"Creature\"" +
"    ]" +
"  }";
}
