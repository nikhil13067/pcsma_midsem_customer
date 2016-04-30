package com.example.nikhil.aircanteen_customer;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class MainActivity extends AppCompatActivity{
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    ArrayList<Item> sampleItems;
    private TextView item_name, item_price, item_time,item_quantity;
    private Button button_decrement,button_increment;
    private ImageView item_image,veg_image;
    static ArrayList<Item> selectedItems;
    static String ROOT_URL = "http://192.168.54.76:8080/";
    static boolean status=false;
    ProgressDialog loading;
    static RecyclerView recyclerView;
    static boolean flag_finish = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


       /* DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);*/
        sampleItems = new ArrayList<>();
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("order status",BaseNavigationDrawer.orders.toString());

                selectedItems = new ArrayList<>();
                for(Item item:sampleItems){
                    if(item.getQuantity()>0 ){
                        selectedItems.add(item);
                    }
                }
                Intent intent = new Intent(MainActivity.this,CartActivity.class);
                startActivity(intent);
            }
        });

        //getItemsFromServer();
        //Toast.makeText(getApplicationContext(),sampleItems.toString(),Toast.LENGTH_LONG).show();
        sampleItems = createSampleData();
        initViews(sampleItems);

        /*loading = ProgressDialog.show(this,"Fetching Data","Please wait...",false,false);
        loading.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                Toast.makeText(getApplicationContext(),sampleItems.toString(),Toast.LENGTH_LONG).show();
            }
        });
*/
        item_name = (TextView)findViewById(R.id.card_item_name);
        item_price = (TextView)findViewById(R.id.item_price);
        item_time = (TextView)findViewById(R.id.item_time);
        item_quantity = (TextView)findViewById(R.id.quantity_text_view);
        button_decrement = (Button)findViewById(R.id.button_decrement);
        button_increment = (Button)findViewById(R.id.button_increment);
        item_image = (ImageView)findViewById(R.id.card_tem_image);
        veg_image = (ImageView)findViewById(R.id.item_veg);


    }

    private void getItemsFromServer() {
        //sampleItems = new ArrayList<>();
        final ProgressDialog loading = ProgressDialog.show(this,"Fetching Data","Please wait...",false,false);
        final ArrayList<Item> someItems = new ArrayList<>();
        RestAdapter adapter = new RestAdapter.Builder()
                .setEndpoint(MainActivity.ROOT_URL)
                .build();
        API api = adapter.create(API.class);
        api.getAllItems(new Callback<ArrayList<Item>>(){
            @Override
            public void success(ArrayList<Item> items, Response response) {
                sampleItems = items;
                loading.dismiss();
                Toast.makeText(getApplicationContext(), "Items fetched", Toast.LENGTH_LONG).show();
                Log.d("itemResponse", items.toString());

                ListIterator<Item> itr = items.listIterator();

                //Storing the data in our list
                status=true;
                //Toast.makeText(getApplicationContext(),sampleItems.toString(),Toast.LENGTH_LONG).show();
            }

            @Override
            public void failure(RetrofitError error) {
                Toast.makeText(getApplicationContext(),"Items not fetched" , Toast.LENGTH_LONG).show();
                error.printStackTrace();
            }
        });
        //return someItems;
    }


    private ArrayList<Item> createSampleData(){
        sampleItems = new ArrayList<>();
        Item burger = new Item(1,"Burger",35,2,true,"VEG",R.drawable.burger);
        Item sandwich = new Item(2,"Sandwich",35,2,true,"VEG",R.drawable.sandwich_veg);
        Item pizza = new Item(3,"Pizza",50,20,true,"NON-VEG",R.drawable.pizza);
        Item thali1 = new Item(4,"Thali",50,10,true,"VEG",R.drawable.thali_veg);
        Item thali2 = new Item(5,"Thali",70,10,true,"NON-VEG",R.drawable.thali_non_veg);
        sampleItems.add(burger);
        sampleItems.add(sandwich);
        sampleItems.add(pizza);
        sampleItems.add(thali1);
        sampleItems.add(thali2);
        return sampleItems;
    }
    private void initViews(final ArrayList<Item> sampleItems){
        recyclerView = (RecyclerView)findViewById(R.id.card_recycler_view);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);

        RecyclerView.Adapter adapter = new DataAdapterItems(sampleItems);
        recyclerView.setAdapter(adapter);

        recyclerView.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
            GestureDetector gestureDetector = new GestureDetector(getApplicationContext(), new GestureDetector.SimpleOnGestureListener() {

                @Override public boolean onSingleTapUp(MotionEvent e) {
                    return true;
                }

            });
            @Override
            public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {

                View child = rv.findChildViewUnder(e.getX(), e.getY());
                if(child != null && gestureDetector.onTouchEvent(e)) {
                    int position = rv.getChildAdapterPosition(child);
                    Toast.makeText(getApplicationContext(), sampleItems.get(position).toString(), Toast.LENGTH_SHORT).show();
                }
                return false;
            }

            @Override
            public void onTouchEvent(RecyclerView rv, MotionEvent e) {

            }

            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

            }
        });


    }

    @Override
    protected void onResume() {
        super.onResume();
        if(flag_finish == true){
            flag_finish = false;
            this.finish();
        }
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
