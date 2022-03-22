package net.n4dev.treespot.core;

import android.content.Context;
import android.graphics.ColorFilter;
import android.view.ViewGroup;

import androidx.core.content.ContextCompat;
import androidx.core.graphics.BlendModeColorFilterCompat;
import androidx.core.graphics.BlendModeCompat;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import net.n4dev.treespot.R;
import net.n4dev.treespot.core.api.IEntity;

public abstract class AbstractViewHolder<B extends ViewDataBinding> extends RecyclerView.ViewHolder {
    private final B binding;

    public abstract B getDataBinding(Context context, ViewGroup parent, boolean attach);

    /**
     * Returns a fresh instance of {@link AbstractViewHolder<B>} to be used by
     * the related {@link AbstractEntityAdapter<>}
     * @param binding - The instance of {@link B} to pass into the adapter.
     * @return - A instance of AbstractViewHolder
     */
    public abstract AbstractViewHolder<B> newInstance(B binding);

    /**
     * Returns a already initialized {@link B} object for modification of
     * the Databinding class assigned to the XML file.
     * @return - A databinding class for modifying the XML file and its views and objects.
     */
    public abstract B getXMLBinding();

    public AbstractViewHolder(B binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

    /**
     * Sets the object to be displayed for the view item.
     *
     * @param br_selection - The com.synsoltec.losscontrol.mobile.app.BR variable associated with the XML file being used.
     * @param obj - The IEntity being assigned.
     */
    public void set(int br_selection, IEntity obj) {
        binding.setVariable(br_selection, obj);
        binding.executePendingBindings();
    }

    /**
     * Helper method designed to put black separation lines between RecyclerView items.
     * This is to help distinguish and isolate objects from one another.
     * @param recyclerView - The {@link RecyclerView} that will get the separation lines.
     * @param layoutManager - The @{@link LinearLayoutManager} that holds the layout format and orientation for the RecyclerView.
     */
    public static void generateItemDecoration(RecyclerView recyclerView, LinearLayoutManager layoutManager) {
        Context context = recyclerView.getContext();
        int color = ContextCompat.getColor(context, R.color.md_black_1000);
        DividerItemDecoration itemDecoration = new DividerItemDecoration(context, layoutManager.getOrientation());
        ColorFilter filter = BlendModeColorFilterCompat.createBlendModeColorFilterCompat(color, BlendModeCompat.SRC_OUT);

        itemDecoration.getDrawable().setColorFilter(filter);
        recyclerView.addItemDecoration(itemDecoration);
    }
}
