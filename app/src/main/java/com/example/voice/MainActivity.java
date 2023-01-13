package com.example.voice;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.Toast;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    ImageView imageView;
    ClipboardManager clipboardManager;
    ClipData clipData;

    private TextToSpeech mTTS;
    private EditText MEditText;
    private SeekBar MSeekBarPitch, MSeekBarSpeed;
    private Button mButtonSpeek;

    @Override
    protected void onDestroy() {
        if (mTTS != null) {
            mTTS.stop();
            mTTS.shutdown();

        }
        super.onDestroy();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mButtonSpeek = findViewById(R.id.button_speak);
        mTTS = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status == TextToSpeech.SUCCESS) {
                    int result = mTTS.setLanguage(Locale.ENGLISH);
                    if (result == TextToSpeech.LANG_MISSING_DATA
                            || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                        Log.e("TTS", "Language not supported");
                    } else {
                        mButtonSpeek.setEnabled(true);
                    }

                } else {
                    Log.e("TTS", "initialization failed");

                }
            }
        });

        imageView = findViewById(R.id.imageView);
        String texttoPaste = null;
        clipboardManager = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
        MEditText = findViewById(R.id.edit_text);
        MSeekBarPitch = findViewById(R.id.seek_bar_pitch);
        MSeekBarSpeed = findViewById(R.id.seek_bar_speed);
        mButtonSpeek.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                speak();
            }
        });
    }

    private void speak() {
        String text = MEditText.getText().toString();
        float pitch = (float) MSeekBarPitch.getProgress() / 50;
        if (pitch < 0.1) {
            pitch = 0.1f;
        }
        float speed = (float) MSeekBarSpeed.getProgress() / 50;
        if (speed < 0.1) {
            speed = 0.1f;
        }

        mTTS.setPitch(pitch);
        mTTS.setSpeechRate(speed);
        mTTS.speak(text, TextToSpeech.QUEUE_FLUSH, null);
    }
    //public void copy(){
    // String text = MEditText.getText().toString();
    // clipData = ClipData.newPlainText("text",text);
    // clipboardManager.setPrimaryClip(clipData);
    //Toast.makeText(getApplicationContext."TextCopied",Toast.LENGTH_SHORT.show());
//}
    public void Paste(){
       // String texttoPaste = null;

        //ClipData a = ClipboardManager.getPrimaryClip();
        //
        if (clipboardManager.hasPrimaryClip()) {
            ClipData clip = clipboardManager.getPrimaryClip();
            ClipData.Item item = clip.getItemAt(0);
            String text1 = item.getText().toString();
            MEditText.setText(text1);

        }else{
            Toast.makeText(getApplicationContext(),"No text on clipboard",Toast.LENGTH_SHORT);
        }

    }
}


