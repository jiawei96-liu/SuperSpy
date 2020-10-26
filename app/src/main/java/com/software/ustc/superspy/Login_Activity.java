package com.software.ustc.superspy;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.software.ustc.superspy.kits.BaseActivity;
import com.software.ustc.superspy.kits.Login_data;

import org.litepal.LitePal;
import org.litepal.tablemanager.Connector;

import java.util.List;

public class Login_Activity extends BaseActivity {

    private Button login;
    private CheckBox rememberpass;
    private EditText accountEdit;
    private  EditText passwordEdit;
    private Button roll;

    int flag=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login__activity);
        //LitePal.initialize(this);
        login=findViewById(R.id.bLogin);
        roll=findViewById(R.id.cRoll);
        accountEdit=findViewById(R.id.eUsername);
        passwordEdit=findViewById(R.id.ePassward);
        rememberpass=findViewById(R.id.cb_remember_pwd);

        Login_data loginId1 = LitePal.find(Login_data.class, 1);
        accountEdit.setText(loginId1.getUsername());
        passwordEdit.setText(loginId1.getPassward());

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Connector.getDatabase();

                String account = accountEdit.getText().toString();
                String password = passwordEdit.getText().toString();
                List<Login_data> logins = LitePal.findAll(Login_data.class);

                for (Login_data data1 : logins) {
                    if (account.equals(data1.getUsername()) && password.equals(data1.getPassward())) {
                        flag = 1;
                        break;
                    }
                }

                Toast toast = Toast.makeText(Login_Activity.this,null,Toast.LENGTH_SHORT);
                if(account.equals("") && password.equals(""))
                    flag=0;
                if (flag == 1) {
                    if (rememberpass.isChecked()) {
                        Login_data login1 = new Login_data();
                        login1.setUsername(account);
                        login1.setPassward(password);
                        login1.updateAll("id=?", "1");
                    }
                    else{
                        Login_data login1 = new Login_data();
                        login1.setUsername("");
                        login1.setPassward("");
                        login1.updateAll("id=?", "1");
                    }
                    toast.setText("登录成功");
                    Intent intent1 = new Intent(Login_Activity.this, AppInfoShow.class);
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
                Intent intent = new Intent(Login_Activity.this,roll_Activity.class);
                startActivity(intent);

            }
        });
    }

}