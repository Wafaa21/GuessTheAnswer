package com.barmej.guesstheanswer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class ShareActivity extends AppCompatActivity {
private String mQuestionText;
public EditText mEditTextShareTitle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share);
        mEditTextShareTitle=findViewById(R.id.editTextTextPersonName);
        mQuestionText=getIntent().getStringExtra("Questions text extra");
        SharedPreferences sharep=getSharedPreferences("app pref", MODE_PRIVATE);
        String questionTitle=sharep.getString("share title","");
        mEditTextShareTitle.setText(questionTitle);
    }
    public void shareClick(View view){
        String questionTitle=mEditTextShareTitle.getText().toString();
        Intent shareIntent=new Intent();
        shareIntent.setAction(Intent.ACTION_SEND);
        shareIntent.putExtra(Intent.EXTRA_TEXT,questionTitle+"\n"+mQuestionText);
        shareIntent.setType("text/plain");
        startActivity(shareIntent);

        SharedPreferences sharep=getSharedPreferences("app pref", MODE_PRIVATE);
        SharedPreferences.Editor editor=sharep.edit();
        editor.putString("share title",questionTitle);
        editor.apply();
    }
}