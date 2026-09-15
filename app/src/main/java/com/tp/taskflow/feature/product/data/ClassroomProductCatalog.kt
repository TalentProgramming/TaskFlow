package com.tp.taskflow.feature.product.data

import org.json.JSONArray
import org.json.JSONObject

object ClassroomProductCatalog {
    const val PAGE_SIZE = 8

    data class Item(
        val id: String,
        val title: String,
        val type: String,
        val amount: Double
    )

    val all: List<Item> = listOf(
        Item("p-1", "Notebook Pro", "Stationery", 4.5),
        Item("p-2", "Task Stickers", "Stationery", 2.0),
        Item("p-3", "Focus Timer", "Gadgets", 18.0),
        Item("p-4", "Desk Lamp", "Gadgets", 32.0),
        Item("p-5", "Water Bottle", "Lifestyle", 12.0),
        Item("p-6", "Canvas Backpack", "Lifestyle", 45.0),
        Item("p-7", "Kotlin Handbook", "Books", 22.0),
        Item("p-8", "Android Workbook", "Books", 19.0),
        Item("p-9", "Wireless Mouse", "Gadgets", 16.0),
        Item("p-10", "Plant Pot", "Lifestyle", 9.0),
        Item("p-11", "Gel Pen Set", "Stationery", 3.5),
        Item("p-12", "Index Tabs", "Stationery", 1.8),
        Item("p-13", "Desk Organizer", "Stationery", 11.0),
        Item("p-14", "Whiteboard Mini", "Stationery", 14.0),
        Item("p-15", "USB-C Hub", "Gadgets", 28.0),
        Item("p-16", "Bluetooth Keyboard", "Gadgets", 39.0),
        Item("p-17", "Webcam Cover", "Gadgets", 6.0),
        Item("p-18", "Laptop Stand", "Gadgets", 34.0),
        Item("p-19", "Noise Earbuds", "Gadgets", 49.0),
        Item("p-20", "Cable Sleeve", "Gadgets", 8.5),
        Item("p-21", "Ceramic Mug", "Lifestyle", 10.0),
        Item("p-22", "Desk Mat", "Lifestyle", 21.0),
        Item("p-23", "Tote Bag", "Lifestyle", 15.0),
        Item("p-24", "Umbrella Compact", "Lifestyle", 17.0),
        Item("p-25", "Snack Box", "Lifestyle", 7.5),
        Item("p-26", "Room Spray", "Lifestyle", 13.0),
        Item("p-27", "Coroutines Guide", "Books", 24.0),
        Item("p-28", "Hilt Field Notes", "Books", 18.0),
        Item("p-29", "Room Cookbook", "Books", 26.0),
        Item("p-30", "Compose Patterns", "Books", 29.0),
        Item("p-31", "Paging Case Study", "Books", 16.0),
        Item("p-32", "Clean Architecture", "Books", 31.0),
        Item("p-33", "Highlighter Pack", "Stationery", 4.0),
        Item("p-34", "Binder Clips", "Stationery", 2.5),
        Item("p-35", "Drafting Ruler", "Stationery", 3.2),
        Item("p-36", "Phone Tripod", "Gadgets", 22.0),
        Item("p-37", "Power Bank", "Gadgets", 27.0),
        Item("p-38", "Sleep Mask", "Lifestyle", 8.0),
        Item("p-39", "Tea Sampler", "Lifestyle", 11.5),
        Item("p-40", "Offline-First Notes", "Books", 20.0)
    )

    fun page(query: String, page: Int): List<Item> {
        val needle = query.trim()
        val filtered = if (needle.isEmpty()) {
            all
        } else {
            all.filter { item ->
                item.title.contains(needle, ignoreCase = true) ||
                    item.type.contains(needle, ignoreCase = true)
            }
        }
        val start = (page.coerceAtLeast(1) - 1) * PAGE_SIZE
        if (start >= filtered.size) return emptyList()
        return filtered.subList(start, minOf(start + PAGE_SIZE, filtered.size))
    }

    fun toJson(items: List<Item>): String {
        val array = JSONArray()
        items.forEach { item ->
            array.put(
                JSONObject()
                    .put("product_id", item.id)
                    .put("title", item.title)
                    .put("type", item.type)
                    .put("amount", item.amount)
            )
        }
        return array.toString()
    }
}
