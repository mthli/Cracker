package io.github.mthli.Cracker.Crash;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.github.curioustechizen.ago.RelativeTimeTextView;
import io.github.mthli.Cracker.R;

import java.util.List;

public class CrashAdapter extends ArrayAdapter<CrashItem> {
    private Context context;
    private int layoutResId;
    private List<CrashItem> list;

    public CrashAdapter(Context context, int layoutResId, List<CrashItem> list) {
        super(context, layoutResId, list);

        this.context = context;
        this.layoutResId = layoutResId;
        this.list = list;
    }

    private class Holder {
        public ImageView icon;
        public TextView title;
        public RelativeTimeTextView time;
        public TextView content;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        View view = convertView;
        Holder holder;

        if (view == null) {
            view = LayoutInflater.from(context).inflate(layoutResId, viewGroup, false);
            holder = new Holder();

            holder.icon = (ImageView) view.findViewById(R.id.crash_item_icon);
            holder.title = (TextView) view.findViewById(R.id.crash_item_title);
            holder.time = (RelativeTimeTextView) view.findViewById(R.id.crash_item_time);
            holder.content = (TextView) view.findViewById(R.id.crash_item_content);

            view.setTag(holder);
        } else {
            holder = (Holder) view.getTag();
        }

        CrashItem item = list.get(position);

        holder.icon.setImageDrawable(CrashUnit.getAppIcon(context, item.getPackageName()));
        holder.title.setText(CrashUnit.getAppName(context, item.getPackageName()));
        holder.time.setReferenceTime(item.getTime());
        holder.content.setText(item.getContent());

        return view;
    }
}
