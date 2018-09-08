package com.app.repository.generic;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.lang.reflect.ParameterizedType;
import java.util.List;
import java.util.Optional;

public abstract class AbstractGenericDao<T> implements GenericDao<T> {
    private final Class<T> entityClass;
    private SessionFactory factory = DbConnection.getInstance().getSessionFactory();

    protected SessionFactory getFactory() {
        return factory;
    }

    public AbstractGenericDao() {
        this.entityClass = (Class<T>) ((ParameterizedType) this.getClass().getGenericSuperclass())
                .getActualTypeArguments()[0];
    }

    @Override
    public void add(T t) {
        if (t != null) {
            Session session = factory.openSession();
            Transaction tx = session.getTransaction();
            try {
                tx.begin();
                session.persist(t);
                tx.commit();
            } catch (Exception e) {
                if (tx != null) {
                    tx.rollback();
                }
            } finally {
                if (session != null) {
                    session.close();
                }
            }
        }
    }

    @Override
    public void update(T t) {
        if (t != null) {
            Session session = factory.openSession();
            Transaction tx = session.getTransaction();
            try {
                tx.begin();
                session.update(t);
                tx.commit();
            } catch (Exception e) {
                if (tx != null) {
                    tx.rollback();
                }
            } finally {
                if (session != null) {
                    session.close();
                }
            }
        }
    }

    @Override
    public void delete(Long id) {
        if (id != null) {
            Session session = factory.openSession();
            Transaction tx = session.getTransaction();
            try {
                tx.begin();
                T t = session.get(this.entityClass, id);
                session.delete(t);
                tx.commit();
            } catch (Exception e) {
                if (tx != null) {
                    tx.rollback();
                }
            } finally {
                if (session != null) {
                    session.close();
                }
            }
        }
    }

    @Override
    public Optional<T> getById(Long id) {
        Session session = factory.openSession();
        Transaction tx = session.getTransaction();
        Optional<T> op = Optional.empty();
        try {
            tx.begin();
            ParameterizedType superclass = (ParameterizedType) getClass().getGenericSuperclass();
            //System.out.println("---> " + getClass().getGenericSuperclass());
            //System.out.println("---> " + session.get((Class<T>)superclass.getActualTypeArguments()[0],id));
            //op = Optional.of(session.get((Class<T>)superclass.getActualTypeArguments()[0],id));
            // op=Optional.of((T)session.get(daoType,id));
            //ParameterizedType superclass = (ParameterizedType)getClass().getGenericSuperclass();
            op = Optional.of(session.get(this.entityClass, id));
            tx.commit();
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return op;
    }

    @Override
    public List<T> getAll() {
        Session session = factory.openSession();
        Transaction tx = session.getTransaction();
        try {
            tx.begin();
            Query query = session.createQuery("select p from " + this.entityClass.getName() + " p");
            List<T> players = query.list();
            tx.commit();
            return players;
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return null;
    }
}
