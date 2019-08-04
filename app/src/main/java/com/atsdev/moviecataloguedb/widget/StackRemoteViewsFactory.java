package com.atsdev.moviecataloguedb.widget;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.os.Binder;
import android.os.Bundle;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.atsdev.moviecataloguedb.R;
import com.atsdev.moviecataloguedb.models.MovieItem;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.Target;

import java.util.concurrent.ExecutionException;

import static com.atsdev.moviecataloguedb.database.DatabaseContract.MovieColumns.CONTENT_URI;

class StackRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {

    private final Context context;
    private Cursor cursor;

    StackRemoteViewsFactory(Context mContext) {
        this.context = mContext;
    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onDataSetChanged() {
        if (cursor != null) {
            cursor.close();
        }
        final long identityToken = Binder.clearCallingIdentity();
        cursor = context.getContentResolver().query(
                CONTENT_URI, null, null, null, null);
        Binder.restoreCallingIdentity(identityToken);
    }

    @Override
    public void onDestroy() {
        if (cursor != null) {
            cursor.close();
        }
    }

    @Override
    public int getCount() {
        if (cursor != null) {
            return cursor.getCount();
        } else {
            return 0;
        }
    }

    @Override
    public RemoteViews getViewAt(int i) {
        if (getCount() > 0) {
            MovieItem item = getItem(i);
            RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.favorite_widget);
            try {
                Bitmap bitmap = Glide.with(context)
                        .asBitmap()
                        .load(item.getPoster())
                        .submit(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
                        .get();

                remoteViews.setImageViewBitmap(R.id.imageView, bitmap);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }

            Bundle extras = new Bundle();
            extras.putInt(ImageBannerWidget.EXTRA_ITEM, i);
            Intent fillInIntent = new Intent();
            fillInIntent.putExtras(extras);

            remoteViews.setOnClickFillInIntent(R.id.imageView, fillInIntent);
            return remoteViews;
        } else {
            return new RemoteViews(context.getPackageName(), R.layout.favorite_widget);
        }
    }

    private MovieItem getItem(int position) {
        if (cursor.moveToPosition(position)) {
            return new MovieItem(cursor);
        } else {
            throw new IllegalStateException("Position invalid!");
        }
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int i) {
        return cursor.moveToPosition(i) ? cursor.getLong(0) : i;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }
}
