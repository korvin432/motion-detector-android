# Motion Detector Android

## Description

The preview of the camera will be drawn on a surface. At this point the preview input of the camera will be analysed at a given interval.
To detect a movement the picture will be dived into tiles. For each tile the average brightness will be calculated. If a average value of a single tile differs from the previous value, a motion is registered.

## Screenshots
Application screenshots:

<img src="https://psv4.userapi.com/c848020/u151656187/docs/d16/0218ce2ce681/Screenshot_2018-08-18-17-48-37.png?extra=XYB00oJv_0QttkjWzruji7oTDnuszXQtiBM4enOsAnybbFVsxf8Gx9MBEgxG2VyfiI9gxtQxOzfsxnX3q30SgYQXawPQNb9gV3b6HqnTaUFSkKVHXu4I0UTU6_qnsaABFKibtzbfnKzuApOMeQ" width="25%" heighth="25%"> <img src="https://psv4.userapi.com/c848024/u151656187/docs/d8/f6bb9596a9f5/Screenshot_2018-08-18-17-49-01.png?extra=aNOEUfk51odxjIFsKk41A2hr5QX4P4QNoc6xh3WJdH02zo4nFoCP2n99DZINdR6iGjcQzGKABxC14OT3rNYHLyCi2FZ3n_QkAV3HFa6zTXkHMJyxHsPhvaJW1SJaO-ElNtOTehQ6dGfq6rKS8Q" width="45%" heighth="45%">

## Integration

1. Copy all classes of the subpackge `motiondetection` into your project. Adapt the package names.
2. There must be a `SurfaceView` in your layout:
```
<SurfaceView
    android:layout_width="254dp"
    android:layout_height="118dp"
    android:id="@+id/surfaceView" />
```
3. create instance of `MotionDetector`
```
motionDetector = new MotionDetector(this, (SurfaceView) findViewById(R.id.surfaceView));
```
4. call lifecycle methods: `motionDetector.onResume() and .onPause()`
5. register callbacks
```
motionDetector.setMotionDetectorCallback(new MotionDetectorCallback() {
    @Override
    public void onMotionDetected() {
        total++;
        //Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        //v.vibrate(80);
        txtStatus.setText("Motion detected");
        txtStatus2.setText("Total images: " + total);
    }

    @Override
    public void onTooDark() {
        txtStatus.setText("Too dark here");
    }
});
```

## Customize parameters

There are some important parameters that can be adjusted at runtime.

| Method | Description | Default |
| --- | --- | --- |
| motionDetector.setCheckInterval(500); | milliseconds between pictures to compare. less = less energy cos t | 500 |
| motionDetector.setLeniency(20); | maximal tolerence of difference in pictures. less = more sensible | 20 |
| motionDetector.setMinLuma(1000); | minimum brightness. if lower the callback onTooDark is called | 1000 |

## Notice

1. Permissions must be given in the AndroidManifest.xml
```
<uses-feature android:name="android.hardware.camera" />
<uses-permission android:name="android.permission.CAMERA"/>
```
2. If `targetSdkVersion` is greater than 21 you have to implement Runtime Permissions.
3. The detection is only running while the activity is active. It may make sense to build in a Wake-Lock.
4. Parts of the code is taken from https://github.com/phishman3579/android-motion-detection and https://github.com/jjoe64/android-motion-detection.
