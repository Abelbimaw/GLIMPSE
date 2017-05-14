package id.sch.smktelkom_mlg.privateassignment.xirpl113.privateassignment3;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.List;

/**
 * Created by Fay on 14/05/2017.
 */

public class NextAdapter  extends RecyclerView.Adapter<NextAdapter.ViewHolder> {

    private List<NextItem> nextListItems;
    private Context context;

    public NextAdapter(List<NextItem> nextListItems, Context context) {
        this.nextListItems = nextListItems;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.next_item, parent, false);
        return new ViewHolder(v);
    } //menyambungkan ke xmlnya

    @Override
    public void onBindViewHolder(NextAdapter.ViewHolder holder, final int position) {
        final NextItem nextItem = nextListItems.get(position);
        holder.tvTitle.setText(nextItem.getTitle());
        Glide
                .with(context)
                .load("http://image.tmdb.org/t/p/w500" + nextItem.getImageUrl())
                .into(holder.ivData);

        holder.rLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, nextItem.getTitle() + " selected", Toast.LENGTH_LONG).show();
                Intent singleBlogIntent = new Intent(context, NextDetailActivity.class);
                singleBlogIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                singleBlogIntent.putExtra("blog_id", position);
                context.startActivity(singleBlogIntent);
            }
        });
    }//mau diapakan viewnya

    @Override
    public int getItemCount() {
        return nextListItems.size();
    }//mengambil data, brp banyak data yg akan ditampilkan


    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView ivData;
        public TextView tvTitle;
        public TextView tvDesc;
        public RelativeLayout rLayout;

        public ViewHolder(View itemView) {
            super(itemView);

            ivData = (ImageView) itemView.findViewById(R.id.imageViewData);
            tvTitle = (TextView) itemView.findViewById(R.id.textViewTitle);
            tvDesc = (TextView) itemView.findViewById(R.id.textViewDesc);
            rLayout = (RelativeLayout) itemView.findViewById(R.id.LinearLayout);
        }
    }//inisialisasi
}
