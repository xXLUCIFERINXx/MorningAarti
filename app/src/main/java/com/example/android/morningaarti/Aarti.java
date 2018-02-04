package com.example.android.morningaarti;

class Aarti {

    private String Aarti_name;
    private int Image_res;
    private int Audio_aarti;

    Aarti(String aarti_name, int image_res, int audio_aarti) {
        Aarti_name = aarti_name;
        Image_res = image_res;
        Audio_aarti = audio_aarti;
    }

    String getAartiName() {
        return Aarti_name;
    }

    int getImageRes() {
        return Image_res;
    }

    int getAudio_aarti() {
        return Audio_aarti;
    }
}
