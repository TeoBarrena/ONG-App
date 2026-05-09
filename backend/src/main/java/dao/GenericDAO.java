package dao;

import jakarta.annotation.PreDestroy;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.NoResultException;
import jakarta.persistence.NonUniqueResultException;
import jakarta.persistence.TypedQuery;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

//@RequestScoped
public abstract class GenericDAO<T> implements IGenericDAO<T> {
	
	//ATRIBUTOS
	protected final Class<T> entityClass;

	@Inject
	protected EntityManager em;

	@PreDestroy
	public void preDestroy() {
		this.em.close();
	}
	
	//CONSTRUCTORES  
	
	public GenericDAO() {
		Type superClass = getClass().getGenericSuperclass();
		this.entityClass = (Class<T>) ((ParameterizedType) superClass).getActualTypeArguments()[0];	
	}
	
	public GenericDAO(Class<T> entityClass) {
		this.entityClass = entityClass;	
	}
	
	//MÉTODOS PRIVADOS
	private void log(Exception e) {
		System.out.println("Ocurrió un error en el DAO"+e.getMessage()+" "+e); // REFACTOR - Reemplazar por logger
	}

	//MÉTODOS PÚBLICOS	
	public List<T> getList(String column, String string) {
		//EntityManager em = getEntityManager();
		try {
			String jpql = "SELECT e FROM " + entityClass.getSimpleName() + " e WHERE e." + column + " = :valor";
			TypedQuery<T> q = em.createQuery(jpql, entityClass);
			q.setParameter("valor", string);
			
			return q.getResultList();
		}
		finally {
			//em.close();			
		}
	}
	public List<T> getPaginada(int numeroPagina, int tamanio) {
		
	    int offset = (tamanio * numeroPagina) - tamanio;

        TypedQuery<T> q = em.createQuery("SELECT e FROM " + entityClass.getSimpleName() + " e", entityClass);
        q.setFirstResult(offset);
        q.setMaxResults(tamanio);
        return q.getResultList();
	}

	public List<T> getPaginada(int numeroPagina, int tamanio, String column, String string) {
		
	    int offset = (tamanio * numeroPagina) - tamanio;
	
	    String jpql = "SELECT e FROM " + entityClass.getSimpleName() + " e WHERE e." + column + " = :valor";
		TypedQuery<T> q = em.createQuery(jpql, entityClass);
		q.setParameter("valor", string);
	    q.setFirstResult(offset);
	    q.setMaxResults(tamanio);
	    return q.getResultList();
	}

	public List<T> getList() {
		//EntityManager em = getEntityManager();
		try {
			TypedQuery<T> q = em.createQuery("SELECT e FROM "+ entityClass.getSimpleName() +" e",entityClass);
			//q.setParameter(0, entityClass.getSimpleName());
			return q.getResultList();
		}
		catch (Exception e) {
			throw e;
		}
		finally {
			//em.close();			
		}
	}
	
	public List<T> getList(String nombreQuery, Map<String, Object> parametros) {
	    //EntityManager em = getEntityManager();
	    try {
	        TypedQuery<T> q = em.createNamedQuery(nombreQuery, entityClass);
	        parametros.forEach((key, value) -> {
	        	q.setParameter(key, value);	//Donde la clave es el nombre del atributo / columna y value es el valor por el que queremos comparar.
	        }); 
	        return q.getResultList();
	    } finally {
	        //em.close();
	    }
	}
	

	
	public T get(Long id)  throws NoResultException, NonUniqueResultException {
		//EntityManager em = getEntityManager();
        try {
            return em.find(entityClass, id);
        } 
        catch(Exception e) {
        	log(e);
        	throw e;
        }
        finally {
            //this.em.close();
        }
	}
	
	public T get(String column, Object string) throws NoResultException, NonUniqueResultException  {
		//EntityManager this.em = getEntityManager();
		try {
			String jpql = "SELECT e FROM " + entityClass.getSimpleName() + " e WHERE e." + column + " = :valor";
			TypedQuery<T> q = this.em.createQuery(jpql, entityClass);
			q.setParameter("valor", string);
			
			List<T> results = q.getResultList();
			return results.isEmpty() ? null : results.get(0);
		} 
		catch(Exception e) {
			log(e);
			throw e;
		}
		finally {
			//this.em.close();
		}
	}
	
	/*public T get(String column, Integer value) throws NoResultException, NonUniqueResultException  {
		//EntityManager this.em = getEntityManager();
		try {
			String jpql = "SELECT e FROM " + entityClass.getSimpleName() + " e WHERE e." + column + " = :valor";
			TypedQuery<T> q = this.em.createQuery(jpql, entityClass);
			q.setParameter("valor", value);
			
			List<T> results = q.getResultList();
			return results.isEmpty() ? null : results.get(0);
		} 
		catch(Exception e) {
			log(e);
			//return null;
			throw e;
		}
		finally {
			//this.em.close();
		}
	}*/
	
	
	public T get(String nombreQuery, Map<String, Object> parametros) throws NoResultException, NonUniqueResultException {
	    //EntityManager this.em = getEntityManager();
	    try {
	        TypedQuery<T> q = this.em.createNamedQuery(nombreQuery, entityClass);
	        parametros.forEach((key, value) -> {
	        	q.setParameter(key, value);	//Donde la clave es el nombre del atributo / columna y value es el valor por el que queremos comparar.
	        }); 
	        return q.getSingleResult();
	    } 
	    catch(Exception e) {
        	log(e);
        	throw e;
        }
	    finally {
	        //this.em.close();
	    }
	}

	public Boolean create(T object) {
		//EntityManager this.em = getEntityManager();
		EntityTransaction transaction = this.em.getTransaction();
        try {
        	transaction.begin();
        	this.em.persist(object);
        	transaction.commit();
        	return true;
        }
        catch(Exception e){
        	log(e);
        	transaction.rollback();
        	throw e;
        }
        finally {
        	//this.em.close();
        }
	}
	
	public T createAndReturn(T elemento) {
	    EntityTransaction transaction = em.getTransaction();
	    try {
	        transaction.begin();
	        em.persist(elemento);
	        transaction.commit();
	        return elemento; //
	    } catch(Exception e) {
	        transaction.rollback();
	        return null;
	    }
	}
	
	/*
	
	private void copyNonNullFields(T source, T target) {
	    for (Field field : source.getClass().getDeclaredFields()) {
	        field.setAccessible(true);
	        try {
	            Object value = field.get(source);
	            if (value != null) {
	                field.set(target, value);
	            }
	        } catch (IllegalAccessException e) {
	            throw new RuntimeException("No se pudo copiar el campo " + field.getName(), e);
	        }
	    }
	}
	private Object getPrimaryKey(T entity) {
	    try {
	        Field idField = entity.getClass().getDeclaredField("id");
	        idField.setAccessible(true);
	        return idField.get(entity);
	    } catch (Exception e) {
	        throw new RuntimeException("No se pudo acceder al campo id", e);
	    }
	}
	public Boolean update(T entity) {
	    T managed = (T) em.find(entity.getClass(), getPrimaryKey(entity));
	    if (managed == null) {
	        throw new EntityNotFoundException("No se encontró la entidad con id " + getPrimaryKey(entity));
	    }

	    // Acá actualizás solo los campos necesarios (manual o con helper)
	    copyNonNullFields(entity, managed);

	    em.merge(managed);
	    return true;
	}

	@Override
	public Boolean update(T object, Long id) {
		// TODO Auto-generated method stub
		return this.update(object);
	}		*/
	
	public T update(T object, Long id) {
		//EntityManager this.em = getEntityManager();
		EntityTransaction transaction = this.em.getTransaction();
        try {
        	if (this.em.find(entityClass, id) == null) {
        		return null;
            }
        	else {
        		transaction.begin();
        		this.em.merge(object);
        		transaction.commit();
        		return object;
        	}
        }
        catch(Exception e){
        	transaction.rollback();
        	throw e;
        }
        finally {
        	//this.em.close();
        }
	}
	
	public Boolean delete(Long id) {
		//EntityManager this.em = getEntityManager();
		EntityTransaction transaction = this.em.getTransaction();
        try {
        	T object = this.em.find(entityClass, id);
        	if(object!=null) {
	        	transaction.begin();
	        	this.em.remove(object);
	        	transaction.commit();
	        	return true;
        	}
        	else
        		return false;
        }
        catch(Exception e){
        	transaction.rollback();
        	throw e;
        }
        finally {
        	//this.em.close();
        }
	}
	
}
