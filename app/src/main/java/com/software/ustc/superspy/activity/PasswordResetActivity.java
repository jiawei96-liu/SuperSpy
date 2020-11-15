package com.software.ustc.superspy.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.software.ustc.superspy.R;
import com.software.ustc.superspy.db.litepal.LoginData;
import com.software.ustc.superspy.kits.BaseActivity;

import org.litepal.LitePal;

import java.util.List;

public class PasswordResetActivity extends BaseActivity {
    private TextView change;
    private EditText newpass;
    private TextView tips;
    private TextView ret;
    private CheckBox disply;
    private ImageView delete;
    int isChecked=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_reset);
        newpass=findViewById(R.id.newpass);
        change=findViewById(R.id.change);
        tips=findViewById(R.id.tip);
        ret=findViewById(R.id.return3);
        disply=findViewById(R.id.displypassword);
        delete=findViewById(R.id.delete5);
        String content = "温馨提示：<font color='#ffffff'>请设置与历史密码尽可能不同的新密码，以保护账号安全</font>";
        tips.setText(Html.fromHtml(content,Html.FROM_HTML_MODE_LEGACY));

        change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginData login1 = LitePal.find(LoginData.class, 3);
                List<LoginData> logins = LitePal.findAll(LoginData.class);
                for (LoginData data1 : logins) {
                    if (login1.getUsername().equals(data1.getUsername())) {
                        data1.setPassward(newpass.getText().toString());
                        break;
                    }
                }
                LoginData login3 = new LoginData();
                login3.setUsername("");
                login3.setPassward("");
                login3.setVertify1("");
                login3.setVertifyid1("");
                login3.setVertify2("");
                login3.setVertifyid2("");
                login3.setId(3);
                login3.save();
                Toast toast = Toast.makeText(getApplicationContext(),null,Toast.LENGTH_SHORT);
                toast.setText("修改密码成功");
                toast.show();
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
                finish();
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

        disply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isChecked==0) {
                    newpass.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    isChecked=1;
                }
                else {
                    newpass.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    isChecked=0;

                }
            }
        });
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newpass.setText("");
            }
        });
    }
}