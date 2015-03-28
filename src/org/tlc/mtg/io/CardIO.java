package org.tlc.mtg.io;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.tlc.mtg.nouns.RawCard;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 */
public class CardIO {


    public static Map<String, RawCard> read(String fileNm) throws IOException {
        Map<String, RawCard> ret = new HashMap<String, RawCard>();

        JsonFactory factory = new JsonFactory();
        factory.enable(JsonParser.Feature.ALLOW_COMMENTS);

        JsonParser jp = factory.createParser(new File(fileNm));
        jp.setCodec(new ObjectMapper());

        Map<?,?> m = jp.readValueAs(Map.class);
        toRaw(m, ret);

        return ret;

    }

    public static List<String> readCardLines(String fileNm) throws IOException {
        List<String> ret = new ArrayList<>(60);
        try(
            FileReader fr = new FileReader(fileNm);
            BufferedReader br = new BufferedReader(fr)
        ) {

            String ln;
            while( (ln = br.readLine()) != null ) {
                if( ln.startsWith("#") || ln.startsWith("//") )
                    continue;
                if( ln.trim().length() == 0 )
                    continue;
                ret.add(ln);
            }
        }
        return ret;
    }

    protected static void toRaw(Map<?, ?> raw, Map<String, RawCard> cards) {
        for( Object o : raw.keySet() ) {
            if( ! (o instanceof String) ) {
                continue;
            }

            if( ((String)o).equals("cards") ) {
                Object v = raw.get(o);
                if( v instanceof ArrayList ) {
                    for( Map m : ((ArrayList<Map<String, ?>>)v) ) {
                        String name = (String) m.get("name");
                        RawCard c = new RawCard(m);
                        cards.put(name, c);
                    }
                }
                continue;
            }

            Object v = raw.get(o);
            if( v instanceof Map ) {
                toRaw((Map<?, ?>) v, cards);
                continue;
            }

//            System.out.println("Ran out of stuff: " + o);

        }
    }
}
