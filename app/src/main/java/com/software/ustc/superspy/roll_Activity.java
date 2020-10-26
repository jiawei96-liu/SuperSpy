package com.software.ustc.superspy;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.software.ustc.superspy.kits.BaseActivity;
import com.software.ustc.superspy.kits.Login_data;

public class roll_Activity extends BaseActivity {

    private Button button5;
    private EditText accountRollEdit;
    private  EditText passwordRollEdit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_roll_);
        button5 =findViewById(R.id.roll_4);
        accountRollEdit=findViewById(R.id.roll_2);
        passwordRollEdit=findViewById(R.id.roll_3);
        button5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(roll_Activity.this,"注册成功",Toast.LENGTH_SHORT).show();

                String account1 = accountRollEdit.getText().toString();
                String password1 =passwordRollEdit.getText().toString();
                Login_data login=new Login_data();
                login.setUsername(account1);
                login.setPassward(password1);
                login.save();
                Intent intent = new Intent(roll_Activity.this, Login_Activity.class);
                startActivity(intent);
            }
        });
    }
}