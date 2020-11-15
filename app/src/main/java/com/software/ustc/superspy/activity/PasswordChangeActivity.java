package com.software.ustc.superspy.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.software.ustc.superspy.R;
import com.software.ustc.superspy.db.litepal.LoginData;
import com.software.ustc.superspy.kits.BaseActivity;

import org.litepal.LitePal;

import java.util.List;

public class PasswordChangeActivity extends BaseActivity {

    private EditText oldpassword,newpassword,newpassword2;
    private Button confirm;
    private ImageView delete,disply1,disply2;
    private int isvisiable1=0;
    private int isvisiable2=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_change);
        oldpassword=findViewById(R.id.oldpassword);
        newpassword=findViewById(R.id.newpassword);
        newpassword2=findViewById(R.id.newpassword2);
        delete=findViewById(R.id.delete6);
        disply1=findViewById(R.id.disply1);
        disply2=findViewById(R.id.disply2);
        confirm=findViewById(R.id.affirm);
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String oldpasswords=oldpassword.getText().toString();
                String newpasswords=newpassword.getText().toString();
                String newpassword2s=newpassword2.getText().toString();
                LoginData loginId2 = LitePal.find(LoginData.class, 2);
                if(oldpasswords.equals(loginId2.getPassward())){

                    if(newpasswords.equals(newpassword2s)){
                        Toast toast = Toast.makeText(PasswordChangeActivity.this,null,Toast.LENGTH_SHORT);
                        toast.setText("修改密码成功");
                        List<LoginData> logins = LitePal.findAll(LoginData.class);
                        for (LoginData data1 : logins) {
                            if (loginId2.getUsername().equals(data1.getUsername()) && oldpasswords.equals(data1.getPassward())) {
                                data1.setPassward(newpasswords);
                                data1.save();
                            }
                        }

                        Intent intent = new Intent(PasswordChangeActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    }
                    else{
                        Toast toast = Toast.makeText(PasswordChangeActivity.this,null,Toast.LENGTH_SHORT);
                        toast.setText("两次密码不一致");
                    }
                }
                else{
                    Toast toast = Toast.makeText(PasswordChangeActivity.this,null,Toast.LENGTH_SHORT);
                    toast.setText("密码不正确");
                }
            }
        });
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                oldpassword.setText("");
            }
        });
        disply1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    if(isvisiable1==0) {
                        disply1.setImageResource(R.drawable.xianshi);
                        newpassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                        isvisiable1 = 1;

                    }else {
                        disply1.setImageResource(R.drawable.yincang);
                        newpassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                        isvisiable1=0;

                    }

            }
        });
        disply2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isvisiable2==0) {
                    disply2.setImageResource(R.drawable.xianshi);
                    newpassword2.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    isvisiable2=1;
                    }

                    else {
                        disply2.setImageResource(R.drawable.yincang);
                        newpassword2.setTransformationMethod(PasswordTransformationMethod.getInstance());
                        isvisiable2=0;
                    }

            }
        });
    }
}