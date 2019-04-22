package ib.ganz.etoll.helper;

import com.google.gson.Gson;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

/**
 * Created by limakali on 3/17/2018.
 */

public class Gxon
{
    public static String toJsonObject(Object o)
    {
        return new Gson().toJson(o);
    }

    public static <T> T fromJsonObject(String json, Class<T> clazz)
    {
        return new Gson().fromJson(json, clazz);
    }

    public static <T> String toJsonArray(List<T> l)
    {
        return new Gson().toJson(l);
    }

    public static <T> List<T> fromJsonArray(String s, Class<T> c)
    {
        return new Gson().fromJson(s, new ListOfJson<T>(c));
    }

    private static class ListOfJson<T> implements ParameterizedType
    {
        private Class<?> wrapped;

        ListOfJson(Class<T> wrapper)
        {
            this.wrapped = wrapper;
        }

        @Override
        public Type[] getActualTypeArguments()
        {
            return new Type[] { wrapped };
        }

        @Override
        public Type getRawType()
        {
            return List.class;
        }

        @Override
        public Type getOwnerType()
        {
            return null;
        }
    }
}
