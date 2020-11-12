package com.software.ustc.superspy.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.software.ustc.superspy.R;
import com.software.ustc.superspy.kits.BaseActivity;
import com.software.ustc.superspy.db.litepal.LoginData;

import org.litepal.LitePal;
import org.litepal.tablemanager.Connector;

import java.util.List;

public class LoginActivity extends BaseActivity {

    private Button login;
    private CheckBox rememberpass;
    private EditText accountEdit;
    private  EditText passwordEdit;
    private Button roll;

    int flag=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_activity);
        LitePal.initialize(this);
        Connector.getDatabase();
        login=findViewById(R.id.bLogin);
        roll=findViewById(R.id.cRoll);
        accountEdit=findViewById(R.id.eUsername);
        passwordEdit=findViewById(R.id.ePassward);
        rememberpass=findViewById(R.id.cb_remember_pwd);
        LoginData movie = LitePal.find(LoginData.class,1);
        if(movie!=null) {
            LoginData loginId1 = LitePal.find(LoginData.class, 1);
            accountEdit.setText(loginId1.getUsername());
            passwordEdit.setText(loginId1.getPassward());
        }
        else {
            LoginData login1 = new LoginData();
            login1.setUsername("");
            login1.setPassward("");
            login1.setId(1);
            login1.save();
//            login1.updateAll("id=?", "1");
            LoginData login2 = new LoginData();
            login2.setUsername("");
            login2.setPassward("");
            login2.setId(2);
            login2.save();
//            login2.updateAll("id=?", "2");
        }

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                String account = accountEdit.getText().toString();
                String password = passwordEdit.getText().toString();
                List<LoginData> logins = LitePal.findAll(LoginData.class);

                for (LoginData data1 : logins) {
                    if (account.equals(data1.getUsername()) && password.equals(data1.getPassward())) {
                        flag = 1;
                        break;
                    }
                }

                Toast toast = Toast.makeText(LoginActivity.this,null,Toast.LENGTH_SHORT);
                if(account.equals("") && password.equals(""))
                    flag=0;
                if (flag == 1) {
                    if (rememberpass.isChecked()) {
                        LoginData login1 = new LoginData();
                        login1.setUsername(account);
                        login1.setPassward(password);
                        login1.updateAll("id=?", "1");

                    }
                    else{
                        LoginData login1 = new LoginData();
                        login1.setUsername("");
                        login1.setPassward("");
                        login1.updateAll("id=?", "1");
                    }
                    LoginData login2 = new LoginData();
                    login2.setUsername(account);
                    login2.setPassward(password);
                    login2.updateAll("id=?", "2");
                    toast.setText("登录成功");
                    Intent intent1 = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent1);
                    finish();
                } else {
                    toast.setText("用户名或密码错误");
                }
                toast.show();



            }



        });
        roll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this,RollActivity.class);
                startActivity(intent);

            }
        });
    }

}