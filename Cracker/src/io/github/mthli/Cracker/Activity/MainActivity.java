package io.github.mthli.Cracker.Activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.NotificationManager;
import android.content.*;
import android.net.Uri;
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
    public boolean onOptionsItemSelected(MenuItem item) {
        DataAction action = new DataAction(this);

        switch (item.getItemId()) {
            case R.id.main_menu_refresh:
                action.open(true);
                list.clear();
                for (CrashItem i : action.list()) {
                    list.add(i);
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
                showAboutDialog();
                break;
            default:
                break;
        }

        return super.onOptionsItemSelected(item);
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

        final ListView listView = (ListView) findViewById(R.id.main_listview);

        TextView empty = (TextView) findViewById(R.id.main_empty);
        listView.setEmptyView(empty);
        registerForContextMenu(listView);

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
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this, DetailActivity.class);
                intent.putExtra(getString(R.string.detain_intent_content), list.get(position).getContent());
                startActivity(intent);
            }
        });
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View view, ContextMenu.ContextMenuInfo info) {
        super.onCreateContextMenu(menu, view, info);
        getMenuInflater().inflate(R.menu.context_menu, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        switch (item.getItemId()) {
            case R.id.context_menu_delete:
                DataAction action = new DataAction(this);
                action.open(true);
                action.delete(list.get(info.position));
                action.close();
                list.remove(info.position);
                adapter.notifyDataSetChanged();
                break;
            case R.id.context_menu_share:
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_SEND);
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_TEXT, list.get(info.position).getContent());
                startActivity(Intent.createChooser(intent, getString(R.string.share_label)));
                break;
            default:
                break;
        }

        return super.onContextItemSelected(item);
    }

    private AlertDialog dialog;

    private void showAboutDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.about_label);

        View view = LayoutInflater.from(this).inflate(R.layout.about, null, false);
        builder.setView(view);

        builder.setPositiveButton(R.string.about_ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialog.hide();
                dialog.dismiss();
            }
        });

        builder.setNegativeButton(R.string.about_github, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.app_github)));
                startActivity(intent);
            }
        });

        builder.setNeutralButton(R.string.about_gmail, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:" + getString(R.string.app_email)));
                startActivity(intent);
            }
        });

        builder.setCancelable(true);

        dialog = builder.create();
        dialog.show();
    }
}
