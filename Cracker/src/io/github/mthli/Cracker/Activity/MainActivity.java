package io.github.mthli.Cracker.Activity;

import android.app.Activity;
import android.app.NotificationManager;
import android.content.*;
import android.os.Bundle;
import android.view.*;
import android.widget.*;
import io.github.mthli.Cracker.Crash.CrashUnit;
import io.github.mthli.Cracker.Data.DataAction;
import io.github.mthli.Cracker.Crash.CrashAdapter;
import io.github.mthli.Cracker.Crash.CrashItem;
import io.github.mthli.Cracker.R;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity {
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;

    private CrashAdapter adapter;
    private List<CrashItem> list = new ArrayList<CrashItem>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        preferences = getSharedPreferences(getString(R.string.sp_cracker), MODE_PRIVATE);
        editor = preferences.edit();

        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        manager.cancel(CrashUnit.NOTIFICATION_ID);

        initUI();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        DataAction action = new DataAction(this);

        switch (menuItem.getItemId()) {
            case R.id.main_menu_refresh:
                action.open(true);
                list.clear();
                for (CrashItem item : action.list()) {
                    list.add(item);
                }
                action.close();
                adapter.notifyDataSetChanged();
                break;
            case R.id.main_menu_clear:
                action.open(true);
                action.clear();
                action.close();
                list.clear();
                adapter.notifyDataSetChanged();
                break;
            case R.id.main_menu_about:
                // TODO
                break;
            default:
                break;
        }

        return super.onOptionsItemSelected(menuItem);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    private void initUI() {
        Switch switcher = (Switch) findViewById(R.id.main_header_switch);
        switcher.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean check) {
                editor.putBoolean(getString(R.string.sp_notification), check).commit();
            }
        });
        switcher.setChecked(preferences.getBoolean(getString(R.string.sp_notification), false));

        ListView listView = (ListView) findViewById(R.id.main_listview);

        TextView empty = (TextView) findViewById(R.id.main_empty);
        listView.setEmptyView(empty);

        adapter = new CrashAdapter(this, R.layout.crash_item, list);
        listView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        DataAction action = new DataAction(this);
        action.open(false);
        list.clear();
        for (CrashItem item : action.list()) {
            list.add(item);
        }
        action.close();
        adapter.notifyDataSetChanged();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                // TODO
            }
        });
    }
}
