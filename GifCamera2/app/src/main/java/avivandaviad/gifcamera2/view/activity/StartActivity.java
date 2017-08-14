package avivandaviad.gifcamera2.view.activity;

import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import avivandaviad.gifcamera2.R;
import avivandaviad.gifcamera2.presenter.BaseView;
import avivandaviad.gifcamera2.presenter.OnSettingCallBack;
import avivandaviad.gifcamera2.presenter.OnStartCallBack;
import avivandaviad.gifcamera2.presenter.Presenter;
import avivandaviad.gifcamera2.presenter.StartPresenter;
import avivandaviad.gifcamera2.view.BaseActivity;

/**
 * Created by DELL on 20/07/2017.
 */

public class StartActivity extends BaseActivity implements BaseView, OnStartCallBack,OnSettingCallBack {


    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.start_main);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        ImageView startImageBtn = (ImageView) findViewById(R.id.start_btn);
        Button btnSetting = (Button) findViewById(R.id.btn_settings);
        startImageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ((StartPresenter) mPresenter).onStartPressed();

            }
        });
        btnSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((StartPresenter) mPresenter).onSettingsPressed();

            }
        });
    }



    @Override
    public void onRequestPermissionsResult(int permsRequestCode, String[] permissions, int[] grantResults) {

        if (permsRequestCode == 200) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                ((StartPresenter) mPresenter).onStartPressed();
            } else {
                Toast.makeText(StartActivity.this,
                        "Permission denied, You need to give permission to use this feature",
                        Toast.LENGTH_SHORT).show();
            }
        }
    }


    @Override
    protected Presenter getPresenter() {
        return new StartPresenter();
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
    public void onStartPressed() {

        //((StartPresenter)mPresenter).onStartPressed();
    }

    @Override
    public void onSettingsPressed() {

    }
}
