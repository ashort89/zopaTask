package ratecalculator.service;

import ratecalculator.model.Lender;

import java.util.List;

public interface LenderCsvFileReadingService {

    List<Lender> readLenderFile(String filePath);
}
