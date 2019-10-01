package com.nahuelpas.cuentabilidad.View.layout;

import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.nahuelpas.cuentabilidad.Database.Database;
import com.nahuelpas.cuentabilidad.Model.dao.CuentaDao;
import com.nahuelpas.cuentabilidad.Model.dao.CuentaDao_Impl;
import com.nahuelpas.cuentabilidad.R;
import com.nahuelpas.cuentabilidad.View.CuentasAdapter;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class CuentasActivity extends AppCompatActivity {

    CuentaDao cuentaDao;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cuentas);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), NuevaCuentaActivity.class);
                i.putExtra("class", CuentasActivity.class);
                startActivity(i);
            }
        });
        cuentaDao = new CuentaDao_Impl(Database.getAppDatabase(getApplicationContext()));
        initRecyclerView();
    }

    private void initRecyclerView(){
        RecyclerView recyclerView;
        RecyclerView.Adapter mAdapter;

        recyclerView = (RecyclerView) findViewById(R.id.recyclerCuentas);

        mAdapter = new CuentasAdapter(cuentaDao.getAll());
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);
        recyclerView.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean b) {

            }

            @Override
            public boolean onInterceptTouchEvent(RecyclerView recyclerView, MotionEvent motionEvent) {
                /*
                try {
                    View child = recyclerView.findChildViewUnder(motionEvent.getX(), motionEvent.getY());

                    if (child != null && mGestureDetector.onTouchEvent(motionEvent)) {

                        int position = recyclerView.getChildAdapterPosition(child);

                        Toast.makeText(MyActivity.this,"The Item Clicked is: "+ position ,Toast.LENGTH_SHORT).show();

                        return true;
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
*/
                return false;
            }

            @Override
            public void onTouchEvent(RecyclerView recyclerView, MotionEvent motionEvent) {

            }
        });
    }
}
