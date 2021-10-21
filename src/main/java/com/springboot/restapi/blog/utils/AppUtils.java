package com.springboot.restapi.blog.utils;

import com.springboot.restapi.blog.exception.BlogApiException;
import org.springframework.http.HttpStatus;

public class AppUtils {

    public static void validatePageNumberAndSize(int pageNumber, int pageSize) {
        // pageNumber > 0 and 0 < pageSize < 30
        if (pageNumber < 0) {
            throw new BlogApiException(HttpStatus.BAD_REQUEST, "Page number cannot be less than 0.");
        }
        if (pageSize < 0) {
            throw new BlogApiException(HttpStatus.BAD_REQUEST, "Size number cannot be less than 0.");
        }
        if (pageSize > AppConstants.MAX_PAGE_SIZE) {
            throw new BlogApiException(HttpStatus.BAD_REQUEST, "Page size must not be greater than " + AppConstants.MAX_PAGE_SIZE);
        }
    }

}
