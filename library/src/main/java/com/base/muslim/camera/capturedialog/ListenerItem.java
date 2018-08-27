package com.base.muslim.camera.capturedialog;



import com.base.muslim.camera.OnPicturePathListener;

import java.io.Serializable;


public class ListenerItem implements Serializable {
    private OnPicturePathListener listener;

    public ListenerItem() {
    }

    public OnPicturePathListener getListener() {
        return listener;
    }

    public void setListener(OnPicturePathListener listener) {
        this.listener = listener;
    }

}
