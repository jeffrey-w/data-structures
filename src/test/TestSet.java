package test;

import main.AbstractSet;

import java.lang.reflect.Field;

final class TestSet extends AbstractSet<Integer> {

    TestSet() {
        try {
            Field map = AbstractSet.class.getDeclaredField("map");
            map.setAccessible(true);
            map.set(this, new TestMap());
        } catch (IllegalAccessException | NoSuchFieldException e) {
            e.printStackTrace();
        }
    }

    private static final long serialVersionUID = -2031715972964286963L;

}
