package io.github.mthli.Cracker.Activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.*;
import android.widget.ListView;
import io.github.mthli.Cracker.R;

public class MainActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        View header = LayoutInflater.from(this).inflate(R.layout.header, null, false);

        ListView listView = (ListView) findViewById(R.id.main_listview);
        listView.addHeaderView(header);
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
}
