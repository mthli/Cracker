package io.github.mthli.Cracker.Activity;

import android.app.Activity;
import android.content.*;
import android.os.Bundle;
import android.view.*;
import android.widget.*;
import io.github.mthli.Cracker.Crash.CrashAction;
import io.github.mthli.Cracker.Crash.CrashAdapter;
import io.github.mthli.Cracker.Crash.CrashItem;
import io.github.mthli.Cracker.R;
import io.github.mthli.Cracker.Service.CrackerService;

import java.util.ArrayList;
import java.util.List;

public class CrackerActivity extends Activity {
    protected class UpdateReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            list.clear();
            CrashAction action = new CrashAction(context);
            action.open(false);
            for (CrashItem item : action.list()) {
                list.add(item);
            }
            action.close();
            adapter.notifyDataSetChanged();
        }
    }
    private UpdateReceiver receiver;
    private Intent intent;

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

        intent = new Intent(this, CrackerService.class);

        initUI();
        initReceiver();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.main_menu_clear:
                CrashAction action = new CrashAction(this);
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
    public boolean onKeyDown(int keyCode, KeyEvent keyEvent) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            unregisterReceiver(receiver);
            finish();
        }
        return true;
    }

    @Override
    public void onDestroy() {
        unregisterReceiver(receiver);
        super.onDestroy();
    }

    private void initUI() {
        Switch service = (Switch) findViewById(R.id.main_header_switch);
        service.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean check) {
                editor.putBoolean(getString(R.string.sp_service), check).commit();

                if (check) {
                    startService(intent);
                } else {
                    stopService(intent);
                }
            }
        });
        service.setChecked(preferences.getBoolean(getString(R.string.sp_service), false));

        ListView listView = (ListView) findViewById(R.id.main_listview);

        TextView empty = (TextView) findViewById(R.id.main_empty);
        listView.setEmptyView(empty);

        adapter = new CrashAdapter(this, R.layout.crash_item, list);
        listView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        CrashAction action = new CrashAction(this);
        action.open(false);
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

    private void initReceiver() {
        receiver = new UpdateReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction(getString(R.string.receiver_intent_action));
        registerReceiver(receiver, filter);
    }
}
