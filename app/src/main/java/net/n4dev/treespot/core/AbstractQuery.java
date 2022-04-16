package net.n4dev.treespot.core;

import net.n4dev.treespot.core.api.IEntity;
import net.n4dev.treespot.core.api.IQuery;
import net.n4dev.treespot.db.TreeSpotObjectBox;

import io.objectbox.Box;
import io.objectbox.Property;
import io.objectbox.query.Query;
import io.objectbox.query.QueryCondition;

public abstract class AbstractQuery<T extends IEntity> implements IQuery<T> {

    private final Box<T> paramBox;
    private Query<T> query;
    private Class<T> entityClass;

    public abstract QueryCondition<T> buildConditions(Property<T>[] fields);

    public AbstractQuery(Class<T> klass) {
        this.entityClass = klass;
        paramBox = TreeSpotObjectBox.INSTANCE.getBox(entityClass);
    }


    @Override
    public QueryCondition<T> buildQuery() {
        return buildConditions(paramBox.getEntityInfo().getAllProperties());
    }

    @Override
    public Query<T> get() {
        return query;
    }
}
