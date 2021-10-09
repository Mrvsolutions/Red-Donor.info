package mr.vsolutions.red_donorinfo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class LoginActivity extends AppCompatActivity {

    ImageView imgback;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().hide();

        SpannableStringBuilder builder = new SpannableStringBuilder();
        TextView txtnewuser = (TextView) findViewById(R.id.txtnewuser);
        Button btn_login = (Button) findViewById(R.id.btn_login);
        imgback = findViewById(R.id.imgback);
        SpannableString str1= new SpannableString("New User?");
        str1.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.colorbottomtext)), 0, str1.length(), 0);
        builder.append(str1);

        SpannableString str2= new SpannableString(" Sign Up");
        str2.setSpan(new ForegroundColorSpan(Color.RED), 0, str2.length(), 0);
        builder.append(str2);

        txtnewuser.setText( builder, TextView.BufferType.SPANNABLE);

        txtnewuser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),SignupActivity.class);
                startActivity(i);
            }
        });

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);
            }
        });
        imgback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}