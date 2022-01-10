package com.example.a4000essentialwordsbook1.DownloadClasses.Downloads;

import android.annotation.SuppressLint;
import android.app.DownloadManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

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
import com.example.a4000essentialwordsbook1.R;
import com.example.a4000essentialwordsbook1.StringNote.DB_NOTES.DB_NOTES;
import com.google.android.material.card.MaterialCardView;

import java.io.File;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class DialogDownload extends DialogFragment implements View.OnClickListener {


    private final Context context;
    private final String pausedMessage = "دسترسی شما به اینترنت قطع شده است!";
    private final String pendingMessage = "برنامه در انتظار اتصال به اینترنت است";
    //private RelativeLayout layout;
    private final int wrdImgCount = 600;
    private final String[] wordImageList = new String[wrdImgCount];
    private final int[] wordUnitNumList = new int[wrdImgCount];
    private final String[] unitImagesList = new String[30];
    private final String[] wrdAudioUrlList = new String[30];
    private final String[] unitAudioUrlList = new String[30];
    private final int byteToMB = 1000000;
    private final DownloadInterface downEventListener;
    private final File mainPath = new File(Environment.DIRECTORY_DOWNLOADS, File.separator + "4000 Essential Words");
    private final String unitAudioPath = "Unit Audios";
    private ProgressBar downProgressBar;
    private TextView anmTextView, ttlNumTxtView;
    private TextView prgPercentTextView, remainNumTextView;
    private MaterialCardView cnclDownloadCardView, noInternetCnclCardView;
    private RelativeLayout internetDownloadingLayout, noInternetLayout, failedDownloadLayout;
    private MaterialCardView failedDownloadCancelBtn, retryDownloadBtn;
    private ImageView pausePendingImageView;
    private int dbNum;
    private String whtDown;
    private int downNumber;
    private float ttlSizeDown;
    private SharedPreferences imgSizePreferences, audioPreferences;
    private boolean downReRunning = false;
    private boolean cancelMainLoop = false;

    public DialogDownload(Context context,
                          DownloadInterface downEventListener) {
        this.downEventListener = downEventListener;
        this.context = context;
    }

    public static DialogDownload newInstance(Context dowContext,
                                             String whtDown,
                                             int dbNum,
                                             DownloadInterface downFailureInterface) {
        DialogDownload dialogDownload = new DialogDownload(
                dowContext,
                downFailureInterface);
        Bundle downBundle = new Bundle();
        downBundle.putInt("dbNum", dbNum);
        downBundle.putString("whtDown", whtDown);
        dialogDownload.setArguments(downBundle);
        return dialogDownload;
    }

    @Override
    public void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dbNum = requireArguments().getInt("dbNum");
        whtDown = requireArguments().getString("whtDown");

    }

    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_download, container, false);
        setRadiusPreparation();
        setCancelable(false);
        return view;
    }

    private void setRadiusPreparation() {
        if (getDialog() != null && getDialog().getWindow() != null) {
            getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        downDialogFindViews(view);


        whtDownFunctions();
    }

    private String downAnnouncement() {
        if (dbNum == 1) {
            return " درحال دانلود فایل های" + whtDownString() + "کتاب  اول";
        } else if (dbNum == 2) {
            return " درحال دانلود فایل های" + whtDownString() + "کتاب  دوم";
        } else if (dbNum == 3) {
            return " درحال دانلود فایل های" + whtDownString() + "کتاب  سوم";
        } else if (dbNum == 4) {
            return " درحال دانلود فایل های" + whtDownString() + "کتاب  چهارم";
        } else if (dbNum == 5) {
            return " درحال دانلود فایل های" + whtDownString() + "کتاب  پنجم";
        } else {
            return " درحال دانلود فایل های" + whtDownString() + "کتاب  ششم";
        }
    }

    private String whtDownString() {
        if (whtDown.equalsIgnoreCase("Image")) {
            return " تصویری ";
        } else {
            return " صوتی ";
        }
    }

    private void whtDownFunctions() {
        if (whtDown.equalsIgnoreCase("Image")) {
            ImageDownProgressBarSetter();
            ttlNumTxtView.setText(String.valueOf(630));
            downloadImagesFunctions();
        } else {
            AudioProgressBarrSetter();
            ttlNumTxtView.setText(String.valueOf(60));
            audiosDownloadFunctions();
        }
    }

    private void ImageDownProgressBarSetter() {
        downProgressBar.setMax(imgProgressMaxVal());
    }

    private void AudioProgressBarrSetter() {
        downProgressBar.setMax(audioProgressMaxVal());
    }

    private int imgProgressMaxVal() {
        if (dbNum == 1) {
            return 15822848;
        } else if (dbNum == 2) {
            return (int) 15.5 * byteToMB;
        } else if (dbNum == 3) {
            return (int) 15.5 * byteToMB;
        } else if (dbNum == 4) {
            return (int) 15.7 * byteToMB;
        } else if (dbNum == 5) {
            return (int) 15.9 * byteToMB;
        } else {
            return (int) 15.6 * byteToMB;
        }
    }

    private int audioProgressMaxVal() {
        if (dbNum == 1) {
            return 169320448;
        } else if (dbNum == 2) {
            return (150 * byteToMB);
        } else if (dbNum == 3) {
            return (152 * byteToMB);
        } else if (dbNum == 4) {
            return (166 * byteToMB);
        } else if (dbNum == 5) {
            return (171 * byteToMB);
        } else {
            return (165 * byteToMB);
        }
    }

    private void downloadImagesFunctions() {
        ExecutorService thread = Executors.newSingleThreadExecutor();
        thread.execute(() -> {
            try {
                wordImageListDataReceiver(dbNum);
                unitImageListReceiver(dbNum);
                wordImageDownloader(wordImageList);
                unitImageDownloader(unitImagesList);
            } catch (Exception e) {
                //cursor_status
                Log.e("imageDownError", "" + e);
            }
        });
    }

    @SuppressLint("Range")
    private void wordImageDownloader(String[] imgUrlList) {
        downNumber = getImgNum(dbNum);
        ttlSizeDown = getImgSize(dbNum);


        Handler wrdImgRunOnUiThread = new Handler(Looper.getMainLooper());


        final File imgMainPath = new File("Image Files");
        final File wordImgPath = new File(imgMainPath, File.separator + "Word Images");
        final File wordImgBookPath = new File(wordImgPath, File.separator + "Book_" + dbNum);


        for (int index = 0; index < wrdImgCount; index++) {

            final File wordUnitImgBookPath = new File(wordImgBookPath, File.separator + "Unit_" + wordUnitNumList[index]);
            Uri downloadUri = Uri.parse(imgUrlList[index]);

            final File secondSubFile = new File(wordUnitImgBookPath, File.separator + "." + new File(imgUrlList[index]).getName());
            final File file = new File(Environment.getExternalStoragePublicDirectory(mainPath.toString()), secondSubFile.toString());


            final boolean imgExists = file.exists();
            boolean[] cancelFlags;
            if (!imgExists) {

                DownloadManager.Request wrdImageRequest = new DownloadManager.Request(downloadUri);
                File path = new File(imgUrlList[index]);
                String imageName = path.getName();
                wrdImageRequest.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI | DownloadManager.Request.NETWORK_MOBILE)
                        .setAllowedOverRoaming(true)
                        .setTitle(imageName)
                        .setNotificationVisibility(DownloadManager.Request.VISIBILITY_HIDDEN)
                        .setDestinationInExternalPublicDir(mainPath.toString(), secondSubFile.toString());


                DownloadManager wrdImgDownloadManager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);

                long downloadID = wrdImgDownloadManager.enqueue(wrdImageRequest);
                boolean[] downloading = {true};

                while (downloading[0]) {
                    DownloadManager.Query wrdImgDQ = new DownloadManager.Query();
                    wrdImgDQ.setFilterById(downloadID);
                    Cursor cursor = wrdImgDownloadManager.query(wrdImgDQ);
                    cursor.moveToFirst();

                    int downloadStatus = cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_STATUS));
                    Log.e("cursor_status", cursor.toString());
                    String column_status = "No Status Available";
                    final float soFarSize = cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_BYTES_DOWNLOADED_SO_FAR));
                    final int ttlSize = cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_TOTAL_SIZE_BYTES));


                    if (downloadStatus == DownloadManager.STATUS_SUCCESSFUL) {
                        column_status = "wrdImage_STATUS_SUCCESSFUL";


                        imageDownInSuccess(soFarSize, wrdImgRunOnUiThread);
                        downloading[0] = false;
                    } else if (downloadStatus == DownloadManager.STATUS_FAILED) {
                        column_status = "wrdImage_STATUS_FAILED";
                        cancelFlags = imageDownInFailed(wrdImgDownloadManager, downloadID, wrdImgRunOnUiThread, file);

                        downloading[0] = cancelFlags[0];
                        cancelMainLoop = cancelFlags[1];

                    } else if (downloadStatus == DownloadManager.STATUS_RUNNING) {
                        column_status = "wrdImage_STATUS_RUNNING";

                        cancelFlags = imageDownInRunning(wrdImgRunOnUiThread, wrdImgDownloadManager, downloadID);

                        downloading[0] = cancelFlags[0];
                        cancelMainLoop = cancelFlags[1];

                    } else if (downloadStatus == DownloadManager.STATUS_PAUSED) {

                        column_status = "wrdImage_STATUS_PAUSED";
                        cancelFlags = imageDownInPause(wrdImgDownloadManager, downloadID, wrdImgRunOnUiThread, pausedMessage);

                        downloading[0] = cancelFlags[0];
                        cancelMainLoop = cancelFlags[1];

                        downReRunning = (downloading[0] && !cancelMainLoop);

                    } else if (downloadStatus == DownloadManager.STATUS_PENDING) {
                        column_status = "DownloadManager.STATUS_PENDING";

                        cancelFlags = imageDownInPause(wrdImgDownloadManager, downloadID, wrdImgRunOnUiThread, pendingMessage);

                        downloading[0] = cancelFlags[0];
                        cancelMainLoop = cancelFlags[1];

                        downReRunning = (downloading[0] && !cancelMainLoop);
                    }

                    wrdImgRunOnUiThread.post(() -> {
                        downProgressBar.setMax(ttlSize);
                        downProgressBar.setProgress((int) soFarSize);
                    });


                    Log.d("wrdImage_down_status", column_status);


                    cursor.close();
                }

            }
            if (cancelMainLoop) {
                break;
            }
        }
    }

    @SuppressLint("Range")
    private void unitImageDownloader(String[] unitImgUrlList) {

        Handler runOnUiThread = new Handler(Looper.getMainLooper());

        final File imgMainPath = new File("Image Files");
        final File wordImgPath = new File(imgMainPath, File.separator + "Unit Images");
        final File unitImgBookPath = new File(wordImgPath, File.separator + "Book_" + dbNum);


        for (int index = 0; index < 30; index++) {
            final File secondSubFile = new File(unitImgBookPath, File.separator + "." + new File(unitImgUrlList[index]).getName());
            final File file = new File(Environment.getExternalStoragePublicDirectory(mainPath.toString()), secondSubFile.toString());
            final boolean imgExists = file.exists();
            boolean[] cancelFlags;
            boolean[] cancelMainLoop = {false};

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


                DownloadManager dm = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
                final long downloadID = dm.enqueue(request);


                boolean[] downloading = {true};
                while (downloading[0]) {
                    DownloadManager.Query dq = new DownloadManager.Query();
                    dq.setFilterById(downloadID);

                    Cursor cursor = dm.query(dq);
                    cursor.moveToFirst();

                    int downloadStatus = cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_STATUS));
                    String column_status = "No Status Available";
                    float soFarSize = cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_BYTES_DOWNLOADED_SO_FAR));
                    float ttlSize = cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_TOTAL_SIZE_BYTES));


                    if (downloadStatus == DownloadManager.STATUS_SUCCESSFUL) {

                        imageDownInSuccess(soFarSize, runOnUiThread);
                        downloading[0] = false;

                    } else if (downloadStatus == DownloadManager.STATUS_FAILED) {
                        column_status = "wrdImage_STATUS_FAILED";
                        cancelFlags = imageDownInFailed(dm, downloadID, runOnUiThread, file);

                        downloading[0] = cancelFlags[0];
                        cancelMainLoop[0] = cancelFlags[1];
                    } else if (downloadStatus == DownloadManager.STATUS_RUNNING) {
                        column_status = "wrdImage_STATUS_RUNNING";

                        cancelFlags = imageDownInRunning(runOnUiThread, dm, downloadID);

                        downloading[0] = cancelFlags[0];
                        cancelMainLoop[0] = cancelFlags[1];

                    } else if (downloadStatus == DownloadManager.STATUS_PAUSED) {
                        column_status = "wrdImage_STATUS_PAUSED";
                        downReRunning = true;
                        cancelFlags = imageDownInPause(dm, downloadID, runOnUiThread, pausedMessage);

                        downloading[0] = cancelFlags[0];
                        cancelMainLoop[0] = cancelFlags[1];

                    } else if (downloadStatus == DownloadManager.STATUS_PENDING) {
                        column_status = "wrdImage_STATUS_PENDING";

                        downReRunning = true;
                        cancelFlags = imageDownInPause(dm, downloadID, runOnUiThread, pendingMessage);

                        downloading[0] = cancelFlags[0];
                        cancelMainLoop[0] = cancelFlags[1];
                    }
                    cursor.close();


                    runOnUiThread.post(() -> {
                        downProgressBar.setMax((int) ttlSize);
                        downProgressBar.setProgress((int) soFarSize);
                    });
                    Log.d("audioImage_down_status", column_status);
                }
            }
            if (cancelMainLoop[0]) {
                break;
            }
            if (index == 29) {
                runOnUiThread.postDelayed(this::dismiss, 500);
            }

        }
    }

    private void percentTextViewDeterminer() {
        final int percent = (downNumber * 100) / totalNumber();
        prgPercentTextView.setText(String.valueOf(percent));
    }

    private int totalNumber() {
        return (whtDown.equalsIgnoreCase("Image")) ? 630 : 60;
    }

    private void audiosDownloadFunctions() {
        ExecutorService audioThread = Executors.newSingleThreadExecutor();
        audioThread.execute(() -> {
            try {
                downNumber = getAudioNum(dbNum);
                ttlSizeDown = getAudioSize(dbNum);

                wordAudioReceiver(dbNum);
                audioDownloaderFunctions();
            } catch (Exception e) {
                Log.e("audiosDownError", "" + e);
            }
        });

    }

    private void audioDownloaderFunctions() {
        for (int index = 0; index < 30; index++) {
            final String wordAudioPath = "Word Audios";
            audioDownloader(wrdAudioUrlList, wordAudioPath, index);
            if (cancelMainLoop) {
                break;
            }
            audioDownloader(unitAudioUrlList, unitAudioPath, index);
            if (cancelMainLoop) {
                break;
            }
        }
    }

    @SuppressLint("Range")
    private void audioDownloader(String[] unitAudioList, String whtAudio, int index) {


        final Handler runOnUiThread = new Handler(Looper.getMainLooper());


        final File audioMainPath = new File("Audio Files");
        final File audioUnitPath = new File(audioMainPath, File.separator + whtAudio);
        final File audioUnitBookPath = new File(audioUnitPath, File.separator + "Book_" + dbNum);

        final File secondSubFile = new File(audioUnitBookPath, File.separator + "." + new File(unitAudioList[index]).getName());
        final File file = new File(Environment.getExternalStoragePublicDirectory(mainPath.toString()), secondSubFile.toString());
        final boolean audioExists = file.exists();

        if (!audioExists) {
            Uri downloadUri = Uri.parse(unitAudioList[index]);

            DownloadManager.Request request = new DownloadManager.Request(downloadUri);
            File path = new File(unitAudioList[index]);
            String imageName = path.getName();
            request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI | DownloadManager.Request.NETWORK_MOBILE)
                    .setAllowedOverRoaming(true)
                    .setTitle(imageName)
                    .setNotificationVisibility(DownloadManager.Request.VISIBILITY_HIDDEN)
                    .setDestinationInExternalPublicDir(mainPath.toString(), secondSubFile.toString());


            DownloadManager dm = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
            final long downloadID = dm.enqueue(request);

            boolean[] downloading = {true};

            while (downloading[0]) {
                DownloadManager.Query wrdImgDQ = new DownloadManager.Query();
                wrdImgDQ.setFilterById(downloadID);
                Cursor cursor = dm.query(wrdImgDQ);
                cursor.moveToFirst();

                String column_status = "No Status Available";
                final int downloadStatus = cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_STATUS));
                final float soFarSize = cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_BYTES_DOWNLOADED_SO_FAR));
                final float ttlSize = cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_TOTAL_SIZE_BYTES));


                // downloading, cancelLoop
                boolean[] cancelFlags;
                if (downloadStatus == DownloadManager.STATUS_SUCCESSFUL) {

                    column_status = "UNIT_STATUS_SUCCESSFUL";
                    audioDownloadInSuccess(soFarSize, runOnUiThread);
                    downloading[0] = false;

                } else if (downloadStatus == DownloadManager.STATUS_FAILED) {

                    column_status = "UNIT_STATUS_FAILED";
                    cancelFlags = audioDownInFailed(dm, downloadID, runOnUiThread, file);
                    downloading[0] = cancelFlags[0];
                    cancelMainLoop = cancelFlags[1];


                } else if (downloadStatus == DownloadManager.STATUS_RUNNING) {
                    column_status = "UNIT_STATUS_RUNNING";
                    cancelFlags = audioDownInRunning(runOnUiThread, dm, downloadID);
                    downloading[0] = cancelFlags[0];
                    cancelMainLoop = cancelFlags[1];
                } else if (downloadStatus == DownloadManager.STATUS_PAUSED) {

                    column_status = "UNIT_STATUS_PAUSED";
                    downReRunning = true;
                    cancelFlags = audioDownInPause(dm, downloadID, runOnUiThread, pausedMessage);
                    downloading[0] = cancelFlags[0];
                    cancelMainLoop = cancelFlags[1];
                } else if (downloadStatus == DownloadManager.STATUS_PENDING) {
                    downReRunning = true;
                    cancelFlags = audioDownInPause(dm, downloadID, runOnUiThread, pendingMessage);
                    downloading[0] = cancelFlags[0];
                    cancelMainLoop = cancelFlags[1];
                }


                cursor.close();

                runOnUiThread.post(() -> {
                    downProgressBar.setMax((int) ttlSize);
                    downProgressBar.setProgress((int) soFarSize);
                });

                Log.d("untAudioColumnStatus", column_status);
            }
        }

        if (whtAudio.equalsIgnoreCase(unitAudioPath) && (index == 29)) {
            runOnUiThread.postDelayed(this::dismiss, 1000);
        }
    }


    private boolean[] audioDownInPause(DownloadManager dm, long downId, Handler audioRunOnUiThread, String message) {
        boolean[] downloading = new boolean[]{true, false};
        if (isNetWorkConnected() && disConnectedVisibility()) {
            audioRunOnUiThread.postDelayed(() -> {
                if (isNetWorkConnected()) {
                    mainDownLayoutVisibility(View.GONE, View.VISIBLE, View.GONE);
                    Log.d("layoutVisibility", "" + internetDownloadingLayout.getVisibility());

                    NoInternetTextContent(message);

                    ImageView imageView = new ImageView(context);
                    imageView.setImageResource(R.drawable.failed_icon);

                    noInternetCnclCardView.setOnClickListener(view -> {

                        downloading[0] = false;
                        downloading[1] = true;
                        cancelDownloadManager(dm, downId);
                        dismiss();
                    });
                }
            }, 3000);
        }
        return downloading;
    }


    private boolean[] audioDownInRunning(Handler audioRunOnUiThread, DownloadManager dm, long downId) {
        boolean[] downloading = new boolean[2];
        downloading[0] = true;

        audioRunOnUiThread.post(() -> {
            mainDownLayoutVisibility(View.VISIBLE, View.GONE, View.GONE);

            cnclDownloadCardView.setOnClickListener(view -> {
                cancelDownloadManager(dm, downId);
                downloading[0] = false;
                downloading[1] = true;
                dismiss();
            });
            InternetTextContent();

            if (downReRunning) {
                downReRunning = false;
            }
        });

        return downloading;
    }

    private boolean[] audioDownInFailed(DownloadManager dm, long downId, Handler audioRunOnUiThread, File file) {
        dm.remove(downId);
        audioRunOnUiThread.postDelayed(() -> {
            mainDownLayoutVisibility(View.GONE, View.GONE, View.VISIBLE);
            deleterFiledFile(file);
            downReRunning = true;
        }, 5000);
        return new boolean[]{false, true};
    }

    private void audioDownloadInSuccess(float soFarSize, Handler audioRunOnUiThread) {
        ttlSizeDown += soFarSize;
        Log.d("successAudioDown", "" + soFarSize);
        downNumber++;
        preferenceAudioValues(downNumber, ttlSizeDown, dbNum);

        audioRunOnUiThread.post(() -> {
            remainNumTextView.setText(String.valueOf(downNumber));
            percentTextViewDeterminer();
        });
    }


    private boolean[] imageDownInPause(DownloadManager dm, long downId, Handler imageRunOnUiThread, String message) {
        boolean[] downloading = new boolean[]{true, false};
        if (isNetWorkConnected() && disConnectedVisibility()) {
            imageRunOnUiThread.postDelayed(() -> {
                if (isNetWorkConnected()) {
                    mainDownLayoutVisibility(View.GONE, View.VISIBLE, View.GONE);

                    NoInternetTextContent(message);

                    ImageView imageView = new ImageView(context);
                    imageView.setImageResource(R.drawable.failed_icon);

                    noInternetCnclCardView.setOnClickListener(view -> {
                        downloading[0] = false;
                        downloading[1] = true;
                        cancelDownloadManager(dm, downId);
                        dismiss();
                    });
                }
            }, 3000);
        }
        return downloading;
    }

    private boolean[] imageDownInRunning(Handler imageRunOnUiThread, DownloadManager dm, long downId) {
        boolean[] downloading = new boolean[2];
        downloading[0] = true;

        imageRunOnUiThread.post(() -> {
            mainDownLayoutVisibility(View.VISIBLE, View.GONE, View.GONE);

            cnclDownloadCardView.setOnClickListener(view -> {
                cancelDownloadManager(dm, downId);
                downloading[0] = false;
                downloading[1] = true;
                dismiss();
            });
            InternetTextContent();

            if (downReRunning) {
                downReRunning = false;
            }
        });

        return downloading;

    }

    private boolean[] imageDownInFailed(DownloadManager dm, long downId, Handler wrdImgRunOnUiThread, File file) {

        dm.remove(downId);
        wrdImgRunOnUiThread.postDelayed(() -> {
            mainDownLayoutVisibility(View.GONE, View.GONE, View.VISIBLE);
            deleterFiledFile(file);
            downReRunning = true;

        }, 5000);

        return new boolean[]{false, true};

    }

    private void imageDownInSuccess(float soFarSize, Handler wrdImgRunOnUiThread) {
        ttlSizeDown += soFarSize;
        downNumber++;
        preferenceImgValues(downNumber, ttlSizeDown, dbNum);

        wrdImgRunOnUiThread.post(() -> {
            remainNumTextView.setText(String.valueOf(downNumber));
            percentTextViewDeterminer();
        });
    }


    private boolean disConnectedVisibility() {
        return noInternetLayout.getVisibility() == View.GONE;
    }

    private void deleterFiledFile(File file) {
        if (file.exists()) {
            if (file.delete()) {
                Log.d("untAudioDelete", "" + file.delete());
            }
        }
    }


    private void mainDownLayoutVisibility(int INTERNET_VISIBILITY, int NO_INTERNET_VISIBILITY, int FAILED_VISIBILITY) {
        failedDownloadLayout.setVisibility(FAILED_VISIBILITY);
        noInternetLayout.setVisibility(NO_INTERNET_VISIBILITY);
        internetDownloadingLayout.setVisibility(INTERNET_VISIBILITY);
    }


    private boolean isNetWorkConnected() {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() == null || !cm.getActiveNetworkInfo().isConnected();
    }

    private void NoInternetTextContent(String message) {
        anmTextView.setText(message);

        Glide.with(context)
                .load(pausePendingImage(message))
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                .placeholder(R.drawable.download_icon_image)
                .error(R.drawable.download_icon_image)
                .into(pausePendingImageView);
    }

    private int pausePendingImage(String message) {
        if (message.equalsIgnoreCase(pendingMessage)) {
            return R.drawable.waiting_gif;
        } else {
            return R.drawable.warning_icon;
        }
    }


    private void InternetTextContent() {
        anmTextView.setText(downAnnouncement());
    }


    private void cancelDownloadManager(DownloadManager dm, long dmID) {
        dm.remove(dmID);
    }


    @SuppressLint("Range")
    private void wordAudioReceiver(int dbNumber) {
        SQLiteDatabase db = unitListDatabase(dbNumber).getReadableDatabase();
        int index = 0;

        Cursor awCursor = db.query(DB_NOTES.UNIT_TABLE,
                new String[]{DB_NOTES.UNIT_COMPLETE_WORD_AUDIO, DB_NOTES.UNIT_AUDIO},
                null, null,
                null, null, null);

        if (awCursor != null && awCursor.getCount() != 0) {
            while (awCursor.moveToNext()) {
                wrdAudioUrlList[index] = awCursor.getString(awCursor.getColumnIndex(DB_NOTES.UNIT_COMPLETE_WORD_AUDIO));
                unitAudioUrlList[index] = awCursor.getString(awCursor.getColumnIndex(DB_NOTES.UNIT_AUDIO));
                index++;
            }
        }

        assert awCursor != null;
        awCursor.close();
        db.close();
    }


    @SuppressLint("Range")
    private void wordImageListDataReceiver(final int dbNumber) {
        int index = 0;

        SQLiteDatabase db = wordListDatabase(dbNumber).getReadableDatabase();
        Cursor cursor = null;

        for (int unitIndex = 1; unitIndex <= 30; unitIndex++) {
            cursor = db.query(DB_NOTES.NEUTRAL_WORD_TABLE + unitIndex,
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
    private void unitImageListReceiver(final int dbNumber) {
        SQLiteDatabase db = unitListDatabase(dbNumber).getReadableDatabase();
        int index = 0;

        Cursor awCursor = db.query(DB_NOTES.UNIT_TABLE,
                new String[]{DB_NOTES.UNIT_IMG},
                null, null, null, null, null);

        if (awCursor != null && awCursor.getCount() != 0) {
            while (awCursor.moveToNext()) {
                unitImagesList[index] = awCursor.getString(awCursor.getColumnIndex(DB_NOTES.UNIT_IMG));
                index++;
            }
        }

        assert awCursor != null;
        awCursor.close();
        db.close();
    }

    private SQLiteOpenHelper unitListDatabase(final int unitId) {
        if (unitId == 1) {
            return new UnitDatabaseBookOne(context);
        } else if (unitId == 2) {
            return new UnitDatabaseBookTwo(context);
        } else if (unitId == 3) {
            return new UnitDatabaseBookThree(context);
        } else if (unitId == 4) {
            return new UnitDatabaseBookFour(context);
        } else if (unitId == 5) {
            return new UnitDatabaseBookFive(context);
        } else {
            return new UnitDatabaseBookSix(context);
        }
    }

    private SQLiteOpenHelper wordListDatabase(final int databaseNum) {
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


    private void preferenceImgValues(int number, float size, int dbNumber) {
        imgSizePreferences = context.getSharedPreferences("image_size_" + dbNumber, Context.MODE_PRIVATE);
        SharedPreferences.Editor sizeEdit = imgSizePreferences.edit();
        sizeEdit.putInt("Img_Num", number);
        sizeEdit.putFloat("Img_Size", size);
        sizeEdit.apply();
    }

    private void preferenceAudioValues(int number, float size, int dbNumber) {
        audioPreferences = context.getSharedPreferences("audio_size_" + dbNumber, Context.MODE_PRIVATE);
        SharedPreferences.Editor sizeEdit = audioPreferences.edit();
        sizeEdit.putInt("Audio_Num", number);
        sizeEdit.putFloat("Audio_Size", size);
        sizeEdit.apply();
    }

    private float getImgSize(int dbNumber) {
        imgSizePreferences = context.getSharedPreferences("image_size_" + dbNumber, Context.MODE_PRIVATE);
        return imgSizePreferences.getFloat("Img_Size", 0f);
    }

    private int getImgNum(int dbNum) {
        imgSizePreferences = context.getSharedPreferences("image_size_" + dbNum, Context.MODE_PRIVATE);
        return imgSizePreferences.getInt("Img_Num", 0);
    }

    private float getAudioSize(int dbNumber) {
        audioPreferences = context.getSharedPreferences("audio_size_" + dbNumber, Context.MODE_PRIVATE);
        return audioPreferences.getFloat("Audio_Size", 0f);
    }

    private int getAudioNum(int dbNum) {
        audioPreferences = context.getSharedPreferences("audio_size_" + dbNum, Context.MODE_PRIVATE);
        return audioPreferences.getInt("Audio_Num", 0);
    }

    private void downDialogFindViews(View view) {
        downProgressBar = view.findViewById(R.id.download_dialog_progressbar);
        anmTextView = view.findViewById(R.id.dialog_download_announcement_text_view);
        prgPercentTextView = view.findViewById(R.id.download_dialog_progress_percent_text_view);
        remainNumTextView = view.findViewById(R.id.download_dialog_number_done_text_view);
        ttlNumTxtView = view.findViewById(R.id.download_dialog_total_number_text_view);

        internetDownloadingLayout = view.findViewById(R.id.download_dialog_downloading_relative_layout);
        noInternetLayout = view.findViewById(R.id.download_dialog_no_internet_relative_layout);
        failedDownloadLayout = view.findViewById(R.id.download_dialog_failed_download_relative_layout);

        cnclDownloadCardView = view.findViewById(R.id.download_dialog_cancel_card_view);
        noInternetCnclCardView = view.findViewById(R.id.download_dialog_cancel_no_internet_card_view);

        failedDownloadCancelBtn = view.findViewById(R.id.download_dialog_failed_download_cancel_download_btn);
        retryDownloadBtn = view.findViewById(R.id.download_dialog_failed_download_retry_download_btn);

        pausePendingImageView = view.findViewById(R.id.download_dialog_warning_icon_image_view);

        anmTextView.setText(downAnnouncement());
        ViewsValueInitializer();
        thisClickListener();
    }

    private void thisClickListener() {
        failedDownloadCancelBtn.setOnClickListener(this);
        retryDownloadBtn.setOnClickListener(this);
    }

    private void ViewsValueInitializer() {
        remainNumTextView.setText(String.valueOf(remainNum()));
    }

    private int remainNum() {
        if (whtDown.equalsIgnoreCase("Image")) {
            return getImgNum(dbNum);
        } else {
            return getAudioNum(dbNum);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case (R.id.download_dialog_failed_download_cancel_download_btn):
                dismiss();
                break;
            case (R.id.download_dialog_failed_download_retry_download_btn):
                dismiss();
                downEventListener.itemListener(itemPosition(), whtDown);
                break;
        }
    }

    private int itemPosition() {
        return dbNum - 1;
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        downEventListener.downDialogDismissed();
    }
}
