package com.luckynumbers.mycax.luckynumbers;

import android.app.Fragment;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.view.LayoutInflater;
import android.view.ViewGroup;

public class LuckyNumbersFragment extends Fragment implements
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_luckynumbers,
                container, false);
        mFirstName = (EditText) view.findViewById(R.id.firstName);
        mLastName = (EditText) view.findViewById(R.id.lastName);
        mOutputText = (TextView) view.findViewById(R.id.output_text);

        mButton_Calculate = (Button) view.findViewById(R.id.button_calculate);
        mButton_Calculate.setOnClickListener(this);
        mButton_Reset = (Button) view.findViewById(R.id.button_reset);
        mButton_Reset.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.button_calculate) {
            if (isEmpty(mFirstName) && isEmpty(mLastName) || isEmpty(mFirstName) || isEmpty(mLastName)) {
                Toast.makeText(getActivity(), "Input fields cannot be empty", Toast.LENGTH_SHORT).show();

            } else if (isAlpha(mFirstName.getText().toString()) && isAlpha(mLastName.getText().toString())) {
                mOutputText.setText(Calculate(mFirstName.getText().toString(), mLastName.getText().toString()));
            } else {
                Toast.makeText(getActivity(), "Input fields can only contain letters!", Toast.LENGTH_SHORT).show();
            }
        } else if (v.getId() == R.id.button_reset) {
            mFirstName.getText().clear();
            mLastName.getText().clear();
            mOutputText.setText("");
        }
    }

    public native String Calculate(String jFirstName, String jLastName);

}
