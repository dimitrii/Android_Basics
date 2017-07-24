package com.example.firstname_lastname.listviews;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends Activity {

    EditText editText;
    CustomArrayAdapter customArrayAdapter;
    Button sendButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final ArrayList<Message> messages = new ArrayList<>();
        messages.add(new Message() {{
            message = "Welcome to my ListView Example!!";
            sender = "Bob";
        }});

        customArrayAdapter = new CustomArrayAdapter(this,messages);
        ListView listView = (ListView)findViewById(R.id.mainListView);
        listView.setAdapter(customArrayAdapter);

        editText = (EditText)findViewById(R.id.mainEditText);

        sendButton = (Button)findViewById(R.id.sendButton);
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create New Message
                Message newMessage = new Message();
                newMessage.message = editText.getText().toString();
                newMessage.sender = "Default";
                editText.setText("");
                // Send
                customArrayAdapter.add(newMessage);
            }
        });
    }

    private class CustomArrayAdapter extends ArrayAdapter<Message> {
        private final Context context;
        private final ArrayList<Message> arrayList;

        public CustomArrayAdapter(Context context, ArrayList<Message> objects) {
            super(context, 0, objects);
            this.context = context;
            this.arrayList = objects;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View rowView = inflater.inflate(R.layout.row_layout, parent, false);
            TextView textView1 = (TextView)rowView.findViewById(R.id.mainText);
            Message message = arrayList.get(position);
            textView1.setText(message.message);
            return rowView;
        }
    }
}
