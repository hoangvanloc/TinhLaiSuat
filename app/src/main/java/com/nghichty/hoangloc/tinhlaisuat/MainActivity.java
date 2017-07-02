package com.nghichty.hoangloc.tinhlaisuat;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.DigitsKeyListener;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private Spinner spNganhang,spKihan,spPhuongthuc;
    private EditText edxLaisuat,edxTiengoc,edxTongtien,edxSothang;
    private Button btnTinhlai,btnNhaplai;
    private String listChukiBIDV[] =
            {" 1 Tuần "," 2 Tuần"," 3 Tuần "," 1 Tháng "," 2 Tháng ", " 3 Tháng "," 4 Tháng ",
             " 5 Tháng ", " 6 Tháng"," 7 Tháng "," 8 Tháng "," 9 Tháng"," 10 Tháng "," 11 Tháng",
             " 12 Tháng "," 15 Tháng"," 18 Tháng ", " 24 Tháng", " 36 Tháng"};
    private String listPhuongthuc[]={"Quay vòng gốc","Quay vòng cả gốc và lãi"};
    private double lai_suat=0.3,ki_han=0.25,Tong_tien,so_thang,chu_ki,tien_goc;
    private int phuong_thuc=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }
   private void init()
   {
       spNganhang = (Spinner)findViewById(R.id.spNganhang);
       spKihan = (Spinner)findViewById(R.id.spKihan);
       edxLaisuat = (EditText)findViewById(R.id.edxLaisuat);
       spPhuongthuc = (Spinner) findViewById(R.id.spPhuongthuc);
       edxSothang = (EditText)findViewById(R.id.edxSothang);
       edxTiengoc = (EditText)findViewById(R.id.edxTiengoc);
       edxTongtien = (EditText)findViewById(R.id.edxTongtien);
       btnTinhlai = (Button)findViewById(R.id.btnTinhlai);
       btnNhaplai = (Button)findViewById(R.id.btnNhaplai);

       NumberFormat nf = NumberFormat.getInstance();
       if (nf instanceof DecimalFormat) {
           DecimalFormatSymbols sym = ((DecimalFormat) nf).getDecimalFormatSymbols();
           char decSeparator = sym.getDecimalSeparator();
           edxTiengoc.setKeyListener(DigitsKeyListener.getInstance("0123456789" + decSeparator));
       }

       List<String> listNganhang = new ArrayList<>();
       listNganhang.add("BIDV");
       ArrayAdapter<String> arrNganhang = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,listNganhang);
       arrNganhang.setDropDownViewResource(android.R.layout.simple_list_item_1);
       spNganhang.setAdapter(arrNganhang);

       ArrayAdapter<String> arrPhuongthuc = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,listPhuongthuc);
       arrPhuongthuc.setDropDownViewResource(android.R.layout.simple_list_item_1);
       spPhuongthuc.setAdapter(arrPhuongthuc);
       spPhuongthuc.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
           @Override
           public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
               phuong_thuc = i;
           }

           @Override
           public void onNothingSelected(AdapterView<?> adapterView) {

           }
       });

       ArrayAdapter<String> arrChukiBIDV = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,listChukiBIDV);
       arrChukiBIDV.setDropDownViewResource(android.R.layout.simple_list_item_1);
       spKihan.setAdapter(arrChukiBIDV);
       spKihan.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
           @Override
           public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
               setKihan(i);
           }

           @Override
           public void onNothingSelected(AdapterView<?> adapterView) {

           }
       });

       btnTinhlai.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               double so_chu_ki=0,temp_du=0;
               if(edxSothang.getText()==null||edxTiengoc==null)
               {
                   so_thang=0;
                   tien_goc=0;
               }else {
                   so_thang = Integer.parseInt(edxSothang.getText().toString());
                   tien_goc = Integer.parseInt(edxTiengoc.getText().toString());
               }
                Tong_tien=tien_goc;
                   so_chu_ki= so_thang/ki_han;
                   temp_du = so_thang%ki_han;
                   if(so_chu_ki<1) {
                       Tong_tien *= 1 + so_thang*30*0.2/36500;
                   }else if(temp_du==0){
                       if(phuong_thuc==0)
                       {
                           Tong_tien *= 1+ so_chu_ki*lai_suat/100;
                       }else{
                           for(int i=0;i<so_chu_ki;i++) Tong_tien *= 1  + lai_suat/100;
                       }
                   }else {
                       if(phuong_thuc==0)
                       {
                           Tong_tien *= 1+ so_chu_ki*lai_suat/100;
                       }else{
                           for(int i=0;i<so_chu_ki;i++) Tong_tien *= 1  + lai_suat/100;
                       }
                       Tong_tien *= 1+ temp_du*30*0.2/36500;
                   }

               Log.d("Phuong thuc ",phuong_thuc+"");
               Log.d("Temp du ",temp_du+"");
               Log.d("Tong tien ",Tong_tien+"");
               edxTongtien.setText((int)Tong_tien+"");
           }
       });

       btnNhaplai.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               edxSothang.setText("");
               edxTiengoc.setText("");
               edxTongtien.setText("");
           }
       });

       edxTongtien.setOnLongClickListener(new View.OnLongClickListener() {
           @Override
           public boolean onLongClick(View view) {
               return true;
           }
       });
   }
    private void setKihan(int i)
    {
        switch (i)
        {
            case 0: {
                lai_suat = 2.1/365;
                edxLaisuat.setText("0.3 %/năm");
                ki_han = 0.25;
            }break;
            case 1: {
                lai_suat = 4.2/365;
                edxLaisuat.setText("0.3 %/năm");
                ki_han = 0.5;
            }break;
            case 2: {
                lai_suat = 6.3/365;
                edxLaisuat.setText("0.3 %/năm");
                ki_han = 0.75;
            }break;
            case 3: {
                lai_suat = 4.4/12;
                edxLaisuat.setText("4.4 %/năm");
                ki_han = 1;
            }break;
            case 4: {
                lai_suat = 4.4/12;
                edxLaisuat.setText("4.4 %/năm");
                ki_han = 2;
            }break;
            case 5: {
                lai_suat = 4.9/4;
                edxLaisuat.setText("4.9 %/năm");
                ki_han = 3;
            }break;
            case 6: {
                lai_suat = 4.9/4;
                edxLaisuat.setText("4.9 %/năm");
                ki_han = 4;
            } break;
            case 7: {
                lai_suat = 5.1*5/12;
                edxLaisuat.setText("5.1 %/năm");
                ki_han = 5;
            } break;
            case 8: {
                lai_suat = 5.4/2;
                edxLaisuat.setText("5.4 %/năm");
                ki_han = 6;
            }break;
            case 9: {
                lai_suat = 5.4/2;
                edxLaisuat.setText("5.4 %/năm");
                ki_han = 7;
            }break;
            case 10: {
                lai_suat = 5.4/2;
                edxLaisuat.setText("5.4 %/năm");
                ki_han = 8;
            } break;
            case 11: {
                lai_suat = 5.9*3/4;
                edxLaisuat.setText("5.9 %/năm");
                ki_han = 9;
            }break;
            case 12:{
                lai_suat = 5.9*3/4;
                edxLaisuat.setText("5.9 %/năm");
                ki_han = 10;
            }break;
            case 13: {
                lai_suat = 5.9*3/4;
                edxLaisuat.setText("5.9 %/năm");
                ki_han = 11;
            } break;
            case 14:{
                lai_suat = 6.8;
                edxLaisuat.setText("6.8 %/năm");
                ki_han = 12;
            }break;
            case 15: {
                lai_suat = 6.8+0.95;
                edxLaisuat.setText("6.8 %/năm");
                ki_han = 15;
            } break;
            case 16:{
                lai_suat = 7+3.5;
                edxLaisuat.setText("7 %/năm");
                ki_han = 18;
            }break;
            case 17:{
                lai_suat = 14;
                edxLaisuat.setText("7 %/năm");
                ki_han = 24;
            }break;
            case 18: {
                lai_suat = 21;
                edxLaisuat.setText("7 %/năm");
                ki_han = 36;
            }   break;
        }
    }
}
