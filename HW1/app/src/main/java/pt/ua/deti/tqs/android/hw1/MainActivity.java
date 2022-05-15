package pt.ua.deti.tqs.android.hw1;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.content.res.Configuration;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.Arrays;
import java.util.List;

import pt.ua.deti.tqs.android.hw1.utils.SpeedDial;

public class MainActivity extends AppCompatActivity {

    private String dialerString;
    private List<SpeedDial> speedDials;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dialerString = "";
        speedDials = Arrays.asList(new SpeedDial(), new SpeedDial(), new SpeedDial());

        setupDeleteBtnLongPress();
        setupSpeedDialLongPress();

        //adaptToThemeMode(getApplicationContext());

    }

    public void addNumber(View view) {

        Button clickedButton = (Button) view;
        String buttonText = clickedButton.getText().toString();

        if (buttonText.matches("[0-9]+")) {
            dialerString += buttonText;
        }

        onDialStringChange();

    }

    public void deleteNumber(View view) {

        if (dialerString.isEmpty()) return;

        dialerString = dialerString.substring(0, dialerString.length()-1);
        onDialStringChange();

    }

    public void callNumber(View view) {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse(String.format("tel:%s", dialerString)));
        startActivity(intent);
    }

    public void speedDial(View view) {

        Intent intent = new Intent(Intent.ACTION_DIAL);

        if (view.getId() == R.id.btn_dial_1) {
            intent.setData(Uri.parse(String.format("tel:%s", speedDials.get(0).getNumber())));
        } else if (view.getId() == R.id.btn_dial_2) {
            intent.setData(Uri.parse(String.format("tel:%s", speedDials.get(1).getNumber())));
        } else if (view.getId() == R.id.btn_dial_3) {
            intent.setData(Uri.parse(String.format("tel:%s", speedDials.get(2).getNumber())));
        }

        startActivity(intent);

    }

    private void onDialStringChange() {
        TextView textView = findViewById(R.id.textView);
        textView.setText(dialerString);
    }

    private void onSpeedDialChange() {

        Button speedDial1Button = findViewById(R.id.btn_dial_1);
        Button speedDial2Button = findViewById(R.id.btn_dial_2);
        Button speedDial3Button = findViewById(R.id.btn_dial_3);

        speedDial1Button.setText(speedDials.get(0).getName());
        speedDial2Button.setText(speedDials.get(1).getName());
        speedDial3Button.setText(speedDials.get(2).getName());

    }

    private void setupDeleteBtnLongPress() {

        ImageButton deleteBtn = findViewById(R.id.btn_delete);

        deleteBtn.setOnLongClickListener(view -> {
            dialerString = "";
            onDialStringChange();
            return true;
        });

    }

    private void setupSpeedDialLongPress() {

        final String dialIdKey = "DIAL_ID";

        Button btnDial1 = findViewById(R.id.btn_dial_1);
        Button btnDial2 = findViewById(R.id.btn_dial_2);
        Button btnDial3 = findViewById(R.id.btn_dial_3);

        ActivityResultLauncher<Intent> launchSpeedDialWithResult = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK) {

                        Intent data = result.getData();

                        if (data == null) return;

                        int dialId = data.getIntExtra("DIAL_ID", 1)-1;
                        String dialName = data.getStringExtra("DIAL_NAME");
                        String dialNumber = data.getStringExtra("DIAL_NUMBER");

                        speedDials.set(dialId, new SpeedDial(dialName, dialNumber));
                        onSpeedDialChange();

                    }
                });

        btnDial1.setOnLongClickListener(view -> {
            Intent intent = new Intent(this, SpeedDialManager.class);
            intent.putExtra(dialIdKey, 1);
            launchSpeedDialWithResult.launch(intent);
            return true;
        });

        btnDial2.setOnLongClickListener(view -> {
            Intent intent = new Intent(this, SpeedDialManager.class);
            intent.putExtra(dialIdKey, 2);
            launchSpeedDialWithResult.launch(intent);
            return true;
        });

        btnDial3.setOnLongClickListener(view -> {
            Intent intent = new Intent(this, SpeedDialManager.class);
            intent.putExtra(dialIdKey, 3);
            launchSpeedDialWithResult.launch(intent);
            return true;
        });
    }

    private void adaptToThemeMode(Context context) {

        int nightModeFlags = context.getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;

        Button[] dialButtons = {findViewById(R.id.btn_0), findViewById(R.id.btn_1), findViewById(R.id.btn_2), findViewById(R.id.btn_3),
                findViewById(R.id.btn_4), findViewById(R.id.btn_5), findViewById(R.id.btn_6), findViewById(R.id.btn_7),
                findViewById(R.id.btn_8), findViewById(R.id.btn_9)};

        switch (nightModeFlags) {
            case Configuration.UI_MODE_NIGHT_YES:
                for (Button button : dialButtons)
                    button.setTextColor(Color.rgb(255, 255, 255));
                break;

            case Configuration.UI_MODE_NIGHT_UNDEFINED:
            case Configuration.UI_MODE_NIGHT_NO:
                for (Button button : dialButtons)
                    button.setTextColor(Color.rgb(0, 0, 0));
                break;

        }

    }

}