package com.example.a4000essentialwordsbook1.SelectedBook;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.a4000essentialwordsbook1.Models.UnitModel;
import com.example.a4000essentialwordsbook1.R;
import com.example.a4000essentialwordsbook1.SelectedUnitTab.ActivitySelectedTab;
import com.example.a4000essentialwordsbook1.StringNote.DB_NOTES.ExtraNotes;

import java.io.File;
import java.util.ArrayList;

public class AdapterSelectedBook  extends RecyclerView.Adapter<AdapterSelectedBook.ViewHolder>{
    public final Context unitContext;
    private final ArrayList<UnitModel> unitList;
    private final LayoutInflater inflater;

    private final int dbNUm;

    public AdapterSelectedBook(Context context, ArrayList<UnitModel> list, int dbNum){
        this.unitContext = context;
        this.unitList = list;
        this. dbNUm = dbNum;
        this.inflater = LayoutInflater.from(context);
    }



    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.single_item_selected_book, parent, false);
        return new ViewHolder(view, unitContext);
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        UnitModel model = unitList.get(holder.getLayoutPosition());

        setImageResources(holder, model);

        String text = Integer.toString(model.getuId());
        holder.unitTtl.setText(model.getUnitTitle());
        holder.unitNum.setText(text);
    }

    private void setImageResources(ViewHolder holder, UnitModel model){

        final File imageDir = new File(Environment.DIRECTORY_DOWNLOADS, File.separator + "4000 Essential Words");

        final File imgMainPath = new File("Image Files");
        final File wordImgPath = new File(imgMainPath, File.separator + "Unit Images");
        final File wordImgBookPath = new File(wordImgPath, File.separator + "Book_" + dbNUm);

        final File imgName = new File(wordImgBookPath, File.separator + "." + new File(model.getUnitImg()).getName());
        final File imgFile = new File(Environment.getExternalStoragePublicDirectory(imageDir.toString()), imgName.toString());


        if (imgFile.exists()){
            Drawable imgDrawable = Drawable.createFromPath(imgFile.toString());
            Glide.with(unitContext)
                    .load(imgDrawable)
                    .placeholder(R.drawable.loadimg)
                    .error(R.drawable.loadimg)
                    .into(holder.unitImg);
        }else {
            Glide.with(unitContext)
                    .load(model.getUnitImg())
                    .placeholder(R.drawable.loadimg)
                    .error(R.drawable.loadimg)
                    .into(holder.unitImg);
        }
    }



    @Override
    public int getItemCount() {
        return unitList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener{
        ImageView unitImg;
        TextView unitTtl;
        TextView unitNum;
        CardView cardUnit;
        Context context;
        Intent unitIntent;

        public ViewHolder(@NonNull View itemView, Context context) {
            super(itemView);
            this.context = context;
            unitImg = itemView.findViewById(R.id.unit_img);
            unitTtl = itemView.findViewById(R.id.unit_title);
            unitNum = itemView.findViewById(R.id.unit_num);
            cardUnit = itemView.findViewById(R.id.unit_card_view);
            componentsClickListener();
        }

        public void componentsClickListener(){
            cardUnit.setOnClickListener(this);
        }


        @Override
        public void onClick(View v) {
            String sDbNumber = ExtraNotes.DB_NUMBER;
            String sUnitNumber = ExtraNotes.UNIT_NUMBER;
            switch (v.getId()) {
                case (R.id.unit_card_view):
                    int position = getBindingAdapterPosition() + 1;
                    unitIntent = new Intent(this.context, ActivitySelectedTab.class);
                    unitIntent.putExtra(sDbNumber, dbNUm);
                    unitIntent.putExtra(sUnitNumber, position);
                    context.startActivity(unitIntent);
                    break;
                case (R.id.story_tab_card_view):
                    Toast.makeText(context, "story Section !", Toast.LENGTH_SHORT).show();
                    break;
            }

        }

        @Override
        public boolean onLongClick(View v) {
            Toast.makeText(context, "you son of a bitch!", Toast.LENGTH_SHORT).show();

            return true;
        }
    }
}
