package ratecalculator.service;

import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import ratecalculator.error.LoanCalculationErrors;
import ratecalculator.model.Lender;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class LenderCsvFileReadingServiceImpl implements LenderCsvFileReadingService {

    private static final int LINE_TO_SKIP = 1;
    private static final int NAME = 0;
    private static final int RATE = 1;
    private static final int AMOUNT = 2;

    @Override
    public List<Lender> readLenderFile(String filePath) {
        List<Lender> lenders = new ArrayList<>();
        CSVReader csvReader = new CSVReaderBuilder(getFileReader(filePath))
                .withSkipLines(LINE_TO_SKIP)
                .build();
        try {
            csvReader.readAll().forEach(record -> {
                lenders.add(new Lender(record[NAME], new BigDecimal(record[RATE]), new BigDecimal(record[AMOUNT])));
            });
        } catch (IOException e) {
            throw new RuntimeException(LoanCalculationErrors.ERROR_READING_FILE.getText() + " " + filePath);
        } catch (NumberFormatException | ArrayIndexOutOfBoundsException exception) {
            throw new RuntimeException(LoanCalculationErrors.ERROR_WITH_CONTENTS.getText());
        }
        return lenders;
    }

    private FileReader getFileReader(String filePath) {
        FileReader fileReader;
        try {
            fileReader = new FileReader(filePath);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(LoanCalculationErrors.FILE_NOT_FOUND.getText() + " " + filePath);
        }
        return fileReader;
    }
}
