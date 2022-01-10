package com.example.a4000essentialwordsbook1.DownloadClasses.Downloads;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import com.example.a4000essentialwordsbook1.DownloadClasses.Downloads.DownloadInterfaces.DownItemListener;
import com.example.a4000essentialwordsbook1.Models.WordModel;
import com.example.a4000essentialwordsbook1.R;
import com.example.a4000essentialwordsbook1.StringNote.DB_NOTES.DB_NOTES;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class DownLoadFragment extends Fragment implements DownItemListener {

    private final int dbNumber;
    private RecyclerView fmDownRecyclerView;
    private ArrayList<ArrayList<String[]>> mulImgList;
    private ArrayList<String[]> unitImgUrl;
    private ArrayList<String[]> unitAudioUrl;
    private ArrayList<String[]> wordAudioUrl;

    public DownLoadFragment(int position) {
        this.dbNumber = position + 1;
    }


    @Override
    public void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_downloads, container, false);
        findViews(view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    private void findViews(View view) {
        fmDownRecyclerView = view.findViewById(R.id.fragment_download_recycler_view);


    }


    @Override
    public void onResume() {
        super.onResume();
        setRecyclerView();
    }

    private void setRecyclerView() {

        ExecutorService thread = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.getMainLooper());

        thread.execute(() -> {
            unitDownloadInfoReceiver();
            wordDownloadInfoReceiver();
            handler.post(() -> {
                DownloadRecyclerAdapter dowRecyclerAdapter =
                        new DownloadRecyclerAdapter(requireActivity(), dbNumber,
                                mulImgList,
                                unitImgUrl,
                                unitAudioUrl,
                                wordAudioUrl,
                                this);
                fmDownRecyclerView.setLayoutManager(new GridLayoutManager(requireActivity(), 1));
                fmDownRecyclerView.setAdapter(dowRecyclerAdapter);
            });
        });
    }


    @SuppressLint("Range")
    private void unitDownloadInfoReceiver() {

        unitImgUrl = new ArrayList<>();
        unitAudioUrl = new ArrayList<>();
        wordAudioUrl = new ArrayList<>();

        Cursor cursor = null;
        SQLiteDatabase unitDB = null;


        for (int dbIndex = 1; dbIndex <= 6; dbIndex++) {

            int index = 0;
            String[] unitImgLink = new String[30];
            String[] unitAudioLink = new String[30];
            String[] wordAudioLink = new String[30];


            unitDB = unitListDatabase(dbIndex).getReadableDatabase();

            cursor = unitDB.query(DB_NOTES.UNIT_TABLE,
                    new String[]{DB_NOTES.UNIT_IMG, DB_NOTES.UNIT_AUDIO, DB_NOTES.UNIT_COMPLETE_WORD_AUDIO},
                    null, null, null, null, null);
            if (cursor != null && cursor.getCount() != 0) {
                while (cursor.moveToNext()) {

                    final String unitImg = cursor.getString(cursor.getColumnIndex(DB_NOTES.UNIT_IMG));
                    final String unitAudio = cursor.getString(cursor.getColumnIndex(DB_NOTES.UNIT_AUDIO));
                    final String wordAudio = cursor.getString(cursor.getColumnIndex(DB_NOTES.UNIT_COMPLETE_WORD_AUDIO));

                    unitImgLink[index] = unitImg;
                    unitAudioLink[index] = unitAudio;
                    wordAudioLink[index] = wordAudio;
                    index++;
                }
                unitImgUrl.add(unitImgLink);
                unitAudioUrl.add(unitAudioLink);
                wordAudioUrl.add(wordAudioLink);
            }
        }
        assert cursor != null;
        cursor.close();
        unitDB.close();
    }

    private SQLiteOpenHelper unitListDatabase(int databaseNum) {
        final Context context = requireActivity();
        if (databaseNum == 1) {
            return new UnitDatabaseBookOne(context);
        } else if (databaseNum == 2) {
            return new UnitDatabaseBookTwo(context);
        } else if (databaseNum == 3) {
            return new UnitDatabaseBookThree(context);
        } else if (databaseNum == 4) {
            return new UnitDatabaseBookFour(context);
        } else if (databaseNum == 5) {
            return new UnitDatabaseBookFive(context);
        } else {
            return new UnitDatabaseBookSix(context);
        }
    }


    @SuppressLint("Range")
    private void wordDownloadInfoReceiver() {
        mulImgList = new ArrayList<>();
        Cursor cursor = null;
        SQLiteDatabase db = null;


        for (int dbIndex = 1; dbIndex <= 6; dbIndex++) {
            ArrayList<String[]> imgUrl = new ArrayList<>();

            db = wordListDatabase(dbIndex).getReadableDatabase();

            for (int unitIndex = 1; unitIndex <= 30; unitIndex++) {

                int index = 0;
                String[] wordImgLink = new String[20];

                cursor = db.query(DB_NOTES.NEUTRAL_WORD_TABLE + unitIndex,
                        new String[]{DB_NOTES.WORD_ID, DB_NOTES.WORD_IMG,
                                DB_NOTES.BOOK_NUMBER, DB_NOTES.UNIT_NUMBER},
                        null, null, null, null, null);

                if (cursor != null && cursor.getCount() != 0) {
                    while (cursor.moveToNext()) {
                        WordModel model = new WordModel();

                        final String image = cursor.getString(cursor.getColumnIndex(DB_NOTES.WORD_IMG));
                        final int id = cursor.getInt(cursor.getColumnIndex(DB_NOTES.WORD_ID));
                        final int bookNum = cursor.getInt(cursor.getColumnIndex(DB_NOTES.BOOK_NUMBER));
                        final int unitNum = cursor.getInt(cursor.getColumnIndex(DB_NOTES.UNIT_NUMBER));

                        model.setId(id);
                        //model.setWordImage(image);
                        model.setImgUri(image);
                        model.setBookNum(bookNum);
                        model.setUnitNum(unitNum);
                        wordImgLink[index] = image;
                        index++;
                    }
                }
                imgUrl.add(wordImgLink);
            }
            mulImgList.add(imgUrl);
        }
        assert cursor != null;
        cursor.close();
        db.close();
    }

    private SQLiteOpenHelper wordListDatabase(int databaseNum) {
        final Context context = requireActivity();
        if (databaseNum == 1) {
            return new WordDatabaseBookOne(context);
        } else if (databaseNum == 2) {
            return new WordDatabaseBookTwo(context);
        } else if (databaseNum == 3) {
            return new WordDatabaseBookThree(context);
        } else if (databaseNum == 4) {
            return new WordDatabaseBookFour(context);
        } else if (databaseNum == 5) {
            return new WordDatabaseBookFive(context);
        } else {
            return new WordDatabaseBookSix(context);
        }
    }


    @Override
    public void itemClicked(int position) {
        /*downloadDialog(1,
                mulImgList.get(dbNumber - 1 ).get(position),
                unitImgUrl.get(position),
                unitAudioUrl.get(position),
                wordAudioUrl.get(position));*/
    }

    private void downloadDialog(int dbInfoList,
                                String[] mulImgList,
                                String[] unitImgUrl,
                                String[] unitAudioUrl,
                                String[] wordAudioUrl) {
        DialogDownload dialogDownloadFragment =
                DialogDownload.newInstance(
                        requireActivity(),
                        "Image",
                        dbInfoList,
                        null);

        FragmentTransaction ft = requireActivity().getSupportFragmentManager().beginTransaction();
        Fragment prev = requireActivity().getSupportFragmentManager().findFragmentByTag("dialogDownloadFragment");

        if (prev != null) {
            ft.remove(prev);
        }
        dialogDownloadFragment.show(ft, "dialogDownloadFragment");
    }
}
