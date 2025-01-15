package pl.comp.storage;

public interface Dao<T> extends AutoCloseable {
    T read() throws Exception;

    void write(T o) throws Exception;
}