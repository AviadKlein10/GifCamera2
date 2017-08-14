package avivandaviad.gifcamera2.presenter;

import android.content.Intent;

import avivandaviad.gifcamera2.view.activity.SettingsActivity;

/**
 * Created by Aviad on 14/08/2017.
 */
public class SettingsPresenter extends Presenter<SettingsActivity> implements OnSettingCallBack{
    @Override
    public void onResume() {
        
    }

    @Override
    public void onPause() {

    }

    @Override
    public void onSettingsPressed() {
        Intent intent = new Intent(mView.getApplicationContext(),SettingsActivity.class);
        mView.startActivity(intent);
        mView.finish();
    }

}
