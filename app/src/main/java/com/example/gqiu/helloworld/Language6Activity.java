package com.example.gqiu.helloworld;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.aihuishou.switchlanguagelibrary.LanguageBean;
import com.aihuishou.switchlanguagelibrary.SelectLanguageDialog;
import com.aihuishou.switchlanguagelibrary.SwitchLanguageListener;
import com.aihuishou.switchlanguagelibrary.utils.LanguageSwitchUtils;

import java.util.ArrayList;
import java.util.Locale;

public class Language6Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_language6);


        //   TextPaintView textPaintView = ;

    }

    public void helloTv(View view) {
        ArrayList<LanguageBean> languageBeans = new ArrayList<>();
        languageBeans.add(new LanguageBean(getString(R.string.chinese), Locale.CHINA));
        languageBeans.add(new LanguageBean(getString(R.string.english), Locale.ENGLISH));
        new SelectLanguageDialog(this, languageBeans, new SwitchLanguageListener() {
            @Override
            public void onSwitchLanguageSuccess(Locale locale) {
                LanguageSwitchUtils.switchAppLanguage(Language6Activity.this, locale);
                Intent refresh = new Intent(Language6Activity.this, Language6Activity.class);
                refresh.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(refresh);
            }
        }).show();
    }

    public void btn(View view) {
        startActivity(new Intent(this,Main5Activity.class));
    }
}
