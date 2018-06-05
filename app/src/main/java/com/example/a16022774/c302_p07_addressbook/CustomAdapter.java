package com.example.a16022774.c302_p07_addressbook;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class CustomAdapter extends ArrayAdapter<Contact> {
    Context context;
    ArrayList<Contact> contacts;
    int resource;

    public CustomAdapter(Context context, int resource, ArrayList<Contact> contacts) {
        super(context, resource, contacts);
        this.context = context;
        this.contacts = contacts;
        this.resource = resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View rowView = inflater.inflate(resource, parent, false);

        TextView tvName = (TextView) rowView.findViewById(R.id.tvName);
        TextView tvMobile = (TextView) rowView.findViewById(R.id.tvMobile);

        Contact contact = contacts.get(position);
        tvName.setText(contact.getFirstName() + " " + contact.getLastName());
        tvMobile.setText(contact.getMobile());

        return rowView;
    }
}
