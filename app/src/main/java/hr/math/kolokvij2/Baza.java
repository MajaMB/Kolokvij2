package hr.math.kolokvij2;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

public class Baza extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_baza);


        DBAdapter db = new DBAdapter(this);


        //---dodaj zapise u obe tablice---
        db.open();
        long id = db.insertZapis("12.1.2009.","termin1","dodadaj1");
        id = db.insertZapis("13.1.2009","termin2","dodadaj2");
        id = db.insertZapis("13.1.2009","termin3","dodadaj3");
        id = db.insertZapis("14.12.2009","termin4","dodadaj4");
        id = db.insertZapis("15.1.2009","termin5","dodadaj5");
        id = db.insertZapis("25.12.2013","termin6","dodadaj6");

        long id1 = db.insertVrsta(3,1);
        id1 = db.insertVrsta(3,2);
        id1 = db.insertVrsta(4,3);
        id1 = db.insertVrsta(4,4);
        id1 = db.insertVrsta(5,5);
        db.close();


        //ispise zadrzaje tablica ; tj cijelu bazu
        IspisiZapis(db);
        IspisiVrsta(db);

        //jos samo ispis trazenog posdatka

        //IspisiZapisByDate(db,"25.12.2013.");

                        /*
                            //--get all zapis---
                            db.open();
                            Cursor c = db.getAllZapis();
                            if (c.moveToFirst())
                            {
                                do {
                                    DisplayZapis(c);
                                } while (c.moveToNext());
                            }
                            db.close();
                            */
                        /*

                            //---get a zapis---
                            db.open();
                            Cursor cu = db.getZapis(2);
                            if (cu.moveToFirst())
                                DisplayZapis(cu);
                            else
                                Toast.makeText(this, "No contact found", Toast.LENGTH_LONG).show();
                            db.close();
                        */

                        /*
                            //---update zapis---
                            db.open();
                            if (db.updateZapis(1, "12-2-2009","termin2","dodadaj2"))
                                Toast.makeText(this, "Update successful.", Toast.LENGTH_LONG).show();
                            else
                                Toast.makeText(this, "Update failed.", Toast.LENGTH_LONG).show();
                            db.close();
                            */

                        /*
                            //---delete a zapis---
                            db.open();
                            if (db.deleteZapis(1))
                                Toast.makeText(this, "Delete successful.", Toast.LENGTH_LONG).show();
                            else
                                Toast.makeText(this, "Delete failed.", Toast.LENGTH_LONG).show();
                            db.close();
                            */
    }

    //funkcija za ispis
    public String DisplayZapis(Cursor c)
    {
        String zapis="id: " + c.getString(0) + "datum: " + c.getString(1) + "termin:"
                + c.getString(2) + "dogadaj: " + c.getString(3);
        return zapis;
    }

    //funkcija za ispis
    public String DisplayVrsta(Cursor c)
    {
        String zapis="id_V: " + c.getString(0) + "kategorija: " + c.getString(1) + "id:"
                + c.getString(2) ;
        return zapis;
    }


    public void IspisiZapis(DBAdapter db){
        String Sadrzaj="";
        db.open();
        Cursor c = db.getAllZapis();
        if (c.moveToFirst())
        {
            do {
                Sadrzaj +=  DisplayZapis(c)+"\n";
            } while (c.moveToNext());
        }
        db.close();

        TextView text=(TextView) findViewById(R.id.rokovnik);
        text.setText(Sadrzaj);
    }

    public void IspisiVrsta(DBAdapter db){
        String Sadrzaj="";
        db.open();
        Cursor c = db.getAllVrsta();
        if (c.moveToFirst())
        {
            do {
                Sadrzaj +=  DisplayVrsta(c)+"\n";
            } while (c.moveToNext());
        }
        db.close();

        TextView text=(TextView) findViewById(R.id.vrsta);
        text.setText(Sadrzaj);
    }


    public void IspisiZapisByDate(DBAdapter db,String Date){
        String Sadrzaj="";
        db.open();
        Cursor c = db.getZapisByDate(Date);
        if (c.moveToFirst())
        {
            do {
                Sadrzaj +=  DisplayZapis(c)+"\n";
            } while (c.moveToNext());
        }
        db.close();

        TextView text=(TextView) findViewById(R.id.pretraga);
        text.setText(Sadrzaj);
    }
}
