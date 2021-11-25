package com.barmej.guesstheanswer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class QuestionActivity extends AppCompatActivity {
    private TextView mTextViewQuestion;
    private String [] QUESTIONS;
    private static boolean [] ANSWERS={false,true,true,false};
    private String [] ANSWERS_DETAILS;



    private String mCureentQuestion, mCureenrAnswerDetails;
    private  boolean mCurrentAnswers;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences sh=getSharedPreferences("app_pref", MODE_PRIVATE);
        String appLang=sh.getString("app_lang","");
        if(!appLang.equals(""))// اول مايشتغل التطبيق بيتم استخدام لغة الجهاز ثم بعدها نحفظ اللغة اللي يختارها المستخدم
            LocalHelper.setLocale(this,appLang);

        setContentView(R.layout.activity_question);
        mTextViewQuestion=findViewById(R.id.text_view_question);
       QUESTIONS=getResources().getStringArray(R.array.questions);
       ANSWERS_DETAILS=getResources().getStringArray(R.array.answers_details);

        showNewQuestion();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==R.id.menuChangeLang){
            showLanguageDialog();
            return true;
        }
        else
        {return super.onOptionsItemSelected(item);
        }
    }
    private void showLanguageDialog(){
        AlertDialog alert=new AlertDialog.Builder(this)
                .setTitle(R.string.change_lang_text)
                .setItems(R.array.languages, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
                        String language = "ar";
                        switch (which) {
                            case 0:
                                language = "ar";
                                break;
                            case 1:
                                language = "en";
                                break;
                            case 2:
                                language = "fr";
                                break;
                        }
                      saveLanguage(language);
                      LocalHelper.setLocale(QuestionActivity.this, language);
                      Intent intent = new Intent(getApplicationContext(), QuestionActivity.class);
                      intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);// ازالة كل الاكتفيتي السابقة
                      intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);// تشغيل الاكتفيتي الجديدة
                      startActivity(intent);

                  }
              }).create();
      alert.show();
    }
    private void saveLanguage(String lang){ // حفظ اللغة الي اختارها المستخدم
        SharedPreferences sh=getSharedPreferences("app_pref", MODE_PRIVATE);
        SharedPreferences.Editor editor=sh.edit();
        editor.putString("app_lnag","lang");
        editor.apply();

    }

    public void showNewQuestion(){
        Random random=new Random();
        int randomQuestionIndex=random.nextInt(QUESTIONS.length);
        mCureentQuestion=QUESTIONS[randomQuestionIndex];
        mCureenrAnswerDetails=ANSWERS_DETAILS[randomQuestionIndex];
        mCurrentAnswers=ANSWERS[randomQuestionIndex];

        mTextViewQuestion.setText(mCureentQuestion);
    }

    public void onChanegQuestionOnClick(View view){
        showNewQuestion();
    }
    public void onTrureClicked(View view){
        if(mCurrentAnswers==true){
            Toast.makeText(this,"إجابة صحيحة",Toast.LENGTH_SHORT).show();
            showNewQuestion();
        }
        else
        {Toast.makeText(this,"إجابة خاطئة",Toast.LENGTH_SHORT).show();
        Intent intent=new Intent(QuestionActivity.this, AnswerActivity.class);
        intent.putExtra("Question Answer", mCureenrAnswerDetails);
        startActivity(intent);}


    }
    public void onFalseClicked(View view) {
        if (mCurrentAnswers == false) {
            Toast.makeText(this, "إجابة صحيحة", Toast.LENGTH_SHORT).show();
            showNewQuestion();
        } else{
            Toast.makeText(this, "إجابة خاطئة", Toast.LENGTH_SHORT).show();
        Intent intent=new Intent(QuestionActivity.this, AnswerActivity.class);
        intent.putExtra("Question Answer", mCureenrAnswerDetails);
        startActivity(intent);}

    }
    public void ShareClick(View view){
        Intent intent=new Intent(QuestionActivity.this, ShareActivity.class);
        intent.putExtra("Questions text extra", mCureentQuestion);
        startActivity(intent);
    }

}