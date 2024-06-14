package com.example.project;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

public class MainActivity3 extends AppCompatActivity {
int pos;
    RecyclerView rview;
    LinearLayoutManager layoutManager;
    List<model> users;
    com.example.project.Adapter adapter ;
    final int ADD_CODE=1, VIEW_CODE=10, EDIT_CODE=11;
    Button add;
TextView userinfoname;
ImageView userimage,userlogout, delbtn, editbtn;
Bitmap bitmap;
Context c = this;
String gender, hobbies, name, user, pass, email, address, contact, secanswer1, secanswer2, secanswer3, secq1, secq2, secq3;
ArrayList<Bitmap> bitarray;
ArrayList<String> namearray, remarkarray,bdayarray,genderarray,hobbyarray;
@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        initialize();
        listeners();
    }

    private void initialize() {
        users = new ArrayList<>();
        bitarray = new ArrayList<>();
        namearray = new ArrayList<>();
        remarkarray = new ArrayList<>();
        bdayarray = new ArrayList<>();
        genderarray = new ArrayList<>();
        hobbyarray = new ArrayList<>();

        bitarray.add(BitmapFactory.decodeResource(getResources(),R.drawable.jett));
        namearray.add("Jett");
        remarkarray.add("Duelist");
        bdayarray.add("2001/05/04");
        genderarray.add("Female");
        hobbyarray.add("lipad lipad");
        users.add(new model(bitarray.get(bitarray.size()-1),namearray.get(namearray.size()-1), remarkarray.get(remarkarray.size()-1)));

        bitarray.add(BitmapFactory.decodeResource(getResources(),R.drawable.killjoy));
        namearray.add("Killjoy");
        remarkarray.add("Sentinel");
        bdayarray.add("2001/05/04");
        genderarray.add("Female");
        hobbyarray.add("Mag kalikot");
        users.add(new model(bitarray.get(bitarray.size()-1),namearray.get(namearray.size()-1), remarkarray.get(remarkarray.size()-1)));

        bitarray.add(BitmapFactory.decodeResource(getResources(),R.drawable.brimstone));
        namearray.add("Brimstone");
        remarkarray.add("Controller");
        bdayarray.add("2001/05/04");
        genderarray.add("Male");
        hobbyarray.add("Mag command");
        users.add(new model(bitarray.get(bitarray.size()-1),namearray.get(namearray.size()-1), remarkarray.get(remarkarray.size()-1)));

        bitarray.add(BitmapFactory.decodeResource(getResources(),R.drawable.chamber));
        namearray.add("Chamber");
        remarkarray.add("Sentinel");
        bdayarray.add("2001/05/04");
        genderarray.add("Male");
        hobbyarray.add("Mamaril");
        users.add(new model(bitarray.get(bitarray.size()-1),namearray.get(namearray.size()-1), remarkarray.get(remarkarray.size()-1)));
        rview=findViewById(R.id.rview);

        bitarray.add(BitmapFactory.decodeResource(getResources(),R.drawable.cypher));
        namearray.add("Cypher");
        remarkarray.add("Sentinel");
        bdayarray.add("2001/05/04");
        genderarray.add("Male");
        hobbyarray.add("Photoshoot");
        users.add(new model(bitarray.get(bitarray.size()-1),namearray.get(namearray.size()-1), remarkarray.get(remarkarray.size()-1)));
        rview=findViewById(R.id.rview);

        bitarray.add(BitmapFactory.decodeResource(getResources(),R.drawable.sova));
        namearray.add("Sova");
        remarkarray.add("Initiator");
        bdayarray.add("2001/05/04");
        genderarray.add("Male");
        hobbyarray.add("Mamana");
        users.add(new model(bitarray.get(bitarray.size()-1),namearray.get(namearray.size()-1), remarkarray.get(remarkarray.size()-1)));
        rview=findViewById(R.id.rview);

        bitarray.add(BitmapFactory.decodeResource(getResources(),R.drawable.breach));
        namearray.add("Breach");
        remarkarray.add("Initiator");
        bdayarray.add("2001/05/04");
        genderarray.add("Male");
        hobbyarray.add("Earth bender");
        users.add(new model(bitarray.get(bitarray.size()-1),namearray.get(namearray.size()-1), remarkarray.get(remarkarray.size()-1)));
        rview=findViewById(R.id.rview);

        bitarray.add(BitmapFactory.decodeResource(getResources(),R.drawable.astra));
        namearray.add("Astra");
        remarkarray.add("Controller");
        bdayarray.add("2001/05/04");
        genderarray.add("Female");
        hobbyarray.add("mag magic");
        users.add(new model(bitarray.get(bitarray.size()-1),namearray.get(namearray.size()-1), remarkarray.get(remarkarray.size()-1)));
        rview=findViewById(R.id.rview);

        bitarray.add(BitmapFactory.decodeResource(getResources(),R.drawable.sage));
        namearray.add("Sage");
        remarkarray.add("Sentinel");
        bdayarray.add("2001/05/04");
        genderarray.add("Female");
        hobbyarray.add("mang gamot");
        users.add(new model(bitarray.get(bitarray.size()-1),namearray.get(namearray.size()-1), remarkarray.get(remarkarray.size()-1)));
        rview=findViewById(R.id.rview);

        bitarray.add(BitmapFactory.decodeResource(getResources(),R.drawable.viper));
        namearray.add("Viper");
        remarkarray.add("Controller");
        bdayarray.add("2001/05/04");
        genderarray.add("Female");
        hobbyarray.add("poison maker");
        users.add(new model(bitarray.get(bitarray.size()-1),namearray.get(namearray.size()-1), remarkarray.get(remarkarray.size()-1)));
        rview=findViewById(R.id.rview);

        layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(rview.VERTICAL);
        rview.setLayoutManager(layoutManager);
        adapter = new Adapter(users);
        rview.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        delbtn = findViewById(R.id.delbtn);editbtn = findViewById(R.id.editbtn);
        add = findViewById(R.id.add);
        userimage = findViewById(R.id.userimage);
        userinfoname = findViewById(R.id.userinfoname);
        userlogout = findViewById(R.id.userlogout);
        Intent receive = getIntent();
        bitmap = receive.getParcelableExtra("image");
        name = receive.getExtras().getString("name");
        user = receive.getExtras().getString("user");
        pass = receive.getExtras().getString("pass");
        email = receive.getExtras().getString("email");
        gender = receive.getExtras().getString("gender");
        address = receive.getExtras().getString("address");
        contact = receive.getExtras().getString("contact");
        hobbies = receive.getExtras().getString("hobbies");
        secanswer1 = receive.getExtras().getString("secanswer1");
        secq1 = receive.getExtras().getString("secq1");
        secanswer2 = receive.getExtras().getString("secanswer2");
        secq2 = receive.getExtras().getString("secq2");
        secanswer3 = receive.getExtras().getString("secanswer3");
        secq3 = receive.getExtras().getString("secq3");
        userimage.setImageBitmap(bitmap);
        userinfoname.setText(name);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode==2)
        {
           if(data.hasExtra("name"))
           {
               namearray.add(data.getStringExtra("name"));
               byte[] bytearray = data.getByteArrayExtra("image");
               Bitmap img = BitmapFactory.decodeByteArray(bytearray, 0, bytearray.length);
               bitarray.add(img);
               remarkarray.add(data.getStringExtra("remark"));
               bdayarray.add(data.getStringExtra("bday"));
               genderarray.add(data.getStringExtra("gender"));
               hobbyarray.add(data.getStringExtra("hobby"));
               users.add(new model(bitarray.get(bitarray.size()-1),namearray.get(namearray.size()-1), remarkarray.get(remarkarray.size()-1)));
               adapter.notifyItemInserted(adapter.getItemCount()-1);
               rview.scrollToPosition(adapter.getItemCount()-1);
           }

        }
        else if (resultCode==3)
        {
            if(data.hasExtra("name"))
            {
                pos = data.getIntExtra("position",0);
                namearray.set(pos, data.getStringExtra("name"));
                byte[] bytearray = data.getByteArrayExtra("image");
                Bitmap img = BitmapFactory.decodeByteArray(bytearray, 0, bytearray.length);
                bitarray.set(pos,img);
                remarkarray.set(pos,data.getStringExtra("remark"));
                bdayarray.set(pos,data.getStringExtra("bday"));
                genderarray.set(pos,data.getStringExtra("gender"));
                hobbyarray.set(pos,data.getStringExtra("hobby"));
                users.set(pos,new model(bitarray.get(pos),namearray.get(pos), remarkarray.get(pos)));
                adapter.notifyItemChanged(pos);
                rview.scrollToPosition(pos);
            }
        }
    }

    private void listeners() {
        userlogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent logout = new Intent(c, MainActivity.class);
                logout.putExtra("image", bitmap);
                logout.putExtra("name", name);
                logout.putExtra("user", user);
                logout.putExtra("pass", pass);
                logout.putExtra("email", email);
                logout.putExtra("gender", gender);
                logout.putExtra("address", address);
                logout.putExtra("contact", contact);
                logout.putExtra("hobbies", hobbies);
                logout.putExtra("secanswer1", secanswer1);
                logout.putExtra("secq1",secq1);
                logout.putExtra("secanswer2", secanswer2);
                logout.putExtra("secq2",secq2);
                logout.putExtra("secanswer3", secanswer3);
                logout.putExtra("secq3",secq3);
                startActivity(logout);
                finish();
            }
        });
        View.OnClickListener action = new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder builder = new AlertDialog.Builder(c);
                builder.setTitle("User Information")
                        .setMessage("Name: "+name+"\nUsername: "+user+"\nPassword: "+pass+"\nEmail: "+email+"\nGender: "
                                +gender+"\nAddress: "+address+"\nHobbies: "+hobbies+"\n"+secq1+":\n"+secanswer1
                                +"\n"+secq2+":\n"+secanswer2+"\n"+secq3+":\n"+secanswer3)
                        .setCancelable(true)
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        });

                AlertDialog dialog = builder.create();
                dialog.show();
            }
        };
        userimage.setOnClickListener(action);
        userinfoname.setOnClickListener(action);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent add = new Intent(c,MainActivity4.class);
                startActivityForResult(add, ADD_CODE);
            }
        });

       adapter.setOnItemClickListener(new Adapter.OnItemClickListener() {
           @Override
           public void OnItemClick(int position) {
               Intent check = new Intent(c, MainActivity5.class);
               Bitmap img = bitarray.get(position);
               ByteArrayOutputStream stream = new ByteArrayOutputStream();
               img.compress(Bitmap.CompressFormat.PNG, 100, stream);
               byte[] bytearray = stream.toByteArray();

               check.putExtra("name",namearray.get(position));
               check.putExtra("image",bytearray);
               check.putExtra("remark", remarkarray.get(position));
               check.putExtra("bday",bdayarray.get(position));
               check.putExtra("gender", genderarray.get(position));
               check.putExtra("hobby", hobbyarray.get(position));
               startActivityForResult(check, VIEW_CODE);
           }

           @Override
           public void OnEditClick(int position) {
               Intent edit = new Intent(c, MainActivity6.class);
               Bitmap img = bitarray.get(position);
               pos = position;
               ByteArrayOutputStream stream = new ByteArrayOutputStream();
               img.compress(Bitmap.CompressFormat.PNG, 100, stream);
               byte[] bytearray = stream.toByteArray();
               edit.putExtra("name",namearray.get(position));
               edit.putExtra("image",bytearray);
               edit.putExtra("remark", remarkarray.get(position));
               edit.putExtra("bday",bdayarray.get(position));
               edit.putExtra("gender", genderarray.get(position));
               edit.putExtra("hobby", hobbyarray.get(position));
               edit.putExtra("position",pos);
               startActivityForResult(edit, EDIT_CODE);
           }

           @Override
           public void OnDeleteClick(int position) {
               AlertDialog.Builder builder = new AlertDialog.Builder(c);
               builder.setTitle("Warning")
                       .setMessage("Are you sure?\nThi entry cant be recovered after deleting")
                       .setCancelable(true)
                       .setPositiveButton("Proceed", new DialogInterface.OnClickListener() {
                           @Override
                           public void onClick(DialogInterface dialogInterface, int i) {
                            users.remove(position);
                            adapter.notifyItemRemoved(position);
                            bitarray.remove(position);
                            namearray.remove(position);
                            remarkarray.remove(position);
                            bdayarray.remove(position);
                            genderarray.remove(position);
                            hobbyarray.remove(position);
                           }
                       })
                       .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                           @Override
                           public void onClick(DialogInterface dialogInterface, int i) {

                           }
                       });

               AlertDialog dialog = builder.create();
               dialog.show();
           }
       });


    }
}