package com.android.mycax.luckynumbers;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {


    // Used to load the 'native-lib' library on application startup.
    static {
        System.loadLibrary("luckynumbers");
    }

    private EditText mFirstName;
    private EditText mLastName;
    private Button mButton;
    

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

        mFirstName = (EditText)findViewById(R.id.firstName);
        mLastName = (EditText)findViewById(R.id.lastName);
        mButton = (Button)findViewById(R.id.button);

        mButton.setOnClickListener(
                new View.OnClickListener()
                {
                    public void onClick(View view) {
                        if (isEmpty(mFirstName) && isEmpty(mLastName) || isEmpty(mFirstName) || isEmpty(mLastName)) {
                            Toast.makeText(MainActivity.this, "Input fields cannot be empty", Toast.LENGTH_SHORT).show();

                        } else if (isAlpha(mFirstName.getText().toString()) && isAlpha(mLastName.getText().toString())) {
                            TextView tv = (TextView) findViewById(R.id.output_text);
                            tv.setText(Calculate(mFirstName.getText().toString(), mLastName.getText().toString()));
                        }
                        else {
                            Toast.makeText(MainActivity.this, "Input fields can only contain letters!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    public native String Calculate(String jFirstName, String jLastName);

}
