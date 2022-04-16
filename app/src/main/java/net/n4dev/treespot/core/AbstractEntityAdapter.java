package net.n4dev.treespot.core;

import android.content.Context;
import android.util.Log;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SortedList;

import com.orhanobut.logger.Logger;

import net.n4dev.treespot.core.api.IEntity;
import net.n4dev.treespot.core.api.IFriend;
import net.n4dev.treespot.core.api.IQuery;
import net.n4dev.treespot.core.api.ITreeSpot;
import net.n4dev.treespot.core.api.ITreeSpotMedia;
import net.n4dev.treespot.db.TreeSpotObjectBox;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import io.objectbox.Box;
import io.objectbox.query.Query;

public abstract class AbstractEntityAdapter<T extends IEntity, H extends AbstractViewHolder> extends RecyclerView.Adapter<H> {

    private final SortedList<T> entities;
    private final H             viewHolder;
    private final int           br_selection;
    private final boolean       isSimple;
    private final Box<T>        mainBox;

    /**
     * The onItemSelected is where customization and business logic of a pressing/selecting a item as a whole is applied.
     * Setting up click listeners for individual buttons and icons should be done in {@link AbstractEntityAdapter#onBindItem(AbstractViewHolder, IEntity, int)}
     */
    protected abstract void onItemSelected(H holder, T entity, Context context, int position);

    /**
     * Allows customization of each item in the list.
     * This method is invoked within RecyclerView.Adapter#onBindViewholder.
     *
     * If you have data missing/not showing on screen, be sure that {@link AbstractViewHolder#set(int, IEntity)}
     * is being invoked. As a good practice, it should be the first line called.
     * @param holder
     * @param entity
     * @param position
     */
    protected abstract void onBindItem(H holder, T entity, int position);

    /**
     * Invoked when the Primary Entity (T) under the specific parameters of
     * its paired query returned 0 or null results. This should not be invoked
     * for secondary entity lists.
     * @param holder - The ViewHolder representing a adapteritem object.
     */
    protected abstract void onNoItemsAvailable(H holder);

    public void removeItem(int position) {
        getEntities().removeItemAt(position);
        super.notifyItemRemoved(position);
    }

    /**
     *
     * @param holder - The custom viewholder that extends {@link AbstractViewHolder}.
     * @param query - The query being used
     * @param selection - Expects a net.n4dev.treespot..BR variable, it should correspond with
     *                  the xml file being used.
     */
    public AbstractEntityAdapter(H holder, Query<T> query, int selection, boolean simple, Class<T> klass) {
        this.viewHolder = holder;
        this.br_selection = selection;
        this.isSimple = simple;
        this.entities = new SortedList<>(klass, this.callback);
         this.setHasStableIds(true);
         mainBox = TreeSpotObjectBox.INSTANCE.getBoxStore().boxFor(klass);

        try {
             load((Query<T>) query);
        } catch (Exception e) {
            Logger.e(e, "Attempted to invoke primary query load but returned exception!");
            this.onNoItemsAvailable(holder);
        }
    }

    @NonNull
    @Override
    public final H onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ViewDataBinding binding = viewHolder.getDataBinding(parent.getContext(), parent, false);
        AbstractViewHolder holder = this.viewHolder.newInstance(binding);
        return (H) holder;
    }

    @Override
    public final void onBindViewHolder(@NonNull H holder, int position) {
        T entity = entities.get(position);
        Context context = holder.itemView.getContext();
        holder.itemView.setOnClickListener(l -> {
            this.onItemSelected(holder, entity, context, position);
        });

        holder.set(br_selection, entity);
        if (!isSimple) {
            this.onBindItem(holder, entity, position);
        }
    }

    @Override
    public final int getItemCount() {
        if(entities != null) {
            return entities.size();
        } else {
            return 0;
        }
    }


    /**
     * Directly interacts with the entity list shown on the device.
     * This query should only be used in a couple of scenarios;
     *
     * 1. Loading the initial data to display.
     *
     * 2. Change the data being displayed based on user input.
     *
     * If you need other data loaded, but is not the primary Entity,
     * use {@link #loadSecondary(Query, List)} instead.
     * @param query
     * @throws Exception
     */
    public synchronized void load(Query<T> query)
            throws Exception
    {
      if(query != null) {
          entities.clear();
          CopyOnWriteArrayList<IEntity> copyEntities = new CopyOnWriteArrayList<>();

          Thread loadThread = new Thread(() -> {
              List<T> returnedQuery = query.find();
              copyEntities.addAll(returnedQuery);
              query.close();
          });

          loadThread.start();
          loadThread.join();

          //Convert objects from IEntity to the intended class (T)
          if(copyEntities.size() > 0) {
              copyEntities.forEach(entity -> entities.add((T) entity));
              Logger.i("Copied " + copyEntities.size() + " entities!");
              copyEntities.clear();
          } else {
              Logger.i("Attempted to return results for " + query + " but returned zero results!");
              this.onNoItemsAvailable(viewHolder);
          }

          Log.i("SST", "Item Range Changed! (" + entities.size() + ")");
          notifyItemRangeChanged(0, entities.size(), entities);
      } else {
          Logger.e(new Throwable(), "AbstractEntityAdapter.load() failed to run because the provided query was null!");
      }
    }

    /**
     * Helper method for loading another list of data that will also be displayed along side
     * the primary Entity.
     * @param query
     * @param queryList - The list that will be given the results of query.
     * @throws Exception
     */
    public void loadSecondary(Query query, @NonNull List<IEntity> queryList)
            throws Exception {

        Thread loadSecondaryThread = new Thread(() -> {
            List retunedQuery = query.find();
            queryList.addAll(retunedQuery);
            query.close();
        });

        loadSecondaryThread.start();
        loadSecondaryThread.join();
    }

    public void save(IEntity entity) {

        Thread saveThread = new Thread(() -> {

        });

        saveThread.start();

        try {
            saveThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    public void delete(IEntity entity) {

        Thread deleteThread = new Thread(() -> {

        });

        deleteThread.start();

        try {
            deleteThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
            Logger.e(e, "");
        }
    }

    public SortedList.Callback<T> getCallback() {
        return callback;
    }

    public SortedList<T> getEntities() {
        return entities;
    }

    /**
     * Converts the default SortedList into a ArrayList.
     * SortedLists does NOT extend or implement the List internface,
     * so certain operations such as iterating and addAll do not work.
     *
     * Calling this method should be done with caution and should
     * only be used when screens require a search function. The less this is called,
     * the better performance overall.
     *
     * @return A ArrayList of T objects.
     */
    public ArrayList<T> getEntitiesAsArrayList() {
        ArrayList<T> converted = new ArrayList<>();

        for(int i = 0; i < entities.size(); i++) {
            T entity = entities.get(i);
            converted.add(entity);
        }

        return converted;
    }

    /**
     * Because we are displaying custom classes, RecyclerView does not know how to sort and display them.
     * So we must tell it how to.
     */
     private final SortedList.Callback<T> callback = new SortedList.Callback<>() {
        @Override
        public int compare(T item1, T item2) {

            if(item1 instanceof ITreeSpot && item2 instanceof ITreeSpot) {
                ITreeSpot tree1 = (ITreeSpot) item1;
                ITreeSpot tree2 = (ITreeSpot) item2;

                return tree1.getSpotID().compareTo(tree2.getSpotID());
            } else  if(item1 instanceof IFriend && item2 instanceof IFriend) {
                IFriend friend1 = (IFriend) item1;
                IFriend friend2 = (IFriend) item2;

                return friend1.getFriendID().compareTo(friend2.getFriendID());
            } else if(item1 instanceof ITreeSpotMedia && item2 instanceof ITreeSpotMedia) {
              ITreeSpotMedia media1 = (ITreeSpotMedia) item1;
              ITreeSpotMedia media2 = (ITreeSpotMedia) item2;

              Long timestamp1 = media1.getMediaCreationDate();
              Long timestamp2 = media2.getMediaCreationDate();

              return timestamp1.compareTo(timestamp2);
            } else {
                Logger.e(new Throwable(), "Failed to properly compare in SortedList.Callback! (" + item1.getClass() + ")");
            }
            return 0;
        }


        @Override
        public boolean areContentsTheSame(T oldItem, T newItem) {

            if(oldItem instanceof ITreeSpot && newItem instanceof ITreeSpot) {
                ITreeSpot tree1 = (ITreeSpot) oldItem;
                ITreeSpot tree2 = (ITreeSpot) newItem;

                return tree1.getSpotID().equals(tree2.getSpotID());

            } else  if(oldItem instanceof IFriend && newItem instanceof IFriend) {
                IFriend friend1 = (IFriend) oldItem;
                IFriend friend2 = (IFriend) newItem;

                return friend1.getFriendID().equals(friend2.getFriendID());
            } else {
                Logger.e(new Throwable(), "Failed to properly compare in SortedList.Callback! (" + oldItem.getClass() + ")");
            }

            return false;
        }

        @Override
        public boolean areItemsTheSame(T item1, T item2) {

            return false;
        }

        @Override
        public void onChanged(int position, int count) {
            notifyItemRangeChanged(position, count);
        }

        @Override
        public void onInserted(int position, int count) { notifyItemRangeInserted(position, count); }

        @Override
        public void onRemoved(int position, int count) {
            notifyItemRangeRemoved(position, count);
        }

        @Override
        public void onMoved(int fromPosition, int toPosition) { notifyItemMoved(fromPosition, toPosition); }
    };
}
