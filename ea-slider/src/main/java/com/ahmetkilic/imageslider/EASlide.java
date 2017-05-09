package com.ahmetkilic.imageslider;

/*
 * Copyright 2017 Ahmet Kılıç
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import android.net.Uri;
import android.os.Bundle;

import java.io.File;
import java.io.Serializable;

/*
 * Created by AhmetPC on 2.05.2017.
 */

public class EASlide implements Serializable {
    private transient Bundle extras;

    private String path;
    private File file;
    private int resourceId;
    private Uri uri;

    public EASlide(File file) {
        this.file = file;
    }

    public EASlide(String path) {
        this.path = path;
    }

    public EASlide(int resourceId) {
        this.resourceId = resourceId;
    }

    public EASlide(Uri uri) {
        this.uri = uri;
    }

    public void setExtras(Bundle extras) {
        this.extras = extras;
    }

    public Bundle getExtras() {
        if (extras == null)
            extras = new Bundle();

        return extras;
    }

    public String getPath() {
        return path;
    }

    public File getFile() {
        return file;
    }

    public int getResourceId() {
        return resourceId;
    }

    public Uri getUri() {
        return uri;
    }
}
