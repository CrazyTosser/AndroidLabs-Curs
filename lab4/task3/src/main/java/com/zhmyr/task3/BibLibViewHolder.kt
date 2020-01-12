package com.zhmyr.task3

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.zhmyr.biblib.BibEntry
import com.zhmyr.biblib.Keys

abstract class BibLibViewHolder<T>(view: View) : RecyclerView.ViewHolder(view) {
    abstract fun bind(entry: T)
}

class ArticleViewHolder(view: View) : BibLibViewHolder<BibEntry>(view) {

    private val title: TextView = view.findViewById(R.id.articleTitle)
    private val author: TextView = view.findViewById(R.id.articleAuthor)
    private val journal: TextView = view.findViewById(R.id.articleJournal)
    private val year: TextView = view.findViewById(R.id.articleYear)

    override fun bind(entry: BibEntry) {
        title.text = entry.getField(Keys.TITLE)
        author.text = entry.getField(Keys.AUTHOR)
        journal.text = entry.getField(Keys.JOURNAL)
        year.text = entry.getField(Keys.YEAR)
    }
}

class MiscViewHolder(view: View) : BibLibViewHolder<BibEntry>(view) {
    private val title: TextView = view.findViewById(R.id.miscTitle)
    private val author: TextView = view.findViewById(R.id.miscAuthor)
    private val booktitle: TextView = view.findViewById(R.id.miscBooktitle)

    override fun bind(entry: BibEntry) {
        title.text = entry.getField(Keys.TITLE)
        author.text = entry.getField(Keys.AUTHOR)
        booktitle.text = entry.getField(Keys.BOOKTITLE)
    }
}

class InproceedingsViewHolder(view: View) : BibLibViewHolder<BibEntry>(view) {
    private val title: TextView = view.findViewById(R.id.inproceedingsTitle)
    private val author: TextView = view.findViewById(R.id.inproceedingsAuthor)
    private val pages: TextView = view.findViewById(R.id.inproceedingsPages)
    private val year: TextView = view.findViewById(R.id.inproceedingsYear)

    override fun bind(entry: BibEntry) {
        title.text = entry.getField(Keys.TITLE)
        author.text = entry.getField(Keys.AUTHOR)
        pages.text = entry.getField(Keys.PAGES)
        year.text = entry.getField(Keys.YEAR)
    }
}

class TechreportViewHolder(view: View) : BibLibViewHolder<BibEntry>(view) {
    private val title: TextView = view.findViewById(R.id.techreportTitle)
    private val author: TextView = view.findViewById(R.id.techreportAuthor)
    private val address: TextView = view.findViewById(R.id.techreportAddress)
    private val year: TextView = view.findViewById(R.id.techreportYear)

    override fun bind(entry: BibEntry) {
        title.text = entry.getField(Keys.TITLE)
        author.text = entry.getField(Keys.AUTHOR)
        address.text = entry.getField(Keys.ADDRESS)
        year.text = entry.getField(Keys.YEAR)
    }
}

class BookViewHolder(view: View) : BibLibViewHolder<BibEntry>(view) {
    private val title: TextView = view.findViewById(R.id.bookTitle)
    private val author: TextView = view.findViewById(R.id.bookAuthor)
    private val publisher: TextView = view.findViewById(R.id.bookPublisher)
    private val year: TextView = view.findViewById(R.id.bookYear)

    override fun bind(entry: BibEntry) {
        title.text = entry.getField(Keys.TITLE)
        author.text = entry.getField(Keys.AUTHOR)
        publisher.text = entry.getField(Keys.PUBLISHER)
        year.text = entry.getField(Keys.YEAR)
    }
}

class IncollectionViewHolder(view: View) : BibLibViewHolder<BibEntry>(view) {
    private val title: TextView = view.findViewById(R.id.incollectionTitle)
    private val author: TextView = view.findViewById(R.id.incollectionAuthor)
    private val booktitle: TextView = view.findViewById(R.id.incollectionBooktitle)

    override fun bind(entry: BibEntry) {
        title.text = entry.getField(Keys.TITLE)
        author.text = entry.getField(Keys.AUTHOR)
        booktitle.text = entry.getField(Keys.BOOKTITLE)
    }
}

class UnpublishedViewHolder(view: View) : BibLibViewHolder<BibEntry>(view) {
    private val title: TextView = view.findViewById(R.id.unpublishedTitle)
    private val author: TextView = view.findViewById(R.id.unpublishedAuthor)
    private val booktitle: TextView = view.findViewById(R.id.unpublishedBooktitle)

    override fun bind(entry: BibEntry) {
        title.text = entry.getField(Keys.TITLE)
        author.text = entry.getField(Keys.AUTHOR)
        booktitle.text = entry.getField(Keys.BOOKTITLE)
    }
}