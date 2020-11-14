package com.software.ustc.superspy.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.software.ustc.superspy.R;
import com.software.ustc.superspy.db.litepal.LoginData;
import com.software.ustc.superspy.kits.BaseActivity;

import org.litepal.LitePal;

import java.util.List;

public class PasswordGetActivity extends BaseActivity {
    private Button confirm;
    private String str;
    private EditText usr;
    private TextView ret;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_get);
        confirm=findViewById(R.id.confirm);
        usr=findViewById(R.id.usr);
        ret=findViewById(R.id.return1);
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                str=usr.getText().toString();
                List<LoginData> logins = LitePal.findAll(LoginData.class);
                int flag=0;
                for (LoginData data1 : logins) {
                    if (str.equals(data1.getUsername())&& !str.equals("")) {
                        flag = 1;
                        LoginData login1 = new LoginData();
                        login1.setUsername(str);
                        login1.setVertify1(data1.getVertify1());
                        login1.setVertifyid1(data1.getVertifyid1());
                        login1.setVertify2(data1.getVertify2());
                        login1.setVertifyid2(data1.getVertifyid2());
                        login1.updateAll("id=?", "3");
                        break;
                    }
                }
                if(flag==1) {
                    Intent intent = new Intent(getApplicationContext(), PasswordInfoActivity.class);
                    startActivity(intent);
                    finish();

                }
                else{
                    Toast toast = Toast.makeText(getApplicationContext(),null,Toast.LENGTH_SHORT);
                    toast.setText("没有找到此用户");
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
    }
}