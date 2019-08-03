package service

import ratecalculator.error.LoanCalculationErrors
import ratecalculator.model.Lender
import ratecalculator.service.LenderCsvFileReadingServiceImpl
import spock.lang.Specification
import spock.lang.Unroll


@Unroll
class LenderCsvFileReadingServiceSpec extends Specification {
    def "File reading service reads file successfully #reason"() {
        given : "A file with lenders in it and a file reading service"
            def service = new LenderCsvFileReadingServiceImpl()
            String fileLocation = getClass().getResource('/data.csv').path

        when : "I call the file reading service with a file"
            def lenders = service.readLenderFile(fileLocation)

        then : "No exception is thrown"
            noExceptionThrown()

        and : "the list contains lenders"
            lenders.size() == 7
            lenders.forEach({ lender ->
                assert lender as Lender
            })
    }

    def "Exception is thrown if application cannot find file" () {
        given : "A file that does not exist and a file reading service"
            def service = new LenderCsvFileReadingServiceImpl()
            String fileLocation = '/i/dont/exist/data.csv'

        when : "I call the file reading service with a file"
            service.readLenderFile(fileLocation)

        then : "An exception is thrown due to no file existing"
            RuntimeException ex = thrown()
            ex.message == LoanCalculationErrors.FILE_NOT_FOUND.text +" "+ fileLocation
    }

    def "Exception is thrown if application reads a file with bad data" () {
        given : "A file that does not exist and a file reading service"
            def service = new LenderCsvFileReadingServiceImpl()
            String fileLocation = getClass().getResource('/bad_data.csv').path

        when : "A call the file reading service with a file"
            service.readLenderFile(fileLocation)

        then : "An exception is thrown due to bad contents"
            RuntimeException ex = thrown()
            ex.message == LoanCalculationErrors.ERROR_WITH_CONTENTS.text
    }


}
