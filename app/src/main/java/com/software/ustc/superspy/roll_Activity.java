package com.software.ustc.superspy;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.software.ustc.superspy.kits.BaseActivity;
import com.software.ustc.superspy.kits.Login_data;

import org.litepal.LitePal;

import java.util.List;

public class roll_Activity extends BaseActivity {

    private Button roll;
    private EditText accountRollEdit;
    private EditText passwordRollEdit;
    private EditText passwordConfirm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_roll);
        accountRollEdit = findViewById(R.id.eLUsername);
        passwordRollEdit = findViewById(R.id.eLPassward);
        passwordConfirm = findViewById(R.id.eConfirm);
        roll = findViewById(R.id.bRoll);
        roll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int flag=0;
                Toast toast = Toast.makeText(roll_Activity.this, null, Toast.LENGTH_SHORT);
                String passward = passwordRollEdit.getText().toString();
                String passwardConfirm = passwordConfirm.getText().toString();
                String account1 = accountRollEdit.getText().toString();
                List<Login_data> logins = LitePal.findAll(Login_data.class);
                for (Login_data data1 : logins) {
                    if (account1.equals(data1.getUsername()) ) {
                        flag = 1;
                        break;
                    }
                }
                if (!(passward.equals(passwardConfirm))) {
                    toast.setText("两次输入的密码不一致");
                } else if (passward.equals("") || account1.equals("")) {
                    toast.setText("用户名或密码为空");
                } else if(flag==1){
                    toast.setText("用户名已被注册");
                }
                else {
                    toast.setText("注册成功");
                    Login_data login = new Login_data();
                    login.setUsername(account1);
                    login.setPassward(passward);
                    login.save();
                    Intent intent = new Intent(roll_Activity.this, Login_Activity.class);
                    startActivity(intent);
                    finish();
                }
                toast.show();
            }
        });
    }
}