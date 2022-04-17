package net.n4dev.treespot.core.api;


import io.objectbox.EntityInfo;
import io.objectbox.query.Query;
import io.objectbox.query.QueryCondition;

public interface IQuery<T extends IEntity> {

    QueryCondition<T> buildQuery();
}
