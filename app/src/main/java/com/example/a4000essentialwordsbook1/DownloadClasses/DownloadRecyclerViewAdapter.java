package com.example.a4000essentialwordsbook1.DownloadClasses;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.a4000essentialwordsbook1.DownloadClasses.DownloadInterfaces.DownloadInterface;
import com.example.a4000essentialwordsbook1.R;

import java.text.DecimalFormat;
import java.util.ArrayList;


public class DownloadRecyclerViewAdapter extends RecyclerView.Adapter<DownloadRecyclerViewAdapter.downloadHolder> {

    private final Context dwnContext;
    private final ArrayList<DownloadModel> downloadList;
    private final int byteToMB = 1000000;
    private final DownloadInterface downloadInterface;
    private final LayoutInflater inflater;
    private String whtDown;
    private SharedPreferences imgSizePreferences, audioPreferences;


    public DownloadRecyclerViewAdapter(Context context,
                                       DownloadInterface downloadInterface,
                                       ArrayList<DownloadModel> downloadList,
                                       String whtDown) {
        this.inflater = LayoutInflater.from(context);
        this.dwnContext = context;
        this.downloadInterface = downloadInterface;
        this.downloadList = downloadList;
        this.whtDown = whtDown;
    }

    @NonNull
    @Override
    public DownloadRecyclerViewAdapter.downloadHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.recyclervie_download_adapter, parent, false);
        return new downloadHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DownloadRecyclerViewAdapter.downloadHolder holder, int position) {
        viewValueSetter(holder, position);
        downloadInterFaces(holder, position);

    }


    private void downloadInterFaces(downloadHolder holder, int position) {
        imageDownloadInterface(holder, position);
        audioDownloadInterface(holder, position);
    }

    private void imageDownloadInterface(downloadHolder holder, int position) {
        if (hasAllImages(position)) {
            Toast.makeText(dwnContext, "All Images Has Been Downloaded", Toast.LENGTH_SHORT).show();
            imageInDownloadIsDone(holder);
            holder.imageRelativeLayout.setClickable(false);
        } else {
            holder.imageRelativeLayout.setOnClickListener(view -> {
                whtDown = "Image";
                downloadInterface.itemListener(position, "Image");
            });
        }
    }

    private void audioDownloadInterface(downloadHolder holder, int position) {
        if (hasAllAudio(position)) {
            audioInDownloadIsDone(holder);
            holder.audioRelativeLayout.setClickable(false);
        } else {
            holder.audioRelativeLayout.setOnClickListener(view -> {
                whtDown = "Audio";
                downloadInterface.itemListener(position, "Audio");
            });
        }
    }

    private boolean hasAllAudio(int position) {
        return getAudioCount(position) > 59;
    }

    private boolean hasAllImages(int position) {
        return getImgCount(position) > 629;
    }


    private void viewValueSetter(downloadHolder holder, int position) {
        holder.tvImageView.setImageResource(downloadList.get(position).getTvImage());

        holder.audioTextView.setText(downloadList.get(position).getAudioFileSize());
        holder.imageTextView.setText(downloadList.get(position).getImageFileSize());
        Log.d("audioTextView", "" + downloadList.get(position).getAudioFileSize());
        Log.d("imageTextView", "" + downloadList.get(position).getImageFileSize());

        preInitializeViewsValue(holder, position);
        normalImage(holder);
        //imageViewResourceSetter(holder, downStatus);
    }


    private void imageInDownload(downloadHolder holder) {
        Glide.with(dwnContext)
                .load(R.drawable.download_icon_gif_2)
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                .placeholder(R.drawable.download_icon_image)
                .error(R.drawable.download_icon_image)
                .into(imageView(holder));
    }

    private void imageInPause(downloadHolder holder) {
        Glide.with(dwnContext)
                .load(R.drawable.warning_icon)
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                .placeholder(R.drawable.download_icon_image)
                .error(R.drawable.download_icon_image)
                .into(imageView(holder));
    }

    private void imageInFailed(downloadHolder holder) {
        Glide.with(dwnContext)
                .load(R.drawable.failed_icon)
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                .placeholder(R.drawable.download_icon_image)
                .error(R.drawable.download_icon_image)
                .into(imageView(holder));
    }

    private void normalImage(downloadHolder holder) {
        Glide.with(dwnContext)
                .load(R.drawable.download_icon_image)
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                .placeholder(R.drawable.download_icon_image)
                .error(R.drawable.download_icon_image)
                .into(holder.imageImgView);

        Glide.with(dwnContext)
                .load(R.drawable.download_icon_image)
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                .placeholder(R.drawable.download_icon_image)
                .error(R.drawable.download_icon_image)
                .into(holder.audioImgView);
    }

    private void imageInDownloadIsDone(downloadHolder holder) {
        Glide.with(dwnContext)
                .load(R.drawable.green_tick_icon)
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                .placeholder(R.drawable.download_icon_image)
                .error(R.drawable.download_icon_image)
                .into(holder.imageImgView);
    }

    private void audioInDownloadIsDone(downloadHolder holder) {
        Glide.with(dwnContext)
                .load(R.drawable.green_tick_icon)
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                .placeholder(R.drawable.download_icon_image)
                .error(R.drawable.download_icon_image)
                .into(holder.audioImgView);
    }


    private ImageView imageView(downloadHolder holder) {
        if (whtDown.equalsIgnoreCase("Image")) {
            return holder.audioImgView;
        } else {
            return holder.imageImgView;
        }
    }









    private boolean hasAllPermission(){
        return hasReadPermission() && hasWritePermission() && hasInternetAccess();
    }
    private boolean hasReadPermission(){
        return ContextCompat.checkSelfPermission(dwnContext, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;
    }

    private boolean hasWritePermission() {
        return ContextCompat.checkSelfPermission(dwnContext, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;
    }

    private boolean hasInternetAccess() {
        return ContextCompat.checkSelfPermission(dwnContext, Manifest.permission.INTERNET) == PackageManager.PERMISSION_GRANTED;
    }

    private float getImgSize(int position) {
        imgSizePreferences = dwnContext.getSharedPreferences("image_size_" + dbNumber(position), Context.MODE_PRIVATE);
        return imgSizePreferences.getFloat("Img_Size", 0f);
    }

    private float getAudioSize(int position) {
        audioPreferences = dwnContext.getSharedPreferences("audio_size_" + dbNumber(position), Context.MODE_PRIVATE);
        return audioPreferences.getFloat("Audio_Size", 0);
    }

    private int getAudioCount(int position) {
        imgSizePreferences = dwnContext.getSharedPreferences("audio_size_" + dbNumber(position), Context.MODE_PRIVATE);
        return imgSizePreferences.getInt("Audio_Num", 0);
    }

    private int getImgCount(int position) {
        audioPreferences = dwnContext.getSharedPreferences("image_size_" + dbNumber(position), Context.MODE_PRIVATE);
        return audioPreferences.getInt("Img_Num", 0);
    }


    private void setImgSize(downloadHolder holder, float size, int position) {
        imgSizePreferences = dwnContext.getSharedPreferences("image_size_" + dbNumber(position), Context.MODE_PRIVATE);
        SharedPreferences.Editor sizeEdit = imgSizePreferences.edit();
        sizeEdit.putFloat("Img_Size", size);
        sizeEdit.apply();
    }


    private void setImgSize(float size, int position) {
        imgSizePreferences = dwnContext.getSharedPreferences("image_size_" + dbNumber(position), Context.MODE_PRIVATE);
        SharedPreferences.Editor sizeEdit = imgSizePreferences.edit();
        sizeEdit.putFloat("Img_Size", size);
        sizeEdit.apply();
    }

    private void setAudioSize(float size, int position) {
        audioPreferences = dwnContext.getSharedPreferences("audio_size_" + dbNumber(position), Context.MODE_PRIVATE);
        SharedPreferences.Editor sizeEdit = audioPreferences.edit();
        sizeEdit.putFloat("Audio_Size", size);
        sizeEdit.apply();
    }


    private void preInitializeViewsValue(downloadHolder holder, int position) {
        //final int position = (getLayoutPosition() > -1) ? getLayoutPosition() : 0;
        holder.audioRemainTxtView.setText(audioSeizeDeci(position));
        holder.imageRemainTxtView.setText(imgSizeDeci(position));
        Log.d("imgSizeDeci", "" + imgSizeDeci(position));
        Log.d("audioSeizeDeci", "" + audioSeizeDeci(position));
    }


    private String imgSizeDeci(int position) {
        DecimalFormat deci = new DecimalFormat("#.#");
        final float imgSize = downloadList.get(position).getDefaultImgSize() / byteToMB;
        return (deci.format(imgSize));
    }

    private String audioSeizeDeci(int position) {
        DecimalFormat deci = new DecimalFormat("#.#");
        final float imgSize = downloadList.get(position).getDefaultAudioSize() / byteToMB;
        return (deci.format(imgSize));
    }


    private void preInitializedViews(downloadHolder holder, int position) {
        DecimalFormat deci = new DecimalFormat("#.##");
        final float imgSize = getImgSize(position) / byteToMB;
        final float audioSize = getAudioSize(position);
        final String imgOutPut = (deci.format(imgSize));
        final String audioOutPut = (deci.format(audioSize));
        holder.imageRemainTxtView.setText(imgOutPut);
        holder.audioRemainTxtView.setText(audioOutPut);
    }


    private int dbNumber(int position) {
        return (position + 1);
    }


    @Override
    public int getItemCount() {
        return downloadList.size();
    }

    @SuppressLint("Range")
    public static class downloadHolder extends RecyclerView.ViewHolder {

        private final ImageView tvImageView;
        private final ImageView imageImgView;
        private final ImageView audioImgView;
        private final TextView audioTextView;
        private final TextView imageTextView;
        private final TextView audioRemainTxtView;
        private final TextView imageRemainTxtView;
        private final RelativeLayout audioRelativeLayout;
        private final RelativeLayout imageRelativeLayout;


        public downloadHolder(@NonNull View itemView) {
            super(itemView);

            tvImageView = itemView.findViewById(R.id.download_tv_image_view);
            audioImgView = itemView.findViewById(R.id.download_audio_circle_image_view);
            audioTextView = itemView.findViewById(R.id.audio_total_download_size_text_view);
            imageImgView = itemView.findViewById(R.id.download_images_circle_image_view);
            imageTextView = itemView.findViewById(R.id.image_total_download_size_text_view);

            audioRemainTxtView = itemView.findViewById(R.id.audio_downloading_remain_text_view);
            imageRemainTxtView = itemView.findViewById(R.id.image_downloading_remain_text_view);


            audioRelativeLayout = itemView.findViewById(R.id.audio_download_relative_layout);
            imageRelativeLayout = itemView.findViewById(R.id.download_image_relative_layout);
        }
    }

}
