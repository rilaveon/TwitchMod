package com.bumptech.glide;


import android.net.Uri;


public class RequestBuilder<TranscodeType> {
    /* ... */

    public RequestBuilder<TranscodeType> load(String str) { // TODO: __REPLACE_METHOD
        if (str != null && str.startsWith("file://"))
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
