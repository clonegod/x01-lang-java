/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asynclife.basic.advanced.construction.factory;

/**
 *
 * @author Administrator
 */
public class KindleLibrary implements BookFactory {

    @Override
    public Book newBook() {
        return new KindleBook();
    }

}
