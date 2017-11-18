package com.luckynumbers.mycax.luckynumbers;

        import android.support.v7.app.AppCompatActivity;
        import android.os.Bundle;
        import android.view.View;
        import android.widget.Button;
        import android.widget.EditText;
        import android.widget.TextView;
        import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements
        View.OnClickListener {


    // Used to load the 'native-lib' library on application startup.
    static {
        System.loadLibrary("luckynumbers");
    }

    private EditText mFirstName;
    private EditText mLastName;
    private TextView mOutputText;
    private Button mButton_Calculate;
    private Button mButton_Reset;


    private boolean isEmpty(EditText etText) {
        return etText.getText().toString().trim().length() == 0;
    }

    private boolean isAlpha(String name) {
        return name.matches("[a-zA-Z]+");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mFirstName = (EditText) findViewById(R.id.firstName);
        mLastName = (EditText) findViewById(R.id.lastName);
        mOutputText= (TextView) findViewById(R.id.output_text);

        mButton_Calculate = (Button) findViewById(R.id.button_calculate);
        mButton_Calculate.setOnClickListener(this);
        mButton_Reset = (Button) findViewById(R.id.button_reset);
        mButton_Reset.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.button_calculate) {
            if (isEmpty(mFirstName) && isEmpty(mLastName) || isEmpty(mFirstName) || isEmpty(mLastName)) {
                Toast.makeText(MainActivity.this, "Input fields cannot be empty", Toast.LENGTH_SHORT).show();

            } else if (isAlpha(mFirstName.getText().toString()) && isAlpha(mLastName.getText().toString())) {
                mOutputText.setText(Calculate(mFirstName.getText().toString(), mLastName.getText().toString()));
            } else {
                Toast.makeText(MainActivity.this, "Input fields can only contain letters!", Toast.LENGTH_SHORT).show();
            }
        } else if (v.getId() == R.id.button_reset) {
            mFirstName.getText().clear();
            mLastName.getText().clear();
            mOutputText.setText("");
        }
    }

    public native String Calculate(String jFirstName, String jLastName);

}
