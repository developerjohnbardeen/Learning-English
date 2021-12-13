package com.example.a4000essentialwordsbook1.DownloadClasses;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
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
import com.example.a4000essentialwordsbook1.DataBases.UnitBookDatabases.UnitDatabaseBookFive;
import com.example.a4000essentialwordsbook1.DataBases.UnitBookDatabases.UnitDatabaseBookFour;
import com.example.a4000essentialwordsbook1.DataBases.UnitBookDatabases.UnitDatabaseBookOne;
import com.example.a4000essentialwordsbook1.DataBases.UnitBookDatabases.UnitDatabaseBookSix;
import com.example.a4000essentialwordsbook1.DataBases.UnitBookDatabases.UnitDatabaseBookThree;
import com.example.a4000essentialwordsbook1.DataBases.UnitBookDatabases.UnitDatabaseBookTwo;
import com.example.a4000essentialwordsbook1.DataBases.WordBookDatabases.WordDatabaseBookFive;
import com.example.a4000essentialwordsbook1.DataBases.WordBookDatabases.WordDatabaseBookFour;
import com.example.a4000essentialwordsbook1.DataBases.WordBookDatabases.WordDatabaseBookOne;
import com.example.a4000essentialwordsbook1.DataBases.WordBookDatabases.WordDatabaseBookSix;
import com.example.a4000essentialwordsbook1.DataBases.WordBookDatabases.WordDatabaseBookThree;
import com.example.a4000essentialwordsbook1.DataBases.WordBookDatabases.WordDatabaseBookTwo;
import com.example.a4000essentialwordsbook1.DownloadClasses.DownloadInterfaces.DownloadInterface;
import com.example.a4000essentialwordsbook1.DownloadClasses.DownloadInterfaces.DownloadSizeListener;
import com.example.a4000essentialwordsbook1.DownloadClasses.DownloadInterfaces.PermissionListener;
import com.example.a4000essentialwordsbook1.R;
import com.example.a4000essentialwordsbook1.StringNote.DB_NOTES.DB_NOTES;

import java.io.File;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class DownloadRecyclerViewAdapter extends RecyclerView.Adapter<DownloadRecyclerViewAdapter.downloadHolder>{

    private final Context dwnContext;
    private final DownloadInterface downloadInterface;
    private final DownloadSizeListener downloadSizeListener;
    private final PermissionListener permissionListener;
    private final ArrayList<DownloadModel> modelList;
    private final LayoutInflater inflater;
    public downloadHolder downloadHolder;




    public DownloadRecyclerViewAdapter(Context context, ArrayList<DownloadModel> modelList,
                                       DownloadInterface downloadInterface,
                                       PermissionListener permissionListener, DownloadSizeListener downloadSizeListener){
        this.dwnContext = context;
        this.downloadInterface = downloadInterface;
        this.permissionListener = permissionListener;
        this.downloadSizeListener = downloadSizeListener;
        this.modelList = modelList;
        this.inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public DownloadRecyclerViewAdapter.downloadHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.recyclervie_download_adapter, parent, false);
        return new downloadHolder(view, dwnContext, modelList, downloadInterface, permissionListener, downloadSizeListener);
    }

    @Override
    public void onBindViewHolder(@NonNull DownloadRecyclerViewAdapter.downloadHolder holder, int position) {
        viewValueSetter(holder, position);
    }

    private void viewValueSetter(downloadHolder holder, int position) {
        holder.tvImageView.setImageResource(modelList.get(position).getTvImage());


        holder.tvImageView.setOnClickListener(v -> {
            downloadImagesFunctions(holder, position + 1);
            Toast.makeText(dwnContext, "Download just began!", Toast.LENGTH_SHORT).show();
        });

        holder.audioTextView.setText(modelList.get(position).getAudioFileSize());
        holder.imageTextView.setText(modelList.get(position).getImageFileSize());

    }




    private void downloadImagesFunctions(downloadHolder holder, int position){
        if (hasAllPermission()) {
            ExecutorService imageThread = Executors.newSingleThreadExecutor();
            imageThread.execute(() -> {
                wordImageListDataReceiver(position);
                wordImageDownloader(holder, dwnContext, position, wordImageList);

            });
        }else {
            permissionListener.requestForPermissions();
        }
    }

    @SuppressLint("Range")
    private void wordImageListDataReceiver(int position){
        int index = 0;

        SQLiteDatabase db = wordListDatabase(dwnContext, position).getReadableDatabase();
        Cursor cursor = null;

        for (int unitNum = 1 ; unitNum <= 30 ; unitNum ++) {

            cursor = db.query(DB_NOTES.NEUTRAL_WORD_TABLE + unitNum,
                    new String[]{DB_NOTES.WORD_ID, DB_NOTES.WORD_IMG, DB_NOTES.WORD, DB_NOTES.PHONETIC_WORD, DB_NOTES.TRANSLATE_WORD,
                            DB_NOTES.UNIT_NUMBER,
                            DB_NOTES.DEFINITION_WORD, DB_NOTES.EXAMPLE_WORD, DB_NOTES.EXAMPLE_TRANSLATE_WORD, DB_NOTES.AUDIO_WORD},
                    null, null, null, null, null);


            if (cursor != null && cursor.getCount() != 0) {
                while (cursor.moveToNext()) {
                    final String wordImgUrl = cursor.getString(cursor.getColumnIndex(DB_NOTES.WORD_IMG));
                    final int unitNumber = cursor.getInt(cursor.getColumnIndex(DB_NOTES.UNIT_NUMBER));
                    wordImageList[index] = wordImgUrl;
                    wordUnitNumList[index] = unitNumber;
                    index++;
                }
            }
        }
        assert cursor != null;
        cursor.close();
        db.close();
    }
    private SQLiteOpenHelper wordListDatabase(Context context, int databaseNum){
        if (databaseNum == 1){
            return new WordDatabaseBookOne(context);
        }else if (databaseNum == 2){
            return new WordDatabaseBookTwo(context);
        }else if (databaseNum == 3){
            return new WordDatabaseBookThree(context);
        }else if (databaseNum == 4){
            return new WordDatabaseBookFour(context);
        }else if (databaseNum == 5){
            return new WordDatabaseBookFive(context);
        }else {
            return new WordDatabaseBookSix(context);
        }
    }

    @SuppressLint("Range")
    private void wordImageDownloader(downloadHolder holder, Context context, int position, String[] imgUrlList){
        final String appPath = context.getApplicationInfo().dataDir;
        Handler wrdImgRunOnUiThread = new Handler(Looper.getMainLooper());

        final File imgMainPath = new File("Image Files");
        final File wordImgPath = new File(imgMainPath, File.separator + "Word Images");
        final File wordImgBookPath = new File(wordImgPath, File.separator + "Book_" + position);


        //wrdImgCount
        for (int index = 0 ; index < 20 ; index++) {
            final File wordUnitImgBookPath = new File(wordImgBookPath, File.separator + "Unit_" + wordUnitNumList[index]);
            Uri downloadUri = Uri.parse(imgUrlList[index]);

            final File secondSubFile = new File(wordUnitImgBookPath, File.separator + "." + new File(imgUrlList[index]).getName());
            final File file = new File(Environment.getExternalStoragePublicDirectory(appPath) , secondSubFile.toString());


            final boolean imgExists = file.exists();
            if (!imgExists) {

                DownloadManager.Request wrdImageRequest = new DownloadManager.Request(downloadUri);
                File path = new File(imgUrlList[index]);
                String imageName = path.getName();
                wrdImageRequest.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI | DownloadManager.Request.NETWORK_MOBILE)
                        .setAllowedOverRoaming(true)
                        .setTitle(imageName)
                        .setNotificationVisibility(DownloadManager.Request.VISIBILITY_HIDDEN)
                        .setDestinationInExternalPublicDir(appPath, secondSubFile.toString());


                DownloadManager wrdImgDownloadManager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);

                wrdImgDownloadId = wrdImgDownloadManager.enqueue(wrdImageRequest);
                context.registerReceiver(onDownloadCompleted(holder), new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));
                boolean downloading = true;

                while (downloading){
                    DownloadManager.Query wrdImgDQ = new DownloadManager.Query();
                    wrdImgDQ.setFilterById(wrdImgDownloadId);
                    Cursor cursor = wrdImgDownloadManager.query(wrdImgDQ);
                    cursor.moveToFirst();

                    int downloadStatus = cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_STATUS));
                    float soFarSize = cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_BYTES_DOWNLOADED_SO_FAR));
                    float localSize = getImgSize(holder.dbNumber());



                    if (downloadStatus == DownloadManager.STATUS_SUCCESSFUL){
                        downloading = false;
                    } else if (downloadStatus == DownloadManager.STATUS_FAILED) {
                        downloading = false;
                    }else if (downloadStatus == DownloadManager.STATUS_RUNNING){
                    }else if (downloadStatus == DownloadManager.STATUS_PAUSED){}

                    localSize += soFarSize;

                    DecimalFormat deci = new DecimalFormat("#.##");
                    final String outPut = (deci.format(localSize / byteToMB));
                    wrdImgRunOnUiThread.post(() -> holder.imageRemainTxtView.setText(outPut));

                    setImgSize(holder, localSize);
                    cursor.close();

                }

            }
        }
    }







    private BroadcastReceiver onDownloadCompleted (downloadHolder holder){
        return new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                final long dmID = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1);
                if (wrdImgDownloadId == dmID){

                    downloadSizeListener.downloadedSize(getImgSize(holder.dbNumber()));
                    try {
                        DecimalFormat deci = new DecimalFormat("#.##");
                        final String outPut = (deci.format(getImgSize(holder.dbNumber()) / byteToMB));
                        holder.imageRemainTxtView.setText(outPut);
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }
        };
    }


    private boolean hasAllPermission(){
        return hasReadPermission() && hasWritePermission() && hasInternetAccess();
    }
    private boolean hasReadPermission(){
        return ContextCompat.checkSelfPermission(dwnContext, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;
    }
    private boolean hasWritePermission(){
        return ContextCompat.checkSelfPermission(dwnContext, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;
    }
    private boolean hasInternetAccess(){
        return ContextCompat.checkSelfPermission(dwnContext, Manifest.permission.INTERNET) == PackageManager.PERMISSION_GRANTED;
    }

    private float getImgSize(int position){
        imgSizePreferences = dwnContext.getSharedPreferences("image_size_" + position,Context.MODE_PRIVATE);
        return imgSizePreferences.getFloat("Img_Size", 0);
    }
    private void setImgSize(downloadHolder holder, float size){
        imgSizePreferences = dwnContext.getSharedPreferences("image_size_" + holder.dbNumber(), Context.MODE_PRIVATE);
        SharedPreferences.Editor sizeEdit = imgSizePreferences.edit();
        sizeEdit.putFloat("Img_Size", size);
        sizeEdit.apply();
    }









    private SharedPreferences imgSizePreferences;
    private long wrdImgDownloadId;
    private final int byteToMB = 1000000;
    private final int wrdImgCount = 600;
    private final String[] wordImageList = new String[wrdImgCount];
    private final int[] wordUnitNumList = new int[wrdImgCount];

    @Override
    public int getItemCount() {
        return modelList.size();
    }

    @SuppressLint("Range")
    public static class downloadHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final DownloadSizeListener downloadSizeListener;
        private final PermissionListener permissionListener;
        private final Context dwnContext;
        private final ImageView tvImageView;
        private final ImageView imageImgView;
        private final TextView audioTextView;
        private final TextView imageTextView;
        private final TextView audioRemainTxtView;
        private final TextView imageRemainTxtView;
        private final ArrayList<DownloadModel> modelList;
        private final RelativeLayout audioRelativeLayout;
        private final RelativeLayout imageRelativeLayout;
        private final int wrdImgCount = 600;
        private final int byteToMB = 1000000;
        private float audioSize;
        private SharedPreferences imgSizePreferences, audioPreferences;
        private final String[] wordAudioList = new String[30];
        private final String[] unitAudioList = new String[30];
        private final String[] wordImageList = new String[wrdImgCount];
        private final int[] wordUnitNumList = new int[wrdImgCount];
        private final String[] storyImageList = new String[30];
        private long wrdImgDownloadId;


        public downloadHolder(@NonNull View itemView,
                              Context dwnContext,
                              ArrayList<DownloadModel> modelList,
                              DownloadInterface downloadInterface,
                              PermissionListener permissionListener,
                              DownloadSizeListener downloadSizeListener) {
            super(itemView);
            this.permissionListener = permissionListener;
            this.downloadSizeListener = downloadSizeListener;
            this.dwnContext = dwnContext;
            this.modelList = modelList;
            tvImageView = itemView.findViewById(R.id.download_tv_image_view);
            ImageView audioImgView = itemView.findViewById(R.id.download_audio_circle_image_view);
            audioTextView = itemView.findViewById(R.id.audio_total_download_size_text_view);
            imageImgView = itemView.findViewById(R.id.download_images_circle_image_view);
            imageTextView = itemView.findViewById(R.id.image_total_download_size_text_view);
            audioRemainTxtView = itemView.findViewById(R.id.audio_downloading_remain_text_view);
            imageRemainTxtView = itemView.findViewById(R.id.image_downloading_remain_text_view);
            audioRelativeLayout = itemView.findViewById(R.id.audio_download_relative_layout);
            imageRelativeLayout = itemView.findViewById(R.id.download_image_relative_layout);

            preInitializedViews();
            layoutClickListener();
            preInitializeViewsValue();
        }

        private void preInitializedViews(){
            DecimalFormat deci = new DecimalFormat("#.##");
            final float size = getImgSize() / byteToMB;
            final String outPut = (deci.format(size));
            imageRemainTxtView.setText(outPut);
            Log.d("outPutValue", outPut);
            Log.d("txtViewValue", imageRemainTxtView.getText().toString());
            Log.d("imgSizeValue", String.valueOf(getImgSize()));
        }
        private void layoutClickListener(){
            audioRelativeLayout.setOnClickListener(this);
            imageRelativeLayout.setOnClickListener(this);
        }
        private int itemPosition(){return getAbsoluteAdapterPosition();}
        private int dbNumber(){return (itemPosition() + 1);}


        private void imageInDownload(Context context){
            Glide.with(context)
                        .load(R.drawable.download_icon_gif_2)
                        .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                        .placeholder(R.drawable.download_icon_image)
                        .error(R.drawable.download_icon_image)
                        .into(imageImgView);
        }
        private void normalImage(Context context){
            Glide.with(context)
                        .load(R.drawable.download_icon_image)
                        .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                        .placeholder(R.drawable.download_icon_image)
                        .error(R.drawable.download_icon_image)
                        .into(imageImgView);
        }
        private void imageInDownloadIsDone(Context context){
            Glide.with(context)
                        .load(R.drawable.green_tick_icon)
                        .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                        .placeholder(R.drawable.download_icon_image)
                        .error(R.drawable.download_icon_image)
                        .into(imageImgView);
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case (R.id.audio_download_relative_layout):
                    //downloadInterface.audioDownload(getAbsoluteAdapterPosition());
                    downloadAudiosFunctions();
                    break;
                case (R.id.download_image_relative_layout):
                    //downloadInterface.imageDownload(getAbsoluteAdapterPosition());
                    downloadImagesFunctions();
                    break;
            }
        }

        private void downloadAudiosFunctions(){
            if (hasAllPermission()) {
                ExecutorService audioThread = Executors.newSingleThreadExecutor();
                audioThread.execute(() -> {
                    AudiosListReceiver(dbNumber());
                    wordAudioDownloader(dwnContext, wordAudioList);
                    unitAudioDownloader(dwnContext, unitAudioList);
                });
            }else {
                permissionListener.requestForPermissions();
            }
        }
        private void wordAudioDownloader(Context context, String[] wordAudioUrlList){
            final String appPath = context.getApplicationInfo().dataDir;
            Handler runOnUiThread = new Handler(Looper.getMainLooper());


            final File audioMainPath = new File("Audio Files");
            final File audioUnitPath = new File(audioMainPath, File.separator + "Word Audios");
            final File audioWordBookPath = new File(audioUnitPath, File.separator + "Book_" + 1);

            for (int index = 0 ; index < 30 ; index++) {
                final File secondSubFile = new File(audioWordBookPath, File.separator + "." + new File(wordAudioUrlList[index]).getName());
                final File file = new File(Environment.getExternalStoragePublicDirectory(appPath) , secondSubFile.toString());
                final boolean imgExists = file.exists();
                if (!imgExists) {
                    Uri downloadUri = Uri.parse(wordAudioUrlList[index]);

                    DownloadManager.Request request = new DownloadManager.Request(downloadUri);
                    File path = new File(wordAudioUrlList[index]);
                    String imageName = path.getName();
                    request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI | DownloadManager.Request.NETWORK_MOBILE)
                            .setAllowedOverRoaming(true)
                            .setTitle(imageName)
                            .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
                            .setDestinationInExternalPublicDir(appPath, secondSubFile.toString());


                    DownloadManager dm = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);

                    final long downloadId = dm.enqueue(request);
                    ExecutorService thread = Executors.newSingleThreadExecutor();
                    thread.execute(() ->{

                        while (true){
                            DownloadManager.Query dq = new DownloadManager.Query();
                            dq.setFilterById(downloadId);

                            Cursor cursor = dm.query(dq);
                            cursor.moveToFirst();
                            int downloadedSize = cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_BYTES_DOWNLOADED_SO_FAR));

                            int downloadStatus = cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_STATUS));

                            if (downloadStatus == DownloadManager.STATUS_SUCCESSFUL){

                                break;

                            } else if (downloadStatus == DownloadManager.STATUS_FAILED) {
                                break;

                            }else if (downloadStatus == DownloadManager.STATUS_RUNNING){
                                //update UI textView
                                new Handler(Looper.getMainLooper()).post(() ->{
                                    audioSize += downloadedSize;
                                    audioRemainTxtView.setText(String.valueOf(audioSize));
                                });

                            }else if (downloadStatus == DownloadManager.STATUS_PAUSED){

                            }


                            runOnUiThread.post(() ->{});
                            cursor.close();

                        }
                    });
                }
            }
        }
        private void unitAudioDownloader(Context context, String[] unitAudioUrlList){
            final String appPath = context.getApplicationInfo().dataDir;
            Handler runOnUiThread = new Handler(Looper.getMainLooper());


            final File audioMainPath = new File("Audio Files");
            final File audioUnitPath = new File(audioMainPath, File.separator + "Unit Audios");
            final File audioWordBookPath = new File(audioUnitPath, File.separator + "Book_" + dbNumber());

            for (int index = 0 ; index < 30 ; index++) {
                final File secondSubFile = new File(audioWordBookPath, File.separator + "." + new File(unitAudioUrlList[index]).getName());
                final File file = new File(Environment.getExternalStoragePublicDirectory(appPath) , secondSubFile.toString());
                final boolean imgExists = file.exists();
                if (!imgExists) {
                    Uri downloadUri = Uri.parse(unitAudioUrlList[index]);

                    DownloadManager.Request request = new DownloadManager.Request(downloadUri);
                    File path = new File(unitAudioUrlList[index]);
                    String imageName = path.getName();
                    request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI | DownloadManager.Request.NETWORK_MOBILE)
                            .setAllowedOverRoaming(true)
                            .setTitle(imageName)
                            .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
                            .setDestinationInExternalPublicDir(appPath, secondSubFile.toString());


                    DownloadManager dm = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);

                    final long downloadId = dm.enqueue(request);
                    ExecutorService thread = Executors.newSingleThreadExecutor();
                    thread.execute(() ->{

                        while (true){
                            DownloadManager.Query dq = new DownloadManager.Query();
                            dq.setFilterById(downloadId);

                            Cursor cursor = dm.query(dq);
                            cursor.moveToFirst();

                            int downloadStatus = cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_STATUS));
                            int downloadedSize = cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_BYTES_DOWNLOADED_SO_FAR));


                            if (downloadStatus == DownloadManager.STATUS_SUCCESSFUL){

                                break;

                            } else if (downloadStatus == DownloadManager.STATUS_FAILED) {
                                break;

                            }else if (downloadStatus == DownloadManager.STATUS_RUNNING){
                                audioSize += downloadedSize;
                                runOnUiThread.post(() -> audioRemainTxtView.setText(String.valueOf(audioSize)));

                            }else if (downloadStatus == DownloadManager.STATUS_PAUSED){

                            }
                            cursor.close();

                        }
                    });
                }
            }
        }



        private void downloadImagesFunctions(){
            if (hasAllPermission()) {
                ExecutorService imageThread = Executors.newSingleThreadExecutor();
                imageThread.execute(() -> {
                    wordImageListDataReceiver();
                    unitImageListReceiver(dbNumber());
                    wordImageDownloader(dwnContext, wordImageList);
                    unitImageDownloader(dwnContext, storyImageList);

                });
            }else {
                permissionListener.requestForPermissions();
            }
        }


        private void wordImageDownloader(Context context, String[] imgUrlList){
            final String appPath = context.getApplicationInfo().dataDir;
            Handler wrdImgRunOnUiThread = new Handler(Looper.getMainLooper());

            final File imgMainPath = new File("Image Files");
            final File wordImgPath = new File(imgMainPath, File.separator + "Word Images");
            final File wordImgBookPath = new File(wordImgPath, File.separator + "Book_" + dbNumber());


            //wrdImgCount
            for (int index = 0 ; index < 20 ; index++) {
                final File wordUnitImgBookPath = new File(wordImgBookPath, File.separator + "Unit_" + wordUnitNumList[index]);
                Uri downloadUri = Uri.parse(imgUrlList[index]);

                final File secondSubFile = new File(wordUnitImgBookPath, File.separator + "." + new File(imgUrlList[index]).getName());
                final File file = new File(Environment.getExternalStoragePublicDirectory(appPath) , secondSubFile.toString());


                final boolean imgExists = file.exists();
                if (!imgExists) {

                    DownloadManager.Request wrdImageRequest = new DownloadManager.Request(downloadUri);
                    File path = new File(imgUrlList[index]);
                    String imageName = path.getName();
                    wrdImageRequest.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI | DownloadManager.Request.NETWORK_MOBILE)
                            .setAllowedOverRoaming(true)
                            .setTitle(imageName)
                            .setNotificationVisibility(DownloadManager.Request.VISIBILITY_HIDDEN)
                            .setDestinationInExternalPublicDir(appPath, secondSubFile.toString());


                    DownloadManager wrdImgDownloadManager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);

                    wrdImgDownloadId = wrdImgDownloadManager.enqueue(wrdImageRequest);
                    context.registerReceiver(onDownloadCompleted, new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));
                    boolean downloading = true;

                    while (downloading){
                        DownloadManager.Query wrdImgDQ = new DownloadManager.Query();
                        wrdImgDQ.setFilterById(wrdImgDownloadId);
                        Cursor cursor = wrdImgDownloadManager.query(wrdImgDQ);
                        cursor.moveToFirst();

                        int downloadStatus = cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_STATUS));
                        float soFarSize = cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_BYTES_DOWNLOADED_SO_FAR));
                        float localSize = getImgSize();



                        if (downloadStatus == DownloadManager.STATUS_SUCCESSFUL){
                            downloading = false;
                        } else if (downloadStatus == DownloadManager.STATUS_FAILED) {
                            downloading = false;
                        }else if (downloadStatus == DownloadManager.STATUS_RUNNING){
                        }else if (downloadStatus == DownloadManager.STATUS_PAUSED){}

                        localSize += soFarSize;
                        Log.d("downloadSize","downloadSize is : " + localSize);
                        DecimalFormat deci = new DecimalFormat("#.##");
                        final String outPut = (deci.format(localSize / byteToMB));
                        wrdImgRunOnUiThread.post(() -> imageRemainTxtView.setText(outPut));

                        setImgSize(localSize);
                        cursor.close();

                    }

                }
            }
        }
        private void unitImageDownloader(Context context, String[] unitImgUrlList){
            final String appPath = context.getApplicationInfo().dataDir;
            Handler runOnUiThread = new Handler(Looper.getMainLooper());

            final File imgMainPath = new File("Image Files");
            final File wordImgPath = new File(imgMainPath, File.separator + "Unit Images");
            final File unitImgBookPath = new File(wordImgPath, File.separator + "Book_" + dbNumber());


            for (int index = 0 ; index < 30 ; index++) {
                final File secondSubFile = new File(unitImgBookPath, File.separator + "." + new File(unitImgUrlList[index]).getName());
                final File file = new File(Environment.getExternalStoragePublicDirectory(appPath) , secondSubFile.toString());
                final boolean imgExists = file.exists();
                if (!imgExists) {
                    Uri downloadUri = Uri.parse(unitImgUrlList[index]);

                    DownloadManager.Request request = new DownloadManager.Request(downloadUri);
                    File path = new File(unitImgUrlList[index]);
                    String imageName = path.getName();
                    request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI | DownloadManager.Request.NETWORK_MOBILE)
                            .setAllowedOverRoaming(true)
                            .setTitle(imageName)
                            //.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
                            .setDestinationInExternalPublicDir(appPath, secondSubFile.toString());


                    DownloadManager dm = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);


                    final long downloadId = dm.enqueue(request);
                    boolean downloading = true;
                    while (downloading){
                        DownloadManager.Query dq = new DownloadManager.Query();
                        dq.setFilterById(downloadId);

                        Cursor cursor = dm.query(dq);
                        cursor.moveToFirst();

                        int downloadStatus = cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_STATUS));
                        float soFarSize = cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_BYTES_DOWNLOADED_SO_FAR));
                        float localSize = getImgSize();


                        if (downloadStatus == DownloadManager.STATUS_SUCCESSFUL){
                            downloading = false;

                        } else if (downloadStatus == DownloadManager.STATUS_FAILED) {
                            downloading = false;

                        }else if (downloadStatus == DownloadManager.STATUS_RUNNING){

                        }else if (downloadStatus == DownloadManager.STATUS_PAUSED){

                        }


                        localSize += soFarSize;
                        Log.d("downloadSize", " downloadSize is : " + localSize);
                        final DecimalFormat deci = new DecimalFormat("#.##");
                        final String outPut = deci.format(localSize / byteToMB);
                        runOnUiThread.post(() -> imageRemainTxtView.setText(outPut));

                        setImgSize(localSize);
                        cursor.close();

                    }


                }

            }
        }






        private void wordImageListDataReceiver(){
            int index = 0;

            SQLiteDatabase db = wordListDatabase(dwnContext, dbNumber()).getReadableDatabase();
            Cursor cursor = null;

            for (int unitNum = 1 ; unitNum <= 30 ; unitNum ++) {

                cursor = db.query(DB_NOTES.NEUTRAL_WORD_TABLE + unitNum,
                        new String[]{DB_NOTES.WORD_ID, DB_NOTES.WORD_IMG, DB_NOTES.WORD, DB_NOTES.PHONETIC_WORD, DB_NOTES.TRANSLATE_WORD,
                                DB_NOTES.UNIT_NUMBER,
                                DB_NOTES.DEFINITION_WORD, DB_NOTES.EXAMPLE_WORD, DB_NOTES.EXAMPLE_TRANSLATE_WORD, DB_NOTES.AUDIO_WORD},
                        null, null, null, null, null);


                if (cursor != null && cursor.getCount() != 0) {
                    while (cursor.moveToNext()) {
                        final String wordImgUrl = cursor.getString(cursor.getColumnIndex(DB_NOTES.WORD_IMG));
                        final int unitNumber = cursor.getInt(cursor.getColumnIndex(DB_NOTES.UNIT_NUMBER));
                        wordImageList[index] = wordImgUrl;
                        wordUnitNumList[index] = unitNumber;
                        index++;
                    }
                }
            }
            assert cursor != null;
            cursor.close();
            db.close();
        }
        private SQLiteOpenHelper wordListDatabase(Context context, int databaseNum){
            if (databaseNum == 1){
                return new WordDatabaseBookOne(context);
            }else if (databaseNum == 2){
                return new WordDatabaseBookTwo(context);
            }else if (databaseNum == 3){
                return new WordDatabaseBookThree(context);
            }else if (databaseNum == 4){
                return new WordDatabaseBookFour(context);
            }else if (databaseNum == 5){
                return new WordDatabaseBookFive(context);
            }else {
                return new WordDatabaseBookSix(context);
            }
        }

        private void AudiosListReceiver(int dbNumber){
            SQLiteDatabase db = unitListDatabase(dwnContext, dbNumber).getReadableDatabase();
            int index = 0;

            Cursor awCursor = db.query(DB_NOTES.UNIT_TABLE,
                    new String[]{DB_NOTES.UNIT_COMPLETE_WORD_AUDIO},
                    null, null,
                    null, null, null);

            if (awCursor != null && awCursor.getCount() != 0){
                while (awCursor.moveToNext()){
                    wordAudioList[index] = awCursor.getString(awCursor.getColumnIndex(DB_NOTES.UNIT_COMPLETE_WORD_AUDIO));
                    unitAudioList[index] = awCursor.getString(awCursor.getColumnIndex(DB_NOTES.UNIT_AUDIO));
                    index++;
                }
            }

            assert awCursor != null;
            awCursor.close();
            db.close();
        }
        private void unitImageListReceiver(int dbNumber){
            SQLiteDatabase db = unitListDatabase(dwnContext, dbNumber).getReadableDatabase();
            int index = 0;

            Cursor awCursor = db.query(DB_NOTES.UNIT_TABLE,
                    new String[]{DB_NOTES.UNIT_IMG},
                    null,null,null,null,null);

            if (awCursor != null && awCursor.getCount() != 0){
                while (awCursor.moveToNext()){
                    storyImageList[index] = awCursor.getString(awCursor.getColumnIndex(DB_NOTES.UNIT_IMG));
                    index++;
                }
            }

            assert awCursor != null;
            awCursor.close();
            db.close();
        }
        private SQLiteOpenHelper unitListDatabase(Context context, int unitId){
            if (unitId == 1){
                return new UnitDatabaseBookOne(context);
            }else if (unitId == 2){
                return new UnitDatabaseBookTwo(context);
            }else if (unitId == 3){
                return new UnitDatabaseBookThree(context);
            }else if (unitId == 4){
                return new UnitDatabaseBookFour(context);
            }else if (unitId == 5){
                return new UnitDatabaseBookFive(context);
            }else {
                return new UnitDatabaseBookSix(context);
            }
        }


        private void setImgSize(float size){
            imgSizePreferences = dwnContext.getSharedPreferences("image_size_" + dbNumber(), Context.MODE_PRIVATE);
            SharedPreferences.Editor sizeEdit = imgSizePreferences.edit();
            sizeEdit.putFloat("Img_Size", size);
            sizeEdit.apply();
        }
        private void setAudioSize(float size){
            audioPreferences = dwnContext.getSharedPreferences("audio_size_" + dbNumber() , Context.MODE_PRIVATE);
            SharedPreferences.Editor sizeEdit = audioPreferences.edit();
            sizeEdit.putFloat("Audio_Size", size);
            sizeEdit.apply();
        }

        private float getImgSize(){
            imgSizePreferences = dwnContext.getSharedPreferences("image_size_" + dbNumber(),Context.MODE_PRIVATE);
            return imgSizePreferences.getFloat("Img_Size", 0);
        }
        private float getAudioSize(){
            audioPreferences = dwnContext.getSharedPreferences("audio_size_" + dbNumber(), Context.MODE_PRIVATE);
            return audioPreferences.getFloat("Audio_Size", 0);
        }

        private final BroadcastReceiver onDownloadCompleted = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

                final long dmID = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1);
                if (wrdImgDownloadId == dmID){
                    //Toast.makeText(context, "Download Completed ---> " + getImgSize(), Toast.LENGTH_SHORT).show();
                    downloadSizeListener.downloadedSize(getImgSize());
                    try {
                        DecimalFormat deci = new DecimalFormat("#.##");
                        final String outPut = (deci.format(getImgSize() / byteToMB));
                        imageRemainTxtView.setText(outPut);
                        Log.d("update", outPut);
                    }catch (Exception e){
                        Log.i("updateTextView", "" + e);
                    }
                }

            }
        };

        private void preInitializeViewsValue(){
            audioRemainTxtView.setText(String.valueOf(modelList.get(getBindingAdapterPosition()).getDefaultAudioSize()));
            imageRemainTxtView.setText(String.valueOf(modelList.get(getBindingAdapterPosition()).getDefaultImgSize()));
        }

        private boolean hasAllPermission(){
            return hasReadPermission() && hasWritePermission() && hasInternetAccess();
        }
        private boolean hasReadPermission(){
            return ContextCompat.checkSelfPermission(dwnContext, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;
        }
        private boolean hasWritePermission(){
            return ContextCompat.checkSelfPermission(dwnContext, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;
        }
        private boolean hasInternetAccess(){
            return ContextCompat.checkSelfPermission(dwnContext, Manifest.permission.INTERNET) == PackageManager.PERMISSION_GRANTED;
        }

    }



}
