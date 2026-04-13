package roychanGill.belajar_java_CSV;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVPrinter;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

public class TestCSV {

    @Test
    void testCreateCSV() {
        // untuk membuat CSV gunakan class CSVPrinter lalu tambahkan target

        try(Writer writer = Files.newBufferedWriter(Path.of("src/csv/cek.csv"))) {

            CSVPrinter printer = new CSVPrinter(writer, CSVFormat.DEFAULT);

            printer.printRecord("Gilbrant", 24, 100);
            printer.printRecord("Roychan", 14, 100);

            printer.flush();
        }catch (IOException e){
            Assertions.fail(e);
        }
    }

    @Test
    void readCsv() {
        // untuk membaca CSV, gunakan class CSVParser


        try(
                Reader r = Files.newBufferedReader(Path.of("src/csv/cek.csv"));
        ){
            var baca =  CSVParser.parse(r,CSVFormat.DEFAULT);

            for ( var x: baca.getRecords()){
                System.out.println(x.get(0));
                System.out.println(x.get(1));
                System.out.println(x.get(2));
            }

            System.out.println(baca.getRecordNumber());

        }catch (IOException e){
            Assertions.fail(e);
        }
    }

    @Test
    void settingHeaderTest() {

        // biasanya kita menentukan header di baris pertama, tapi kita bs men set  Header di format

        StringWriter writer = new StringWriter();

        CSVFormat format = CSVFormat.DEFAULT.builder().setHeader("Nama", "umur", "angka")
                .get();

        // header akan membuat otomatis di baris pertama
        try(Writer r = Files.newBufferedWriter(Path.of("src/csv/cek.csv"));
                CSVPrinter printer = new CSVPrinter(writer, format);
            CSVPrinter printerFiles = new CSVPrinter(r, format)
        ) {


            printer.printRecord("Gilbrant", 24, 100);
            printer.printRecord("Roychan", 12, 120);
            printer.printRecord("TehCIna",400);

            // di simpan di files

            printerFiles.printRecord("Gilbrant", 24, 100);
            printerFiles.printRecord("Roychan", 12, 120);
            printerFiles.printRecord("TehCIna",400);


            printer.flush();


            // lalu cek dengan dengan mengambil data writter

            String s = writer.getBuffer().toString();

            System.out.println(s);

        } catch (IOException e) {
            Assertions.fail(e);
        }
    }
}
