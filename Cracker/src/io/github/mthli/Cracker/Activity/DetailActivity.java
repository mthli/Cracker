package io.github.mthli.Cracker.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import io.github.mthli.Cracker.R;

public class DetailActivity extends Activity {
    private String content;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail);

        getActionBar().setDisplayHomeAsUpEnabled(true);

        content = getIntent().getStringExtra(getString(R.string.detain_intent_content));

        TextView textView = (TextView) findViewById(R.id.detail_textview);
        textView.setText(content);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.detail_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            case R.id.detail_menu_share:
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_SEND);
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_TEXT, content);
                startActivity(Intent.createChooser(intent, getString(R.string.share_label)));
                break;
            default:
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}
