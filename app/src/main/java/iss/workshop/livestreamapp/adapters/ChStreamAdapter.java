package iss.workshop.livestreamapp.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.google.android.material.chip.Chip;

import iss.workshop.livestreamapp.R;
import java.util.List;

import iss.workshop.livestreamapp.models.Stream;

public class ChStreamAdapter extends BaseAdapter {
    private Context context;
    protected List<Stream> streams;


    public ChStreamAdapter (Context context, List<Stream> streams){
        this.context = context;
        this.streams = streams;
    }

    @Override
    public int getCount() {
        return streams.size();
    }

    @Override
    public Object getItem(int i) {
        return streams.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.channelrow, viewGroup, false);
        }

        Stream currentStream = streams.get(i);

        //channel name
        TextView channelName = (TextView) view
                .findViewById(R.id.entire_row)
                .findViewById(R.id.top_container)
                .findViewById(R.id.text_fields)
                .findViewById(R.id.channel_name);
        channelName.setText(currentStream.getChannelStream().getName());

        //stream name
        TextView streamName = (TextView) view
                .findViewById(R.id.entire_row)
                .findViewById(R.id.top_container)
                .findViewById(R.id.text_fields)
                .findViewById(R.id.stream_name);
        streamName.setText(currentStream.getTitle());

        //stream name
        /*
        TextView streamDesc = (TextView) view
                .findViewById(R.id.entire_row)
                .findViewById(R.id.top_container)
                .findViewById(R.id.text_fields)
                .findViewById(R.id.stream_desc);
        streamDesc.setText(currentStream.getDescription());

         */

        //stream name
        TextView streamDate = (TextView) view
                .findViewById(R.id.bottom_container)
                .findViewById(R.id.date_of_stream);
        streamDate.setText(currentStream.getSchedule().toString());

        Chip liveChip = (Chip) view
                .findViewById(R.id.bottom_container)
                .findViewById(R.id.live_chip);
        liveChip.setText("LIVE");

        return view;
    }


}
