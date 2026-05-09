package dao;

import jakarta.persistence.NoResultException;
import jakarta.persistence.NonUniqueResultException;
import java.util.List;
import java.util.Map;

public interface IGenericDAO<T> {

  public List<T> getList();

  public List<T> getList(String column, String string);

  public List<T> getList(String nombreQuery, Map<String, Object> parametros);

  //public Map<String,T> getMap();
  public T get(Long id) throws NoResultException, NonUniqueResultException;

  public T get(String column, Object string) throws NoResultException, NonUniqueResultException;

  public T get(String nombreQuery, Map<String, Object> parametros)
      throws NoResultException, NonUniqueResultException;

  public Boolean create(T object);

  public T update(T object, Long id);

  public Boolean delete(Long id);
}
