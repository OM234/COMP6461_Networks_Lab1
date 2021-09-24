package input.parser.helper;

import org.junit.jupiter.api.Test;

import java.util.AbstractMap;
import java.util.function.IntFunction;
import java.util.function.IntPredicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

class GetRequestParserTest {

    String[] args = {"get", "-h", "arr:abb", "sdag", "-h", "ars:gsd"};

    @Test
    public void aTest(){
        IntStream.range(0, args.length-1)
                .filter(argumentIsHeader())
                .mapToObj(mapKeyValToMapEntry())
                .collect(Collectors.toMap(AbstractMap.SimpleImmutableEntry::getKey, AbstractMap.SimpleImmutableEntry::getValue));
    }

    private IntFunction<AbstractMap.SimpleImmutableEntry<String, String>> mapKeyValToMapEntry() {
        return index -> {
            String[] keyValArray = splitKeyValIntoArray(args[index + 1]);
            String key = keyValArray[0];
            String val = keyValArray[1];
            return new AbstractMap.SimpleImmutableEntry<>(key, val);
        };
    }

    private String[] splitKeyValIntoArray(String keyVal) {
        return keyVal.split(":");
    }

    private IntPredicate argumentIsHeader() {
        return index -> args[index].equalsIgnoreCase("-h");
    }
}