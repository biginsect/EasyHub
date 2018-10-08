package com.biginsect.easyhub.app;

import android.content.Context;
import android.support.annotation.NonNull;

import com.biginsect.easyhub.R;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.load.engine.cache.InternalCacheDiskCacheFactory;
import com.bumptech.glide.module.AppGlideModule;
import com.bumptech.glide.request.RequestOptions;

/**
 *设置Glide全局缓存大小、占位图
 * @author biginsect
 */

@GlideModule
public final class MyAppGlideModule extends AppGlideModule {

    @Override
    public void applyOptions(@NonNull Context context, @NonNull GlideBuilder builder) {
        super.applyOptions(context, builder);
        builder.setDiskCache(new InternalCacheDiskCacheFactory(context, AppConfig.HTTP_CACHE_MAX_AGE));
        builder.setDefaultRequestOptions(RequestOptions.placeholderOf(R.drawable.app_logo));
    }
}
