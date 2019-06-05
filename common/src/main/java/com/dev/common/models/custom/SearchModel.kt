package com.dev.common.models.custom

import ir.mirrajabi.searchdialog.core.Searchable


class SearchModel<T> : Searchable {
    var mTitle: String? = null
    var mid: String? = null
    var mImage: String? = null
    var mserices: T? = null

    constructor (title: String?=null, id: String?=null, image: String?=null, serices: T?=null) {
        mTitle = title
        mid = id
        mImage = image
        mserices = serices
    }

    override fun getTitle(): String? {
        return mTitle
    }

    fun getServices(): T? {
        return mserices
    }


    fun getId(): String? {
        return mid
    }

    fun getImage(): String? {
        return mImage
    }


    fun set(title: String?=null, id: String?=null, image: String?=null,serices: T?=null): SearchModel<T> {
        mTitle = title
        mid = id
        mImage = image
        mserices = serices
        return this
    }
}