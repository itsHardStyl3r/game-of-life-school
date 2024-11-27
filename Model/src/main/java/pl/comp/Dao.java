package pl.comp;

public interface Dao<T> {
    T read() throws Exception;

    void write(T o) throws Exception;

}