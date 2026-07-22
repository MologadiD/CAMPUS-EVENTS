public interface Iservice {
    public <T> T create(T t);
    public <T> T read(Long id);
    public <T> T update(T t);
    public <T> void delete(T t);
}
