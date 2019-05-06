/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tugas2;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author ASTRONAUT
 */
public class Tugas2 {

    /**
     * @param args the command line arguments
     */
    
        static String getValueBetweenBrackets(String att) {
            int start = 0; // '(' position in string
            int end = 0; // ')' position in string
            for(int i = 0; i < att.length(); i++) { 
                if(att.charAt(i) == '(') // Looking for '(' position in string
                    start = i;
                else if(att.charAt(i) == ')') // Looking for ')' position in  string
                    end = i;
            }
            String number = att.substring(start+1, end); // you take value between start and end
            return number;
    }
    
        // BFR dan Fanout Ratio
        static int BFR(String[] baris0, String[] table) {
            String[] nilai = baris0[0].split(";");
            int B = Integer.parseInt(getValueBetweenBrackets(nilai[1]));
            int len = table.length;
            String r = table[len - 3];
            int R = Integer.parseInt(getValueBetweenBrackets(r));
            int BFR = (int) Math.floor(B/R);
            return BFR;
        }
        
        static int FOR(String[] baris0, String[] table){
            String[] nilai = baris0[0].split(";");
            int P = Integer.parseInt(getValueBetweenBrackets(nilai[0]));
            int B = Integer.parseInt(getValueBetweenBrackets(nilai[1]));
            int len = table.length;
            String v = table[len - 1];
            int V = Integer.parseInt(getValueBetweenBrackets(v));
            int Y = (int) Math.floor(B / (V + P));
            return Y;
        }
        
        // tampilin BFR dan Fanout ration
        static void menuBFRnFOR(String[] baris0, String[] table){ //baris0 == dataSplit, table == isiBaris1 / isiBaris2 / isiBaris3
            String[] nilai = baris0[0].split(";");
            int P = Integer.parseInt(getValueBetweenBrackets(nilai[0]));
            int B = Integer.parseInt(getValueBetweenBrackets(nilai[1]));
            System.out.println("P = " + P);
            System.out.println("B = " + B);
            System.out.println("Nama tabel : " + table[0]);
            System.out.println("BFR " + table[0] +" = " +BFR(baris0, table));
            System.out.println("Fanout Ratio (Y) " + table[0] + " = " + FOR(baris0, table));
            System.out.println("--------------------------");
            
        }
            
        // Total Blok data dan Blok index setiap tabel n / bfr
        static int jumlahBlok(String[] baris0, String[] table){
            String[] nilai = baris0[0].split(";");
            int len = table.length;
            String n = table[len - 2];
            int N = Integer.parseInt(getValueBetweenBrackets(n));
            int nBlok =  (int) Math.ceil(N / BFR(baris0, table)) + 1;
            return nBlok;
        }
        
        static int indexTable(String[] baris0, String[] table){
            String[] nilai = baris0[0].split(";");
            int len = table.length;
            String n = table[len - 2];
            int N = Integer.parseInt(getValueBetweenBrackets(n));
            int index = (int) Math.ceil(N / FOR(baris0, table)) + 1;
            return index;
        }
        
        static void menuJumlahBlok(String[] baris0, String[] table){
            String namaTable = table[0];
            System.out.println("Table data " + namaTable + " = " + jumlahBlok(baris0, table));
            System.out.println("Index " + namaTable + " = " + indexTable(baris0, table));
        }
        // Pencarian Record
        static int cariRecordNonIndex(int x, String[] table, String[] baris0){
            int len = table.length;
            String n = table[len - 2];
            int N = Integer.parseInt(getValueBetweenBrackets(n));
            int jmlBlok = jumlahBlok(baris0, table);
//            int blokPerRecord = (int) Math.ceil(N / jmlBlok); 
            int recordKe = (int) Math.ceil(x / BFR(baris0, table)) + 1;
            return recordKe;
        }
        
        static int cariRecordBerIndex(int x, String[] table, String[] baris0){
            int len = table.length;
            String n = table[len - 2];
            int N = Integer.parseInt(getValueBetweenBrackets(n));
            int jmlBlok = jumlahBlok(baris0, table);
            int jmlIdx = indexTable(baris0, table);
            int indexPerRecord = (int) Math.ceil(N / jmlIdx);
            int blokPerRecord = (int) Math.ceil(N / jmlBlok);
            int posisiMasukIndex = x % indexPerRecord; // div
            int recordKe = (int) (Math.ceil(posisiMasukIndex / blokPerRecord) + 1);
            return recordKe;
        }
        
        static String[] getNamaTable(String[] tbl1, String[] tbl2, String[] tbl3, String namaTable) {
            String[] ret = null;
            if (tbl1[0].equals(namaTable)) {
                ret =  tbl1;
            } else if (tbl2[0].equals(namaTable)) {
                ret = tbl2;
            } else if (tbl3[0].equals(namaTable)) {
                ret = tbl3;
            } else {
                ret = null;
            }
            return ret;
        }
        
        // tampilin blok yang diakses saat pencarian record
        static void menuCariRecord(String[] baris0){
            String[] isiBaris1 = baris0[1].split(";");  //Developer
            String[] isiBaris2 = baris0[2].split(";");  // Client
            String[] isiBaris3 = baris0[3].split(";");   //Project
            Scanner sc = new Scanner(System.in);
            System.out.print("Masukkan nama table : ");
            String namaTable = sc.nextLine();
            System.out.print("Cari record ke : ");
            int idx = sc.nextInt();
            String[] cekTable = getNamaTable(isiBaris1, isiBaris2, isiBaris3, namaTable); 
            System.out.println("Tanpa Menggunakan Index : " + cariRecordNonIndex(idx, cekTable, baris0));
            System.out.println("Menggunakan Index : " + cariRecordBerIndex(idx, cekTable, baris0));
        }
        
        // Hitung Cost
        static int costA1(String[] baris0, String[] table){
            // cost = banyaknya blok / 2
            int br = jumlahBlok(baris0, table);
            int cost = br/2;
            return cost;
        }
        
        static int costA2(String[] baris0, String[] table){
            int y = FOR(baris0, table);
            int len = table.length;
            String N = table[len - 2];
            int n = Integer.parseInt(getValueBetweenBrackets(N));
            int b = (int) Math.ceil(n / BFR(baris0, table));
            int h1 = (int) Math.ceil(Math.log(b) / Math.log(y));
            int cost = h1 + 1;
            return cost;
        }
        
        static int costBNLJ1(String[] baris0, String[] table1, String[] table2){
            int len = table1.length;
            String N = table1[len - 2];
            int nr = Integer.parseInt(getValueBetweenBrackets(N));
            int bs = jumlahBlok(baris0, table2);
            int br = jumlahBlok(baris0, table1);
            int cost = nr * bs + br;
            return cost;
        }
        static int costBNLJ2(String[] baris0, String[] table1, String[] table2){
            int len = table2.length;
            String N = table2[len - 2];
            int nr = Integer.parseInt(getValueBetweenBrackets(N));
            int bs = jumlahBlok(baris0, table1);
            int br = jumlahBlok(baris0, table2);
            int cost = nr * bs + br;
            return cost;
        }
        
        // output hasil query
        
        // cek isi tabel selected
        static void cekIsiTabel(String[] query, String[] isi) {
            ArrayList<String> outpoot = new ArrayList<String>();
            int p = 0;
            for (int i = 0; i<query.length; i++) {
                for (int j = 1; j<=isi.length-1; j++) {
                    if (query[i].equals(isi[j])) {
                        outpoot.add(isi[j]);
                        p++;
                    }
                }
            }
            if (p == query.length) {
//                System.out.print("Kolom : ");
                for (int k = 0; k<p; k++) {
                    if (k != p-1) {
                        System.out.print(outpoot.get(k)+", ");
                    } else {
                        System.out.println(outpoot.get(k)); 
                    }   
                }
            } else {
                System.out.println("Error");
            }
        }
        
        static String[] cekTabelJoin(String[] tbl1, String[] tbl2, String[] tbl3, String tblJoin) {
            String[] ret = null;
            if (tbl1[0].equals(tblJoin)) {
                ret =  tbl1;
            } else if (tbl2[0].equals(tblJoin)) {
                ret = tbl2;
            } else if (tbl3[0].equals(tblJoin)) {
                ret = tbl3;
            } 
            return ret;
        }   
        
       static String tabelJoin(String[] tab1, String[] tab2, String attribut, String[] isiSQL) {
            try {
                boolean a = true;
                boolean b = true;
                int i=1;
                String[] pars = parsing(isiSQL);
                while ((a == true) || (b==true)){
                    if (tab1[i].equals(attribut)) {
                        a = false;
                    }
                    if (tab2[i].equals(attribut)) {
                        b = false;
                    }
                    i++;
                }
                if (a==false && b==false){
                    System.out.print("List Kolom 1: ");
                    for (int j = 0; j<pars.length; j++) {
                        for (int k = 1; k<tab1.length; k++) {
                            if (pars[j].equals(tab1[k])) {
                                System.out.print(pars[j] + " ");
                            }
                        }
                    }
                    System.out.println("");
                    System.out.print("List Kolom 2: ");
                    for (int j = 0; j<pars.length; j++) {
                        for (int k = 1; k<tab2.length; k++) {
                            if (pars[j].equals(tab2[k])) {
                                System.out.print(pars[j] + " ");
                            }
                        }
                    }
                } return null;
            }
            catch(ArrayIndexOutOfBoundsException e) {
                System.out.println("Syntax Error");
                return null;
            } 
       
        }
        
        static String[] parsing(String[] query){
            String[] sqlparse = query[1].split(",");
            return sqlparse;
        }

        
        // Query handling
        
        static void queryHandling(String[] table) throws IOException{
            
            // buat file shared pool
            File file = new File("sharedPool.txt");
            FileWriter fileWriter = new FileWriter(file,true);
            BufferedWriter writer = new BufferedWriter(fileWriter);
            
            String[] isiBaris1 = table[1].split(";");  //Developer
            String[] isiBaris2 = table[2].split(";");  // Client
            String[] isiBaris3 = table[3].split(";");   //Project
            Scanner sc = new Scanner(System.in);
            System.out.println("Masukkan query : ");
            String query = sc.nextLine();
            writer.append("Query : " + query + "\n");
            String[] splitQuery = query.split(" ");
            int len = splitQuery.length;
            String attTitikoma = splitQuery[len-1]; // get titik koma
            int lenAttTitikoma = splitQuery[len-1].length();
            char titikoma = attTitikoma.charAt(lenAttTitikoma-1);
            String namaTable = null;
            if (titikoma == ';') {
                // cek select * biasa  SELECT * FROM Developer;
                if (splitQuery[1].equals("*") && splitQuery[0].equals("select") && splitQuery[2].equals("from")) {
                    if (splitQuery[3].equals(isiBaris1[0])) {
                        namaTable = isiBaris1[0];
                        System.out.println("Table : " + namaTable);
                    } else if (splitQuery[3].equals(isiBaris2[0])) {
                        namaTable = isiBaris2[0];
                        System.out.println("Table : " + namaTable);
                    } else if (splitQuery[3].equals(isiBaris3[0])){
                        namaTable = isiBaris3[0];
                        System.out.println("Table : " + namaTable);
                    } else {
                        System.out.println("Error : Table Not Found");
                    }
                    String[] tableSelected = getNamaTable(isiBaris1, isiBaris2, isiBaris3, namaTable);
                    int lenTable = tableSelected.length;
                    System.out.print("Attribute : ");
                    for (int i = 1; i < lenTable-3; i++){
                        System.out.print(tableSelected[i] + " , ");
                    }
                    System.out.println("");
                    // Cari QEP dan COST
                    // QEP 1
                    System.out.println("QEP 1");
                    System.out.print("PROJECTION ");
                    for (int i = 1; i < lenTable-3; i++){
                        System.out.print(tableSelected[i] + " , ");
                    }
                    System.out.println(" -- on the fly");
                    System.out.print("SELECTION " + splitQuery[len-3] + " " + splitQuery[len-2] + " " + splitQuery[len-1] + " -- A1 Key");
                    System.out.println("");
                    System.out.println(tableSelected[0]);
                    System.out.println("Cost : " + costA1(table, tableSelected) + " block");
                    // QEP 2
                    System.out.println("QEP 2");
                    System.out.print("PROJECTION ");
                    for (int i = 1; i < lenTable-3; i++){
                        System.out.print(tableSelected[i] + " , ");
                    }
                    System.out.println(" -- on the fly");
                    System.out.print("SELECTION " + splitQuery[len-3] + " " + splitQuery[len-2] + " " + splitQuery[len-1] + " -- A2");
                    System.out.println("");
                    System.out.println(tableSelected[0]);
                    System.out.println("Cost : " + costA2(table, tableSelected) + " block");
                    int cost1 = costA1(table, tableSelected);
                    int cost2 = costA2(table, tableSelected);
                    if (Math.min(cost1,cost2) == cost1){
                        System.out.println("===> QEP optimal : QEP 1");
                        writer.append("PROJECTION ");
                        for (int i = 1; i < lenTable-3; i++){
                            writer.append(tableSelected[i] + ", ");
                        }
                        writer.append(" -- on the fly \n");
                        writer.append("SELECTION " + splitQuery[len-3] + " " + splitQuery[len-2] + " " + splitQuery[len-1] + " -- A1 key \n");
                        writer.append(tableSelected[0] + "\n");
                        writer.append("Cost : " + cost1 + " block" + "\n \n");
                        writer.close();
                        
                    } else {
                        System.out.println("===> QEP optimal : QEP 2");
                        writer.append("PROJECTION ");
                        for (int i = 1; i < lenTable-3; i++){
                            writer.append(tableSelected[i] + ", ");
                        }
                        writer.append(" -- on the fly \n");
                        writer.append("SELECTION " + splitQuery[len-3] + " " + splitQuery[len-2] + " " + splitQuery[len-1] + " -- A2 \n");
                        writer.append(tableSelected[0] + "\n");
                        writer.append("Cost : " + cost2 + " block" + "\n \n");
                        writer.close();
                    }
                    
                // query select tanpa *    
                } else {
                    if (!splitQuery[1].equals("*") && !splitQuery[len-4].equals("join")){
                        String[] getTable = null;
                        if (splitQuery[len-5].equals(isiBaris1[0])) {
                            System.out.println("Tabel : " + isiBaris1[0]);
                            getTable = isiBaris1;
                            System.out.print("Attribut : ");
                            cekIsiTabel(parsing(splitQuery), isiBaris1);
                        } else if (splitQuery[len-5].equals(isiBaris2[0])) {
                            System.out.println("Tabel : " + isiBaris2[0]);
                            getTable = isiBaris2;
                            System.out.print("Attribut : ");
                            cekIsiTabel(parsing(splitQuery), isiBaris2);
                        }  else if (splitQuery[len-5].equals(isiBaris3[0])) {
                           System.out.println("Tabel : " + isiBaris3[0]);
                           getTable = isiBaris3;
                           System.out.print("Attribut : ");
                            cekIsiTabel(parsing(splitQuery), isiBaris3);
                        }
                        System.out.println("");
                        // QEP 1
                        System.out.println("QEP 1");
                        System.out.print("PROJECTION ");
                        cekIsiTabel(parsing(splitQuery), getTable);
                        System.out.println(" -- on the fly");
                        System.out.print("SELECTION " + splitQuery[len-3] + " " + splitQuery[len-2] + " " + splitQuery[len-1] + " -- A1 key");
                        System.out.println("");
                        System.out.println(getTable[0]);
                        System.out.println("Cost : " + costA1(table, getTable) + " block");
                        // QEP 2
                        System.out.println("QEP 2");
                        System.out.print("PROJECTION ");
                        cekIsiTabel(parsing(splitQuery), getTable);
                        System.out.println(" -- on the fly");
                        System.out.print("SELECTION " + splitQuery[len-3] + " " + splitQuery[len-2] + " " + splitQuery[len-1] + " -- A2");
                        System.out.println("");
                        System.out.println(getTable[0]);
                        System.out.println("Cost : " + costA2(table, getTable) + " block");
                        int cost1 = costA1(table, getTable);
                        int cost2 = costA2(table, getTable);
                        if (Math.min(cost1,cost2) == cost1){
                            System.out.println("===> QEP optimal : QEP 1");
                            writer.append("PROJECTION " + splitQuery[1] + " -- on the fly \n");
                            writer.append("SELECTION " + splitQuery[len-3] + " " + splitQuery[len-2] + " " + splitQuery[len-1] + " -- A1 key \n");
                            writer.append(getTable[0] + "\n");
                            writer.append("Cost : " + cost1 + " block" + "\n \n");
                            writer.close();
                            
                        } else {
                            System.out.println("===> QEP optimal : QEP 2");
                            writer.append("PROJECTION " + splitQuery[1] + " -- on the fly \n");
                            writer.append("SELECTION " + splitQuery[len-3] + " " + splitQuery[len-2] + " " + splitQuery[len-1] + " -- A2 \n");
                            writer.append(getTable[0] + "\n");
                            writer.append("Cost : " + cost2 + " block" + "\n \n");
                            writer.close();
                        }
                        
                    } else {
                        // query dengan join
                        if (splitQuery[0].equals("select") && splitQuery[len-4].equals("join") && splitQuery[len-6].equals("from") && splitQuery[len-2].equals("using")) {
                            System.out.println("SQL join");
                            String tabel1 = splitQuery[len-5];
                            System.out.println("tabel 1 : " + tabel1);
                            String tabel2 = splitQuery[len-3];
                            System.out.println("tabel 2 : " + tabel2);
                            String entitasJoin = splitQuery[len-1];
                            String[] cekcek = cekTabelJoin(isiBaris1, isiBaris2, isiBaris3, tabel1);
                            String[] cekcek2 = cekTabelJoin(isiBaris1, isiBaris2, isiBaris3, tabel2);
                            String attr = getValueBetweenBrackets(entitasJoin);
                            tabelJoin(cekcek, cekcek2, attr, splitQuery);
                            // QEP 1
                            System.out.println("QEP 1");
                            System.out.println("PROJECTION " + splitQuery[1] + " -- on the fly");
                            System.out.println("    JOIN " + tabel1+"."+attr + " = " + tabel2+"."+attr + " -- BNLJ");
                            System.out.println(tabel1 + " " + tabel2);
                            System.out.println("Cost (worst case) : " + costBNLJ1(table, cekcek, cekcek2) + " block");
                            
                            // QEP 2
                            System.out.println("QEP 2");
                            System.out.println("PROJECTION " + splitQuery[1] + " -- on the fly");
                            System.out.println("    JOIN " + tabel1+"."+attr + " = " + tabel2+"."+attr + " -- BNLJ");
                            System.out.println(tabel1 + " " + tabel2);
                            System.out.println("Cost (worst case) : " + costBNLJ2(table, cekcek, cekcek2) + " block");
                            int cost1 = costBNLJ1(table, cekcek, cekcek2);
                            int cost2 = costBNLJ2(table, cekcek, cekcek2);
                            if (Math.min(cost1,cost2) == cost1){
                                System.out.println("===> QEP optimal : QEP 1");
                                writer.append("PROJECTION " + splitQuery[1] + " -- on the fly \n");
                                writer.append("    JOIN " + tabel1+"."+attr + " = " + tabel2+"."+attr + " -- Block Nested Loop Join \n");
                                writer.append(tabel1 +"     " + tabel2 + "\n");
                                writer.append("Cost (worst case) : " + cost1 + " block" + "\n \n");
                                writer.close();
                            } else {
                                System.out.println("===> QEP optimal : QEP 2");
                                writer.append("PROJECTION " + splitQuery[1] + " -- on the fly \n");
                                writer.append("    JOIN " + tabel1+"."+attr + " = " + tabel2+"."+attr + " -- Block Nested Loop Join \n");
                                writer.append(tabel1 +"     " + tabel2 + "\n");
                                writer.append("Cost (worst case) : " + cost2 + " block" + "\n \n");
                                writer.close();
                            }
                            
                            } else {
                                    System.out.println("Syntax error");
                            }
                    
                        }
                    }
                } else {
                    System.out.println("Error : Missing ;");
                }
            }
         
   
    public static void main(String[] args) throws FileNotFoundException, IOException {
        // Main program 
        String fileName = "data2.txt";   
        File file = new File(fileName);
        Scanner inputStream = new Scanner(file);            
        String data = inputStream.next();
        String[] dataSplit = data.split("#");
        String[] isiBaris0 = dataSplit[0].split(";");
        String[] isiBaris1 = dataSplit[1].split(";");  //Developer
        String[] isiBaris2 = dataSplit[2].split(";");  // Client
        String[] isiBaris3 = dataSplit[3].split(";");  // project
        
        // Menu tampilan
        System.out.println("TUBES SBD");
        System.out.println("--------------------");
        System.out.println("1. Lihat BFR dan Fanout Ratio");
        System.out.println("2. Lihat Jumlah blok data");
        System.out.println("3. Lihat Jumlah Blok yang diakses untuk pencarian record");
        System.out.println("4. Lihat QEP dan Cost");
        System.out.println("5. Shared Pool");
        System.out.println("6. Exit");
        System.out.println("--------------------------");
        System.out.print("Pilih Menu : ");
        
        Scanner s = new Scanner(System.in);
        int menu = s.nextInt();
        while (menu != 6) {
            switch (menu) {
                case 1:
                    System.out.println("Menu BFR dan Fanout Ratio");
                    menuBFRnFOR(dataSplit,isiBaris1);
                    menuBFRnFOR(dataSplit,isiBaris2);
                    menuBFRnFOR(dataSplit,isiBaris3);
                    System.out.println("--------------------");
                    System.out.print("Pilih menu lagi : ");
                    menu = s.nextInt();
                    break;
                case 2:
                    System.out.println("Menu Jumlah Blok Data");
                    menuJumlahBlok(dataSplit, isiBaris1);
                    menuJumlahBlok(dataSplit, isiBaris2);
                    menuJumlahBlok(dataSplit, isiBaris3);
                    System.out.println("--------------------");
                    System.out.print("Pilih menu lagi : ");
                    menu = s.nextInt();
                    break;
                case 3:
                    System.out.println("Menu Blok yang diakses untuk pencarian record");
//                    System.out.println("Under maintenance");
                    menuCariRecord(dataSplit);
                    System.out.println("--------------------");
                    System.out.print("Pilih menu lagi : ");
                    menu = s.nextInt();
                    break;
                case 4:
                    System.out.println("Menu QEP dan COST");
                    queryHandling(dataSplit);
                    System.out.println("--------------------");
                    System.out.print("Pilih menu lagi : ");
                    menu = s.nextInt();
                    break;
                case 5:
                    System.out.println("Menu Shared Pool");
                    System.out.println("--------------------");
                    try (FileReader reader = new FileReader("sharedPool.txt");
                    BufferedReader br = new BufferedReader(reader)) {
                        String line;
                        while ((line = br.readLine()) != null) {
                            System.out.println(line);
                        }
                    } catch (IOException e) {
                        System.err.format("IOException: %s%n", e);
                    }
                    System.out.print("Pilih menu lagi : ");
                    menu = s.nextInt();
                  break;
              }
        } 
    }
}
