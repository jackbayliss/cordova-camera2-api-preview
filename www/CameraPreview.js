var exec = require('cordova/exec');

exports.startCameraPreview = function (arg0, success, error) {
    exec(success, error, 'CameraPreview', 'CameraActivity', [arg0]);
};
exports.takePhoto = function (arg0, success, error) {
    exec(success, error, 'CameraPreview', 'takePhoto', [arg0]);
};
exports.stopCamera = function (arg0, success, error) {
    exec(success, error, 'CameraPreview', 'stopCamera', [arg0]);
};