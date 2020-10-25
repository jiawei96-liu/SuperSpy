package com.software.ustc.superspy;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import org.litepal.LitePal;
import org.litepal.tablemanager.Connector;

import java.util.List;

public class Login_Activity extends BaseActivity {

    private Button button1;
    private CheckBox rememberpass;
    private EditText accountEdit;
    private  EditText passwordEdit;
    private Button button2;

    int flag=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login__activity);


        //LitePal.initialize(this);

        button1=findViewById(R.id.btn_login);
        button2=findViewById(R.id.et_0);
        accountEdit=findViewById(R.id.et_1);
        passwordEdit=findViewById(R.id.et_2);
        rememberpass=findViewById(R.id.cb_remember_pwd);

        Login_data login_id1 = LitePal.find(Login_data.class, 1);
        accountEdit.setText(login_id1.getUsername());
        passwordEdit.setText(login_id1.getPassward());

        button1.setOnClickListener(new View.OnClickListener() {
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
                if (flag == 1) {
                    if (rememberpass.isChecked()) {
                        Login_data login1 = new Login_data();
                        login1.setUsername(account);
                        login1.setPassward(password);
                        login1.updateAll("id=?", "1");
                    }
                    Toast.makeText(Login_Activity.this, "登录成功", Toast.LENGTH_SHORT).show();
                    Intent intent1 = new Intent(Login_Activity.this, MainActivity.class);
                    startActivity(intent1);
                } else {
                    Toast.makeText(Login_Activity.this, "用户名或密码错误", Toast.LENGTH_SHORT).show();
                }



            }



        });
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Login_Activity.this,roll_Activity.class);
                startActivity(intent);
            }
        });
    }

}