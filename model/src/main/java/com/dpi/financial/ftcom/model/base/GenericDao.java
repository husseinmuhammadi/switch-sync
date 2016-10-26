package com.dpi.financial.ftcom.model.base;


import com.dpi.financial.ftcom.utility.exception.model.EntityIdIsNullException;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Dependent
public abstract class GenericDao<T extends EntityBase> {

    protected EntityManager getEntityManager() {
        return entityManager;
    }

    @Inject
    private EntityManager entityManager;

    private Class<T> entityBeanType;

    public GenericDao(Class<T> entityBeanType) {
        this.entityBeanType = entityBeanType;
    }

    public T create(T t) {
        entityManager.persist(t);
        return t;
    }

    public void remove(T t) {
        t.setDeleted(true);
        entityManager.merge(t);
    }

    public T update(T t) {
        if (t.getId() != null) {
            if (entityManager.contains(t)) {
                return t;
            } else {
                return entityManager.merge(t);
            }
        } else {
            throw new EntityIdIsNullException(t.getClass().getName() +
                    " not contain id when merge operation occurred");
        }
    }

    public T findById(Long id) {
        return entityManager.find(entityBeanType, id);
    }

    // TODO: Add method body
    public abstract List<T> findAll();/* {
        return createNamedQuery(T.FIND_ALL).getResultList();
    }*/

    public TypedQuery<T> createNamedQuery(String queryName) {
        return entityManager.createNamedQuery(queryName, entityBeanType);
    }

    public TypedQuery<T> createNamedQuery(String namedQueryName, Map<String, Object> parameters) {
        return createNamedQuery(namedQueryName, parameters, 0);
    }

    public TypedQuery<T> createNamedQuery(String namedQueryName, Map<String, Object> parameters, int limitResult) {
        Set<Map.Entry<String, Object>> rawParameters = parameters.entrySet();
        TypedQuery typedQuery = createNamedQuery(namedQueryName);
        if (limitResult > 0) {
            typedQuery.setMaxResults(limitResult);
        }
        for (Map.Entry<String, Object> entry : rawParameters) {
            typedQuery.setParameter(entry.getKey(), entry.getValue());
        }
        return typedQuery;
    }
}
