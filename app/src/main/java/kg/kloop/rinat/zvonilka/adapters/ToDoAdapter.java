package kg.kloop.rinat.zvonilka.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import kg.kloop.rinat.zvonilka.R;
import kg.kloop.rinat.zvonilka.data.ToDo;


public class ToDoAdapter extends BaseListAdapter {
    Context context;
    List<ToDo> toDoList;

    public ToDoAdapter(Context context, List<ToDo> toDoList) {
        this.context = context;
        this.toDoList = toDoList;
    }

    @Override
    public int getCount() {
        return toDoList.size();
    }

    @Override
    public ToDo getItem(int i) {
        return toDoList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    public void add(List toDos) {
        toDoList.addAll(toDos);
    }


    @Override
    public void replaceAdapter(List list) {
        toDoList = list;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        View v = view;
        if (v == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.user_todo_list, null);
        }
        ToDo toDo = getItem(i);

        TextView name = (TextView) v.findViewById(R.id.user_to_do_list_name);
        TextView events = (TextView) v.findViewById(R.id.user_to_do_list_notes);

        if (toDo != null) {
            String nameStr = context.getString(R.string.name);
            name.setText(nameStr);
        }
        String dateStr = context.getResources().getString(R.string.notes) + toDo.getNote();
        events.setText(dateStr);

        return v;
    }
}
