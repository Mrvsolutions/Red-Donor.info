package mr.vsolutions.red_donorinfo;

import android.graphics.Color;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class SignupActivity extends AppCompatActivity {
    ImageView imgback;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        getSupportActionBar().hide();
        imgback = findViewById(R.id.imgback);
        SpannableStringBuilder builder = new SpannableStringBuilder();
        TextView txtnewuser = (TextView) findViewById(R.id.txtnewuser);
        SpannableString str1= new SpannableString("Already have an account?");
        str1.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.colorbottomtext)), 0, str1.length(), 0);
        builder.append(str1);

        SpannableString str2= new SpannableString(" Log In");
        str2.setSpan(new ForegroundColorSpan(Color.RED), 0, str2.length(), 0);
        builder.append(str2);

        txtnewuser.setText( builder, TextView.BufferType.SPANNABLE);
        imgback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}