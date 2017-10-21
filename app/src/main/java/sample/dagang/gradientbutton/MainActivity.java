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
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        GradientButton magicButton = (GradientButton) findViewById(R.id.magic_button);
        TextView textView = magicButton.getButton();
        textView.setText("Custom Button");
        textView.setTextColor(Color.BLUE);
        magicButton.setButtonRadius(20);
        magicButton.setShadowRadius(10);
        magicButton.setShadowColor(Color.RED);
        magicButton.setButtonStartColor(Color.parseColor("#EEE5DE"));
        magicButton.setButtonEndColor(Color.parseColor("#9370DB"));
        magicButton.setButtonPressStartColor(Color.BLACK);
        magicButton.setButtonPressEndColor(Color.BLACK);
        magicButton.setButtonGradientOrientation(GradientButton.LEFT_RIGHT);
        magicButton.getButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "onClick", Toast.LENGTH_LONG).show();
            }
        });
    }
}
