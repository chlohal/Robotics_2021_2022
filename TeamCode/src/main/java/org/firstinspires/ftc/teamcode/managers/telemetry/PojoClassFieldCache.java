package org.firstinspires.ftc.teamcode.managers.telemetry;

import org.firstinspires.ftc.teamcode.auxilary.PaulMath;

import java.lang.reflect.Field;

public class PojoClassFieldCache {
    Field field;
    Class type;
    Object object;

    public String name;

    public PojoClassFieldCache(Field field, Object object) {
        //null checks! Don't we love them!
        if(object == null) throw new IllegalArgumentException("given Object is null");
        if(field == null) throw new IllegalArgumentException("given Field is null");
        if(!field.getDeclaringClass().isAssignableFrom(object.getClass())) throw new IllegalArgumentException("given Field isn't declared on given Object");

        this.name = field.getName();
        this.field = field;
        this.type = field.getType();
        this.object = object;
    }

    public String getJSONFragment() {
        try {
            return getJSONFragmentRecursiveBoy(name, field.get(object).toString());
        } catch(IllegalAccessException ignored) {
            return "";
        }
    }

    public Class getType() {
        return type;
    }
    public String getName() {
        return name;
    }

    public boolean containsKey(String key) {
        if(key == null) return false;
        else if(key.indexOf('[') == -1) return key.equals(name);
        else return key.substring(0, key.indexOf('[')).equals(name);
    }

    public void set(String key, Object value) {
        if(!containsKey(key)) throw new IllegalArgumentException();
        String path = key.substring(name.length());

        try {
            if (path.equals("")) field.set(object, value);
            else setInArray(path, value);
        } catch(Exception ignored) {}
    }

    private void setInArray(String path, Object value) {
        //`path` should be of form `[x][y][z]...`, where x, y, and z are integers
        //validate this with regex
        if(!path.matches("^(\\[\\d+])+$")) throw new NumberFormatException();

        //convert to comma-separated values
        String commasep = path.replace('[', ',')
                .replace("]", "")
                .substring(1);

        String[] commasepvalues = commasep.split(",");

        //convert the string CSVs into ints
        int[] valuepath = new int[commasepvalues.length];
        for(int i = 0; i < commasepvalues.length; i++) valuepath[i] = Integer.parseInt(commasepvalues[i]);

        try {
            field.set(object, modifyNestedArrayPath(valuepath, value, (Object[]) field.get(object)));
        } catch (Exception ignored) {}
    }

    private static Object[] modifyNestedArrayPath(int[] indexes, Object value, Object[] array) {
        Object[] arrAddressed = array;
        for(int i = 0; i < indexes.length - 1; i++) {
            arrAddressed = (Object[])arrAddressed[indexes[i]];
        }
        arrAddressed[indexes[indexes.length - 1]] = value;
        return array;
    }

    private static String getJSONFragmentRecursiveBoy(String name, Object value) {
        Class type = value.getClass();
        if(!type.isArray()) return PaulMath.JSONify(name) + ":" + PaulMath.JSONify(value);

        StringBuilder result = new StringBuilder();
        Object[] target = (Object[])value;
        for(int i = 0; i < target.length; i++) {
            result.append(getJSONFragmentRecursiveBoy(name + "[" + i + "]", target[i]));
            if(i + 1 < target.length) result.append(",");
        }
        return result.toString();
    }
}
