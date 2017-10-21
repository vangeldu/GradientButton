package sample.dagang.gradientbutton;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.dagang.library.GradientButton;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        GradientButton gradientButton = (GradientButton) findViewById(R.id.magic_button);
        TextView textView = gradientButton.getButton();
        textView.setText("Custom Button");
        textView.setTextColor(Color.BLUE);
        gradientButton.setButtonRadius(20);
        gradientButton.setShadowRadius(10);
        gradientButton.setShadowColor(Color.RED);
        gradientButton.setButtonStartColor(Color.parseColor("#EEE5DE"));
        gradientButton.setButtonEndColor(Color.parseColor("#9370DB"));
        gradientButton.setButtonPressStartColor(Color.BLACK);
        gradientButton.setButtonPressEndColor(Color.BLACK);
        gradientButton.setButtonGradientOrientation(GradientButton.LEFT_RIGHT);
        gradientButton.getButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "onClick", Toast.LENGTH_LONG).show();
            }
        });
    }
}
