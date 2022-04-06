package simulator;

import java.util.AbstractMap;
 
public class Pair <T, U>
{
    private AbstractMap.SimpleEntry<T, U> contents;

    public Pair (T first, U second) {
        contents = new AbstractMap.SimpleEntry<>(first, second);
    }

    public T getKey() {
        return contents.getKey();
    }

    public U getValue() {
        return contents.getValue();
    }
}
