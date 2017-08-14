package avivandaviad.gifcamera2.view.activity;

import android.annotation.SuppressLint;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;

import avivandaviad.gifcamera2.R;
import avivandaviad.gifcamera2.presenter.BaseView;
import avivandaviad.gifcamera2.presenter.GifCameraPresenter;
import avivandaviad.gifcamera2.presenter.Presenter;
import avivandaviad.gifcamera2.view.BaseActivity;
import avivandaviad.gifcamera2.view.fragments.CameraFrag;
import avivandaviad.gifcamera2.view.fragments.CameraFragCallBack;
import avivandaviad.gifcamera2.view.fragments.PreviewCallBack;
import avivandaviad.gifcamera2.view.fragments.PreviewFrag;

public class GifCameraActivity extends BaseActivity implements BaseView, GifCameraPresenter.GifCameraPresenterCallback, CameraFragCallBack, PreviewCallBack {


    public static final int PREVIEW_FRAME_RATE = 110;

    private String gifUri;
    private CameraFrag cameraFrag;
    private Handler handler;
    private boolean inPreview;
    private PreviewFrag previewFrag;


    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        handler = new Handler();
        showCameraFragment();

    }

    @Override
    protected Presenter getPresenter() {
        return new GifCameraPresenter();
    }


    @Override
    protected void bind() {
        mPresenter.bind(this);
    }

    @Override
    protected void unbind() {
        mPresenter.unbind();
    }


    private void showCameraFragment() {
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        cameraFrag = CameraFrag.newInstance(this);
        ft.replace(R.id.frame_counter, cameraFrag);
        ft.commit();
    }


    public Handler getHandler() {
        return handler;
    }

    @Override
    public void onError(String message) {

    }

    public void changeCounterNum(final String text) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                cameraFrag.showCountDownText(true);
                cameraFrag.changeCountDownText(text);


            }
        });
    }

    @Override
    public void takeFrontCameraPicture() {
        handler.post(new Runnable() {
            @Override
            public void run() {
                cameraFrag.showCountDownText(false);
                ((GifCameraPresenter) mPresenter).takePicture(cameraFrag.getBitmapFromTextureView());
            }
        });
    }

    public void changeFrameCountNum(final String text) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                cameraFrag.changeFrameCounter(text);
            }
        });
    }

    public void showPreviewLayout() {
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        previewFrag = PreviewFrag.newInstance(this, PREVIEW_FRAME_RATE);
        ft.replace(R.id.frame_counter, previewFrag);
        ft.commit();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                previewFrag.startImagesPreview(((GifCameraPresenter) mPresenter).getPreviewBitmaps());
            }
        }, 1000);

        inPreview = true;
    }

    @Override
    public void onStartClick() {
        ((GifCameraPresenter) mPresenter).onStartClicked();
    }


    @Override
    public void onSharePressed() {
        Intent shareIntent = new Intent(android.content.Intent.ACTION_SEND);
        shareIntent.setType("image/gif");
        shareIntent.putExtra(Intent.EXTRA_STREAM, Uri.parse(gifUri));
        startActivity(Intent.createChooser(shareIntent, "Gif"));
    }

    @Override
    protected void onResume() {
        super.onResume();

        if(!inPreview){
            showCameraFragment();
        }

    }

    @Override
    public void onBackPressed() {
        if (inPreview) {
            onBackClicked();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void onBackClicked() {
        startActivity(new Intent(this, GifCameraActivity.class));
        finish();
    }

    public void handleGifReady(String uri) {
        gifUri = uri;
        previewFrag.makeGifSharable(uri);
    }
}
