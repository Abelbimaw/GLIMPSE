package id.sch.smktelkom_mlg.privateassignment.xirpl113.privateassignment3;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

/**
 * Created by Fay on 15/05/2017.
 */

public class PinnedAdapter extends RecyclerView.Adapter<PinnedAdapter.ViewHolder> {

    private final Context context;
    ArrayList<PinnedItem> fItem;

    public PinnedAdapter(ArrayList<PinnedItem> favouriteItem, Context context) {
        this.fItem = favouriteItem;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.pinned_list_item, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        PinnedItem favouriteItem = fItem.get(position);
        holder.textViewHeadfav.setText(favouriteItem.judul);
        holder.textViewDescfav.setText(favouriteItem.deskripsi);
        Glide
                .with(context)
                .load(favouriteItem.urlgambar)
                .into(holder.imageViewOtoffav);

        holder.buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final PinnedItem favouriteItem1 = fItem.get(position);
                fItem.remove(position);
                favouriteItem1.delete();
                PinnedAdapter.this.notifyDataSetChanged();

            }
        });
    }

    @Override
    public int getItemCount() {
        return fItem.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView textViewHeadfav;
        public TextView textViewDescfav;
        public ImageView imageViewOtoffav;
        public Button buttonDelete;

        public ViewHolder(View itemView) {
            super(itemView);

            textViewHeadfav = (TextView) itemView.findViewById(R.id.textViewHeadfav);
            textViewDescfav = (TextView) itemView.findViewById(R.id.textViewDescfav);
            imageViewOtoffav = (ImageView) itemView.findViewById(R.id.imageViewOtoffav);
            buttonDelete = (Button) itemView.findViewById(R.id.buttonDelete);

        }
    }

}