package com.scar.android.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;

import com.android.scar.R;
import com.scar.android.Fragments.GenericStore;
import com.scar.android.Fragments.SQLiteStore;
import com.scar.android.MetaData;
import com.scar.android.StoreFrag;

public class AddServer extends FragmentActivity {
    public static int SUCCESS = 0, FAIL = 1;

    private StoreFrag frag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_server_layout);

        Spinner type = (Spinner)findViewById(R.id.asl_types);

        type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                AddServer.this.setAddContent(pos);
            }

            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        Button add = (Button) findViewById(R.id.asl_add_btn);
        add.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View arg0) {
                Intent ret = new Intent();
                ret.putExtra("type", frag.getType());
                ret.putExtra("lbl", frag.getLabel());
                ret.putExtra("host", frag.getHost());
                ret.putExtra("port", frag.getPort());
                ret.putExtra("uname", frag.getUsername());
                ret.putExtra("pass", frag.getPassword());
                setResult(SUCCESS, ret);
                //Kill this activity
                frag = null;
                AddServer.this.finish();
            }
        });


        Button cancel = (Button) findViewById(R.id.asl_cancel_btn);
        cancel.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View arg0) {
                setResult(FAIL);
                //Kill this activity
                AddServer.this.finish();
            }
        });

        setAddContent(MetaData.TYPE_MYSQL_STORE);
    }


    private void setAddContent(int type) {
        switch(type) {
            case MetaData.TYPE_CASS_STORE:
            case MetaData.TYPE_MYSQL_STORE:
                frag = new GenericStore();
                break;
            case MetaData.TYPE_SQLITE_STORE:
                frag = new SQLiteStore();
                break;
            case MetaData.TYPE_GDRIVE_STORE:
            case MetaData.TYPE_DROPBOX_STORE:
                //TODO: change this later
                frag = new GenericStore();
                break;
        }
        Bundle args = new Bundle();
        args.putInt("type", type);
        ((Fragment) frag).setArguments(args);
        getSupportFragmentManager().beginTransaction().replace(R.id.ms_content, (Fragment)frag).commit();
    }
}
