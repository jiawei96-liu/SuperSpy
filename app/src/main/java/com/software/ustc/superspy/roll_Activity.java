package com.software.ustc.superspy;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.software.ustc.superspy.kits.BaseActivity;
import com.software.ustc.superspy.kits.Login_data;

public class Roll_Activity extends BaseActivity {

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
                Toast toast = Toast.makeText(Roll_Activity.this, null, Toast.LENGTH_SHORT);
                String passward = passwordRollEdit.getText().toString();
                String passwardConfirm = passwordConfirm.getText().toString();
                String account1 = accountRollEdit.getText().toString();
                if (!(passward.equals(passwardConfirm))) {
                    toast.setText("两次输入的密码不一致");
                } else if (passward.equals("") || account1.equals("")) {
                    toast.setText("用户名或密码为空");
                } else {
                    toast.setText("注册成功");
                    Login_data login = new Login_data();
                    login.setUsername(account1);
                    login.setPassward(passward);
                    login.save();
                    Intent intent = new Intent(Roll_Activity.this, Login_Activity.class);
                    startActivity(intent);
                    finish();
                }
                toast.show();
            }
        });
    }
}