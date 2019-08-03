package com.atsdev.moviecataloguedb.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.widget.RemoteViews;
import android.widget.Toast;

import com.atsdev.moviecataloguedb.R;

import java.util.Objects;

public class ImageBannerWidget extends AppWidgetProvider {

    private static final String TOAST_ACTION = "com.atsdev.moviecataloguedb.TOAST_ACTION";
    public static final String EXTRA_ITEM = "com.atsdev.moviecataloguedb.EXTRA_ITEM";
    public static final String UPDATE_WIDGET = "update_widget";

    static void setUpdateWidget(Context context, AppWidgetManager appWidgetManager, int appWidgetId) {

        Intent intent = new Intent(context, StackWidgetService.class);
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
        intent.setData(Uri.parse(intent.toUri(Intent.URI_INTENT_SCHEME)));

        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.favourite_widget);
        views.setRemoteAdapter(R.id.stack_view, intent);
        views.setEmptyView(R.id.stack_view, R.id.empty_view);

        Intent intent1 = new Intent(context, ImageBannerWidget.class);
        intent1.setAction(ImageBannerWidget.TOAST_ACTION);
        intent1.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);

        intent.setData(Uri.parse(intent.toUri(Intent.URI_INTENT_SCHEME)));
        PendingIntent pi = PendingIntent.getBroadcast(context, 0, intent1, PendingIntent.FLAG_UPDATE_CURRENT);
        views.setPendingIntentTemplate(R.id.stack_view, pi);
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);

        if(intent.getAction() != null){
            if(intent.getAction().equals(TOAST_ACTION)){
                String title = intent.getStringExtra(EXTRA_ITEM);
                Toast.makeText(context, "Favorite: " + title, Toast.LENGTH_SHORT).show();
            }
        }

        if(Objects.equals(intent.getAction(), UPDATE_WIDGET)){
            AppWidgetManager manager = AppWidgetManager.getInstance(context);
            int[] ids = manager.getAppWidgetIds(new ComponentName(context, ImageBannerWidget.class));

            manager.notifyAppWidgetViewDataChanged(ids, R.id.stack_view);
        }
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        for (int appWidgetId : appWidgetIds) {
            setUpdateWidget(context, appWidgetManager, appWidgetId);
        }
    }
}
