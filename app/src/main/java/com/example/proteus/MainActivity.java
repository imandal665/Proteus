package com.example.proteus;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Application;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.proteus.managers.ProteusManager;
import com.flipkart.android.proteus.LayoutManager;
import com.flipkart.android.proteus.Proteus;
import com.flipkart.android.proteus.ProteusBuilder;
import com.flipkart.android.proteus.ProteusContext;
import com.flipkart.android.proteus.ProteusLayoutInflater;
import com.flipkart.android.proteus.ProteusView;
import com.flipkart.android.proteus.Styles;
import com.flipkart.android.proteus.gson.ProteusTypeAdapterFactory;
import com.flipkart.android.proteus.support.v7.layoutmanager.ProteusLinearLayoutManager;
import com.flipkart.android.proteus.value.Layout;
import com.flipkart.android.proteus.value.ObjectValue;
import com.flipkart.android.proteus.value.Value;

import org.json.JSONObject;

import java.util.Map;

public class MainActivity extends AppCompatActivity implements ProteusManager.Listener {
    private ProteusManager proteusManager;
    private ProteusLayoutInflater layoutInflater;

    ObjectValue data;
    Layout layout;
    Styles styles;
    Map<String, Layout> layouts;

    ProteusView view;
    private ViewGroup container;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ProteusDemo application = (ProteusDemo) getApplication();
        proteusManager = application.getProteusManager();

        ProteusContext context = proteusManager.getProteus().createContextBuilder(this)
                .setLayoutManager(layoutManager)
                .setCallback(callback)
//                .setImageLoader(loader)
//                .setStyleManager(styleManager)
                .build();

        layoutInflater = context.getInflater();
    }

    private ProteusLayoutInflater.Callback callback = new ProteusLayoutInflater.Callback() {
        @NonNull
        @Override
        public ProteusView onUnknownViewType(ProteusContext context, String type, Layout layout, ObjectValue data, int index) {
            return null;
        }

        @Override
        public void onEvent(String event, Value value, ProteusView view) {

        }
    };

    @Override
    public void onLoad() {
        data = proteusManager.getData();
        layout = proteusManager.getRootLayout();
        layouts = proteusManager.getLayouts();
        styles = proteusManager.getStyles();
        render();
    }

    @Override
    public void onError(@NonNull Exception e) {
        Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
    }


    private LayoutManager layoutManager = new LayoutManager() {
        @Nullable
        @Override
        protected Map<String, Layout> getLayouts() {
            return layouts;
        }
    };


    void render() {

        // remove the current view
        container.removeAllViews();

        // Inflate a new view using proteus
        long start = System.currentTimeMillis();
        view = layoutInflater.inflate(layout, data, container, 0);
        System.out.println("inflate time: " + (System.currentTimeMillis() - start));

        // Add the inflated view to the container
        container.addView(view.getAsView());
    }

    void reload() {
        proteusManager.update();
    }
}
