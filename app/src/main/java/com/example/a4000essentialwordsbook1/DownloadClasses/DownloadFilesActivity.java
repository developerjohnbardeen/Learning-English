package com.example.a4000essentialwordsbook1.DownloadClasses;

import android.Manifest;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.Environment;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.a4000essentialwordsbook1.DownloadClasses.DownloadInterfaces.DownloadInterface;
import com.example.a4000essentialwordsbook1.DownloadClasses.DownloadInterfaces.PermissionListener;
import com.example.a4000essentialwordsbook1.DownloadClasses.Downloads.DialogDownload;
import com.example.a4000essentialwordsbook1.R;

import java.io.File;
import java.util.ArrayList;


public class DownloadFilesActivity extends AppCompatActivity
        implements DownloadInterface, PermissionListener {

    private RecyclerView downloadRecyclerView;
    private TextView toolBarTitle;
    private RelativeLayout backPressedLayout;


    @Override
    protected void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download_files);
        allFunctions();
        Toast.makeText(this, "" + isConnected(), Toast.LENGTH_SHORT).show();
        File file = new File(Environment.DIRECTORY_DOWNLOADS);

    }
    private void allFunctions(){
        findViewsById();
        recyclerviewFunctions();
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


    private void findViewsById() {
        downloadRecyclerView = findViewById(R.id.download_recycler_view);
        toolBarTitle = findViewById(R.id.download_unit);
        backPressedLayout = findViewById(R.id.download_tab_layout_bck_bttn_layout);


        backPressedLayout.setOnClickListener(view -> onBackPressed());
        toolBarTitle.setText(R.string.download_eng_str);
    }






    private void recyclerviewFunctions() {
        downloadRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        DownloadRecyclerViewAdapter downloadAdapter =
                new DownloadRecyclerViewAdapter(this, this, downloadList(), "");
        downloadRecyclerView.setAdapter(downloadAdapter);
        downloadList();


    }

    private ArrayList<DownloadModel> downloadList() {
        ArrayList<DownloadModel> list = new ArrayList<>();
        list.add(new DownloadModel(R.drawable.book_cover_1, "169.2 MB", "13.9 MB", getAudioSize(1), getImgSize(1)));
        list.add(new DownloadModel(R.drawable.book_cover_2, "150 MB", "15.5 MB", getAudioSize(2), getImgSize(2)));
        list.add(new DownloadModel(R.drawable.book_cover_3, "152 MB", "15.4 MB", getAudioSize(3), getImgSize(3)));
        list.add(new DownloadModel(R.drawable.book_cover_4, "166 MB", "15.7 MB", getAudioSize(4), getImgSize(4)));
        list.add(new DownloadModel(R.drawable.book_cover_5, "171 MB", "15.6 MB", getAudioSize(5), getImgSize(5)));
        list.add(new DownloadModel(R.drawable.book_cover_6, "165 MB", "15.9 MB", getAudioSize(6), getImgSize(6)));
        return list;
    }

    private float getImgSize(int dbNumber) {
        SharedPreferences imgSizePreferences = getSharedPreferences("image_size_" + dbNumber, Context.MODE_PRIVATE);
        return imgSizePreferences.getFloat("Img_Size", 0);
    }

    private float getAudioSize(int dbNumber) {
        SharedPreferences audioPreferences = getSharedPreferences("audio_size_" + dbNumber, Context.MODE_PRIVATE);
        return audioPreferences.getFloat("Audio_Size", 0);
    }

    @Override
    public void itemListener(int position, String whtDown) {

        if (isConnected()) {
            final int dbNumber = position + 1;
            downloadDialog(dbNumber, whtDown);
        } else {
            Toast.makeText(getApplicationContext(), "اینترنت شما قطع است\n لطفا بعدا دوباره امتحان کنین!", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void downDialogDismissed() {
        Toast.makeText(this, "RecyclerView Has Just Been Reloaded!", Toast.LENGTH_SHORT).show();
        recyclerviewFunctions();
    }


    private void downloadDialog(int dbNum,
                                String whtDown) {
        DialogDownload dialogDownloadFragment =
                DialogDownload.newInstance(
                        this,
                        whtDown,
                        dbNum,
                        this);

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        Fragment prev = getSupportFragmentManager().findFragmentByTag("dialogDownloadFragment");

        if (prev != null) {
            ft.remove(prev);
        }
        dialogDownloadFragment.show(ft, "dialogDownloadFragment");
    }


    private boolean isConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected();
    }


    @Override
    protected void onResume() {
        super.onResume();

    }
}
