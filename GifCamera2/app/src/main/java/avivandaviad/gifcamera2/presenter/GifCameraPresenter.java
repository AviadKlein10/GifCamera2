package avivandaviad.gifcamera2.presenter;

import android.graphics.Bitmap;

import java.util.ArrayList;

import avivandaviad.gifcamera2.Utils;
import avivandaviad.gifcamera2.custom.thread.GenerateGifFile;
import avivandaviad.gifcamera2.custom.thread.GifCreationCallback;
import avivandaviad.gifcamera2.view.activity.GifCameraActivity;
import avivandaviad.gifcamera2.view.fragments.CameraFrag;

/**
 * Created by DELL on 17/07/2017.
 */

public class GifCameraPresenter extends Presenter<GifCameraActivity> implements GifCreationCallback {
    private static final int CAPTURE_FRAME_RATE = 700;
    private static int FRAMES_COUNT = 7;
    private static final int COUNT_DOWN_TIME = 3;

    private ArrayList<Bitmap> previewBitmaps;
    private ArrayList<Bitmap> gifBitmaps;
    private int maxPreviewSize = 920;

    public ArrayList<Bitmap> getPreviewBitmaps() {
        return previewBitmaps;
    }

    public GifCameraPresenter() {
        previewBitmaps = new ArrayList<>();
        gifBitmaps = new ArrayList<>();
    }

    public void onStartClicked() {
        new CountDownThread().start();

    }


    @Override
    public void onResume() {

    }

    @Override
    public void onPause() {

    }

    public void takePicture(Bitmap bitmap) {

        previewBitmaps.add(Utils.getResizedBitmap(bitmap, CameraFrag.maxSize));
        gifBitmaps.add(Utils.getResizedBitmap(bitmap, 600));
    }

    @Override
    public void onGifFileReady(final String uri) {
        mView.getHandler().post(new Runnable() {
            @Override
            public void run() {
                mView.handleGifReady(uri);
            }
        });
    }

    public void setFrameCount(String newValue) {
        FRAMES_COUNT = Integer.parseInt(newValue);
    }

    public interface GifCameraPresenterCallback extends BaseView {
        void takeFrontCameraPicture();
    }

    private void generateGifFromBitmaps() {
        new GenerateGifFile(gifBitmaps, GifCameraActivity.PREVIEW_FRAME_RATE, this).start();
    }




    private class CaptureBitmapsThread extends Thread {

        @Override
        public void run() {

            for (int i = 1; i <= FRAMES_COUNT; i++) {
                mView.takeFrontCameraPicture();
                mView.changeFrameCountNum(String.valueOf(i));

                try {
                    Thread.sleep(CAPTURE_FRAME_RATE);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            mView.getHandler().post(new Runnable() {
                @Override
                public void run() {
                    showPreview();
                    generateGifFromBitmaps();
                }
            });


        }
    }

    private void showPreview() {
        mView.showPreviewLayout();
    }


    class CountDownThread extends Thread {

        @Override
        public void run() {
            for (int i = COUNT_DOWN_TIME; i >= 0; i--) {
                if (i != 0) {
                    mView.changeCounterNum(String.valueOf(i));
                } else {
                    mView.getHandler().post(new Runnable() {
                        @Override
                        public void run() {
                            mView.changeCounterNum("Shot!");
                        }
                    });
                }

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (i == 0) {
                    startCaptureBitmaps();
                }
            }
        }
    }

    private void startCaptureBitmaps() {
        new CaptureBitmapsThread().start();
    }
}








