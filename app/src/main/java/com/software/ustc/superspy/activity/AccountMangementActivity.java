package com.software.ustc.superspy.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.software.ustc.superspy.R;
import com.software.ustc.superspy.kits.BaseActivity;

public class AccountMangementActivity extends BaseActivity {
    private Button changepassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_mangement);

        changepassword=findViewById(R.id.changepassword);
        changepassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AccountMangementActivity.this, PasswordChangeActivity.class));
            }
        });
    }


}