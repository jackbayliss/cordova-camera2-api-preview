# Cordova Camera 2 API Preview
Cordova camera preview using  https://github.com/CameraKit/camerakit-android 

(So covers camera api 1 & 2)

Not an Android / Cordova Developer PR's appreciated. 



### Installation
To access the camera download the zip & copy it into a folder outside of your project, for example CameraPreview. 

Then, open a terminal in your ionic project and do the following.

```
ionic cordova plugin add ../CameraPreview

```

Once doing that, just add the below to your code.. In my example I use it with ionViewWillLoad()
```
ionViewWillLoad(){
 window.cordova.plugins.CameraPreview.startCameraPreview();
  }
```

And it's that simple....

If you're running on a physical device do
```
ionic cordova run android --device
```
### Development
Want to contribute? Feel free to PR.

### Todos

 - <strike> Embed within the Ionic Activity itself (or under), so it doesn't overlay </strike>(done)
 - <strike>Add TakePicture ability. </strike>(done)
 - iOS implementation?

License
----

MIT



