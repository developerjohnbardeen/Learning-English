package com.example.a4000essentialwordsbook1.MarkedWords;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Environment;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.example.a4000essentialwordsbook1.MarkedWords.ReviewWords.MainReviewMarkedWordActivity;
import com.example.a4000essentialwordsbook1.QuizFile.QuizRezult.QuizResultFragments.CorrectAnswer.RecyclerViewCorrectAnswer;
import com.example.a4000essentialwordsbook1.R;
import com.example.a4000essentialwordsbook1.Models.WordModel;
import com.example.a4000essentialwordsbook1.StringNote.DB_NOTES.ExtraNotes;

import java.io.File;
import java.util.ArrayList;



public class RecyclerViewMarkedWords extends RecyclerView.Adapter<RecyclerViewMarkedWords.ViewHolder> implements View.OnClickListener{
    private final ArrayList<WordModel> list;
    private final Context context;
    private final LayoutInflater inflater;
    private final int dbNum, unitNum;
    private final String sDbNumber = ExtraNotes.DB_NUMBER;
    private final String sUnitNumber = ExtraNotes.UNIT_NUMBER;
    private final String sWordId = ExtraNotes.WORD_ID;
    private final MarkedWordRemover wordRemover;
    private final MarkedWordPosition itemPositionInterface;

    public RecyclerViewMarkedWords(Context context, ArrayList<WordModel> list, int[] dbInfoList,
                                   MarkedWordRemover remover, MarkedWordPosition itemPosition){
        this.list = list;
        this.context = context;
        this.inflater = LayoutInflater.from(context);
        this.dbNum = dbInfoList[0];
        this.unitNum = dbInfoList[1];
        this.wordRemover = remover;
        this.itemPositionInterface = itemPosition;
    }


    @NonNull
    @Override
    public RecyclerViewMarkedWords.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.marked_word_item, parent, false);
        return new ViewHolder(view, list, itemPositionInterface);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewMarkedWords.ViewHolder holder, int position) {
        viewsGetterAndSetter(holder, position);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }



    private void viewsGetterAndSetter(ViewHolder holder, int position){
        WordModel markedList = list.get(position);

        int image = markedList.getWordImage();
        String word = markedList.getWord();
        String phonetic = markedList.getPhonetic();

        viewValuesSetter(holder, position, image, word, phonetic);
    }

    private void viewValuesSetter(ViewHolder holder, int position, int image, String word, String phonetic){

        Glide.with(context)
                .load(image)
                .placeholder(R.drawable.loadimg)
                .error(R.drawable.loadimg)
                .into(holder.wordImage);
        setImageResources(holder, list.get(position));

        holder.wordName.setText(word);
        holder.wordPhonetic.setText(phonetic);
        holder.cardView.setOnClickListener(v -> intentActivity(position));

    }

    private void setImageResources(ViewHolder holder, WordModel model){
        final String appPath = context.getApplicationInfo().dataDir;

        final File imageDir = new File(Environment.DIRECTORY_DOWNLOADS, File.separator + "4000 Essential Words");

        final File imgMainPath = new File("Image Files");
        final File wordImgPath = new File(imgMainPath, File.separator + "Word Images");
        final File wordImgBookPath = new File(wordImgPath, File.separator + "Book_" + model.getBookNum());
        final File wordUnitImgBookPath = new File(wordImgBookPath, File.separator + "Unit_" + model.getUnitNum());

        final File imgName = new File(wordUnitImgBookPath, File.separator + "." + new File(model.getImgUri()).getName());
        final File imgFile = new File(Environment.getExternalStoragePublicDirectory(imageDir.toString()), imgName.toString());


        if (imgFile.exists()){
            Drawable imgDrawable = Drawable.createFromPath(imgFile.toString());
            Glide.with(context)
                    .load(imgDrawable)
                    .placeholder(R.drawable.loadimg)
                    .error(R.drawable.loadimg)
                    .into(holder.wordImage);
        }else {
            Glide.with(context)
                    .load(model.getImgUri())
                    .placeholder(R.drawable.loadimg)
                    .error(R.drawable.loadimg)
                    .into(holder.wordImage);
        }
    }


    private void intentActivity(int position){
        Intent intent = new Intent(context, MainReviewMarkedWordActivity.class);
        intent.putParcelableArrayListExtra("reviewList", list);
        intent.putExtra("cardPosition", position);
        intent.putExtra(sDbNumber, dbNum);
        intent.putExtra(sUnitNumber, unitNum);
        context.startActivity(intent);
    }

    @Override
    public void onClick(View v) {

    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        CardView cardView;
        ImageView wordImage;
        ImageButton imgRemoveButton, playAudioBtn;
        MarkedWordPosition itemPositionInterface;
        TextView wordName, wordTranslate, wordPhonetic;
        ArrayList<WordModel> holderList;

        public ViewHolder(@NonNull View itemView, ArrayList<WordModel> holderList, MarkedWordPosition itemPositionInterface) {
            super(itemView);

            this.holderList = holderList;
            this.itemPositionInterface = itemPositionInterface;

            cardView = itemView.findViewById(R.id.marked_word_card_view);
            wordImage = itemView.findViewById(R.id.img_word_marked);
            wordName = itemView.findViewById(R.id.marked_words_name);
            wordPhonetic = itemView.findViewById(R.id.marked_words_phonetic);
            imgRemoveButton = itemView.findViewById(R.id.marked_btn_word_changer);
            wordTranslate = itemView.findViewById(R.id.marked_words_translate);
            playAudioBtn = itemView.findViewById(R.id.play_audio_marked_word);

            onThisViewHolderClickListener();
         }

         private void onThisViewHolderClickListener(){
             imgRemoveButton.setOnClickListener(this);
             playAudioBtn.setOnClickListener(this);
             wordImage.setOnClickListener(this);

         }



        @Override
        public void onClick(View v) {
            final int dbId = holderList.get(getLayoutPosition()).getBookNum();
            final int unitId = holderList.get(getLayoutPosition()).getUnitNum();
            final int wordId = holderList.get(getLayoutPosition()).getId();

            switch (v.getId()){
                case (R.id.marked_btn_word_changer):
                    itemRemover(dbId, unitId, wordId);
                    break;
                case (R.id.img_word_marked):
                case (R.id.play_audio_marked_word):
                    audioPlayer(getLayoutPosition());
                    break;
            }

        }
        private void itemRemover(int dbId, int unitId, int wordId){
            wordRemover.removeMarkedWord(dbId, unitId, getLayoutPosition(), wordId);
            itemPositionInterface.recyclerItemPosition(getLayoutPosition());
        }
        private void audioPlayer(int position){
            wordRemover.audioPlayer(position);
        }
    }
}
