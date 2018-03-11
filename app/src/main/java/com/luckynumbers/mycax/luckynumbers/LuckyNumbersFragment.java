package com.luckynumbers.mycax.luckynumbers;

import android.app.Fragment;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.hanks.htextview.base.AnimationListener;
import com.hanks.htextview.base.HTextView;

public class LuckyNumbersFragment extends Fragment implements
        View.OnClickListener {


    // Used to load the 'native-lib' library on application startup.
    static {
        System.loadLibrary("luckynumbers");
    }

    private EditText mFirstName;
    private EditText mLastName;
    private HTextView mOutputText;

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
        mFirstName = view.findViewById(R.id.firstName);
        mLastName = view.findViewById(R.id.lastName);
        mOutputText = view.findViewById(R.id.output_text);
        mOutputText.setAnimationListener(new AnimationListener() {
            @Override
            public void onAnimationEnd(HTextView hTextView) {
                //noop
            }
        });

        ImageButton mButton_Calculate = view.findViewById(R.id.imageButtonCalculate);
        mButton_Calculate.setOnClickListener(this);
        ImageButton mButton_Reset = view.findViewById(R.id.imageButtonReset);
        mButton_Reset.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.imageButtonCalculate) {
            if (isEmpty(mFirstName) && isEmpty(mLastName) || isEmpty(mFirstName) || isEmpty(mLastName)) {
                Snackbar.make(v, "Input fields cannot be empty", Snackbar.LENGTH_SHORT).show();

            } else if (isAlpha(mFirstName.getText().toString()) && isAlpha(mLastName.getText().toString())) {
                String result = getNumberMeaning(Calculate(mFirstName.getText().toString(), mLastName.getText().toString()));
                mOutputText.animateText(result);
                DataBHelper database = new DataBHelper(getActivity());
                database.saveToDB(mFirstName.getText().toString(), mLastName.getText().toString(), result);
                database.close();

            } else {
                Snackbar.make(v, "Input fields can only contain letters!", Snackbar.LENGTH_SHORT).show();
            }
        } else if (v.getId() == R.id.imageButtonReset) {
            mFirstName.getText().clear();
            mLastName.getText().clear();
            mOutputText.animateText("");
        }
    }

    public String getNumberMeaning(int LuckyNumber) {
        Resources res = getResources();
        String[] meanings = res.getStringArray(R.array.number_meaning);
        return meanings[LuckyNumber-1];
    }

    public native int Calculate(String jFirstName, String jLastName);

}
