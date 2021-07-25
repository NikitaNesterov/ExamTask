import au.com.bytecode.opencsv.CSVWriter;

import java.io.FileWriter;
import java.io.IOException;

public class CsvFileWriter implements ProjectService {
    private CSVWriter csvWriter;

    public CsvFileWriter(CSVWriter csvWriter) {
        this.csvWriter = csvWriter;
    }

    @Override
    public void toCreateFile(String fileName) {
        CSVWriter writer = null;
        try {
            writer = new CSVWriter(new FileWriter(fileName));
        } catch (IOException e) {
            e.printStackTrace();
        }
        String[] header = {"Link", "Image", "Title", "Current price", "Original price", "Discount", "Sold", "Claimed"};
        writer.writeNext(header);
    }

    @Override
    public void toWrite(String[] s) {
        csvWriter.writeNext(s);

    }

    @Override
    public void toClose() {
        try {
            csvWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean checkError() {
        return csvWriter.checkError();
    }
}
