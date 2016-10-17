package com.tuotiansudai.service;


import com.tuotiansudai.dto.BasePaginationDataDto;
import com.tuotiansudai.dto.BookingLoanPaginationItemDataDto;
import com.tuotiansudai.enums.Source;
import com.tuotiansudai.repository.model.BookingLoanSumAmountView;
import com.tuotiansudai.repository.model.ProductType;

import java.util.Date;
import java.util.List;

public interface BookingLoanService {
    void create(String phoneNum, ProductType productType, String bookingAmount);

    BasePaginationDataDto<BookingLoanPaginationItemDataDto> bookingLoanList(ProductType productType, Date bookingTimeStartTime,
                                                                            Date bookingTimeEndTime, String mobile,
                                                                            Date noticeTimeStartTime, Date noticeTimeEndTime,
                                                                            Source source,
                                                                            Boolean status, Integer index, Integer pageSize);

    List<List<String>> getBookingLoanReportCsvData(ProductType productType, Date bookingTimeStartTime,
                                                   Date bookingTimeEndTime, String mobile,
                                                   Date noticeTimeStartTime, Date noticeTimeEndTime,
                                                   Source source,
                                                   Boolean status);

    void noticeBookingLoan(long bookingLoanId);

    List<BookingLoanSumAmountView> findBookingLoanSumAmountByProductType(ProductType productType, Date bookingTimeStartTime,
                                                                         Date bookingTimeEndTime, String mobile,
                                                                         Date noticeTimeStartTime, Date noticeTimeEndTime,
                                                                         Source source, Boolean status);
}
