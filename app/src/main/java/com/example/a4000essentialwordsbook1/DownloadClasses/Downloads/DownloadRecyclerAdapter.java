package com.example.a4000essentialwordsbook1.DownloadClasses.Downloads;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.a4000essentialwordsbook1.DownloadClasses.Downloads.DownloadInterfaces.DownItemListener;
import com.example.a4000essentialwordsbook1.R;

import java.io.File;
import java.util.ArrayList;

public class DownloadRecyclerAdapter extends RecyclerView.Adapter<DownloadRecyclerAdapter.ViewHolder> {


    private final ArrayList<ArrayList<String[]>> mulImgList;
    private final ArrayList<String[]> unitImgUrl;
    private final ArrayList<String[]> unitAudioUrl;
    private final ArrayList<String[]> wordAudioUrl;
    private final DownItemListener itemListener;
    private final Context context;
    private final LayoutInflater inflater;
    private final int dbNum;
    private ArrayList<String[]> imgUrl;
    private String[] wordImgLink;
    private String[] wordAudioLink;
    private String[] unitImgLink;
    private String[] unitAudioLink;
    private int itemPosition;


    public DownloadRecyclerAdapter(Context context, int dbNum,
                                   ArrayList<ArrayList<String[]>> mulImgList,
                                   ArrayList<String[]> unitImgUrl,
                                   ArrayList<String[]> unitAudioUrl,
                                   ArrayList<String[]> wordAudioUrl,
                                   DownItemListener itemListener) {

        this.inflater = LayoutInflater.from(context);
        this.context = context;
        this.dbNum = dbNum;
        this.mulImgList = mulImgList;
        this.unitImgUrl = unitImgUrl;
        this.unitAudioUrl = unitAudioUrl;
        this.wordAudioUrl = wordAudioUrl;
        this.itemListener = itemListener;
    }


    @NonNull
    @Override
    public DownloadRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.recyclerview_download_items, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DownloadRecyclerAdapter.ViewHolder holder, int position) {
        itemPosition = (position + 1);

        String[] unitImgModel = unitImgUrl.get(dbNum - 1);

        dialogDownloadStarter(holder, position);
        holder.txtTv.setText(tvTitle(itemPosition));
        viewValueSetter(holder, unitImgModel, position);
    }

    private void dialogDownloadStarter(ViewHolder holder, int position) {
        holder.downCardView.setOnClickListener(view -> itemListener.itemClicked(position));
    }


    private String tvTitle(int unitNum) {
        return "Unit " + unitNum;
    }


    @Override
    public int getItemCount() {
        return 31;
    }

    private void viewValueSetter(ViewHolder holder, String[] unitImgModel, int position) {
        try {
            if (position != 30) {
                imageValueSetter(holder, unitImgModel, position);
            } else {
                lastItemValueSetter(holder);
            }
        } catch (Exception e) {
            Log.e("unitImgError", "" + e);
        }
    }

    private void imageValueSetter(ViewHolder holder, String[] unitImgModel, int position) {


        if (imgFile(unitImgModel, position).exists()) {
            Drawable imgDrawable = Drawable.createFromPath(imgFile(unitImgModel, position).toString());
            Glide.with(context)
                    .load(imgDrawable)
                    .placeholder(R.drawable.loadimg)
                    .error(R.drawable.loadimg)
                    .into(holder.imgTv);
        } else {
            Glide.with(context)
                    .load(unitImgModel)
                    .placeholder(R.drawable.loadimg)
                    .error(R.drawable.loadimg)
                    .into(holder.imgTv);
        }
    }

    private File imgFile(String[] unitImgModel, int position) {
        final File imageDir = new File(Environment.DIRECTORY_DOWNLOADS, File.separator + "4000 Essential Words");

        final File imgMainPath = new File("Image Files");
        final File unitImgPath = new File(imgMainPath, File.separator + "Unit Images");
        final File unitImgBookPath = new File(unitImgPath, File.separator + "Book_" + dbNum);

        final File imgName = new File(unitImgBookPath, File.separator + "." + new File(unitImgModel[position]).getName());

        return new File(Environment.getExternalStoragePublicDirectory(imageDir.toString()), imgName.toString());
    }

    private void lastItemValueSetter(ViewHolder holder) {
        imageValueSetter(holder);
    }

    private void imageValueSetter(ViewHolder holder) {
        holder.imgTv.setImageResource(R.drawable.download_icon_image);
    }

    private void textValueSetter(ViewHolder holder) {
        holder.txtTv.setText("Download All Units ");
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView txtTv;
        private final ImageView imgTv;
        private final CardView downCardView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            downCardView = itemView.findViewById(R.id.fragment_download_item_card_view);
            txtTv = itemView.findViewById(R.id.download_item_text_view_tv);
            imgTv = itemView.findViewById(R.id.download_item_image_tv);
        }
    }

}
