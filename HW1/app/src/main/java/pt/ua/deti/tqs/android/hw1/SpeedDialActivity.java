package pt.ua.deti.tqs.android.hw1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.time.Duration;
import java.time.temporal.TemporalUnit;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class SpeedDialActivity extends AppCompatActivity {

    private int dialId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_speed_dial_manager);

        final String dialIdKey = "DIAL_ID";

        Intent callingIntent = getIntent();
        dialId = callingIntent.getIntExtra(dialIdKey, 0);

        TextView dialBeingEditedTextView = findViewById(R.id.text_speed_dial_editting);
        dialBeingEditedTextView.setText(String.format(Locale.ENGLISH, "Editing Dial %d", dialId));

    }

    public void onSaveClick(View view) {

        EditText editTextDialName = findViewById(R.id.editText_dial_name);
        EditText editTextDialNumber = findViewById(R.id.editText_dial_number);

        if (editTextDialName.getText().length() == 0 || editTextDialNumber.getText().length() == 0) {
            Toast.makeText(getApplicationContext(), "Invalid fields", Toast.LENGTH_SHORT).show();
            return;
        }

        returnReply();

    }

    public void returnReply() {

        EditText editTextDialName = findViewById(R.id.editText_dial_name);
        EditText editTextDialNumber = findViewById(R.id.editText_dial_number);

        String dialName = editTextDialName.getText().toString();
        String dialNumber = editTextDialNumber.getText().toString();

        Intent replyIntent = new Intent();

        replyIntent.putExtra("DIAL_ID", dialId);
        replyIntent.putExtra("DIAL_NAME", dialName);
        replyIntent.putExtra("DIAL_NUMBER", dialNumber);

        setResult(RESULT_OK, replyIntent);
        finish();

    }

}