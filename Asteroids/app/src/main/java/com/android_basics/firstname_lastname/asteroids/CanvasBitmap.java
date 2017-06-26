package com.android_basics.firstname_lastname.asteroids;

import android.graphics.Bitmap;

class CanvasBitmap extends CanvasObject {
    // Bitmap
    protected Bitmap bitmap;
    public Bitmap getBitmap() {
        return this.bitmap;
    }
    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }
}
