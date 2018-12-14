package com.base.project.camera.capturedialog;



import com.base.project.camera.OnPicturePathListener;

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
