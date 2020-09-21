package com.bumptech.glide;


import android.net.Uri;
import android.text.TextUtils;


public class RequestBuilder<TranscodeType> {
    /* ... */

    public RequestBuilder<TranscodeType> load(String str) { // TODO: __REPLACE_METHOD
        if (!TextUtils.isEmpty(str) && str.startsWith("file://"))
            return loadGeneric(Uri.parse(str));
        else
            return loadGeneric(str);
    }

    private RequestBuilder<TranscodeType> loadGeneric(Object obj) {
        /* ... */

        return this;
    }

    /* ... */
}
