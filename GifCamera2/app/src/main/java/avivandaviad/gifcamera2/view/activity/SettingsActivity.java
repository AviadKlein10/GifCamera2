package avivandaviad.gifcamera2.view.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import avivandaviad.gifcamera2.R;
import avivandaviad.gifcamera2.SharedPreferencesManager;
import avivandaviad.gifcamera2.presenter.BaseView;
import avivandaviad.gifcamera2.presenter.Presenter;
import avivandaviad.gifcamera2.presenter.SettingsPresenter;
import avivandaviad.gifcamera2.view.BaseActivity;

/**
 * Created by Aviad on 14/08/2017.
 */

public class SettingsActivity extends BaseActivity implements BaseView {
    private EditText edit_duration_between_frames, edit_duration_for_frame,
            edit_frame_count, edit_title;
    private Spinner spinner_quality, spinner_font, spinner_font_size;
    private Button btn_add_frame;

    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        initViews();
        loadLastSettings();
    }


    private void initViews() {
        edit_duration_between_frames = (EditText) findViewById(R.id.edit_duration_between_frames);
        edit_duration_for_frame = (EditText) findViewById(R.id.edit_duration_for_frame);
        edit_frame_count = (EditText) findViewById(R.id.edit_frame_count);
        edit_title = (EditText) findViewById(R.id.edit_title);
        spinner_quality = (Spinner) findViewById(R.id.spinner_quality);
        spinner_font = (Spinner) findViewById(R.id.spinner_font);
        spinner_font_size = (Spinner) findViewById(R.id.spinner_font_size);
        btn_add_frame = (Button) findViewById(R.id.btn_add_frame);

        EditText.OnEditorActionListener editorActionListener = new EditText.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                Context context = getApplicationContext();
                switch (v.getId()) {
                    case R.id.edit_duration_between_frames:
                        SharedPreferencesManager.saveValue(context, SharedPreferencesManager.KEY_DURATION_BETWEEN_FRAMES, v.getText() + "");
                        break;
                    case R.id.edit_duration_for_frame:
                        SharedPreferencesManager.saveValue(context, SharedPreferencesManager.KEY_DURATION_FOR_FRAME, v.getText() + "");
                        break;
                    case R.id.edit_frame_count:
                        SharedPreferencesManager.saveValue(context, SharedPreferencesManager.KEY_FRAME_COUNT, v.getText() + "");
                        break;
                    case R.id.edit_title:
                        SharedPreferencesManager.saveValue(context, SharedPreferencesManager.KEY_TITLE, v.getText() + "");
                        break;

                }

                Toast.makeText(context, "Value:" + v.getText()+" saved succesfuly",Toast.LENGTH_LONG);

                return true;
            }
        };
        edit_duration_between_frames.setOnEditorActionListener(editorActionListener);
        edit_duration_for_frame.setOnEditorActionListener(editorActionListener);
        edit_frame_count.setOnEditorActionListener(editorActionListener);
        edit_title.setOnEditorActionListener(editorActionListener);
    }


    private void loadLastSettings() {
        Context context = getApplicationContext();
        String lastValue;
        lastValue = SharedPreferencesManager.loadValue(context, SharedPreferencesManager.KEY_DURATION_BETWEEN_FRAMES);
        if (!lastValue.isEmpty()) {
            edit_duration_between_frames.setText(lastValue);
        }
    }

    @Override
    protected Presenter getPresenter() {
        return new SettingsPresenter();
    }

    @Override
    protected void bind() {
        mPresenter.bind(this);
    }

    @Override
    protected void unbind() {
        mPresenter.unbind();
    }


    @Override
    public void onError(String message) {

    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(this, StartActivity.class));
        finish();
    }
}
