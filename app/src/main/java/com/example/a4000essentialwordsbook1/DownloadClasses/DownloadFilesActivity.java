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
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.util.DisplayMetrics;
import android.util.Log;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
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
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;



public class DownloadFilesActivity extends AppCompatActivity
        implements DownloadInterface, PermissionListener, DownloadSizeListener {

    private RecyclerView downloadRecyclerView;
    private DownloadRecyclerViewAdapter downloadAdapter;
    //private static TextView leftTxt;
    private static TextView one_audioLeftTxt, one_imgLeftTxt;
    private static TextView two_audioLeftTxt, two_imgLeftTxt;
    private static TextView three_audioLeftTxt, three_imgLeftTxt;
    private static TextView four_audioLeftTxt, four_imgLeftTxt;
    private static TextView five_audioLeftTxt, five_imgLeftTxt;
    private static TextView six_audioLeftTxt, six_imgLeftTxt;

    private static RelativeLayout one_audioLayout, one_imgLayout;
    private static RelativeLayout two_audioLayout, two_imgLayout;
    private static RelativeLayout three_audioLayout, three_imgLayout;
    private static RelativeLayout four_audioLayout, four_imgLayout;
    private static RelativeLayout five_audioLayout, five_imgLayout;
    private static RelativeLayout six_audioLayout, six_imgLayout;
    private static ImageView one_audioImgView, one_imgImageView;
    private static ImageView two_audioImgView, two_imgImageView;
    private static ImageView three_audioImgView, three_imgImageView;
    private static ImageView four_audioImgView, four_imgImageView;
    private static ImageView five_audioImgView, five_imgImageView;
    private static ImageView six_audioImgView, six_imgImageView;

    private ImageView imgCover_One, imgCover_Two, imgCover_Three,
            imgCover_four, imgCover_five, imgCover_six;

    //private RelativeLayout layout;
    private final int wrdImgCount = 600;
    private final String[] wordImageList = new String[wrdImgCount];
    private final int[] wordUnitNumList = new int[wrdImgCount];
    private final String[] unitImagesList = new String[30];
    private final String[] wrdAudioUrlList = new String[30];
    private final String[] unitAudioUrlList = new String[30];
    private SharedPreferences imgSizePreferences, audioPreferences;
    private final int byteToMB = 1000000;
    private final File mainPath = new File(Environment.DIRECTORY_DOWNLOADS, File.separator + "4000 Essential Words");


    @Override
    protected void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download_files);
        allFunctions();
    }
    private void allFunctions(){
        findViewsById();
        recyclerviewFunctions();
    }


    private void downloadWordImageFunctions_ONE(){
        ExecutorService thread = Executors.newSingleThreadExecutor();
        thread.execute(() ->{
            wordImageListDataReceiver(1);
            unitImageListReceiver(1);
            wordImageDownloader_ONE(wordImageList);
            unitImageDownloader_ONE(unitImagesList);
        });
    }
    @SuppressLint("Range")
    private void wordImageDownloader_ONE(String[] imgUrlList){
        final String appPath = this.getApplicationInfo().dataDir;


        Handler wrdImgRunOnUiThread = new Handler(Looper.getMainLooper());

        final File imgMainPath = new File("Image Files");
        final File wordImgPath = new File(imgMainPath, File.separator + "Word Images");
        final File wordImgBookPath = new File(wordImgPath, File.separator + "Book_" + 1);



        for (int index = 0 ; index < wrdImgCount ; index++) {
            final File wordUnitImgBookPath = new File(wordImgBookPath, File.separator + "Unit_" + wordUnitNumList[index]);
            Uri downloadUri = Uri.parse(imgUrlList[index]);

            final File secondSubFile = new File(wordUnitImgBookPath, File.separator + "." + new File(imgUrlList[index]).getName());
            final File file = new File(Environment.getExternalStoragePublicDirectory(mainPath.toString()) , secondSubFile.toString());


            final boolean imgExists = file.exists();
            if (!imgExists) {
                new Handler(Looper.getMainLooper()).post(this::one_image_imageInDownload);

                DownloadManager.Request wrdImageRequest = new DownloadManager.Request(downloadUri);
                File path = new File(imgUrlList[index]);
                String imageName = path.getName();
                wrdImageRequest.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI | DownloadManager.Request.NETWORK_MOBILE)
                        .setAllowedOverRoaming(true)
                        .setTitle(imageName)
                        .setNotificationVisibility(DownloadManager.Request.VISIBILITY_HIDDEN)
                        .setDestinationInExternalPublicDir(mainPath.toString(), secondSubFile.toString());


                DownloadManager wrdImgDownloadManager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);

                long downloadID = wrdImgDownloadManager.enqueue(wrdImageRequest);
                registerReceiver(onDownloadCompleted_ONE(downloadID), new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));
                boolean downloading = true;

                while (downloading){
                    DownloadManager.Query wrdImgDQ = new DownloadManager.Query();
                    wrdImgDQ.setFilterById(downloadID);
                    Cursor cursor = wrdImgDownloadManager.query(wrdImgDQ);
                    cursor.moveToFirst();

                    int downloadStatus = cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_STATUS));
                    float soFarSize = cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_BYTES_DOWNLOADED_SO_FAR));
                    float localSize = getImgSize(1);



                    if (downloadStatus == DownloadManager.STATUS_SUCCESSFUL){
                        wrdImgRunOnUiThread.post(() -> {
                            int count = getImgSize(1);
                            count++;
                            one_imgLeftTxt.setText(String.valueOf(count));
                            setImgSize(count,1);
                        });
                        cursor.close();
                        downloading = false;
                    } else if (downloadStatus == DownloadManager.STATUS_FAILED) {
                        downloading = false;
                    }else if (downloadStatus == DownloadManager.STATUS_RUNNING){
                    }else if (downloadStatus == DownloadManager.STATUS_PAUSED){}

                    cursor.close();

                }

            }
            if (index == wrdImgCount - 1){
                try {
                    unregisterReceiver(onDownloadCompleted_ONE(0));
                }catch (Exception e){
                    Log.i("closBroadCastLog", "" + e);
                }
            }
        }
    }
    @SuppressLint("Range")
    private void unitImageDownloader_ONE(String[] unitImgUrlList){
        final String appPath = getApplicationInfo().dataDir;
        Handler runOnUiThread = new Handler(Looper.getMainLooper());

        final File imgMainPath = new File("Image Files");
        final File wordImgPath = new File(imgMainPath, File.separator + "Unit Images");
        final File unitImgBookPath = new File(wordImgPath, File.separator + "Book_" + 1);


        for (int index = 0 ; index < 30 ; index++) {
            final File secondSubFile = new File(unitImgBookPath, File.separator + "." + new File(unitImgUrlList[index]).getName());
            final File file = new File(Environment.getExternalStoragePublicDirectory(mainPath.toString()) , secondSubFile.toString());
            final boolean imgExists = file.exists();
            if (!imgExists) {
                Uri downloadUri = Uri.parse(unitImgUrlList[index]);

                DownloadManager.Request request = new DownloadManager.Request(downloadUri);
                File path = new File(unitImgUrlList[index]);
                String imageName = path.getName();
                request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI | DownloadManager.Request.NETWORK_MOBILE)
                        .setAllowedOverRoaming(true)
                        .setTitle(imageName)
                        .setNotificationVisibility(DownloadManager.Request.VISIBILITY_HIDDEN)
                        .setDestinationInExternalPublicDir(mainPath.toString(), secondSubFile.toString());


                DownloadManager dm = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
                final long downloadID = dm.enqueue(request);
                registerReceiver(onDownloadCompleted_ONE(downloadID), new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));


                boolean downloading = true;
                while (downloading){
                    DownloadManager.Query dq = new DownloadManager.Query();
                    dq.setFilterById(downloadID);

                    Cursor cursor = dm.query(dq);
                    cursor.moveToFirst();

                    int downloadStatus = cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_STATUS));
                    float soFarSize = cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_BYTES_DOWNLOADED_SO_FAR));
                    float localSize = getImgSize(1);


                    if (downloadStatus == DownloadManager.STATUS_SUCCESSFUL){
                        runOnUiThread.post(() -> {
                            int count = getImgSize(1);
                            count++;
                            one_imgLeftTxt.setText(String.valueOf(count));
                            setImgSize(count,1);
                        });
                        cursor.close();
                        downloading = false;

                    } else if (downloadStatus == DownloadManager.STATUS_FAILED) {
                        downloading = false;
                    }else if (downloadStatus == DownloadManager.STATUS_RUNNING){
                    }else if (downloadStatus == DownloadManager.STATUS_PAUSED){}
                    cursor.close();
                }
            }
            if (index == 29){
                try {
                    new Handler(Looper.getMainLooper()).post(this::one_image_imageInDownloadIsDone);
                    unregisterReceiver(onDownloadCompleted_ONE(0));
                }catch (Exception e){
                    Log.i("closBroadCastLog", "" + e);
                }
            }

        }
    }
    private BroadcastReceiver onDownloadCompleted_ONE(final long downloadID){

        return new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                final long dmID = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1);
                if (downloadID == dmID){

                    try {
                        one_imgLeftTxt.setText(String.valueOf(getImgSize(1)));
                        Log.d("mainUpdate", one_imgLeftTxt.getText().toString());
                    }catch (Exception e){
                        Log.i("updateTextView", "" + e);
                    }
                }
            }
        };
    }



    private void downloadWordImageFunctions_TWO(){
        ExecutorService thread = Executors.newSingleThreadExecutor();
        thread.execute(() ->{
            wordImageListDataReceiver(2);
            unitImageListReceiver(2);
            wordImageDownloader_TWO(wordImageList);
            unitImageDownloader_TWO(unitImagesList);
        });
    }
    @SuppressLint("Range")
    private void wordImageDownloader_TWO(String[] imgUrlList){
        final String appPath = this.getApplicationInfo().dataDir;
        Handler wrdImgRunOnUiThread = new Handler(Looper.getMainLooper());

        final File imgMainPath = new File("Image Files");
        final File wordImgPath = new File(imgMainPath, File.separator + "Word Images");
        final File wordImgBookPath = new File(wordImgPath, File.separator + "Book_" + 2);



        for (int index = 0 ; index < wrdImgCount ; index++) {
            final File wordUnitImgBookPath = new File(wordImgBookPath, File.separator + "Unit_" + wordUnitNumList[index]);
            Uri downloadUri = Uri.parse(imgUrlList[index]);

            final File secondSubFile = new File(wordUnitImgBookPath, File.separator + "." + new File(imgUrlList[index]).getName());
            final File file = new File(Environment.getExternalStoragePublicDirectory(mainPath.toString()) , secondSubFile.toString());


            final boolean imgExists = file.exists();
            if (!imgExists) {
                new Handler(Looper.getMainLooper()).post(this::two_image_imageInDownload);


                DownloadManager.Request wrdImageRequest = new DownloadManager.Request(downloadUri);
                File path = new File(imgUrlList[index]);
                String imageName = path.getName();
                wrdImageRequest.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI | DownloadManager.Request.NETWORK_MOBILE)
                        .setAllowedOverRoaming(true)
                        .setTitle(imageName)
                        .setNotificationVisibility(DownloadManager.Request.VISIBILITY_HIDDEN)
                        .setDestinationInExternalPublicDir(mainPath.toString(), secondSubFile.toString());


                DownloadManager wrdImgDownloadManager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);

                final long downloadID = wrdImgDownloadManager.enqueue(wrdImageRequest);
                registerReceiver(onDownloadCompleted_TWO(downloadID), new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));
                boolean downloading = true;

                while (downloading){
                    DownloadManager.Query wrdImgDQ = new DownloadManager.Query();
                    wrdImgDQ.setFilterById(downloadID);
                    Cursor cursor = wrdImgDownloadManager.query(wrdImgDQ);
                    cursor.moveToFirst();

                    int downloadStatus = cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_STATUS));
                    float soFarSize = cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_BYTES_DOWNLOADED_SO_FAR));
                    float localSize = getImgSize(2);



                    if (downloadStatus == DownloadManager.STATUS_SUCCESSFUL){
                        wrdImgRunOnUiThread.post(() -> {
                            int count = getImgSize(2);
                            count++;
                            one_imgLeftTxt.setText(String.valueOf(count));
                            setImgSize(count,2);
                        });
                        cursor.close();
                        downloading = false;
                    } else if (downloadStatus == DownloadManager.STATUS_FAILED) {
                        downloading = false;
                    }else if (downloadStatus == DownloadManager.STATUS_RUNNING){
                    }else if (downloadStatus == DownloadManager.STATUS_PAUSED){}


                    cursor.close();

                }

            }
            if (index == 19){
                try {
                    unregisterReceiver(onDownloadCompleted_TWO(0));
                }catch (Exception e){
                    Log.i("closBroadCastLog", "" + e);
                }
            }
        }
    }
    @SuppressLint("Range")
    private void unitImageDownloader_TWO(String[] unitImgUrlList){
        final String appPath = getApplicationInfo().dataDir;
        Handler runOnUiThread = new Handler(Looper.getMainLooper());

        final File imgMainPath = new File("Image Files");
        final File wordImgPath = new File(imgMainPath, File.separator + "Unit Images");
        final File unitImgBookPath = new File(wordImgPath, File.separator + "Book_" + 2);


        for (int index = 0 ; index < 30 ; index++) {
            final File secondSubFile = new File(unitImgBookPath, File.separator + "." + new File(unitImgUrlList[index]).getName());
            final File file = new File(Environment.getExternalStoragePublicDirectory(mainPath.toString()) , secondSubFile.toString());
            final boolean imgExists = file.exists();
            if (!imgExists) {
                Uri downloadUri = Uri.parse(unitImgUrlList[index]);

                DownloadManager.Request request = new DownloadManager.Request(downloadUri);
                File path = new File(unitImgUrlList[index]);
                String imageName = path.getName();
                request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI | DownloadManager.Request.NETWORK_MOBILE)
                        .setAllowedOverRoaming(true)
                        .setTitle(imageName)
                        .setNotificationVisibility(DownloadManager.Request.VISIBILITY_HIDDEN)
                        .setDestinationInExternalPublicDir(mainPath.toString(), secondSubFile.toString());


                DownloadManager dm = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
                final long downloadID = dm.enqueue(request);
                registerReceiver(onDownloadCompleted_TWO(downloadID), new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));


                boolean downloading = true;
                while (downloading){
                    DownloadManager.Query dq = new DownloadManager.Query();
                    dq.setFilterById(downloadID);

                    Cursor cursor = dm.query(dq);
                    cursor.moveToFirst();

                    int downloadStatus = cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_STATUS));
                    float soFarSize = cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_BYTES_DOWNLOADED_SO_FAR));
                    float localSize = getImgSize(2);


                    if (downloadStatus == DownloadManager.STATUS_SUCCESSFUL){
                        runOnUiThread.post(() -> {
                            int count = getImgSize(2);
                            count++;
                            one_imgLeftTxt.setText(String.valueOf(count));
                            setImgSize(count,2);
                        });
                        cursor.close();
                        downloading = false;

                    } else if (downloadStatus == DownloadManager.STATUS_FAILED) {
                        downloading = false;

                    }else if (downloadStatus == DownloadManager.STATUS_RUNNING){

                    }else if (downloadStatus == DownloadManager.STATUS_PAUSED){}
                    cursor.close();

                }


            }
            if (index == 29){
                try {
                    new Handler(Looper.getMainLooper()).post(this::two_image_imageInDownloadIsDone);
                    unregisterReceiver(onDownloadCompleted_TWO(0));
                }catch (Exception e){
                    Log.i("closBroadCastLog", "" + e);
                }
            }

        }
    }
    private BroadcastReceiver onDownloadCompleted_TWO(long downloadID){

        return new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                final long dmID = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1);
                if (downloadID == dmID){

                    try {
                        two_imgLeftTxt.setText(String.valueOf(getImgSize(2)));
                        Log.d("mainUpdate", two_imgLeftTxt.getText().toString());
                    }catch (Exception e){
                        Log.i("updateTextView", "" + e);
                    }
                }
            }
        };
    }

    private void downloadWordImageFunctions_THREE(){
        ExecutorService thread = Executors.newSingleThreadExecutor();
        thread.execute(() ->{
            wordImageListDataReceiver(3);
            unitImageListReceiver(3);
            wordImageDownloader_THREE(wordImageList);
            unitImageDownloader_THREE(unitImagesList);
        });
    }
    @SuppressLint("Range")
    private void wordImageDownloader_THREE(String[] imgUrlList){
        final String appPath = this.getApplicationInfo().dataDir;
        Handler wrdImgRunOnUiThread = new Handler(Looper.getMainLooper());

        final File imgMainPath = new File("Image Files");
        final File wordImgPath = new File(imgMainPath, File.separator + "Word Images");
        final File wordImgBookPath = new File(wordImgPath, File.separator + "Book_" + 3);



        for (int index = 0 ; index < wrdImgCount ; index++) {
            final File wordUnitImgBookPath = new File(wordImgBookPath, File.separator + "Unit_" + wordUnitNumList[index]);
            Uri downloadUri = Uri.parse(imgUrlList[index]);

            final File secondSubFile = new File(wordUnitImgBookPath, File.separator + "." + new File(imgUrlList[index]).getName());
            final File file = new File(Environment.getExternalStoragePublicDirectory(mainPath.toString()) , secondSubFile.toString());


            final boolean imgExists = file.exists();
            if (!imgExists) {
                new Handler(Looper.getMainLooper()).post(this::three_image_imageInDownload);

                DownloadManager.Request wrdImageRequest = new DownloadManager.Request(downloadUri);
                File path = new File(imgUrlList[index]);
                String imageName = path.getName();
                wrdImageRequest.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI | DownloadManager.Request.NETWORK_MOBILE)
                        .setAllowedOverRoaming(true)
                        .setTitle(imageName)
                        .setNotificationVisibility(DownloadManager.Request.VISIBILITY_HIDDEN)
                        .setDestinationInExternalPublicDir(mainPath.toString(), secondSubFile.toString());


                DownloadManager wrdImgDownloadManager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);

                final long downloadID = wrdImgDownloadManager.enqueue(wrdImageRequest);
                registerReceiver(onDownloadCompleted_THREE(downloadID), new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));
                boolean downloading = true;

                while (downloading){
                    DownloadManager.Query wrdImgDQ = new DownloadManager.Query();
                    wrdImgDQ.setFilterById(downloadID);
                    Cursor cursor = wrdImgDownloadManager.query(wrdImgDQ);
                    cursor.moveToFirst();

                    int downloadStatus = cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_STATUS));
                    float soFarSize = cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_BYTES_DOWNLOADED_SO_FAR));
                    float localSize = getImgSize(3);



                    if (downloadStatus == DownloadManager.STATUS_SUCCESSFUL){
                        wrdImgRunOnUiThread.post(() -> {
                            int count = getImgSize(3);
                            count++;
                            one_imgLeftTxt.setText(String.valueOf(count));
                            setImgSize(count,3);
                        });
                        cursor.close();
                        downloading = false;
                    } else if (downloadStatus == DownloadManager.STATUS_FAILED) {
                        downloading = false;
                    }else if (downloadStatus == DownloadManager.STATUS_RUNNING){
                    }else if (downloadStatus == DownloadManager.STATUS_PAUSED){}

                    cursor.close();

                }

            }
            if (index == 19){
                try {
                    unregisterReceiver(onDownloadCompleted_THREE(0));
                }catch (Exception e){
                    Log.i("closBroadCastLog", "" + e);
                }
            }
        }
    }
    @SuppressLint("Range")
    private void unitImageDownloader_THREE(String[] unitImgUrlList){
        final String appPath = getApplicationInfo().dataDir;
        Handler runOnUiThread = new Handler(Looper.getMainLooper());

        final File imgMainPath = new File("Image Files");
        final File wordImgPath = new File(imgMainPath, File.separator + "Unit Images");
        final File unitImgBookPath = new File(wordImgPath, File.separator + "Book_" + 3);


        for (int index = 0 ; index < 30 ; index++) {
            final File secondSubFile = new File(unitImgBookPath, File.separator + "." + new File(unitImgUrlList[index]).getName());
            final File file = new File(Environment.getExternalStoragePublicDirectory(mainPath.toString()) , secondSubFile.toString());
            final boolean imgExists = file.exists();
            if (!imgExists) {
                Uri downloadUri = Uri.parse(unitImgUrlList[index]);

                DownloadManager.Request request = new DownloadManager.Request(downloadUri);
                File path = new File(unitImgUrlList[index]);
                String imageName = path.getName();
                request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI | DownloadManager.Request.NETWORK_MOBILE)
                        .setAllowedOverRoaming(true)
                        .setTitle(imageName)
                        .setNotificationVisibility(DownloadManager.Request.VISIBILITY_HIDDEN)
                        .setDestinationInExternalPublicDir(mainPath.toString(), secondSubFile.toString());


                DownloadManager dm = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
                final long downloadID = dm.enqueue(request);
                registerReceiver(onDownloadCompleted_THREE(downloadID), new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));


                boolean downloading = true;
                while (downloading){
                    DownloadManager.Query dq = new DownloadManager.Query();
                    dq.setFilterById(downloadID);

                    Cursor cursor = dm.query(dq);
                    cursor.moveToFirst();

                    int downloadStatus = cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_STATUS));
                    float soFarSize = cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_BYTES_DOWNLOADED_SO_FAR));
                    float localSize = getImgSize(3);


                    if (downloadStatus == DownloadManager.STATUS_SUCCESSFUL){
                        runOnUiThread.post(() -> {
                            int count = getImgSize(3);
                            count++;
                            one_imgLeftTxt.setText(String.valueOf(count));
                            setImgSize(count,3);
                        });
                        cursor.close();
                        downloading = false;

                    } else if (downloadStatus == DownloadManager.STATUS_FAILED) {
                        downloading = false;

                    }else if (downloadStatus == DownloadManager.STATUS_RUNNING){

                    }else if (downloadStatus == DownloadManager.STATUS_PAUSED){}
                    cursor.close();

                }


            }
            if (index == 29){
                try {
                    new Handler(Looper.getMainLooper()).post(this::three_image_imageInDownloadIsDone);
                    unregisterReceiver(onDownloadCompleted_THREE(0));
                }catch (Exception e){
                    Log.i("closBroadCastLog", "" + e);
                }
            }

        }
    }
    private BroadcastReceiver onDownloadCompleted_THREE(long downloadID){

        return new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                final long dmID = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1);
                if (downloadID == dmID){

                    try {
                        three_imgLeftTxt.setText(String.valueOf(getImgSize(3)));
                        Log.d("mainUpdate", three_imgLeftTxt.getText().toString());
                    }catch (Exception e){
                        Log.i("updateTextView", "" + e);
                    }
                }
            }
        };
    }


    private void downloadWordImageFunctions_FOUR(){
        ExecutorService thread = Executors.newSingleThreadExecutor();
        thread.execute(() ->{
            wordImageListDataReceiver(4);
            unitImageListReceiver(4);
            wordImageDownloader_FOUR(wordImageList);
            unitImageDownloader_FOUR(unitImagesList);
        });
    }
    @SuppressLint("Range")
    private void wordImageDownloader_FOUR(String[] imgUrlList){
        final String appPath = this.getApplicationInfo().dataDir;
        Handler wrdImgRunOnUiThread = new Handler(Looper.getMainLooper());

        final File imgMainPath = new File("Image Files");
        final File wordImgPath = new File(imgMainPath, File.separator + "Word Images");
        final File wordImgBookPath = new File(wordImgPath, File.separator + "Book_" + 4);



        for (int index = 0 ; index < wrdImgCount ; index++) {
            final File wordUnitImgBookPath = new File(wordImgBookPath, File.separator + "Unit_" + wordUnitNumList[index]);
            Uri downloadUri = Uri.parse(imgUrlList[index]);

            final File secondSubFile = new File(wordUnitImgBookPath, File.separator + "." + new File(imgUrlList[index]).getName());
            final File file = new File(Environment.getExternalStoragePublicDirectory(mainPath.toString()) , secondSubFile.toString());


            final boolean imgExists = file.exists();
            if (!imgExists) {
                new Handler(Looper.getMainLooper()).post(this::four_image_imageInDownload);

                DownloadManager.Request wrdImageRequest = new DownloadManager.Request(downloadUri);
                File path = new File(imgUrlList[index]);
                String imageName = path.getName();
                wrdImageRequest.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI | DownloadManager.Request.NETWORK_MOBILE)
                        .setAllowedOverRoaming(true)
                        .setTitle(imageName)
                        .setNotificationVisibility(DownloadManager.Request.VISIBILITY_HIDDEN)
                        .setDestinationInExternalPublicDir(mainPath.toString(), secondSubFile.toString());


                DownloadManager wrdImgDownloadManager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);

                final long downloadID = wrdImgDownloadManager.enqueue(wrdImageRequest);
                registerReceiver(onDownloadCompleted_FOUR(downloadID), new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));
                boolean downloading = true;

                while (downloading){
                    DownloadManager.Query wrdImgDQ = new DownloadManager.Query();
                    wrdImgDQ.setFilterById(downloadID);
                    Cursor cursor = wrdImgDownloadManager.query(wrdImgDQ);
                    cursor.moveToFirst();

                    int downloadStatus = cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_STATUS));
                    float soFarSize = cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_BYTES_DOWNLOADED_SO_FAR));
                    float localSize = getImgSize(4);



                    if (downloadStatus == DownloadManager.STATUS_SUCCESSFUL){
                        wrdImgRunOnUiThread.post(() -> {
                            int count = getImgSize(4);
                            count++;
                            one_imgLeftTxt.setText(String.valueOf(count));
                            setImgSize(count,4);
                        });
                        cursor.close();
                        downloading = false;
                    } else if (downloadStatus == DownloadManager.STATUS_FAILED) {
                        downloading = false;
                    }else if (downloadStatus == DownloadManager.STATUS_RUNNING){
                    }else if (downloadStatus == DownloadManager.STATUS_PAUSED){}

                    cursor.close();

                }

            }
            if (index == 19){
                try {
                    unregisterReceiver(onDownloadCompleted_FOUR(0));
                }catch (Exception e){
                    Log.i("closBroadCastLog", "" + e);
                }
            }
        }
    }
    @SuppressLint("Range")
    private void unitImageDownloader_FOUR(String[] unitImgUrlList){
        final String appPath = getApplicationInfo().dataDir;
        Handler runOnUiThread = new Handler(Looper.getMainLooper());

        final File imgMainPath = new File("Image Files");
        final File wordImgPath = new File(imgMainPath, File.separator + "Unit Images");
        final File unitImgBookPath = new File(wordImgPath, File.separator + "Book_" + 4);


        for (int index = 0 ; index < 30 ; index++) {
            final File secondSubFile = new File(unitImgBookPath, File.separator + "." + new File(unitImgUrlList[index]).getName());
            final File file = new File(Environment.getExternalStoragePublicDirectory(mainPath.toString()) , secondSubFile.toString());
            final boolean imgExists = file.exists();
            if (!imgExists) {
                Uri downloadUri = Uri.parse(unitImgUrlList[index]);

                DownloadManager.Request request = new DownloadManager.Request(downloadUri);
                File path = new File(unitImgUrlList[index]);
                String imageName = path.getName();
                request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI | DownloadManager.Request.NETWORK_MOBILE)
                        .setAllowedOverRoaming(true)
                        .setTitle(imageName)
                        .setNotificationVisibility(DownloadManager.Request.VISIBILITY_HIDDEN)
                        .setDestinationInExternalPublicDir(mainPath.toString(), secondSubFile.toString());


                DownloadManager dm = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
                final long downloadID = dm.enqueue(request);
                registerReceiver(onDownloadCompleted_FOUR(downloadID), new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));


                boolean downloading = true;
                while (downloading){
                    DownloadManager.Query dq = new DownloadManager.Query();
                    dq.setFilterById(downloadID);

                    Cursor cursor = dm.query(dq);
                    cursor.moveToFirst();

                    int downloadStatus = cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_STATUS));
                    float soFarSize = cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_BYTES_DOWNLOADED_SO_FAR));
                    float localSize = getImgSize(4);


                    if (downloadStatus == DownloadManager.STATUS_SUCCESSFUL){
                        runOnUiThread.post(() -> {
                            int count = getImgSize(4);
                            count++;
                            one_imgLeftTxt.setText(String.valueOf(count));
                            setImgSize(count,4);
                        });
                        cursor.close();
                        downloading = false;

                    } else if (downloadStatus == DownloadManager.STATUS_FAILED) {
                        downloading = false;

                    }else if (downloadStatus == DownloadManager.STATUS_RUNNING){

                    }else if (downloadStatus == DownloadManager.STATUS_PAUSED){}
                    cursor.close();

                }
            }
            if (index == 29){
                try {
                    new Handler(Looper.getMainLooper()).post(this::four_image_imageInDownloadIsDone);
                    unregisterReceiver(onDownloadCompleted_FOUR(0));
                }catch (Exception e){
                    Log.i("closBroadCastLog", "" + e);
                }
            }

        }
    }
    private BroadcastReceiver onDownloadCompleted_FOUR(long downloadID){

        return new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                final long dmID = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1);
                if (downloadID == dmID){

                    try {
                        four_imgLeftTxt.setText(String.valueOf(getImgSize(4)));
                        Log.d("mainUpdate", four_imgLeftTxt.getText().toString());
                    }catch (Exception e){
                        Log.i("updateTextView", "" + e);
                    }
                }
            }
        };
    }

    private void downloadWordImageFunctions_FIVE(){
        ExecutorService thread = Executors.newSingleThreadExecutor();
        thread.execute(() ->{
            wordImageListDataReceiver(5);
            unitImageListReceiver(5);
            wordImageDownloader_FIVE(wordImageList);
            unitImageDownloader_FIVE(unitImagesList);
        });
    }
    @SuppressLint("Range")
    private void wordImageDownloader_FIVE(String[] imgUrlList){
        final String appPath = this.getApplicationInfo().dataDir;
        Handler wrdImgRunOnUiThread = new Handler(Looper.getMainLooper());

        final File imgMainPath = new File("Image Files");
        final File wordImgPath = new File(imgMainPath, File.separator + "Word Images");
        final File wordImgBookPath = new File(wordImgPath, File.separator + "Book_" + 5);



        for (int index = 0 ; index < wrdImgCount ; index++) {
            final File wordUnitImgBookPath = new File(wordImgBookPath, File.separator + "Unit_" + wordUnitNumList[index]);
            Uri downloadUri = Uri.parse(imgUrlList[index]);

            final File secondSubFile = new File(wordUnitImgBookPath, File.separator + "." + new File(imgUrlList[index]).getName());
            final File file = new File(Environment.getExternalStoragePublicDirectory(mainPath.toString()) , secondSubFile.toString());


            final boolean imgExists = file.exists();
            if (!imgExists) {
                new Handler(Looper.getMainLooper()).post(this::five_image_imageInDownload);

                DownloadManager.Request wrdImageRequest = new DownloadManager.Request(downloadUri);
                File path = new File(imgUrlList[index]);
                String imageName = path.getName();
                wrdImageRequest.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI | DownloadManager.Request.NETWORK_MOBILE)
                        .setAllowedOverRoaming(true)
                        .setTitle(imageName)
                        .setNotificationVisibility(DownloadManager.Request.VISIBILITY_HIDDEN)
                        .setDestinationInExternalPublicDir(mainPath.toString(), secondSubFile.toString());


                DownloadManager wrdImgDownloadManager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);

                final long downloadID = wrdImgDownloadManager.enqueue(wrdImageRequest);
                registerReceiver(onDownloadCompleted_FIVE(downloadID), new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));
                boolean downloading = true;

                while (downloading){
                    DownloadManager.Query wrdImgDQ = new DownloadManager.Query();
                    wrdImgDQ.setFilterById(downloadID);
                    Cursor cursor = wrdImgDownloadManager.query(wrdImgDQ);
                    cursor.moveToFirst();

                    int downloadStatus = cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_STATUS));
                    float soFarSize = cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_BYTES_DOWNLOADED_SO_FAR));
                    float localSize = getImgSize(5);



                    if (downloadStatus == DownloadManager.STATUS_SUCCESSFUL){
                        wrdImgRunOnUiThread.post(() -> {
                            int count = getImgSize(5);
                            count++;
                            one_imgLeftTxt.setText(String.valueOf(count));
                            setImgSize(count,5);
                        });
                        downloading = false;
                    } else if (downloadStatus == DownloadManager.STATUS_FAILED) {
                        downloading = false;
                    }else if (downloadStatus == DownloadManager.STATUS_RUNNING){
                    }else if (downloadStatus == DownloadManager.STATUS_PAUSED){}
                    cursor.close();

                }

            }
            if (index == 19){
                try {
                    unregisterReceiver(onDownloadCompleted_FIVE(0));
                }catch (Exception e){
                    Log.i("closBroadCastLog", "" + e);
                }
            }
        }
    }
    @SuppressLint("Range")
    private void unitImageDownloader_FIVE(String[] unitImgUrlList){
        final String appPath = getApplicationInfo().dataDir;
        Handler runOnUiThread = new Handler(Looper.getMainLooper());

        final File imgMainPath = new File("Image Files");
        final File wordImgPath = new File(imgMainPath, File.separator + "Unit Images");
        final File unitImgBookPath = new File(wordImgPath, File.separator + "Book_" + 5);


        for (int index = 0 ; index < 30 ; index++) {
            final File secondSubFile = new File(unitImgBookPath, File.separator + "." + new File(unitImgUrlList[index]).getName());
            final File file = new File(Environment.getExternalStoragePublicDirectory(mainPath.toString()) , secondSubFile.toString());
            final boolean imgExists = file.exists();
            if (!imgExists) {
                Uri downloadUri = Uri.parse(unitImgUrlList[index]);

                DownloadManager.Request request = new DownloadManager.Request(downloadUri);
                File path = new File(unitImgUrlList[index]);
                String imageName = path.getName();
                request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI | DownloadManager.Request.NETWORK_MOBILE)
                        .setAllowedOverRoaming(true)
                        .setTitle(imageName)
                        .setNotificationVisibility(DownloadManager.Request.VISIBILITY_HIDDEN)
                        .setDestinationInExternalPublicDir(mainPath.toString(), secondSubFile.toString());


                DownloadManager dm = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
                final long downloadID = dm.enqueue(request);
                registerReceiver(onDownloadCompleted_FIVE(downloadID), new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));


                boolean downloading = true;
                while (downloading){
                    DownloadManager.Query dq = new DownloadManager.Query();
                    dq.setFilterById(downloadID);

                    Cursor cursor = dm.query(dq);
                    cursor.moveToFirst();

                    int downloadStatus = cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_STATUS));
                    float soFarSize = cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_BYTES_DOWNLOADED_SO_FAR));
                    float localSize = getImgSize(5);


                    if (downloadStatus == DownloadManager.STATUS_SUCCESSFUL){
                        runOnUiThread.post(() -> {
                            int count = getImgSize(5);
                            count++;
                            one_imgLeftTxt.setText(String.valueOf(count));
                            setImgSize(count,5);
                        });
                        cursor.close();
                        downloading = false;

                    } else if (downloadStatus == DownloadManager.STATUS_FAILED) {
                        downloading = false;

                    }else if (downloadStatus == DownloadManager.STATUS_RUNNING){

                    }else if (downloadStatus == DownloadManager.STATUS_PAUSED){}
                    cursor.close();

                }


            }
            if (index == 29){
                try {
                    new Handler(Looper.getMainLooper()).post(this::five_image_imageInDownloadIsDone);
                    unregisterReceiver(onDownloadCompleted_FIVE(0));
                }catch (Exception e){
                    Log.i("closBroadCastLog", "" + e);
                }
            }

        }
    }
    private BroadcastReceiver onDownloadCompleted_FIVE(long downloadID){

        return new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                final long dmID = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1);
                if (downloadID == dmID){

                    try {
                        five_imgLeftTxt.setText(String.valueOf(getImgSize(5)));
                        Log.d("mainUpdate", five_imgLeftTxt.getText().toString());
                    }catch (Exception e){
                        Log.i("updateTextView", "" + e);
                    }
                }
            }
        };
    }


    private void downloadWordImageFunctions_SIX(){
        ExecutorService thread = Executors.newSingleThreadExecutor();
        thread.execute(() ->{
            wordImageListDataReceiver(6);
            unitImageListReceiver(6);
            wordImageDownloader_SIX(wordImageList);
            unitImageDownloader_SIX(unitImagesList);
        });
    }
    @SuppressLint("Range")
    private void wordImageDownloader_SIX(String[] imgUrlList){
        final String appPath = this.getApplicationInfo().dataDir;
        Handler wrdImgRunOnUiThread = new Handler(Looper.getMainLooper());

        final File imgMainPath = new File("Image Files");
        final File wordImgPath = new File(imgMainPath, File.separator + "Word Images");
        final File wordImgBookPath = new File(wordImgPath, File.separator + "Book_" + 6);



        for (int index = 0 ; index < wrdImgCount ; index++) {
            final File wordUnitImgBookPath = new File(wordImgBookPath, File.separator + "Unit_" + wordUnitNumList[index]);
            Uri downloadUri = Uri.parse(imgUrlList[index]);

            final File secondSubFile = new File(wordUnitImgBookPath, File.separator + "." + new File(imgUrlList[index]).getName());
            final File file = new File(Environment.getExternalStoragePublicDirectory(mainPath.toString()) , secondSubFile.toString());


            final boolean imgExists = file.exists();
            if (!imgExists) {
                new Handler(Looper.getMainLooper()).post(this::six_image_imageInDownload);

                DownloadManager.Request wrdImageRequest = new DownloadManager.Request(downloadUri);
                File path = new File(imgUrlList[index]);
                String imageName = path.getName();
                wrdImageRequest.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI | DownloadManager.Request.NETWORK_MOBILE)
                        .setAllowedOverRoaming(true)
                        .setTitle(imageName)
                        .setNotificationVisibility(DownloadManager.Request.VISIBILITY_HIDDEN)
                        .setDestinationInExternalPublicDir(mainPath.toString(), secondSubFile.toString());


                DownloadManager wrdImgDownloadManager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);

                final long downloadID = wrdImgDownloadManager.enqueue(wrdImageRequest);
                registerReceiver(onDownloadCompleted_SIX(downloadID), new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));
                boolean downloading = true;

                while (downloading){
                    DownloadManager.Query wrdImgDQ = new DownloadManager.Query();
                    wrdImgDQ.setFilterById(downloadID);
                    Cursor cursor = wrdImgDownloadManager.query(wrdImgDQ);
                    cursor.moveToFirst();

                    int downloadStatus = cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_STATUS));
                    float soFarSize = cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_BYTES_DOWNLOADED_SO_FAR));
                    float localSize = getImgSize(6);



                    if (downloadStatus == DownloadManager.STATUS_SUCCESSFUL){
                        wrdImgRunOnUiThread.post(() -> {
                            int count = getImgSize(1);
                            count++;
                            one_imgLeftTxt.setText(String.valueOf(count));
                            setImgSize(count,1);
                        });
                        cursor.close();
                        downloading = false;
                    } else if (downloadStatus == DownloadManager.STATUS_FAILED) {
                        downloading = false;
                    }else if (downloadStatus == DownloadManager.STATUS_RUNNING){
                    }else if (downloadStatus == DownloadManager.STATUS_PAUSED){}

                    cursor.close();

                }

            }
            if (index == 19){
                try {
                    unregisterReceiver(onDownloadCompleted_SIX(0));
                }catch (Exception e){
                    Log.i("closBroadCastLog", "" + e);
                }
            }
        }
    }
    @SuppressLint("Range")
    private void unitImageDownloader_SIX(String[] unitImgUrlList){
        final String appPath = getApplicationInfo().dataDir;
        Handler runOnUiThread = new Handler(Looper.getMainLooper());

        final File imgMainPath = new File("Image Files");
        final File wordImgPath = new File(imgMainPath, File.separator + "Unit Images");
        final File unitImgBookPath = new File(wordImgPath, File.separator + "Book_" + 6);


        for (int index = 0 ; index < 30 ; index++) {
            final File secondSubFile = new File(unitImgBookPath, File.separator + "." + new File(unitImgUrlList[index]).getName());
            final File file = new File(Environment.getExternalStoragePublicDirectory(mainPath.toString()) , secondSubFile.toString());
            final boolean imgExists = file.exists();
            if (!imgExists) {
                Uri downloadUri = Uri.parse(unitImgUrlList[index]);

                DownloadManager.Request request = new DownloadManager.Request(downloadUri);
                File path = new File(unitImgUrlList[index]);
                String imageName = path.getName();
                request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI | DownloadManager.Request.NETWORK_MOBILE)
                        .setAllowedOverRoaming(true)
                        .setTitle(imageName)
                        .setNotificationVisibility(DownloadManager.Request.VISIBILITY_HIDDEN)
                        .setDestinationInExternalPublicDir(mainPath.toString(), secondSubFile.toString());


                DownloadManager dm = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
                final long downloadID = dm.enqueue(request);
                registerReceiver(onDownloadCompleted_SIX(downloadID), new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));


                boolean downloading = true;
                while (downloading){
                    DownloadManager.Query dq = new DownloadManager.Query();
                    dq.setFilterById(downloadID);

                    Cursor cursor = dm.query(dq);
                    cursor.moveToFirst();

                    int downloadStatus = cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_STATUS));
                    float soFarSize = cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_BYTES_DOWNLOADED_SO_FAR));
                    float localSize = getImgSize(6);


                    if (downloadStatus == DownloadManager.STATUS_SUCCESSFUL){
                        runOnUiThread.post(() -> {
                            int count = getImgSize(6);
                            count++;
                            one_imgLeftTxt.setText(String.valueOf(count));
                            setImgSize(count,6);
                        });
                        cursor.close();
                        downloading = false;

                    } else if (downloadStatus == DownloadManager.STATUS_FAILED) {
                        downloading = false;

                    }else if (downloadStatus == DownloadManager.STATUS_RUNNING){

                    }else if (downloadStatus == DownloadManager.STATUS_PAUSED){}
                    cursor.close();
                }
            }
            if (index == 29){
                try {
                    new Handler(Looper.getMainLooper()).post(this::six_image_imageInDownloadIsDone);
                    unregisterReceiver(onDownloadCompleted_SIX(0));
                }catch (Exception e){
                    Log.i("closBroadCastLog", "" + e);
                }
            }

        }
    }
    private BroadcastReceiver onDownloadCompleted_SIX(long downloadID){

        return new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                final long dmID = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1);
                if (downloadID == dmID){

                    try {
                        six_imgLeftTxt.setText(String.valueOf(getImgSize(6)));
                        Log.d("mainUpdate", six_imgLeftTxt.getText().toString());
                    }catch (Exception e){
                        Log.i("updateTextView", "" + e);
                    }
                }
            }
        };
    }


    @SuppressLint("Range")
    private void wordImageListDataReceiver(final int dbNumber){
        int index = 0;

        SQLiteDatabase db = wordListDatabase(dbNumber).getReadableDatabase();
        Cursor cursor = null;

        for (int count = 1 ; count <= 30 ; count++) {
            cursor = db.query(DB_NOTES.NEUTRAL_WORD_TABLE + count,
                    new String[]{DB_NOTES.WORD_ID, DB_NOTES.WORD_IMG, DB_NOTES.UNIT_NUMBER},
                    null, null, null, null, null);


            if (cursor != null && cursor.getCount() != 0) {
                while (cursor.moveToNext()) {

                    wordImageList[index] = cursor.getString(cursor.getColumnIndex(DB_NOTES.WORD_IMG));
                    wordUnitNumList[index] = cursor.getInt(cursor.getColumnIndex(DB_NOTES.UNIT_NUMBER));
                    index++;
                }
            }
        }

        assert cursor != null;
        cursor.close();
        db.close();
    }
    @SuppressLint("Range")
    private void unitImageListReceiver(final int dbNumber){
        SQLiteDatabase db = unitListDatabase(dbNumber).getReadableDatabase();
        int index = 0;

        Cursor awCursor = db.query(DB_NOTES.UNIT_TABLE,
                new String[]{DB_NOTES.UNIT_IMG},
                null,null,null,null,null);

        if (awCursor != null && awCursor.getCount() != 0){
            while (awCursor.moveToNext()){
                unitImagesList[index] = awCursor.getString(awCursor.getColumnIndex(DB_NOTES.UNIT_IMG));
                index++;
            }
        }

        assert awCursor != null;
        awCursor.close();
        db.close();
    }
    private SQLiteOpenHelper unitListDatabase(final int unitId){
        if (unitId == 1){
            return new UnitDatabaseBookOne(DownloadFilesActivity.this);
        }else if (unitId == 2){
            return new UnitDatabaseBookTwo(DownloadFilesActivity.this);
        }else if (unitId == 3){
            return new UnitDatabaseBookThree(DownloadFilesActivity.this);
        }else if (unitId == 4){
            return new UnitDatabaseBookFour(DownloadFilesActivity.this);
        }else if (unitId == 5){
            return new UnitDatabaseBookFive(DownloadFilesActivity.this);
        }else {
            return new UnitDatabaseBookSix(DownloadFilesActivity.this);
        }
    }
    private SQLiteOpenHelper wordListDatabase(final int databaseNum){
        if (databaseNum == 1){
            return new WordDatabaseBookOne(DownloadFilesActivity.this);
        }else if (databaseNum == 2){
            return new WordDatabaseBookTwo(DownloadFilesActivity.this);
        }else if (databaseNum == 3){
            return new WordDatabaseBookThree(DownloadFilesActivity.this);
        }else if (databaseNum == 4){
            return new WordDatabaseBookFour(DownloadFilesActivity.this);
        }else if (databaseNum == 5){
            return new WordDatabaseBookFive(DownloadFilesActivity.this);
        }else {
            return new WordDatabaseBookSix(DownloadFilesActivity.this);
        }
    }

    private DecimalFormat deci(){return new DecimalFormat("#.##");}
    private String realTimeDownResult(final float size){
        return (deci().format(size / byteToMB));
    }




    private void setImgSize(int size, int dbNumber){
        imgSizePreferences = this.getSharedPreferences("image_size_" + dbNumber, Context.MODE_PRIVATE);
        SharedPreferences.Editor sizeEdit = imgSizePreferences.edit();
        sizeEdit.putInt("Img_Size", size);
        sizeEdit.apply();
    }
    private void setAudioSize(int size, int dbNumber){
        audioPreferences = getSharedPreferences("audio_size_" + dbNumber , Context.MODE_PRIVATE);
        SharedPreferences.Editor sizeEdit = audioPreferences.edit();
        sizeEdit.putInt("Audio_Size", size);
        sizeEdit.apply();
    }
    private int getImgSize(int dbNumber){
        imgSizePreferences = getSharedPreferences("image_size_" + dbNumber, Context.MODE_PRIVATE);
        return imgSizePreferences.getInt("Img_Size", 0);
    }
    private int getAudioSize(int dbNumber){
        audioPreferences = getSharedPreferences("audio_size_" + dbNumber, Context.MODE_PRIVATE);
        return audioPreferences.getInt("Audio_Size", 0);
    }



    private void audiosDownloadFunctions_ONE(){
        ExecutorService audioThread = Executors.newSingleThreadExecutor();
        audioThread.execute(() ->{
            wordAudioReceiver(1);
            wordAudioDownloader_ONE(wrdAudioUrlList);
            unitAudioDownloader_ONE(unitAudioUrlList);
        });
    }
    @SuppressLint("Range")
    private void wordAudioDownloader_ONE(String[] audioUrlList){
        final String appPath = DownloadFilesActivity.this.getApplicationInfo().dataDir;
        Handler audioRunOnUiThread = new Handler(Looper.getMainLooper());


        final File audioMainPath = new File("Audio Files");
        final File audioUnitPath = new File(audioMainPath, File.separator + "Word Audios");
        final File audioWordBookPath = new File(audioUnitPath, File.separator + "Book_" + 1);

        for (int index = 0 ; index < 30 ; index++) {
            final File secondSubFile = new File(audioWordBookPath, File.separator + "." + new File(audioUrlList[index]).getName());
            final File file = new File(Environment.getExternalStoragePublicDirectory(mainPath.toString()) , secondSubFile.toString());
            final boolean imgExists = file.exists();
            if (!imgExists) {
                new Handler(Looper.getMainLooper()).post(this::one_audio_imageInDownload);

                Uri downloadUri = Uri.parse(audioUrlList[index]);

                DownloadManager.Request request = new DownloadManager.Request(downloadUri);
                File path = new File(audioUrlList[index]);
                String imageName = path.getName();
                request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI | DownloadManager.Request.NETWORK_MOBILE)
                        .setAllowedOverRoaming(true)
                        .setTitle(imageName)
                        .setNotificationVisibility(DownloadManager.Request.VISIBILITY_HIDDEN)
                        .setDestinationInExternalPublicDir(mainPath.toString(), secondSubFile.toString());


                DownloadManager dm = (DownloadManager) this.getSystemService(Context.DOWNLOAD_SERVICE);
                final long downloadID = dm.enqueue(request);
                registerReceiver(audioBroadcastReceiver_ONE(downloadID, 1), new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));

                boolean downloading = true;

                while (downloading){
                    DownloadManager.Query wrdImgDQ = new DownloadManager.Query();
                    wrdImgDQ.setFilterById(downloadID);
                    Cursor cursor = dm.query(wrdImgDQ);
                    cursor.moveToFirst();

                    int downloadStatus = cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_STATUS));
                    float soFarSize = cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_BYTES_DOWNLOADED_SO_FAR));
                    float localSize = getAudioSize(1);



                    if (downloadStatus == DownloadManager.STATUS_SUCCESSFUL){
                        audioRunOnUiThread.post(() -> {
                            int count = getAudioSize(1);
                            count++;
                            one_audioLeftTxt.setText(String.valueOf(count));
                            setAudioSize(count, 1);
                        });
                        cursor.close();
                        downloading = false;
                    } else if (downloadStatus == DownloadManager.STATUS_FAILED) {
                        downloading = false;
                    }else if (downloadStatus == DownloadManager.STATUS_RUNNING){
                    }else if (downloadStatus == DownloadManager.STATUS_PAUSED){}

                    cursor.close();

                }

            }
            if (index == 29){
                try {
                    unregisterReceiver(audioBroadcastReceiver_ONE(0, 0));
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
    }
    @SuppressLint("Range")
    private void unitAudioDownloader_ONE(String[] unitAudioList){
        final String appPath = DownloadFilesActivity.this.getApplicationInfo().dataDir;
        final Handler runOnUiThread = new Handler(Looper.getMainLooper());


        final File audioMainPath = new File("Audio Files");
        final File audioUnitPath = new File(audioMainPath, File.separator + "Unit Audios");
        final File audioUnitBookPath = new File(audioUnitPath, File.separator + "Book_" + 1);

        for (int index = 0 ; index < 30 ; index++) {
            final File secondSubFile = new File(audioUnitBookPath, File.separator + "." + new File(unitAudioList[index]).getName());
            final File file = new File(Environment.getExternalStoragePublicDirectory(mainPath.toString()) , secondSubFile.toString());
            final boolean imgExists = file.exists();
            if (!imgExists) {
                Uri downloadUri = Uri.parse(unitAudioList[index]);

                DownloadManager.Request request = new DownloadManager.Request(downloadUri);
                File path = new File(unitAudioList[index]);
                String imageName = path.getName();
                request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI | DownloadManager.Request.NETWORK_MOBILE)
                        .setAllowedOverRoaming(true)
                        .setTitle(imageName)
                        .setNotificationVisibility(DownloadManager.Request.VISIBILITY_HIDDEN)
                        .setDestinationInExternalPublicDir(mainPath.toString(), secondSubFile.toString());


                DownloadManager dm = (DownloadManager) this.getSystemService(Context.DOWNLOAD_SERVICE);
                final long downloadID = dm.enqueue(request);
                registerReceiver(audioBroadcastReceiver_ONE(downloadID, 1), new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));

                boolean downloading = true;

                while(downloading){
                    DownloadManager.Query wrdImgDQ = new DownloadManager.Query();
                    wrdImgDQ.setFilterById(downloadID);
                    Cursor cursor = dm.query(wrdImgDQ);
                    cursor.moveToFirst();

                    int downloadStatus = cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_STATUS));
                    float soFarSize = cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_BYTES_DOWNLOADED_SO_FAR));
                    //float localSize = getAudioSize(1);



                    if (downloadStatus == DownloadManager.STATUS_SUCCESSFUL){
                        runOnUiThread.post(() -> {
                            int count = getAudioSize(1);
                            count++;
                            one_audioLeftTxt.setText(String.valueOf(count));
                            setAudioSize(count, 1);
                        });
                        cursor.close();
                        downloading = false;
                    } else if (downloadStatus == DownloadManager.STATUS_FAILED) {
                        downloading = false;
                    }else if (downloadStatus == DownloadManager.STATUS_RUNNING){
                    }else if (downloadStatus == DownloadManager.STATUS_PAUSED){}


                    cursor.close();
                }
            }
            if (index == 29){
                try {
                    new Handler(Looper.getMainLooper()).post(this::one_audio_imageInDownloadIsDone);
                    unregisterReceiver(audioBroadcastReceiver_ONE(0, 0));

                }catch (Exception e){
                    Log.e("closeUnitAudioLog1", "" + e);
                }
            }
        }
    }
    private BroadcastReceiver audioBroadcastReceiver_ONE(long downloadID, int dbNumber){
        return new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                final long intentDownloadID = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1);
                if (downloadID == intentDownloadID){
                    try {
                        one_audioLeftTxt.setText(String.valueOf(getAudioSize(1)));
                        Log.d("audioDownloadLogInfoDb" + dbNumber, "" + one_audioLeftTxt.getText().toString());
                    }catch (Exception e){
                        Log.i("AudioDownloadLogDB" + dbNumber, "" + e);
                    }
                }

            }
        };
    }

    private void audiosDownloadFunctions_TWO(){
        ExecutorService audioThread = Executors.newSingleThreadExecutor();
        audioThread.execute(() ->{
            wordAudioReceiver(2);
            wordAudioDownloader_TWO(wrdAudioUrlList);
            unitAudioDownloader_TWO(unitAudioUrlList);
        });
    }
    @SuppressLint("Range")
    private void wordAudioDownloader_TWO(String[] audioUrlList){
        final String appPath = DownloadFilesActivity.this.getApplicationInfo().dataDir;
        Handler audioRunOnUiThread = new Handler(Looper.getMainLooper());


        final File audioMainPath = new File("Audio Files");
        final File audioUnitPath = new File(audioMainPath, File.separator + "Word Audios");
        final File audioWordBookPath = new File(audioUnitPath, File.separator + "Book_" + 2);

        for (int index = 0 ; index < 30 ; index++) {
            final File secondSubFile = new File(audioWordBookPath, File.separator + "." + new File(audioUrlList[index]).getName());
            final File file = new File(Environment.getExternalStoragePublicDirectory(mainPath.toString()) , secondSubFile.toString());
            final boolean imgExists = file.exists();
            if (!imgExists) {
                new Handler(Looper.getMainLooper()).post(this::two_audio_imageInDownload);

                Uri downloadUri = Uri.parse(audioUrlList[index]);

                DownloadManager.Request request = new DownloadManager.Request(downloadUri);
                File path = new File(audioUrlList[index]);
                String imageName = path.getName();
                request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI | DownloadManager.Request.NETWORK_MOBILE)
                        .setAllowedOverRoaming(true)
                        .setTitle(imageName)
                        .setNotificationVisibility(DownloadManager.Request.VISIBILITY_HIDDEN)
                        .setDestinationInExternalPublicDir(mainPath.toString(), secondSubFile.toString());


                DownloadManager dm = (DownloadManager) this.getSystemService(Context.DOWNLOAD_SERVICE);
                final long downloadID = dm.enqueue(request);
                registerReceiver(audioBroadcastReceiver_TWO(downloadID, 2), new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));

                boolean downloading = true;

                while (downloading){
                    DownloadManager.Query wrdImgDQ = new DownloadManager.Query();
                    wrdImgDQ.setFilterById(downloadID);
                    Cursor cursor = dm.query(wrdImgDQ);
                    cursor.moveToFirst();

                    int downloadStatus = cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_STATUS));
                    float soFarSize = cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_BYTES_DOWNLOADED_SO_FAR));
                    float localSize = getAudioSize(2);



                    if (downloadStatus == DownloadManager.STATUS_SUCCESSFUL){
                        audioRunOnUiThread.post(() -> {
                            int count = getAudioSize(2);
                            count++;
                            one_audioLeftTxt.setText(String.valueOf(count));
                            setAudioSize(count, 2);
                        });
                        cursor.close();
                        downloading = false;
                    } else if (downloadStatus == DownloadManager.STATUS_FAILED) {
                        downloading = false;
                    }else if (downloadStatus == DownloadManager.STATUS_RUNNING){
                    }else if (downloadStatus == DownloadManager.STATUS_PAUSED){}

                    cursor.close();

                }

            }
            if (index == 29){
                try {
                    unregisterReceiver(audioBroadcastReceiver_TWO(0, 0));
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
    }
    @SuppressLint("Range")
    private void unitAudioDownloader_TWO(String[] unitAudioList){
        final String appPath = DownloadFilesActivity.this.getApplicationInfo().dataDir;
        final Handler runOnUiThread = new Handler(Looper.getMainLooper());


        final File audioMainPath = new File("Audio Files");
        final File audioUnitPath = new File(audioMainPath, File.separator + "Unit Audios");
        final File audioUnitBookPath = new File(audioUnitPath, File.separator + "Book_" + 2);

        for (int index = 0 ; index < 30 ; index++) {
            final File secondSubFile = new File(audioUnitBookPath, File.separator + "." + new File(unitAudioList[index]).getName());
            final File file = new File(Environment.getExternalStoragePublicDirectory(mainPath.toString()) , secondSubFile.toString());
            final boolean imgExists = file.exists();
            if (!imgExists) {
                Uri downloadUri = Uri.parse(unitAudioList[index]);

                DownloadManager.Request request = new DownloadManager.Request(downloadUri);
                File path = new File(unitAudioList[index]);
                String imageName = path.getName();
                request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI | DownloadManager.Request.NETWORK_MOBILE)
                        .setAllowedOverRoaming(true)
                        .setTitle(imageName)
                        .setNotificationVisibility(DownloadManager.Request.VISIBILITY_HIDDEN)
                        .setDestinationInExternalPublicDir(mainPath.toString(), secondSubFile.toString());


                DownloadManager dm = (DownloadManager) this.getSystemService(Context.DOWNLOAD_SERVICE);
                final long downloadID = dm.enqueue(request);
                registerReceiver(audioBroadcastReceiver_TWO(downloadID, 2), new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));

                boolean downloading = true;

                while(downloading){
                    DownloadManager.Query wrdImgDQ = new DownloadManager.Query();
                    wrdImgDQ.setFilterById(downloadID);
                    Cursor cursor = dm.query(wrdImgDQ);
                    cursor.moveToFirst();

                    int downloadStatus = cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_STATUS));
                    float soFarSize = cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_BYTES_DOWNLOADED_SO_FAR));
                    float localSize = getAudioSize(2);



                    if (downloadStatus == DownloadManager.STATUS_SUCCESSFUL){
                        runOnUiThread.post(() -> {
                            int count = getAudioSize(2);
                            count++;
                            one_audioLeftTxt.setText(String.valueOf(count));
                            setAudioSize(count, 2);
                        });
                        cursor.close();
                        downloading = false;
                    } else if (downloadStatus == DownloadManager.STATUS_FAILED) {
                        downloading = false;
                    }else if (downloadStatus == DownloadManager.STATUS_RUNNING){
                    }else if (downloadStatus == DownloadManager.STATUS_PAUSED){}


                    cursor.close();
                }
            }
            if (index == 29){
                try {
                    new Handler(Looper.getMainLooper()).post(this::two_audio_imageInDownloadIsDone);
                    unregisterReceiver(audioBroadcastReceiver_TWO(0, 0));
                }catch (Exception e){
                    Log.e("closeUnitAudioLog2", "" + e);
                }
            }
        }
    }
    private BroadcastReceiver audioBroadcastReceiver_TWO(long downloadID, int dbNumber){
        return new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                final long intentDownloadID = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1);
                if (downloadID == intentDownloadID){
                    try {
                        two_audioLeftTxt.setText(String.valueOf(getAudioSize(dbNumber)));
                        Log.d("audioDownloadLogInfoDb" + dbNumber, "" + two_audioLeftTxt.getText().toString());
                    }catch (Exception e){
                        Log.i("AudioDownloadLogDB" + dbNumber, "" + e);
                    }
                }

            }
        };
    }

    private void audiosDownloadFunctions_THREE(){
        ExecutorService audioThread = Executors.newSingleThreadExecutor();
        audioThread.execute(() ->{
            wordAudioReceiver(3);
            wordAudioDownloader_THREE(wrdAudioUrlList);
            unitAudioDownloader_THREE(unitAudioUrlList);
        });
    }
    @SuppressLint("Range")
    private void wordAudioDownloader_THREE(String[] audioUrlList){
        final String appPath = DownloadFilesActivity.this.getApplicationInfo().dataDir;
        Handler audioRunOnUiThread = new Handler(Looper.getMainLooper());


        final File audioMainPath = new File("Audio Files");
        final File audioUnitPath = new File(audioMainPath, File.separator + "Word Audios");
        final File audioWordBookPath = new File(audioUnitPath, File.separator + "Book_" + 3);

        for (int index = 0 ; index < 30 ; index++) {
            final File secondSubFile = new File(audioWordBookPath, File.separator + "." + new File(audioUrlList[index]).getName());
            final File file = new File(Environment.getExternalStoragePublicDirectory(mainPath.toString()) , secondSubFile.toString());
            final boolean imgExists = file.exists();
            if (!imgExists) {
                new Handler(Looper.getMainLooper()).post(this::three_audio_imageInDownload);

                Uri downloadUri = Uri.parse(audioUrlList[index]);

                DownloadManager.Request request = new DownloadManager.Request(downloadUri);
                File path = new File(audioUrlList[index]);
                String imageName = path.getName();
                request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI | DownloadManager.Request.NETWORK_MOBILE)
                        .setAllowedOverRoaming(true)
                        .setTitle(imageName)
                        .setNotificationVisibility(DownloadManager.Request.VISIBILITY_HIDDEN)
                        .setDestinationInExternalPublicDir(mainPath.toString(), secondSubFile.toString());


                DownloadManager dm = (DownloadManager) this.getSystemService(Context.DOWNLOAD_SERVICE);
                final long downloadID = dm.enqueue(request);
                registerReceiver(audioBroadcastReceiver_THREE(downloadID, 3), new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));

                boolean downloading = true;

                while (downloading){
                    DownloadManager.Query wrdImgDQ = new DownloadManager.Query();
                    wrdImgDQ.setFilterById(downloadID);
                    Cursor cursor = dm.query(wrdImgDQ);
                    cursor.moveToFirst();

                    int downloadStatus = cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_STATUS));
                    float soFarSize = cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_BYTES_DOWNLOADED_SO_FAR));
                    float localSize = getAudioSize(3);



                    if (downloadStatus == DownloadManager.STATUS_SUCCESSFUL){
                        audioRunOnUiThread.post(() -> {
                            int count = getAudioSize(3);
                            count++;
                            one_audioLeftTxt.setText(String.valueOf(count));
                            setAudioSize(count, 3);
                        });
                        cursor.close();
                        downloading = false;
                    } else if (downloadStatus == DownloadManager.STATUS_FAILED) {
                        downloading = false;
                    }else if (downloadStatus == DownloadManager.STATUS_RUNNING){
                    }else if (downloadStatus == DownloadManager.STATUS_PAUSED){}

                    cursor.close();

                }

            }
            if (index == 29){
                try {
                    unregisterReceiver(audioBroadcastReceiver_THREE(0, 0));
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
    }
    @SuppressLint("Range")
    private void unitAudioDownloader_THREE(String[] unitAudioList){
        final String appPath = DownloadFilesActivity.this.getApplicationInfo().dataDir;
        final Handler runOnUiThread = new Handler(Looper.getMainLooper());


        final File audioMainPath = new File("Audio Files");
        final File audioUnitPath = new File(audioMainPath, File.separator + "Unit Audios");
        final File audioUnitBookPath = new File(audioUnitPath, File.separator + "Book_" + 3);

        for (int index = 0 ; index < 30 ; index++) {
            final File secondSubFile = new File(audioUnitBookPath, File.separator + "." + new File(unitAudioList[index]).getName());
            final File file = new File(Environment.getExternalStoragePublicDirectory(mainPath.toString()) , secondSubFile.toString());
            final boolean imgExists = file.exists();
            if (!imgExists) {
                Uri downloadUri = Uri.parse(unitAudioList[index]);

                DownloadManager.Request request = new DownloadManager.Request(downloadUri);
                File path = new File(unitAudioList[index]);
                String imageName = path.getName();
                request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI | DownloadManager.Request.NETWORK_MOBILE)
                        .setAllowedOverRoaming(true)
                        .setTitle(imageName)
                        .setNotificationVisibility(DownloadManager.Request.VISIBILITY_HIDDEN)
                        .setDestinationInExternalPublicDir(mainPath.toString(), secondSubFile.toString());


                DownloadManager dm = (DownloadManager) this.getSystemService(Context.DOWNLOAD_SERVICE);
                final long downloadID = dm.enqueue(request);
                registerReceiver(audioBroadcastReceiver_THREE(downloadID, 3), new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));

                boolean downloading = true;

                while(downloading){
                    DownloadManager.Query wrdImgDQ = new DownloadManager.Query();
                    wrdImgDQ.setFilterById(downloadID);
                    Cursor cursor = dm.query(wrdImgDQ);
                    cursor.moveToFirst();

                    int downloadStatus = cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_STATUS));
                    float soFarSize = cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_BYTES_DOWNLOADED_SO_FAR));
                    float localSize = getAudioSize(3);



                    if (downloadStatus == DownloadManager.STATUS_SUCCESSFUL){
                        runOnUiThread.post(() -> {
                            int count = getAudioSize(3);
                            count++;
                            one_audioLeftTxt.setText(String.valueOf(count));
                            setAudioSize(count, 3);
                        });
                        cursor.close();
                        downloading = false;
                    } else if (downloadStatus == DownloadManager.STATUS_FAILED) {
                        downloading = false;
                    }else if (downloadStatus == DownloadManager.STATUS_RUNNING){
                    }else if (downloadStatus == DownloadManager.STATUS_PAUSED){}

                    cursor.close();
                }
            }
            if (index == 29){
                try {
                    new Handler(Looper.getMainLooper()).post(this::three_audio_imageInDownloadIsDone);
                    unregisterReceiver(audioBroadcastReceiver_THREE(0, 0));
                }catch (Exception e){
                    Log.e("closeUnitAudioLog3", "" + e);
                }
            }
        }
    }
    private BroadcastReceiver audioBroadcastReceiver_THREE(long downloadID, int dbNumber){
        return new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                final long intentDownloadID = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1);
                if (downloadID == intentDownloadID){
                    try {
                        three_audioLeftTxt.setText(String.valueOf(getAudioSize(dbNumber)));
                        Log.d("audioDownloadLogInfoDb" + dbNumber, "" + three_audioLeftTxt.getText().toString());
                    }catch (Exception e){
                        Log.i("AudioDownloadLogDB" + dbNumber, "" + e);
                    }
                }

            }
        };
    }

    private void audiosDownloadFunctions_FOUR(){
        ExecutorService audioThread = Executors.newSingleThreadExecutor();
        audioThread.execute(() ->{
            wordAudioReceiver(4);
            wordAudioDownloader_FOUR(wrdAudioUrlList);
            unitAudioDownloader_FOUR(unitAudioUrlList);
        });
    }
    @SuppressLint("Range")
    private void wordAudioDownloader_FOUR(String[] audioUrlList){
        final String appPath = DownloadFilesActivity.this.getApplicationInfo().dataDir;
        Handler audioRunOnUiThread = new Handler(Looper.getMainLooper());


        final File audioMainPath = new File("Audio Files");
        final File audioUnitPath = new File(audioMainPath, File.separator + "Word Audios");
        final File audioWordBookPath = new File(audioUnitPath, File.separator + "Book_" + 4);

        for (int index = 0 ; index < 30 ; index++) {
            final File secondSubFile = new File(audioWordBookPath, File.separator + "." + new File(audioUrlList[index]).getName());
            final File file = new File(Environment.getExternalStoragePublicDirectory(mainPath.toString()) , secondSubFile.toString());
            final boolean imgExists = file.exists();
            if (!imgExists) {
                new Handler(Looper.getMainLooper()).post(this::four_audio_imageInDownload);

                Uri downloadUri = Uri.parse(audioUrlList[index]);

                DownloadManager.Request request = new DownloadManager.Request(downloadUri);
                File path = new File(audioUrlList[index]);
                String imageName = path.getName();
                request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI | DownloadManager.Request.NETWORK_MOBILE)
                        .setAllowedOverRoaming(true)
                        .setTitle(imageName)
                        .setNotificationVisibility(DownloadManager.Request.VISIBILITY_HIDDEN)
                        .setDestinationInExternalPublicDir(mainPath.toString(), secondSubFile.toString());


                DownloadManager dm = (DownloadManager) this.getSystemService(Context.DOWNLOAD_SERVICE);
                final long downloadID = dm.enqueue(request);
                registerReceiver(audioBroadcastReceiver_FOUR(downloadID, 4), new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));

                boolean downloading = true;

                while (downloading){
                    DownloadManager.Query wrdImgDQ = new DownloadManager.Query();
                    wrdImgDQ.setFilterById(downloadID);
                    Cursor cursor = dm.query(wrdImgDQ);
                    cursor.moveToFirst();

                    int downloadStatus = cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_STATUS));
                    float soFarSize = cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_BYTES_DOWNLOADED_SO_FAR));
                    float localSize = getAudioSize(4);



                    if (downloadStatus == DownloadManager.STATUS_SUCCESSFUL){
                        audioRunOnUiThread.post(() -> {
                            int count = getAudioSize(4);
                            count++;
                            one_audioLeftTxt.setText(String.valueOf(count));
                            setAudioSize(count, 4);
                        });
                        cursor.close();
                        downloading = false;
                    } else if (downloadStatus == DownloadManager.STATUS_FAILED) {
                        downloading = false;
                    }else if (downloadStatus == DownloadManager.STATUS_RUNNING){
                    }else if (downloadStatus == DownloadManager.STATUS_PAUSED){}

                    cursor.close();

                }

            }
            if (index == 29){
                try {
                    unregisterReceiver(audioBroadcastReceiver_FOUR(0, 0));
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
    }
    @SuppressLint("Range")
    private void unitAudioDownloader_FOUR(String[] unitAudioList){
        final String appPath = DownloadFilesActivity.this.getApplicationInfo().dataDir;
        final Handler runOnUiThread = new Handler(Looper.getMainLooper());


        final File audioMainPath = new File("Audio Files");
        final File audioUnitPath = new File(audioMainPath, File.separator + "Unit Audios");
        final File audioUnitBookPath = new File(audioUnitPath, File.separator + "Book_" + 4);

        for (int index = 0 ; index < 30 ; index++) {
            final File secondSubFile = new File(audioUnitBookPath, File.separator + "." + new File(unitAudioList[index]).getName());
            final File file = new File(Environment.getExternalStoragePublicDirectory(mainPath.toString()) , secondSubFile.toString());
            final boolean imgExists = file.exists();
            if (!imgExists) {
                Uri downloadUri = Uri.parse(unitAudioList[index]);

                DownloadManager.Request request = new DownloadManager.Request(downloadUri);
                File path = new File(unitAudioList[index]);
                String imageName = path.getName();
                request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI | DownloadManager.Request.NETWORK_MOBILE)
                        .setAllowedOverRoaming(true)
                        .setTitle(imageName)
                        .setNotificationVisibility(DownloadManager.Request.VISIBILITY_HIDDEN)
                        .setDestinationInExternalPublicDir(mainPath.toString(), secondSubFile.toString());


                DownloadManager dm = (DownloadManager) this.getSystemService(Context.DOWNLOAD_SERVICE);
                final long downloadID = dm.enqueue(request);
                registerReceiver(audioBroadcastReceiver_FOUR(downloadID, 4), new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));

                boolean downloading = true;

                while(downloading){
                    DownloadManager.Query wrdImgDQ = new DownloadManager.Query();
                    wrdImgDQ.setFilterById(downloadID);
                    Cursor cursor = dm.query(wrdImgDQ);
                    cursor.moveToFirst();

                    int downloadStatus = cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_STATUS));
                    float soFarSize = cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_BYTES_DOWNLOADED_SO_FAR));
                    float localSize = getAudioSize(4);



                    if (downloadStatus == DownloadManager.STATUS_SUCCESSFUL){
                        runOnUiThread.post(() -> {
                            int count = getAudioSize(4);
                            count++;
                            one_audioLeftTxt.setText(String.valueOf(count));
                            setAudioSize(count, 4);
                        });
                        cursor.close();
                        downloading = false;
                    } else if (downloadStatus == DownloadManager.STATUS_FAILED) {
                        downloading = false;
                    }else if (downloadStatus == DownloadManager.STATUS_RUNNING){
                    }else if (downloadStatus == DownloadManager.STATUS_PAUSED){}


                    cursor.close();
                }
            }
            if (index == 29){
                try {
                    new Handler(Looper.getMainLooper()).post(this::four_audio_imageInDownloadIsDone);
                    unregisterReceiver(audioBroadcastReceiver_FOUR(0, 0));
                }catch (Exception e){
                    Log.e("closeUnitAudioLog4", "" + e);
                }
            }
        }
    }
    private BroadcastReceiver audioBroadcastReceiver_FOUR(long downloadID, int dbNumber){
        return new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                final long intentDownloadID = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1);
                if (downloadID == intentDownloadID){
                    try {
                        four_audioLeftTxt.setText(String.valueOf(getAudioSize(dbNumber)));
                        Log.d("audioDownloadLogInfoDb" + dbNumber, "" + four_audioLeftTxt.getText().toString());
                    }catch (Exception e){
                        Log.i("AudioDownloadLogDB" + dbNumber, "" + e);
                    }
                }

            }
        };
    }

    private void audiosDownloadFunctions_FIVE(){
        ExecutorService audioThread = Executors.newSingleThreadExecutor();
        audioThread.execute(() ->{
            wordAudioReceiver(5);
            wordAudioDownloader_FIVE(wrdAudioUrlList);
            unitAudioDownloader_FIVE(unitAudioUrlList);
        });
    }
    @SuppressLint("Range")
    private void wordAudioDownloader_FIVE(String[] audioUrlList){
        final String appPath = DownloadFilesActivity.this.getApplicationInfo().dataDir;
        Handler audioRunOnUiThread = new Handler(Looper.getMainLooper());


        final File audioMainPath = new File("Audio Files");
        final File audioUnitPath = new File(audioMainPath, File.separator + "Word Audios");
        final File audioWordBookPath = new File(audioUnitPath, File.separator + "Book_" + 5);

        for (int index = 0 ; index < 30 ; index++) {
            final File secondSubFile = new File(audioWordBookPath, File.separator + "." + new File(audioUrlList[index]).getName());
            final File file = new File(Environment.getExternalStoragePublicDirectory(mainPath.toString()) , secondSubFile.toString());
            final boolean imgExists = file.exists();
            if (!imgExists) {
                new Handler(Looper.getMainLooper()).post(this::five_audio_imageInDownload);

                Uri downloadUri = Uri.parse(audioUrlList[index]);

                DownloadManager.Request request = new DownloadManager.Request(downloadUri);
                File path = new File(audioUrlList[index]);
                String imageName = path.getName();
                request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI | DownloadManager.Request.NETWORK_MOBILE)
                        .setAllowedOverRoaming(true)
                        .setTitle(imageName)
                        .setNotificationVisibility(DownloadManager.Request.VISIBILITY_HIDDEN)
                        .setDestinationInExternalPublicDir(mainPath.toString(), secondSubFile.toString());


                DownloadManager dm = (DownloadManager) this.getSystemService(Context.DOWNLOAD_SERVICE);
                final long downloadID = dm.enqueue(request);
                registerReceiver(audioBroadcastReceiver_FIVE(downloadID, 5), new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));

                boolean downloading = true;

                while (downloading){
                    DownloadManager.Query wrdImgDQ = new DownloadManager.Query();
                    wrdImgDQ.setFilterById(downloadID);
                    Cursor cursor = dm.query(wrdImgDQ);
                    cursor.moveToFirst();

                    int downloadStatus = cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_STATUS));
                    float soFarSize = cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_BYTES_DOWNLOADED_SO_FAR));
                    float localSize = getAudioSize(5);



                    if (downloadStatus == DownloadManager.STATUS_SUCCESSFUL){
                        audioRunOnUiThread.post(() -> {
                            int count = getAudioSize(5);
                            count++;
                            one_audioLeftTxt.setText(String.valueOf(count));
                            setAudioSize(count, 5);
                        });
                        cursor.close();
                        downloading = false;
                    } else if (downloadStatus == DownloadManager.STATUS_FAILED) {
                        downloading = false;
                    }else if (downloadStatus == DownloadManager.STATUS_RUNNING){
                    }else if (downloadStatus == DownloadManager.STATUS_PAUSED){}

                    cursor.close();

                }

            }
            if (index == 29){
                try {
                    unregisterReceiver(audioBroadcastReceiver_FIVE(0, 0));
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
    }
    @SuppressLint("Range")
    private void unitAudioDownloader_FIVE(String[] unitAudioList){
        final String appPath = DownloadFilesActivity.this.getApplicationInfo().dataDir;
        final Handler runOnUiThread = new Handler(Looper.getMainLooper());


        final File audioMainPath = new File("Audio Files");
        final File audioUnitPath = new File(audioMainPath, File.separator + "Unit Audios");
        final File audioUnitBookPath = new File(audioUnitPath, File.separator + "Book_" + 5);

        for (int index = 0 ; index < 30 ; index++) {
            final File secondSubFile = new File(audioUnitBookPath, File.separator + "." + new File(unitAudioList[index]).getName());
            final File file = new File(Environment.getExternalStoragePublicDirectory(mainPath.toString()) , secondSubFile.toString());
            final boolean imgExists = file.exists();
            if (!imgExists) {
                Uri downloadUri = Uri.parse(unitAudioList[index]);

                DownloadManager.Request request = new DownloadManager.Request(downloadUri);
                File path = new File(unitAudioList[index]);
                String imageName = path.getName();
                request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI | DownloadManager.Request.NETWORK_MOBILE)
                        .setAllowedOverRoaming(true)
                        .setTitle(imageName)
                        .setNotificationVisibility(DownloadManager.Request.VISIBILITY_HIDDEN)
                        .setDestinationInExternalPublicDir(mainPath.toString(), secondSubFile.toString());


                DownloadManager dm = (DownloadManager) this.getSystemService(Context.DOWNLOAD_SERVICE);
                final long downloadID = dm.enqueue(request);
                registerReceiver(audioBroadcastReceiver_FIVE(downloadID, 5), new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));

                boolean downloading = true;

                while(downloading){
                    DownloadManager.Query wrdImgDQ = new DownloadManager.Query();
                    wrdImgDQ.setFilterById(downloadID);
                    Cursor cursor = dm.query(wrdImgDQ);
                    cursor.moveToFirst();

                    int downloadStatus = cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_STATUS));
                    float soFarSize = cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_BYTES_DOWNLOADED_SO_FAR));
                    float localSize = getAudioSize(5);



                    if (downloadStatus == DownloadManager.STATUS_SUCCESSFUL){
                        runOnUiThread.post(() -> {
                            int count = getAudioSize(5);
                            count++;
                            one_audioLeftTxt.setText(String.valueOf(count));
                            setAudioSize(count, 5);
                        });
                        cursor.close();
                        downloading = false;
                    } else if (downloadStatus == DownloadManager.STATUS_FAILED) {
                        downloading = false;
                    }else if (downloadStatus == DownloadManager.STATUS_RUNNING){
                    }else if (downloadStatus == DownloadManager.STATUS_PAUSED){}

                    cursor.close();
                }
            }
            if (index == 29){
                try {
                    new Handler(Looper.getMainLooper()).post(this::five_audio_imageInDownloadIsDone);
                    unregisterReceiver(audioBroadcastReceiver_FIVE(0, 0));
                }catch (Exception e){
                    Log.e("closeUnitAudioLog5", "" + e);
                }
            }
        }
    }
    private BroadcastReceiver audioBroadcastReceiver_FIVE(long downloadID, int dbNumber){
        return new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                final long intentDownloadID = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1);
                if (downloadID == intentDownloadID){
                    try {
                        five_audioLeftTxt.setText(String.valueOf(getAudioSize(dbNumber)));
                        Log.d("audioDownloadLogInfoDb" + dbNumber, "" + five_audioLeftTxt.getText().toString());
                    }catch (Exception e){
                        Log.i("AudioDownloadLogDB" + dbNumber, "" + e);
                    }
                }

            }
        };
    }


    private void audiosDownloadFunctions_SIX(){
        ExecutorService audioThread = Executors.newSingleThreadExecutor();
        audioThread.execute(() ->{
            wordAudioReceiver(6);
            wordAudioDownloader_SIX(wrdAudioUrlList);
            unitAudioDownloader_SIX(unitAudioUrlList);
        });
    }
    @SuppressLint("Range")
    private void wordAudioDownloader_SIX(String[] audioUrlList){
        final String appPath = DownloadFilesActivity.this.getApplicationInfo().dataDir;
        Handler audioRunOnUiThread = new Handler(Looper.getMainLooper());


        final File audioMainPath = new File("Audio Files");
        final File audioUnitPath = new File(audioMainPath, File.separator + "Word Audios");
        final File audioWordBookPath = new File(audioUnitPath, File.separator + "Book_" + 6);

        for (int index = 0 ; index < 30 ; index++) {
            final File secondSubFile = new File(audioWordBookPath, File.separator + "." + new File(audioUrlList[index]).getName());
            final File file = new File(Environment.getExternalStoragePublicDirectory(mainPath.toString()) , secondSubFile.toString());
            final boolean imgExists = file.exists();
            if (!imgExists) {
                new Handler(Looper.getMainLooper()).post(this::six_audio_imageInDownload);

                Uri downloadUri = Uri.parse(audioUrlList[index]);

                DownloadManager.Request request = new DownloadManager.Request(downloadUri);
                File path = new File(audioUrlList[index]);
                String imageName = path.getName();
                request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI | DownloadManager.Request.NETWORK_MOBILE)
                        .setAllowedOverRoaming(true)
                        .setTitle(imageName)
                        .setNotificationVisibility(DownloadManager.Request.VISIBILITY_HIDDEN)
                        .setDestinationInExternalPublicDir(mainPath.toString(), secondSubFile.toString());


                DownloadManager dm = (DownloadManager) this.getSystemService(Context.DOWNLOAD_SERVICE);
                final long downloadID = dm.enqueue(request);
                registerReceiver(audioBroadcastReceiver_SIX(downloadID, 6), new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));

                boolean downloading = true;

                while (downloading){
                    DownloadManager.Query wrdImgDQ = new DownloadManager.Query();
                    wrdImgDQ.setFilterById(downloadID);
                    Cursor cursor = dm.query(wrdImgDQ);
                    cursor.moveToFirst();

                    int downloadStatus = cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_STATUS));
                    float soFarSize = cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_BYTES_DOWNLOADED_SO_FAR));
                    float localSize = getAudioSize(6);



                    if (downloadStatus == DownloadManager.STATUS_SUCCESSFUL){
                        audioRunOnUiThread.post(() -> {
                            int count = getAudioSize(5);
                            count++;
                            one_audioLeftTxt.setText(String.valueOf(count));
                            setAudioSize(count, 5);
                        });
                        cursor.close();
                        downloading = false;
                    } else if (downloadStatus == DownloadManager.STATUS_FAILED) {
                        downloading = false;
                    }else if (downloadStatus == DownloadManager.STATUS_RUNNING){
                    }else if (downloadStatus == DownloadManager.STATUS_PAUSED){}

                    cursor.close();

                }

            }
            if (index == 29){
                try {
                    unregisterReceiver(audioBroadcastReceiver_SIX(0, 0));
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
    }
    @SuppressLint("Range")
    private void unitAudioDownloader_SIX(String[] unitAudioList){
        final String appPath = DownloadFilesActivity.this.getApplicationInfo().dataDir;
        final Handler runOnUiThread = new Handler(Looper.getMainLooper());


        final File audioMainPath = new File("Audio Files");
        final File audioUnitPath = new File(audioMainPath, File.separator + "Unit Audios");
        final File audioUnitBookPath = new File(audioUnitPath, File.separator + "Book_" + 6);

        for (int index = 0 ; index < 30 ; index++) {
            final File secondSubFile = new File(audioUnitBookPath, File.separator + "." + new File(unitAudioList[index]).getName());
            final File file = new File(Environment.getExternalStoragePublicDirectory(mainPath.toString()) , secondSubFile.toString());
            final boolean imgExists = file.exists();
            if (!imgExists) {
                Uri downloadUri = Uri.parse(unitAudioList[index]);

                DownloadManager.Request request = new DownloadManager.Request(downloadUri);
                File path = new File(unitAudioList[index]);
                String imageName = path.getName();
                request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI | DownloadManager.Request.NETWORK_MOBILE)
                        .setAllowedOverRoaming(true)
                        .setTitle(imageName)
                        .setNotificationVisibility(DownloadManager.Request.VISIBILITY_HIDDEN)
                        .setDestinationInExternalPublicDir(mainPath.toString(), secondSubFile.toString());


                DownloadManager dm = (DownloadManager) this.getSystemService(Context.DOWNLOAD_SERVICE);
                final long downloadID = dm.enqueue(request);
                registerReceiver(audioBroadcastReceiver_SIX(downloadID, 6), new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));

                boolean downloading = true;

                while(downloading){
                    DownloadManager.Query wrdImgDQ = new DownloadManager.Query();
                    wrdImgDQ.setFilterById(downloadID);
                    Cursor cursor = dm.query(wrdImgDQ);
                    cursor.moveToFirst();

                    int downloadStatus = cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_STATUS));
                    float soFarSize = cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_BYTES_DOWNLOADED_SO_FAR));
                    float localSize = getAudioSize(6);



                    if (downloadStatus == DownloadManager.STATUS_SUCCESSFUL){
                        runOnUiThread.post(() -> {
                            int count = getAudioSize(6);
                            count++;
                            one_audioLeftTxt.setText(String.valueOf(count));
                            setAudioSize(count, 6);
                        });
                        cursor.close();
                        downloading = false;
                    } else if (downloadStatus == DownloadManager.STATUS_FAILED) {
                        downloading = false;
                    }else if (downloadStatus == DownloadManager.STATUS_RUNNING){
                    }else if (downloadStatus == DownloadManager.STATUS_PAUSED){}

                    cursor.close();
                }
            }
            if (index == 29){
                try {
                    new Handler(Looper.getMainLooper()).post(this::six_audio_imageInDownloadIsDone);
                    unregisterReceiver(audioBroadcastReceiver_SIX(0, 0));
                }catch (Exception e){
                    Log.e("closeUnitAudioLog6", "" + e);
                }
            }
        }
    }
    private BroadcastReceiver audioBroadcastReceiver_SIX(long downloadID, int dbNumber){
        return new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                final long intentDownloadID = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1);
                if (downloadID == intentDownloadID){
                    try {
                        six_audioLeftTxt.setText(String.valueOf(getAudioSize(dbNumber)));
                        Log.d("audioDownloadLogInfoDb" + dbNumber, "" + six_audioLeftTxt.getText().toString());
                    }catch (Exception e){
                        Log.i("AudioDownloadLogDB" + dbNumber, "" + e);
                    }
                }

            }
        };
    }


    @SuppressLint("Range")
    private void wordAudioReceiver(int dbNumber){
        SQLiteDatabase db = unitListDatabase(dbNumber).getReadableDatabase();
        int index = 0;

        Cursor awCursor = db.query(DB_NOTES.UNIT_TABLE,
                new String[]{DB_NOTES.UNIT_COMPLETE_WORD_AUDIO, DB_NOTES.UNIT_AUDIO},
                null, null,
                null, null, null);

        for (int i = 1 ; i <= 30 ; i++) {
            if (awCursor != null && awCursor.getCount() != 0) {
                while (awCursor.moveToNext()) {
                    wrdAudioUrlList[index] = awCursor.getString(awCursor.getColumnIndex(DB_NOTES.UNIT_COMPLETE_WORD_AUDIO));
                    unitAudioUrlList[index] = awCursor.getString(awCursor.getColumnIndex(DB_NOTES.UNIT_AUDIO));
                    index++;
                }
            }
        }

        assert awCursor != null;
        awCursor.close();
        db.close();
    }





    private void one_image_imageInDownload(){
        Glide.with(getApplicationContext())
                .load(R.drawable.download_icon_gif_2)
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                .placeholder(R.drawable.download_icon_image)
                .error(R.drawable.download_icon_image)
                .into(one_imgImageView);
    }
    private void one_image_imageInDownloadIsDone(){
        Glide.with(getApplicationContext())
                .load(R.drawable.green_tick_icon)
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                .placeholder(R.drawable.download_icon_image)
                .error(R.drawable.download_icon_image)
                .into(one_imgImageView);
    }

    private void two_image_imageInDownload(){
        Glide.with(getApplicationContext())
                .load(R.drawable.download_icon_gif_2)
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                .placeholder(R.drawable.download_icon_image)
                .error(R.drawable.download_icon_image)
                .into(two_imgImageView);
    }
    private void two_image_imageInDownloadIsDone(){
        Glide.with(getApplicationContext())
                .load(R.drawable.green_tick_icon)
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                .placeholder(R.drawable.download_icon_image)
                .error(R.drawable.download_icon_image)
                .into(two_imgImageView);
    }

    private void three_image_imageInDownload(){
        Glide.with(getApplicationContext())
                .load(R.drawable.download_icon_gif_2)
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                .placeholder(R.drawable.download_icon_image)
                .error(R.drawable.download_icon_image)
                .into(three_imgImageView);
    }
    private void three_image_imageInDownloadIsDone(){
        Glide.with(getApplicationContext())
                .load(R.drawable.green_tick_icon)
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                .placeholder(R.drawable.download_icon_image)
                .error(R.drawable.download_icon_image)
                .into(three_imgImageView);
    }

    private void four_image_imageInDownload(){
        Glide.with(getApplicationContext())
                .load(R.drawable.download_icon_gif_2)
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                .placeholder(R.drawable.download_icon_image)
                .error(R.drawable.download_icon_image)
                .into(four_imgImageView);
    }
    private void four_image_imageInDownloadIsDone(){
        Glide.with(getApplicationContext())
                .load(R.drawable.green_tick_icon)
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                .placeholder(R.drawable.download_icon_image)
                .error(R.drawable.download_icon_image)
                .into(four_imgImageView);
    }

    private void five_image_imageInDownload(){
        Glide.with(getApplicationContext())
                .load(R.drawable.download_icon_gif_2)
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                .placeholder(R.drawable.download_icon_image)
                .error(R.drawable.download_icon_image)
                .into(five_imgImageView);
    }
    private void five_image_imageInDownloadIsDone(){
        Glide.with(getApplicationContext())
                .load(R.drawable.green_tick_icon)
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                .placeholder(R.drawable.download_icon_image)
                .error(R.drawable.download_icon_image)
                .into(five_imgImageView);
    }

    private void six_image_imageInDownload(){
        Glide.with(getApplicationContext())
                .load(R.drawable.download_icon_gif_2)
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                .placeholder(R.drawable.download_icon_image)
                .error(R.drawable.download_icon_image)
                .into(six_imgImageView);
    }
    private void six_image_imageInDownloadIsDone(){
        Glide.with(getApplicationContext())
                .load(R.drawable.green_tick_icon)
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                .placeholder(R.drawable.download_icon_image)
                .error(R.drawable.download_icon_image)
                .into(six_imgImageView);
    }




    private void one_audio_imageInDownload(){
        Glide.with(getApplicationContext())
                .load(R.drawable.download_icon_gif_2)
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                .placeholder(R.drawable.download_icon_image)
                .error(R.drawable.download_icon_image)
                .into(one_audioImgView);
    }
    private void one_audio_imageInDownloadIsDone(){
        Glide.with(getApplicationContext())
                .load(R.drawable.green_tick_icon)
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                .placeholder(R.drawable.download_icon_image)
                .error(R.drawable.download_icon_image)
                .into(one_audioImgView);
    }

    private void two_audio_imageInDownload(){
        Glide.with(getApplicationContext())
                .load(R.drawable.download_icon_gif_2)
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                .placeholder(R.drawable.download_icon_image)
                .error(R.drawable.download_icon_image)
                .into(two_audioImgView);
    }
    private void two_audio_imageInDownloadIsDone(){
        Glide.with(getApplicationContext())
                .load(R.drawable.green_tick_icon)
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                .placeholder(R.drawable.download_icon_image)
                .error(R.drawable.download_icon_image)
                .into(two_audioImgView);
    }

    private void three_audio_imageInDownload(){
        Glide.with(getApplicationContext())
                .load(R.drawable.download_icon_gif_2)
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                .placeholder(R.drawable.download_icon_image)
                .error(R.drawable.download_icon_image)
                .into(three_audioImgView);
    }
    private void three_audio_imageInDownloadIsDone(){
        Glide.with(getApplicationContext())
                .load(R.drawable.green_tick_icon)
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                .placeholder(R.drawable.download_icon_image)
                .error(R.drawable.download_icon_image)
                .into(three_audioImgView);
    }

    private void four_audio_imageInDownload(){
        Glide.with(getApplicationContext())
                .load(R.drawable.download_icon_gif_2)
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                .placeholder(R.drawable.download_icon_image)
                .error(R.drawable.download_icon_image)
                .into(four_audioImgView);
    }
    private void four_audio_imageInDownloadIsDone(){
        Glide.with(getApplicationContext())
                .load(R.drawable.green_tick_icon)
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                .placeholder(R.drawable.download_icon_image)
                .error(R.drawable.download_icon_image)
                .into(four_audioImgView);
    }

    private void five_audio_imageInDownload(){
        Glide.with(getApplicationContext())
                .load(R.drawable.download_icon_gif_2)
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                .placeholder(R.drawable.download_icon_image)
                .error(R.drawable.download_icon_image)
                .into(five_audioImgView);
    }
    private void five_audio_imageInDownloadIsDone(){
        Glide.with(getApplicationContext())
                .load(R.drawable.green_tick_icon)
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                .placeholder(R.drawable.download_icon_image)
                .error(R.drawable.download_icon_image)
                .into(five_audioImgView);
    }

    private void six_audio_imageInDownload(){
        Glide.with(getApplicationContext())
                .load(R.drawable.download_icon_gif_2)
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                .placeholder(R.drawable.download_icon_image)
                .error(R.drawable.download_icon_image)
                .into(six_audioImgView);
    }
    private void six_audio_imageInDownloadIsDone(){
        Glide.with(getApplicationContext())
                .load(R.drawable.green_tick_icon)
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                .placeholder(R.drawable.download_icon_image)
                .error(R.drawable.download_icon_image)
                .into(six_audioImgView);
    }




































































    @Override
    public void requestForPermissions() {
        permissionRequester();
    }
    private void permissionRequester(){
        if (!hasInternetAccess()){
            ActivityCompat.requestPermissions(this, permissions(), 1);
        }
        if (!hasWritePermission()){
            ActivityCompat.requestPermissions(this, permissions(), 1);
        }
        if (!hasReadPermission()){
            ActivityCompat.requestPermissions(this, permissions(), 1);
        }
    }

    private boolean hasReadPermission(){
        return ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;
    }
    private boolean hasWritePermission(){
        return ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;
    }
    private boolean hasInternetAccess(){
        return ContextCompat.checkSelfPermission(this, Manifest.permission.INTERNET) == PackageManager.PERMISSION_GRANTED;
    }

    private String[] permissions(){
        return new String[]{
                Manifest.permission.INTERNET,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
        };
    }

    @Override
    public void downloadedSize( float size) {
        DecimalFormat deci = new DecimalFormat("#.##");
        final String outPut = (deci.format(size / byteToMB));
        one_audioLeftTxt.setText(outPut);
    }

    private void findViewsById(){
        downloadRecyclerView = findViewById(R.id.download_recycler_view);


        //remain size textView
        one_audioLeftTxt = findViewById(R.id.main_audio_downloading_remain_text_view_one);
        one_imgLeftTxt = findViewById(R.id.main_image_downloading_remain_text_view_one);
        two_audioLeftTxt = findViewById(R.id.main_audio_downloading_remain_text_view_two);
        two_imgLeftTxt = findViewById(R.id.main_image_downloading_remain_text_view_two);
        three_audioLeftTxt = findViewById(R.id.main_audio_downloading_remain_text_view_three);
        three_imgLeftTxt = findViewById(R.id.main_image_downloading_remain_text_view_three);
        four_audioLeftTxt = findViewById(R.id.main_audio_downloading_remain_text_view_four);
        four_imgLeftTxt = findViewById(R.id.main_image_downloading_remain_text_view_four);
        five_audioLeftTxt = findViewById(R.id.main_audio_downloading_remain_text_view_five);
        five_imgLeftTxt = findViewById(R.id.main_image_downloading_remain_text_view_five);
        six_audioLeftTxt = findViewById(R.id.main_audio_downloading_remain_text_view_six);
        six_imgLeftTxt = findViewById(R.id.main_image_downloading_remain_text_view_six);

        imgCover_One = findViewById(R.id.main_download_tv_image_view_one);
        imgCover_Two = findViewById(R.id.main_download_tv_image_view_two);
        imgCover_Three = findViewById(R.id.main_download_tv_image_view_three);
        imgCover_four = findViewById(R.id.main_download_tv_image_view_four);
        imgCover_five = findViewById(R.id.main_download_tv_image_view_five);
        imgCover_six = findViewById(R.id.main_download_tv_image_view_six);




        //layout clickListener
        one_audioLayout = findViewById(R.id.main_audio_download_relative_layout_one);
        one_imgLayout = findViewById(R.id.main_download_image_relative_layout_one);
        two_audioLayout = findViewById(R.id.main_audio_download_relative_layout_two);
        two_imgLayout = findViewById(R.id.main_download_image_relative_layout_two);
        three_audioLayout = findViewById(R.id.main_audio_download_relative_layout_three);
        three_imgLayout = findViewById(R.id.main_download_image_relative_layout_three);
        four_audioLayout = findViewById(R.id.main_audio_download_relative_layout_four);
        four_imgLayout = findViewById(R.id.main_download_image_relative_layout_four);
        five_audioLayout = findViewById(R.id.main_audio_download_relative_layout_five);
        five_imgLayout = findViewById(R.id.main_download_image_relative_layout_five);
        six_audioLayout = findViewById(R.id.main_audio_download_relative_layout_six);
        six_imgLayout = findViewById(R.id.main_download_image_relative_layout_six);

        //imageView Downloader
        one_audioImgView = findViewById(R.id.main_download_audio_circle_image_view_one);
        one_imgImageView = findViewById(R.id.main_download_images_circle_image_view_one);
        two_audioImgView = findViewById(R.id.main_download_audio_circle_image_view_two);
        two_imgImageView = findViewById(R.id.main_download_images_circle_image_view_two);
        three_audioImgView = findViewById(R.id.main_download_audio_circle_image_view_three);
        three_imgImageView = findViewById(R.id.main_download_images_circle_image_view_three);
        four_audioImgView = findViewById(R.id.main_download_audio_circle_image_view_four);
        four_imgImageView = findViewById(R.id.main_download_images_circle_image_view_four);
        five_audioImgView = findViewById(R.id.main_download_audio_circle_image_view_five);
        five_imgImageView = findViewById(R.id.main_download_images_circle_image_view_five);
        six_audioImgView = findViewById(R.id.main_download_audio_circle_image_view_six);
        six_imgImageView = findViewById(R.id.main_download_images_circle_image_view_six);

        downloadAudiosFunctions();
        downloadImagesFunctions();
        setLeftTextViewsValues();
        //imageViewParamsReSetter();
    }
    private void imageViewParamsReSetter(){
        DisplayMetrics metrics = this.getResources().getDisplayMetrics();
        final int width = metrics.widthPixels;
        final int height = metrics.heightPixels;
        imgCover_One.getLayoutParams().width = (int) (width * 0.47);
        imgCover_One.getLayoutParams().height = (int) (height * 0.27);
        imgCover_Two.getLayoutParams().width = (int) (width * 0.47);
        imgCover_Two.getLayoutParams().height = (int) (height * 0.27);
        imgCover_Three.getLayoutParams().width = (int) (width * 0.47);
        imgCover_Three.getLayoutParams().height = (int) (height * 0.27);
        imgCover_four.getLayoutParams().width = (int) (width * 0.47);
        imgCover_four.getLayoutParams().height = (int) (height * 0.27);
        imgCover_five.getLayoutParams().width = (int) (width * 0.47);
        imgCover_five.getLayoutParams().height = (int) (height * 0.27);
        imgCover_six.getLayoutParams().width = (int) (width * 0.47);
        imgCover_six.getLayoutParams().height = (int) (height * 0.27);
    }

    private void setLeftTextViewsValues(){
        one_audioLeftTxt.setText(String.valueOf(getAudioSize(1)));
        one_imgLeftTxt.setText(String.valueOf(getImgSize(1)));
        two_audioLeftTxt.setText(String.valueOf(getAudioSize(2)));
        two_imgLeftTxt.setText(String.valueOf(getImgSize(2)));
        three_audioLeftTxt.setText(String.valueOf(getAudioSize(3)));
        three_imgLeftTxt.setText(String.valueOf(getImgSize(3)));
        four_audioLeftTxt.setText(String.valueOf(getAudioSize(4)));
        four_imgLeftTxt.setText(String.valueOf(getImgSize(4)));
        five_audioLeftTxt.setText(String.valueOf(getAudioSize(5)));
        five_imgLeftTxt.setText(String.valueOf(getImgSize(5)));
        six_audioLeftTxt.setText(String.valueOf(getAudioSize(6)));
        six_imgLeftTxt.setText(String.valueOf(getImgSize(6)));
    }


    private void downloadAudiosFunctions(){
        audioDownloaderBookOneFunctions();
        audioDownloaderBookTwoFunctions();
        audioDownloaderBookThreeFunctions();
        audioDownloaderBookFourFunctions();
        audioDownloaderBookFiveFunctions();
        audioDownloaderBookSixFunctions();
    }
    private void downloadImagesFunctions(){
        imageDownloaderBookOneFunctions();
        imageDownloaderBookTwoFunctions();
        imageDownloaderBookThreeFunctions();
        imageDownloaderBookFourFunctions();
        imageDownloaderBookFiveFunctions();
        imageDownloaderBookSixFunctions();
    }

    private void audioDownloaderBookOneFunctions(){
        one_audioLayout.setOnClickListener(v -> {
            one_audioLayout.setClickable(false);
            audiosDownloadFunctions_ONE();
        });
    }
    private void audioDownloaderBookTwoFunctions(){
        two_audioLayout.setOnClickListener(v -> {
            two_audioLayout.setClickable(false);
            audiosDownloadFunctions_TWO();
        });
    }
    private void audioDownloaderBookThreeFunctions(){
        three_audioLayout.setOnClickListener(v -> {
            three_audioLayout.setClickable(false);
            audiosDownloadFunctions_THREE();
        });
    }
    private void audioDownloaderBookFourFunctions(){
        four_audioLayout.setOnClickListener(v -> {
            four_audioLayout.setClickable(false);
            audiosDownloadFunctions_FOUR();
        });
    }
    private void audioDownloaderBookFiveFunctions(){
        five_audioLayout.setOnClickListener(v -> {
            five_audioLayout.setClickable(false);
            audiosDownloadFunctions_FIVE();
        });
    }
    private void audioDownloaderBookSixFunctions(){
        six_audioLayout.setOnClickListener(v -> {
            six_audioLayout.setClickable(false);
            audiosDownloadFunctions_SIX();
        });
    }



    private void imageDownloaderBookOneFunctions(){
        one_imgLayout.setOnClickListener(v -> {
            one_imgLayout.setClickable(false);
            downloadWordImageFunctions_ONE();
        });
    }
    private void imageDownloaderBookTwoFunctions(){
        two_imgLayout.setOnClickListener(v -> {
            two_imgLayout.setClickable(false);
            downloadWordImageFunctions_TWO();
        });
    }
    private void imageDownloaderBookThreeFunctions(){
        three_imgLayout.setOnClickListener(v -> {
            three_imgLayout.setClickable(false);
            downloadWordImageFunctions_THREE();
        });
    }
    private void imageDownloaderBookFourFunctions(){
        four_imgLayout.setOnClickListener(v -> {
            four_imgLayout.setClickable(false);
            downloadWordImageFunctions_FOUR();
        });
    }
    private void imageDownloaderBookFiveFunctions(){
        five_imgLayout.setOnClickListener(v -> {
            five_imgLayout.setClickable(false);
            downloadWordImageFunctions_FIVE();
        });
    }
    private void imageDownloaderBookSixFunctions(){
        six_imgLayout.setOnClickListener(v -> {
            six_imgLayout.setClickable(false);
            downloadWordImageFunctions_SIX();
        });
    }




    private void recyclerviewFunctions(){
        downloadRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        downloadAdapter = new DownloadRecyclerViewAdapter(this, downloadList(),
                this, this, this);
        downloadRecyclerView.setAdapter(downloadAdapter);
    }
    private ArrayList<DownloadModel> downloadList(){
        ArrayList<DownloadModel> list = new ArrayList<>();
        list.add(new DownloadModel(R.drawable.book_cover_1, "161 MB", "13.8 MB", getAudioSize(1), getImgSize(1)));
        list.add(new DownloadModel(R.drawable.book_cover_2, "150 MB", "15.5 MB", getAudioSize(2), getImgSize(2)));
        list.add(new DownloadModel(R.drawable.book_cover_3, "152 MB", "15.4 MB", getAudioSize(3), getImgSize(3)));
        list.add(new DownloadModel(R.drawable.book_cover_4, "166 MB", "15.7 MB", getAudioSize(4), getImgSize(4)));
        list.add(new DownloadModel(R.drawable.book_cover_5, "171 MB", "15.6 MB", getAudioSize(5), getImgSize(5)));
        list.add(new DownloadModel(R.drawable.book_cover_6, "165 MB", "15.9 MB", getAudioSize(6), getImgSize(6)));
        return list;
    }
    @Override
    public void audioDownload(int position) {
        Toast.makeText(this, "audioDownload " + (position + 1), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void imageDownload(int position) {
        Toast.makeText(this, "imageDownload " + (position + 1), Toast.LENGTH_SHORT).show();
    }
}
