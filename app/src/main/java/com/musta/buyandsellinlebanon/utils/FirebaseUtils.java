package com.musta.buyandsellinlebanon.utils;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.dynamiclinks.DynamicLink;
import com.google.firebase.dynamiclinks.FirebaseDynamicLinks;
import com.google.firebase.dynamiclinks.ShortDynamicLink;

public class FirebaseUtils {


    public static void buildDeepLinkVideo(final Context context, @NonNull Uri deepLink, String imageUrl, String title, String description) {


        String domain = "buyandsellinlebanon.page.link";
        DynamicLink.SocialMetaTagParameters.Builder params = new DynamicLink.SocialMetaTagParameters.Builder();
        params.setImageUrl(Uri.parse(imageUrl));
        params.setDescription(description);
        params.setTitle(title);

        FirebaseDynamicLinks.getInstance()
                .createDynamicLink()
                .setLink(deepLink)
                .setDynamicLinkDomain(domain)
                .setAndroidParameters(new DynamicLink.AndroidParameters.Builder().build())
                .setSocialMetaTagParameters(new DynamicLink.SocialMetaTagParameters.Builder()
                        .setTitle(title)
                        .setDescription(description)
                        .setImageUrl(Uri.parse(imageUrl))
                        .build())
                .buildShortDynamicLink(ShortDynamicLink.Suffix.SHORT)
                .addOnCompleteListener(new OnCompleteListener<ShortDynamicLink>() {
                    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP_MR1)
                    @Override
                    public void onComplete(@NonNull Task<ShortDynamicLink> task) {
                        if (task.isSuccessful()) {
                            Uri shortLink = task.getResult().getShortLink();
                            Uri flowchartLink = task.getResult().getPreviewLink();
                            Intent shareIntent = new Intent();
                            shareIntent.setAction(Intent.ACTION_SEND);
                            shareIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            shareIntent.putExtra(Intent.EXTRA_TEXT, shortLink.toString());
                            shareIntent.setType("text/plain");

                            final String shareAction = "MyAction";
                            Intent receiver = new Intent(context, MyBroadcastReceiver.class);
                            receiver.putExtra("test", "test");
                            receiver.setAction("MyAction");
                            PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, receiver, PendingIntent.FLAG_UPDATE_CURRENT);
                            context.startActivity(Intent.createChooser(shareIntent, "share", pendingIntent.getIntentSender()));
                        }
                    }
                });
    }
}
