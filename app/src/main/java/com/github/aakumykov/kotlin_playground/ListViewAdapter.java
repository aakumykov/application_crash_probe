package com.github.aakumykov.kotlin_playground;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.IdRes;
import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;

import java.util.List;

public abstract class ListViewAdapter<T> extends ArrayAdapter<T> {

    private final LayoutInflater inflater;
    @LayoutRes private final int layoutRes;
    @IdRes private final int titleId;
    private final List<T> externalList;


    public ListViewAdapter(Context context,
                           @LayoutRes int resource,
                           @IdRes int titleId,
                           List<T> externalList
    ) {
        super(context, resource, externalList);

        this.layoutRes = resource;
        this.titleId = titleId;
        this.externalList = externalList;

        this.inflater = LayoutInflater.from(context);
    }


    public abstract String getTitle(T listItem, int itemPosition);


    @NonNull
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {

        ViewHolder viewHolder;

        if(convertView==null){
            convertView = inflater.inflate(this.layoutRes, parent, false);
            viewHolder = new ViewHolder(convertView, titleId);
            convertView.setTag(viewHolder);
        }
        else{
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.titleView.setText(getTitle(externalList.get(position), position));

        return convertView;
    }


    public void setList(List<T> list) {
        externalList.clear();
        externalList.addAll(list);
        notifyDataSetChanged();
    }


    private static class ViewHolder {
        final TextView titleView;
        ViewHolder(View view, @IdRes int titleId){
            titleView = view.findViewById(titleId);
        }
    }
}
