/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.safaricom.movie.utils;

/**
 *
 * @author david
 */
public interface AppConstants {

    public interface Pagination {
        // Pagination default settings
        String DEFAULT_ORDER_DIRECTION = "DESC";
        String DEFAULT_PAGE_NUMBER = "0";
        String DEFAULT_PAGE_SIZE = "30";
        String DEFAULT_ORDER_BY ="id";
        int MAX_PAGE_SIZE = 50;
    }
}
