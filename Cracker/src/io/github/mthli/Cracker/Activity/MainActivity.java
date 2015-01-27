package io.github.mthli.Cracker.Activity;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.*;
import android.widget.AdapterView;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.Switch;
import io.github.mthli.Cracker.Crash.CrashAdapter;
import io.github.mthli.Cracker.Crash.CrashItem;
import io.github.mthli.Cracker.R;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity {
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;

    private ListView listView;
    private CrashAdapter crashAdapter;
    private List<CrashItem> list = new ArrayList<CrashItem>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        preferences = getSharedPreferences(getString(R.string.sp_cracker), MODE_PRIVATE);
        editor = preferences.edit();

        initUI();
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
                // TODO
                break;
            case R.id.main_menu_about:
                // TODO
                break;
            default:
                break;
        }

        return super.onOptionsItemSelected(menuItem);
    }

    private void initUI() {
        Switch service = (Switch) findViewById(R.id.main_header_switch);
        service.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                editor.putBoolean(getString(R.string.sp_service), b).commit();
            }
        });
        service.setChecked(preferences.getBoolean(getString(R.string.sp_service), false));

        ListView listView = (ListView) findViewById(R.id.main_listview);

        crashAdapter = new CrashAdapter(this, R.layout.crash_item, list);
        listView.setAdapter(crashAdapter);
        crashAdapter.notifyDataSetChanged();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                // TODO
            }
        });
    }
}
