package com.software.ustc.superspy.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.software.ustc.superspy.R;
import com.software.ustc.superspy.db.litepal.FixDataProvider;
import com.software.ustc.superspy.kits.BaseActivity;
import com.software.ustc.superspy.db.litepal.LoginData;

import org.litepal.LitePal;

import java.util.List;

public class RollActivity extends BaseActivity {

    private Button roll;
    private EditText accountRollEdit;
    private EditText passwordRollEdit;
    private EditText passwordConfirm;
    private EditText vertify1;
    private EditText vertify2;
    private TextView login;
    private Spinner spinner1;
    private Spinner spinner2;
    String str1;
    String str2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_roll);
        accountRollEdit = findViewById(R.id.eLUsername);
        passwordRollEdit = findViewById(R.id.eLPassward);
        passwordConfirm = findViewById(R.id.eConfirm);
        login = findViewById(R.id.cbLogin);
        vertify1=findViewById(R.id.vertify1);
        vertify2=findViewById(R.id.vertify2);
        spinner1 = (Spinner) this.findViewById(R.id.mySpinnerA);
        spinner2 = (Spinner) this.findViewById(R.id.mySpinnerA2);
        this.loadDataForSpinnerA();

        str1 = (String) spinner1.getSelectedItem();
        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {

                //拿到被选择项的值
                str1 = (String) spinner1.getSelectedItem();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub

            }
        });
        str2 = (String) spinner2.getSelectedItem();
        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {

                //拿到被选择项的值
                str2 = (String) spinner2.getSelectedItem();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub

            }
        });


        roll = findViewById(R.id.bRoll);
        roll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int flag=0;
                Toast toast = Toast.makeText(RollActivity.this, null, Toast.LENGTH_SHORT);
                String passward = passwordRollEdit.getText().toString();
                String passwardConfirm = passwordConfirm.getText().toString();
                String account1 = accountRollEdit.getText().toString();
                String ver1 = vertify1.getText().toString();
                String ver2 = vertify2.getText().toString();
                List<LoginData> logins = LitePal.findAll(LoginData.class);
                for (LoginData data1 : logins) {
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
                } else if(str1.equals(str2)){
                    toast.setText("两次验证问题不能相同");
                }
                else {
                    ////////////////////////////////
//                    Spinner spinner = (Spinner) findViewById(R.id.spinner1);
//                    // 建立数据源
//                    String[] mItems = getResources().getStringArray(R.array.languages);
//                    // 建立Adapter并且绑定数据源
//                    ArrayAdapter<String> adapter=new ArrayAdapter<String>(RollActivity.this,android.R.layout.simple_spinner_item, mItems);
//                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//                    //绑定 Adapter到控件
//                    spinner .setAdapter(adapter);
//                    spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//                        @Override
//                        public void onItemSelected(AdapterView<?> parent, View view,
//                                                   int pos, long id) {
//
//                            String[] languages = getResources().getStringArray(R.array.languages);
//                            Toast.makeText(RollActivity.this, "你点击的是:"+languages[pos], 2000).show();
//                        }
//                        @Override
//                        public void onNothingSelected(AdapterView<?> parent) {
//                            // Another interface callback
//                        }
//                    });
                    /////////////////////////
//                    Spinner spinner = (Spinner) findViewById(R.id.spinner1);
//                    spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//                        @Override
//                        public void onItemSelected(AdapterView<?> parent, View view,
//                                                   int pos, long id) {
//
//                            String[] languages = getResources().getStringArray(R.array.languages);
//                            Toast.makeText(RollActivity.this, "你点击的是:"+languages[pos], 2000).show();
//
//                        }
//                        @Override
//                        public void onNothingSelected(AdapterView<?> parent) {
//                            // Another interface callback
//                        }
//                    });
                    //////////////////////////////////////

                    toast.setText("注册成功");
                    LoginData login = new LoginData();
                    login.setUsername(account1);
                    login.setPassward(passward);
                    login.setVertifyid1(str1);
                    login.setVertify1(ver1);
                    login.setVertifyid2(str2);
                    login.setVertify2(ver2);
                    login.save();
                    Intent intent = new Intent(RollActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                }
                toast.show();
            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RollActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();

            }
        });

    }

    private void loadDataForSpinnerA() {
        List<String> spinnerList = FixDataProvider.GetTenNumberList();
        ArrayAdapter<String> myAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, spinnerList);
        this.spinner1.setAdapter(myAdapter);
        this.spinner2.setAdapter(myAdapter);
    }
}