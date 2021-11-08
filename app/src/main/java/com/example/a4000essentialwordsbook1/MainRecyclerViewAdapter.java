package com.example.a4000essentialwordsbook1;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.a4000essentialwordsbook1.NavigationClasses.SubNavigationDrawer.NavRecyclerView.SubNavModel;
import com.example.a4000essentialwordsbook1.SelectedBook.SelectedBookActivity;
import com.example.a4000essentialwordsbook1.StringNote.DB_NOTES.ExtraNotes;

import java.util.ArrayList;



public class MainRecyclerViewAdapter extends RecyclerView.Adapter<MainRecyclerViewAdapter.ViewHolder>{
    private final Context navContext;
    private final ArrayList<SubNavModel> navList;
    private final LayoutInflater inflater;
    private final String sDbNumber = ExtraNotes.DB_NUMBER;
    private final String sUnitNumber = ExtraNotes.UNIT_NUMBER;
    private final String sWordId = ExtraNotes.WORD_ID;

    public MainRecyclerViewAdapter(Context context, ArrayList<SubNavModel> list){
        this.navContext = context;
        this.navList = list;
        this.inflater = LayoutInflater.from(context);
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.single_item_main_adapter, parent, false);
        return new ViewHolder(view, navContext);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        SubNavModel model = navList.get(position);

        Glide.with(navContext)
                .load(model.getImage())
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                .placeholder(R.drawable.loadimg)
                .error(R.drawable.loadimg)
                .into(holder.img);

        holder.txt.setText(model.getTitle());

    }

    @Override
    public int getItemCount() {
        return navList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        ImageView img;
        TextView txt;
        CardView cardView;
        Context context;


        public ViewHolder(@NonNull View itemView, Context context) {
            super(itemView);
            this.context = context;

            img = itemView.findViewById(R.id.main_adapter_image_view);
            txt = itemView.findViewById(R.id.main_adapter_title);
            cardView = itemView.findViewById(R.id.main_adapter_card_view);

            cardView.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(context, SelectedBookActivity.class);
            intent.putExtra(sDbNumber, (getBindingAdapterPosition() + 1));
            context.startActivity(intent);
        }

    }


}
