package com.software.ustc.superspy.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.software.ustc.superspy.R;
import com.software.ustc.superspy.db.litepal.LoginData;
import com.software.ustc.superspy.kits.BaseActivity;

import org.litepal.LitePal;

public class PasswordInfoActivity extends BaseActivity {
    private EditText insurance1;
    private EditText insurance2;
    private TextView confirm;
    private TextView ret;
    private ImageView delete1,delete2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_info);
        ret=findViewById(R.id.return2);
        insurance1=findViewById(R.id.insurance1);
        insurance2=findViewById(R.id.insurance2);
        delete1=findViewById(R.id.delete3);
        delete2=findViewById(R.id.delete4);
        confirm=findViewById(R.id.confirm);
        final LoginData login1 = LitePal.find(LoginData.class, 3);
        insurance1.setHint(login1.getVertifyid1());
        insurance2.setHint(login1.getVertifyid2());
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String str1=insurance1.getText().toString();
                String str2=insurance2.getText().toString();
                LoginData login2 = LitePal.find(LoginData.class, 3);
                if(str1.equals(login2.getVertify1()) && str2.equals(login2.getVertify2()) ){
                    Intent intent = new Intent(getApplicationContext(), PasswordResetActivity.class);
                    startActivity(intent);

                }
                else{
                    Toast toast = Toast.makeText(PasswordInfoActivity.this,null,Toast.LENGTH_SHORT);
                    toast.setText("密保验证信息错误");
                    toast.show();
                }

            }
        });
        ret.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
                finish();

            }
        });
        delete1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insurance1.setText("");
            }
        });
        delete2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insurance2.setText("");
            }
        });
    }

}