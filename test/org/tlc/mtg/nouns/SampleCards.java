package org.tlc.mtg.nouns;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.Map;

/**
 */
public class SampleCards {
  
  public static Map<String, Object> elves() {
    @SuppressWarnings("unchecked") Map<String,Object> m = rawCard(elfMap);
    return m;
  }

  public static Map<String, Object> forrest() {
    @SuppressWarnings("unchecked") Map<String,Object> m = rawCard(forrestMap);
    return m;
  }

  public static Map<String, Object> bears() {
    @SuppressWarnings("unchecked") Map<String,Object> m = rawCard(bearMap);
    return m;
  }

  private static Map<String, Object> rawCard(String src) {
    try {
      JsonFactory factory = new JsonFactory();
      factory.enable(JsonParser.Feature.ALLOW_COMMENTS);

      JsonParser jp = factory.createParser(src);
      jp.setCodec(new ObjectMapper());

      @SuppressWarnings("unchecked") Map<String, Object> m = jp.readValueAs(Map.class);
      return m;
    } catch(IOException e) {
      throw new RuntimeException("Could not create card", e);
    }

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


  static String forrestMap =
"      {" +
"          \"artist\": \"John Avon\"," +
"          \"cmc\": 0," +
"          \"colorIdentity\": [" +
"          \"G\"" +
"          ]," +
"          \"id\": \"70523a1e8264d75a44d6d18af66f813dda4e7960\"," +
"          \"imageName\": \"forest\"," +
"          \"layout\": \"normal\"," +
"          \"multiverseid\": 439605," +
"          \"name\": \"Forest\"," +
"          \"number\": \"216\"," +
"          \"rarity\": \"Basic Land\"," +
"          \"subtypes\": [" +
"              \"Forest\"" +
"          ]," +
"          \"supertypes\": [" +
"              \"Basic\"" +
"          ]," +
"          \"type\": \"Basic Land — Forest\"," +
"          \"types\": [" +
"              \"Land\"" +
"          ]," +
"          \"watermark\": \"Green\"" +
"      }";

  static String bearMap =
"      {" +
"          \"artist\": \"Zina Saunders\"," +
"          \"cmc\": 2," +
"          \"colorIdentity\": [" +
"              \"G\"" +
"          ]," +
"          \"colors\": [" +
"             \"Green\"" +
"          ]," +
"          \"flavor\": \"Don't worry about provoking grizzly bears; they come that way.\"," +
"          \"id\": \"8aaf65af7667f55f9af2a4a325cd02fbce432121\"," +
"          \"imageName\": \"grizzly bears\"," +
"          \"layout\": \"normal\"," +
"          \"manaCost\": \"{1}{G}\"," +
"          \"mciNumber\": \"94\"," +
"          \"multiverseid\": 4300," +
"          \"name\": \"Grizzly Bears\"," +
"          \"power\": \"2\"," +
"          \"rarity\": \"Common\"," +
"          \"subtypes\": [" +
"            \"Bear\"" +
"          ]," +
"          \"toughness\": \"2\"," +
"          \"type\": \"Creature — Bear\"," +
"          \"types\": [" +
"             \"Creature\"" +
"          ]" +
"      }";

    }
