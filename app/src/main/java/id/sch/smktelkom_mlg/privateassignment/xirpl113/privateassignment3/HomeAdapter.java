package id.sch.smktelkom_mlg.privateassignment.xirpl113.privateassignment3;

/**
 * Created by Fay on 12/05/2017.
 */

import android.app.Application;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.bumptech.glide.Glide;

import java.util.List;

class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.ViewHolder> {

    private List<HomeListItem> homeListItems;
    private Context context;

    public HomeAdapter(List<HomeListItem> homeListItems, Context context) {
        this.homeListItems = homeListItems;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.home_item, parent, false);
        return new ViewHolder(v);
    } //menyambungkan ke xmlnya

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final HomeListItem homeListItem = homeListItems.get(position);// yg homeListItem digunakan untuk .load

        holder.textViewHead.setText(homeListItem.getHead());
        holder.textViewDesc.setText(homeListItem.getDesc());

        Glide
                .with(context)
                .load(homeListItem.getImageUrl())
                .into(holder.imageViewOtof);
    }//mau diapakan viewnya

    @Override
    public int getItemCount() {
        return homeListItems.size();
    }//mengambil data, brp banyak data yg akan ditampilkan



    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView textViewHead;
        public TextView textViewDesc;
        public ImageView imageViewOtof;
        public RecyclerView recyclerView;

        public ViewHolder(View itemView) {
            super(itemView);

            textViewHead = (TextView) itemView.findViewById(R.id.textViewHead);
            textViewDesc = (TextView) itemView.findViewById(R.id.textViewDesc);
            imageViewOtof = (ImageView) itemView.findViewById(R.id.imageViewPhoto);
            recyclerView = (RecyclerView) itemView.findViewById(R.id.recyclerView);
        }
    }//inisialisasi
}

