package kg.kloop.rinat.zvonilka.adapters;

import android.content.Context;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import kg.kloop.rinat.zvonilka.R;
import kg.kloop.rinat.zvonilka.data.Event;

public class EventsAdapter extends BaseListAdapter{

    Context context;
    List<Event> events;

    @Override
    public void replaceAdapter(List list) {
        events = list;
    }

    public EventsAdapter(Context context, List<Event> events) {
        this.context = context;
        this.events = events;
    }

    @Override
    public int getCount() {
        return events.size();
    }

    @Override
    public Event getItem(int i) {
        return events.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    public void add(List events){
        this.events.addAll(events);
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View v = view;
        Event event = events.get(i);

        if (view == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.user_event_list, null);
        }

        TextView name = (TextView) v.findViewById(R.id.user_event_list_event_name);
        TextView city = (TextView) v.findViewById(R.id.user_event_list_event_note);
        TextView date = (TextView) v.findViewById(R.id.user_event_list_event_date);
        name.setTextColor(context.getResources().getColor(R.color.textColor));

        String nameStr = event.getName();
        name.setText(nameStr);
        String cityStr = context.getResources().getString(R.string.city) + event.getCity();
        city.setText(cityStr);
        String dateStr = context.getResources().getString(R.string.date) + event.getDateOfEvent();
        date.setText(dateStr);
        return v;
    }
}
