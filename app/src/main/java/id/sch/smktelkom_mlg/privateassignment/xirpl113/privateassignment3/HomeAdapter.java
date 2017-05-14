package id.sch.smktelkom_mlg.privateassignment.xirpl113.privateassignment3;

/**
 * Created by Fay on 12/05/2017.
 */

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

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
    } //connects to xmlnya

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final HomeListItem homeListItem = homeListItems.get(position);
        // used by homeListItem to .load


        holder.textViewHead.setText(homeListItem.getHead());
        holder.textViewDesc.setText(homeListItem.getDesc());

        Glide
                .with(context)
                .load(homeListItem.getImageUrl())
                .into(holder.imageViewPhoto);
        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(context, "Click" + homeListItem.getHead(), Toast.LENGTH_LONG).show();
                Intent singleBlogIntent = new Intent(context, DetailActivity.class);
                singleBlogIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                singleBlogIntent.putExtra("blog_id", position);
                context.startActivity(singleBlogIntent);
            }
        });
    }//mau diapakan viewnya

    @Override
    public int getItemCount() {
        return homeListItems.size();
    }//retrieving data, amount showed


    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView textViewHead;
        public TextView textViewDesc;
        public ImageView imageViewPhoto;
        public LinearLayout linearLayout;

        public ViewHolder(View itemView) {
            super(itemView);

            textViewHead = (TextView) itemView.findViewById(R.id.textViewHead);
            textViewDesc = (TextView) itemView.findViewById(R.id.textViewDesc);
            imageViewPhoto = (ImageView) itemView.findViewById(R.id.imageViewPhoto);
            linearLayout = (LinearLayout) itemView.findViewById(R.id.linearLayout);
        }
    }
}
