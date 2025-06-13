package com.keepa.books;

import com.keepa.books.service.KeepaService;

public class KeepaBooksApplication {
    public static void main(String[] args) {
        try {
            KeepaService service = new KeepaService();
            service.getAndSaveTopAsins();
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}